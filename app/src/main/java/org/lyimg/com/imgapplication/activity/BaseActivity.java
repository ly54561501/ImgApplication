package org.lyimg.com.imgapplication.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.lyimg.com.imgapplication.R;

public class BaseActivity extends AppCompatActivity {

    public Dialog dialog;
    private View dialogView;
    private TextView loadingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_loading, null);
        loadingText = (TextView) dialogView.findViewById(R.id.xlg_loading_text);
        Initdialog();
    }

    protected void showDialog() {
        this.showDialog(null);
    }

    protected void showDialog(CharSequence title) {
        if (title == null) {
            title = "加载中";
        }
        loadingText.setText(title);
        if (null != dialog && !dialog.isShowing()) {
            dialog.show();
        }

    }

    private void Initdialog() {
        dialog = new Dialog(this, R.style.xlg_loading_dialog);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(dialogView);
    }

    protected void dismissDialog() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    Toast mToast;
    public void Showtosat(String str) {
        Looper.prepare();
        if (mToast == null) {
            mToast = Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT);

        } else {
            mToast.setText(str);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
        Looper.loop();
    }

    /**
     * 设置标题
     *
     * @param haveimg 是否有返回
     * @param title   标题
     */
    public void setTitle(boolean haveimg, String title) {
        ImageView img_back = (ImageView) findViewById(R.id.img_back);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        if (haveimg) {
            img_back.setVisibility(View.VISIBLE);
        } else {
            img_back.setVisibility(View.GONE);
        }
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_title.setText(title);
    }

    @Override
    public void onDestroy() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        super.onDestroy();
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    /**
     * 屏幕宽度
     *
     * @return
     */
    public int getWidth() {
        WindowManager wm = this.getWindowManager();
        return wm.getDefaultDisplay().getWidth();
    }

    /**
     * 屏幕高度
     *
     * @return
     */
    public int getHeight() {
        WindowManager wm = this.getWindowManager();
        return wm.getDefaultDisplay().getHeight();
    }

}
