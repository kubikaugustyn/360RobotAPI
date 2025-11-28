#  -*- coding: utf-8 -*-
__author__ = "Jakub Augustýn <kubik.augustyn@post.cz>"

import tarfile, subprocess, tempfile, hashlib
from copy import copy

from colorama import Fore
import os.path

IN_PATH = os.path.abspath(r"./files")
BASE_TAR_PATH = os.path.abspath(r"../source/en_us_20221013.tar.gz")
OUT_TAR_PATH = os.path.abspath(r"./custom_voice.tar.gz")


def main() -> None:
    print(f"{Fore.LIGHTBLACK_EX}Base .tar.gz sourced from {BASE_TAR_PATH}{Fore.RESET}")
    print(f"{Fore.LIGHTBLACK_EX}Override files located in {IN_PATH}{Fore.RESET}")

    in_files: dict[str, str] = {}
    for name in os.listdir(IN_PATH):
        full_path = os.path.join(IN_PATH, name)
        if os.path.isfile(full_path):
            in_files[os.path.basename(full_path)] = full_path

    with (tarfile.open(BASE_TAR_PATH, "r:gz") as base_tar,
          tarfile.open(OUT_TAR_PATH, "w:gz") as out_tar):
        base_members: list[tarfile.TarInfo] = base_tar.getmembers()
        for member in base_members:
            member_name: str = os.path.basename(member.name)
            if member.isfile() and member_name in in_files:
                print(f"{Fore.GREEN}Replacing {member_name}{Fore.RESET}")

                if os.path.splitext(member_name)[1] == ".mp3":
                    # FFmpeg conversion into a temporary file-like object
                    print(f"{Fore.GREEN}Converting {member_name} to a lower quality "
                          f"to be ready for playback on the robot vacuum{Fore.RESET}")
                    with tempfile.TemporaryFile() as temp_file:
                        # ffmpeg -i INPUT -ar 44100 -b:a 128k pipe:1
                        subprocess.run([
                            "ffmpeg", "-i", in_files[member_name],
                            "-ar", "44100",
                            "-b:a", "128k",
                            "-f", "mp3", "pipe:1"
                        ], stdout=temp_file, check=True)

                        # Idk why, but we have to provide the real size of the temporary file
                        temp_member = copy(member)
                        temp_member.size = temp_file.tell()
                        # We have to seek to the beginning of the file (or maybe not?)
                        temp_file.seek(0)
                        # Finally, add the file to the tar archive
                        out_tar.addfile(temp_member, temp_file)
                else:
                    with open(in_files[member_name], "rb") as f:
                        out_tar.addfile(member, f)
            else:
                print(f"{Fore.LIGHTBLACK_EX}Copying {member.name}{Fore.RESET}")
                with (base_tar.extractfile(member) if member.isfile() else None) as f:
                    out_tar.addfile(member, f)

    print(f"{Fore.GREEN}Output .tar.gz saved to {OUT_TAR_PATH}{Fore.RESET}")

    print(f"{Fore.GREEN}Useful stats for uploading:{Fore.RESET}")
    md5 = hashlib.md5()
    size: int = 0
    with open(OUT_TAR_PATH, "rb") as f:
        while chunk := f.read(64 * 1024):
            md5.update(chunk)
            size += len(chunk)
    print(f"\t{Fore.GREEN}MD5: {md5.hexdigest()}{Fore.RESET}")
    print(f"\t{Fore.GREEN}Size: {size} B{Fore.RESET}")


if __name__ == '__main__':
    main()
