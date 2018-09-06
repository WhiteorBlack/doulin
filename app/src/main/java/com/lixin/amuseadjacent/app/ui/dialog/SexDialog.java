package com.lixin.amuseadjacent.app.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

import org.greenrobot.eventbus.EventBus;

/**
 * 选择性别
 * Created by Slingge on 2017/2/4 0004.
 */

public class SexDialog {

    public static void dialog(final Context context) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("请选择");
        dialog.setPositiveButton("男",
                (arg0, arg1) -> {
                    EventBus.getDefault().post("男");
                });
        dialog.setNegativeButton("女", (arg0, arg1) -> {
            EventBus.getDefault().post("女");
        });
        dialog.show();
    }

}
