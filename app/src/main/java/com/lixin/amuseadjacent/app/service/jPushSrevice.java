package com.lixin.amuseadjacent.app.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lixin.amuseadjacent.app.util.abLog;


/**
 * Created by Slingge on 2017/4/1 0001.
 */

public class jPushSrevice extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
      /*  Bundle bundle = intent.getExtras();

        //Log.e("收到的推送消息", bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle
                    .getString(JPushInterface.EXTRA_REGISTRATION_ID);
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent
                .getAction())) {
            //Log.d(TAG,
            //"[MyReceiver] 接收到推送下来的自定义消息: "
            //+ bundle.getString(JPushInterface.EXTRA_MESSAGE));
            //processCustomMessage(context, bundle);

            String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
            abLog.INSTANCE.e("推送消息", message);
            if (message.endsWith("+")) {//重新登陆
                Intent intents = new Intent("ReLoggedIn");
                intents.putExtra("msg", message.replace("+", ""));//要传递的参数
                context.sendBroadcast(intents);
            } else {
                Intent intents = new Intent("messge");
                intents.putExtra("msg", message);//要传递的参数
                context.sendBroadcast(intents);
            }

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent
                .getAction())) {
            //Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
            int notifactionId = bundle
                    .getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            //Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent
                .getAction())) {
            //Log.d(TAG, "[MyReceiver] 用户点击打开了通知");

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
        }*/
    }
}
