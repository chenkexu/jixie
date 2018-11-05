package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.GetCodeUtils;
import com.qmwl.zyjx.utils.ITextUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/7/20.
 *
 */

public class RegisterActivity extends BaseActivity {
    public static final String TYPE = "com.qmwl.zyjx.register_type";
    private Button fasongyanzhengma;
    private View registerButton;
    private EditText yonghumingEdittext;
    private EditText passwordEdittext;
    private EditText passwordTooEdittext;
    private EditText farenEdittext;
    private EditText gongsimingchengEdittext;
    private EditText shoujihaoEdittext;
    private EditText duanxinyanzhengmaEdittext;


    private boolean isGeRenRegister = false;//是否是个人注册

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.register_activity_layout);
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        isGeRenRegister = intent.getBooleanExtra(TYPE, false);
        findViewById(R.id.register_yonghuxieyi).setOnClickListener(this);
        if (isGeRenRegister) {
            setTitleContent(R.string.register);
        } else {
            setTitleContent(R.string.woyaokaidian);
        }

        fasongyanzhengma = (Button) findViewById(R.id.register_register_fasongyanzhengma);
        registerButton = findViewById(R.id.register_register_button);

        registerButton.setOnClickListener(this);
        fasongyanzhengma.setOnClickListener(this);

        yonghumingEdittext = (EditText) findViewById(R.id.register_register_yonghuming);
        passwordEdittext = (EditText) findViewById(R.id.register_register_password);
        passwordTooEdittext = (EditText) findViewById(R.id.register_register_password_too);
        farenEdittext = (EditText) findViewById(R.id.register_register_faren);
        gongsimingchengEdittext = (EditText) findViewById(R.id.register_register_gongsimingcheng);
        shoujihaoEdittext = (EditText) findViewById(R.id.register_register_shoujihao);
        duanxinyanzhengmaEdittext = (EditText) findViewById(R.id.register_register_duanxinyanzhengma);

        if (!isGeRenRegister) {
            farenEdittext.setVisibility(View.VISIBLE);
            gongsimingchengEdittext.setVisibility(View.VISIBLE);
        }
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
            case R.id.register_yonghuxieyi:
//                intent = new Intent(this, UserXieYiActivity.class);
//                startActivity(intent);
//                postData();
                break;
            case R.id.register_register_button:
                //注册
                postData();
                break;
            case R.id.register_register_fasongyanzhengma:
                getCode();
                break;
        }
    }

    //开始注册
    private void postData() {
        String yonghuming = yonghumingEdittext.getText().toString();
        String password = passwordEdittext.getText().toString();
        String password_too = passwordTooEdittext.getText().toString();
        String faren = farenEdittext.getText().toString();
        String gongsimingcheng = gongsimingchengEdittext.getText().toString();
        String shoujihao = shoujihaoEdittext.getText().toString();
        String duanxinCode = duanxinyanzhengmaEdittext.getText().toString();
        if (!password.equals(password_too)) {
            Toast.makeText(this, getString(R.string.mimabuyizhi), Toast.LENGTH_SHORT).show();
            return;
        }
//        if (ITextUtils.validatePhoneNumber(shoujihao)) {
//            Toast.makeText(this, getString(R.string.qingshuruzhengquedeyanzhengma), Toast.LENGTH_SHORT).show();
//            return;
//        }
        if(password.length()<6||password.length()>12){
            Toast.makeText(this, getString(R.string.mimachangdubudui), Toast.LENGTH_SHORT).show();
            return;
        }


        ANRequest.PostRequestBuilder post = AndroidNetworking.post(Contact.register);
        if (isGeRenRegister) {
            if ("".equals(yonghuming) || "".equals(password) || "".equals(password_too) ||
                    "".equals(shoujihao) || "".equals(duanxinCode)) {
                Toast.makeText(this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
                return;
            }
            post.addBodyParameter("flag", "0");
        } else {
            if ("".equals(yonghuming) || "".equals(password) || "".equals(password_too) || "".equals(faren) || "".equals(gongsimingcheng) ||
                    "".equals(shoujihao) || "".equals(duanxinCode)) {
                Toast.makeText(this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
                return;
            }
            post.addBodyParameter("legal_person", faren)
                    .addBodyParameter("company", gongsimingcheng)
                    .addBodyParameter("flag", "1");
        }



        post.addBodyParameter("username", yonghuming)
                .addBodyParameter("password", password)
                .addBodyParameter("mobile", shoujihao)
                .addBodyParameter("code", duanxinCode)
//                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        dismissLoadingDialog();
                        try {
                            String code = response.getString("code");
                            if ("0".equals(code)) {
                                Toast.makeText(RegisterActivity.this, getString(R.string.zhucechenggong), Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                String msg = response.getString("message");
                                Toast.makeText(RegisterActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, getString(R.string.zhuceshibai) + "JSONException", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        dismissLoadingDialog();
                        Toast.makeText(RegisterActivity.this, getString(R.string.zhuceshibai) + error.getErrorCode(), Toast.LENGTH_SHORT).show();
                    }
                });
        showLoadingDialog();
    }


    private void getCode() {
        String shoujihao = shoujihaoEdittext.getText().toString().trim();
        if ("".equals(shoujihao)) {
            Toast.makeText(this, getString(R.string.qingshurushoujihao), Toast.LENGTH_SHORT).show();
            return;
        }
//        if (ITextUtils.validatePhoneNumber(shoujihao)) {
//            Toast.makeText(this, getString(R.string.qingshuruzhengquedeyanzhengma), Toast.LENGTH_SHORT).show();
//            return;
//        }
        AndroidNetworking.post(Contact.register_getcode)
                .addBodyParameter("tel", shoujihao)
                .addBodyParameter("state", "1")
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
//                JsonUtils.isSuccess()
                parseCode(response);
            }

            @Override
            public void onError(ANError anError) {
                Toast.makeText(RegisterActivity.this, getString(R.string.qingshuruzhengquedeyanzhengma), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void yanzhengCode() {
        String shoujihao = shoujihaoEdittext.getText().toString().trim();
        String code = duanxinyanzhengmaEdittext.getText().toString().trim();
        if ("".equals(shoujihao) || "".equals(code)) {
            Toast.makeText(this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
            return;
        }
        AndroidNetworking.post(Contact.register_yanzhengcode)
                .addBodyParameter("tel", shoujihao)
                .addBodyParameter("code", code)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseYanZhengCode(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });

    }

    //验证code
    private void parseYanZhengCode(JSONObject response) {
        try {
            String code = response.getString("code");
            if ("10".equals(code)) {
                //验证通过，开始注册
                postData();
            } else {
                if (response.has("message"))
                    Toast.makeText(this, response.getString("message"), Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.yanzhnegmabuzhengque), Toast.LENGTH_SHORT).show();
        }

    }

    private void parseCode(JSONObject response) {
        try {
            String code = response.getString("code");
            if ("60".equals(code)) {
                Toast.makeText(this, getString(R.string.yanzhengmafasongchenggong), Toast.LENGTH_SHORT).show();
                GetCodeUtils getCodeUtils = new GetCodeUtils(fasongyanzhengma, getString(R.string.huoquyanzhengma), getString(R.string.chongxinhuoquyanzhengma));
                getCodeUtils.startTimerTask();
            } else {
                Toast.makeText(this, getString(R.string.yanzhengmafasongshibai), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.yanzhengmafasongshibaiwangluo), Toast.LENGTH_SHORT).show();
        }
    }
}
