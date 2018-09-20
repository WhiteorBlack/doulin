package com.netease.nim.uikit.api;

import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

abstract class StrCallback extends StringCallback {

    @Override
    public void onResponse(String response, int id) {

    }

    @Override
    public void onError(Call call, Exception e, int id) {
    }
}
