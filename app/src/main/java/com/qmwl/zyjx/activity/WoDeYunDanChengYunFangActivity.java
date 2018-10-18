package com.qmwl.zyjx.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioGroup;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.FlowFragmentAdapter;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.fragment.WoDeYunDanFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/10.
 * 我的运单(承运方)
 */

public class WoDeYunDanChengYunFangActivity extends BaseActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {
    private RadioGroup radioGroup;
    private ViewPager mVp;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.wodeyundan_chengyunfang_layout);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.wodeyundan);

        radioGroup = (RadioGroup) findViewById(R.id.yundan_layout_radiogroup);

        mVp = (ViewPager) findViewById(R.id.wodeyundan_layout_viewpager);
        List<Fragment> list = new ArrayList<>();
        WoDeYunDanFragment wodefabuFragment1 = new WoDeYunDanFragment();
        WoDeYunDanFragment wodefabuFragment2 = new WoDeYunDanFragment();
        WoDeYunDanFragment wodefabuFragment3 = new WoDeYunDanFragment();
        WoDeYunDanFragment wodefabuFragment4 = new WoDeYunDanFragment();
        WoDeYunDanFragment wodefabuFragment5 = new WoDeYunDanFragment();
        setStatue(wodefabuFragment1, 10);
        setStatue(wodefabuFragment2, 1);
        setStatue(wodefabuFragment3, 2);
        setStatue(wodefabuFragment4, 3);
        setStatue(wodefabuFragment5, 9);

        list.add(wodefabuFragment1);
        list.add(wodefabuFragment2);
        list.add(wodefabuFragment3);
        list.add(wodefabuFragment4);
        list.add(wodefabuFragment5);
        FlowFragmentAdapter adapter = new FlowFragmentAdapter(getSupportFragmentManager(), list);
        mVp.setAdapter(adapter);
        mVp.addOnPageChangeListener(this);
        mVp.setOffscreenPageLimit(5);
        radioGroup.setOnCheckedChangeListener(this);
    }

    private void setStatue(WoDeYunDanFragment fragment, int statue) {
        Bundle wodefabu = new Bundle();
        wodefabu.putInt("statue", statue);
        wodefabu.putBoolean("isyundan", true);
        fragment.setArguments(wodefabu);
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {

    }

    @Override
    protected void onMyClick(View v) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                radioGroup.check(R.id.yundan_layout_radiobutton_b1);
                break;
            case 1:
                radioGroup.check(R.id.yundan_layout_radiobutton_b2);
                break;
            case 2:
                radioGroup.check(R.id.yundan_layout_radiobutton_b3);
                break;
            case 3:
                radioGroup.check(R.id.yundan_layout_radiobutton_b4);
                break;
            case 4:
                radioGroup.check(R.id.yundan_layout_radiobutton_b5);
                break;
            case 5:
//                radioGroup.check(R.id.yundan_layout_radiobutton_b6);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.yundan_layout_radiobutton_b1:
                mVp.setCurrentItem(0);
                break;
            case R.id.yundan_layout_radiobutton_b2:
                mVp.setCurrentItem(1);
                break;
            case R.id.yundan_layout_radiobutton_b3:
                mVp.setCurrentItem(2);
                break;
            case R.id.yundan_layout_radiobutton_b4:
                mVp.setCurrentItem(3);
                break;
            case R.id.yundan_layout_radiobutton_b5:
                mVp.setCurrentItem(4);
                break;
//            case R.id.yundan_layout_radiobutton_b6:
//                mVp.setCurrentItem(5);
//                break;
        }
    }




}
