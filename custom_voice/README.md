# Custom voice for your 360 Robot

## Fill in the required data

We'll need to fill in all the fields:

```json
{
  "cmd": "download OR downloadAndApply",
  "id": "...",
  "downUrl": "...",
  "md5sum": "...",
  "size": 0,
  "lang": "..."
}
```

To get the MD5 hash of the file on Windows, run this command in PowerShell:

```powershell
Get-FileHash lingo.tar.gz -Algorithm MD5
# Or even simpler:
(Get-FileHash lingo.tar.gz -Algorithm MD5).Hash
```

The ID and lang fields are fairly simple. I don't want to try random strings, so choose one of the existing below. The
language you choose doesn't affect the audio files being played; that is determined by what you upload to the robot.
These are just the values for the UI to be able to show that "Spanish" is active, even though under the hood it's your
voice.

| Lingo    | id        | lang  |
|----------|-----------|-------|
| Croatia  | Voicehr   | en_us |
| Spanish  | esvoiceen | en_us |
| Korean   | 4en       | en_us |
| Turkish  | trvoicede | en_us |
| Italian  | itvoiceen | en_us |
| French   | frvoiceen | en_us |
| German   | devoiceen | en_us |
| Japanese | jpvoiceen | en_us |
| Russian  | ruvoiceen | en_us |
| Chinese  | chinese   | en_us |
| English  | ywb       | en_us |

Oh yeah, and the lang is always `en_us`... but that depends on 360. So it may change in the future. Or it may be a bug
in their code, because the Chinese don't correct typos and stuff.

To get the size: well, come on... If you're here, you'll know. It's the length of the file in bytes.

And finally, `downUrl`, the URL to download the file from. This is also on you, I'm using a simple HTTP server and
letting the robot download it from the LAN.

```shell
python -m http.server 6969
```

Damn it, it doesn't seem to be downloading the file. Ah, I messed up the request. No error, but it did nothing. Now it's
fixed, and it downloaded it from my PC. Is this how we get RCE using path traversal? Well, anyway, now I need to apply
it.

## Upload

Remember to first change the language to a different one in the UI, then delete the old one (such as your English) and
only then download and apply the new version.

## Soundtrack transcripts

Feel free to add more; these are just a few I've used.

"Usual" — These sounds are the ones I think are commonly played, so it makes sense to translate them. The other ones can
be, of course, translated as well, but there are a lot of them.

Oh yes, and the MP3 files in the archive are in a specific format (bitrate etc.) to be able to be played by the robot's
hardware. Otherwise, there will be cracking noises and the audio will be hard to make out. This is taken care of by my
script (`pack.py`), which uses [FFmpeg](https://www.ffmpeg.org/) to convert the files into the correct format before
adding them to the archive. The files on your PC will stay untouched, the conversion happens within temporary files.

Also, the files are located in a `.` folder within the archive (e.g., `/path/to/archive/lang.tar.gz/./Q008.mp3`) and the
ID is prefixed with the letter Q.

| ID  | Usual | English transcript                                                                                          |
|-----|-------|-------------------------------------------------------------------------------------------------------------|
| 001 |       | [Startup chime]                                                                                             |
| 003 | ✅     | Turned on.                                                                                                  |
| 004 |       | Connected.                                                                                                  |
| 005 |       | WiFi reset. Please follow the instructions [...]                                                            |
| 006 | ✅     | Initiating...                                                                                               |
| 007 | ✅     | Take a break.                                                                                               |
| 008 | ✅     | Cleaning makes me happy!                                                                                    |
| 009 | ✅     | Okay, I'm going to clean here.                                                                              |
| 010 | ✅     | Cleaning task completed, going to charge.                                                                   |
| 011 | ✅     | Low battery, going to charge.                                                                               |
| 012 | ✅     | Beginning to charge.                                                                                        |
| 013 | ✅     | Charging is paused.                                                                                         |
| 014 | ✅     | Charging.                                                                                                   |
| 016 | ✅     | Fully charged, resuming cleaning.                                                                           |
| 017 | ✅     | Battery low, please try again.                                                                              |
| 018 |       | Starting firmware upgrade.                                                                                  |
| 019 |       | Firmware upgraded.                                                                                          |
| 020 | ✅     | Dust bin removed.                                                                                           |
| 021 | ✅     | Dust bin returned.                                                                                          |
| 022 | ✅     | Goodbye!                                                                                                    |
| 023 |       | Map data invalid, planning route again.                                                                     |
| 024 | ✅     | Dust bin missing.                                                                                           |
| 025 |       | The top radar seems to be blocked.                                                                          |
| 026 |       | Please restart me somewhere else.                                                                           |
| 027 |       | Please wipe the bottom anti-drop sensor and restart me somewhere else.                                      |
| 028 |       | Please wipe the front anti-collision sensor and restart me somewhere else.                                  |
| 029 |       | Please wipe the right side wall sensor and restart me somewhere else.                                       |
| 030 |       | Please wipe the top radar cover and restart me somewhere else.                                              |
| 031 |       | I'm stuck! Restart me somewhere else.                                                                       |
| 032 |       | The dust bin is full. Please clear the dust bin and filter.                                                 |
| 033 |       | Please clean the dust bin and the filter surface.                                                           |
| 034 |       | I'm stuck! Restart me somewhere else.                                                                       |
| 035 |       | Floor uneven. Please restart me on an even surface.                                                         |
| 036 |       | Driving wheel seems to be stuck. Please help me!                                                            |
| 037 |       | Main brush seems to be stuck. Please help me!                                                               |
| 038 |       | Side brush seems to be stuck. Please help me!                                                               |
| 039 |       | Charging failed, because the charging dock cannot be located.                                               |
| 040 |       | Malfunction, please try to restart.                                                                         |
| 041 |       | Something went wrong. Please turn off and wait a while.                                                     |
| 042 |       | Battery low, please recharge.                                                                               |
| 043 | ✅     | Unable to turn off while on charging dock.                                                                  |
| 044 | ✅     | Okay, I'm going to clean this area.                                                                         |
| 045 | ✅     | I'm here!                                                                                                   |
| 046 | ✅     | Okay, I'm going to clean there.                                                                             |
| 047 | ✅     | Scheduled cleaning time has arrived. Starting...                                                            |
| 055 | ✅     | I'll come back later.                                                                                       |
| 058 | ✅     | This door seems to be closed.                                                                               |
| 059 |       | Please remove the surrounding obstacles.                                                                    |
| 066 |       | Fan isn't working properly.                                                                                 |
| 070 |       | The firmware upgrade failed.                                                                                |
| 072 |       | Location settings failed, unable to clean.                                                                  |
| 073 |       | Location settings failed, unable to find charging dock.                                                     |
| 074 | ✅     | Water tank has been removed.                                                                                |
| 075 | ✅     | Water tank has been put back.                                                                               |
| 078 |       | Linking failed, please try again.                                                                           |
| 079 |       | Starting to connect...                                                                                      |
| 081 |       | Please restart me in a new place.                                                                           |
| 084 |       | Something went wrong.                                                                                       |
| 089 |       | The robot is stuck and is trying to get away.                                                               |
| 090 |       | Path is blocked, trying other paths.                                                                        |
| 091 |       | Turning on, please wait.                                                                                    |
| 092 | ✅     | Scheduled cleaning started.                                                                                 |
| 093 |       | Customized cleaning                                                                                         |
| 094 |       | Regional settings set.                                                                                      |
| 100 |       | [Unknown chime]                                                                                             |
| 103 |       | The password is incorrect. Please reset WiFi and enter the correct password.                                |
| 104 |       | Location settings failed, cleaning task suspended.                                                          |
| 107 |       | Volume set.                                                                                                 |
| 110 | ✅     | Entering mopping mode.                                                                                      |
| 111 | ✅     | Water tank has been removed, exiting mopping mode.                                                          |
| 112 | ✅     | Please put the water tank back and restart.                                                                 |
| 113 |       | Water insufficient. Please fill the water tank.                                                             |
| 114 | ✅     | The remote control is on.                                                                                   |
| 115 | ✅     | The remote control is off.                                                                                  |
| 116 |       | Please clean the dust bin.                                                                                  |
| 117 |       | Please wipe the bottom anti-drop sensor and restart me in a safe place.                                     |
| 118 |       | Please remove the obstacles around the charging dock.                                                       |
| 119 |       | Please wipe the front sensor so I can locate the charging dock.                                             |
| 120 |       | Please make sure the top radar is NOT blocked.                                                              |
| 121 | ✅     | Charged, ready to resume cleaning.                                                                          |
| 122 |       | Please put me back in the charging dock and check<br/>that the battery level is above 20% before upgrading. |
| 123 |       | Please do not use while upgrading.                                                                          |
| 124 |       | Please upgrade the firmware again.                                                                          |
| 125 |       | Please put me back on the floor, then restart.                                                              |
| 126 |       | Sorry, I cannot go to the area you specified.                                                               |
| 127 |       | Please restart away from the off-limit area.                                                                |
| 128 |       | The battery is damaged or not installed.                                                                    |
| 129 |       | The water pump is stuck and unable to continue mopping<br/>the floor. Please contact aftersales services.   |
| 130 |       | Please take off the anti-collision bar.                                                                     |
| ... |       | [If you want these, you'll have to listen yourself, and then ideally send a PR]                             |
| 148 | ✅     | Start mopping.                                                                                              |
| 149 | ✅     | Continue mopping.                                                                                           |
| 150 | ✅     | Mopping completed, going to charge.                                                                         |
| 151 | ✅     | Mopping mode. Suction power cannot be adjusted.                                                             |
| ... |       | [If you want these, you'll have to listen yourself, and then ideally send a PR]                             |
| 161 |       | Quiet level.                                                                                                |
| 162 |       | Standard level.                                                                                             |
| 163 |       | Power level.                                                                                                |
| 164 |       | MAX level.                                                                                                  |
| 165 |       | Low water level.                                                                                            |
| 166 |       | Medium water level.                                                                                         |
| 167 |       | High water level.                                                                                           |
| ... |       | [If you want these, you'll have to listen yourself, and then ideally send a PR]                             |
| 170 |       | Enter sleep mode, goodbye!                                                                                  |
| ... |       | [If you want these, you'll have to listen yourself, and then ideally send a PR]                             |
| 179 |       | Exploring the room environment...                                                                           |
| 180 |       | Switched to the current map. Reset the cleaning area.                                                       |
| 181 |       | New environment found. Start to create the map.                                                             |
| 182 |       | Exploring the room environment. Please wait.                                                                |
| 183 |       | I'm lost! I'll explore the current environment before docking.                                              |
| 184 |       | Start mopping. I will not clean the carpet area.                                                            |
| 185 |       | Carpet detected. Remove mopping pad bracket and clean.                                                      |
| ... |       | [If you want these, you'll have to listen yourself, and then ideally send a PR]                             |
| 189 |       | Quiet mode.                                                                                                 |
| 190 |       | Standard mode.                                                                                              |
| 191 |       | Power mode.                                                                                                 |
| 192 |       | MAX mode.                                                                                                   |   
| ... |       | [If you want these, you'll have to listen yourself, and then ideally send a PR]                             |
