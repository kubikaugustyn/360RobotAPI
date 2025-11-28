package p323ta;

import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import p195k7.C4546c;
import p367we.C6443a;
import p376x8.C6468a;
import retrofit2.C6067k;
import ve.C6310g;

/* compiled from: LogService.java */
/* renamed from: ta.b */
/* loaded from: classes2.dex */
public class C6142b {

    /* renamed from: a */
    public static String f23331a = "https://log.jia.360.cn/";

    /* renamed from: b */
    private static InterfaceC6141a f23332b;

    /* renamed from: a */
    public static <T> T m23735a(Class<T> cls) {
        OkHttpClient.Builder readTimeout = new OkHttpClient.Builder().readTimeout(30L, TimeUnit.SECONDS);
        if (C6468a.m24929c()) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            readTimeout.addInterceptor(httpLoggingInterceptor).addNetworkInterceptor(httpLoggingInterceptor);
        }
        return (T) new C6067k.b().m23442g(readTimeout.build()).m23436a(C6310g.m24396d()).m23437b(C6443a.m24886d()).m23438c(f23331a).m23440e().m23428d(cls);
    }

    /* renamed from: b */
    public static InterfaceC6141a m23736b() {
        C4546c.m18758d("sLogApi=" + f23332b);
        if (f23332b == null) {
            f23332b = (InterfaceC6141a) m23735a(InterfaceC6141a.class);
        }
        return f23332b;
    }
}
