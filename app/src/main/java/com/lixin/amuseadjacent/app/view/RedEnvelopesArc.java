package com.lixin.amuseadjacent.app.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 红包圆弧
 * Created by Slingge on 2018/3/21 0021.
 */

public class RedEnvelopesArc extends View {

    private Paint paint;
    private RectF mArcRect;

    public RedEnvelopesArc(Context context) {
        super(context);
    }

    public RedEnvelopesArc(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }


    private void initPaint() {
        paint = new Paint();//这个是画矩形的画笔，方便大家理解这个圆弧
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.parseColor("#ffffff"));
        paint.setStyle(Paint.Style.FILL);//设置画圆弧的画笔的属性为描边(空心)，个人喜欢叫它描边，叫空心有点会引起歧义

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


        mArcRect = new RectF();
        mArcRect.top = -h;
        mArcRect.left = 0 ;
        mArcRect.right = w ;
        mArcRect.bottom = h ;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(mArcRect, 0, 180, false, paint);
    }
}
