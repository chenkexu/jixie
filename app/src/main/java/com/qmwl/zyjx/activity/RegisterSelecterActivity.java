package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.view.View;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;

/**
 * Created by 郭辉 on 2018/1/17 13:18.
 * TODO:选择注册方式
 */

public class RegisterSelecterActivity extends BaseActivity {

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.activity_layout_selecter_register);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.woyaokaidian_zhuce);
        findViewById(R.id.id_selecter_register_gerenkaidian).setOnClickListener(this);
        findViewById(R.id.id_selecter_register_qiyekaidian).setOnClickListener(this);
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {

    }

    @Override
    protected void onMyClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.id_selecter_register_gerenkaidian:
                intent = new Intent(this, RegisterAgreementActivity.class);
                startActivity(intent);
                break;
            case R.id.id_selecter_register_qiyekaidian:
                intent = new Intent(this, RegisterAgreementActivity.class);
                intent.putExtra("type",true);
                startActivity(intent);
                break;

        }
    }
}
