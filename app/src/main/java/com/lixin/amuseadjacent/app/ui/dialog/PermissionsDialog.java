package com.lixin.amuseadjacent.app.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

/**
 * 申请开启权限
 * Created by Slingge on 2017/2/4 0004.
 */

public class PermissionsDialog {

    public static void dialog(final Context context, String title) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle(title);
        dialog.setMessage("是否开启？");
        dialog.setPositiveButton("确定",
                (arg0, arg1) -> {
                    // TODO Auto-generated method stub
                    Uri packageURI = Uri.parse("package:" + context.getPackageName());
                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                    context.startActivity(intent);
                });
        dialog.setNegativeButton("取消", (arg0, arg1) -> {
        });

        dialog.show();
    }

}
