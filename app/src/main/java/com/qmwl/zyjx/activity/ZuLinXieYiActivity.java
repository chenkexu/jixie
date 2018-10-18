package com.qmwl.zyjx.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.view.CommomDialog;

/**
 * Created by Administrator on 2017/8/2.
 * 租赁协议页面
 */

public class ZuLinXieYiActivity extends BaseActivity {
    public static final int QIANMING_CODE = 1;//签名的获取码
    public static final String DINGDAN_NO = "com.gh.zulin,no";
    public static final String DATA = "com.gh.zulin.data";
    public static final String URL = "com.gh.zulin.URL";
    public static final String TYPE = "com.gh.zulin.ishetong";
    private boolean isHeTong;
    private String json = "";
    private String url = "";
    private String dingdanNo = "";
    private WebView mWb;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.webview_layout);
    }

    @Override
    protected void initView() {
        json = getIntent().getStringExtra(DATA);
        url = getIntent().getStringExtra(URL);
        isHeTong = getIntent().getBooleanExtra(TYPE, false);
        dingdanNo = getIntent().getStringExtra(DINGDAN_NO);
        if (isHeTong) {
            setTitleContent(R.string.zulinhetong);
            setRightText(R.string.qianming);
        } else {
            setTitleContent(R.string.zulingxieyi);
        }

        mWb = (WebView) findViewById(R.id.webview_layout_webview);
        initWebView(mWb);
        mWb.addJavascriptInterface(new H5Obj(this, mWb), "android");
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
        switch (v.getId()) {
            case R.id.base_top_bar_righttext:
                Intent intent = new Intent(this, QianMingActivity.class);
                intent.putExtra(DINGDAN_NO, dingdanNo);
                startActivityForResult(intent, QIANMING_CODE);
//                mWb.loadUrl("javascript:keep()");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == QIANMING_CODE) {
            if (resultCode == QianMingActivity.SUCCESS_CODE) {
                mWb.loadUrl("javascript:keep()");
            }

        }
    }

    class H5Obj {
        Context context;
        WebView webView;

        public H5Obj(Context cx, WebView webView) {
            this.context = cx;
            this.webView = webView;
        }

        @JavascriptInterface
        public void next() {
            Intent intent = new Intent(ZuLinXieYiActivity.this, ZuLinGeRenMessageActivity.class);
            intent.putExtra(DATA, json);
            startActivity(intent);
        }

        @JavascriptInterface
        public void showToast(String content) {
            Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
        }

        //开始审核结果页面
        @JavascriptInterface
        public void startLookThrough() {
            CommomDialog dialog = new CommomDialog(ZuLinXieYiActivity.this, R.style.dialog, getString(R.string.dengdaishenhe_hetong), new CommomDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    Intent intent = new Intent(ZuLinXieYiActivity.this, MainActivity.class);
                    intent.putExtra(MainActivity.MAIN_INDEX, MainActivity.WODE);
                    startActivity(intent);
                    dialog.dismiss();
                }
            }).setHideCancelButton();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }

        @JavascriptInterface
        public void lookThroughLoase() {
            CommomDialog dialog = new CommomDialog(ZuLinXieYiActivity.this, R.style.dialog, getString(R.string.tijiaoshibai_chongshi), new CommomDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    dialog.dismiss();
                }
            }).setHideCancelButton();
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            dialog.show();
        }
    }
}
