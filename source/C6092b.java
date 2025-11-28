package sa;

import android.os.Build;
import android.text.TextUtils;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import p195k7.C4546c;

/* compiled from: CommonParamsInterceptor.java */
/* renamed from: sa.b */
/* loaded from: classes2.dex */
public class C6092b implements Interceptor {

    /* renamed from: d */
    private static C6092b f23173d;

    /* renamed from: a */
    private String f23174a;

    /* renamed from: b */
    private String f23175b;

    /* renamed from: c */
    private String f23176c;

    /* renamed from: a */
    public static C6092b m23565a() {
        if (f23173d == null) {
            f23173d = new C6092b();
        }
        return f23173d;
    }

    /* renamed from: b */
    private String m23566b() {
        Locale locale = Locale.getDefault();
        if (locale == null) {
            return "";
        }
        return locale.getLanguage() + "_" + locale.getCountry();
    }

    /* renamed from: c */
    public void m23567c(String str) {
        if (str == null) {
            this.f23174a = "";
        } else {
            this.f23174a = str;
        }
    }

    /* renamed from: d */
    public void m23568d(String str) {
        if (str == null) {
            this.f23175b = "";
        } else {
            this.f23175b = str;
        }
    }

    /* renamed from: e */
    public void m23569e(String str) {
        this.f23176c = str;
    }

    @Override // okhttp3.Interceptor
    public Response intercept(Interceptor.Chain chain) {
        Request request = chain.request();
        if (HttpPost.METHOD_NAME.equals(request.method())) {
            RequestBody body = request.body();
            if (body != null) {
                if (body instanceof FormBody) {
                    FormBody formBody = (FormBody) body;
                    FormBody.Builder builder = new FormBody.Builder();
                    HashMap hashMap = new HashMap(10);
                    for (int i10 = 0; i10 < formBody.size(); i10++) {
                        String name = formBody.name(i10);
                        String value = formBody.value(i10);
                        builder.add(name, value);
                        hashMap.put(name, value);
                    }
                    if (!hashMap.containsKey("taskid")) {
                        builder.add("taskid", UUID.randomUUID().toString());
                    }
                    if (!hashMap.containsKey("from")) {
                        builder.add("from", "mpc_and");
                    }
                    if (!hashMap.containsKey("devType")) {
                        builder.add("devType", "3");
                    }
                    if (!hashMap.containsKey("channel_id")) {
                        builder.add("channel_id", this.f23175b);
                    }
                    if (!hashMap.containsKey("appVer")) {
                        builder.add("appVer", this.f23174a);
                    }
                    if (!hashMap.containsKey("lang")) {
                        String m23566b = m23566b();
                        C4546c.m18758d("url = " + request.url() + ", lang=" + m23566b);
                        builder.add("lang", m23566b);
                    }
                    if (!hashMap.containsKey("mcc")) {
                        String str = this.f23176c;
                        if (!TextUtils.isEmpty(str)) {
                            builder.add("mcc", str);
                        }
                    }
                    if (!hashMap.containsKey("model")) {
                        builder.add("model", Build.MODEL);
                    }
                    if (!hashMap.containsKey("manufacturer")) {
                        builder.add("manufacturer", Build.MANUFACTURER);
                    }
                    C4546c.m18758d("url = " + request.url() + ", params=" + hashMap);
                    request = request.newBuilder().post(builder.build()).build();
                } else {
                    C4546c.m18758d("body is not FormBody");
                    FormBody.Builder builder2 = new FormBody.Builder();
                    builder2.add("taskid", UUID.randomUUID().toString());
                    builder2.add("from", "mpc_and");
                    builder2.add("devType", "3");
                    builder2.add("channel_id", this.f23175b);
                    builder2.add("appVer", this.f23174a);
                    builder2.add("lang", m23566b());
                    request = request.newBuilder().post(builder2.build()).build();
                }
            }
        } else if (HttpGet.METHOD_NAME.equals(request.method())) {
            request = request.newBuilder().url(request.url().newBuilder().addQueryParameter("from", "mpc_and").addQueryParameter("taskId", UUID.randomUUID().toString()).addQueryParameter("devType", "3").addQueryParameter("channel_id", this.f23175b).addQueryParameter("appVer", this.f23174a).addQueryParameter("lang", m23566b()).build()).build();
        }
        return chain.proceed(request);
    }
}
