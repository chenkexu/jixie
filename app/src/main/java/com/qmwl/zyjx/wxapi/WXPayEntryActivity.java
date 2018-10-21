package com.qmwl.zyjx.wxapi;


import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.activity.RegisterKaiDianChengGongActivity;
import com.qmwl.zyjx.fragment.WoDeYunDanFragment;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.SharedUtils;
import com.qmwl.zyjx.view.CommomDialog;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay_result);
        api = WXAPIFactory.createWXAPI(this, Contact.wxAppid);
        api.handleIntent(getIntent(), this);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
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
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            boolean wxPayStatue = SharedUtils.getWxPayStatue(this);
            if (wxPayStatue) {
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
                String dialogMessage = "";
                if (baseResp.errCode == 0) {
                    setPayResult(wxPayResult.success);
                    dialogMessage = getString(R.string.zhifuchenggong);
                } else {
                    setPayResult(wxPayResult.fail);
                    dialogMessage = getString(R.string.zhifushibaiqingchongxinzhifu);
                }
                CommomDialog commomDialog = new CommomDialog(WXPayEntryActivity.this, R.style.dialog, dialogMessage, new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        Intent intent = new Intent(WoDeYunDanFragment.UPDATE_DATA);
                        sendBroadcast(intent);
                        finish();
                    }
                }).setHideCancelButton();
                commomDialog.setCanceledOnTouchOutside(false);
                commomDialog.setCancelable(false);
                commomDialog.show();
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
}