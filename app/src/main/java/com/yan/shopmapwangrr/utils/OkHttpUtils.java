package com.yan.shopmapwangrr.utils;

import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Created by dell on 2017/2/25.
 */

public class OkHttpUtils {
    private OkHttpClient client;
    private Handler handler = new Handler(Looper.getMainLooper());
    public OkHttpUtils() {
        this.init();
    }
    private void init() {
        client=new OkHttpClient();
    }
    /**
     * get 请求
     */
    public void getJson(String url,final HttpCallBack callBack){
        Request request = new Request.Builder().url(url).build();
        OnStart(callBack);
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                OnError(callBack,e.getMessage());
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()){
                    onSuccess(callBack,response.body().string());
                }else{
                    OnError(callBack,response.message());
                }
            }
        });
    }
    public void OnStart(HttpCallBack callBack){
        if(callBack!=null){
            callBack.onstart();
        }
    }
    public void onSuccess(final HttpCallBack callBack,final String data){
        if(callBack!=null){
            handler.post(new Runnable() {
                @Override
                public void run() {//在主线程操作
                    callBack.onSusscess(data);
                }
            });
        }
    }
    public void OnError(final HttpCallBack callBack,final String msg){
        if(callBack!=null){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    callBack.onError(msg);
                }
            });
        }
    }
    public static abstract class HttpCallBack{
        //开始
        public void onstart(){};
        //成功回调
        public abstract void onSusscess(String data);
        //失败
        public void onError(String meg){};
    }
}
