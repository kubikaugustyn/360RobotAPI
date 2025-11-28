package ua;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import p367we.C6443a;
import p376x8.C6468a;
import pa.C5848a;
import retrofit2.C6067k;
import sa.C6092b;
import ve.C6310g;

/* compiled from: OTAService.java */
/* renamed from: ua.b */
/* loaded from: classes2.dex */
public class C6220b {
    /* renamed from: a */
    public static <T> T m24059a(Class<T> cls) {
        C5848a.a aVar = new C5848a.a();
        OkHttpClient.Builder dns = new OkHttpClient.Builder().sslSocketFactory(C5848a.m22864b(aVar), aVar).addInterceptor(C6092b.m23565a()).dns(C6468a.m24928b());
        if (C6468a.m24929c()) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            dns.addInterceptor(httpLoggingInterceptor).addNetworkInterceptor(httpLoggingInterceptor);
        }
        return (T) new C6067k.b().m23442g(dns.build()).m23436a(C6310g.m24396d()).m23437b(C6443a.m24886d()).m23438c("https://ota5.jia.360.cn/").m23440e().m23428d(cls);
    }
}
