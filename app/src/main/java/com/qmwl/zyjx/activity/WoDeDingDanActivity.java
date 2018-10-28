package com.qmwl.zyjx.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.chinapay.mobilepayment.global.CPGlobalInfo;
import com.chinapay.mobilepayment.global.ResultInfo;
import com.chinapay.mobilepayment.utils.Utils;
import com.orhanobut.logger.Logger;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.FlowFragmentAdapter;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.DingDanBean;
import com.qmwl.zyjx.fragment.DingDanFragment;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.utils.ToastUtils;
import com.qmwl.zyjx.wxapi.WXPayEntryActivity;
import com.qmwl.zyjx.zfb.PayBean;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONObject;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/7/25.
 * 我的订单
 */

public class WoDeDingDanActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    public static final String WODEDINGDAN_INDEX_VALUE = "com.gh.wodedingdan_value";
    public static final String WODEDINGDAN_BROADCAST_STRING = "com.gh.wodedingdan.receiver";

    private ViewPager mVp;
    private RadioGroup radioGroup;
    private RadioButton radiobutton1;
    private RadioButton radiobutton2;
    private RadioButton radiobutton3;
    private RadioButton radiobutton4;
    private RadioButton radiobutton5;
    private RadioButton radiobutton6;
    private RadioButton radiobutton7;
    private DingDanFragment dingDanFragment1;
    private DingDanFragment dingDanFragment2;
    private DingDanFragment dingDanFragment3;
    private DingDanFragment dingDanFragment4;
    private DingDanFragment dingDanFragment5;
    private DingDanFragment dingDanFragment6;
    private DingDanFragment dingDanFragment7;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.myzuling_activity_layout);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.wodedingdan);
//        setRightImageView(R.mipmap.search);
//        setRightImageView2(R.mipmap.message);
        mVp = (ViewPager) findViewById(R.id.zulin_layout_viewpager);
        radioGroup = (RadioGroup) findViewById(R.id.zulin_layout_radiogroup);
        radiobutton1 = (RadioButton) findViewById(R.id.zulin_layout_radiobutton_b1);
        radiobutton2 = (RadioButton) findViewById(R.id.zulin_layout_radiobutton_b2);
        radiobutton3 = (RadioButton) findViewById(R.id.zulin_layout_radiobutton_b3);
        radiobutton4 = (RadioButton) findViewById(R.id.zulin_layout_radiobutton_b4);
        radiobutton5 = (RadioButton) findViewById(R.id.zulin_layout_radiobutton_b5);
        radiobutton6 = (RadioButton) findViewById(R.id.zulin_layout_radiobutton_b6);
        radiobutton7 = (RadioButton) findViewById(R.id.zulin_layout_radiobutton_b7);

        radiobutton1.setText(R.string.all);
        radiobutton2.setText(R.string.daifukuan);
        radiobutton3.setText(R.string.daifahuo);
        radiobutton4.setText(R.string.daishouhuo);
        radiobutton5.setText(R.string.yiwancheng);
        radiobutton6.setText(R.string.refund_thing);
        radiobutton7.setText(R.string.yichang);

        radiobutton1.setOnClickListener(this);
        radiobutton2.setOnClickListener(this);
        radiobutton3.setOnClickListener(this);
        radiobutton4.setOnClickListener(this);
        radiobutton5.setOnClickListener(this);
        radiobutton6.setOnClickListener(this);
        radiobutton7.setOnClickListener(this);


        List<Fragment> list = new ArrayList<>();
        dingDanFragment1 = new DingDanFragment();
        dingDanFragment2 = new DingDanFragment();
        dingDanFragment3 = new DingDanFragment();
        dingDanFragment4 = new DingDanFragment();
        dingDanFragment5 = new DingDanFragment();
        dingDanFragment6 = new DingDanFragment();
        dingDanFragment7 = new DingDanFragment();
        list.add(dingDanFragment1);
        list.add(dingDanFragment2);
        list.add(dingDanFragment3);
        list.add(dingDanFragment4);
        list.add(dingDanFragment5);
        list.add(dingDanFragment6);
        list.add(dingDanFragment7);

        FlowFragmentAdapter adapter = new FlowFragmentAdapter(getSupportFragmentManager(), list);
        mVp.setAdapter(adapter);
        mVp.setOffscreenPageLimit(7);
        mVp.addOnPageChangeListener(this);
//        radioGroup.check(R.id.zulin_layout_radiobutton_b1);

         int tuiSongIndex = getIntent().getIntExtra("index",0);
         if (tuiSongIndex!=0){
             int index = tuiSongIndex;
             mVp.setCurrentItem(index);
         }else{
             int index = getIntent().getIntExtra(WODEDINGDAN_INDEX_VALUE, 0);
             mVp.setCurrentItem(index);
         }


    }

    @Override
    protected void onListener() {
    }

    private void cancelFragmentRefersh() {
        dingDanFragment1.cancelRefresh();
        dingDanFragment2.cancelRefresh();
        dingDanFragment3.cancelRefresh();
        dingDanFragment4.cancelRefresh();
        dingDanFragment5.cancelRefresh();
        dingDanFragment6.cancelRefresh();
        dingDanFragment7.cancelRefresh();
    }

    @Override
    protected void getInterNetData() {
        getData();
    }



    public void getData() {
        Log.d("huangrui", "userId"+MyApplication.getIntance().userBean.getUid());
        AndroidNetworking.get(Contact.wodedingdan_list + "?uid=" + MyApplication.getIntance().userBean.getUid())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        cancelFragmentRefersh();
                        dismissLoadingDialog();
                        final List<DingDanBean> dingDanBeen = JsonUtils.parseDingDan(response);
                        Log.d("huangrui","dingDanBeen:"+dingDanBeen);
                        com.orhanobut.logger.Logger.json(response.toString());
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                final List<DingDanBean> list1 = new ArrayList<DingDanBean>();
                                final List<DingDanBean> list2 = new ArrayList<DingDanBean>();
                                final List<DingDanBean> list3 = new ArrayList<DingDanBean>();
                                final List<DingDanBean> list4 = new ArrayList<DingDanBean>();
                                final List<DingDanBean> list5 = new ArrayList<DingDanBean>();
                                final List<DingDanBean> list6 = new ArrayList<DingDanBean>();
                                for (DingDanBean bean : dingDanBeen) {
                                    switch (bean.getDingdan_statue_code()) {
                                        case 0:
                                            list1.add(bean);
                                            break;
                                        case 1:
                                            list2.add(bean);
                                            break;
                                        case 2:
                                            list3.add(bean);
                                            break;
                                        case 3:
                                            list4.add(bean);
                                            break;
                                        case 4:
                                            list4.add(bean);
                                            break;
                                        case -1:
                                            list5.add(bean);
                                            break;
                                        case 6:
                                            list6.add(bean);
                                            break;
                                    }
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        dingDanFragment2.setData(list1);
                                        dingDanFragment3.setData(list2);
                                        dingDanFragment4.setData(list3);
                                        dingDanFragment5.setData(list4);
                                        dingDanFragment6.setData(list5);
                                        dingDanFragment7.setData(list6);
                                    }
                                });

                            }
                        }.start();
                        dingDanFragment1.setData(dingDanBeen);
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                        cancelFragmentRefersh();
                    }
                });
        showLoadingDialog();
    }

    @Override
    protected void onMyClick(View v) {
        switch (v.getId()) {
            case R.id.zulin_layout_radiobutton_b1:
                mVp.setCurrentItem(0);
                break;
            case R.id.zulin_layout_radiobutton_b2:
                mVp.setCurrentItem(1);
                break;

            case R.id.zulin_layout_radiobutton_b3:
                mVp.setCurrentItem(2);
                break;

            case R.id.zulin_layout_radiobutton_b4:
                mVp.setCurrentItem(3);
                break;

            case R.id.zulin_layout_radiobutton_b5:
                mVp.setCurrentItem(4);
                break;

            case R.id.zulin_layout_radiobutton_b6:
                mVp.setCurrentItem(5);
                break;


            case R.id.zulin_layout_radiobutton_b7:
                mVp.setCurrentItem(6);
                break;

        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }



    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                radioGroup.check(R.id.zulin_layout_radiobutton_b1);
//                radiobutton1.setSelected(true);
                break;
            case 1:
                radioGroup.check(R.id.zulin_layout_radiobutton_b2);
//                radiobutton2.setSelected(true);
                break;
            case 2:
                radioGroup.check(R.id.zulin_layout_radiobutton_b3);
//                radiobutton3.setSelected(true);
                break;
            case 3:
                radioGroup.check(R.id.zulin_layout_radiobutton_b4);
//                radiobutton4.setSelected(true);
                break;
            case 4:
                radioGroup.check(R.id.zulin_layout_radiobutton_b5);
                break;
            case 5:
                radioGroup.check(R.id.zulin_layout_radiobutton_b6);
                break;
            case 6:
                radioGroup.check(R.id.zulin_layout_radiobutton_b7);
                break;

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private WoDiDingDanBroadcastReceiver receiver;

    @Override
    protected void onResume() {
        super.onResume();
//        receiver = new WoDiDingDanBroadcastReceiver();
//        IntentFilter intentFilter = new IntentFilter(WODEDINGDAN_BROADCAST_STRING);
//        registerReceiver(receiver, intentFilter);

        getInterNetData();


        //银联支付的回调
        if (Utils.getResultInfo() != null) {
            ResultInfo resultInfo = Utils.getResultInfo();
            Logger.d("银联resultInfo:"+resultInfo.orderInfo);
            if (resultInfo.getRespCode() != null && !resultInfo.getRespCode().equals("")) {
                if (resultInfo.getRespCode().equals("0000")) {
                    String orderInfo = resultInfo.getOrderInfo();
//                    if(orderInfo != null){
//                        Utils.showDialogNoFinish(this, "应答码："+resultInfo.getRespCode() + "\n应答描述:" + resultInfo.getRespDesc()+ "\n详细结果：" + orderInfo);}
                } else {
//                    Utils.showDialogNoFinish(this,
//                            "应答码："+resultInfo.getRespCode() + "\n应答描述:" + resultInfo.getRespDesc());
                }

                Logger.d("银联resultInfo"+resultInfo.getRespCode()+resultInfo.getRespDesc());
            }
        }	CPGlobalInfo.init();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }

    class WoDiDingDanBroadcastReceiver extends BroadcastReceiver {


        @Override
        public void onReceive(Context context, Intent intent) {
            getInterNetData();
        }
    }


    public static void refreshData(Context context) {

        Intent intent = new Intent(WODEDINGDAN_BROADCAST_STRING);
        context.sendBroadcast(intent);
    }



    //收到申请退款后刷新
    @Subscriber(tag = "refresh", mode= ThreadMode.MAIN)
    public void getEventMessage(String message) {
        Log.d("huangrui","刷新界面了");
        getData();
    }






    //微信支付的回调
    //在ui线程执行
    @Subscribe
    public void onWeChatCharge(WXPayEntryActivity.wxPayResult payResultResult) {
        Logger.d("收到了微信支付的回调"+payResultResult);
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
                Logger.d("支付错误");
                ToastUtils.showShort("支付错误");
                break;
            case error:
                ToastUtils.showShort("支付错误");
                break;
            default:
                break;
        }
    }


    //支付宝回调
    @Subscribe
    public void onZhifubaoCharge(PayBean payBean){
        Logger.d("收到支付宝支付成功的回调");
        //支付宝支付成功
//        submit();
    }






}
