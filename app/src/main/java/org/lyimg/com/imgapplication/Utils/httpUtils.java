package org.lyimg.com.imgapplication.Utils;

import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.lyimg.com.imgapplication.Myapplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static org.lyimg.com.imgapplication.Myapplication.mOkHttpClient;

/**
 * Created by liuyong on 2017/4/19.
 */

public class httpUtils {

    HttpResultListener listener;
    private static final String BASE_URL = "http://route.showapi.com/";
    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");//mdiatype 这个需要和服务端保持一致
    private static final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");//mdiatype 这个需要和服务端保持一致
    private static final MediaType MEDIA_OBJECT_STREAM = MediaType.parse("application/octet-stream");//mdiatype 这个需要和服务端保持一致

    public httpUtils(HttpResultListener listener) {
        this.listener = listener;
    }

    /**
     * okHttp get异步请求
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param num       第几个请求
     */
    public void requestGetByAsyn(String actionUrl, HashMap<String, String> paramsMap, final int num) {
        StringBuilder tempParams = new StringBuilder();
        try {
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                pos++;
            }
            String requestUrl = String.format("%s/%s?%s", BASE_URL, actionUrl, tempParams.toString());
            Request request = addHeaders().url(requestUrl).build();

            Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("TAG", e.toString());
                    listener.onFailure(num, e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        Log.e("TAG", "response ----->" + string);
                        listener.onSuccess(num, string);

                    } else {
                        listener.onFailure(num, "服务器错误");
                    }
                }
            });
        } catch (Exception e) {
            Log.e("TAG", e.toString());
        }
    }

    /**
     * okHttp post异步请求
     *
     * @param actionUrl 接口地址
     * @param paramsMap 请求参数
     * @param num       第几个请求
     */
    public void requestPostByAsyn(String actionUrl, HashMap<String, String> paramsMap, final int num) {
        try {
            StringBuilder tempParams = new StringBuilder();
            int pos = 0;
            for (String key : paramsMap.keySet()) {
                if (pos > 0) {
                    tempParams.append("&");
                }
                tempParams.append(String.format("%s=%s", key, URLEncoder.encode(paramsMap.get(key), "utf-8")));
                pos++;
            }
            String params = tempParams.toString();
            RequestBody body = RequestBody.create(MEDIA_TYPE_JSON, params);
            String requestUrl = String.format("%s/%s", BASE_URL, actionUrl);
            final Request request = addHeaders().url(requestUrl).post(body).build();
            final Call call = mOkHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("TAG", e.toString());
                    listener.onFailure(num, e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string = response.body().string();
//                        Log.e("TAG", "response ----->" + string);
                        listener.onSuccess(num, string);

                    } else {
                        listener.onFailure(num, "服务器错误");
                    }
                }
            });
        } catch (Exception e) {
            Log.e("TAG", e.toString());
        }
    }

    /**
     * 不带参数上传文件
     *
     * @param actionUrl 接口地址
     * @param filePath  本地文件地址
     */
    public void upLoadFile(String actionUrl, String filePath, final int num) {
        //补全请求地址
        String requestUrl = String.format("%s/%s", BASE_URL, actionUrl);
        //创建File
        File file = new File(filePath);
        //创建RequestBody
        RequestBody body = RequestBody.create(MEDIA_OBJECT_STREAM, file);
        //创建Request
        final Request request = new Request.Builder().url(requestUrl).post(body).build();
        final Call call = mOkHttpClient.newBuilder().writeTimeout(50, TimeUnit.SECONDS).build().newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", e.toString());
                listener.onFailure(num, e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String string = response.body().string();
                    Log.e("TAG", "response ----->" + string);
                    listener.onSuccess(num, string);

                } else {
                    listener.onFailure(num, "服务器错误");
                }
            }
        });
    }

    /**
     * 带参数上传文件
     *
     * @param actionUrl 接口地址
     * @param paramsMap 参数
     */
    public void upLoadFile(String actionUrl, HashMap<String, Object> paramsMap, final int num) {
        try {
            //补全请求地址
            String requestUrl = String.format("%s/%s", BASE_URL, actionUrl);
            MultipartBody.Builder builder = new MultipartBody.Builder();
            //设置类型
            builder.setType(MultipartBody.FORM);
            //追加参数
            for (String key : paramsMap.keySet()) {
                Object object = paramsMap.get(key);
                if (!(object instanceof File)) {
                    builder.addFormDataPart(key, object.toString());
                } else {
                    File file = (File) object;
                    builder.addFormDataPart(key, file.getName(), RequestBody.create(null, file));
                }
            }
            //创建RequestBody
            RequestBody body = builder.build();
            //创建Request
            final Request request = new Request.Builder().url(requestUrl).post(body).build();
            //单独设置参数 比如读取超时时间
            final Call call = mOkHttpClient.newBuilder().writeTimeout(50, TimeUnit.SECONDS).build().newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e("TAG", e.toString());
                    listener.onFailure(num, e.toString());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        Log.e("TAG", "response ----->" + string);
                        listener.onSuccess(num, string);

                    } else {
                        listener.onFailure(num, "服务器错误");
                    }
                }
            });
        } catch (Exception e) {
            Log.e("TAG", e.toString());
        }
    }

    /**
     * 统一为请求添加头信息
     *
     * @return
     */
    private Request.Builder addHeaders() {
        Request.Builder builder = new Request.Builder()
                .addHeader("Connection", "keep-alive")
                .addHeader("platform", "2")
                .addHeader("phoneModel", Build.MODEL)
                .addHeader("systemVersion", Build.VERSION.RELEASE)
                .addHeader("appVersion", "3.2.0");
        return builder;
    }
}
