package va;

import com.qihoo.smarthome.sweeper.entity.AdMainInfoList;
import com.qihoo.smarthome.sweeper.entity.BackupMapList;
import com.qihoo.smarthome.sweeper.entity.ConsumableMaterial;
import com.qihoo.smarthome.sweeper.entity.InviteCode;
import com.qihoo.smarthome.sweeper.entity.InviteInfoList;
import com.qihoo.smarthome.sweeper.entity.LoginResponseBody;
import com.qihoo.smarthome.sweeper.entity.MaterialStatusMap;
import com.qihoo.smarthome.sweeper.entity.ReceivedDeviceData;
import com.qihoo.smarthome.sweeper.entity.RecentlyCleanListInfo;
import com.qihoo.smarthome.sweeper.entity.ShareInfo;
import com.qihoo.smarthome.sweeper.entity.ShareNotice;
import com.qihoo.smarthome.sweeper.entity.SweepRecord;
import com.qihoo.smarthome.sweeper.entity.SweepRecordList;
import com.qihoo.smarthome.sweeper.entity.SweepRecordStatisticInfo;
import com.qihoo.smarthome.sweeper.entity.SweepStrategyListData;
import com.qihoo.smarthome.sweeper.entity.SweeperCleanInfo;
import com.qihoo.smarthome.sweeper.entity.VoicePacketList;
import com.qihoo.smarthome.sweeper.net.entity.BindInfo;
import com.qihoo.smarthome.sweeper.net.entity.DeviceList;
import com.qihoo.smarthome.sweeper.net.entity.ErrorInfo;
import com.qihoo.smarthome.sweeper.net.entity.Head;
import p382xe.InterfaceC6499c;
import p382xe.InterfaceC6501e;
import p382xe.InterfaceC6511o;
import td.AbstractC6158e;
import td.AbstractC6167n;

/* compiled from: SweeperApi.java */
/* renamed from: va.c */
/* loaded from: classes2.dex */
public interface InterfaceC6292c {
    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("/clean/bind/bind") // @POST("...")
    /* renamed from: A */
    AbstractC6167n<Head<BindInfo>> m24314A(@InterfaceC6499c("ts") String str, @InterfaceC6499c("qid") String str2);

    @InterfaceC6511o("clean/devuser/getDevList") // @POST("...")
    /* renamed from: B */
    AbstractC6167n<Head<DeviceList>> m24315B();

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("/clean/bind/bind") // @POST("...")
    /* renamed from: C */
    AbstractC6158e<Head<BindInfo>> m24316C(@InterfaceC6499c("ts") String str, @InterfaceC6499c("qid") String str2);

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("clean/ad/applist") // @POST("...")
    /* renamed from: D */
    AbstractC6158e<Head<AdMainInfoList>> m24317D(@InterfaceC6499c("dummy") String str);

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("clean/record/delMapByIds") // @POST("...")
    /* renamed from: E */
    AbstractC6158e<ErrorInfo> m24318E(@InterfaceC6499c("sn") String str, @InterfaceC6499c("ids") String str2);

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("clean/record/setAreaAndCleaning") // @POST("...")
    /* renamed from: F */
    AbstractC6158e<ErrorInfo> m24319F(@InterfaceC6499c("sn") String str, @InterfaceC6499c("cleanId") String str2, @InterfaceC6499c("areaSetting") String str3, @InterfaceC6499c("taskid") String str4);

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("clean/record/collect") // @POST("...")
    /* renamed from: G */
    AbstractC6158e<ErrorInfo> m24320G(@InterfaceC6499c("sn") String str, @InterfaceC6499c("cleanId") String str2);

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("common/share/inviteByCode") // @POST("...")
    /* renamed from: H */
    AbstractC6167n<Head<InviteCode>> m24321H(@InterfaceC6499c("sn") String str);

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("/common/user/login") // @POST("...")
    /* renamed from: I */
    AbstractC6158e<Head<LoginResponseBody>> m24322I(@InterfaceC6499c("form") String str, @InterfaceC6499c("clientInfo") String str2, @InterfaceC6499c("phoneNum") String str3);

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("/clean/activity/shareNotice") // @POST("...")
    /* renamed from: J */
    AbstractC6158e<Head<ShareNotice>> m24323J(@InterfaceC6499c("model") String str);

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("common/share/getInviteList") // @POST("...")
    /* renamed from: K */
    AbstractC6167n<Head<InviteInfoList>> m24324K(@InterfaceC6499c("sn") String str);

    @InterfaceC6511o("/clean/dev/getMaterialStatus") // @POST("...")
    /* renamed from: L */
    AbstractC6158e<Head<MaterialStatusMap>> m24325L();

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("clean/record/getOne") // @POST("...")
    /* renamed from: a */
    AbstractC6167n<Head<SweepRecord>> m24326a(@InterfaceC6499c("sn") String str, @InterfaceC6499c("cleanId") String str2);

    @InterfaceC6511o("/common/dev/GetList") // @POST("...")
    /* renamed from: b */
    AbstractC6158e<Head<DeviceList>> m24327b();

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("/clean/dev/getC60Material") // @POST("...")
    /* renamed from: c */
    AbstractC6158e<Head<ConsumableMaterial>> m24328c(@InterfaceC6499c("sn") String str);

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("/clean/record/recentlycleanlist") // @POST("...")
    /* renamed from: d */
    AbstractC6158e<Head<RecentlyCleanListInfo>> m24329d(@InterfaceC6499c("sn") String str, @InterfaceC6499c("timezone") String str2);

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("common/share/acceptByCode") // @POST("...")
    /* renamed from: e */
    AbstractC6167n<Head<ReceivedDeviceData>> m24330e(@InterfaceC6499c("inviteCode") String str);

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("clean/record/updateMapName") // @POST("...")
    /* renamed from: f */
    AbstractC6158e<ErrorInfo> m24331f(@InterfaceC6499c("sn") String str, @InterfaceC6499c("cleanId") String str2, @InterfaceC6499c("mapName") String str3);

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("/clean/setting/getOne") // @POST("...")
    /* renamed from: g */
    AbstractC6158e<Head<SweepStrategyListData>> m24332g(@InterfaceC6499c("sn") String str, @InterfaceC6499c("key") String str2);

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("clean/record/setAreaSetting") // @POST("...")
    /* renamed from: h */
    AbstractC6158e<ErrorInfo> m24333h(@InterfaceC6499c("sn") String str, @InterfaceC6499c("cleanId") String str2, @InterfaceC6499c("mapId") long j, @InterfaceC6499c("cmd") String str3, @InterfaceC6499c("areaSetting") String str4, @InterfaceC6499c("taskid") String str5);

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("clean/record/delByIds") // @POST("...")
    /* renamed from: i */
    AbstractC6158e<ErrorInfo> m24334i(@InterfaceC6499c("sn") String str, @InterfaceC6499c("ids") String str2);

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("/clean/voice/getList") // @POST("...")
    /* renamed from: j */
    AbstractC6158e<Head<VoicePacketList>> m24335j(@InterfaceC6499c("sn") String str);

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("clean/record/getList") // @POST("...")
    /* renamed from: k */
    AbstractC6167n<Head<SweepRecordList>> m24336k(@InterfaceC6499c("sn") String str, @InterfaceC6499c("lastId") String str2, @InterfaceC6499c("pageSize") int i10);

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("clean/record/areaRank") // @POST("...")
    /* renamed from: l */
    AbstractC6158e<Head<ShareInfo>> m24337l(@InterfaceC6499c("sn") String str, @InterfaceC6499c("area") int i10);

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("/clean/dev/resetmaterial") // @POST("...")
    /* renamed from: m */
    AbstractC6158e<Head<ErrorInfo>> m24338m(@InterfaceC6499c("sn") String str, @InterfaceC6499c("material") String str2);

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("common/share/cancelAccept") // @POST("...")
    /* renamed from: n */
    AbstractC6167n<ErrorInfo> m24339n(@InterfaceC6499c("sn") String str);

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("clean/record/statis") // @POST("...")
    /* renamed from: o */
    AbstractC6158e<Head<SweepRecordStatisticInfo>> m24340o(@InterfaceC6499c("sn") String str);

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("/clean/record/allStatis") // @POST("...")
    /* renamed from: p */
    AbstractC6158e<Head<SweeperCleanInfo>> m24341p(@InterfaceC6499c("qid ") String str, @InterfaceC6499c("sid") String str2);

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("clean/cmd/send") // @POST("...")
    /* renamed from: q */
    AbstractC6167n<ErrorInfo> m24342q(@InterfaceC6499c("sn") String str, @InterfaceC6499c("infoType") String str2, @InterfaceC6499c("data") String str3);

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("clean/activity/share") // @POST("...")
    /* renamed from: r */
    AbstractC6158e<Head<ErrorInfo>> m24343r(@InterfaceC6499c("type") int i10);

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("clean/record/unCollect") // @POST("...")
    /* renamed from: s */
    AbstractC6158e<ErrorInfo> m24344s(@InterfaceC6499c("sn") String str, @InterfaceC6499c("cleanId") String str2);

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("clean/devuser/updateInfo") // @POST("...")
    /* renamed from: t */
    AbstractC6167n<ErrorInfo> m24345t(@InterfaceC6499c("sn") String str, @InterfaceC6499c("title") String str2);

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("clean/bind/unbind") // @POST("...")
    /* renamed from: u */
    AbstractC6167n<ErrorInfo> m24346u(@InterfaceC6499c("sn") String str);

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("common/share/acceptByAccount") // @POST("...")
    /* renamed from: v */
    AbstractC6167n<ErrorInfo> m24347v(@InterfaceC6499c("sn") String str);

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("clean/cmd/send") // @POST("...")
    /* renamed from: w */
    AbstractC6158e<ErrorInfo> m24348w(@InterfaceC6499c("sn") String str, @InterfaceC6499c("infoType") String str2, @InterfaceC6499c("data") String str3);

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("clean/cmd/send") // @POST("...")
    /* renamed from: x */
    AbstractC6167n<ErrorInfo> m24349x(@InterfaceC6499c("sn") String str, @InterfaceC6499c("infoType") String str2, @InterfaceC6499c("data") String str3, @InterfaceC6499c("taskid") String str4);

    @InterfaceC6511o("/common/user/cancel") // @POST("...")
    /* renamed from: y */
    AbstractC6158e<ErrorInfo> m24350y();

    @InterfaceC6501e // @FormUrlEncoded
    @InterfaceC6511o("clean/record/getBackupMapList") // @POST("...")
    /* renamed from: z */
    AbstractC6158e<Head<BackupMapList>> m24351z(@InterfaceC6499c("sn") String str, @InterfaceC6499c("lastId") String str2, @InterfaceC6499c("pageSize") int i10);
}
