package com.yan.shopmapwangrr;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.yan.shopmapwangrr.utils.ImageLoaderUtil;

/**
 * Created by dell on 2017/2/25.
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        SDKInitializer.initialize(getApplicationContext());
        ImageLoaderUtil.init(getApplicationContext());
    }

}
