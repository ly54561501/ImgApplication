package org.lyimg.com.imgapplication.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.lyimg.com.imgapplication.R;
import org.lyimg.com.imgapplication.Utils.HttpResultListener;
import org.lyimg.com.imgapplication.Utils.httpUtils;

import java.util.HashMap;

public class ImgsActivity extends BaseActivity {

    httpUtils mhttpUtils;
    RecyclerView main_recycler;
    String url = "/1208-2";
    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imgs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mhttpUtils = new httpUtils(listener_http);

        main_recycler = (RecyclerView) findViewById(R.id.main_recycler);
        //设置布局管理器
        main_recycler.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        //设置Item增加、移除动画
        main_recycler.setItemAnimator(new DefaultItemAnimator());

        HashMap<String, String> map = new HashMap<String, String>();
        map.put("showapi_appid", "36018");
        map.put("showapi_sign", "6e9ba2c7c48d45fdbdeeef30395aa3ed");
        map.put("rows", "20");
        map.put("page", page + "");
        showDialog();
        mhttpUtils.requestPostByAsyn(url, map, 0);
    }

    HttpResultListener listener_http = new HttpResultListener() {

        @Override
        public void onSuccess(int several, String result) {
            dismissDialog();
        }

        @Override
        public void onFailure(int several, String msg) {
            dismissDialog();
        }
    };
}
