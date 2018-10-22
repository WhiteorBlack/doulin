package com.lixin.amuseadjacent.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.view.View;

import com.baidu.mapapi.SDKInitializer;
import com.lixin.amuseadjacent.R;
import com.lixin.amuseadjacent.app.ui.contacts.DemoCache;
import com.lixin.amuseadjacent.app.ui.contacts.NimSDKOptionConfig;
import com.lixin.amuseadjacent.app.ui.contacts.Preferences;
import com.lixin.amuseadjacent.app.util.ImageLoaderUtil;
import com.lixin.amuseadjacent.app.util.SharedPreferencesUtil;
import com.lixin.amuseadjacent.app.util.SpUtil;
import com.lixin.amuseadjacent.app.util.StaticUtil;
import com.lixin.amuseadjacent.app.util.abLog;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.util.NIMUtil;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.zhy.http.okhttp.OkHttpUtils;


import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import cn.bingoogolapple.badgeview.BGABadgeTextView;
import cn.jpush.android.api.JPushInterface;
import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2018/3/9 0009.
 */

public class MyApplication extends MultiDexApplication {
    public static Context CONTEXT;

    private static MyApplication myApplication;

//val json = "{\"cmd\":\"getMsg\"" + "}"
// val json = "{\"cmd\":\"upPrize\",\"prizeId\":\"" + prizeId  + "\",\"userNme\":\"" + MyApplication.getUserName() + "\"}";

    public static String CameraVideoPath = Environment.getExternalStorageDirectory().getPath() + "/Android/data/com.lixin.amuseadjacent/video/";

    public static MyApplication getInstance() {
        // if语句下是不会走的，Application本身已单例
        if (myApplication == null) {
            synchronized (MyApplication.class) {
                if (myApplication == null) {
                    myApplication = new MyApplication();
                }
            }
        }
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT = getApplicationContext();
        myApplication = this;

        SharedPreferences sp = getSharedPreferences(SharedPreferencesUtil.NAME, 0);
        StaticUtil.INSTANCE.setCommunityId(sp.getString(SharedPreferencesUtil.communityId, ""));
        StaticUtil.INSTANCE.setHeaderUrl(sp.getString(SharedPreferencesUtil.headerUrl, ""));
        StaticUtil.INSTANCE.setNickName(sp.getString(SharedPreferencesUtil.nickName, ""));
        StaticUtil.INSTANCE.setCommunityName(sp.getString(SharedPreferencesUtil.communityName, ""));


        abLog.INSTANCE.setE(true);
        JPushInterface.init(this);
        JPushInterface.setDebugMode(false);// 设置开启日志,发布时请关闭日志

        ImageLoaderUtil.configImageLoader(CONTEXT);
        com.nostra13.universalimageloader.utils.L.disableLogging();

        //网易云信
        DemoCache.setContext(this);
        NIMClient.init(this, getLoginInfo(), NimSDKOptionConfig.getSDKOptions(this));
        if (NIMUtil.isMainProcess(this)) {
            NimUIKit.init(this);
        }

        //百度
        SDKInitializer.initialize(this);

        UMShareAPI.get(this);
        PlatformConfig.setWeixin("wxe73e5c8db33225ac", "f862b2b950468f2ead96259bbde11568");
        PlatformConfig.setQQZone("1106937627", "KePldFLgZzyUZ47F");

        initTBS();

//        CrashHandler catchExcep = new CrashHandler(this);
//        Thread.setDefaultUncaughtExceptionHandler(catchExcep);

        if ((Boolean) SpUtil.get("isOn", true)) {
            JPushInterface.resumePush(this);
        } else {
            JPushInterface.stopPush(this);
        }


        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60000L, TimeUnit.MILLISECONDS)
                .readTimeout(60000L, TimeUnit.MILLISECONDS)
                .hostnameVerifier((hostname, session) -> true)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }


    /**
     * 初始化TBS浏览服务X5内核
     */
    private void initTBS() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.setDownloadWithoutWifi(true);//非wifi条件下允许下载X5内核
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。

            }

            @Override
            public void onCoreInitFinished() {
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }


    private LoginInfo getLoginInfo() {
        String account = Preferences.getUserAccount();
        String token = Preferences.getUserToken();

        if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(token)) {
            DemoCache.setAccount(account.toLowerCase());
            return new LoginInfo(account, token);
        } else {
            return null;
        }
    }

    /**
     * 分割 Dex 支持
     *
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }


    /**
     * 通过类名启动Activity
     *
     * @param targetClass
     */
    public static void openActivity(Context context, Class<?> targetClass) {
        openActivity(context, targetClass, null);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param targetClass
     * @param extras
     */
    public static void openActivity(Context context, Class<?> targetClass,
                                    Bundle extras) {
        Intent intent = new Intent(context, targetClass);
        if (extras != null) {
            intent.putExtras(extras);
        }
        context.startActivity(intent);
    }

    public static void openActivityForResult(Activity activity,
                                             Class<?> targetClass, Bundle extras, int requestCode) {
        Intent intent = new Intent(activity, targetClass);
        if (extras != null) {
            intent.putExtras(extras);
        }
        activity.startActivityForResult(intent, requestCode);
    }


    /**
     * Fragment中无效
     */
    public static void openActivityForResult(Activity activity,
                                             Class<?> targetClass, int requestCode) {
        openActivityForResult(activity, targetClass, null, requestCode);
    }

    public static Context getContext() {
        return CONTEXT;
    }

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }


    public static void setRedNum(BGABadgeTextView badgeTextView, int MsgNum) {
        badgeTextView.getBadgeViewHelper().setBadgeTextSizeSp(9);
        badgeTextView.getBadgeViewHelper().setBadgeTextColorInt(CONTEXT.getResources().getColor(R.color.white));
        badgeTextView.getBadgeViewHelper().setBadgeBgColorInt(CONTEXT.getResources().getColor(R.color.red));
        badgeTextView.getBadgeViewHelper().setDragable(true);
        badgeTextView.getBadgeViewHelper().setBadgePaddingDp(4);
        badgeTextView.getBadgeViewHelper().setBadgeBorderWidthDp(1);
        badgeTextView.getBadgeViewHelper().setBadgeBorderColorInt(CONTEXT.getResources().getColor(R.color.red));
        badgeTextView.showCirclePointBadge();
        //注意带上这个显示数字，否则将变成空
        if (MsgNum > 99) {
            badgeTextView.showTextBadge("99+");
            badgeTextView.setVisibility(View.VISIBLE);
        } else if (MsgNum <= 0) {
            badgeTextView.setVisibility(View.GONE);
        } else {
            badgeTextView.setVisibility(View.VISIBLE);
            badgeTextView.showTextBadge(MsgNum + "");
        }
    }

}
