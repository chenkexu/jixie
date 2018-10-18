package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.GetCodeUtils;
import com.qmwl.zyjx.utils.ITextUtils;
import com.qmwl.zyjx.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/9/19.
 */

public class WangJiMiMaActivity extends BaseActivity {
    private GetCodeUtils GetCodeUtils;
    private Button getCodeButton;
    private EditText usernameEdittext;
    private EditText shoujihaoEdittext;
    private EditText yanzhengCodeEdittext;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.wangjimima_layout);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.wangjimima);
        findViewById(R.id.wangjimima_button_ok).setOnClickListener(this);

        usernameEdittext = (EditText) findViewById(R.id.wangjimima_edittext_username);
        shoujihaoEdittext = (EditText) findViewById(R.id.wangjimima_edittext_shoujihao);
        yanzhengCodeEdittext = (EditText) findViewById(R.id.wangjimima_edittext_yanzhnegcode);
        getCodeButton = (Button) findViewById(R.id.wangjimima_button_yanzhnegcode);
        getCodeButton.setOnClickListener(this);

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
            case R.id.wangjimima_button_ok:
                yanzhengCode();
                break;
            case R.id.wangjimima_button_yanzhnegcode:
                getCode();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (GetCodeUtils != null) {
            GetCodeUtils.onDestroy();
        }
    }

    private void getCode() {
        String shoujihao = shoujihaoEdittext.getText().toString().trim();
        if ("".equals(shoujihao)) {
            Toast.makeText(this, getString(R.string.qingshurushoujihao), Toast.LENGTH_SHORT).show();
            return;
        }
        AndroidNetworking.post(Contact.register_getcode)
                .addBodyParameter("tel", shoujihao)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
//                JsonUtils.isSuccess()
                parseCode(response);
            }

            @Override
            public void onError(ANError anError) {
                Toast.makeText(WangJiMiMaActivity.this, getString(R.string.qingshuruzhengquedeyanzhengma), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void yanzhengCode() {
        String shoujihaoStr = shoujihaoEdittext.getText().toString().trim();
        final String usernameStr = usernameEdittext.getText().toString().trim();
        String yanzhengmaStr = yanzhengCodeEdittext.getText().toString().trim();

        if (isStringsNull(new String[]{shoujihaoStr, usernameStr, yanzhengmaStr})) {
            Toast.makeText(this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
            return;
        }
        AndroidNetworking.get(Contact.wangjimimayanzheng + "?tel=" + shoujihaoStr + "&code=" + yanzhengmaStr + "&username=" + usernameStr)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        try {
                            if (JsonUtils.isSuccess(response)) {
                                Intent intent = new Intent(WangJiMiMaActivity.this, WangJiMimaXiuGaiMiMaActivity.class);
                                intent.putExtra(WangJiMimaXiuGaiMiMaActivity.DATA, usernameStr);
                                startActivity(intent);
                                finish();
                            } else {
                                String message = response.getString("message");
                                Toast.makeText(WangJiMiMaActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(WangJiMiMaActivity.this, getString(R.string.yanzhengmacuowu), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                        Toast.makeText(WangJiMiMaActivity.this, getString(R.string.yanzhengmacuowu), Toast.LENGTH_SHORT).show();
                    }
                });
        showLoadingDialog();

    }

    private void parseCode(JSONObject response) {
        try {
            String code = response.getString("code");
            if ("60".equals(code)) {
                GetCodeUtils = new GetCodeUtils(getCodeButton, getString(R.string.huoquyanzhengma), getString(R.string.chongxinhuoquyanzhengma));
                GetCodeUtils.startTimerTask();
                Toast.makeText(this, getString(R.string.yanzhengmafasongchenggong), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.yanzhengmafasongshibai), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.yanzhengmafasongshibaiwangluo), Toast.LENGTH_SHORT).show();
        }
    }

}
