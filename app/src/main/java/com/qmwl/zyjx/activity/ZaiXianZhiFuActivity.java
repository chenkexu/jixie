package com.qmwl.zyjx.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.utils.PayResult;
import com.qmwl.zyjx.view.CommomDialog;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Administrator on 2017/8/18.
 *
 */





public class ZaiXianZhiFuActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    //是否是购物车过来的,接口不同，要区别对待
    public static final String IS_CAR_SHOPIING = "com.gh.isgouwuche.boolean";
    public static final String OUT_TRADE_NO_DATA = "com.gh.zaixianzhifu.out_trade_no";
    public static final String PRICE_DATA = "com.gh.zaixianzhifu.price";
    public static final String GOODSNAME_DATA = "com.gh.zaixianzhifu.goodsName";
    public static final String PAY_TYPE = "com.gh.zaixianzhifu_type";

    //是否是购物车过来的,接口不同，要区别对待
    private boolean isCarShoppForm = false;
    //是否是租赁
    private boolean zulintype;
    private String out_trade_no;
    private String price;
    private String goodsName;
    private IWXAPI wxapi;







    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            @SuppressWarnings("unchecked")
            PayResult payResult = new PayResult((Map<String, String>) msg.obj);
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息
            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为9000则代表支付成功
            AlertDialog.Builder builder = new AlertDialog.Builder(ZaiXianZhiFuActivity.this);
            String dialogMessage = "";
            if (TextUtils.equals(resultStatus, "9000")) {
                // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                builder.setMessage("支付成功");
                dialogMessage = getString(R.string.zhifuchenggong);
//                Toast.makeText(ZaiXianZhiFuActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
            } else {
                // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                dialogMessage= getString(R.string.zhifushibaiqingchongxinzhifu);
                builder.setMessage("支付失败,请重新支付"+resultInfo);
//                Toast.makeText(ZaiXianZhiFuActivity.this, "支付失败", Toast.LENGTH_SHORT).show();
            }
//            builder.setNegativeButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    Intent intent = new Intent(ZaiXianZhiFuActivity.this, MainActivity.class);
//                    intent.putExtra(MainActivity.MAIN_INDEX, MainActivity.WODE);
//                    startActivity(intent);
//                    finish();
//                }
//            });
//            builder.show();
            CommomDialog commomDialog = new CommomDialog(ZaiXianZhiFuActivity.this, R.style.dialog, dialogMessage, new CommomDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    Intent intent = new Intent(ZaiXianZhiFuActivity.this, MainActivity.class);
                    intent.putExtra(MainActivity.MAIN_INDEX, MainActivity.WODE);
                    startActivity(intent);
                    finish();
                }
            }).setHideCancelButton();
            commomDialog.setCanceledOnTouchOutside(false);
            commomDialog.setCancelable(false);
            commomDialog.show();
        }
    };




    @Override
    protected void setLayout() {
        setContentLayout(R.layout.zaixianzhifu_layout);
    }

    private RadioGroup zaixianRadioGroup;
    @Override
    protected void initView() {

        Intent intent = getIntent();
        out_trade_no = intent.getStringExtra(OUT_TRADE_NO_DATA);
        price = intent.getStringExtra(PRICE_DATA);
        goodsName = intent.getStringExtra(GOODSNAME_DATA);
        isCarShoppForm = intent.getBooleanExtra(IS_CAR_SHOPIING, false);
        zulintype = intent.getBooleanExtra(PAY_TYPE, false);


        setTitleContent(R.string.zaixianzhifu);
        zaixianRadioGroup = (RadioGroup) findViewById(R.id.zaixianzhifu_layout_radiogroup);
        zaixianRadioGroup.setOnCheckedChangeListener(this);
        findViewById(R.id.zaixianzhifu_layout_pay).setOnClickListener(this);
        wxapi = WXAPIFactory.createWXAPI(this, Contact.wxAppid);
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
            case R.id.zaixianzhifu_layout_pay:
                if (isZhiFuBao) {
                    payZhiFubao();
                } else if (isWeiXin) {
                    payWx();
                } else {
                    Toast.makeText(this, getString(R.string.qingxuanzezhifufangshi), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private boolean isZhiFuBao = false;
    private boolean isWeiXin = false;

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.zaixianzhifu_layout_zhifubao:
                isZhiFuBao = true;
                isWeiXin = false;
//                payZhiFubao();
//                payZhifubao("aaa");
                break;
            case R.id.zaixianzhifu_layout_weixin:
                isWeiXin = true;
                isZhiFuBao = false;
//                payWx();
                break;
        }
    }

    private void payWx() {
        String url = "";
        if (isCarShoppForm) {
            url = Contact.weixin_car_qingqiu;
        } else {
            if (zulintype) {
                url = Contact.zulin_weixinqingqiu;
            } else {
                url = Contact.weixinqingqiu;
            }
        }
        AndroidNetworking.post(url)
                .addBodyParameter("order_no", out_trade_no)
                .addBodyParameter("total_amount", price)
                .addBodyParameter("goods_name", goodsName)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
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
                            request.sign = object.getString("sign"
                            );
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
//                        Toast.makeText(ZaiXianZhiFuActivity.this, "微信请求出错", Toast.LENGTH_SHORT).show();
                    }
                });
        showLoadingDialog();
    }

    private void payZhiFubao() {
        String url = "";
            if (isCarShoppForm) {
                url = Contact.zhifubao_car_qingqiu;
            } else {
                if (zulintype) {
                    url = Contact.zulin_zhifubaoqingqiu;
                } else {
                    url = Contact.zhifubaoqingqiu;
                }
            }
            AndroidNetworking.post(url)
                    .addBodyParameter("out_trade_no", out_trade_no)
                    .addBodyParameter("total_amount", price)
                    .addBodyParameter("goods_name", goodsName)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            dismissLoadingDialog();
                            String s = parseOrderInfo(response);
                            if ("".equals(s) || TextUtils.isEmpty(s)) {
                                return;
                            }
                            payZhifubao(s);
                        }

                        @Override
                        public void onError(ANError anError) {
                            dismissLoadingDialog();
                            Toast.makeText(ZaiXianZhiFuActivity.this, getString(R.string.wangluocuowu), Toast.LENGTH_SHORT).show();
                        }
                    });
            showLoadingDialog();
    }

    private void payZhifubao(final String orderStr) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                PayTask alipay = new PayTask(ZaiXianZhiFuActivity.this);
                Map<String, String> result = alipay.payV2(orderStr, true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        }.start();
    }

    String parseOrderInfo(JSONObject response) {
        try {
            if (JsonUtils.isSuccess(response)) {
                JSONObject data = response.getJSONObject("data");
                return data.getString("niu_index_response");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }
}
