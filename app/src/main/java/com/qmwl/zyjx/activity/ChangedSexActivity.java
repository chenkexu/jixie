package com.qmwl.zyjx.activity;

import android.support.annotation.IdRes;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
 * Created by Administrator on 2017/7/31.
 * 更改性别
 */

public class ChangedSexActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private RadioButton nan;
    private RadioButton nv;
    private RadioButton baomi;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.changed_sex_layout);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.xiugaixingbie);
        setRightText(R.string.ok);
        RadioGroup sexGroup = (RadioGroup) findViewById(R.id.changed_sex_radiogroup_container);
        sexGroup.setOnCheckedChangeListener(this);

        nan = (RadioButton) findViewById(R.id.changed_sex_radio_nan);
        nv = (RadioButton) findViewById(R.id.changed_sex_radio_nv);
        baomi = (RadioButton) findViewById(R.id.changed_sex_radio_baomi);
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
            case R.id.base_top_bar_righttext:
                submitData();
                break;

        }
    }

    void submitData() {
        int value = -1;
        if (nan.isChecked()) {
            value = 1;
        } else if (nv.isChecked()) {
            value = 2;
        } else if (baomi.isChecked()) {
            value = 0;
        }
        if (value < 0) {
            Toast.makeText(this, getString(R.string.qingxuanzexingbie), Toast.LENGTH_SHORT).show();
            return;
        }

        final int finalValue = value;
        showLoadingDialog();
        AndroidNetworking.post(Contact.xiugaixingbie)
                .addBodyParameter("user_id", MyApplication.getIntance().userBean.getUid())
                .addBodyParameter("sex", String.valueOf(value))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        try {
                            if (JsonUtils.is100Success(response)) {
                                MyApplication.getIntance().userBean.setSex(String.valueOf(finalValue));
                                Toast.makeText(ChangedSexActivity.this, getString(R.string.xiugaichenggong), Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(ChangedSexActivity.this, getString(R.string.xiugaishibai), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ChangedSexActivity.this, getString(R.string.xiugaishibai), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                        Toast.makeText(ChangedSexActivity.this, getString(R.string.tijiaoshibai_wangluo), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {

    }
}
