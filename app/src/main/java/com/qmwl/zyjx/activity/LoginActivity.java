package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.UserBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.utils.MD5Utils;
import com.qmwl.zyjx.utils.SharedUtils;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/7/20.
 */

public class LoginActivity extends BaseActivity {

    private EditText nameEdittext;
    private EditText passwordEdittext;

    private boolean isResult = false;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.login_activity_layout);
    }

    @Override
    protected void initView() {
        View back = findViewById(R.id.base_top_bar_back);
        TextView titleTv = (TextView) findViewById(R.id.base_top_bar_title);
        View gerenRegister = findViewById(R.id.login_geren_register);
        View qiyeRegister = findViewById(R.id.login_qiye_register);
        View wangjiPassword = findViewById(R.id.login_wanjimima_button);
        View loginButton = findViewById(R.id.login_login_button);

        nameEdittext = (EditText) findViewById(R.id.login_edittext_username);
        passwordEdittext = (EditText) findViewById(R.id.login_edittext_password);
        back.setVisibility(View.VISIBLE);
        titleTv.setText(getResources().getString(R.string.login));
        back.setOnClickListener(this);
        gerenRegister.setOnClickListener(this);
        qiyeRegister.setOnClickListener(this);
        wangjiPassword.setOnClickListener(this);
        loginButton.setOnClickListener(this);

    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {

    }

    @Override
    protected void onMyClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.login_wanjimima_button:
                intent = new Intent(this, WangJiMiMaActivity.class);
                startActivity(intent);
                break;
            case R.id.login_login_button:
                login_login();
//                Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent1);
                break;
            case R.id.login_geren_register:
                intent = new Intent(this, RegisterActivity.class);
                intent.putExtra(RegisterActivity.TYPE, true);
                startActivity(intent);
//                intent = new Intent(this, RegisterSelecterActivity.class);
//                startActivity(intent);
                break;
            case R.id.login_qiye_register:
                intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                break;

        }
    }

    private void login_login() {
        final String nameString = nameEdittext.getText().toString().trim();
        final String passwordString = passwordEdittext.getText().toString().trim();

        if ("".equals(nameString) || "".equals(passwordString)) {
            Toast.makeText(this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
            return;
        }
        if (passwordString.length() < 6 || passwordString.length() > 12) {
            Toast.makeText(this, getString(R.string.mimachangdubudui), Toast.LENGTH_SHORT).show();
            return;
        }
        PushAgent mPushAgent = PushAgent.getInstance(this);
        ANRequest.PostRequestBuilder postRequestBuilder = AndroidNetworking.post(Contact.login)
                .addBodyParameter("username", nameString)
                .addBodyParameter("password", passwordString)
                .setPriority(Priority.MEDIUM);
        if (mPushAgent != null) {
            postRequestBuilder.addBodyParameter("device_token", "" + mPushAgent.getRegistrationId());
        }
        postRequestBuilder.build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        // do anything with response
                        try {
                            if (JsonUtils.isSuccess(response)) {
                                UserBean userBean = parseJson(response);
                                MyApplication.getIntance().userBean = userBean;
                                if (userBean != null) {
                                    MyApplication.getIntance().is_system = userBean.getIsSystem();
                                    userBean.setUserName(nameString);
                                    userBean.setUserPassword(passwordString);
                                    SharedUtils.putString(SharedUtils.USER_UID, userBean.getUid(), LoginActivity.this);
                                    SharedUtils.putString(SharedUtils.USER_NAME, nameString, LoginActivity.this);
                                    SharedUtils.putString(SharedUtils.USER_PASSWORD, passwordString, LoginActivity.this);
                                    SharedUtils.putString(SharedUtils.USER_IMAGE, userBean.getHeadImg(), LoginActivity.this);
                                    SharedUtils.putBoolean(SharedUtils.USER_LOGIN_STATUE, true, LoginActivity.this);
                                    SharedUtils.putString(SharedUtils.USER_NICKNAME, userBean.getNickName(), LoginActivity.this);
                                }
                                addTuiSongAlias(userBean.getUid());
                                im_login();
                                setResult(0);
                                finish();
                            } else {
//                                String message = response.getString("message");
                                Toast.makeText(LoginActivity.this, getString(R.string.yonghumingmimacuowu), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, getString(R.string.wangluocuowu), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        dismissLoadingDialog();
                        Toast.makeText(LoginActivity.this, getString(R.string.wangluocuowu), Toast.LENGTH_SHORT).show();
                    }
                });
        showLoadingDialog();
    }

    private void im_login() {
        String s = MD5Utils.md5(passwordEdittext.getText().toString());
        EMClient.getInstance().login("zyjx" + MyApplication.getIntance().userBean.getUid(), s, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();
//                Intent intent = new Intent(LoginActivity.this, ImActivity.class);
//                startActivity(intent);
            }

            @Override
            public void onProgress(int progress, String status) {
            }

            @Override
            public void onError(int code, String message) {
            }
        });
    }

    //添加推送的别名
    private void addTuiSongAlias(String uid) {
//        PushAgent.getInstance(this)
        PushAgent.getInstance(this).addExclusiveAlias(uid, Contact.TUISONG_ALISA, new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean isSuccess, String message) {
            }
        });
    }

    //解析用户信息
    private UserBean parseJson(JSONObject response) {
        UserBean userBean = null;
        try {
            JSONObject data1 = response.getJSONObject("data");
            JSONObject obj = data1.getJSONObject("niu_index_response");
            userBean = new UserBean();
            String data = obj.getString("data");
            String uid = obj.getString("uid");
            int is_system = obj.getInt("is_system");
            userBean.setIsSystem(is_system);
            String instance_id = obj.getString("instance_id");
            String avatar = obj.getString("avatar");
            String nickname = obj.getString("nickname");

            userBean.setNickName(nickname);
            userBean.setHeadImg(avatar);
            userBean.setData(data);
            userBean.setUid(uid);
            userBean.setInstance_id(instance_id);
        } catch (JSONException e) {

        }
        return userBean;
    }
}
