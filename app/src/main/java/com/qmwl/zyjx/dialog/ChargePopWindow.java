package com.qmwl.zyjx.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.chinapay.mobilepayment.activity.MainActivity;
import com.chinapay.mobilepayment.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.orhanobut.logger.Logger;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.activity.DuiGongFuKuanActivity;
import com.qmwl.zyjx.activity.OrderCancelActivity;
import com.qmwl.zyjx.activity.PaySuccessActivity;
import com.qmwl.zyjx.api.ApiManager;
import com.qmwl.zyjx.api.ApiResponse;
import com.qmwl.zyjx.api.BaseObserver;
import com.qmwl.zyjx.base.Constant;
import com.qmwl.zyjx.bean.ChinaPayOrder;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.utils.RxUtil;
import com.qmwl.zyjx.utils.SharedUtils;
import com.qmwl.zyjx.utils.ToastUtils;
import com.qmwl.zyjx.view.CommomDialog;
import com.qmwl.zyjx.wxapi.WXPayEntryActivity;
import com.qmwl.zyjx.zfb.Alipay;
import com.qmwl.zyjx.zfb.PayBean;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by ckx on 2018/6/27.
 */

public class ChargePopWindow extends Dialog implements View.OnClickListener {


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
    private String out_trade_no; //订单号 order_id
    private String price;
    private String goodsName;
    private IWXAPI wxapi;

    private static final int ZHIFUBAO = 1;

    private Context context;
    private Double value;

    //订单号
    private String order_no;

    public String getOrder_no() {
        return order_no == null ? "" : order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }



    //    Intent intent = getIntent();
//    out_trade_no = intent.getStringExtra(OUT_TRADE_NO_DATA);
//    price = intent.getStringExtra(PRICE_DATA);
//    goodsName = intent.getStringExtra(GOODSNAME_DATA);
//    isCarShoppForm = intent.getBooleanExtra(IS_CAR_SHOPIING, false);
//    zulintype = intent.getBooleanExtra(PAY_TYPE, false);
//

    public ChargePopWindow(boolean isCarShoppForm,final Context context,boolean zulintype,String out_trade_no,String price,String goodsName) {
        super(context, R.style.RemindDialog);// 必须调用父类的构造函数
        Logger.d("订单号:"+ out_trade_no +"------"+ price + "元"+ "商品名称: "+goodsName);
        this.context = context;

        this.isCarShoppForm = isCarShoppForm;
        this.zulintype = zulintype;
        this.out_trade_no = out_trade_no;
        this.price = price;
        this.goodsName = goodsName;
        Logger.d( "是否从购物车过来"+isCarShoppForm);

        SharedUtils.putBoolean("ChargePopWindowisCarShoppForm", isCarShoppForm,getContext());


        View contentView = LayoutInflater.from(context).inflate(R.layout.selecter_pay_type_layout, null);
        setContentView(contentView);
        ButterKnife.bind(this, contentView);

        Window window = getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        Display d = window.getWindowManager().getDefaultDisplay();
        //获取屏幕宽
        wlp.width = (int) (d.getWidth());
        getWindow().setGravity(Gravity.BOTTOM);
        getWindow().setWindowAnimations(R.style.take_photo_anim);
        window.setAttributes(wlp);
        wxapi = WXAPIFactory.createWXAPI(context, Contact.wxAppid);
    }


//    private RechargeDialog rechargeDialog;

    //微信支付的回调
    //在ui线程执行
    @Subscribe
    public void onEventMainThread(WXPayEntryActivity.wxPayResult payResultResult) {
        Logger.d("收到了微信支付的回调" + payResultResult);
        if (this == null)
            return;
        switch (payResultResult) {
            case success:

                Toast.makeText(context, "成功", Toast.LENGTH_SHORT).show();
                getContext().startActivity(new Intent(getContext(), PaySuccessActivity.class));
                Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();

                break;
            case cancle:
                Toast.makeText(context, "取消支付", Toast.LENGTH_SHORT).show();
                break;
            case fail:
                //弹出充值失败的dialog
                // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                Toast.makeText(context, "支付失败", Toast.LENGTH_SHORT).show();
                new CommomDialog(context, R.style.dialog, context.getString(R.string.pay_fail), new CommomDialog.OnCloseListener() {
                    @Override
                    public void onClick(Dialog dialog, boolean confirm) {
                        if (confirm) {
                            payWx();
                        }
                        dialog.dismiss();
                    }
                }).setTitle(context.getString(R.string.tishi)).setNegativeButton("取消").setPositiveButton("重新支付").show();

                break;
            case error:
                Toast.makeText(context, "未知", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }


    @Override
    public void onClick(View v) {

    }



    @OnClick({R.id.st_aplipay, R.id.st_wechat, R.id.st_yilian, R.id.st_zhuanzhang, R.id.st_small_daikuan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.st_aplipay: //支付宝
                payZhiFubao();
                break;
            case R.id.st_wechat: //微信
                payWx();
                break;
            case R.id.st_yilian: //银联
                boolean isInstalled = Utils.checkInstalled(context);
//                if (!isInstalled) {
//                    ToastUtils.showShort("unionPay sdk is not Installed");
//                    return;
//                }
                yinlianPay();
                break;
            case R.id.st_zhuanzhang: //对公付款
                Intent intent = new Intent(context, DuiGongFuKuanActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra(Constant.order_id, out_trade_no);
                context.startActivity(intent);
                break;
            case R.id.st_small_daikuan: //小额贷款
                ToastUtils.showLong(context.getString(R.string.No_opening));
                break;
        }
        dismiss();
    }


    //微信支付
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
                            //保存临时id 微信支付失败重新支付使用
                            SharedUtils.putString("ChargePopWindowOrderId", out_trade_no, getContext());
                            SharedUtils.putBoolean("ChargePopWindowisCarShoppForm", isCarShoppForm, getContext());
                            SharedUtils.putBoolean("ChargePopWindowzulintype", zulintype, getContext());
                            SharedUtils.putString("ChargePopWindowtotal_amount", price, getContext());
                            SharedUtils.putString("ChargePopWindowgoods_name", goodsName, getContext());
                            wxapi.sendReq(request);

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









    //支付宝支付
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
                        String payInfo = parseOrderInfo(response);
                        if ("".equals(payInfo) || TextUtils.isEmpty(payInfo)) {
                            return;
                        }
//                        payZhifubao(s);

                        new Alipay(context, payInfo, new Alipay.AlipayResultCallBack() {
                            @Override
                            public void onSuccess() {
                                PayBean payBean = new PayBean();
                                EventBus.getDefault().post(payBean);
                                Log.d("huangrui","支付成功");
                                Intent intent2=new Intent(getContext(), PaySuccessActivity.class);
                                intent2.putExtra("out_trade_no",out_trade_no);
                                getContext().startActivity(intent2);


                                Toast.makeText(context, "支付成功", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onDealing() {
                                Toast.makeText(context, "支付处理中...", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onError(int error_code) {
                                Toast.makeText(context, "支付错误", Toast.LENGTH_SHORT).show();
                                new CommomDialog(context, R.style.dialog, context.getString(R.string.pay_fail), new CommomDialog.OnCloseListener() {
                                    @Override
                                    public void onClick(Dialog dialog, boolean confirm) {
                                        if (confirm) {
                                            payZhiFubao();
                                        }
                                        dialog.dismiss();
                                    }
                                }).setTitle(context.getString(R.string.tishi)).setNegativeButton("取消").setPositiveButton("重新支付").show();
                            }

                            @Override
                            public void onCancel() {
                                Toast.makeText(context, "支付取消", Toast.LENGTH_SHORT).show();
                                new CommomDialog(context, R.style.dialog, context.getString(R.string.pay_fail), new CommomDialog.OnCloseListener() {
                                    @Override
                                    public void onClick(Dialog dialog, boolean confirm) {
                                        if (confirm) {
                                            payZhiFubao();
                                        }
                                        dialog.dismiss();
                                    }
                                }).setTitle(context.getString(R.string.tishi)).setNegativeButton("取消").setPositiveButton("重新支付").show();
                            }
                        }).doPay();
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }



//    private void payZhifubao(final String orderStr) {
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                PayTask alipay = new PayTask(ZaiXianZhiFuActivity.this);
//                Map<String, String> result = alipay.payV2(orderStr, true);
//                Message msg = new Message();
//                msg.what = SDK_PAY_FLAG;
//                msg.obj = result;
//                mHandler.sendMessage(msg);
//            }
//        }.start();
//    }



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


    private void yinlianPay(){

        if (isCarShoppForm) {
            //保存临时id 微信支付失败重新支付使用
            SharedUtils.putString("ChargePopWindowYlOrderId", out_trade_no, getContext());
            ApiManager.getInstence().getApiService()
                    .getChinaPayInfo(out_trade_no,price)
                    .compose(RxUtil.<ApiResponse<ChinaPayOrder>>rxSchedulerHelper())
                    .subscribe(new BaseObserver<ChinaPayOrder>() {
                        @Override
                        protected void onSuccees(ApiResponse<ChinaPayOrder> t) {

                            ChinaPayOrder chinaPayOrder = t.getData();
                            ChinaPayOrder.NiuIndexResponseBean responseBean = chinaPayOrder.getNiu_index_response();

                            Gson gson = new GsonBuilder()
                                    .serializeNulls()
                                    .create();
                            String json1 = gson.toJson(responseBean);

                            Log.d("huangrui","银联"+json1);
                            // 初始化手机POS环境
                            Utils.setPackageName("com.qmwl.zyjx");//MY_PKG是你项目的包名
                            // 设置Intent指向Initialize.class
                            Intent intent = new Intent(context, MainActivity.class);
                            // this为你当前的activity.this
                            // 传入对象参数
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("orderInfo", json1);
                            intent.putExtras(bundle);
                            intent.putExtra("mode", "00");
                            // orderInfo为启动插件时传入的OrderInfo对象。
                            // 使用intent跳转至移动认证插件
                            context.startActivity(intent);
                        }

                        @Override
                        protected void onFailure(String errorInfo, boolean isNetWorkError) {
                            ToastUtils.showShort(errorInfo);
                        }
                    });
        }else {
            //保存临时id 微信支付失败重新支付使用
            SharedUtils.putString("ChargePopWindowYlOrderId", out_trade_no, getContext());
            ApiManager.getInstence().getApiService()
                    .getChinaPayInfo(out_trade_no)
                    .compose(RxUtil.<ApiResponse<ChinaPayOrder>>rxSchedulerHelper())
                    .subscribe(new BaseObserver<ChinaPayOrder>() {
                        @Override
                        protected void onSuccees(ApiResponse<ChinaPayOrder> t) {

                            ChinaPayOrder chinaPayOrder = t.getData();
                            ChinaPayOrder.NiuIndexResponseBean responseBean = chinaPayOrder.getNiu_index_response();

                            Gson gson = new GsonBuilder()
                                    .serializeNulls()
                                    .create();
                            String json1 = gson.toJson(responseBean);

                            Log.d("huangrui","银联"+json1);
                            // 初始化手机POS环境
                            Utils.setPackageName("com.qmwl.zyjx");//MY_PKG是你项目的包名
                            // 设置Intent指向Initialize.class
                            Intent intent = new Intent(context, MainActivity.class);
                            // this为你当前的activity.this
                            // 传入对象参数
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("orderInfo", json1);
                            intent.putExtras(bundle);
                            intent.putExtra("mode", "00");
                            // orderInfo为启动插件时传入的OrderInfo对象。
                            // 使用intent跳转至移动认证插件
                            context.startActivity(intent);
                        }

                        @Override
                        protected void onFailure(String errorInfo, boolean isNetWorkError) {
                            ToastUtils.showShort(errorInfo);
                        }
                    });
        }



    }


    @Subscribe
    public void onWeChatCharge(WXPayEntryActivity.wxPayResult payResultResult) {
        Logger.d("onWeChatCharge收到了微信支付的回调"+payResultResult);
        if (this == null)
            return;
        switch (payResultResult) {
            case success:
//                submit();
                ToastUtils.showShort("支付成功");
                break;
            case cancle:
                ToastUtils.showShort("取消支付");
                break;
            case fail:
                ToastUtils.showShort("支付错误");
                break;
            case error:
                ToastUtils.showShort("支付错误");
                break;
            default:
                break;
        }
    }
}
