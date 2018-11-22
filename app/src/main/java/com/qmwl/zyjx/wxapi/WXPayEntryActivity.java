package com.qmwl.zyjx.wxapi;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.orhanobut.logger.Logger;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.activity.PaySuccessActivity;
import com.qmwl.zyjx.activity.RegisterKaiDianChengGongActivity;
import com.qmwl.zyjx.fragment.WoDeYunDanFragment;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.SharedUtils;
import com.qmwl.zyjx.view.CommomDialog;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;
    private Context mContext;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        api = WXAPIFactory.createWXAPI(this, Contact.wxAppid);
        api.handleIntent(getIntent(), this);
        mContext=this;
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        mContext=this;
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }


    public enum wxPayResult {success, fail, cancle, error}

    private void setPayResult(wxPayResult payResultResult) {
        EventBus.getDefault().post(payResultResult);
//        finish();
    }


    @Override
    public void onResp(BaseResp baseResp) {
        mContext=this;
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

            Logger.d("微信支付返回码:"+baseResp.errCode);
            boolean wxPayStatue = SharedUtils.getWxPayStatue(this);
            if (wxPayStatue) {
                Logger.d("微信支付返回码000");
                //关闭这个状态
                SharedUtils.putWxPayStatue(this, false);
                Intent intent = null;
                if (baseResp.errCode == 0) {
                    setPayResult(wxPayResult.success);
                    intent = new Intent(this, RegisterKaiDianChengGongActivity.class);
                    startActivity(intent);
                } else {
                    setPayResult(wxPayResult.fail);
                    new CommomDialog(this, R.style.dialog, getString(R.string.zhifushibaiqingchongxinzhifu), new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
//                            Intent intent1 = new Intent(WXPayEntryActivity.this, MainActivity.class);
//                            startActivity(intent1);
                            finish();
                        }
                    }).setHideCancelButton().show();
                }
            } else {
                Logger.d("微信支付返回码1111:" );
                String dialogMessage = "";
                final String out_trade_no=SharedUtils.getString("ChargePopWindowOrderId", mContext);
                Logger.d("微信支付返回码1111:"+out_trade_no);
                if (baseResp.errCode == 0) {
                   /* setPayResult(wxPayResult.success);
                    dialogMessage = getString(R.string.zhifuchenggong);
                    CommomDialog commomDialog = new CommomDialog(WXPayEntryActivity.this, R.style.dialog, dialogMessage, new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {

                        }
                    }).setHideCancelButton();
                    commomDialog.setCanceledOnTouchOutside(false);
                    commomDialog.setCancelable(false);
                    commomDialog.show();*/

                    Intent intent = new Intent(WoDeYunDanFragment.UPDATE_DATA);
                    sendBroadcast(intent);
                    Intent intent2=new Intent(mContext, PaySuccessActivity.class);
                    intent2.putExtra("out_trade_no",out_trade_no);
                    startActivity(intent2);
                    finish();
                } else {
                    setPayResult(wxPayResult.fail);
                    new CommomDialog(this, R.style.dialog, getString(R.string.pay_fail), new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {

                            if (confirm) {
                                payWx(out_trade_no);
                            }
                            dialog.dismiss();
                            finish();
                        }
                    }).setTitle( getString(R.string.tishi)).setNegativeButton("取消").setPositiveButton("重新支付").show();

                 //   dialogMessage = getString(R.string.zhifushibaiqingchongxinzhifu);
                }

        }
        }

//        switch (baseResp.errCode) {
//            case 0:
////                getCommission();
//                setPayResult(wxPayResult.success);
//                break;
//            case -1:
//                setPayResult(wxPayResult.fail);
//                break;
//            case -2:
//                setPayResult(wxPayResult.cancle);
//                break;
//            default:
//                break;
//        }
    }



//    @Override
//    public void onResp(BaseResp resp) {
//        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
//
//            boolean wxPayStatue = SharedUtils.getWxPayStatue(this);
//            if (wxPayStatue) {
//                //关闭这个状态
//                SharedUtils.putWxPayStatue(this, false);
//                Intent intent = null;
//                if (resp.errCode == 0) {
//                    intent = new Intent(this, RegisterKaiDianChengGongActivity.class);
//                    startActivity(intent);
//                } else {
//                    new CommomDialog(this, R.style.dialog, getString(R.string.zhifushibaiqingchongxinzhifu), new CommomDialog.OnCloseListener() {
//                        @Override
//                        public void onClick(Dialog dialog, boolean confirm) {
////                            Intent intent1 = new Intent(WXPayEntryActivity.this, MainActivity.class);
////                            startActivity(intent1);
//                            finish();
//                        }
//                    }).setHideCancelButton().show();
//                }
//            } else {
//                String dialogMessage = "";
//                if (resp.errCode == 0) {
//                    dialogMessage = getString(R.string.zhifuchenggong);
//                } else {
//                    dialogMessage = getString(R.string.zhifushibaiqingchongxinzhifu);
//                }
//                CommomDialog commomDialog = new CommomDialog(WXPayEntryActivity.this, R.style.dialog, dialogMessage, new CommomDialog.OnCloseListener() {
//                    @Override
//                    public void onClick(Dialog dialog, boolean confirm) {
//                        Intent intent = new Intent(WoDeYunDanFragment.UPDATE_DATA);
//                        sendBroadcast(intent);
//                        finish();
//                    }
//                }).setHideCancelButton();
//                commomDialog.setCanceledOnTouchOutside(false);
//                commomDialog.setCancelable(false);
//                commomDialog.show();
//            }
//        }
//    }



    //微信支付
    private void payWx(String out_trade_no) {

        //临时id 微信支付失败重新支付使用
        boolean isCarShoppForm=SharedUtils.getBoolean("ChargePopWindowisCarShoppForm", mContext);
        boolean zulintype=SharedUtils.getBoolean("ChargePopWindowzulintype", mContext);
        String price= SharedUtils.getString("ChargePopWindowtotal_amount", mContext);
        String goodsName=SharedUtils.getString("ChargePopWindowgoods_name", mContext);
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
                        Logger.d(response);
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
                            api.sendReq(request);

                        } catch (JSONException e) {
                            e.printStackTrace();
//                            Toast.makeText(ZaiXianZhiFuActivity.this, "微信返回值出错", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
//                        Toast.makeText(ZaiXianZhiFuActivity.this, "微信请求出错", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}