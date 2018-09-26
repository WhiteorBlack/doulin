package com.lixin.amuseadjacent.app.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Slingge on 2018/8/15
 */
public class RoundImageView5 extends android.support.v7.widget.AppCompatImageView {

    float width, height;

    public RoundImageView5(Context context) {
        this(context, null);
    }

    public RoundImageView5(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundImageView5(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (Build.VERSION.SDK_INT < 18) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        width = getWidth();
        height = getHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (width > 16 && height >16) {
            Path path = new Path();
            path.moveTo(16, 0);
            path.lineTo(width - 16, 0);
            path.quadTo(width, 0, width, 16);
            path.lineTo(width, height - 16);
            path.quadTo(width, height, width - 16, height);
            path.lineTo(16, height);
            path.quadTo(0, height, 0, height - 16);
            path.lineTo(0, 16);
            path.quadTo(0, 0, 16, 0);
            canvas.clipPath(path);
        }

        super.onDraw(canvas);
    }


}
