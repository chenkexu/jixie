package com.qmwl.zyjx.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.GGBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/7.
 * WebView页面
 */

public class PeiXunWebViewActivity extends BaseActivity {


    private String url = "";


    @Override
    protected void setLayout() {
        setContentLayout(R.layout.webview_peixun_layout);
    }

    @SuppressLint("JavascriptInterface")
    @Override
    protected void initView() {
        setTitleContent(R.string.zhaopinpeixun);
        Intent intent = getIntent();
        url = intent.getStringExtra("url");

         WebView mWb = (WebView) findViewById(R.id.webview_layout_webview);
        initWebView(mWb);
     //   mWb.addJavascriptInterface(new H5Obj(this, mWb), "android");
        mWb.loadUrl(url);
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
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {
        //获取购物车数量
        getGouWuCheData();
    }

    @Override
    protected void onMyClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.webview_top_bar_gouwuche:
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(this, LoginActivity.class);
                    startActivityForResult(intent, 0);
                    return;
                }
                intent = new Intent(this, MainActivity.class);
                intent.putExtra(MainActivity.MAIN_INDEX, MainActivity.GOUWUCHE);
                startActivity(intent);
                break;

        }
    }

    private Bitmap getWebViewBitmap(WebView mWebView) {
        // WebView 生成长图，也就是超过一屏的图片，代码中的 longImage 就是最后生成的长图
        mWebView.measure(View.MeasureSpec.makeMeasureSpec(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        mWebView.layout(0, 0, mWebView.getMeasuredWidth(), mWebView.getMeasuredHeight());
        mWebView.setDrawingCacheEnabled(true);
        mWebView.buildDrawingCache();
        Bitmap longImage = Bitmap.createBitmap(mWebView.getMeasuredWidth(),
                mWebView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(longImage);    // 画布的宽高和 WebView 的网页保持一致
        Paint paint = new Paint();
        canvas.drawBitmap(longImage, 0, mWebView.getMeasuredHeight(), paint);
        mWebView.draw(canvas);
        return longImage;
    }

    class H5Obj {
        Context context;
        WebView webView;

        public H5Obj(Context cx, WebView webView) {
            this.context = cx;
            this.webView = webView;
        }

        //关闭当前页面
        @JavascriptInterface
        public void finish() {
            finish();
        }

        @JavascriptInterface
        public void postMessage(String content) {
            Toast.makeText(context, "传送内容:" + content, Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void map(String content) {
            Intent intent = new Intent(context, MapActivity.class);
            intent.putExtra(MapActivity.DATA, content);
            context.startActivity(intent);
        }

        /**
         * 联系商家
         */

        @JavascriptInterface
        public void call(String id, String name, String heahImage) {
//            if ("".equals(phone) || TextUtils.isEmpty(phone)) {
//                Toast.makeText(context, getString(R.string.zanwulianxifangshi), Toast.LENGTH_SHORT).show();
//                return;
//            }
//            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);
            Intent intent = null;
            if (!MyApplication.getIntance().isLogin()) {
                intent = new Intent(PeiXunWebViewActivity.this, LoginActivity.class);
                startActivityForResult(intent, 0);
                return;
            }
            intent = new Intent(context, ImActivity.class);
            intent.putExtra(ImActivity.IM_ID, "zyjx" + id);
            context.startActivity(intent);
        }

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
//            Toast.makeText(context, "startShopping", Toast.LENGTH_SHORT).show();
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
         * 实际为加入购物车
         *
         * @param str
         */
        @JavascriptInterface
        public void showToast(String str) {
            Intent intent = null;
            if (!MyApplication.getIntance().isLogin()) {
                intent = new Intent(PeiXunWebViewActivity.this, LoginActivity.class);
                startActivityForResult(intent, 0);
                return;
            }
            String url =Contact.addGouwuChe + "?uid=" + MyApplication.getIntance().userBean.getUid() + "&goods_id=" + str + "&num=1";
            AndroidNetworking.get(url)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                if (JsonUtils.isSuccess(response)) {
                                    Toast.makeText(context, "加入购物车成功", Toast.LENGTH_SHORT).show();
                                    getGouWuCheData();
                                } else {
                                    Toast.makeText(context, "加入购物车失败," + response.getString("message"), Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(context, "加入购物车失败", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            Toast.makeText(context, "加入购物车失败", Toast.LENGTH_SHORT).show();
                        }
                    });

        }

        /**
         * 加入购物车
         *
         * @param str
         */
        @JavascriptInterface
        public void addShopings(String str) {
        }

        /**
         * 启动登录页面
         */
        @JavascriptInterface
        public void startLogin() {
            Intent intent = new Intent(context, LoginActivity.class);
            startActivityForResult(intent, 0);
        }

        /**
         * 立即购买
         *
         * @param json
         */
        @JavascriptInterface
        public void placeOrder(String json) {
            Intent intent = null;
            if (MyApplication.getIntance().isLogin()) {
                intent = new Intent(context, QueRenDingDanActivity.class);
                intent.putExtra(QueRenDingDanActivity.DATA, json);
                startActivity(intent);
                PeiXunWebViewActivity.this.finish();
            } else {
                intent = new Intent(PeiXunWebViewActivity.this, LoginActivity.class);
                startActivityForResult(intent, 0);
            }
        }

        /**
         * 立即租赁
         * type没用，只是用来区别立即购买方法的
         *
         * @param json
         */
        @JavascriptInterface
        public void placeOrder(String json, int type) {
            Intent intent = null;
            if (MyApplication.getIntance().isLogin()) {
                intent = new Intent(context, ZuLinXieYiActivity.class);
                intent.putExtra(ZuLinXieYiActivity.DATA, json);
                intent.putExtra(ZuLinXieYiActivity.URL, Contact.zulinxieyi_url);
                startActivity(intent);
            } else {
                intent = new Intent(PeiXunWebViewActivity.this, LoginActivity.class);
                startActivityForResult(intent, 0);
            }
        }

        /**
         * 获取用户id
         */
        @JavascriptInterface
        public String getUserId(String shop_id, String shop_name) {
            if (!MyApplication.getIntance().isLogin()) {
                Intent intent = new Intent(context, LoginActivity.class);
                startActivityForResult(intent, 0);
                return "0";
            }
            String uid = MyApplication.getIntance().userBean.getUid();
            collectionShop(shop_id, shop_name);
            return uid;
        }

        @JavascriptInterface
        public void showString(String content) {
            Toast.makeText(context, "" + content, Toast.LENGTH_SHORT).show();
        }

        @JavascriptInterface
        public void seller_all(String id) {
            Intent intent = new Intent(PeiXunWebViewActivity.this, AllPingJiaActivity.class);
            intent.putExtra(AllPingJiaActivity.SHOP_ID, id);
            startActivity(intent);
        }

        @JavascriptInterface
        public void share(String url, String title, String image) {
            UMWeb web = new UMWeb(url);
            web.setTitle(title);//标题
            web.setDescription(title);//描述
            UMImage umImage = new UMImage(PeiXunWebViewActivity.this, R.mipmap.logo);
            web.setThumb(umImage);
            new ShareAction(PeiXunWebViewActivity.this)
//                    .withText("hello")
                    .withMedia(web)
                    .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                    .setCallback(shareListener)
                    .open();
        }


        public void collectionShop(String shop_id, String shop_name) {
            if (MyApplication.getIntance().isLogin()) {
                AndroidNetworking.post(Contact.tianjiashoucang)
                        .addBodyParameter("uid", MyApplication.getIntance().userBean.getUid())
                        .addBodyParameter("fav_id", shop_id)
                        .addBodyParameter("log_msg", shop_name)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                            }

                            @Override
                            public void onError(ANError anError) {
                            }
                        });
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getGouWuCheData();
    }

    /**
     * 获取购物车数据
     */
    private void getGouWuCheData() {



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
            Toast.makeText(PeiXunWebViewActivity.this, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(PeiXunWebViewActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(PeiXunWebViewActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };

}
