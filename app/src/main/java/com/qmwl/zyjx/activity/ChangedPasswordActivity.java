package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.util.Log;
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
import com.qmwl.zyjx.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/7/31.
 */

public class ChangedPasswordActivity extends BaseActivity {
    EditText jiuEdittext;
    EditText xinEdittext1;
    EditText xinEdittext2;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.changed_password_layout);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.xiugaimima);
        jiuEdittext = (EditText) findViewById(R.id.changed_password_jiumima);
        xinEdittext1 = (EditText) findViewById(R.id.changed_password_newmima);
        xinEdittext2 = (EditText) findViewById(R.id.changed_password_newmima_too);
        findViewById(R.id.changed_password_tijiao).setOnClickListener(this);
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
            case R.id.changed_password_tijiao:
                submitData();

                break;
        }
    }

    private void submitData() {
        String jiumima = jiuEdittext.getText().toString().trim();
        String xinmima = xinEdittext1.getText().toString().trim();
        String xinmimatoo = xinEdittext2.getText().toString().trim();
        if ("".equals(jiumima) || "".equals(xinmima) || "".equals(xinmimatoo)) {
            Toast.makeText(this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
            return;
        }
        if (xinmima.length()<6||xinmima.length()>12){
            Toast.makeText(this, getString(R.string.mimachangdubudui), Toast.LENGTH_SHORT).show();
            return;
        }
        if (xinmimatoo.length()<6||xinmimatoo.length()>12){
            Toast.makeText(this, getString(R.string.mimachangdubudui), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!xinmima.equals(xinmimatoo)) {
            Toast.makeText(this, getString(R.string.mimabuyizhi), Toast.LENGTH_SHORT).show();
            return;
        }

        showLoadingDialog();
        AndroidNetworking.post(Contact.xiugaimima)
                .addBodyParameter("user_id", MyApplication.getIntance().userBean.getUid())
                .addBodyParameter("old_password", jiumima)
                .addBodyParameter("new_password", xinmima)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        try {
                            if (JsonUtils.is100Success(response)) {
                                Toast.makeText(ChangedPasswordActivity.this, getString(R.string.xiugaimimachenggong), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ChangedPasswordActivity.this,LoginActivity.class);
                                startActivity(intent);
                                MyApplication.getIntance().exitLogin();
                                finish();
                            } else {
                                Toast.makeText(ChangedPasswordActivity.this, "" + response.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ChangedPasswordActivity.this, getString(R.string.xiugaimimashibai), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                        Toast.makeText(ChangedPasswordActivity.this, "修改密码失败", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}
