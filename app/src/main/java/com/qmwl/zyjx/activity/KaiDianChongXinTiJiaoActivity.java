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
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.view.CommomDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by User on 2017/11/5.
 * 我要开店，重新提交资料
 */

public class KaiDianChongXinTiJiaoActivity extends BaseActivity{

    private EditText farenEditText;
    private EditText gongsimingchengEditText;
    @Override
    protected void setLayout() {
        setContentLayout(R.layout.chongxintijiao_layout);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.woyaokaidian);
        findViewById(R.id.chongxintijiao_register_button).setOnClickListener(this);
        farenEditText = (EditText) findViewById(R.id.chongxintijiao_register_faren);
        gongsimingchengEditText = (EditText) findViewById(R.id.chongxintijiao_register_gongsimingcheng);
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {

    }

    @Override
    protected void onMyClick(View v) {
            switch (v.getId()){
                case R.id.chongxintijiao_register_button:
                    postData();
                    break;
            }
    }

    private void postData() {
        String farenStr = farenEditText.getText().toString().trim();
        String gongsiNameStr = gongsimingchengEditText.getText().toString().trim();
        if (isStringsNull(new String[]{farenStr,gongsiNameStr})){
            Toast.makeText(this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
            return;
        }
        String s = Contact.woyaokaidian_chongxin_tijiao + "?uid=" + MyApplication.getIntance().userBean.getUid() + "&company=" + gongsiNameStr + "&legal_person=" + farenStr;
        AndroidNetworking.get(s)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        try {
                            boolean success = JsonUtils.isSuccess(response);
                            if (success){
                                String a = getString(R.string.nindeziliaoyitijiaozhipingtai) + "," + getString(R.string.dengdaishenhe);
                                CommomDialog commomDialog = new CommomDialog(KaiDianChongXinTiJiaoActivity.this, R.style.dialog,a, new CommomDialog.OnCloseListener() {
                                    @Override
                                    public void onClick(Dialog dialog, boolean confirm) {
                                        finish();
                                    }
                                }).setHideCancelButton();
                                commomDialog.show();
                            }else{
                                new CommomDialog(KaiDianChongXinTiJiaoActivity.this,R.style.dialog,getString(R.string.baocunshibaiqingchongshi), new CommomDialog.OnCloseListener() {
                                    @Override
                                    public void onClick(Dialog dialog, boolean confirm) {
                                    }
                                }).setHideCancelButton().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            new CommomDialog(KaiDianChongXinTiJiaoActivity.this,R.style.dialog,getString(R.string.baocunshibaiqingchongshi), new CommomDialog.OnCloseListener() {
                                @Override
                                public void onClick(Dialog dialog, boolean confirm) {
                                }
                            }).setHideCancelButton().show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                        new CommomDialog(KaiDianChongXinTiJiaoActivity.this,R.style.dialog,getString(R.string.baocunshibaiqingchongshi), new CommomDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                            }
                        }).setHideCancelButton().show();
                    }
                });
        showLoadingDialog();
    }
}
