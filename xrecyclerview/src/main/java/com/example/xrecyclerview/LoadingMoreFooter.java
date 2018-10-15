package com.example.xrecyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadingMoreFooter extends LinearLayout {

    public final static int STATE_LOADING = 0;
    public final static int STATE_COMPLETE = 1;
    public final static int STATE_NOMORE = 2;
    public final static int STATE_ERR = 3;
    private TextView mText, mText2;
    private ProgressBar progressBar;
    private LinearLayout ll_foot;


    public LoadingMoreFooter(Context context) {
        super(context);
        initView(context);
    }

    /**
     * @param context
     * @param attrs
     */
    public LoadingMoreFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.yun_refresh_footer, this);
        mText = (TextView) findViewById(R.id.msg);
        mText2 = (TextView) findViewById(R.id.msg2);
        ll_foot=findViewById(R.id.ll_foot);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void setState(int state) {
        switch (state) {
            case STATE_LOADING:
                ll_foot.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                mText.setText(getContext().getText(R.string.listview_loading));
                this.setVisibility(View.VISIBLE);
                break;
            case STATE_COMPLETE:
                mText.setText(getContext().getText(R.string.listview_loading));
                this.setVisibility(View.VISIBLE);
                break;
            case STATE_NOMORE:
                mText.setText(getContext().getText(R.string.nomore_loading));
                progressBar.setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
                ll_foot.setVisibility(View.VISIBLE);
                break;
            case STATE_ERR:
                mText.setText(getContext().getText(R.string.err));
                progressBar.setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void reSet() {
        this.setVisibility(GONE);
    }

    public void upText(String msg) {
        mText.setText("没有更多优惠券");
        mText2.setText(msg);
        mText2.setVisibility(View.VISIBLE);
    }

}
