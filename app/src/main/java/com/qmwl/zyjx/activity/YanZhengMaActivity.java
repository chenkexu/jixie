package com.qmwl.zyjx.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
 * 验证码页面
 */

public class YanZhengMaActivity extends BaseActivity {
    public static final String DATA = "com.gh.genggaishoujihao_value";
    TextView callTv;
    EditText codeEdittext;
    String userCall;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.yanzhengma_activity_layout);
    }

    @Override
    protected void initView() {
        userCall = getIntent().getStringExtra(DATA);
        if ("".equals(userCall) || TextUtils.isEmpty(userCall)) {
            finish();
            return;
        }
        setTitleContent(R.string.genghuanshoujihao);
        findViewById(R.id.genghuanshoujihao_queren_okbutton).setOnClickListener(this);
        callTv = (TextView) findViewById(R.id.genghuanshoujihao_queren_calltv);
        callTv.setText(userCall);
        codeEdittext = (EditText) findViewById(R.id.genghuanshoujihao_queren_code_edittext);
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
            case R.id.genghuanshoujihao_queren_okbutton:
                xiugaishoujihao();
                break;

        }
    }

    private void xiugaishoujihao() {
        String codeStr = codeEdittext.getText().toString().trim();
        if ("".equals(codeStr)) {
            Toast.makeText(this, getString(R.string.qingshuruyanzhengma), Toast.LENGTH_SHORT).show();
            return;
        }

        AndroidNetworking.post(Contact.getShouJiHao)
                .addBodyParameter("user_id", MyApplication.getIntance().userBean.getUid())
                .addBodyParameter("user_tel", userCall.trim())
                .addBodyParameter("code", codeStr.trim())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        try {
                            if (JsonUtils.is100Success(response)) {
                                Toast.makeText(YanZhengMaActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(YanZhengMaActivity.this, getString(R.string.xiugaishibai), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(YanZhengMaActivity.this, getString(R.string.xiugaishibai), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                        Toast.makeText(YanZhengMaActivity.this, getString(R.string.xiugaishibai), Toast.LENGTH_SHORT).show();
                    }
                });
        showLoadingDialog();
    }
}
