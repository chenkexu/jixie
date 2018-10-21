package com.qmwl.zyjx.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.fastjson.JSONObject;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.JsonObject;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.util.EasyUtils;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.FlowFragmentAdapter;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.fragment.FourFragment;
import com.qmwl.zyjx.fragment.MainFragment;
import com.qmwl.zyjx.fragment.SecondFragment;
import com.qmwl.zyjx.fragment.ThreadFragment;
import com.qmwl.zyjx.runtimepermissions.PermissionsManager;
import com.qmwl.zyjx.runtimepermissions.PermissionsResultAction;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.EventManager;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.view.CommomDialog;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;


import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {
    public static final String MAIN_INDEX = "com.gh.main.index_value";
    //其他地方登陸，此账号被顶下去
    public static final String IS_OUT_LOGING = "com.gh.main.out_login";
    public static final int MAIN = 0;
    public static final int TIEZI = 1;
    public static final int GOUWUCHE = 2;
    public static final int WODE = 3;


    private ViewPager mVp;
    private RadioGroup buttonContainer;
    private RadioButton mainButton;
    private RadioButton fabuButton;
    private MainBroadCastReceiver mainBroadCastReceiver = null;
    private SecondFragment secondFragment;
    private MainFragment mainFragment;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.activity_main);
    }

    @Override
    protected void initView() {
        isOutLogin();
         //getWoYaokaidianStatue1


        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                //  Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
                Log.d("huangrui","友盟收到的消息推送zi定义通知:"+msg.custom);

                Log.d("huangrui","msg.after_open的值:"+msg.after_open);
                if ("go_app".equals(msg.after_open)){

                }else if("go_custom".equals(msg.after_open)){
                   // JsonObject jsonObject= new JsonObject(msg.custom);
                    //jsonObject.get("");
                    try {
                        org.json.JSONObject obj = new org.json.JSONObject(msg.custom);
                        String orderId=obj.get("orderId")+"";
                        String orderStatus=obj.get("orderStatus")+"";
                        String url=obj.get("url")+"";
                        Log.d("huangrui","获取到的status"+orderStatus);

                        //使用方法三
                        //应用在后台
                        if ("0".equals(orderStatus)) {
                            //待付款
                            startActivity(new Intent(context,WoDeDingDanActivity.class).putExtra("index",1).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        }else if ("1".equals(orderStatus)){
                            //申请退款
                            // startActivity(new Intent(context,WoDeDingDanActivity.class).putExtra("index",1));
                        }else if ("2".equals(orderStatus)){
                            //待收货
                            startActivity(new Intent(context,WoDeDingDanActivity.class).putExtra("index",3).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        }else if ("3".equals(orderStatus)){
                            //已完成
                            startActivity(new Intent(context,WoDeDingDanActivity.class).putExtra("index",4).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        }else if ("4".equals(orderStatus)){
                            //已完成
                            startActivity(new Intent(context,WoDeDingDanActivity.class).putExtra("index",4).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        }else if ("-1".equals(orderStatus)){
                            //待退款退货订单
                            startActivity(new Intent(context,WoDeDingDanActivity.class).putExtra("index",5).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        }else if ("6".equals(orderStatus)){
                            //异常订单
                            startActivity(new Intent(context,WoDeDingDanActivity.class).putExtra("index",6).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        }else if ("-100".equals(orderStatus)){
                            //如果时-100 跳转我的店铺界面,到主界面接收umeng，跳转到第四个fragment
                           // startActivity(new Intent(context,MainActivity.class).putExtra("umeng",3).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                            //点击跳转我的店铺界面
                        //    String url=getIntent().getStringExtra("umeng");
                            setCurrItem(3);
                            EventManager.post("umeng",url);


                        }




                    } catch (JSONException e) {

                        Log.d("huangrui","异常原因"+e.toString());

                    }

                    //{"orderId":"-100","orderStatus":"0"}
                }



            }

        };
        MyApplication.mPushAgent.setNotificationClickHandler(notificationClickHandler);

        /**
         * 请求所有必要的权限----原理就是获取清单文件中申请的权限
         */
        PermissionsManager.getInstance().requestAllManifestPermissionsIfNecessary(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
            }

            @Override
            public void onDenied(String permission) {
            }
        });

        mVp = (ViewPager) findViewById(R.id.main_layout_viewpager);
        buttonContainer = (RadioGroup) findViewById(R.id.main_layout_button_container);
        mainButton = (RadioButton) findViewById(R.id.main_layout_main_button);
        fabuButton = (RadioButton) findViewById(R.id.main_layout_fabu_button);
        List<Fragment> list = new ArrayList<>();

        mainFragment = new MainFragment();
        secondFragment = new SecondFragment();
        list.add(mainFragment);
        list.add(secondFragment);
        list.add(new ThreadFragment());
        list.add(new FourFragment());
        FlowFragmentAdapter adapter = new FlowFragmentAdapter(getSupportFragmentManager(), list);
        mVp.setAdapter(adapter);
        mVp.setOffscreenPageLimit(4);
        mVp.addOnPageChangeListener(this);
        buttonContainer.setOnCheckedChangeListener(this);
        buttonContainer.check(R.id.main_layout_main_button);
//        int umeng=getIntent().getIntExtra("umeng",0);
//        if (umeng!=0){
//
//        }else{
            int intExtra = getIntent().getIntExtra(MAIN_INDEX, MAIN);
            setCurrItem(intExtra);
      //  }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int intExtra = getIntent().getIntExtra(MAIN_INDEX, MAIN);
        setCurrItem(intExtra);
        isOutLogin();
    }

    private void isOutLogin() {
        boolean isOutLogin = getIntent().getBooleanExtra(IS_OUT_LOGING, false);
        if (isOutLogin) {
            CommomDialog dialog = new CommomDialog(MainActivity.this, R.style.dialog, getString(R.string.zhanghaozaiqitashebeidenglu), new CommomDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    dialog.dismiss();
                }
            }).setHideCancelButton();
            dialog.show();
        }
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

    //四个按钮的点击事件
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.main_layout_main_button:
                //首页
                mVp.setCurrentItem(0, false);
                setStatueHide(true);
                break;
            case R.id.main_layout_fabu_button:
                //发布
                mVp.setCurrentItem(1, false);
                setStatueHide(true);
                break;
            case R.id.main_layout_gouwuche_button:
                //购物车
                mVp.setCurrentItem(2, false);
                setStatueHide(true);
                break;
            case R.id.main_layout_wode_button:
                //我的
                mVp.setCurrentItem(3, false);
                setStatueHide(false);
                break;
        }

    }

    public void setCurrItem(int index) {
        mVp.setCurrentItem(index, false);
        RadioButton radioButton = null;
        switch (index) {
            case 0:
                radioButton = (RadioButton) findViewById(R.id.main_layout_main_button);
                radioButton.setChecked(true);
                break;
            case 1:
                radioButton = (RadioButton) findViewById(R.id.main_layout_fabu_button);
                radioButton.setChecked(true);
                break;
            case 2:
                radioButton = (RadioButton) findViewById(R.id.main_layout_gouwuche_button);
                radioButton.setChecked(true);
                break;
            case 3:
                radioButton = (RadioButton) findViewById(R.id.main_layout_wode_button);
                radioButton.setChecked(true);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mainFragment != null) {
            mainFragment.refreshHot();
            mainFragment.mResume();
        }
//        MainBroadCastReceiver = new MainBroadCastReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mainFragment != null) {
            mainFragment.mPause();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (mainFragment != null) {
            if (position == 0) {
                mainFragment.mResume();
            } else {
                mainFragment.onPause();
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    class MainBroadCastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == TIEZI) {
            if (secondFragment != null) {
                secondFragment.onPullRefresh();
            }
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
