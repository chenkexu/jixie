package com.qmwl.zyjx.activity;

import android.text.TextUtils;
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
import com.qmwl.zyjx.utils.SharedUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/7/31.
 * 修改昵称
 */

public class ChangedNickNameActivity extends BaseActivity {
    private EditText nicknameEdittext;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.changed_nickname_layout);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.xiugainickname);
        setRightText(R.string.ok);

        nicknameEdittext = (EditText) findViewById(R.id.changed_nickname_layout_edittext);
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
                sunmitNickname();
                break;

        }
    }

    private void sunmitNickname() {
        final String nickname = nicknameEdittext.getText().toString().trim();
        if (TextUtils.isEmpty(nickname)) {
            Toast.makeText(this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
            return;
        }
        showLoadingDialog();
        AndroidNetworking.post(Contact.xiugainicheng)
                .addBodyParameter("user_id", MyApplication.getIntance().userBean.getUid())
                .addBodyParameter("nickname", nickname)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        try {
                            if (JsonUtils.is100Success(response)) {
                                MyApplication.getIntance().userBean.setNickName(nickname);
                                SharedUtils.putString(SharedUtils.USER_NICKNAME, nickname, ChangedNickNameActivity.this);
                                Toast.makeText(ChangedNickNameActivity.this, getString(R.string.xiugaichenggong), Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(ChangedNickNameActivity.this, getString(R.string.xiugaishibai), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ChangedNickNameActivity.this, getString(R.string.xiugaishibai), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                        Toast.makeText(ChangedNickNameActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
