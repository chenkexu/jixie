package com.qmwl.zyjx.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

/**
 * Created by Administrator on 2017/7/20.
 * 帖子详情
 */

public class TieZiDetailsActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected void setLayout() {
        setContentLayout(R.layout.tiezi_details_activity_layout);
    }

    @Override
    protected void initView() {
        ImageView addView = (ImageView) findViewById(R.id.base_top_bar_right);
        ImageView heimingdanView = (ImageView) findViewById(R.id.base_top_bar_right2);
        TextView titleTv = (TextView) findViewById(R.id.base_top_bar_title);
        addView.setVisibility(View.VISIBLE);
        heimingdanView.setVisibility(View.VISIBLE);
        titleTv.setText(getResources().getString(R.string.xiangqing));
        addView.setImageResource(R.mipmap.fabuanniu);
        heimingdanView.setImageResource(R.mipmap.heimingdan);
        addView.setOnClickListener(this);
        heimingdanView.setOnClickListener(this);
        findViewById(R.id.tiezi_details_fenxiang_tv).setOnClickListener(this);
        findViewById(R.id.tiezi_details_fenxiang_iv).setOnClickListener(this);

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
            case R.id.base_top_bar_right:

                break;
            case R.id.base_top_bar_right2:

                break;
            case R.id.tiezi_details_fenxiang_iv:
            case R.id.tiezi_details_fenxiang_tv:
                //分享按钮处理
//                new ShareAction(this)
//                        .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)//传入平台
//                        .withText("hello")//分享内容
//                        .setCallback(shareListener)//回调监听器
//                        .share();

                new ShareAction(this)
                        .withText("hello")
                        .setDisplayList(SHARE_MEDIA.SINA,SHARE_MEDIA.QQ,SHARE_MEDIA.WEIXIN)
                        .setCallback(shareListener)
                        .open();
                break;
        }
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(TieZiDetailsActivity.this, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(TieZiDetailsActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(TieZiDetailsActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {


    }

    private void questQuanXian() {
        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }
    }


}
