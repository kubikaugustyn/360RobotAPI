#  -*- coding: utf-8 -*-
__author__ = "Jakub Augustýn <kubik.augustyn@post.cz>"

import json, base64, cv2
from datetime import datetime
import os.path
from typing import Any, Optional, Callable
from colorama import Fore
import numpy as np
import lz4.block

# See endpoints/CleaningRecords.http and provide the full response of "Get a single cleaning record"
IN_PATH = os.path.abspath(r"./CleaningRecordResponse.json")


def render_vertices(img: cv2.typing.MatLike, vertices: list[list[int]],
                    fill_color: tuple[int, int, int],
                    transform: Callable[[int, int], tuple[int, int]],
                    centroid: Optional[str] = None,
                    allow_zeroes: bool = True) -> None:
    coverage: list[tuple[int, int]] = []
    for pos in vertices:
        x, y = pos
        if not allow_zeroes and x == 0 and y == 0:
            continue
        coverage.append(transform(x, y))

    np_coverage: np.ndarray = np.array(coverage, np.int32)
    cv2.fillPoly(img, [np_coverage], fill_color)
    cv2.polylines(img, [np_coverage], True, (0, 0, 255), 2)
    for point in coverage:
        cv2.circle(img, point, 1, (255, 0, 0), -1)

    # Centroid
    if centroid is not None:
        M = cv2.moments(np_coverage)
        if M["m00"] != 0:
            cx = int(M["m10"] / M["m00"])
            cy = int(M["m01"] / M["m00"])
        else:
            cx, cy = np_coverage[0]  # fallback
            cx -= 10

        (text_w, text_h), _ = cv2.getTextSize("1", cv2.FONT_HERSHEY_SIMPLEX, 0.8, 2)
        cv2.putText(img, centroid, (cx - text_w // 2, cy + text_h // 2), cv2.FONT_HERSHEY_SIMPLEX,
                    0.8, (255, 127, 255), 2, cv2.LINE_AA)


def main() -> None:
    print(f"{Fore.GREEN}Loading the JSON from {Fore.BLUE}{IN_PATH}{Fore.GREEN}...{Fore.RESET}")
    with open(IN_PATH, "r") as f:
        data = json.load(f)

    assert data["errmsg"] == "Succeeded"
    record: dict[str, Any] = data["data"]["record"]

    # General logging
    start_time: datetime = datetime.fromtimestamp(record["start"])
    end_time: datetime = datetime.fromtimestamp(record["end"])
    print(f"{Fore.GREEN}Cleaning started {Fore.BLUE}{start_time}{Fore.GREEN} and "
          f"ended {Fore.BLUE}{end_time}{Fore.RESET}")

    # Extract the map and its size
    assert len(record["map"]) == record["base64_len"]
    map_bytes: bytes = base64.b64decode(record["map"])
    assert len(map_bytes) == record["lz4_len"]
    w, h, x_min, y_min = record["width"], record["height"], record["x_min"], record["y_min"]
    resolution: float = record["resolution"]
    assert w > 0 and h > 0 and resolution > 0

    # Decompress the map
    map_decompressed: bytes = lz4.block.decompress(map_bytes, uncompressed_size=w * h)
    assert len(map_decompressed) == w * h

    print(f"{Fore.GREEN}The map is {Fore.BLUE}{w} x {h} cells{Fore.GREEN} with a resolution "
          f"of {Fore.BLUE}{resolution} m/cell{Fore.GREEN} and "
          f"the coordinates of the top left corner are {Fore.BLUE}({x_min}, {y_min}){Fore.RESET}")

    map_: np.ndarray = np.frombuffer(map_decompressed, dtype=np.uint8)
    map_.shape = (h, w)

    map_colored: cv2.typing.MatLike = cv2.cvtColor(map_, cv2.COLOR_GRAY2RGB)
    scale: float = 5
    map_ = cv2.resize(map_, (0, 0), fx=scale, fy=scale, interpolation=cv2.INTER_NEAREST)
    map_colored = cv2.resize(map_colored, (0, 0), fx=scale, fy=scale,
                             interpolation=cv2.INTER_NEAREST)
    map_colored_original = np.copy(map_colored)

    # Draw all different features
    def cell2point(cellX: float, cellY: float) -> tuple[int, int]:
        return (int(cellX * scale),
                int(cellY * scale))

    def physical2cell(mmX: float, mmY: float) -> tuple[int, int]:
        # 1000 mm = 1 m
        return (int((mmX / 1000 - x_min) / resolution),
                int((mmY / 1000 - y_min) / resolution))

    def physical2point(mmX: float, mmY: float) -> tuple[int, int]:
        return cell2point(*physical2cell(mmX, mmY))

    # Origin
    print(f"{Fore.GREEN}Origin cell coordinates: {Fore.BLUE}{physical2cell(0, 0)}{Fore.RESET}")
    cv2.circle(map_colored, physical2point(0, 0), 7, (255, 0, 255), -1)
    # Charging dock
    if "chargeHandlePos" in record:
        y, x = record["chargeHandlePos"]
        cv2.circle(map_colored, physical2point(x, y), 10, (0, 255, 0), -1)
    # The points the robot visited (XY pairs in millimeter offsets from the origin)
    if "posArray" in record:
        line: list[tuple[int, int]] = []
        for pos in record["posArray"]:
            x, y = pos
            line.append(physical2point(x, y))
        cv2.polylines(map_colored, [np.array(line, np.int32)], False, (0, 0, 255), 2)
        for point in line:
            cv2.circle(map_colored, point, 1, (255, 0, 0), -1)
    # Rooms/areas
    room_img: Optional[cv2.typing.MatLike] = None
    if "smartArea" in record:
        areas: dict[str, Any] = record["smartArea"]
        print(f"{Fore.GREEN}The map contains {Fore.BLUE}{len(areas["value"])}"
              f"{Fore.GREEN} area(s):{Fore.RESET}")

        room_img: cv2.typing.MatLike = np.copy(map_colored_original)
        for area in areas["value"]:
            id_: int = area["id"]
            active: bool = id_ in areas["activeIds"]
            name: str = base64.b64decode(area["name"]).decode("utf-8")
            cleanTimes: int = area["cleanTimes"]
            windMode: str = area["windMode"]
            waterPump: int = area["waterPump"]

            print(f"{Fore.GREEN}\tID {id_} - '{Fore.BLUE}{name}{Fore.GREEN}', "
                  f"{Fore.GREEN + 'active' if active else Fore.RED + 'inactive'}{Fore.GREEN}, "
                  f"cleaned {Fore.BLUE}{cleanTimes}{Fore.GREEN} time(s) "
                  f"using {Fore.BLUE}{windMode}{Fore.GREEN} suction "
                  f"and mopping mode {Fore.BLUE}{waterPump}{Fore.GREEN} "
                  f"{Fore.RESET}")

            coverage_img: cv2.typing.MatLike = np.copy(room_img)
            render_vertices(coverage_img, area["vertexs"], (0, 255, 0) if active else (255, 255, 0),
                            transform=physical2point, centroid=str(id_))

            alpha: float = 0.7
            room_img = cv2.addWeighted(room_img, 1 - alpha, coverage_img, alpha, 0)
    # Blocked areas
    blocked_img: Optional[cv2.typing.MatLike] = None
    if "area" in record:
        print(f"{Fore.GREEN}The map contains {Fore.BLUE}{len(record["area"])}"
              f"{Fore.GREEN} blocked area(s):{Fore.RESET}")

        blocked_img: cv2.typing.MatLike = np.copy(map_colored_original)
        for block in record["area"]:
            id_: int = block["id"]
            active: str = block["active"]
            forbidType: str = block["forbidType"]
            name: str = block["name"]
            tag: str = block["tag"]

            print(f"{Fore.GREEN}\tID {id_} - {Fore.BLUE}{name}{Fore.GREEN} "
                  f"#{Fore.BLUE}{tag}{Fore.GREEN} - "
                  f"type {Fore.BLUE}{active}{Fore.GREEN}, "
                  f"forbidden cleaning {Fore.BLUE}{forbidType}{Fore.GREEN} "
                  f"{Fore.RESET}")

            color = {
                "forbid": (0, 255, 255),
                "line": (0, 255, 0),
                "depth": (255, 255, 0),
            }.get(active, (0, 255, 0))
            block_coverage_img: cv2.typing.MatLike = np.copy(blocked_img)
            render_vertices(block_coverage_img, block["vertexs"], color,
                            transform=physical2point, centroid=str(id_), allow_zeroes=False)

            alpha: float = 0.7
            blocked_img = cv2.addWeighted(blocked_img, 1 - alpha, block_coverage_img, alpha, 0)

    map_ = cv2.flip(map_, 1)
    # cv2.imshow("Raw map", map_)
    map_colored = cv2.flip(map_colored, 1)
    cv2.imshow("Map", map_colored)
    if room_img is not None:
        # room_img = cv2.flip(room_img, 1)
        cv2.imshow("Map with rooms", room_img)
    if blocked_img is not None:
        cv2.imshow("Map with blocked areas", blocked_img)

    print(f"{Fore.GREEN}Press any key to exit{Fore.RESET}")
    print(f"{Fore.GREEN}Press {Fore.BLUE}R{Fore.GREEN} to save the raw map{Fore.RESET}")
    print(f"{Fore.GREEN}Press {Fore.BLUE}C{Fore.GREEN} to save the colored map{Fore.RESET}")
    print(f"{Fore.GREEN}Press {Fore.BLUE}A{Fore.GREEN} to save the map with rooms{Fore.RESET}")
    print(f"{Fore.GREEN}Press {Fore.BLUE}B{Fore.GREEN} to save the map w/blocked areas{Fore.RESET}")
    key = cv2.waitKey(0)
    if key == ord("r"):
        cv2.imwrite("raw_map.png", map_)
    elif key == ord("c"):
        cv2.imwrite("colored_map.png", map_colored)
    elif key == ord("a"):
        cv2.imwrite("map_with_rooms.png", room_img)
    elif key == ord("b"):
        cv2.imwrite("map_with_blocked_areas.png", blocked_img)

    cv2.destroyAllWindows()
    print(f"{Fore.GREEN}Copyright 2026 {Fore.BLUE}{__author__}{Fore.RESET}")
    print(f"{Fore.GREEN}Map renderer done, goodbye!{Fore.RESET}")


if __name__ == '__main__':
    main()
