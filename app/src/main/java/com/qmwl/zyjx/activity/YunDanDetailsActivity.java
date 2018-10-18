package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.utils.Contact;

/**
 * Created by Administrator on 2017/10/13.
 * 货运详情
 */

public class YunDanDetailsActivity extends BaseActivity {
    private String tid = "";
    private WebView mWb;
    private boolean isYunDanDetails = false;
    private boolean isChengYunFang = false;
    private String url = "";

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.wodeyundan_details_layout);
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        isYunDanDetails = intent.getBooleanExtra("isyundandetails", false);
        isChengYunFang = intent.getBooleanExtra("ischengyunfang",false);
        tid = intent.getStringExtra("tid");
        if (isYunDanDetails) {
            //这里分货主运单和承运方运单
            if (isChengYunFang){
                url = Contact.yundandetails_url + "?id=" + tid + "&uid=" + MyApplication.getIntance().userBean.getUid();
            }else{
                url = Contact.huozhu_yundan_details + "?tid=" + tid + "&uid=" + MyApplication.getIntance().userBean.getUid();
            }
            setTitleContent(R.string.yundanxiangqing);
            findViewById(R.id.yundan_details_layout_lijibaojia).setVisibility(View.GONE);
        } else {
            setTitleContent(R.string.huoyuanxiangqing);
            url = Contact.huoyuanxiangqing_url + "?tid=" + tid + "&uid=" + MyApplication.getIntance().userBean.getUid();
        }

        mWb = (WebView) findViewById(R.id.huoyuandetails_layout_webview);
        initWebView(mWb);
        findViewById(R.id.yundan_details_layout_lijibaojia).setOnClickListener(this);
        mWb.loadUrl(url);
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {

    }

    @Override
    protected void onMyClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.yundan_details_layout_lijibaojia:
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(this, LoginActivity.class);
                    startActivityForResult(intent, 0);
                    return;
                }
                intent = new Intent(this, BaoJiaBianJiActivity.class);
                intent.putExtra(BaoJiaBianJiActivity.TID_DATA, tid);
                startActivity(intent);
                break;
        }
    }

    private void initWebView(WebView webView) {
//        LOAD_CACHE_ONLY:  不使用网络，只读取本地缓存数据
//        LOAD_DEFAULT:  根据cache-control决定是否从网络上取数据。
//        LOAD_CACHE_NORMAL: API level 17中已经废弃, 从API level 11开始作用同LOAD_DEFAULT模式
//        LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
//        LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        webView.getSettings().setJavaScriptEnabled(true);// 设置使用够执行JS脚本
        webView.getSettings().setBuiltInZoomControls(false);// 设置使支持缩放
        // 设置WebView在网页上显示缩放工具
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);  //设置 缓存模式
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setLoadWithOverviewMode(true);
    }

}
