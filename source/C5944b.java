package qa;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import p195k7.C4546c;
import p367we.C6443a;
import p376x8.C6468a;
import pa.C5848a;
import retrofit2.C6067k;
import ve.C6310g;

/* compiled from: ConfigService.java */
/* renamed from: qa.b */
/* loaded from: classes2.dex */
public class C5944b {

    /* renamed from: a */
    public static String f22693a = "https://smart.360.cn/";

    /* renamed from: b */
    private static InterfaceC5943a f22694b;

    /* renamed from: a */
    public static <T> T m23068a(Class<T> cls) {
        C5848a.a aVar = new C5848a.a();
        OkHttpClient.Builder dns = new OkHttpClient.Builder().sslSocketFactory(C5848a.m22864b(aVar), aVar).readTimeout(30L, TimeUnit.SECONDS).dns(C6468a.m24928b());
        if (C6468a.m24929c()) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            dns.addInterceptor(httpLoggingInterceptor).addNetworkInterceptor(httpLoggingInterceptor);
        }
        return (T) new C6067k.b().m23442g(dns.build()).m23436a(C6310g.m24396d()).m23437b(C6443a.m24886d()).m23438c(f22693a).m23440e().m23428d(cls);
    }

    /* renamed from: b */
    public static InterfaceC5943a m23069b() {
        C4546c.m18758d("sConfigApi=" + f22694b);
        if (f22694b == null) {
            f22694b = (InterfaceC5943a) m23068a(InterfaceC5943a.class);
        }
        return f22694b;
    }
}
