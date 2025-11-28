# What's this about?

This is a repository where I put all of my research on the chinese [360](https://smart.360.com/) (company) robot vacuum
cleaner S6, its cloud API and everything around that to help me make my own vacuum better.

The final goal is getting RCE, privilege escalation and root on the robot, thus allowing Valetudo to be installed.
However, I'm currently not there yet, but you can try to exploit a path traversal, as the language packs are `.tar.gz`
archives and may allow for RCE.

What I have achieved so far is getting to know the cloud APIs, the language packs (making the robot speak using
arbitrary MP3 files, such as your custom voice pack :D) and figuring out how to get the information returned by the
APIs (for certain APIs it is sent through a separate push message service/channel, everything is logged by the Android
app, so ADB is the best tool as of now).

# Resources

- [stonegray's site - 360 Smart Vacuum](https://stonegray.ca/blog/360vac/#sending-commands)
- [GitHub - stonegray/360smartai](https://github.com/stonegray/360smartai/)
- [GitHub - bmachuletz/amcv360RequestRecorder](https://github.com/bmachuletz/amcv360RequestRecorder)
- [Unleash your smart-home devices: Vacuum Cleaning Robot Hacking](https://fahrplan.events.ccc.de/congress/2017/Fahrplan/system/event_attachments/attachments/000/003/367/original/34c3_Staubi-current_split_animation.pdf)
- [Robot manual](https://www.botslab360.com/uploads/soft/20230801/1-230P114535aA.pdf)

# Logging in

I'll just add a few words, stonegray described it very well.

On Windows, you'll first need to connect to the phone (such as using `adb connect IP:PORT` with the information from the
Developer Options). Then, as pipes appear to not be working, use the shell to execute the grep:
`adb shell "logcat | grep -m 1 MyPushMessageListener.java | tail -c 93"` You can of course just get the shell and then
run the command.

If you see something like this:

```
11-24 16:49:38.321 22283 22283 D MyPushMessageListener.java: 1:push_debug onNeedRestart
```

don't worry, try navigating in the app and re-running the command. The expected output looks like this:

```
pushkey:<REDACTED> qid:<REDACTED> sid:<REDACTED>
```

# Sniffing on the commands

I copied the `SweeperPresenter.java` file from the decompiled Android app to `source/SweeperPresenter.java`.

After connecting to your phone with ADB, you can run the following command in the shell:

```shell
logcat | grep sendCmd
```

This will give you the following output format, the content depends on what you do in the app:

```
11-24 18:49:45.205 22283 22283 E SweeperPresenter.java: 4:sendCmd(cmd=21034, data=, taskidStr=3fbc4507-b856-49a7-9cd9-13845e106d32, timeout=30)
11-24 18:49:45.478 22283 22283 E SweeperPresenter.java: 4:sendCmd(cmd=30000, data={"cmds":[{"data":{},"infoType":"20001"},{"data":{},"infoType":"21014"},{"data":{"mask":0,"startPos":0,"userId":0},"infoType":"21011"}],"mainCmds":[]}, taskidStr=null, timeout=30)
11-24 18:49:46.036 22283 22283 E qihoo.smarthome: 17:sendCmd complete, taskid=3fbc4507-b856-49a7-9cd9-13845e106d32
11-24 18:49:46.887 22283 22283 E qihoo.smarthome: 17:sendCmd complete, taskid=04b6eeb1-2a6a-4067-a094-d15cf03a56c3
11-24 18:49:56.988 22283 22283 E SweeperPresenter.java: 4:sendCmd(cmd=21022, data={"cmd":"quiet", "cleanType":"total"}, taskidStr=81f102f7-223a-48f8-9222-325b5836e413, timeout=30)
11-24 18:49:57.883 22283 22283 E qihoo.smarthome: 17:sendCmd complete, taskid=81f102f7-223a-48f8-9222-325b5836e413
11-24 18:50:00.798 22283 22283 E SweeperPresenter.java: 4:sendCmd(cmd=21022, data={"cmd":"auto", "cleanType":"total"}, taskidStr=bec73e32-fdbc-45e5-8e0e-20b1dcdca041, timeout=30)
11-24 18:50:01.501 22283 22283 E qihoo.smarthome: 17:sendCmd complete, taskid=bec73e32-fdbc-45e5-8e0e-20b1dcdca041
```

This is like the holy grail...

## Custom voice packs

For instructions on how to create and upload your custom voice packs, please refer to
the [Voice Packs](./custom_voice/README.md) README.

## Voice packs

```
$ logcat | grep -e sendCmd -e onVoicePacket
11-24 19:00:42.195 22283 22283 E SweeperPresenter.java: 4:sendCmd(cmd=21027, data={"cmd":"getVoicePackageInfo"}, taskidStr=611e6fd4-0fd2-452f-9a06-25e938d9752a, timeout=30)
11-24 19:00:43.465 22283 22283 E VoicePacketFragment.java: 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='null', cmd='getVoicePackageInfo', curVoicePackage='ywb', voicePackageList=[PacketInfo{id='mrb', state='saved'}, PacketInfo{id='ywb', state='saved'}, PacketInfo{id='ruvoiceen', state='saved'}], error=0, process=0})
11-24 19:00:43.647 22283 22283 E qihoo.smarthome: 17:sendCmd complete, taskid=611e6fd4-0fd2-452f-9a06-25e938d9752a
11-24 19:00:44.328 22283 22283 E VoicePacketFragment.java: 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='null', cmd='getVoicePackageInfo', curVoicePackage='ywb', voicePackageList=[PacketInfo{id='mrb', state='saved'}, PacketInfo{id='ywb', state='saved'}, PacketInfo{id='ruvoiceen', state='saved'}], error=0, process=0})
11-24 19:00:51.640 22283 22283 E SweeperPresenter.java: 4:sendCmd(cmd=21027, data={"cmd":"downloadAndApply", "id":"ruvoiceen", "downUrl":"http://static.360.cn/wsm/attach/get?pjt=qcms&key=ca8d2e942e30f79221f8a956eebfa580&raw_file_name=1&file_name=ru_ru_20221012.tar.gz", "md5sum":"ca8d2e942e30f79221f8a956eebfa580", "size":8900823, "lang":"en_us" }, taskidStr=be26dfeb-016a-4dfc-b05c-b36a8f118cc3, timeout=30)
11-24 19:00:52.606 22283 22283 E qihoo.smarthome: 17:sendCmd complete, taskid=be26dfeb-016a-4dfc-b05c-b36a8f118cc3
11-24 19:00:53.205 22283 22283 E VoicePacketFragment.java: 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ruvoiceen', cmd='downloadAndApply', curVoicePackage='null', voicePackageList=null, error=0, process=0})
11-24 19:00:54.213 22283 22283 E VoicePacketFragment.java: 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ruvoiceen', cmd='process', curVoicePackage='null', voicePackageList=null, error=0, process=0})
11-24 19:00:56.413 22283 22283 E VoicePacketFragment.java: 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ruvoiceen', cmd='process', curVoicePackage='null', voicePackageList=null, error=0, process=0})
11-24 19:00:58.508 22283 22283 E VoicePacketFragment.java: 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ruvoiceen', cmd='process', curVoicePackage='null', voicePackageList=null, error=0, process=20})
11-24 19:01:00.162 22283 22283 E VoicePacketFragment.java: 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ruvoiceen', cmd='process', curVoicePackage='null', voicePackageList=null, error=0, process=50})
11-24 19:01:02.300 22283 22283 E VoicePacketFragment.java: 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ruvoiceen', cmd='process', curVoicePackage='null', voicePackageList=null, error=0, process=74})
11-24 19:01:05.902 22283 22283 E VoicePacketFragment.java: 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ruvoiceen', cmd='apply', curVoicePackage='null', voicePackageList=null, error=0, process=0})
11-24 19:01:14.279 22283 22283 E SweeperPresenter.java: 4:sendCmd(cmd=21027, data={"cmd":"downloadAndApply", "id":"ywb", "downUrl":"http://static.360.cn/wsm/attach/get?pjt=qcms&key=b567fc4557ad1a2975ab3d16622de38c&raw_file_name=1&file_name=en_us_20221012.tar.gz", "md5sum":"b567fc4557ad1a2975ab3d16622de38c", "size":8485734, "lang":"en_us" }, taskidStr=4d0a26b5-93f3-463c-ab56-34f0b63f1065, timeout=30)
11-24 19:01:15.233 22283 22283 E qihoo.smarthome: 17:sendCmd complete, taskid=4d0a26b5-93f3-463c-ab56-34f0b63f1065
11-24 19:01:16.162 22283 22283 E VoicePacketFragment.java: 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ywb', cmd='downloadAndApply', curVoicePackage='null', voicePackageList=null, error=0, process=0})
11-24 19:01:18.417 22283 22283 E VoicePacketFragment.java: 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ywb', cmd='process', curVoicePackage='null', voicePackageList=null, error=0, process=0})
11-24 19:01:20.774 22283 22283 E VoicePacketFragment.java: 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ywb', cmd='process', curVoicePackage='null', voicePackageList=null, error=0, process=2})
11-24 19:01:22.906 22283 22283 E VoicePacketFragment.java: 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ywb', cmd='process', curVoicePackage='null', voicePackageList=null, error=0, process=28})
11-24 19:01:24.459 22283 22283 E VoicePacketFragment.java: 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ywb', cmd='process', curVoicePackage='null', voicePackageList=null, error=0, process=50})
11-24 19:01:26.440 22283 22283 E VoicePacketFragment.java: 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ywb', cmd='process', curVoicePackage='null', voicePackageList=null, error=0, process=70})
11-24 19:01:28.685 22283 22283 E VoicePacketFragment.java: 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ywb', cmd='process', curVoicePackage='null', voicePackageList=null, error=0, process=94})
11-24 19:01:31.291 22283 22283 E VoicePacketFragment.java: 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ywb', cmd='apply', curVoicePackage='null', voicePackageList=null, error=0, process=0})

$ cat /storage/emulated/0/Android/data/com.qihoo.smarthome/log/sweeper_logs_xxx/sweeper_log_xxx.txt | grep -e VoicePacketFragment.java
$ # OR
$ tail -f /storage/emulated/0/Android/data/com.qihoo.smarthome/log/sweeper_logs_xxx/sweeper_log_xxx.txt | grep -e VoicePacketFragment.java
# Opening the UI
2025-11-24 19:52:07.904 LogCat e VoicePacketFragment.java 1:initData()
2025-11-24 19:52:07.904 LogCat e VoicePacketFragment.java 1:getVoicePacketList()
2025-11-24 19:52:09.520 LogCat e VoicePacketFragment.java 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='null', cmd='getVoicePackageInfo', curVoicePackage='ywb', voicePackageList=[PacketInfo{id='mrb', state='saved'}, PacketInfo{id='ywb', state='saved'}, PacketInfo{id='ruvoiceen', state='saved'}], error=0, process=0})
2025-11-24 19:52:09.522 LogCat e VoicePacketFragment.java 1:apply(voicePackets=[VoicePacket{id='Voicehr', title='Croatia', subTitle='Hrvatska', mp3Url='https://shanghai.xstore.qihu.com/qilin-iot-master-clean-public-data-pub1-shbt/Q003_hr_hr.mp3', coverUrl='https://p1.ssl.qhimg.com/t01734e6217802f8f3f.png', packetUrl='http://static.360.cn/wsm/attach/get?pjt=qcms&key=9d699197bc47a66c3c3856d7f6689f8f&raw_file_name=1&file_name=hr_hr_20221012.tar.gz', packetMd5='9d699197bc47a66c3c3856d7f6689f8f', packetSize=7592952, lang='en_us'}, VoicePacket{id='esvoiceen', title='Spanish', subTitle='Español', mp3Url='https://shanghai.xstore.qihu.com/qilin-iot-master-clean-public-data-pub1-shbt/Q003_en_us_span.mp3', coverUrl='https://p.ssl.qhimg.com/t01129f295e66fa7331.png', packetUrl='http://static.360.cn/wsm/attach/get?pjt=qcms&key=c1e95033753e8ccb6f55ded956a31696&raw_file_name=1&file_name=es_es_20221012.tar.gz', packetMd5='c1e95033753e8ccb6f55ded956a31696', packetSize=9281067, lang='en_us'}, VoicePacket{id='4en', title='Korean', subTitle='한국인', mp3Url='https://shanghai.xstore.qihu.com/qilin-iot-master-clean-public-data-pub1-shbt/Q006_en_us_Korean.mp3', coverUrl='https://p.ssl.qhimg.com/t01067361039beff2f0.png', packetUrl='http://static.360.cn/wsm/attach/get?pjt=qcms&key=4fe9ffa260af88a083c97bf515b5b680&raw_file_name=1&file_name=ko_kr_20221012.tar.gz', packetMd5='4fe9ffa260af88a083c97bf515b5b680', packetSize=7680203, lang='en_us'}, VoicePacket{id='trvoicede', title='Turkish', subTitle='Türk', mp3Url='https://shanghai.xstore.qihu.com/qilin-iot-master-clean-public-data-pub1-shbt/Q0005_en_us_turk.mp3', coverUrl='https://p.ssl.qhimg.com/t0110744819ed84dc23.png', packetUrl='http://static.360.cn/wsm/attach/get?pjt=qcms&key=77d563e62d6cc45bb1e353e125795715&raw_file_name=1&file_name=tr_tr_20240729.tar.gz', packetMd5='77d563e62d6cc45bb1e353e125795715', packetSize=6647253, lang='en_us'}, VoicePacket{id='itvoiceen', title='Italian', subTitle='Italiano', mp3Url='https://shanghai.xstore.qihu.com/qilin-iot-master-clean-public-data-pub1-shbt/Q003_en_us_Italian.mp3', coverUrl='https://p.ssl.qhimg.com/t0179d686f27b4856d9.png', packetUrl='http://static.360.cn/wsm/attach/get?pjt=qcms&key=39fc684dfa293d76d32b9c257a39487e&raw_file_name=1&file_name=it_it_20221216.tar.gz', packetMd5='39fc684dfa293d76d32b9c257a39487e', packetSize=7466716, lang='en_us'}, VoicePacket{id='frvoiceen', title='French', subTitle='Français', mp3Url='https://shanghai.xstore.qihu.com/qilin-iot-master-clean-public-data-pub1-shbt/Q003_en_us_French.mp3', coverUrl='https://p.ssl.qhimg.com/t017851bc9fc328dd41.png', packetUrl='http://static.360.cn/wsm/attach/get?pjt=qcms&key=a0a0344dba18c58246f8fadba0f08591&raw_file_name=1&file_name=fr_fr_20221012.tar.gz', packetMd5='a0a0344dba18c58246f8fadba0f08591', packetSize=9948249, lang='en_us'}, VoicePacket{id='devoiceen', title='German', subTitle='Deutsch', mp3Url='https://shanghai.xstore.qihu.com/qilin-iot-master-clean-public-data-pub1-shbt/Q005_en_us_greman.mp3', coverUrl='https://p.ssl.qhimg.com/t01a972fc727608917b.png', packetUrl='http://static.360.cn/wsm/attach/get?pjt=qcms&key=b464a37b5c2d97dd3ff10cab24d20ed9&raw_file_name=1&file_name=de_de_20221012.tar.gz', packetMd5='b464a37b5c2d97dd3ff10cab24d20ed9', packetSize=10107985, lang='en_us'}, VoicePacket{id='jpvoiceen', title='Japanese', subTitle='日本語', mp3Url='https://shanghai.xstore.qihu.com/qilin-iot-master-clean-public-data-pub1-shbt/Q003_en_us_japanese.mp3', coverUrl='https://p.ssl.qhimg.com/t0164f8c26a433a2c55.png', packetUrl='http://static.360.cn/wsm/attach/get?pjt=qcms&key=81e10d456a80f79e4cb8fc7b6cd6cc5c&raw_file_name=1&file_name=ja_jp_20221012.tar.gz', packetMd5='81e10d456a80f79e4cb8fc7b6cd6cc5c', packetSize=9649168, lang='en_us'}, VoicePacket{id='ruvoiceen', title='Russian', subTitle='Pусский', mp3Url='https://shanghai.xstore.qihu.com/qilin-iot-master-clean-public-data-pub1-shbt/Q003_en_us_Russian.mp3', coverUrl='https://p.ssl.qhimg.com/t013178a42ec61b8b32.png', packetUrl='http://static.360.cn/wsm/attach/get?pjt=qcms&key=ca8d2e942e30f79221f8a956eebfa580&raw_file_name=1&file_name=ru_ru_20221012.tar.gz', packetMd5='ca8d2e942e30f79221f8a956eebfa580', packetSize=8900823, lang='en_us'}, VoicePacket{id='chinese', title='Chinese', subTitle='中文', mp3Url='https://shanghai.xstore.qihu.com/qilin-iot-master-clean-public-data-pub1-shbt/Q003_en_us_chinese.mp3', coverUrl='https://p.ssl.qhimg.com/t01f62c63a0bd615a37.png', packetUrl='http://static.360.cn/wsm/attach/get?pjt=qcms&key=c3a95bc83c955a3507f44995aacaa03f&raw_file_name=1&file_name=mengyin_20221012.tar.gz', packetMd5='c3a95bc83c955a3507f44995aacaa03f', packetSize=8917062, lang='en_us'}, VoicePacket{id='ywb', title='English', subTitle='English', mp3Url='https://shanghai.xstore.qihu.com/qilin-iot-master-clean-public-data-pub1-shbt/Q003_en_us_English.mp3', coverUrl='https://p.ssl.qhimg.com/t019770dfc0cc404aab.png', packetUrl='http://static.360.cn/wsm/attach/get?pjt=qcms&key=b567fc4557ad1a2975ab3d16622de38c&raw_file_name=1&file_name=en_us_20221012.tar.gz', packetMd5='b567fc4557ad1a2975ab3d16622de38c', packetSize=8485734, lang='en_us'}], voicePacketResponse=VoicePacketResponse{id='null', cmd='getVoicePackageInfo', curVoicePackage='ywb', voicePackageList=[PacketInfo{id='mrb', state='saved'}, PacketInfo{id='ywb', state='saved'}, PacketInfo{id='ruvoiceen', state='saved'}], error=0, process=0})
2025-11-24 19:52:09.524 LogCat e VoicePacketFragment.java 1:mVoicePacketAdapter.getItemCount()=11
2025-11-24 19:52:09.792 LogCat e VoicePacketFragment.java 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='null', cmd='getVoicePackageInfo', curVoicePackage='ywb', voicePackageList=[PacketInfo{id='mrb', state='saved'}, PacketInfo{id='ywb', state='saved'}, PacketInfo{id='ruvoiceen', state='saved'}], error=0, process=0})
# "Use" Russian
2025-11-24 20:05:13.868 LogCat e VoicePacketFragment.java 3:已经给扫地机发送应用或下载语音包的指令,等待扫地机回复
2025-11-24 20:05:14.805 LogCat e VoicePacketFragment.java 3:收到扫地机回复的应用或下载语音包的指令
2025-11-24 20:05:14.806 LogCat e VoicePacketFragment.java 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ruvoiceen', cmd='downloadAndApply', curVoicePackage='null', voicePackageList=null, error=0, process=0})
2025-11-24 20:05:14.807 LogCat e VoicePacketFragment.java 1:startTimeoutTimer(id=ruvoiceen, state=saved)
2025-11-24 20:05:14.808 LogCat e VoicePacketFragment.java 1:cancelTimeoutTimer()
2025-11-24 20:05:14.943 LogCat e VoicePacketFragment.java 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ruvoiceen', cmd='process', curVoicePackage='null', voicePackageList=null, error=0, process=0})
2025-11-24 20:05:15.888 LogCat e VoicePacketFragment.java 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ruvoiceen', cmd='process', curVoicePackage='null', voicePackageList=null, error=0, process=0})
2025-11-24 20:05:17.867 LogCat e VoicePacketFragment.java 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ruvoiceen', cmd='process', curVoicePackage='null', voicePackageList=null, error=0, process=0})
2025-11-24 20:05:19.640 LogCat e VoicePacketFragment.java 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ruvoiceen', cmd='process', curVoicePackage='null', voicePackageList=null, error=0, process=7})
2025-11-24 20:05:20.562 LogCat e VoicePacketFragment.java 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ruvoiceen', cmd='process', curVoicePackage='null', voicePackageList=null, error=0, process=27})
2025-11-24 20:05:22.561 LogCat e VoicePacketFragment.java 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ruvoiceen', cmd='process', curVoicePackage='null', voicePackageList=null, error=0, process=37})
2025-11-24 20:05:23.300 LogCat e VoicePacketFragment.java 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ruvoiceen', cmd='process', curVoicePackage='null', voicePackageList=null, error=0, process=55})
2025-11-24 20:05:24.639 LogCat e VoicePacketFragment.java 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ruvoiceen', cmd='process', curVoicePackage='null', voicePackageList=null, error=0, process=64})
2025-11-24 20:05:25.731 LogCat e VoicePacketFragment.java 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ruvoiceen', cmd='process', curVoicePackage='null', voicePackageList=null, error=0, process=78})
2025-11-24 20:05:26.790 LogCat e VoicePacketFragment.java 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ruvoiceen', cmd='process', curVoicePackage='null', voicePackageList=null, error=0, process=84})
2025-11-24 20:05:28.067 LogCat e VoicePacketFragment.java 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ruvoiceen', cmd='process', curVoicePackage='null', voicePackageList=null, error=0, process=97})
2025-11-24 20:05:30.929 LogCat e VoicePacketFragment.java 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ruvoiceen', cmd='apply', curVoicePackage='null', voicePackageList=null, error=0, process=0})

# Custom language application
2025-11-28 19:20:34.632 LogCat e VoicePacketFragment.java 1:mVoicePacketAdapter.getItemCount()=11
2025-11-28 19:20:42.081 LogCat e VoicePacketFragment.java 1:onSendCmdTimeout(cmd=21034, taskid=1ae31269-4e70-4803-98b4-481674337046)
2025-11-28 19:20:43.582 LogCat e VoicePacketFragment.java 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ywb', cmd='delete', curVoicePackage='null', voicePackageList=null, error=0, process=0})
2025-11-28 19:20:57.902 LogCat e VoicePacketFragment.java 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ywb', cmd='downloadAndApply', curVoicePackage='null', voicePackageList=null, error=0, process=0})
2025-11-28 19:20:57.903 LogCat e VoicePacketFragment.java 1:startTimeoutTimer(id=ywb, state=null)
2025-11-28 19:20:57.903 LogCat e VoicePacketFragment.java 1:cancelTimeoutTimer()
2025-11-28 19:20:58.721 LogCat e VoicePacketFragment.java 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ywb', cmd='process', curVoicePackage='null', voicePackageList=null, error=0, process=0})
2025-11-28 19:21:00.522 LogCat e VoicePacketFragment.java 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ywb', cmd='process', curVoicePackage='null', voicePackageList=null, error=0, process=15})
2025-11-28 19:21:01.567 LogCat e VoicePacketFragment.java 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ywb', cmd='process', curVoicePackage='null', voicePackageList=null, error=0, process=87})
2025-11-28 19:21:03.803 LogCat e VoicePacketFragment.java 1:onVoicePacket(voicePacketResponse=VoicePacketResponse{id='ywb', cmd='apply', curVoicePackage='null', voicePackageList=null, error=0, process=0})
```

## TODO

### Commands

Forum: https://www.roboter-forum.com/threads/api-heimautomatisierung.39645/post-525902

```
"StartCleaningCommand":"countryId=AT&data={"mode":"smartClean"}&devType=3&from=mpc_ios&infoType=21005&lang=en_US&mcc=232&sn=SERIAL NUMBER&taskid=A8CB912C-EB32-4AB6-A9A3-2E578113FCD6"
"StopCleaningCommand":"countryId=AT&data={"cmd":"pause"}&devType=3&from=mpc_ios&infoType=21017&lang=en_US&mcc=232&sn=SERIAL NUMBER&taskid=0CA64629-FA5E-4E65-906F-FAC5F93F712E"
"ChargeCleaningCommand":"countryId=AT&data={"cmd":"start"}&devType=3&from=mpc_ios&infoType=21012&lang=en_US&mcc=232&sn=SERIENNUMMER&taskid=FFB0C258-3D7E-45DC-935C-DCB658EAB3E4"


The map or other content apparently comes from Amazon S3. I thought it was stored directly on the device. This request is executed when the map is changed using Select Map.
--> "cmd":"changeMap","downUrl":"https:\/\/q.smart.360.cn\/s3_file_new\/iot-master-clean-online-pub1- bjyt\/XXX?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=

https:\\q.smart.360.cn\s3_file_new\iot-master-clean-online-pub1-bjyt\...
?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Content-Sha256=UNSIGNED-PAYLOAD&X-Amz-Credential=...


"StartCleaningCommand":"countryId=DE&data=%7B%22mode%22%3A%22smartClean%22%7D&devType=3&from=mpc_i os&infoType=21005&lang=en_US&mcc=232&sn=131131313213&taskid=A8CB912C-EB32-4AB6-A9A3-2E578113FCD6",
"StopCleaningCommand":"countryId=DE&data=%7B%22cmd%22%3A%22pause%22%7D&devType=3&from=mpc_ios& infoType=21017&lang=en_US&mcc=232&sn=131131313213&taskid=0CA64629-FA5E-4E65-906F-FAC5F93F712E",
"ChargeCleaningCommand":"countryId=DE&data=%7B%22cmd%22%3A%22start%22%7D&devType=3&from=mpc_ios &infoType=21012&lang=en_US&mcc=232&sn=131131313213&taskid=FFB0C258-3D7E-45DC-935C-DCB658EAB3E4"}
```
