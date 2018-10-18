package com.qmwl.zyjx.activity;

import android.view.View;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;

/**
 * Created by Administrator on 2017/8/2.
 * 等待审核页面
 */

public class ReviewedWaitActivity extends BaseActivity {

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.reviewed_wait_layout);
    }

    @Override
    protected void initView() {
        setTitle(R.string.wait_reviewed);
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {

    }

    @Override
    protected void onMyClick(View v) {

    }


}
