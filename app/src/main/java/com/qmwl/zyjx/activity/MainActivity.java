package com.qmwl.zyjx.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.orhanobut.logger.Logger;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.FlowFragmentAdapter;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.UpdateBean;
import com.qmwl.zyjx.fragment.FourFragment;
import com.qmwl.zyjx.fragment.MainFragment;
import com.qmwl.zyjx.fragment.SecondFragment;
import com.qmwl.zyjx.fragment.ThreadFragment;
import com.qmwl.zyjx.runtimepermissions.PermissionsManager;
import com.qmwl.zyjx.runtimepermissions.PermissionsResultAction;
import com.qmwl.zyjx.utils.CProgressDialogUtils;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.EventManager;
import com.qmwl.zyjx.utils.GsonUtils;
import com.qmwl.zyjx.utils.OkGoUpdateHttpUtil;
import com.qmwl.zyjx.view.CommomDialog;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;
import com.vector.update_app.listener.ExceptionHandler;
import com.vector.update_app.listener.IUpdateDialogFragmentListener;

import org.json.JSONException;
import org.json.JSONObject;

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
    private Context mContext;
    @Override
    protected void setLayout() {
        setContentLayout(R.layout.activity_main);
    }



    /**
     * 更新APP版本
     * @param
     */
    public void updateDiy() {
        new UpdateAppManager
                .Builder()
                //必须设置，当前Activity
                .setActivity(this)
                //必须设置，实现httpManager接口的对象
                .setHttpManager(new OkGoUpdateHttpUtil())
                //必须设置，更新地址
                .setUpdateUrl(Contact.update)
                //全局异常捕获
                .handleException(new ExceptionHandler() {
                    @Override
                    public void onException(Exception e) {
                        e.printStackTrace();
                    }
                })
                .setTopPic(R.mipmap.lib_update_app_top_bg)
                //为按钮，进度条设置颜色。
                .setThemeColor(0xffffac5d)
                .setUpdateDialogFragmentListener(new IUpdateDialogFragmentListener() {
                    @Override
                    public void onUpdateNotifyDialogCancel(UpdateAppBean updateApp) {
                        //用户点击关闭按钮，取消了更新，如果是下载完，用户取消了安装，则可以在 onActivityResult 监听到。

                    }
                })
                //不自动，获取
                .setIgnoreDefParams(true)
                .build()
                //检测是否有新版本
                .checkNewApp(new UpdateCallback() {
                    /**
                     * 解析json,自定义协议
                     *
                     * @param json 服务器返回的json
                     * @return UpdateAppBean
                     */
                    @Override
                    protected UpdateAppBean parseJson(String json) {
                        Logger.d("版本更新的json:"+json);
                        UpdateAppBean updateAppBean = new UpdateAppBean();
                        try {
                            UpdateBean updateBean = GsonUtils.parseJsonToBean(json, UpdateBean.class);
                            String versionNum = updateBean.getData().getNiu_index_response().getNew_version();

                            PackageInfo packageInfo = getPackageManager()
                                    .getPackageInfo(getPackageName(), 0);
                            //获取APP版本versionCode
                            int app_VersionCode = packageInfo.versionCode;
                            int versionCode = Integer.parseInt(versionNum);
                            if (app_VersionCode < versionCode)  {
                                updateAppBean.setUpdate("Yes");
                            }else{
                                updateAppBean.setUpdate("no");
                            }
                            if (updateBean.getData().getNiu_index_response().getConstraint() == 0) {
                                updateAppBean.setConstraint(false);
                            }else{
                                updateAppBean.setConstraint(true);
                            }
                            updateAppBean
                                    //（必须）是否更新Yes,No
                                    //（必须）新版本号，
                                    .setNewVersion(updateBean.getData().getNiu_index_response().getNew_version())
                                    //（必须）下载地址
                                    .setApkFileUrl(updateBean.getData().getNiu_index_response().getApk_file_url())
                                    .setUpdateLog("版本更新："+updateBean.getData().getNiu_index_response().getUpdate_log())
                                    //大小，不设置不显示大小，可以不设置
                                    .setTargetSize(updateBean.getData().getNiu_index_response().getTarget_size());
                            //是否强制更新，可以不设置
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return updateAppBean;
                    }

                    @Override
                    protected void hasNewApp(UpdateAppBean updateApp, UpdateAppManager updateAppManager) {
                        updateAppManager.showDialogFragment();
                    }

                    /**
                     * 网络请求之前
                     */
                    @Override
                    public void onBefore() {
                        CProgressDialogUtils.showProgressDialog(MainActivity.this);
                    }

                    /**
                     * 网路请求之后
                     */
                    @Override
                    public void onAfter() {
                        CProgressDialogUtils.cancelProgressDialog(MainActivity.this);
                    }

                    /**
                     * 没有新版本
                     */
                    @Override
                    public void noNewApp(String error) {
//                        Toast.makeText(MainActivity.this, "没有新版本", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    protected void initView() {
        isOutLogin();
         //getWoYaokaidianStatue1

        //应用更新
        updateDiy();
        mContext=this;

        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                //  Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
                Log.d("huangrui","友盟收到的消息推送zi定义通知:"+msg.custom);

                Log.d("huangrui","msg.after_open的值:"+msg.after_open);
                if ("go_app".equals(msg.after_open)){
                    startActivity(new Intent(context,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

                }else if("go_custom".equals(msg.after_open)){
                   // JsonObject jsonObject= new JsonObject(msg.custom);
                    //jsonObject.get("");
                    try {
                        org.json.JSONObject obj = new org.json.JSONObject(msg.custom);
                        String orderId=obj.get("orderId")+"";
                        String orderStatus=obj.get("orderStatus")+"";
                        String url=obj.get("url")+"";
                        Log.d("huangrui","获取到的status"+orderStatus);
                        if (!TextUtils.isEmpty(url)){
                            // 跳转我的店铺界面,到主界面接收umeng，跳转到第四个fragment
                        //    setCurrItem(3);
                            Log.d("huangrui","跳转到Main" );
                            startActivity(new Intent(context,MainActivity.class).putExtra("umeng",3).putExtra(MAIN_INDEX,3).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
                            //点击跳转我的店铺界面
                            //    String url=getIntent().getStringExtra("umeng");
                           /* if(EasyUtils.isAppRunningForeground(mContext)){
                                LogUtils.d("应用在前台");

                            }else{
                                LogUtils.d("应用在后台");

                            }*/

                            getWoYaokaidianStatue1();
                         //   EventManager.post("umeng",url);
                        }else{

                        //使用方法三
                        //应用在后台
                        if ("0".equals(orderStatus)) {
                            //待付款
                            startActivity(new Intent(context,WoDeDingDanActivity.class).putExtra("index",1).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        }else if ("1".equals(orderStatus)){
                            //申请退款
                            startActivity(new Intent(context,WoDeDingDanActivity.class).putExtra("index",5).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
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
                            startActivity(new Intent(context,MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                        }

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
        int intExtra = getIntent().getIntExtra(MAIN_INDEX, MAIN);
        Logger.d(  "111获取到的设置的值是"+intExtra );
        setCurrItem(intExtra);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int intExtra = getIntent().getIntExtra(MAIN_INDEX, MAIN);
        Logger.d(  "222获取到的设置的值是"+intExtra );
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

    //获取个人信息状态
    private void getWoYaokaidianStatue1() {
        AndroidNetworking.get(Contact.kaidian_url + "?user_id=" + MyApplication.getIntance().userBean.getUid())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                         try {
                            //0 未开通 1此用户对公付款 2 此用户是商户 3提交资料，未到支付
                            int code = response.getInt("code");
                            if (code==2){
                                Intent intent = new Intent( mContext, WebViewShangJiaZhongXinActivity.class);
                                intent.putExtra(WebViewActivity.SHOPURL, Contact.shangjiazhongxin_url + "?user_id=" + MyApplication.getIntance().userBean.getUid());
                                startActivity(intent);
                            }else{
                                String message = response.getString("message");
                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                            }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }
}
