package va;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import p195k7.C4546c;
import p367we.C6443a;
import p376x8.C6468a;
import pa.C5848a;
import retrofit2.C6067k;
import sa.C6091a;
import sa.C6092b;
import ve.C6310g;

/* compiled from: SweeperService.java */
/* renamed from: va.d */
/* loaded from: classes2.dex */
public class C6293d {

    /* renamed from: a */
    private static InterfaceC6292c f23691a;

    /* renamed from: a */
    public static <T> T m24352a(Class<T> cls) {
        C4546c.m18758d("create(service=" + cls + ")");
        C5848a.a aVar = new C5848a.a();
        OkHttpClient.Builder sslSocketFactory = new OkHttpClient.Builder().sslSocketFactory(C5848a.m22864b(aVar), aVar);
        TimeUnit timeUnit = TimeUnit.SECONDS;
        OkHttpClient.Builder dns = sslSocketFactory.connectTimeout(30L, timeUnit).readTimeout(30L, timeUnit).retryOnConnectionFailure(true).addInterceptor(C6091a.m23562a()).addInterceptor(C6092b.m23565a()).dns(C6468a.m24928b());
        if (C6468a.m24927a() != null) {
            dns.cookieJar(C6468a.m24927a());
        }
        if (C6468a.m24929c()) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            dns.addInterceptor(httpLoggingInterceptor).addNetworkInterceptor(httpLoggingInterceptor);
        }
        return (T) new C6067k.b().m23442g(dns.build()).m23436a(C6310g.m24396d()).m23437b(C6443a.m24886d()).m23438c("https://q.smart.360.cn/").m23440e().m23428d(cls);
    }

    /* renamed from: b */
    public static InterfaceC6292c m24353b() {
        if (f23691a == null) {
            f23691a = (InterfaceC6292c) m24352a(InterfaceC6292c.class);
        }
        return f23691a;
    }
}
