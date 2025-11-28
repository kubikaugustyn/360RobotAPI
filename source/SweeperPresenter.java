package com.qihoo.smarthome.sweeper.mvp;

import android.text.TextUtils;
import com.google.gson.Gson;
import com.q360.common.module.FCSdkConfig;
import com.qihoo.smarthome.R;
import com.qihoo.smarthome.sweeper.SweeperApplication;
import com.qihoo.smarthome.sweeper.entity.ChargingPile;
import com.qihoo.smarthome.sweeper.entity.ClipAreaCmd;
import com.qihoo.smarthome.sweeper.entity.CmdHead;
import com.qihoo.smarthome.sweeper.entity.CompositeCmds;
import com.qihoo.smarthome.sweeper.entity.Empty;
import com.qihoo.smarthome.sweeper.entity.FurnitureBean;
import com.qihoo.smarthome.sweeper.entity.FurnitureBeanCmd;
import com.qihoo.smarthome.sweeper.entity.MapInfo;
import com.qihoo.smarthome.sweeper.entity.PathInfo;
import com.qihoo.smarthome.sweeper.entity.PushHead;
import com.qihoo.smarthome.sweeper.entity.SweepArea;
import com.qihoo.smarthome.sweeper.entity.SweepAreaEnable;
import com.qihoo.smarthome.sweeper.entity.SweepAreaList;
import com.qihoo.smarthome.sweeper.entity.SweepBlockAreaCmd;
import com.qihoo.smarthome.sweeper.entity.SweepCmd;
import com.qihoo.smarthome.sweeper.entity.SweepMode;
import com.qihoo.smarthome.sweeper.entity.SweepPath;
import com.qihoo.smarthome.sweeper.entity.Sweeper;
import com.qihoo.smarthome.sweeper.entity.VoicePacketResponse;
import com.qihoo.smarthome.sweeper.entity.WifiInfoBeanList;
import com.qihoo.smarthome.sweeper.mvp.SweeperPresenter;
import com.qihoo.smarthome.sweeper.net.entity.ErrorInfo;
import io.reactivex.disposables.InterfaceC4122b;
import io.reactivex.processors.AbstractC4278a;
import io.reactivex.processors.PublishProcessor;
import java.io.Serializable;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLProtocolException;
import p038ce.C0988a;
import p170ia.AbstractC3947a;
import p170ia.C3955i;
import p195k7.C4546c;
import p250oa.InterfaceC5478b;
import p325te.InterfaceC6178b;
import p352vd.C6302a;
import p381xd.InterfaceC6487a;
import p381xd.InterfaceC6493g;
import p381xd.InterfaceC6494h;
import va.C6293d;

/* loaded from: classes2.dex */
public class SweeperPresenter extends AbstractC3947a implements Serializable {
    private InterfaceC4122b mDisposableCmd;
    private InterfaceC5478b mISweeperView;
    private int mLastErrorCode;
    private MapInfo mOldMapInfo;
    private SweepAreaList mOldSweepAreaList;
    private Sweeper mOldSweeper;
    private VoicePacketResponse mOldVoicePacketResponse;
    private WifiInfoBeanList mOldWifiInfoBeanList;
    private PublishProcessor<C2558a> mPublisherCmd;
    private AbstractC4278a<C2558a> mPublisherSync;
    private String mSN;
    private C3955i mSweeperManager;
    private final int timeOut = 30;
    private Gson mGson = new Gson();
    private List<InterfaceC4122b> mDisposableList = new ArrayList();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.qihoo.smarthome.sweeper.mvp.SweeperPresenter$a */
    /* loaded from: classes2.dex */
    public static class C2558a {

        /* renamed from: a */
        String f11268a;

        /* renamed from: b */
        String f11269b;

        /* renamed from: c */
        String f11270c;

        public C2558a(String str, String str2, String str3) {
            this.f11268a = str;
            this.f11269b = str2;
            this.f11270c = str3;
        }

        public String toString() {
            return "CmdInfo{cmd='" + this.f11268a + "', data='" + this.f11269b + "', taskid='" + this.f11270c + "'}";
        }
    }

    public SweeperPresenter(String str, InterfaceC5478b interfaceC5478b) {
        PublishProcessor<C2558a> m17350E0 = PublishProcessor.m17350E0();
        this.mPublisherCmd = m17350E0;
        this.mPublisherSync = m17350E0.m17361C0();
        this.mLastErrorCode = 0;
        this.mSN = str;
        this.mISweeperView = interfaceC5478b;
        this.mSweeperManager = C3955i.m16287i(str);
        this.mDisposableCmd = this.mPublisherSync.m23840o0(new InterfaceC6494h() { // from class: com.qihoo.smarthome.sweeper.mvp.a
            @Override // p381xd.InterfaceC6494h
            public final Object apply(Object obj) {
                InterfaceC6178b lambda$new$0;
                lambda$new$0 = SweeperPresenter.this.lambda$new$0((SweeperPresenter.C2558a) obj);
                return lambda$new$0;
            }
        }).m23837m0(C0988a.m5685b()).m23804O(C6302a.m24390a()).m23828h0(new InterfaceC6493g() { // from class: oa.n
            @Override // p381xd.InterfaceC6493g
            public final void accept(Object obj) {
                SweeperPresenter.this.lambda$new$1((ErrorInfo) obj);
            }
        }, new InterfaceC6493g() { // from class: oa.u
            @Override // p381xd.InterfaceC6493g
            public final void accept(Object obj) {
                SweeperPresenter.lambda$new$2((Throwable) obj);
            }
        });
    }

    private long getMapId() {
        MapInfo m16314k = this.mSweeperManager.m16314k();
        if (m16314k != null) {
            return m16314k.getMapId();
        }
        return 0L;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ InterfaceC6178b lambda$new$0(C2558a c2558a) {
        C4546c.m18758d("apply(cmdInfo=" + c2558a + ")");
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setErrno(-1);
        return C6293d.m24353b().m24348w(this.mSN, c2558a.f11268a, c2558a.f11269b).m23837m0(C0988a.m5685b()).m23813X(errorInfo);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$1(ErrorInfo errorInfo) {
        C4546c.m18758d("accept(errorInfo=" + errorInfo + ")");
        this.mLastErrorCode = errorInfo.getErrorCode();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$new$2(Throwable th) {
        C4546c.m18758d("accept(throwable=" + th + ")");
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendCmd$3(String str, String str2, String str3, ErrorInfo errorInfo) {
        C4546c.m18758d("accept(errorInfo=" + errorInfo + ",cmd=" + str + ")");
        this.mLastErrorCode = errorInfo.getErrorCode();
        if (errorInfo.getErrno() != 0) {
            if (this.mSweeperManager.m16297H(str)) {
                this.mISweeperView.mo5443y(str, errorInfo.getErrno(), errorInfo.getErrmsg(), str2);
            }
            if (errorInfo.getErrno() == 212) {
                makeDeviceOffline();
                return;
            }
            return;
        }
        this.mISweeperView.mo5430Z(str, str2);
        this.mSweeperManager.m16310e(str2, "cmd=" + str + ", data=" + str3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$sendCmd$4(String str, String str2, Throwable th) {
        C4546c.m18758d("accept(throwable=" + th + ")");
        String message = th.getMessage();
        C4546c.m18756b("msg>>>" + message);
        if ((th instanceof UnknownHostException) || (th instanceof SocketException) || (th instanceof SocketTimeoutException)) {
            message = SweeperApplication.m11552l().getString(R.string.error_network_anomaly);
        }
        if (((th instanceof SSLProtocolException) || (th instanceof SSLHandshakeException) || (th instanceof SSLException)) && TextUtils.equals(str, "21037")) {
            message = "SSLException";
        }
        if (this.mSweeperManager.m16297H(str)) {
            this.mISweeperView.mo5443y(str, -1, message, str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$sendCmd$5(String str) {
        C4546c.m18758d("sendCmd complete, taskid=" + str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSmartAreaInfo$10(String str, String str2, Throwable th) {
        C4546c.m18758d("setSmartAreaInfo -> accept(throwable=" + th + ")");
        String message = th.getMessage();
        if ((th instanceof UnknownHostException) || (th instanceof SocketException) || (th instanceof SocketTimeoutException)) {
            message = SweeperApplication.m11552l().getString(R.string.error_network_anomaly);
        }
        if (this.mSweeperManager.m16297H(str)) {
            this.mISweeperView.mo5443y(str, -1, message, str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setSmartAreaInfo$9(String str, String str2, boolean z, String str3, ErrorInfo errorInfo) {
        C4546c.m18758d("setSmartAreaInfo -> accept(errorInfo=" + errorInfo + ")");
        this.mLastErrorCode = errorInfo.getErrorCode();
        if (errorInfo.getErrno() != 0) {
            if (this.mSweeperManager.m16297H(str)) {
                this.mISweeperView.mo5443y(str, errorInfo.getErrno(), errorInfo.getErrmsg(), str2);
            }
            if (errorInfo.getErrno() == 212) {
                makeDeviceOffline();
                return;
            }
            return;
        }
        if (z) {
            this.mISweeperView.mo5430Z(str, str2);
        }
        this.mSweeperManager.m16310e(str2, "cmd=" + str + ", data=" + str3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startSmartAreaSweep$6(String str, String str2, String str3, ErrorInfo errorInfo) {
        C4546c.m18758d("startSmartAreaSweep -> accept(errorInfo=" + errorInfo + ")");
        this.mLastErrorCode = errorInfo.getErrorCode();
        if (errorInfo.getErrno() != 0) {
            if (this.mSweeperManager.m16297H(str)) {
                this.mISweeperView.mo5443y(str, errorInfo.getErrno(), errorInfo.getErrmsg(), str2);
            }
            if (errorInfo.getErrno() == 212) {
                makeDeviceOffline();
                return;
            }
            return;
        }
        this.mISweeperView.mo5430Z(str, str2);
        this.mSweeperManager.m16310e(str2, "cmd=" + str + ", data=" + str3);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$startSmartAreaSweep$7(String str, String str2, Throwable th) {
        C4546c.m18758d("startSmartAreaSweep -> accept(throwable=" + th + ")");
        String message = th.getMessage();
        if ((th instanceof UnknownHostException) || (th instanceof SocketException) || (th instanceof SocketTimeoutException)) {
            message = SweeperApplication.m11552l().getString(R.string.error_network_anomaly);
        }
        if (this.mSweeperManager.m16297H(str)) {
            this.mISweeperView.mo5443y(str, -1, message, str2);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$startSmartAreaSweep$8(String str) {
        C4546c.m18758d("startSmartAreaSweep -> complete, taskid=" + str);
    }

    private void makeDeviceOffline() {
        C4546c.m18758d("makeDeviceOffline()");
        Sweeper m16317n = this.mSweeperManager.m16317n();
        if (m16317n != null) {
            m16317n.setOnline(0);
            C3955i.m16282T(m16317n);
            this.mSweeperManager.m16321x();
        }
    }

    private void removeAll(List<InterfaceC4122b> list) {
        for (InterfaceC4122b interfaceC4122b : list) {
            if (!interfaceC4122b.isDisposed()) {
                interfaceC4122b.dispose();
            }
        }
        list.clear();
    }

    private void removeDisposed(List<InterfaceC4122b> list) {
        C4546c.m18758d("removeDisposed(disposableList=" + list.size() + ")");
        Iterator<InterfaceC4122b> it = list.iterator();
        while (it.hasNext()) {
            InterfaceC4122b next = it.next();
            if (next.isDisposed()) {
                it.remove();
                C4546c.m18758d("remove disposable=" + next);
            }
        }
    }

    private String sendCmd(String str, String str2) {
        return sendCmd(str, str2, UUID.randomUUID().toString());
    }

    private void sendCompositeCmds(List<CmdHead<?>> list, List<String> list2, String str) {
        C4546c.m18758d("sendCompositeCmds(cmdHeads=" + list + ", mainCmds=" + list2 + ", taskid=" + str + ")");
        CompositeCmds compositeCmds = new CompositeCmds(list, list2);
        StringBuilder sb2 = new StringBuilder();
        sb2.append("compositeCmds=");
        sb2.append(compositeCmds);
        C4546c.m18758d(sb2.toString());
        String json = this.mGson.toJson(compositeCmds);
        C4546c.m18758d("jsonStr=" + json);
        sendCmd("30000", json, str);
    }

    public String addNewWifi(String str, String str2) {
        return sendCmd("21028", "{\"cmd\":\"addWifi\",\"wifiName\":\"" + str + "\",\"wifiPwd\":\"" + str2 + "\"}", 30);
    }

    public String changeMap(String str, String str2, String str3) {
        return sendCmd("21025", "{\"cmd\":\"changeMap\",\"cleanId\":\"" + str + "\",\"downUrl\":\"" + str2 + "\", \"md5\":\"" + str3 + "\"}", UUID.randomUUID().toString(), 45);
    }

    public void continueSweep() {
        continueSweep(UUID.randomUUID().toString());
    }

    public String deleteMap() {
        String uuid = UUID.randomUUID().toString();
        C4546c.m18758d("deleteMap(taskid=" + uuid + ")");
        return sendCmd("21024", "{\"cmd\":\"cancleMap\",\"value\":0}", uuid);
    }

    public String deleteWifi(String str) {
        return sendCmd("21028", "{\"cmd\":\"deleteWifi\",\"wifiName\":\"" + str + "\"}", 30);
    }

    public String getAutoUpdateData() {
        return sendCmd("21034", "");
    }

    public ChargingPile getChargingPile() {
        return this.mSweeperManager.m16312h();
    }

    public int getLastErrorCode() {
        return this.mLastErrorCode;
    }

    public Sweeper getSweeper() {
        return this.mSweeperManager.m16317n();
    }

    public String getWifiInfo() {
        return sendCmd("21019", "");
    }

    public boolean hasPendingCmd(String str) {
        return this.mSweeperManager.m16320q(str);
    }

    public void initData() {
        Sweeper m16317n = this.mSweeperManager.m16317n();
        if (m16317n == null) {
            this.mOldSweeper = null;
        } else {
            this.mOldSweeper = (Sweeper) m16317n.clone();
        }
        this.mISweeperView.mo5433g();
        this.mISweeperView.mo12032c0();
        this.mISweeperView.mo5436j0();
        this.mISweeperView.mo5441s0();
        this.mISweeperView.mo12029C();
        this.mISweeperView.mo5428B();
        this.mISweeperView.mo12035k();
        this.mISweeperView.mo5429F();
        this.mISweeperView.onError();
    }

    public void loadData() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new CmdHead<>("20001", new Empty()));
        arrayList.add(new CmdHead<>("21014", new Empty()));
        arrayList.add(new CmdHead<>("21011", new SweepPath(0, 0, 0)));
        sendCompositeCmds(arrayList, new ArrayList(), null);
    }

    public void loadDataC60() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new CmdHead<>("20001", new Empty()));
        sendCompositeCmds(arrayList, new ArrayList(), null);
    }

    @Override // p170ia.AbstractC3947a
    public void onChanged() {
        Sweeper m16317n = this.mSweeperManager.m16317n();
        if (m16317n == null) {
            return;
        }
        Sweeper sweeper = this.mOldSweeper;
        if (sweeper == null) {
            initData();
        } else {
            if (!TextUtils.equals(sweeper.getTitle(), m16317n.getTitle())) {
                this.mISweeperView.mo5433g();
            }
            if (!TextUtils.equals(this.mOldSweeper.getModel(), m16317n.getModel())) {
                this.mISweeperView.mo12032c0();
            }
            TextUtils.equals(this.mOldSweeper.getFirmwareVersion(), m16317n.getFirmwareVersion());
            if (this.mOldSweeper.getState() != m16317n.getState() || this.mOldSweeper.getMopStatus() != m16317n.getMopStatus() || this.mOldSweeper.getTimerStatus() != m16317n.getTimerStatus() || this.mOldSweeper.getWater() != m16317n.getWater() || this.mOldSweeper.getAutoWater() != m16317n.getAutoWater() || this.mOldSweeper.getPointCleanStyle() != m16317n.getPointCleanStyle() || this.mOldSweeper.getCarpetDepthClean() != m16317n.getCarpetDepthClean() || this.mOldSweeper.getMopCarpet() != m16317n.getMopCarpet() || this.mOldSweeper.getAutoBoost() != m16317n.getAutoBoost() || this.mOldSweeper.getRelocatingFlag() != m16317n.getRelocatingFlag() || !TextUtils.equals(this.mOldSweeper.getExtendStatus(), m16317n.getExtendStatus()) || this.mOldSweeper.getStartBtnDirection() != m16317n.getStartBtnDirection() || this.mOldSweeper.getModeIng() != m16317n.getModeIng() || this.mOldSweeper.getVol() != m16317n.getVol()) {
                this.mISweeperView.mo5436j0();
            }
            if (this.mOldSweeper.getState() != m16317n.getState()) {
                this.mISweeperView.mo5441s0();
            }
            if (!TextUtils.equals(this.mOldSweeper.getStartLeftBtnType(), m16317n.getStartLeftBtnType()) || !TextUtils.equals(this.mOldSweeper.getStartRightBtnType(), m16317n.getStartRightBtnType())) {
                this.mISweeperView.mo12034i0(m16317n.getStartLeftBtnType(), m16317n.getStartRightBtnType());
            }
            if (this.mOldSweeper.getSweepMode() != m16317n.getSweepMode()) {
                this.mISweeperView.mo12029C();
            }
            if (this.mOldSweeper.getBatteryUse() != m16317n.getBatteryUse() || this.mOldSweeper.getState() != m16317n.getState()) {
                this.mISweeperView.mo5428B();
            }
            if (this.mOldSweeper.getPosX() != m16317n.getPosX() || this.mOldSweeper.getPosY() != m16317n.getPosY()) {
                this.mISweeperView.mo12035k();
            }
            if (this.mOldSweeper.getArea() != m16317n.getArea() || this.mOldSweeper.getTime() != m16317n.getTime() || this.mOldSweeper.getAllArea() != m16317n.getAllArea() || this.mOldSweeper.getAllTime() != m16317n.getAllTime() || this.mOldSweeper.getMopStatus() != m16317n.getMopStatus() || this.mOldSweeper.getAvoidCarpetNumber() != m16317n.getAvoidCarpetNumber() || this.mOldSweeper.getAvoidCarpetNumberForShare() != m16317n.getAvoidCarpetNumberForShare() || this.mOldSweeper.getCarpetDepthCleanCnts() != m16317n.getCarpetDepthCleanCnts() || this.mOldSweeper.getObstaclesTimes() != m16317n.getObstaclesTimes() || this.mOldSweeper.getCarpetTimes() != m16317n.getCarpetTimes() || this.mOldSweeper.getDepthTimes() != m16317n.getDepthTimes() || this.mOldSweeper.getBatteryDamage() != m16317n.getBatteryDamage() || this.mOldSweeper.getKitchenToiletLastSweepOnceClean() != m16317n.getKitchenToiletLastSweepOnceClean()) {
                this.mISweeperView.mo5429F();
            }
            if (this.mOldSweeper.getError() != m16317n.getError()) {
                this.mISweeperView.onError();
            }
            if (this.mOldSweeper.getOnline() != m16317n.getOnline() || m16317n.getOnline() == 0) {
                this.mISweeperView.mo5435h0();
            }
            if (this.mOldSweeper.getShowPathType() != m16317n.getShowPathType()) {
                this.mISweeperView.mo12031M(m16317n.getShowPathType());
            }
            if (this.mOldSweeper.getQuicklyMap() != m16317n.getQuicklyMap()) {
                this.mISweeperView.mo12039r0(this.mSweeperManager.m16314k());
            }
        }
        this.mOldSweeper = (Sweeper) m16317n.clone();
        if (this.mSweeperManager.m16314k() != this.mOldMapInfo) {
            this.mISweeperView.mo12039r0(this.mSweeperManager.m16314k());
        }
        if (this.mSweeperManager.m16314k() != null && ((this.mSweeperManager.m16314k().getNewCliffAreaIdsCount() > 0 || this.mSweeperManager.m16314k().getNewCarpetAreaIdsCount() > 0 || this.mSweeperManager.m16314k().getCarpetMissRoomIdsCount() > 0) && m16317n.getSubState() == 0)) {
            this.mISweeperView.mo12036m(this.mSweeperManager.m16314k(), true);
        } else {
            this.mISweeperView.mo12036m(this.mSweeperManager.m16314k(), false);
        }
        if (this.mSweeperManager.m16316m() != this.mOldSweepAreaList) {
            this.mISweeperView.mo12040v(this.mSweeperManager.m16316m());
        }
        if (this.mSweeperManager.m16318o() != this.mOldVoicePacketResponse) {
            this.mISweeperView.mo12030D(this.mSweeperManager.m16318o());
        }
        if (this.mSweeperManager.m16319p() != this.mOldWifiInfoBeanList) {
            this.mISweeperView.mo12038o(this.mSweeperManager.m16319p());
        }
        this.mOldMapInfo = this.mSweeperManager.m16314k();
        this.mOldSweepAreaList = this.mSweeperManager.m16316m();
        this.mOldVoicePacketResponse = this.mSweeperManager.m16318o();
        this.mOldWifiInfoBeanList = this.mSweeperManager.m16319p();
    }

    public void onCreate() {
        this.mSweeperManager.m16309d(this);
    }

    public void onDestroy() {
        InterfaceC4122b interfaceC4122b = this.mDisposableCmd;
        if (interfaceC4122b != null && !interfaceC4122b.isDisposed()) {
            this.mDisposableCmd.dispose();
        }
        removeAll(this.mDisposableList);
        this.mSweeperManager.m16296G(this);
    }

    @Override // p170ia.AbstractC3947a
    public void onEvent(String str, String str2) {
        C4546c.m18758d("onEvent(cmd=" + str + ", data=" + PushHead.cutString(str2));
        this.mISweeperView.mo12037n0(str, str2);
    }

    @Override // p170ia.AbstractC3947a
    public void onPathChanged() {
        this.mISweeperView.mo12033f(this.mSweeperManager.m16315l());
    }

    @Override // p170ia.AbstractC3947a
    public void onResponse(String str, String str2, String str3) {
        C4546c.m18758d("onResponse(cmd=" + str + ", data=" + PushHead.cutString(str2) + ", taskid=" + str3 + ")");
        this.mISweeperView.mo5438q0(str, str2, str3);
    }

    @Override // p170ia.AbstractC3947a
    public void onTimeout(String str, String str2) {
        C4546c.m18758d("onTimeout(cmd=" + str + ", taskid=" + str2 + ")");
        StringBuilder sb2 = new StringBuilder();
        sb2.append("mISweeperView=");
        sb2.append(this.mISweeperView);
        C4546c.m18758d(sb2.toString());
        this.mISweeperView.mo5431e0(str, str2);
    }

    public void pauseSweep() {
        pauseSweep(UUID.randomUUID().toString());
    }

    public String reboot(int i10) {
        C4546c.m18758d("reboot(delayMilliSec=" + i10 + ")");
        return sendCmd("21024", "{\"cmd\":\"reboot\",\"value\":" + i10 + "}");
    }

    public void requestSweepPath() {
        requestSweepPath(-1L);
    }

    public String rotateMap(long j, int i10) {
        return sendCmd("26200", "{\"angle\":" + i10 + ",\"mapId\":" + j + "}");
    }

    public void sendCmd2(String str, String str2) {
        this.mPublisherSync.onNext(new C2558a(str, str2, null));
    }

    public String sendFurnitureData(long j, String str, List<FurnitureBean> list) {
        C4546c.m18758d("sendFurnitureData(mapId=" + j + ", cleanId=" + str + ", list=" + list + ")");
        String uuid = UUID.randomUUID().toString();
        sendCmd("21033", this.mGson.toJson(new FurnitureBeanCmd(j, str, list)), uuid);
        return uuid;
    }

    public void sendHeartBeat(int i10) {
        C4546c.m18758d("sendHeartBeat(timeout=" + i10 + ")");
        sendCmd2("21006", "{\"heartbeatSec\":" + i10 + "}");
    }

    public String setAreaSplitFinish(String str) {
        String uuid = UUID.randomUUID().toString();
        C4546c.m18758d("setAreaSplitFinish(taskid=" + uuid + ")");
        return sendCmd("21038", String.format("{\"cmd\":\"%s\",\"type\":\"%s\"}", "confirm", str), uuid);
    }

    public String setAutoForbidArea(boolean z, long j, int i10) {
        return sendCmd("26000", String.format("{\"cmd\":\"%s\",\"mapId\":%d,\"smartForbidAreaId\":%d}", z ? "set" : "cancel", Long.valueOf(j), Integer.valueOf(i10)));
    }

    public String setAutoUpdateFirmware(int i10) {
        C4546c.m18758d("setAutoUpdateFirmware(value=" + i10 + ")");
        return sendCmd("21024", "{\"cmd\":\"autoUpdate\", \"value\":" + i10 + ",\"timeZone\":" + (TimeZone.getDefault().getOffset(System.currentTimeMillis()) / 3600000.0f) + "}");
    }

    public String setAutoWater(int i10, String str) {
        C4546c.m18758d("setAutoWater(value=" + i10 + ", cleanType=" + str + ")");
        return sendCmd("21024", "{\"cmd\":\"setAutoWater\", \"value\":" + i10 + ", \"cleanType\":\"" + str + "\"}");
    }

    public String setAvoidFallingDown(int i10) {
        C4546c.m18758d("setAvoidFallingDown(value=" + i10 + ")");
        return sendCmd("21024", "{\"cmd\":\"setAvoidFallingDown\", \"value\":" + i10 + "}");
    }

    public String setBatteryProtection(boolean z) {
        String uuid = UUID.randomUUID().toString();
        C4546c.m18758d("setBatteryProtection(bOn=" + z + ", taskid=" + uuid + ")");
        return sendCmd("21024", "{\"cmd\":\"setBatteryProtection\",\"value\":" + (z ? 1 : 0) + "}", uuid);
    }

    public String setBlockSweep(String str, String str2) {
        C4546c.m18758d("setBlockSweep(left=" + str + ",right=" + str2);
        return sendCmd("21024", "{\"cmd\":\"setBlockSweep\",\"leftValue\":\"" + str + "\",\"rightValue\":\"" + str2 + "\"}");
    }

    public String setCarpetAuto(boolean z) {
        String uuid = UUID.randomUUID().toString();
        C4546c.m18758d("setCarpetOn(bOn=" + z + ", taskid=" + uuid + ")");
        return sendCmd("21024", "{\"cmd\":\"carpetAutoRecognize\",\"value\":" + (z ? 1 : 0) + "}", uuid);
    }

    public String setCarpetDepthOn(boolean z) {
        String uuid = UUID.randomUUID().toString();
        C4546c.m18758d("setCarpetOn(bOn=" + z + ", taskid=" + uuid + ")");
        return sendCmd("21024", "{\"cmd\":\"setCarpetDepthClean\",\"value\":" + (z ? 1 : 0) + "}", uuid);
    }

    public String setCarpetMopOn(boolean z) {
        String uuid = UUID.randomUUID().toString();
        C4546c.m18758d("setCarpetOn(bOn=" + z + ", taskid=" + uuid + ")");
        return sendCmd("21024", "{\"cmd\":\"setCarpetMop\",\"value\":" + (z ? 1 : 0) + "}", uuid);
    }

    public String setCarpetOn(boolean z) {
        String uuid = UUID.randomUUID().toString();
        C4546c.m18758d("setCarpetOn(bOn=" + z + ", taskid=" + uuid + ")");
        return sendCmd("21024", "{\"cmd\":\"setAutoBoost\",\"value\":" + (z ? 1 : 0) + "}", uuid);
    }

    public String setCarpetOrCliffOperate(String str, String str2, long j) {
        return sendCmd("21031", String.format("{\"cmd\":\"%s\",\"type\":\"%s\",\"mapId\":%d }", str, str2, Long.valueOf(j)));
    }

    public String setCurrentWifi(String str) {
        return sendCmd("21028", "{\"cmd\":\"useWifi\",\"wifiName\":\"" + str + "\"}", 30);
    }

    public void setDeviceOffline() {
        this.mSweeperManager.m16301L();
    }

    public void setForbiddenAreas(List<SweepArea> list, List<SweepArea> list2, String str) {
        C4546c.m18758d("setForbiddenAreas(areas=" + list + ", tempAreas=" + list2 + ", taskid=" + str + ")");
        ArrayList arrayList = new ArrayList();
        if (list2 == null || list2.size() == 0) {
            arrayList.add(-1);
        }
        Iterator<SweepArea> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(it.next().getId()));
        }
        long mapId = getMapId();
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new CmdHead<>("21003", new SweepAreaList(mapId, list)));
        arrayList2.add(new CmdHead<>("21023", new SweepAreaEnable(mapId, arrayList, list2)));
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add("21005");
        sendCompositeCmds(arrayList2, arrayList3, str);
    }

    public String setHallwaySweepTwoCountOn(boolean z) {
        String uuid = UUID.randomUUID().toString();
        C4546c.m18758d("setHallwaySweepTwoCountTypeOn(bOn=" + z + ", taskid=" + uuid + ")");
        return sendCmd("21024", "{\"cmd\":\"hallwaySweepTwoCount\",\"value\":" + (z ? 1 : 0) + "}", uuid);
    }

    public String setKitchenToiletLastSweepOn(boolean z) {
        String uuid = UUID.randomUUID().toString();
        C4546c.m18758d("setKitchenToiletLastSweepOn(bOn=" + z + ", taskid=" + uuid + ")");
        return sendCmd("21024", "{\"cmd\":\"kitchenToiletLastSweep\",\"value\":" + (z ? 1 : 0) + "}", uuid);
    }

    public String setLedOn(boolean z) {
        String uuid = UUID.randomUUID().toString();
        C4546c.m18758d("setLedOn(bOn=" + z + ", taskid=" + uuid + ")");
        return sendCmd("21024", "{\"cmd\":\"setledswitch\",\"value\":" + (z ? 1 : 0) + "}", uuid);
    }

    public String setMaterial(boolean z) {
        String uuid = UUID.randomUUID().toString();
        C4546c.m18758d("setMaterialOn(bOn=" + z + ", taskid=" + uuid + ")");
        return sendCmd("21024", "{\"cmd\":\"roomMaterial\",\"value\":" + (z ? 1 : 0) + "}", uuid);
    }

    public String setMopOnly(boolean z) {
        String uuid = UUID.randomUUID().toString();
        C4546c.m18758d("setMopMode(bOn=" + z + ", taskid=" + uuid + ")");
        StringBuilder sb2 = new StringBuilder();
        sb2.append("{\"cmd\":\"setMopSwitch\",\"value\":");
        sb2.append(z ? 2 : 1);
        sb2.append("}");
        return sendCmd("21024", sb2.toString(), uuid);
    }

    public void setQuicklyMap() {
        sendCmd("21036", "{\"mode\":\"quicklyMap\"}");
    }

    public void setRemoteControlNet(String str) {
        sendCmd("21037", str);
    }

    public String setSmartAreaInfo(String str, SweepAreaList sweepAreaList) {
        return setSmartAreaInfo(str, null, sweepAreaList);
    }

    public String setSoftAlongWall(int i10) {
        C4546c.m18758d("setSoftAlongWall(value=" + i10 + ")");
        return sendCmd("21024", "{\"cmd\":\"setSoftAlongWall\", \"value\":" + i10 + "}");
    }

    public void setSweepAreas(List<SweepArea> list, List<SweepArea> list2, String str) {
        C4546c.m18758d("setForbiddenAreas(areas=" + list + ", tempAreas=" + list2 + ", taskid=" + str + ")");
        ArrayList arrayList = new ArrayList();
        if (list2 == null || list2.size() == 0) {
            arrayList.add(-1);
        }
        Iterator<SweepArea> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(Integer.valueOf(it.next().getId()));
        }
        long mapId = getMapId();
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new CmdHead<>("21023", new SweepAreaEnable(mapId, arrayList, list2)));
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add("21005");
        sendCompositeCmds(arrayList2, arrayList3, str);
    }

    public void setSweepMode(int i10) {
        C4546c.m18758d("setSweepMode(mode=" + i10 + ")");
        sendCmd("21022", "{\"cmd\":\"" + SweepMode.getMode(i10) + "\"}");
    }

    public String setVolume(int i10) {
        String uuid = UUID.randomUUID().toString();
        C4546c.m18758d("setVolume(vol=" + i10 + ")");
        return sendCmd("21024", "{\"cmd\":\"setVolume\",\"value\":" + i10 + "}", uuid);
    }

    public String setWaterPump(int i10) {
        C4546c.m18758d("setWaterPump(value=" + i10 + ")");
        return sendCmd("21024", "{\"cmd\":\"setWaterPump\", \"value\":" + i10 + "}");
    }

    public void startAreaSweep(List<SweepArea> list, boolean z, String str, boolean z10, int i10, int i11, List<Integer> list2) {
        SweepCmd sweepCmd;
        C4546c.m18758d("startAreaSweep(areas=" + list + ", bContinue=" + z + ", taskid=" + str + ",submode=" + i10 + "，cleanTimes=" + i11 + ",activeId=" + list2 + ")");
        ArrayList arrayList = new ArrayList();
        if (list.size() == 0) {
            arrayList.add(-1);
        }
        arrayList.add(-2);
        if (z) {
            sweepCmd = new SweepCmd("appointClean");
        } else {
            sweepCmd = new SweepCmd("reAppointClean");
        }
        ArrayList arrayList2 = new ArrayList();
        if (!z10) {
            arrayList2.add(new CmdHead<>("21023", new SweepAreaEnable(getMapId(), arrayList, list)));
            arrayList2.add(new CmdHead<>("21005", sweepCmd));
            ArrayList arrayList3 = new ArrayList();
            arrayList3.add("21005");
            sendCompositeCmds(arrayList2, arrayList3, str);
            return;
        }
        startBlockAreaSweep(SweepBlockAreaCmd.MODE_HOME, list, SweepBlockAreaCmd.TYPE_MIXED, str, list2, z, true);
    }

    public String startBlockAreaSweep(String str, List<SweepArea> list, String str2, String str3, List<Integer> list2, boolean z, boolean z10) {
        SweepCmd sweepCmd;
        SweepBlockAreaCmd sweepBlockAreaCmd;
        if (z) {
            sweepCmd = new SweepCmd("appointClean");
        } else {
            sweepCmd = new SweepCmd("reAppointClean");
        }
        if (TextUtils.equals(str, SweepBlockAreaCmd.MODE_HOME)) {
            sweepBlockAreaCmd = new SweepBlockAreaCmd(SweepBlockAreaCmd.MODE_HOME, String.valueOf(getMapId()), list2, list, str2);
        } else {
            sweepBlockAreaCmd = new SweepBlockAreaCmd(SweepBlockAreaCmd.MODE_LIST, String.valueOf(getMapId()), str2);
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(new CmdHead<>("21032", sweepBlockAreaCmd));
        if (z10) {
            arrayList.add(new CmdHead<>("21005", sweepCmd));
        }
        ArrayList arrayList2 = null;
        if (z10) {
            arrayList2 = new ArrayList();
            arrayList2.add("21005");
        }
        sendCompositeCmds(arrayList, arrayList2, str3);
        return str3;
    }

    public void startCharging() {
        startCharging(UUID.randomUUID().toString());
    }

    public void startEdgeSweep() {
        sendCmd("21005", "{\"mode\":\"edgeClean\"}");
    }

    public void startPointSweep(int i10, int i11) {
        sendCmd("21005", "{\"mode\":\"pointClean\", \"count\":" + i10 + ", \"style\":" + i11 + "}");
    }

    public String startRemoteControl() {
        return sendCmd("21026", "");
    }

    public String startRemoteControlNet() {
        return sendCmd("21026", "{\"type\":\"tcp\"}");
    }

    public void startSmartAreaSweep() {
        startSmartAreaSweep(UUID.randomUUID().toString());
    }

    public void startSmartSweep(int i10) {
        startSmartSweep(UUID.randomUUID().toString(), i10);
    }

    public void startSweep() {
        C4546c.m18758d("startSweep()");
        ArrayList arrayList = new ArrayList();
        arrayList.add(-1);
        ArrayList arrayList2 = new ArrayList();
        arrayList2.add(new CmdHead<>("21023", new SweepAreaEnable(getMapId(), arrayList, new ArrayList())));
        arrayList2.add(new CmdHead<>("21005", new SweepCmd("appointClean")));
        ArrayList arrayList3 = new ArrayList();
        arrayList3.add("21005");
        sendCompositeCmds(arrayList2, arrayList3, null);
    }

    public void startTotalSweep() {
        sendCmd("21005", "{\"mode\":\"totalClean\"}");
    }

    public void stopCharging() {
        stopCharging(UUID.randomUUID().toString());
    }

    public String submitClipAreaData(long j, List<SweepArea> list) {
        C4546c.m18758d("submitClipAreaData(mapId=" + j + ", list=" + list + ")");
        String uuid = UUID.randomUUID().toString();
        sendCmd("21035", this.mGson.toJson(new ClipAreaCmd(j, list)), uuid);
        return uuid;
    }

    public String voicePacketApply(String str, String str2, String str3, long j, String str4) {
        return sendCmd("21027", String.format(Locale.getDefault(), "{\"cmd\":\"apply\", \"id\":\"%s\", \"downUrl\":\"%s\", \"md5sum\":\"%s\", \"size\":%d, \"lang\":\"%s\" }", str, str2, str3, Long.valueOf(j), str4));
    }

    public String voicePacketDelete(String str) {
        return sendCmd("21027", String.format("{\"cmd\":\"delete\", \"id\":\"%s\"}", str));
    }

    public String voicePacketDownload(String str, String str2, String str3, long j, String str4) {
        return sendCmd("21027", String.format(Locale.getDefault(), "{\"cmd\":\"download\", \"id\":\"%s\", \"downUrl\":\"%s\", \"md5sum\":\"%s\", \"size\":%d, \"lang\":\"%s\" }", str, str2, str3, Long.valueOf(j), str4));
    }

    public String voicePacketDownloadAndApply(String str, String str2, String str3, long j, String str4) {
        return sendCmd("21027", String.format(Locale.getDefault(), "{\"cmd\":\"downloadAndApply\", \"id\":\"%s\", \"downUrl\":\"%s\", \"md5sum\":\"%s\", \"size\":%d, \"lang\":\"%s\" }", str, str2, str3, Long.valueOf(j), str4));
    }

    public String voicePacketGetList() {
        return sendCmd("21027", "{\"cmd\":\"getVoicePackageInfo\"}");
    }

    public String wifiManagerGetList() {
        return sendCmd("21028", "{\"cmd\":\"getWifiList\"}", 30);
    }

    private String sendCmd(String str, String str2, int i10) {
        return sendCmd(str, str2, UUID.randomUUID().toString(), i10);
    }

    public void continueSweep(String str) {
        C4546c.m18758d("continueSweep(taskid=" + str + ")");
        sendCmd("21017", "{\"cmd\":\"continue\"}", str);
    }

    public void pauseSweep(String str) {
        C4546c.m18758d("pauseSweep(taskid=" + str + ")");
        sendCmd("21017", "{\"cmd\":\"pause\"}", str);
    }

    public void requestSweepPath(long j) {
        C4546c.m18758d("requestSweepPath(startPos=" + j + ")");
        if (getLastErrorCode() != 102 && getLastErrorCode() != 205) {
            PathInfo m16315l = this.mSweeperManager.m16315l();
            if (j == -1) {
                j = m16315l == null ? 0L : m16315l.getPointCounts();
            }
            C4546c.m18758d("handleCleanStatus startPos=" + j);
            sendCmd2("21011", "{\"startPos\":" + j + ",\"userId\":\"0\",\"mask\":0}");
            return;
        }
        C4546c.m18758d("扫地机登录信息过期或无权限,忽略路径请求!");
    }

    public String setSmartAreaInfo(String str, String str2, SweepAreaList sweepAreaList) {
        return setSmartAreaInfo(str, str2, sweepAreaList, true);
    }

    public void startCharging(String str) {
        C4546c.m18758d("startCharging(taskid=" + str + ")");
        sendCmd("21012", "{\"cmd\":\"start\"}", str);
    }

    public void startSmartAreaSweep(String str) {
        C4546c.m18758d("startSmartAreaSweep(taskid=" + str + ")");
        sendCmd("21005", "{\"mode\":\"smartArea\"}", str);
    }

    public void startSmartSweep(String str, int i10) {
        C4546c.m18758d("startSmartSweep(taskid=" + str + "  globalCleanTimes=" + i10 + ")");
        StringBuilder sb2 = new StringBuilder();
        sb2.append("{\"mode\":\"smartClean\",\"globalCleanTimes\":");
        sb2.append(i10);
        sb2.append("}");
        sendCmd("21005", sb2.toString(), str);
    }

    public void stopCharging(String str) {
        C4546c.m18758d("stopCharging(taskid=" + str + ")");
        sendCmd("21012", "{\"cmd\":\"stop\"}", str);
    }

    private String sendCmd(String str, String str2, String str3) {
        return sendCmd(str, str2, str3, 30);
    }

    public String setSmartAreaInfo(String str, String str2, SweepAreaList sweepAreaList, final boolean z) {
        C4546c.m18758d("setSmartAreaInfo(cleanId=" + str + ", command=" + str2 + ", sweepAreaList=" + sweepAreaList + ")");
        final String json = this.mGson.toJson(sweepAreaList);
        final String uuid = UUID.randomUUID().toString();
        final String str3 = "26100";
        this.mSweeperManager.m16295C("26100", uuid, 30);
        removeDisposed(this.mDisposableList);
        this.mDisposableList.add(C6293d.m24353b().m24333h(this.mSN, str, sweepAreaList.getMapId(), str2, json, uuid).m23837m0(C0988a.m5685b()).m23855y0(C0988a.m5685b()).m23804O(C6302a.m24390a()).m23828h0(new InterfaceC6493g() { // from class: oa.t
            @Override // p381xd.InterfaceC6493g
            public final void accept(Object obj) {
                SweeperPresenter.this.lambda$setSmartAreaInfo$9(str3, uuid, z, json, (ErrorInfo) obj);
            }
        }, new InterfaceC6493g() { // from class: oa.o
            @Override // p381xd.InterfaceC6493g
            public final void accept(Object obj) {
                SweeperPresenter.this.lambda$setSmartAreaInfo$10(str3, uuid, (Throwable) obj);
            }
        }));
        return uuid;
    }

    public void setSweepMode(int i10, String str) {
        C4546c.m18758d("setSweepMode(mode=" + i10 + ", cleanType=" + str + ")");
        sendCmd("21022", "{\"cmd\":\"" + SweepMode.getMode(i10) + "\", \"cleanType\":\"" + str + "\"}");
    }

    public String setWaterPump(int i10, String str) {
        C4546c.m18758d("setWaterPump(value=" + i10 + ", cleanType=" + str + ")");
        return sendCmd("21024", "{\"cmd\":\"setWaterPump\", \"value\":" + i10 + ", \"cleanType\":\"" + str + "\"}");
    }

    private String sendCmd(final String cmd, final String data, final String taskidStr, int timeout) {
        C4546c.m18758d("sendCmd(cmd=" + cmd + ", data=" + data + ", taskidStr=" + taskidStr + ", timeout=" + timeout + ")");
        if (TextUtils.isEmpty(taskidStr)) {
            taskidStr = UUID.randomUUID().toString();
        }
        if (!TextUtils.equals(cmd, "21014") && !TextUtils.equals(cmd, "21011")) {
            this.mSweeperManager.m16295C(cmd, taskidStr, timeout);
        }
        removeDisposed(this.mDisposableList);
        this.mDisposableList.add(C6293d.m24353b().m24349x(this.mSN, cmd, data, taskidStr).m23894Q(C0988a.m5685b()).m23902Z(C0988a.m5685b()).m23882D(C6302a.m24390a()).m23892N(new InterfaceC6493g() { // from class: oa.s
            @Override // p381xd.InterfaceC6493g
            public final void accept(Object obj) {
                SweeperPresenter.this.lambda$sendCmd$3(cmd, taskidStr, data, (ErrorInfo) obj);
            }
        }, new InterfaceC6493g() { // from class: oa.p
            @Override // p381xd.InterfaceC6493g
            public final void accept(Object obj) {
                SweeperPresenter.this.lambda$sendCmd$4(cmd, taskidStr, (Throwable) obj);
            }
        }, new InterfaceC6487a() { // from class: oa.m
            @Override // p381xd.InterfaceC6487a
            public final void run() {
                SweeperPresenter.lambda$sendCmd$5(taskidStr);
            }
        }));
        return taskidStr;
    }

    public String startSmartAreaSweep(String str, SweepAreaList sweepAreaList) {
        C4546c.m18758d("startSmartAreaSweep(cleanId=" + str + ", sweepAreaList=" + sweepAreaList + ")");
        final String json = this.mGson.toJson(sweepAreaList);
        final String uuid = UUID.randomUUID().toString();
        final String str2 = "26100";
        this.mSweeperManager.m16295C("26100", uuid, 30);
        removeDisposed(this.mDisposableList);
        this.mDisposableList.add(C6293d.m24353b().m24319F(this.mSN, str, json, uuid).m23837m0(C0988a.m5685b()).m23855y0(C0988a.m5685b()).m23804O(C6302a.m24390a()).m23830i0(new InterfaceC6493g() { // from class: oa.r
            @Override // p381xd.InterfaceC6493g
            public final void accept(Object obj) {
                SweeperPresenter.this.lambda$startSmartAreaSweep$6(str2, uuid, json, (ErrorInfo) obj);
            }
        }, new InterfaceC6493g() { // from class: oa.q
            @Override // p381xd.InterfaceC6493g
            public final void accept(Object obj) {
                SweeperPresenter.this.lambda$startSmartAreaSweep$7(str2, uuid, (Throwable) obj);
            }
        }, new InterfaceC6487a() { // from class: oa.l
            @Override // p381xd.InterfaceC6487a
            public final void run() {
                SweeperPresenter.lambda$startSmartAreaSweep$8(uuid);
            }
        }));
        return uuid;
    }

    public void startSweep(int i10, int i11) {
        C4546c.m18758d("startSweep(x=" + i10 + ", y=" + i11 + ")");
        sendCmd("21005", "{\"mode\":\"givenPoint\",\"point\":[" + i10 + FCSdkConfig.KEY_COMMA + i11 + "]}");
    }

    public void startSweep(List<Integer> list) {
        C4546c.m18758d("startSweep(ids=" + list + ")");
        sendCmd("21005", "{\"mode\":\"areaClean\",\"areaId\":" + list + "}");
    }
}
