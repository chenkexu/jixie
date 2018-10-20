package com.qmwl.zyjx.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.FlowFragmentAdapter;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.DingDanBean;
import com.qmwl.zyjx.fragment.DingDanFragment;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;

import org.json.JSONObject;

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
    private DingDanFragment dingDanFragment1;
    private DingDanFragment dingDanFragment2;
    private DingDanFragment dingDanFragment3;
    private DingDanFragment dingDanFragment4;
    private DingDanFragment dingDanFragment5;

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

        radiobutton1.setText(R.string.all);
        radiobutton2.setText(R.string.daifukuan);
        radiobutton3.setText(R.string.daifahuo);
        radiobutton4.setText(R.string.daishouhuo);
        radiobutton5.setText(R.string.yiwancheng);

        radiobutton1.setOnClickListener(this);
        radiobutton2.setOnClickListener(this);
        radiobutton3.setOnClickListener(this);
        radiobutton4.setOnClickListener(this);
        radiobutton5.setOnClickListener(this);


        List<Fragment> list = new ArrayList<>();
        dingDanFragment1 = new DingDanFragment();
        dingDanFragment2 = new DingDanFragment();
        dingDanFragment3 = new DingDanFragment();
        dingDanFragment4 = new DingDanFragment();
        dingDanFragment5 = new DingDanFragment();
        list.add(dingDanFragment1);
        list.add(dingDanFragment2);
        list.add(dingDanFragment3);
        list.add(dingDanFragment4);
        list.add(dingDanFragment5);

        FlowFragmentAdapter adapter = new FlowFragmentAdapter(getSupportFragmentManager(), list);
        mVp.setAdapter(adapter);
        mVp.setOffscreenPageLimit(5);
        mVp.addOnPageChangeListener(this);
//        radioGroup.check(R.id.zulin_layout_radiobutton_b1);
        int index = getIntent().getIntExtra(WODEDINGDAN_INDEX_VALUE, 0);
        mVp.setCurrentItem(index);
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
    }

    @Override
    protected void getInterNetData() {
        getData();
    }



    public void getData() {
        AndroidNetworking.get(Contact.wodedingdan_list + "?uid=" + MyApplication.getIntance().userBean.getUid())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        cancelFragmentRefersh();
                        dismissLoadingDialog();
                        final List<DingDanBean> dingDanBeen = JsonUtils.parseDingDan(response);
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                final List<DingDanBean> list1 = new ArrayList<DingDanBean>();
                                final List<DingDanBean> list2 = new ArrayList<DingDanBean>();
                                final List<DingDanBean> list3 = new ArrayList<DingDanBean>();
                                final List<DingDanBean> list4 = new ArrayList<DingDanBean>();
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
                                    }
                                }
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        dingDanFragment2.setData(list1);
                                        dingDanFragment3.setData(list2);
                                        dingDanFragment4.setData(list3);
                                        dingDanFragment5.setData(list4);
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





}
