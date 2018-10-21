package com.qmwl.zyjx.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import com.qmwl.zyjx.activity.AllPingJiaActivity;
import com.qmwl.zyjx.activity.LoginActivity;
import com.qmwl.zyjx.activity.MapActivity;
import com.qmwl.zyjx.activity.QueRenDingDanActivity;
import com.qmwl.zyjx.activity.ShangJiaZhongXinActivity;
import com.qmwl.zyjx.base.MyApplication;

public class H5Obj {
    Context context;
    WebView webView;

    public H5Obj(Context cx, WebView webView) {
        this.context = cx;
        this.webView = webView;
    }

    @JavascriptInterface
    public void postMessage(String content) {
        Toast.makeText(context, "传送内容:" + content, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void share(String url, String title, String image) {
        Log.i("TAG", "分享方法:" + url + "  " + title + "   " + image);
        Toast.makeText(context, "分享方法:" + url + "  " + title + "   " + image, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void map(String content) {
        Log.i("TAG", "map" + content);
        Toast.makeText(context, "map", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, MapActivity.class);
        intent.putExtra(MapActivity.DATA, content);
        context.startActivity(intent);
    }

/**
     * 联系商家
     *
     * @param phone 电话号码
     */


    @JavascriptInterface
    public void tel(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

/**
     * 打开商家中心
     *
     * @param shop_id
     */

    @JavascriptInterface
    public void look_all(String shop_id) {
        Toast.makeText(context, "startShopping", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, ShangJiaZhongXinActivity.class);
        intent.putExtra(ShangJiaZhongXinActivity.SHOP_ID, shop_id);
        context.startActivity(intent);
    }

/**
     * 打开全部评价
     *
     * @param shop_id
     */

    @JavascriptInterface
    public void startPingJia(String shop_id) {
        Toast.makeText(context, "startPingJia", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, AllPingJiaActivity.class);
        intent.putExtra(ShangJiaZhongXinActivity.SHOP_ID, shop_id);
        context.startActivity(intent);
    }

/**
     * 获取登录状态
     *
     * @return
     */

    @JavascriptInterface
    public boolean getloginstate() {
        return MyApplication.getIntance().isLogin();
    }

/**
     * 显示一个提示
     *
     * @param str
     */

    @JavascriptInterface
    public void showToast(String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

/**
     * 加入购物车
     *
     * @param str*/


    @JavascriptInterface
    public void addShopings(String str) {

    }
/*
*
     * 启动登录页面*/


    @JavascriptInterface
    public void startLogin() {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

/**
     * 立即购买
     *
     * @param json*/


    @JavascriptInterface
    public void placeOrder(String json) {
        Log.i("TAG", "确认订单:" + json);
        Intent intent = new Intent(context, QueRenDingDanActivity.class);
        intent.putExtra(QueRenDingDanActivity.DATA, json);
        context.startActivity(intent);
    }
}
