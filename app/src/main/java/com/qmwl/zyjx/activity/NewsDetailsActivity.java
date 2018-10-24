package com.qmwl.zyjx.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * Created by Administrator on 2017/7/24.
 * 咨询,帖子，消息详情
 */

public class NewsDetailsActivity extends BaseActivity {
    public static final String DETAILS_URL = "com.gh.details_url";
    public static final String DETAILS_TYPE = "com.gh.details_type";

    public static final int TYPE_ZIXUN = 0;//咨询
    public static final int TYPE_TIEZI = 1;//帖子
    public static final int TYPE_XIAOXI = 2;//消息
    public static final int TYPE_DINGDAN = 3;//订单
    public static final int TYPE_FUWU = 4;//服务
    public static final int TYPE_ZHAOPINPEIXUN = 5;//招聘培训
    public static final int TYPE_JINRONG = 6;//金融
    public static final int TYPE_BAOXIAN = 7;//保险

    private String url = "";
    private int type;//当前所属详情页

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.news_details_layout);
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        url = intent.getStringExtra(DETAILS_URL);
        type = intent.getIntExtra(DETAILS_TYPE, 0);

        switch (type) {
            case TYPE_ZIXUN:
                setTitleContent(R.string.zixun);
                break;
            case TYPE_TIEZI:
                setTitleContent(R.string.tiezi);
                break;
            case TYPE_XIAOXI:
                setTitleContent(R.string.xiaoxi);
                break;
            case TYPE_DINGDAN:
                setTitleContent(R.string.dingdanxiangqing);
                break;

            case TYPE_FUWU:
                setTitleContent(R.string.fuwu);
                break;

            case TYPE_ZHAOPINPEIXUN:
                setTitleContent(R.string.zhaopinpeixun);
                break;

            case TYPE_JINRONG:
                setTitleContent(R.string.jinrong);
                break;
            case TYPE_BAOXIAN:
                setTitleContent(R.string.baoxian);
                break;

        }
        WebView mWb = (WebView) findViewById(R.id.news_details_layout_webview);
        initWebView(mWb);
        mWb.addJavascriptInterface(new H5Obj(this, mWb), "android");
        mWb.loadUrl(this.url);
    }

    private void initWebView(WebView webView) {
        webView.getSettings().setJavaScriptEnabled(true);// 设置使用够执行JS脚本
        webView.getSettings().setBuiltInZoomControls(false);// 设置使支持缩放
        // 设置WebView在网页上显示缩放工具
        webView.getSettings().setDisplayZoomControls(false);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);  //设置 缓存模式
        webView.setWebViewClient(new WebViewClient());

//        webView.getSettings().setDefaultTextEncodingName("utf-8");
//        webView.getSettings().setAllowFileAccess(true);
//        webView.getSettings().setAllowFileAccessFromFileURLs(true);
//        webView.getSettings().setLoadsImagesAutomatically(true);
//        webView.getSettings().setGeolocationEnabled(true);
//        webView.getSettings().setDomStorageEnabled(true);
//        //设置自适应屏幕，两者合用
//        webView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
//        webView.getSettings().setUseWideViewPort(true); //将图片调整到适合webview的大小
//        webView.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小
//        // 设置与Js交互的权限
//        // 设置允许JS弹窗
//        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        webView.getSettings().setSupportZoom(true);
//        // SMALLEST(50%),  SMALLER(75%),          NORMAL(100%),          LARGER(150%),          LARGEST(200%);
//        webView.getSettings().setTextSize(WebSettings.TextSize.SMALLER);
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

    /**
     * 首行缩进两个字符
     *
     * @param tv
     */
    protected void secondBlank(TextView tv) {
        SpannableStringBuilder span = new SpannableStringBuilder("缩进" + tv.getText());
        span.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, 2,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tv.setText(span);
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
            Toast.makeText(NewsDetailsActivity.this, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(NewsDetailsActivity.this, "失败" + t.getMessage(), Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(NewsDetailsActivity.this, "取消了", Toast.LENGTH_LONG).show();
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

    class H5Obj {
        Context context;
        WebView webView;

        public H5Obj(Context cx, WebView webView) {
            this.context = cx;
            this.webView = webView;
        }

        @JavascriptInterface
        public void postMessage(String id, String messageid) {
            if (!MyApplication.getIntance().isLogin()) {
                Intent intent = new Intent(NewsDetailsActivity.this, LoginActivity.class);
                startActivityForResult(intent, 0);
                return;
            }
//            Intent intent = new Intent(context, HuiTieActivity.class);
//            intent.putExtra(HuiTieActivity.FATIEREN_ID, id);
//            intent.putExtra(HuiTieActivity.TIEZI_ID, messageid);
//            context.startActivity(intent);
            Intent intent = new Intent(context, ImActivity.class);
            intent.putExtra(ImActivity.IM_ID, "zyjx" + id);
            context.startActivity(intent);

        }

        @JavascriptInterface
        public void postMessage(String wodeid, String messageid, String id) {
            if (!MyApplication.getIntance().isLogin()) {
                Intent intent = new Intent(NewsDetailsActivity.this, LoginActivity.class);
                startActivityForResult(intent, 0);
            }
            Intent intent = new Intent(context, HuiTieActivity.class);
            intent.putExtra(HuiTieActivity.FATIEREN_ID, id);
            intent.putExtra(HuiTieActivity.TIEZI_ID, messageid);
            context.startActivity(intent);
        }

        @JavascriptInterface
        public void share(String url, String title, String image) {
            UMWeb web = new UMWeb(url);
            web.setTitle(title);//标题
            web.setDescription(title);//描述
            UMImage umImage = new UMImage(NewsDetailsActivity.this, R.mipmap.logo);
            web.setThumb(umImage);
            new ShareAction(NewsDetailsActivity.this)
//                    .withText("hello")
                    .withMedia(web)
                    .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                    .setCallback(shareListener)
                    .open();
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
                intent = new Intent(NewsDetailsActivity.this, LoginActivity.class);
                startActivityForResult(intent, 0);
                return;
            }
            intent = new Intent(context, ImActivity.class);
            intent.putExtra(ImActivity.IM_ID, "zyjx" + id);
            context.startActivity(intent);
        }


        @JavascriptInterface
        public void tel(String phone) {
            /*Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);*/



            Intent intent = null;
            if (!MyApplication.getIntance().isLogin()) {
                intent = new Intent(NewsDetailsActivity.this, LoginActivity.class);
                startActivityForResult(intent, 0);
                return;
            }
            intent = new Intent(context, ReturnWuliuActivity.class);
           // intent.putExtra(ImActivity.IM_ID, "zyjx" + id);
            context.startActivity(intent);
        }

        @JavascriptInterface
        public void wuliu(String orderId) {
            /*Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);*/

            Intent intent = null;
            if (!MyApplication.getIntance().isLogin()) {
                intent = new Intent(NewsDetailsActivity.this, LoginActivity.class);
                startActivityForResult(intent, 0);
                return;
            }
            intent = new Intent(context, ReturnWuliuActivity.class);
             intent.putExtra("isH5" ,true);
            intent.putExtra("orderId" ,orderId);
            context.startActivity(intent);
        }



        @JavascriptInterface
        public void weiquan(String orderId) {
            /*Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);*/

            Intent intent = null;
            if (!MyApplication.getIntance().isLogin()) {
                intent = new Intent(NewsDetailsActivity.this, LoginActivity.class);
                startActivityForResult(intent, 0);
                return;
            }
            intent = new Intent(context, AskWeiQuanActivity.class);
            intent.putExtra("isH5" ,true);
            intent.putExtra("orderId" ,orderId);
            context.startActivity(intent);
        }
    }
}
