package com.qmwl.zyjx.activity;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

import com.orhanobut.logger.Logger;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.FlowFragmentAdapter;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.fragment.ZuLinFragment;
import com.qmwl.zyjx.utils.ToastUtils;
import com.qmwl.zyjx.wxapi.WXPayEntryActivity;
import com.qmwl.zyjx.zfb.PayBean;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/25.
 */

public class WoDeZuLingActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private ViewPager mVp;
    private RadioGroup radioGroup;
    private View radiobutton1;
    private View radiobutton2;
    private View radiobutton3;
    private View radiobutton4;
    private View radiobutton5;


    @Override
    protected void setLayout() {
        setContentLayout(R.layout.myzuling_activity_layout);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.wodezuling);
        mVp = (ViewPager) findViewById(R.id.zulin_layout_viewpager);
        radioGroup = (RadioGroup) findViewById(R.id.zulin_layout_radiogroup);
        radiobutton1 = findViewById(R.id.zulin_layout_radiobutton_b1);
        radiobutton2 = findViewById(R.id.zulin_layout_radiobutton_b2);
        radiobutton3 = findViewById(R.id.zulin_layout_radiobutton_b3);
        radiobutton4 = findViewById(R.id.zulin_layout_radiobutton_b4);
        radiobutton5 = findViewById(R.id.zulin_layout_radiobutton_b5);
        radiobutton5.setBackground(null);
        radiobutton1.setOnClickListener(this);
        radiobutton2.setOnClickListener(this);
        radiobutton3.setOnClickListener(this);
        radiobutton4.setOnClickListener(this);


        List<Fragment> list = new ArrayList<>();
        list.add(new ZuLinFragment(0));
        list.add(new ZuLinFragment(1));
        list.add(new ZuLinFragment(2));
        list.add(new ZuLinFragment(3));
        FlowFragmentAdapter adapter = new FlowFragmentAdapter(getSupportFragmentManager(), list);
        mVp.setAdapter(adapter);
        mVp.addOnPageChangeListener(this);
        mVp.setOffscreenPageLimit(4);
        radioGroup.check(R.id.zulin_layout_radiobutton_b1);
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
                break;

        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

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
                ToastUtils.showShort("支付错误");
                break;
            case error:
                ToastUtils.showShort("支付错误");
                break;
            default:
                break;
        }
    }


    @Subscribe
    public void onZhifubaoCharge(PayBean payBean){
        Logger.d("收到支付宝支付成功的回调");
        //支付宝支付成功
//        submit();
    }

}
