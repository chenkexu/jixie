package com.qmwl.zyjx.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.api.ApiManager;
import com.qmwl.zyjx.api.ApiResponse;
import com.qmwl.zyjx.api.BaseObserver;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.bean.CancelOrderBean;
import com.qmwl.zyjx.bean.DingDanBean;
import com.qmwl.zyjx.bean.ShoppingBean;
import com.qmwl.zyjx.utils.RxUtil;
import com.qmwl.zyjx.view.AskRetunPayDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/20.
 * 申请维权界面
 */

public class AskWeiQuanActivity extends BaseActivity {




    @Override
    protected void setLayout() {
        setContentLayout(R.layout.activity_ask_weiquan);
    }

    @Override
    protected void initView() {

        setTitleContent(R.string.shensuweiquan);

    }
    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {

    }
/*

    @OnClick({R.id.st_tuikuan, R.id.st_tuihuotuikuan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.st_tuikuan:

                break;

        }
    }
*/


    @Override
    protected void onMyClick(View v) {
      /*  Intent intent = null;
        switch (v.getId()) {
            case R.id.login_wanjimima_button:
                intent = new Intent(this, WangJiMiMaActivity.class);
                startActivity(intent);
                break;
            case R.id.login_login_button:
                login_login();
//                Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent1);
                break;
        }*/
    }


}
