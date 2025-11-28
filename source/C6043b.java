package p298ra;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import p376x8.C6468a;
import retrofit2.C6067k;
import ve.C6310g;

/* compiled from: DownloadService.java */
/* renamed from: ra.b */
/* loaded from: classes2.dex */
public class C6043b {

    /* renamed from: a */
    public static String f22940a = "https://smart.360.cn/";

    /* renamed from: a */
    public static <T> T m23322a(Class<T> cls) {
        OkHttpClient.Builder dns = new OkHttpClient.Builder().readTimeout(30L, TimeUnit.SECONDS).dns(C6468a.m24928b());
        if (C6468a.m24929c()) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
            dns.addInterceptor(httpLoggingInterceptor).addNetworkInterceptor(httpLoggingInterceptor);
        }
        return (T) new C6067k.b().m23442g(dns.build()).m23436a(C6310g.m24396d()).m23438c(f22940a).m23440e().m23428d(cls);
    }
}
