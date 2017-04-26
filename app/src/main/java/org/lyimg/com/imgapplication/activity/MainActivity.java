package org.lyimg.com.imgapplication.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;
import org.lyimg.com.imgapplication.R;
import org.lyimg.com.imgapplication.Utils.HttpResultListener;
import org.lyimg.com.imgapplication.Utils.JsonUtils;
import org.lyimg.com.imgapplication.Utils.httpUtils;
import org.lyimg.com.imgapplication.adapter.MainRecAdapter;
import org.lyimg.com.imgapplication.bean.ImgType;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends BaseActivity {

    httpUtils mhttpUtils;
    RecyclerView main_recycler;
    String url = "/1208-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mhttpUtils = new httpUtils(listener_http);

        main_recycler = (RecyclerView) findViewById(R.id.main_recycler);
        //设置布局管理器
        main_recycler.setLayoutManager(new LinearLayoutManager(this));
//设置Item增加、移除动画
        main_recycler.setItemAnimator(new DefaultItemAnimator());

        HashMap<String, String> map = new HashMap<>();
        map.put("showapi_appid", "36018");
        map.put("showapi_sign", "6e9ba2c7c48d45fdbdeeef30395aa3ed");
        showDialog();
        mhttpUtils.requestPostByAsyn(url, map, 0);

    }

    HttpResultListener listener_http = new HttpResultListener() {
        @Override
        public void onSuccess(int several, String result) {
            dismissDialog();
            Log.i("TAG", "response ----->" + result);
            try {
                JSONObject object = new JSONObject(result);
                JSONObject object1 = object.optJSONObject("showapi_res_body");
                if (object1.optInt("ret_code") == 0) {
                    List<ImgType> list = JsonUtils.fromJsonArray(object1.optString("data"), ImgType.class);
                    //设置adapter
                    main_recycler.setAdapter(new MainRecAdapter(MainActivity.this, list));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int several, String msg) {
            Showtosat(msg);
            dismissDialog();
        }
    };
}
