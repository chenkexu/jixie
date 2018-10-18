package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/7/25.
 * 查看手机号页面
 */

public class ShouJiHaoActivity extends BaseActivity {
    private TextView callTv;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.shoujihao_layout);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.shoujihao);
        findViewById(R.id.shoujihao_layout_button).setOnClickListener(this);
        callTv = (TextView) findViewById(R.id.shoujihao_layout_call);
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {
        AndroidNetworking.get(Contact.getShouJiHao + "?user_id=" + MyApplication.getIntance().userBean.getUid())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        parseJson(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                    }
                });
        showLoadingDialog();
    }

    String call = "";

    //解析手机号
    private void parseJson(JSONObject response) {

        try {
            if (JsonUtils.isSuccess(response)) {
                JSONObject data = response.getJSONObject("data");
                call = data.getString("niu_index_response");
                MyApplication.getIntance().userBean.setCall(call);
                callTv.setText(call);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onMyClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.shoujihao_layout_button:
                if ("".equals(call)) {
                    Toast.makeText(this, "未获取到手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                intent = new Intent(this, ShuRuShoujiHaoActivity.class);
                startActivity(intent);
                finish();
                break;
        }

    }
}
