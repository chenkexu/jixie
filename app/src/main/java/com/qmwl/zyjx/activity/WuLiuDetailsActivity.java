package com.qmwl.zyjx.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.ChaKanWuliuAdapter;
import com.qmwl.zyjx.base.BaseActivity;

/**
 * Created by Administrator on 2017/8/18.
 */

public class WuLiuDetailsActivity extends BaseActivity {
    public static final String ID = "com.gh.selecterwuliu.id";
    private String order_id = "";
    @Override
    protected void setLayout() {
        setContentLayout(R.layout.chakanwuliu_layout);
    }

    @Override
    protected void initView() {
//        order_id = getIntent().getStringExtra(ID);
//        if ("".equals(order_id) || TextUtils.isEmpty(order_id)) {
//            finish();
//            return;
//        }
        setTitleContent(R.string.wuliuxiangqing);
        ListView mLv = (ListView) findViewById(R.id.chakanwuliu_layout_listview);
        ChaKanWuliuAdapter chaKanWuliuAdapter = new ChaKanWuliuAdapter();
        mLv.setAdapter(chaKanWuliuAdapter);
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
