package com.lixin.amuseadjacent.app.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.lixin.amuseadjacent.R;
import com.lixin.amuseadjacent.app.MyApplication;
import com.lixin.amuseadjacent.app.ui.MainActivity;
import com.lixin.amuseadjacent.app.util.AppManager;
import com.lixin.amuseadjacent.app.util.abLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import cn.jpush.android.api.JPushInterface;


/**
 * Created by Slingge on 2017/4/1 0001.
 */

public class jPushSrevice extends BroadcastReceiver {
    public static final String PRIMARY_CHANNEL = "default";
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();

        //Log.e("收到的推送消息", bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
                .getAction())) {
            //Log.d(TAG,
            //"[MyReceiver] 接收到推送下来的自定义消息: "
            //+ bundle.getString(JPushInterface.EXTRA_MESSAGE));
            //processCustomMessage(context, bundle);
//            bar(context,bundle);
//            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//            abLog.INSTANCE.e("推送消息", message);
//            if (message.endsWith("+")) {//重新登陆
//                Intent intents = new Intent("ReLoggedIn");
//                intents.putExtra("msg", message.replace("+", ""));//要传递的参数
//                context.sendBroadcast(intents);
//            } else {
//                Intent intents = new Intent("messge");
//                intents.putExtra("msg", message);//要传递的参数
//                context.sendBroadcast(intents);
//            }
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
                .getAction())) {
            //Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle
                    .getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            //Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
                .getAction())) {
            //Log.d(TAG, "[MyReceiver] 用户点击打开了通知");

            Intent i = new Intent(context, MainActivity.class);
            i.putExtras(bundle);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(i);

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent
                .getAction())) {
            //Log.d(TAG,
            //	"[MyReceiver] 用户收到到RICH PUSH CALLBACK: "
            //+ bundle.getString(JPushInterface.EXTRA_EXTRA));
            // 在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity，
            // 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent
                .getAction())) {
            boolean connected = intent.getBooleanExtra(
                    JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            //Log.w(TAG, "[MyReceiver]" + intent.getAction()
            //	+ " connected state change to " + connected);
        } else {
            //Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    private void bar(Context context, Bundle bundle) {

//        ListenerManager.getInstance().sendBroadCast("refresh");
        String message = "";

        NotificationManager mNotificationManager = (NotificationManager) MyApplication.getInstance().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(PRIMARY_CHANNEL, message, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(false); //是否在桌面icon右上角展示小红点
            channel.setShowBadge(false); //是否在久按桌面图标时显示此渠道的通知
            channel.enableVibration(false);
            channel.setSound(null, null);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MyApplication.getInstance(), PRIMARY_CHANNEL);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(MyApplication.getInstance().getResources().getString(R.string.app_name));   //通知栏标题
        builder.setContentText(message);     //通知栏内容
        builder.setTicker(message); //显示在状态栏
        builder.setAutoCancel(true);

        builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);

        Intent notificationIntent = new Intent();
        notificationIntent.setClass(context, MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent intent = PendingIntent.getActivity(context, 0,
                notificationIntent, 0);
        builder.setContentIntent(intent);

        Notification notification = builder.build();

        if (mNotificationManager == null)
            return;

        mNotificationManager.notify(1, notification);
    }
}
