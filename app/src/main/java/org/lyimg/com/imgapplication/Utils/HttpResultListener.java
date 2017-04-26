package org.lyimg.com.imgapplication.Utils;

/**
 * Created by liuyong on 2017/4/21.
 */

public interface HttpResultListener {
    /**
     * @param several 第几个请求
     * @param result  返回的JSON
     */
    void onSuccess(int several, String result);

    /**
     * @param several 第几个请求
     * @param msg     原因
     */
    void onFailure(int several, String msg);
}
