package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.view.View;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;

/**
 * Created by Administrator on 2017/7/25.
 *
 */

public class ZhangHuAnQuanActivity extends BaseActivity {
    @Override
    protected void setLayout() {
        setContentLayout(R.layout.zhanghuanquan_layout);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.zhanghuanquan);
        findViewById(R.id.zhanghuanquan_layout_genggaishoujihao_container).setOnClickListener(this);
        findViewById(R.id.zhanghuanquan_layout_genggaimima_container).setOnClickListener(this);
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
            case R.id.zhanghuanquan_layout_genggaishoujihao_container:
                intent = new Intent(this, ShouJiHaoActivity.class);
                startActivity(intent);
                break;
            case R.id.zhanghuanquan_layout_genggaimima_container:
                intent = new Intent(this, ChangedPasswordActivity.class);
                startActivity(intent);
                break;
        }
    }
}
