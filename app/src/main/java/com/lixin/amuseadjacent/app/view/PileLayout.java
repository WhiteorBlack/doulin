package com.lixin.amuseadjacent.app.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;

import com.lixin.amuseadjacent.R;

/**
 * Created by chenliu on 2016/11/22.<br/>
 * 描述：圆形头像重叠
 * </br>
 */
public class PileLayout extends ViewGroup {

    /**
     * 两个子控件之间的垂直间隙
     */
    protected float vertivalSpace;
    protected float pileWidth;

    public PileLayout(Context context) {
        this(context, null, 0);
    }

    public PileLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PileLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.PileLayout);
        vertivalSpace = ta.getDimension(R.styleable.PileLayout_PileLayout_vertivalSpace, dp2px(4));
        pileWidth = ta.getDimension(R.styleable.PileLayout_PileLayout_pileWidth, dp2px(10));
        ta.recycle();
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        //AT_MOST
        int width = 0;
        int height = 0;
        int rawWidth = 0;//当前行总宽度
        int rawHeight = 0;// 当前行高

        int rowIndex = 0;//当前行位置
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            if(child.getVisibility() == GONE){
                if(i == count - 1){
                    //最后一个child
                    height += rawHeight;
                    width = Math.max(width, rawWidth);
                }
                continue;
            }

            //这里调用measureChildWithMargins 而不是measureChild
            measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            int childWidth = child.getMeasuredWidth()  + lp.leftMargin + lp.rightMargin;
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            if(rawWidth + childWidth  - (rowIndex > 0 ? pileWidth : 0)> widthSpecSize - getPaddingLeft() - getPaddingRight()){
                //换行
                width = Math.max(width, rawWidth);
                rawWidth = childWidth;
                height += rawHeight + vertivalSpace;
                rawHeight = childHeight;
                rowIndex = 0;
            } else {
                rawWidth += childWidth;
                if(rowIndex > 0){
                    rawWidth -= pileWidth;
                }
                rawHeight = Math.max(rawHeight, childHeight);
            }

            if(i == count - 1){
                width = Math.max(rawWidth, width);
                height += rawHeight;
            }

            rowIndex++;
        }

        setMeasuredDimension(
                widthSpecMode == MeasureSpec.EXACTLY ? widthSpecSize : width + getPaddingLeft() + getPaddingRight(),
                heightSpecMode == MeasureSpec.EXACTLY ? heightSpecSize : height + getPaddingTop() + getPaddingBottom()
        );
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        //父view的总宽度
        int viewWidth = getWidth() -getPaddingRight()-getPaddingLeft();
        //子view的右边界
        int maxWidth =viewWidth;
        //子view距离顶部的高度
        int maxHeight = 0;
        //父组件的padingTop
        int topOfset =getPaddingTop();
        //记录 第几个字view
        int index = 0;
        for (int i = 0; i <count ; i++) {
            View childAt = getChildAt(i);
            if(childAt.getVisibility() == GONE){
                continue;
            }
            MarginLayoutParams lp = (MarginLayoutParams) childAt.getLayoutParams();
            int childWidth = lp.leftMargin +lp.rightMargin +childAt.getMeasuredWidth();
            int childHeight = lp.topMargin+lp.bottomMargin +childAt.getMeasuredHeight();
            //从右往左排,view的右边距小于view的宽度时换行
            if(childWidth > maxWidth){
                //换行
                topOfset+=maxHeight;
                maxHeight =0;
                index = 0;
                maxWidth =viewWidth;
            }
            int left = maxWidth - childWidth;
            int top = topOfset+lp.topMargin;
            int right = maxWidth;
            int bottom = topOfset +lp.topMargin+ childAt.getMeasuredHeight();
            childAt.layout(left,top,right,bottom);

            maxWidth -=childWidth;
            if(index != count -1){
                maxWidth += pileWidth;
            }
            maxHeight = Math.max(maxHeight,childHeight);
            index++;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    public float dp2px(float dpValue) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }
}
