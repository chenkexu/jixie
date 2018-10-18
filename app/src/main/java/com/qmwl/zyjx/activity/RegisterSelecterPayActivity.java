package com.qmwl.zyjx.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.fragment.WoDeYunDanFragment;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.utils.PayResult;
import com.qmwl.zyjx.utils.SharedUtils;
import com.qmwl.zyjx.view.CommomDialog;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by 郭辉 on 2018/1/17 17:43.
 * TODO:申请开店选择支付页面
 */

public class RegisterSelecterPayActivity extends BaseActivity {
    private String price = "";
    private String apply_id = "";
    private static final int SDK_PAY_FLAG = 1;
    private RadioButton duigongButton;
    private RadioButton zhifubaoButton;
    private RadioButton weixinButton;
    private RadioButton waihuiButton;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            @SuppressWarnings("unchecked")
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为9000则代表支付成功
            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterSelecterPayActivity.this);
            String dialogMessage = "";
            if (TextUtils.equals(resultStatus, "9000")) {
                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
//                builder.setMessage("支付成功");
//                dialogMessage = getString(R.string.zhifuchenggong);
                Intent intent = new Intent(RegisterSelecterPayActivity.this, RegisterKaiDianChengGongActivity.class);
                startActivity(intent);

            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                dialogMessage = getString(R.string.zhifushibaiqingchongxinzhifu);
                builder.setMessage("支付失败,请重新支付" + resultInfo);
                CommomDialog commomDialog = new CommomDialog(RegisterSelecterPayActivity.this, R.style.dialog, dialogMessage, new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
//                        Intent intent = new Intent(WoDeYunDanFragment.UPDATE_DATA);
//                        sendBroadcast(intent);
                        finish();
                    }
                }).setHideCancelButton();
                commomDialog.setCanceledOnTouchOutside(false);
                commomDialog.setCancelable(false);
                commomDialog.show();
            }

        }
    };
    private TextView priceTv;


    @Override
    protected void setLayout() {
        setContentLayout(R.layout.activity_layout_register_selecter_pay);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.woyaokaidian);
        priceTv = (TextView) findViewById(R.id.id_register_selecter_pay_price);
        duigongButton = (RadioButton) findViewById(R.id.id_register_selecter_pay_duigong);
        zhifubaoButton = (RadioButton) findViewById(R.id.id_register_selecter_pay_zhifubao);
        weixinButton = (RadioButton) findViewById(R.id.id_register_selecter_pay_wexin);
        waihuiButton = (RadioButton) findViewById(R.id.id_register_selecter_pay_waihui);
        duigongButton.setOnClickListener(this);
        zhifubaoButton.setOnClickListener(this);
        weixinButton.setOnClickListener(this);
        waihuiButton.setOnClickListener(this);
        findViewById(R.id.id_register_selecter_pay_duigong_container).setOnClickListener(this);
        findViewById(R.id.id_register_selecter_pay_zhifubao_container).setOnClickListener(this);
        findViewById(R.id.id_register_selecter_pay_wexin_container).setOnClickListener(this);
        findViewById(R.id.id_register_selecter_pay_waihui_container).setOnClickListener(this);
        findViewById(R.id.id_register_selecter_pay_next).setOnClickListener(this);
        getPramas();
        initWx();
    }

    private IWXAPI wxapi;

    private void initWx() {
        wxapi = WXAPIFactory.createWXAPI(this, Contact.wxAppid);
    }


    private void getPramas() {
        Intent intent = getIntent();
        price = intent.getStringExtra("price");
        apply_id = intent.getStringExtra("apply_id");
        priceTv.setText(price);
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
            case R.id.id_register_selecter_pay_duigong_container:
            case R.id.id_register_selecter_pay_duigong:
                duigongButton.setChecked(true);
                zhifubaoButton.setChecked(false);
                weixinButton.setChecked(false);
                waihuiButton.setChecked(false);
                break;
            case R.id.id_register_selecter_pay_zhifubao_container:
            case R.id.id_register_selecter_pay_zhifubao:
                duigongButton.setChecked(false);
                zhifubaoButton.setChecked(true);
                weixinButton.setChecked(false);
                waihuiButton.setChecked(false);
                break;
            case R.id.id_register_selecter_pay_wexin_container:
            case R.id.id_register_selecter_pay_wexin:
                duigongButton.setChecked(false);
                zhifubaoButton.setChecked(false);
                weixinButton.setChecked(true);
                waihuiButton.setChecked(false);
                break;
            case R.id.id_register_selecter_pay_waihui_container:
            case R.id.id_register_selecter_pay_waihui:
                duigongButton.setChecked(false);
                zhifubaoButton.setChecked(false);
                weixinButton.setChecked(false);
                waihuiButton.setChecked(true);
                break;
            case R.id.id_register_selecter_pay_next:
                Intent intent = null;
                if (duigongButton.isChecked()) {
                    intent = new Intent(this, RegisterDuiGongActivity.class);
                    intent.putExtra("apply_id", apply_id);
                    startActivity(intent);
                } else if (zhifubaoButton.isChecked()) {
//                    intent = new Intent(this, RegisterDuiGongActivity.class);
//                    startActivity(intent);
                    postZhiFubao();
                } else if (weixinButton.isChecked()) {
//                    intent = new Intent(this, RegisterDuiGongActivity.class);
//                    startActivity(intent);
                    postWx();
                } else if (waihuiButton.isChecked()) {
                    //外汇
                    intent = new Intent(this, RegisterDuiGongActivity.class);
                    intent.putExtra("apply_id", apply_id);
                    intent.putExtra("iswaihui", true);
                    startActivity(intent);

                } else {
                    Toast.makeText(this, getString(R.string.qingxuanzezhifufangshi), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //发起微信参数请求
    private void postWx() {

        showLoadingDialog();
        AndroidNetworking.post(Contact.woyaokaidianweixin)
                .addBodyParameter("apply_id", apply_id)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        SharedUtils.putWxPayStatue(RegisterSelecterPayActivity.this, true);
                        dismissLoadingDialog();
                        dismissLoadingDialog();
                        try {
                            JSONObject object = response.getJSONObject("x");
                            PayReq request = new PayReq();
                            request.appId = object.getString("appid");
                            request.partnerId = object.getString("partnerid");
                            request.prepayId = object.getString("prepayid");
                            request.packageValue = object.getString("package");
                            request.nonceStr = object.getString("noncestr");
                            request.timeStamp = object.getString("timestamp");
                            request.sign = object.getString("sign");

                            wxapi.sendReq(request);
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
//                            Toast.makeText(ZaiXianZhiFuActivity.this, "微信返回值出错", Toast.LENGTH_SHORT).show();
                        }
                    }


                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                    }
                });

    }


    //发起支付宝参数请求
    private void postZhiFubao() {
        showLoadingDialog();
        AndroidNetworking.post(Contact.woyaokaidianzhifubao)
                .addBodyParameter("apply_id", apply_id)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        //真正发起支付宝支付
                        try {
                            if (JsonUtils.isSuccess(response)) {
                                JSONObject data = response.getJSONObject("data");
                                String niu_index_response = data.getString("niu_index_response");
                                payZhifubao(niu_index_response);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                    }
                });
    }


    private void payZhifubao(final String orderStr) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                PayTask alipay = new PayTask(RegisterSelecterPayActivity.this);
                Map<String, String> result = alipay.payV2(orderStr, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        }.start();
    }


}
