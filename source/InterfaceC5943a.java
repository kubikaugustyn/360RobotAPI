package qa;

import com.google.gson.JsonObject;
import com.qihoo.smarthome.sweeper.entity.CommonConfig;
import com.qihoo.smarthome.sweeper.entity.ConsumableInfo;
import com.qihoo.smarthome.sweeper.entity.DevImgInfo;
import com.qihoo.smarthome.sweeper.entity.DevTypeInfo;
import com.qihoo.smarthome.sweeper.entity.GudieInfo;
import com.qihoo.smarthome.sweeper.net.entity.CommonConfigInfo;
import java.util.List;
import java.util.Map;
import p382xe.InterfaceC6502f;
import p382xe.InterfaceC6515s;
import td.AbstractC6158e;
import td.AbstractC6167n;

/* compiled from: ConfigApi.java */
/* renamed from: qa.a */
/* loaded from: classes2.dex */
// Prefix: https://smart.360.cn/
public interface InterfaceC5943a {
    @InterfaceC6502f("/clean/guide.json") // @GET("...")
    /* renamed from: a */
    AbstractC6158e<List<GudieInfo>> m23059a();

    @InterfaceC6502f("/clean/consumable{lang}.json")  // @GET("...")
    /* renamed from: b */
    AbstractC6158e<Map<String, Map<String, ConsumableInfo>>> m23060b(@InterfaceC6515s("lang") String str);

    @InterfaceC6502f("/clean/modelalias.json")  // @GET("...")
    /* renamed from: c */
    AbstractC6158e<Map<String, Map<String, String>>> m23061c();

    @InterfaceC6502f("/clean/errorInfo{lang}.json")  // @GET("...")
    /* renamed from: d */
    AbstractC6167n<JsonObject> m23062d(@InterfaceC6515s("lang") String str);

    @InterfaceC6502f("/clean_dev/list{lang}.json")  // @GET("...")
    /* renamed from: e */
    AbstractC6158e<List<DevTypeInfo>> m23063e(@InterfaceC6515s("lang") String str);

    @InterfaceC6502f("/clean/consume/consume_{postfix}.json")  // @GET("...")
    /* renamed from: f */
    AbstractC6158e<Map<String, Map<String, ConsumableInfo>>> m23064f(@InterfaceC6515s("postfix") String str);

    @InterfaceC6502f("/app/and_config{lang}.json")  // @GET("...")
    /* renamed from: g */
    AbstractC6158e<CommonConfig> m23065g(@InterfaceC6515s("lang") String str);

    @InterfaceC6502f("/app/common_config.json")  // @GET("...")
    /* renamed from: h */
    AbstractC6158e<CommonConfigInfo> m23066h();

    @InterfaceC6502f("/common/dev_img.json")  // @GET("...")
    /* renamed from: i */
    AbstractC6158e<Map<String, DevImgInfo>> m23067i();
}
