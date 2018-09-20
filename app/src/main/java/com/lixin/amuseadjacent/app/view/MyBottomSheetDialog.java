package com.lixin.amuseadjacent.app.view;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetDialog;
import android.view.ViewGroup;
import android.view.WindowManager;


/**
 * Created by Slingge on 2017/3/27 0027.
 */

public class MyBottomSheetDialog extends BottomSheetDialog {


    public MyBottomSheetDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int screenHeight = getScreenHeight(getContext());
        int dialogHeight = screenHeight;
        //sdk20一下状态栏问题高度会异常
        if (android.os.Build.VERSION.SDK_INT > 19) {
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogHeight == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogHeight);
        }
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        int height = wm.getDefaultDisplay().getHeight();
        return height;
    }
}
