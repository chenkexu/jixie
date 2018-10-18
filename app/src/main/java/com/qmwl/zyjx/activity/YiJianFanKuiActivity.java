package com.qmwl.zyjx.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.ITextUtils;
import com.qmwl.zyjx.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/7/25.
 * 意见反馈
 */

public class YiJianFanKuiActivity extends BaseActivity {
    EditText addressEdittext;
    EditText callEdittext;
    EditText titleEdittext;
    EditText contentEdittext;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.fankui_activity_layout);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.yijianfankui);
        addressEdittext = (EditText) findViewById(R.id.yijianfankui_layout_address);
        callEdittext = (EditText) findViewById(R.id.yijianfankui_layout_call);
        titleEdittext = (EditText) findViewById(R.id.yijianfankui_layout_title);
        contentEdittext = (EditText) findViewById(R.id.yijianfankui_layout_content);
        findViewById(R.id.yijianfankui_layout_submit).setOnClickListener(this);
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {

    }

    @Override
    protected void onMyClick(View v) {
        switch (v.getId()) {
            case R.id.yijianfankui_layout_submit:
                submitData();

                break;
        }
    }

    private void submitData() {
        String addressString = addressEdittext.getText().toString().trim();
        String callString = callEdittext.getText().toString().trim();
        String titleString = titleEdittext.getText().toString().trim();
        String contentString = contentEdittext.getText().toString().trim();
        if ("".equals(callString) || "".equals(titleString) || "".equals(contentString)
                 || TextUtils.isEmpty(callString) || TextUtils.isEmpty(titleString) || "".equals(contentString)) {
            Toast.makeText(this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
            return;
        }
//        if (ITextUtils.validatePhoneNumber(callString)) {
//            Toast.makeText(this, getString(R.string.qingshuruzhengquedeyanzhengma), Toast.LENGTH_SHORT).show();
//            return;
//        }
        AndroidNetworking.post(Contact.yijianfankui)
                .addBodyParameter("uid", MyApplication.getIntance().userBean.getUid())
//                .addBodyParameter("address", addressString)
                .addBodyParameter("tel", callString)
                .addBodyParameter("title", titleString)
                .addBodyParameter("content", contentString)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        try {
                            if (JsonUtils.isSuccess(response)) {
                                Toast.makeText(YiJianFanKuiActivity.this, getString(R.string.tijiaochenggong), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(YiJianFanKuiActivity.this, getString(R.string.baocunshibaiqingchongshi), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                        Toast.makeText(YiJianFanKuiActivity.this, getString(R.string.tijiaoshibai_wangluo), Toast.LENGTH_SHORT).show();
                    }
                });
        showLoadingDialog();
    }
}
