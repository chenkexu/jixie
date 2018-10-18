package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.utils.SharedUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 郭辉 on 2018/1/18 11:16.
 * TODO:
 */

public class RegisterDuiGongTishiActivity extends BaseActivity {
    private String apply_id = "";
    private boolean isWaiHui;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.activity_layout_register_tishi);
    }

    @Override
    protected void initView() {
        apply_id = getIntent().getStringExtra("apply_id");
        isWaiHui = getIntent().getBooleanExtra("iswaihui", false);

        findViewById(R.id.id_register_tishi_kefu).setOnClickListener(this);
        findViewById(R.id.id_register_tishi_next).setOnClickListener(this);
        findViewById(R.id.base_top_bar_back).setVisibility(View.GONE);
        ImageView conentView = (ImageView) findViewById(R.id.id_register_tishi_image);
        if (isWaiHui) {
            setTitleContent(R.string.waihuizhuanzhang);
            conentView.setImageResource(R.mipmap.icon_waihui_tishi_ban);
        } else {
            setTitleContent(R.string.duigongfukuan);
            conentView.setImageResource(R.mipmap.icon_duigong_tishi_ban);
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

        switch (v.getId()) {
            case R.id.id_register_tishi_kefu:
                String web_phone = SharedUtils.getString("web_phone", this);
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + web_phone));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.id_register_tishi_next:
                baocunDuiGong();
                break;
        }
    }

    //保存确认状态状态
    private void baocunDuiGong() {
        if (TextUtils.isEmpty(apply_id)) {
            finish();
            return;
        }
        ANRequest.PostRequestBuilder apply_id = AndroidNetworking.post(Contact.duigongfukuan)
                .addBodyParameter("apply_id", this.apply_id);
        if (isWaiHui) {
            apply_id.addBodyParameter("type", "2");
        } else {
            apply_id.addBodyParameter("type", "1");
        }
        apply_id.build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        try {
                            if (JsonUtils.isSuccess(response)) {
                                Intent intent = new Intent(RegisterDuiGongTishiActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(RegisterDuiGongTishiActivity.this, getString(R.string.baocunshibaiqingchongshi), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(RegisterDuiGongTishiActivity.this, getString(R.string.baocunshibaiqingchongshi), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                        Toast.makeText(RegisterDuiGongTishiActivity.this, getString(R.string.baocunshibaiqingchongshi), Toast.LENGTH_SHORT).show();
                    }
                });
        showLoadingDialog();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Toast.makeText(this, getString(R.string.dianjiquerdinganniufanhui), Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
