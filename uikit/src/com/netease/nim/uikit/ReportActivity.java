package com.netease.nim.uikit;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.netease.nim.uikit.api.NimUIKit;
import com.netease.nim.uikit.business.session.activity.P2PMessageActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

public class ReportActivity extends Activity implements View.OnClickListener{

    private ImageView iv_back;
    private TextView tv_toolbar_submit;
    private EditText et_title;
    private EditText et_content;

    private String sessionId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggest_back_layout);
        StatusBarUtil.setColor(ReportActivity.this, ContextCompat.getColor(ReportActivity.this, R.color.white), 0);
        StatusBarUtil.StatusBarLightMode(this);
        sessionId = getIntent().getStringExtra("sessionId");

        iv_back = findViewById(R.id.iv_back);
        tv_toolbar_submit = findViewById(R.id.tv_toolbar_submit);
        et_title = findViewById(R.id.et_title);
        et_content = findViewById(R.id.et_content);

        iv_back.setOnClickListener(this);
        tv_toolbar_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_back){
            finish();
        }
        if (id == R.id.tv_toolbar_submit){
            if (TextUtils.isEmpty(et_content.getText().toString().trim())){
                Toast.makeText(this,"请输入举报标题",Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(et_title.getText().toString().trim())){
                Toast.makeText(this,"请输入举报内容",Toast.LENGTH_SHORT).show();
                return;
            }
            String json = "{\"cmd\":\"addReport\",\"uid\":\"" +  NimUIKit.getAccount() + "\",\"auid\":\"" + sessionId +
                    "\",\"title\":\"" + et_title.getText().toString().trim() + "\",\"content\":\"" + et_content.getText().toString().trim()+ "\"}";

            OkHttpUtils.post().url( "http://39.107.106.122/wisdom/api/service?").addParams("json",json)
                    .build().execute(new StringCallback() {
                @Override
                public void onError(Call call, Exception e, int id) {
                    Toast.makeText(ReportActivity.this, "网络错误",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onResponse(String response, int id) {
                    try {
                        JSONObject json = JSON.parseObject(response);
                        if (json.getString("result").equals("0")) {
                            Toast.makeText(ReportActivity.this, "举报成功", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(ReportActivity.this, json.getString("resultNote"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.getLocalizedMessage();
                    }
                }
            });

        }
    }
}
