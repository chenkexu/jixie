package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;

/**
 * Created by 郭辉 on 2018/1/18 13:23.
 * TODO:开店成功
 */

public class RegisterKaiDianChengGongActivity extends BaseActivity {
    @Override
    protected void setLayout() {
        setContentLayout(R.layout.activity_layout_register_kaidianchenggong);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.kaidianchenggong);
        findViewById(R.id.base_top_bar_back).setVisibility(View.GONE);
        findViewById(R.id.id_register_kaidianchenggong_next).setOnClickListener(this);
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {

    }

    @Override
    protected void onMyClick(View v) {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(this, getString(R.string.dianjiquerdinganniufanhui), Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
