package com.qmwl.zyjx.base;

import android.app.Dialog;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.utils.AppManager;
import com.qmwl.zyjx.utils.EventManager;
import com.qmwl.zyjx.utils.LoadingDialogUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

import butterknife.ButterKnife;


/**
 * Created by Administrator on 2017/7/18.
 */

public abstract class BaseActivity extends FragmentActivity implements View.OnClickListener {
    //设置布局
    protected abstract void setLayout();

    //初始化View
    protected abstract void initView();

    //设置监听
    protected abstract void onListener();

    //获取数据
    protected abstract void getInterNetData();

    //点击
    protected abstract void onMyClick(View v);

    private FrameLayout baseLayoutContainer;
    private View statue;
    //titleView
    private TextView titleTextView;
    //回退按钮
    private View backView;
    //右侧的第一个图片
    private ImageView rightImageView;
    //右侧的textview
    private TextView rightTextView;

    private Dialog loadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        MyApplication.panduanyuyan(this);
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        //去掉Activity上面的状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        PushAgent.getInstance(this).onAppStart();
        AppManager.getAppManager().addActivity(this);
        setContentView(R.layout.base_layout);

        initBaseView();
        setStatueLayoutParmes();
        setLayout();
        setLayoutBundle(savedInstanceState);
        initBar();
        ButterKnife.bind(this);
        initView();
        onListener();
        getInterNetData();
        EventManager.register(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    //初始化View
    private void initBaseView() {
        baseLayoutContainer = (FrameLayout) findViewById(R.id.base_layout_container);
    }

    //初始化参数的
    protected void setLayoutBundle(Bundle savedInstanceState) {

    }

    /**
     * 获取布局的根目录
     *
     * @return
     */
    protected View getContainerView() {
        return baseLayoutContainer;
    }

    private void initBar() {
        titleTextView = (TextView) findViewById(R.id.base_top_bar_title);
        backView = findViewById(R.id.base_top_bar_back);
        rightImageView = (ImageView) findViewById(R.id.base_top_bar_right);
        rightTextView = (TextView) findViewById(R.id.base_top_bar_righttext);
        if (backView != null) {
            backView.setVisibility(View.VISIBLE);
            backView.setOnClickListener(this);
        }
    }

    /**
     * 显示或者隐藏回退按钮
     *
     * @param b
     */
    protected void showBackView(boolean b) {
        if (backView == null) {
            return;
        }
        if (b) {
            backView.setVisibility(View.VISIBLE);
        } else {
            backView.setVisibility(View.GONE);
        }
    }

    /**
     * 设置title内容
     *
     * @param title
     */
    protected void setTitleContent(String title) {
        if (titleTextView != null) {
            titleTextView.setText(title);
        }
    }

    /**
     * 设置title内容
     *
     * @param resouseId
     */
    protected void setTitleContent(int resouseId) {
        if (titleTextView != null) {
            titleTextView.setText(resouseId);
        }
    }

    /**
     * 设置右侧的按钮图片
     *
     * @param resouseId
     */
    protected void setRightImageView(int resouseId) {
        if (rightImageView != null) {
            rightImageView.setVisibility(View.VISIBLE);
            rightImageView.setImageResource(resouseId);
            rightImageView.setOnClickListener(this);
        }
    }

    /**
     * 设置右侧导航栏的文字
     *
     * @param resouseId
     */
    protected void setRightText(int resouseId) {
        if (rightTextView != null) {
            rightTextView.setVisibility(View.VISIBLE);
            rightTextView.setText(resouseId);
            rightTextView.setOnClickListener(this);
        }
    }

    /**
     * 设置右侧导航栏的文字
     *
     * @param resouseId
     */
    protected void setRightText(String resouseId) {
        if (rightTextView != null) {
            rightTextView.setVisibility(View.VISIBLE);
            rightTextView.setText(resouseId);
            rightTextView.setOnClickListener(this);
        }
    }


    /**
     * 设置layout
     *
     * @param layoutId 布局id
     */
    protected void setContentLayout(int layoutId) {
        if (baseLayoutContainer != null) {
            baseLayoutContainer.removeAllViews();
            getLayoutInflater().inflate(layoutId, baseLayoutContainer);
        }
    }

    /**
     * 设置layout
     *
     * @param view
     */
    protected void setContentLayout(View view) {
        if (baseLayoutContainer != null) {
            baseLayoutContainer.removeAllViews();
            baseLayoutContainer.addView(view);
        }
    }

    protected void setRightImageView2(int rouseId) {
        ImageView rightImageView2 = (ImageView) findViewById(R.id.base_top_bar_right2);
        if (rightImageView2 != null) {
            rightImageView2.setImageResource(rouseId);
        }
    }

    /**
     * 设置为竖屏
     */
    protected void setPortrait() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    /**
     * 设置为横屏
     */
    protected void setLandscape() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }


    //设置状态栏的高度
    private void setStatueLayoutParmes() {
        statue = findViewById(R.id.base_layout_statue);
        ViewGroup.LayoutParams layoutParams = statue.getLayoutParams();
        layoutParams.height = getStatusBarHeight(this);
        statue.setLayoutParams(layoutParams);
    }

    //设置状态栏的隐藏和显示
    protected void setStatueHide(boolean b) {
        if (statue == null) {
            return;
        }
        if (b) {
            statue.setVisibility(View.VISIBLE);
        } else {
            statue.setVisibility(View.GONE);
        }
    }

    /**
     * 获取状态栏的高
     *
     * @param context
     * @return
     */
    public int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public int getHeight() {
        WindowManager wm = this.getWindowManager();
        return wm.getDefaultDisplay().getHeight();
    }

    public int getWidth() {
        WindowManager wm = this.getWindowManager();
        return wm.getDefaultDisplay().getWidth();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.base_top_bar_back:
                finish();
                break;
            default:
                onMyClick(v);
                break;
        }
    }

    protected boolean isStringsNull(String[] s) {
        for (int i = 0; i < s.length; i++) {
            if ("".equals(s[i]) || TextUtils.isEmpty(s[i])) {
                return true;
            }
        }
        return false;
    }

    public void showLoadingDialog() {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialogUtils.createLoadingDialog(this, "正在加载...");
        }
        if (!loadingDialog.isShowing()) {
            loadingDialog.show();
        }
    }

    public void dismissLoadingDialog() {
        if (loadingDialog != null && loadingDialog.isShowing()) {
            loadingDialog.dismiss();
        }
    }


}
