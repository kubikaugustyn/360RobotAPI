package p251ob;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.qihoo.common.widget.C2273e;
import com.qihoo.smarthome.R;
import com.qihoo.smarthome.sweeper.entity.VoicePacket;
import com.qihoo.smarthome.sweeper.entity.VoicePacketList;
import com.qihoo.smarthome.sweeper.entity.VoicePacketResponse;
import com.qihoo.smarthome.sweeper.mvp.SweeperPresenter;
import com.qihoo.smarthome.sweeper.net.entity.Head;
import io.reactivex.disposables.InterfaceC4122b;
import io.reactivex.processors.PublishProcessor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import p038ce.C0988a;
import p195k7.C4546c;
import p237nb.C5398p;
import p237nb.C5404s;
import p251ob.C5499b;
import p352vd.C6302a;
import p363wa.C6394h1;
import p381xd.InterfaceC6489c;
import p381xd.InterfaceC6493g;
import p381xd.InterfaceC6494h;
import p393y9.C6635f1;
import p393y9.C6670w0;
import td.AbstractC6158e;
import va.C6293d;

/* compiled from: VoicePacketFragment.java */
/* renamed from: ob.o */
/* loaded from: classes2.dex */
public class ViewOnClickListenerC5512o extends C6394h1 implements View.OnClickListener {

    /* renamed from: h */
    private View f21091h;

    /* renamed from: j */
    private SwipeRefreshLayout f21092j;

    /* renamed from: k */
    private RecyclerView f21093k;

    /* renamed from: l */
    private C5499b f21094l;

    /* renamed from: m */
    private C5398p f21095m;

    /* renamed from: n */
    private C5404s f21096n;

    /* renamed from: p */
    private SweeperPresenter f21097p;

    /* renamed from: q */
    private MediaPlayer f21098q;

    /* renamed from: s */
    private String f21099s;

    /* renamed from: t */
    private String f21100t;

    /* renamed from: u */
    private String f21101u;

    /* renamed from: w */
    private PublishProcessor<VoicePacketResponse> f21102w;

    /* renamed from: x */
    private long f21103x;

    /* renamed from: y */
    private SwipeRefreshLayout.InterfaceC0732j f21104y = new a();

    /* renamed from: z */
    private InterfaceC4122b f21105z;

    /* compiled from: VoicePacketFragment.java */
    /* renamed from: ob.o$a */
    /* loaded from: classes2.dex */
    class a implements SwipeRefreshLayout.InterfaceC0732j {
        a() {
        }

        @Override // androidx.swiperefreshlayout.widget.SwipeRefreshLayout.InterfaceC0732j
        /* renamed from: b0 */
        public void mo4571b0() {
            if (ViewOnClickListenerC5512o.this.f21092j.mo4562c()) {
                return;
            }
            C4546c.m18761g("start refreshing");
            ViewOnClickListenerC5512o.this.m21991E1();
            ViewOnClickListenerC5512o.this.m21995I1();
            ViewOnClickListenerC5512o.this.f21092j.setRefreshing(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: E1 */
    public void m21991E1() {
        C4546c.m18758d("cancelTimeoutTimer()");
        InterfaceC4122b interfaceC4122b = this.f21105z;
        if (interfaceC4122b == null || interfaceC4122b.isDisposed()) {
            return;
        }
        this.f21105z.dispose();
    }

    /* renamed from: F1 */
    private void m21992F1(Exception exc) {
        C4546c.m18758d("emitterError(e=" + exc + ")");
        PublishProcessor<VoicePacketResponse> publishProcessor = this.f21102w;
        if (publishProcessor != null) {
            publishProcessor.onError(exc);
            this.f21102w.onComplete();
            this.f21102w = null;
        }
    }

    /* renamed from: G1 */
    private String m21993G1(int i10) {
        switch (i10) {
            case 0:
                return getString(R.string.success);
            case 1:
                return getString(R.string.unknown_cmd);
            case 2:
                return getString(R.string.missing_parameter);
            case 3:
                return getString(R.string.voice_packet_not_exist);
            case 4:
                return getString(R.string.apply_using_voice_packet);
            case 5:
                return getString(R.string.delete_using_voice_packet);
            case 6:
                return getString(R.string.voice_packet_too_large);
            case 7:
                return getString(R.string.one_voice_packet_downloading);
            case 8:
                return getString(R.string.voice_packet_download_error);
            case 9:
                return getString(R.string.invalid_md5);
            case 10:
                return getString(R.string.voice_packet_downloading);
            case 11:
                return getString(R.string.local_existing_voice_packet_directly_applied);
            default:
                return "";
        }
    }

    /* renamed from: H1 */
    private AbstractC6158e<List<VoicePacket>> m21994H1() {
        C4546c.m18758d("getVoicePacketList()");
        return C6293d.m24353b().m24335j(this.f23936f).m23803L(new InterfaceC6494h() { // from class: ob.e
            @Override // p381xd.InterfaceC6494h
            public final Object apply(Object obj) {
                return (VoicePacketList) ((Head) obj).getData();
            }
        }).m23803L(new InterfaceC6494h() { // from class: ob.d
            @Override // p381xd.InterfaceC6494h
            public final Object apply(Object obj) {
                return ((VoicePacketList) obj).getList();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SuppressLint({"CheckResult"})
    /* renamed from: I1 */
    public void m21995I1() {
        C4546c.m18758d("initData()");
        this.f21096n.m21559j();
        this.f21095m.m21537c();
        this.f21092j.setVisibility(0);
        PublishProcessor<VoicePacketResponse> m17350E0 = PublishProcessor.m17350E0();
        this.f21102w = m17350E0;
        AbstractC6158e.m23798z0(m17350E0, m21994H1(), new InterfaceC6489c() { // from class: ob.j
            @Override // p381xd.InterfaceC6489c
            public final Object apply(Object obj, Object obj2) {
                Object m21998L1;
                m21998L1 = ViewOnClickListenerC5512o.this.m21998L1((VoicePacketResponse) obj, (List) obj2);
                return m21998L1;
            }
        }).m23837m0(C0988a.m5685b()).m23804O(C6302a.m24390a()).m23828h0(new InterfaceC6493g() { // from class: ob.l
            @Override // p381xd.InterfaceC6493g
            public final void accept(Object obj) {
                ViewOnClickListenerC5512o.this.m21999M1(obj);
            }
        }, new InterfaceC6493g() { // from class: ob.k
            @Override // p381xd.InterfaceC6493g
            public final void accept(Object obj) {
                ViewOnClickListenerC5512o.this.m22000N1((Throwable) obj);
            }
        });
        this.f21099s = this.f21097p.voicePacketGetList();
    }

    /* renamed from: J1 */
    private void m21996J1(View view) {
        View findViewById = view.findViewById(R.id.layout_title_bar);
        this.f21091h = findViewById;
        findViewById.findViewById(R.id.button_back).setOnClickListener(this);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_swipe_refresh);
        this.f21092j = swipeRefreshLayout;
        swipeRefreshLayout.setColorSchemeResources(R.color.swipe_layout_theme_progress);
        this.f21092j.setSize(1);
        this.f21092j.setOnRefreshListener(this.f21104y);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        this.f21093k = recyclerView;
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        this.f21095m = new C5398p(view.findViewById(R.id.layout_exception));
        this.f21096n = new C5404s(view.findViewById(R.id.layout_loading));
        m11581t0(this.f21091h, C6635f1.m25210o(getContext()));
    }

    /* renamed from: K1 */
    private boolean m21997K1() {
        if (SystemClock.elapsedRealtime() - this.f21103x < 1000) {
            return true;
        }
        this.f21103x = SystemClock.elapsedRealtime();
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: L1 */
    public /* synthetic */ Object m21998L1(VoicePacketResponse voicePacketResponse, List list) {
        C4546c.m18758d("apply(voicePackets=" + list + ", voicePacketResponse=" + voicePacketResponse + ")");
        this.f21094l.m21983o(list);
        this.f21094l.m21973d();
        for (VoicePacketResponse.PacketInfo packetInfo : voicePacketResponse.getVoicePackageList()) {
            this.f21094l.m21972b(packetInfo.getId(), packetInfo.getState());
        }
        this.f21094l.m21981m(voicePacketResponse.getCurVoicePackage());
        return new Object();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: M1 */
    public /* synthetic */ void m21999M1(Object obj) {
        C4546c.m18758d("mVoicePacketAdapter.getItemCount()=" + this.f21094l.getItemCount());
        this.f21094l.notifyDataSetChanged();
        this.f21096n.m21555d();
        if (this.f21094l.getItemCount() > 0) {
            this.f21095m.m21537c();
        } else {
            this.f21095m.m21539e();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: N1 */
    public /* synthetic */ void m22000N1(Throwable th) {
        C4546c.m18758d("accept(throwable=" + th + ")");
        this.f21096n.m21555d();
        this.f21092j.setVisibility(8);
        if ((th instanceof RuntimeException) && TextUtils.equals(th.getMessage(), "devOffline")) {
            this.f21095m.m21541g(R.string.error_sweeper_offline_please_check_network, new C5398p.b() { // from class: ob.h
                @Override // p237nb.C5398p.b
                /* renamed from: a */
                public final void mo12200a() {
                    ViewOnClickListenerC5512o.this.m21995I1();
                }
            });
        } else {
            this.f21095m.m21543i(new C5398p.b() { // from class: ob.h
                @Override // p237nb.C5398p.b
                /* renamed from: a */
                public final void mo12200a() {
                    ViewOnClickListenerC5512o.this.m21995I1();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: O1 */
    public /* synthetic */ void m22001O1(int i10, int i11) {
        if (!m21997K1()) {
            VoicePacket m21975f = this.f21094l.m21975f(i10);
            if (i11 != 0) {
                if (i11 == 3) {
                    this.f21100t = this.f21097p.voicePacketDownloadAndApply(m21975f.getId(), m21975f.getPacketUrl(), m21975f.getPacketMd5(), m21975f.getPacketSize(), m21975f.getLang());
                    m22007U1(m21975f.getId());
                    return;
                } else {
                    if (i11 == 1) {
                        this.f21101u = this.f21097p.voicePacketDownload(m21975f.getId(), m21975f.getPacketUrl(), m21975f.getPacketMd5(), m21975f.getPacketSize(), m21975f.getLang());
                        m22008V1(m21975f.getId());
                        return;
                    }
                    return;
                }
            }
            String id2 = m21975f.getId();
            int m21974e = this.f21094l.m21974e(id2);
            if (m21974e == 0) {
                this.f21094l.m21982n(id2, 2);
                m22011Y1();
                return;
            } else if (m21974e == 2) {
                this.f21094l.m21982n(id2, 1);
                m22009W1(m21975f);
                return;
            } else {
                C2273e.m10577d(getContext(), "正在加载...", 0);
                return;
            }
        }
        C4546c.m18758d("点的太快了");
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: P1 */
    public /* synthetic */ void m22002P1(String str, MediaPlayer mediaPlayer) {
        C4546c.m18758d("onPrepared(mp=" + mediaPlayer + ")");
        mediaPlayer.start();
        this.f21094l.m21982n(str, 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: Q1 */
    public /* synthetic */ void m22003Q1(String str, MediaPlayer mediaPlayer) {
        C4546c.m18758d("onCompletion(mp=" + mediaPlayer + ")");
        this.f21094l.m21982n(str, 2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: R1 */
    public /* synthetic */ boolean m22004R1(MediaPlayer mediaPlayer, int i10, int i11) {
        C4546c.m18758d("onError(mp=" + mediaPlayer + ", what=" + i10 + ", extra=" + i11 + ")");
        C2273e.m10577d(getContext(), "播放失败!", 1);
        if (i10 == 1) {
            C4546c.m18758d("what=MediaPlayer.MEDIA_ERROR_UNKNOWN");
        } else if (i10 != 100) {
            C4546c.m18758d("what=" + i10);
        } else {
            C4546c.m18758d("what=MediaPlayer.MEDIA_ERROR_SERVER_DIED");
        }
        if (i11 == -1010) {
            C4546c.m18758d("extra=MediaPlayer.MEDIA_ERROR_UNSUPPORTED");
            return false;
        }
        if (i11 == -1007) {
            C4546c.m18758d("extra=MediaPlayer.MEDIA_ERROR_MALFORMED");
            return false;
        }
        if (i11 == -1004) {
            C4546c.m18758d("extra=MediaPlayer.MEDIA_ERROR_IO");
            return false;
        }
        if (i11 != -110) {
            C4546c.m18758d("extra=" + i11);
            return false;
        }
        C4546c.m18758d("extra=MediaPlayer.MEDIA_ERROR_TIMED_OUT");
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: S1 */
    public /* synthetic */ void m22005S1(String str, String str2, Long l10) {
        C4546c.m18758d("startTimeoutTimer -> 下载超时");
        if (str == null) {
            this.f21094l.m21980l(str2);
        } else {
            this.f21094l.m21972b(str2, str);
        }
        this.f21094l.notifyDataSetChanged();
        C2273e.m10577d(getContext(), "下载超时", 0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: T1 */
    public static /* synthetic */ void m22006T1(Throwable th) {
        C4546c.m18758d("startTimeoutTimer -> throwabel=" + th);
    }

    /* renamed from: U1 */
    private void m22007U1(String str) {
        str.hashCode();
        char c10 = 65535;
        switch (str.hashCode()) {
            case -1350752353:
                if (str.equals("csyyb1")) {
                    c10 = 0;
                    break;
                }
                break;
            case -1350752352:
                if (str.equals("csyyb2")) {
                    c10 = 1;
                    break;
                }
                break;
            case -1350752351:
                if (str.equals("csyyb3")) {
                    c10 = 2;
                    break;
                }
                break;
            case 118212:
                if (str.equals("wyf")) {
                    c10 = 3;
                    break;
                }
                break;
        }
        switch (c10) {
            case 0:
                C6670w0.m25378a(getContext(), "1071");
                return;
            case 1:
                C6670w0.m25378a(getContext(), "1069");
                return;
            case 2:
                C6670w0.m25378a(getContext(), "1067");
                return;
            case 3:
                C6670w0.m25378a(getContext(), "1065");
                return;
            default:
                return;
        }
    }

    /* renamed from: V1 */
    private void m22008V1(String str) {
        str.hashCode();
        char c10 = 65535;
        switch (str.hashCode()) {
            case -1350752353:
                if (str.equals("csyyb1")) {
                    c10 = 0;
                    break;
                }
                break;
            case -1350752352:
                if (str.equals("csyyb2")) {
                    c10 = 1;
                    break;
                }
                break;
            case -1350752351:
                if (str.equals("csyyb3")) {
                    c10 = 2;
                    break;
                }
                break;
            case 118212:
                if (str.equals("wyf")) {
                    c10 = 3;
                    break;
                }
                break;
        }
        switch (c10) {
            case 0:
                C6670w0.m25378a(getContext(), "1070");
                return;
            case 1:
                C6670w0.m25378a(getContext(), "1068");
                return;
            case 2:
                C6670w0.m25378a(getContext(), "1066");
                return;
            case 3:
                C6670w0.m25378a(getContext(), "1064");
                return;
            default:
                return;
        }
    }

    /* renamed from: W1 */
    private void m22009W1(VoicePacket voicePacket) {
        C4546c.m18758d("startPlay(voicePacket=" + voicePacket + ")");
        if (getContext() != null) {
            final String id2 = voicePacket.getId();
            try {
                this.f21098q.reset();
                this.f21098q.setDataSource(getContext(), Uri.parse(voicePacket.getMp3Url()));
                this.f21098q.setAudioStreamType(3);
                this.f21098q.prepareAsync();
                this.f21098q.setOnPreparedListener(new MediaPlayer.OnPreparedListener() { // from class: ob.g
                    @Override // android.media.MediaPlayer.OnPreparedListener
                    public final void onPrepared(MediaPlayer mediaPlayer) {
                        ViewOnClickListenerC5512o.this.m22002P1(id2, mediaPlayer);
                    }
                });
                this.f21098q.setOnCompletionListener(new MediaPlayer.OnCompletionListener() { // from class: ob.c
                    @Override // android.media.MediaPlayer.OnCompletionListener
                    public final void onCompletion(MediaPlayer mediaPlayer) {
                        ViewOnClickListenerC5512o.this.m22003Q1(id2, mediaPlayer);
                    }
                });
                this.f21098q.setOnErrorListener(new MediaPlayer.OnErrorListener() { // from class: ob.f
                    @Override // android.media.MediaPlayer.OnErrorListener
                    public final boolean onError(MediaPlayer mediaPlayer, int i10, int i11) {
                        boolean m22004R1;
                        m22004R1 = ViewOnClickListenerC5512o.this.m22004R1(mediaPlayer, i10, i11);
                        return m22004R1;
                    }
                });
            } catch (IOException unused) {
            }
        }
    }

    /* renamed from: X1 */
    private void m22010X1(final String str, final String str2) {
        C4546c.m18758d("startTimeoutTimer(id=" + str + ", state=" + str2 + ")");
        m21991E1();
        this.f21105z = AbstractC6158e.m23797v0(120L, TimeUnit.SECONDS, C6302a.m24390a()).m23828h0(new InterfaceC6493g() { // from class: ob.m
            @Override // p381xd.InterfaceC6493g
            public final void accept(Object obj) {
                ViewOnClickListenerC5512o.this.m22005S1(str2, str, (Long) obj);
            }
        }, new InterfaceC6493g() { // from class: ob.n
            @Override // p381xd.InterfaceC6493g
            public final void accept(Object obj) {
                ViewOnClickListenerC5512o.m22006T1((Throwable) obj);
            }
        });
    }

    /* renamed from: Y1 */
    private void m22011Y1() {
        C4546c.m18758d("stopPlay()");
        MediaPlayer mediaPlayer = this.f21098q;
        if (mediaPlayer == null || !mediaPlayer.isPlaying()) {
            return;
        }
        this.f21098q.stop();
    }

    @Override // p363wa.C6394h1, p250oa.InterfaceC5478b
    /* renamed from: D */
    public void mo12030D(VoicePacketResponse voicePacketResponse) {
        C4546c.m18758d("onVoicePacket(voicePacketResponse=" + voicePacketResponse + ")");
        String id2 = voicePacketResponse.getId();
        String cmd = voicePacketResponse.getCmd();
        int error = voicePacketResponse.getError();
        if (TextUtils.equals(cmd, "getVoicePackageInfo")) {
            PublishProcessor<VoicePacketResponse> publishProcessor = this.f21102w;
            if (publishProcessor != null) {
                publishProcessor.onNext(voicePacketResponse);
                this.f21102w.onComplete();
                this.f21102w = null;
            }
        } else if (TextUtils.equals(cmd, "download")) {
            if (error == 0) {
                m22010X1(id2, this.f21094l.m21976h(id2));
                this.f21094l.notifyDataSetChanged();
                C2273e.m10575b(getContext(), R.string.start_download, 0);
            }
        } else if (TextUtils.equals(cmd, "process")) {
            if (error == 0) {
                int process = voicePacketResponse.getProcess();
                this.f21094l.m21985q(process);
                if (process == 100) {
                    m21991E1();
                    this.f21094l.m21972b(id2, "saved");
                } else {
                    this.f21094l.m21972b(id2, "downloading");
                }
            } else {
                this.f21094l.m21980l(id2);
            }
            this.f21094l.notifyDataSetChanged();
        } else if (TextUtils.equals(cmd, "apply")) {
            this.f21094l.m21979k();
            if (error == 0) {
                this.f21094l.m21981m(id2);
                this.f21094l.notifyDataSetChanged();
                C2273e.m10575b(getContext(), R.string.apply_ok, 0);
            } else if (error == 8) {
                this.f21094l.m21972b(id2, "saved");
                this.f21094l.notifyDataSetChanged();
            }
        } else if (TextUtils.equals(cmd, "downloadAndApply")) {
            if (error == 0) {
                m22010X1(id2, this.f21094l.m21976h(id2));
            } else if (error == 11 || error == 4) {
                this.f21094l.m21981m(id2);
                this.f21094l.notifyDataSetChanged();
            } else if (error == 8) {
                this.f21094l.m21972b(id2, "saved");
                this.f21094l.notifyDataSetChanged();
            }
        } else if (TextUtils.equals(cmd, "delete")) {
            this.f21094l.m21980l(id2);
            this.f21094l.notifyDataSetChanged();
        }
        if (error != 0) {
            C2273e.m10577d(getContext(), m21993G1(voicePacketResponse.getError()), 0);
        }
    }

    @Override // p363wa.C6394h1, p250oa.InterfaceC5478b
    /* renamed from: Z */
    public void mo5430Z(String str, String str2) {
        if (TextUtils.equals(str, "21027")) {
            if (TextUtils.equals(str2, this.f21100t) || TextUtils.equals(str2, this.f21101u)) {
                C4546c.m18758d("已经给扫地机发送应用或下载语音包的指令,等待扫地机回复");
                this.f21096n.m21559j();
            }
        }
    }

    @Override // p363wa.C6394h1, p250oa.InterfaceC5478b
    /* renamed from: e0 */
    public void mo5431e0(String str, String str2) {
        C4546c.m18758d("onSendCmdTimeout(cmd=" + str + ", taskid=" + str2 + ")");
        if (TextUtils.equals(str, "21027")) {
            if (TextUtils.equals(str2, this.f21099s)) {
                m21992F1(new TimeoutException());
            }
            C4546c.m18758d("等待扫地机回复应用指令或下载指令超时!");
            this.f21096n.m21555d();
            C2273e.m10575b(getContext(), R.string.error_network_request_timeout, 0);
        }
    }

    @Override // p363wa.C6394h1
    /* renamed from: n1 */
    public void m24574m1(String str, Throwable th) {
        C4546c.m18758d("onSendCmdError(cmd=" + str + ", throwable=" + th + ")");
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() != R.id.button_back) {
            return;
        }
        m11583v0();
    }

    @Override // p363wa.C6394h1, com.qihoo.smarthome.sweeper.common.BaseFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.f21098q = new MediaPlayer();
    }

    @Override // com.qihoo.smarthome.sweeper.common.BaseFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View inflate = layoutInflater.inflate(R.layout.fragment_voice_packet, viewGroup, false);
        m21996J1(inflate);
        return inflate;
    }

    @Override // com.qihoo.smarthome.sweeper.common.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroy() {
        this.f21097p.onDestroy();
        this.f21098q.release();
        super.onDestroy();
    }

    @Override // com.qihoo.smarthome.sweeper.common.BaseFragment, androidx.fragment.app.Fragment
    public void onDestroyView() {
        PublishProcessor<VoicePacketResponse> publishProcessor = this.f21102w;
        if (publishProcessor != null) {
            publishProcessor.onComplete();
            this.f21102w = null;
        }
        super.onDestroyView();
    }

    @Override // com.qihoo.smarthome.sweeper.common.BaseFragment, androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        SweeperPresenter sweeperPresenter = new SweeperPresenter(this.f23936f, this);
        this.f21097p = sweeperPresenter;
        sweeperPresenter.onCreate();
        ArrayList arrayList = new ArrayList();
        RecyclerView recyclerView = this.f21093k;
        C5499b c5499b = new C5499b(getContext(), arrayList);
        this.f21094l = c5499b;
        recyclerView.setAdapter(c5499b);
        this.f21094l.m21984p(new C5499b.c() { // from class: ob.i
            @Override // p251ob.C5499b.c
            /* renamed from: a */
            public final void mo21986a(int i10, int i11) {
                ViewOnClickListenerC5512o.this.m22001O1(i10, i11);
            }
        });
        m21995I1();
    }

    @Override // p363wa.C6394h1, p250oa.InterfaceC5478b
    /* renamed from: q0 */
    public void mo5438q0(String str, String str2, String str3) {
        if (TextUtils.equals(str, "21027")) {
            if (TextUtils.equals(str3, this.f21100t) || TextUtils.equals(str3, this.f21101u)) {
                C4546c.m18758d("收到扫地机回复的应用或下载语音包的指令");
                this.f21096n.m21555d();
            }
        }
    }

    @Override // p363wa.C6394h1, p250oa.InterfaceC5478b
    /* renamed from: y */
    public void mo5443y(String str, int i10, String str2, String str3) {
        C4546c.m18758d("onSendCmdFaild(cmd=" + str + ", code=" + i10 + ", msg=" + str2 + ", taskid=" + str3 + ")");
        if (TextUtils.equals(str, "21027")) {
            if (TextUtils.equals(str3, this.f21099s)) {
                if (i10 == 212) {
                    m21992F1(new RuntimeException("devOffline"));
                } else if (i10 != 0) {
                    m21992F1(new RuntimeException("serverError"));
                }
            }
            if (TextUtils.equals(str3, this.f21100t) || TextUtils.equals(str3, this.f21101u)) {
                this.f21096n.m21555d();
            }
            if (i10 == 212) {
                C2273e.m10575b(getContext(), R.string.error_device_offline_retry_later, 0);
            } else {
                C2273e.m10575b(getContext(), R.string.error_network_anomaly, 0);
            }
        }
    }
}
