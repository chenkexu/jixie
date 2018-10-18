package com.qmwl.zyjx.activity;

import android.app.Dialog;
import android.content.Intent;
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
import com.qmwl.zyjx.view.CommomDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/8/28.
 * 运输报价编辑页面
 */

public class BaoJiaBianJiActivity extends BaseActivity {
    //车型选择
    public static final int CHEXING_CODE = 6;
    //车长选择
    public static final int CHECHANG_CODE = 7;
    public static final String TID_DATA = "com.gh.baojia.tid";
    private String tid = "";
    private EditText nameEditText;
    private EditText callEditText;
    private TextView chexingTv;
    private TextView chechangTv;
    private EditText priceEditText;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.baojia_bianji_layout);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.baojia);
        tid = getIntent().getStringExtra(TID_DATA);
        nameEditText = (EditText) findViewById(R.id.baojia_activity_edittext_name);
        callEditText = (EditText) findViewById(R.id.baojia_activity_edittext_call);
        chexingTv = (TextView) findViewById(R.id.baojia_activity_chexing);
        chechangTv = (TextView) findViewById(R.id.baojia_activity_chechang);
        priceEditText = (EditText) findViewById(R.id.baojia_activity_edittext_price);
        View submit = findViewById(R.id.baojia_layout_submit);
        submit.setOnClickListener(this);
        chexingTv.setOnClickListener(this);
        chechangTv.setOnClickListener(this);
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {

    }

    private void submit() {
        String nameStr = nameEditText.getText().toString().trim();
        String callStr = callEditText.getText().toString().trim();
        String chexingStr = chexingTv.getText().toString().trim();
        String chechangStr = chechangTv.getText().toString().trim();
        String priceStr = priceEditText.getText().toString().trim();
        if (isStringsNull(new String[]{nameStr, callStr, chexingStr, chechangStr, priceStr})) {
            Toast.makeText(this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
            return;
        }
        AndroidNetworking.post(Contact.yonghubaojia)
                .addBodyParameter("uid", MyApplication.getIntance().userBean.getUid())
                .addBodyParameter("tid", tid)
                .addBodyParameter("name", nameStr)
                .addBodyParameter("tel", callStr)
                .addBodyParameter("vehicle", chexingStr)
                .addBodyParameter("length", chechangStr)
                .addBodyParameter("price", priceStr)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        try {
                            if (JsonUtils.is100Success(response)) {
                                new CommomDialog(BaoJiaBianJiActivity.this, R.style.dialog, getString(R.string.tijiaochenggong), new CommomDialog.OnCloseListener() {
                                    @Override
                                    public void onClick(Dialog dialog, boolean confirm) {
                                        dialog.dismiss();
                                        BaoJiaBianJiActivity.this.finish();
                                    }
                                }).setHideCancelButton().show();
                            } else {
                                String message = response.getString("message");
                                new CommomDialog(BaoJiaBianJiActivity.this, R.style.dialog, message)
                                        .setHideCancelButton().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            new CommomDialog(BaoJiaBianJiActivity.this, R.style.dialog, getString(R.string.tijiaoshibai))
                                    .setHideCancelButton().show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                        new CommomDialog(BaoJiaBianJiActivity.this, R.style.dialog, getString(R.string.tijiaoshibai))
                                .setHideCancelButton().show();
                    }
                });
        showLoadingDialog();
    }


    @Override
    protected void onMyClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.baojia_activity_chexing:
                intent = new Intent(this, XuanZeCheXingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivityForResult(intent, CHEXING_CODE);
                break;
            case R.id.baojia_activity_chechang:
                intent = new Intent(this, XuanZeCheXingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra(XuanZeCheXingActivity.TYPE, true);
                startActivityForResult(intent, CHECHANG_CODE);
                break;
            case R.id.baojia_layout_submit:
                submit();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case CHEXING_CODE:
                    String chexingStr = data.getStringExtra(XuanZeCheXingActivity.DATA);
                    chexingTv.setText(chexingStr);
                    break;
                case CHECHANG_CODE:
                    String chechangStr = data.getStringExtra(XuanZeCheXingActivity.DATA);
                    chechangTv.setText(chechangStr);
                    break;
            }

        }
    }
}
