package org.lyimg.com.imgapplication;

import android.app.Application;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by liuyong on 2017/4/19.
 */

public class Myapplication extends Application {

    public static OkHttpClient mOkHttpClient;

    @Override
    public void onCreate() {
        super.onCreate();
        mOkHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)//设置超时时间
                .readTimeout(10, TimeUnit.SECONDS)//设置读取超时时间
                .writeTimeout(10, TimeUnit.SECONDS)//设置写入超时时间
                .build();

    }
}
