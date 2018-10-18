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
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.ITextUtils;
import com.qmwl.zyjx.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/7/25.
 * 更改手机号输入手机号页面
 */

public class ShuRuShoujiHaoActivity extends BaseActivity {
    private EditText shurushoujihaoEdittext;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.shurushoujihao_layout);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.shurushoujihao);
        findViewById(R.id.shurushoujihao_layout_button).setOnClickListener(this);

        shurushoujihaoEdittext = (EditText) findViewById(R.id.shurushoujihao_layout_shoujihao_edittext);
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {

    }

    String trim = "";

    @Override
    protected void onMyClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.shurushoujihao_layout_button:
                trim = shurushoujihaoEdittext.getText().toString().trim();
                if ("".equals(trim)) {
                    Toast.makeText(this, getString(R.string.qingshurushoujihao), Toast.LENGTH_SHORT).show();
                    return;
                }

                postsms(trim);
//                intent = new Intent(this, YanZhengMaActivity.class);
//                intent.putExtra(YanZhengMaActivity.DATA, trim);
//                startActivity(intent);
                break;
        }
    }

    private void postsms(final String trim) {
        if (ITextUtils.validatePhoneNumber(trim)) {
            Toast.makeText(this, getString(R.string.qingshuruzhengquedeyanzhengma), Toast.LENGTH_SHORT).show();
            return;
        }

        AndroidNetworking.post(Contact.scend_shoijihao_code)
                .addBodyParameter("mobile", trim)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        try {
                            if (JsonUtils.is60Success(response)) {
                                Intent intent = new Intent(ShuRuShoujiHaoActivity.this, YanZhengMaActivity.class);
                                intent.putExtra(YanZhengMaActivity.DATA, trim);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(ShuRuShoujiHaoActivity.this, R.string.qingshuruzhengquedeyanzhengma, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ShuRuShoujiHaoActivity.this, R.string.qingshuruzhengquedeyanzhengma, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                        Toast.makeText(ShuRuShoujiHaoActivity.this, getString(R.string.qingjianchawangluo), Toast.LENGTH_SHORT).show();
                    }
                });
        showLoadingDialog();
    }
}
