package com.qmwl.zyjx.activity;

import android.app.Dialog;
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
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.view.CommomDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/9/19.
 */

public class WangJiMimaXiuGaiMiMaActivity extends BaseActivity {
    public static String DATA = "com.gh.wangjimima.activity";
    private EditText newEdittext;
    private EditText mimaTooEdittext;

    private String username = "";

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.wangjimimaxiugaimima_layout);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.xiugaimima);
        username = getIntent().getStringExtra(DATA);
        findViewById(R.id.wangjimima_password_okbutton).setOnClickListener(this);
        newEdittext = (EditText) findViewById(R.id.wangjimima_password_newmima);
        mimaTooEdittext = (EditText) findViewById(R.id.wangjimima_password_mima_too);

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
            case R.id.wangjimima_password_okbutton:
                postMima();
                break;
        }
    }

    private void postMima() {
        String newStr = newEdittext.getText().toString().trim();
        String tooStr = mimaTooEdittext.getText().toString().trim();
        if (isStringsNull(new String[]{newStr, tooStr})) {
            Toast.makeText(this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
            return;
        }
        if (newStr.length()<6||newStr.length()>12){
            Toast.makeText(this, getString(R.string.mimachangdubudui), Toast.LENGTH_SHORT).show();
            return;
        }
        if (tooStr.length()<6||tooStr.length()>12){
            Toast.makeText(this, getString(R.string.mimachangdubudui), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!newStr.equals(tooStr)) {
            Toast.makeText(this, getString(R.string.mimabuyizhi), Toast.LENGTH_SHORT).show();
            return;
        }
        AndroidNetworking.get(Contact.wangjimimaxiugaimima + "?user_name=" + username + "&password=" + newStr)
                .build()
                .getAsJSONObject(
                        new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                dismissLoadingDialog();
                                try {
                                    if (JsonUtils.isSuccess(response)) {
                                        CommomDialog dialog = new CommomDialog(WangJiMimaXiuGaiMiMaActivity.this, R.style.dialog, getString(R.string.xiugaichenggong), new CommomDialog.OnCloseListener() {
                                            @Override
                                            public void onClick(Dialog dialog, boolean confirm) {
                                                dialog.dismiss();
                                                finish();
                                            }
                                        }).setHideCancelButton();
                                        dialog.setCanceledOnTouchOutside(false);
                                        dialog.show();
                                    } else {
                                        String message = response.getString("message");
                                        Toast.makeText(WangJiMimaXiuGaiMiMaActivity.this, message, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onError(ANError anError) {
                                dismissLoadingDialog();
                            }
                        }
                );
        showLoadingDialog();
    }
}
