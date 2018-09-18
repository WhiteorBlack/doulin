package com.lixin.amuseadjacent.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.view.View;

import com.lixin.amuseadjacent.R;
import com.lixin.amuseadjacent.app.ui.contacts.DemoCache;
import com.lixin.amuseadjacent.app.ui.contacts.NimSDKOptionConfig;
import com.lixin.amuseadjacent.app.ui.contacts.Preferences;
import com.lixin.amuseadjacent.app.util.ImageLoaderUtil;
import com.lixin.amuseadjacent.app.util.SharedPreferencesUtil;
import com.lixin.amuseadjacent.app.util.StaticUtil;
import com.lixin.amuseadjacent.app.util.abLog;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nimlib.sdk.NIMClient;
import com.netease.nimlib.sdk.auth.LoginInfo;
import com.netease.nimlib.sdk.util.NIMUtil;

import cn.bingoogolapple.badgeview.BGABadgeTextView;

/**
 * Created by Administrator on 2018/3/9 0009.
 */

public class MyApplication extends MultiDexApplication {
    public static Context CONTEXT;

    private static MyApplication myApplication;

//val json = "{\"cmd\":\"getMsg\"" + "}"
// val json = "{\"cmd\":\"upPrize\",\"prizeId\":\"" + prizeId  + "\",\"userNme\":\"" + MyApplication.getUserName() + "\"}";

    public static String CameraPath = Environment.getExternalStorageDirectory().getPath() + "/带路圈/";

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

        StaticUtil.INSTANCE.setCommunityName(SharedPreferencesUtil.getSharePreStr(this, SharedPreferencesUtil.communityId));

        abLog.INSTANCE.setE(true);
//        JPushInterface.init(this);
//        JPushInterface.setDebugMode(false);// 设置开启日志,发布时请关闭日志

        ImageLoaderUtil.configImageLoader(CONTEXT);
        com.nostra13.universalimageloader.utils.L.disableLogging();

        DemoCache.setContext(this);
        NIMClient.init(this, getLoginInfo(), NimSDKOptionConfig.getSDKOptions(this));
        //网易云信
        if (NIMUtil.isMainProcess(this)) {
            NimUIKit.init(this);
        }
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
        } else if (MsgNum <= 0) {
            badgeTextView.setVisibility(View.GONE);
        } else {
            badgeTextView.showTextBadge(MsgNum + "");
        }
    }

}
