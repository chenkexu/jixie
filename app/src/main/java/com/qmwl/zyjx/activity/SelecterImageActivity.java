package com.qmwl.zyjx.activity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/9/12.
 *
 */

public class SelecterImageActivity extends BaseActivity {
    public static final String URL_DATA = "com.gh.selecter.imageview";
    private ImageView iv;
    private String id;
    private String url = "";

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.selecter_image_layout);
        setStatueHide(false);
    }

    @Override
    protected void initView() {
        id = getIntent().getStringExtra(URL_DATA);
        iv = (ImageView) findViewById(R.id.selecter_image_layout);

    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {
        showLoadingDialog();
        AndroidNetworking.get(Contact.chakanzulindingdan + "?order_id=" + id)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        String string = JsonUtils.parseChaKanZuLinDingDan(response);
                        Glide.with(SelecterImageActivity.this).load(string).into(iv);
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                    }
                });
    }

    @Override
    protected void onMyClick(View v) {

    }
}
