package com.qmwl.zyjx.activity;

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
 * Created by Administrator on 2017/8/30.
 * 我的运单页面（货主）
 */

public class WoDeYunDanActivity extends BaseActivity implements ViewPager.OnPageChangeListener, RadioGroup.OnCheckedChangeListener {

    private ViewPager mVp;
    private RadioGroup radioGroup;
    private WoDeYunDanFragment daifahuoFragment;
    @Override
    protected void setLayout() {
        setContentLayout(R.layout.wodeyundan_activity_layout);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.wodeyundan);

        mVp = (ViewPager) findViewById(R.id.wodedingdan_layout_viewpager);
//        mVp.setOffscreenPageLimit(5);
        List<Fragment> list = new ArrayList<>();
        WoDeYunDanFragment wodefabuFragment = new WoDeYunDanFragment();
        setStatue(wodefabuFragment, 0);

        daifahuoFragment = new WoDeYunDanFragment();
        setStatue(daifahuoFragment, 1);
        WoDeYunDanFragment daishouhuoFragment = new WoDeYunDanFragment();
        setStatue(daishouhuoFragment, 2);
        WoDeYunDanFragment yiwanchengFaragment = new WoDeYunDanFragment();
        setStatue(yiwanchengFaragment, 3);
        WoDeYunDanFragment quanbuFragment = new WoDeYunDanFragment();
        setStatue(quanbuFragment, 4);
        list.add(wodefabuFragment);
        list.add(daifahuoFragment);
        list.add(daishouhuoFragment);
        list.add(yiwanchengFaragment);
        list.add(quanbuFragment);
        FlowFragmentAdapter adapter = new FlowFragmentAdapter(getSupportFragmentManager(), list);
        mVp.setAdapter(adapter);
        mVp.addOnPageChangeListener(this);
        mVp.setOffscreenPageLimit(5);

        radioGroup = (RadioGroup) findViewById(R.id.wodeyundan_layout_radiogroup);
        radioGroup.setOnCheckedChangeListener(this);
    }

    private void setStatue(WoDeYunDanFragment fragment, int statue) {
        Bundle wodefabu = new Bundle();
        wodefabu.putInt("statue", statue);
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
    protected void onResume() {
        super.onResume();
        if (daifahuoFragment!=null){
            daifahuoFragment.update();
        }

    }

    @Override
    public void onPageSelected(int position) {
        switch (position) {
            case 0:
                radioGroup.check(R.id.wodeyundan_layout_wodefabu);
                break;
            case 1:
                radioGroup.check(R.id.wodeyundan_layout_daifahuo);
                break;
            case 2:
                radioGroup.check(R.id.wodeyundan_layout_daishouhuo);
                break;
            case 3:
                radioGroup.check(R.id.wodeyundan_layout_yiwancheng);
                break;
            case 4:
                radioGroup.check(R.id.wodeyundan_layout_quanbu);
                break;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.wodeyundan_layout_wodefabu:
                mVp.setCurrentItem(0);
                break;
            case R.id.wodeyundan_layout_daifahuo:
                mVp.setCurrentItem(1);
                break;
            case R.id.wodeyundan_layout_daishouhuo:
                mVp.setCurrentItem(2);
                break;
            case R.id.wodeyundan_layout_yiwancheng:
                mVp.setCurrentItem(3);
                break;
            case R.id.wodeyundan_layout_quanbu:
                mVp.setCurrentItem(4);
                break;
        }
    }
}
