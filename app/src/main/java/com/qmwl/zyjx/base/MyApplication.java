package com.qmwl.zyjx.base;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.hyphenate.EMCallBack;
import com.hyphenate.EMConnectionListener;
import com.hyphenate.EMError;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.EaseUI;
import com.hyphenate.easeui.domain.EaseUser;
import com.hyphenate.easeui.model.EaseNotifier;
import com.hyphenate.util.NetUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.activity.MainActivity;
import com.qmwl.zyjx.bean.HxUserData;
import com.qmwl.zyjx.bean.UserBean;
import com.qmwl.zyjx.fragment.ImListFragment;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.DBDao;
import com.qmwl.zyjx.utils.SharedUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;


public class MyApplication extends Application {
    public UserBean userBean;
    private static MyApplication myApplication;
    //存放环信用户信息的内存
    private HashMap<String, EaseUser> contactList = new HashMap<>();
    //是否是中文
    public boolean isChina;
    private DBDao dbDao;
    //是否是商家,从登陆获取
    public int is_system = 0;

    private Handler mHandler = null;
    private static MyApplication instance;


    public static PushAgent mPushAgent;

    public static MyApplication getIntance() {
        if (myApplication == null) {
            myApplication = new MyApplication();
        }
        return myApplication;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        pushMessage();
        mHandler = new Handler();
        Config.DEBUG = true;
        UMShareAPI.get(this);
        MobclickAgent.setScenarioType(MyApplication.this, MobclickAgent.EScenarioType.E_UM_NORMAL);
        UMConfigure.init(MyApplication.this, null, null, UMConfigure.DEVICE_TYPE_PHONE, "1e221fdbb93b21a7924c0e2ebf4a889b");
        AndroidNetworking.initialize(getApplicationContext());
//        IWXAPI msgApi = WXAPIFactory.createWXAPI(context, null);
//        msgApi.registerApp(Contact.wxAppid);
        myApplication = this;
        //判断是哪种语言
        panduanyuyan(getBaseContext());
        dbDao = DBDao.getIntance(getApplicationContext());
        getSharedData();
        initPush();
        initHuanXin();


        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(0)         // (Optional) How many method line to show. Default 2
                .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
                .tag("okgo")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {

        });


    }

    //获取存储的信息
    private void getSharedData() {
        boolean loginStatue = SharedUtils.getBoolean(SharedUtils.USER_LOGIN_STATUE, getApplicationContext());
        if (loginStatue) {
            userBean = new UserBean();
            userBean.setUid(SharedUtils.getString(SharedUtils.USER_UID, this));
            userBean.setUserName(SharedUtils.getString(SharedUtils.USER_NAME, this));
            userBean.setUserPassword(SharedUtils.getString(SharedUtils.USER_PASSWORD, this));
            userBean.setHeadImg(SharedUtils.getString(SharedUtils.USER_IMAGE, this));
            userBean.setNickName(SharedUtils.getString(SharedUtils.USER_NICKNAME, this));
        }

    }

    private void initPush() {
         mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
                     Log.d("huangrui","推送注册成功"+"deviceToken"+deviceToken);
                if (isLogin()) {
                    addTuiSongAlias(userBean.getUid());
                }
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.i("huangrui", "推送token:" + s + "   " + s1);
            }
        });


        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            @Override
            public void dealWithNotificationMessage(Context context, UMessage uMessage) {
                super.dealWithNotificationMessage(context, uMessage);
                Log.d("huangrui","友盟收到的消息推送:"+uMessage.custom+"uMessage.after_open:"+uMessage.after_open+"after_open"
                +uMessage.custom);
            }
        };
        mPushAgent.setMessageHandler(messageHandler);




    }

    private void initHuanXin() {
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回
        if (processAppName == null || !processAppName.equalsIgnoreCase(getApplicationContext().getPackageName())) {
            Log.e("TAG", "enter the service process!");

            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        EMOptions options = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        // options.setAcceptInvitationAlways(false);
        // 不自动登陆
        options.setAutoLogin(true);
        EaseUI.getInstance().init(this, options);
        EMClient.getInstance().chatManager().addMessageListener(msgListener);

        EaseUI.getInstance().setUserProfileProvider(new EaseUI.EaseUserProfileProvider() {

            @Override
            public EaseUser getUser(String username) {
                return getUserMessage(username);
            }
        });
        //注册一个监听连接状态的listener
        EMClient.getInstance().addConnectionListener(new MyConnectionListener());

    }

    EMMessageListener msgListener = new EMMessageListener() {
        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            //收到消息
            for (int i = 0; i < messages.size(); i++) {
                EMMessage emMessage = messages.get(i);
                String from = emMessage.getFrom();

                String nickName = emMessage.getStringAttribute(Contact.HX_USER_NICKNAME, "");

                String user_id = emMessage.getStringAttribute(Contact.HX_USER_ID, "");

                String user_image = emMessage.getStringAttribute(Contact.HX_USER_IMAGE, "");
                if (contactList != null && !contactList.containsKey(from)) {
                    EaseUser user = new EaseUser(from);
                    user.setAvatar(user_image);
                    user.setNickname(nickName);
                    contactList.put(from, user);
                    dbDao.add_userData(from, nickName, user_image);
                }
            }
            sendBroadcast(new Intent(ImListFragment.CONVER_SATION_LIST_CATION));
            getNotifier().onNewMesg(messages);
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //收到透传消息
        }

        @Override
        public void onMessageRead(List<EMMessage> messages) {
            //收到已读回执
        }

        @Override
        public void onMessageDelivered(List<EMMessage> message) {
            //收到已送达回执
        }

        @Override
        public void onMessageRecalled(List<EMMessage> messages) {
            //消息被撤回
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
            //消息状态变动
        }
    };

    //实现ConnectionListener接口
    private class MyConnectionListener implements EMConnectionListener {
        @Override
        public void onConnected() {
        }

        @Override
        public void onDisconnected(final int error) {
            if (error == EMError.USER_REMOVED) {
                // 显示帐号已经被移除
            } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                // 显示帐号在其他设备登录
            } else {
                if (NetUtils.hasNetwork(getApplicationContext())) {
                    //连接不到聊天服务器
                } else {
                    //当前网络不可用，请检查网络设置
                }
            }

            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    String str = "";

                    if (error == EMError.USER_REMOVED) {
                        // 显示帐号已经被移除
                    } else if (error == EMError.USER_LOGIN_ANOTHER_DEVICE) {
                        // 显示帐号在其他设备登录
                        MyApplication.getIntance().exitLogin();
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        intent.putExtra(MainActivity.IS_OUT_LOGING, true);
                        startActivity(intent);
                    } else {
                        if (NetUtils.hasNetwork(getApplicationContext())) {
                            //连接不到聊天服务器
                        } else {
                            //当前网络不可用，请检查网络设置
                        }
                    }
                }
            });
        }
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    private EaseUser getUserMessage(String username) {
//        Log.i("TAG","用戶username:"+username);
//        EaseUser user = new EaseUser(username);
//        user.setAvatar(imgaeUrl);
//        user.setNickname(username);
//        return user;

        //获取 EaseUser实例, 这里从内存中读取
        //如果你是从服务器中读读取到的，最好在本地进行缓存
        EaseUser user = null;
         if(EMClient.getInstance().getCurrentUser()==null){
             return null;
         }
        //如果內存中有
        if (username.equals(EMClient.getInstance().getCurrentUser())) {
            //如果用户是本人，就设置自己的头像
            user = new EaseUser(username);
            user.setNickname(SharedUtils.getString(SharedUtils.USER_NICKNAME, getApplicationContext()));
            user.setAvatar(SharedUtils.getString(SharedUtils.USER_IMAGE, getApplicationContext()));
//            contactList.put(username,user);
            return user;
        }

        if (contactList != null && contactList.containsKey(username)) {
            return contactList.get(username);
        } else {
//            內存中如果沒有,从数据库获取
            user = new EaseUser(username);
            HxUserData userData = dbDao.get_userData(username);
            if (userData != null) {
                user.setNickname(userData.getUserName());
//                user.setNickname(SharedUtils.getString(SharedUtils.USER_NAME,getApplicationContext()));
                user.setAvatar(userData.getUserImage());
//                user.setAvatar(SharedUtils.getString(SharedUtils.USER_IMAGE,getApplicationContext()));
                contactList.put(username, user);
            } else {
                user.setNickname(username);
                user.setAvatar(String.valueOf(R.drawable.ease_default_avatar));
            }
        }
        return user;
    }

    /**
     * get instance of EaseNotifier
     *
     * @return
     */
    public EaseNotifier getNotifier() {
        return EaseUI.getInstance().getNotifier();
    }

    public static void panduanyuyan(Context cx) {
        String language = SharedUtils.getLanguage(cx);
        if ("".equals(language) || TextUtils.isEmpty(language)) {
            language = cx.getResources().getConfiguration().locale.getLanguage();
        }
        if ("zh".equals(language)) {
//            Log.i("TAG", "设置中文:" + language);
            MyApplication.getIntance().isChina = true;
            //接口变为中文接口
            Contact.httpaddress = Contact.zh_http;
            Contact.resetHttpAddress();
            setChina(cx);
        } else {
//            Log.i("TAG", "设置英文:" + language);
            MyApplication.getIntance().isChina = false;
            //接口变为英文接口
            Contact.httpaddress = Contact.en_http;
            Contact.resetHttpAddress();
            setEnglish(cx);

        }
    }

    private static void setChina(Context cx) {
//        Locale.setDefault(Locale.CHINA);
        Configuration config = cx.getResources().getConfiguration();
        config.locale = Locale.CHINA;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            cx.createConfigurationContext(config);
//        } else {
        cx.getResources().updateConfiguration(config, cx.getResources().getDisplayMetrics());
//        }
    }


    private static void setEnglish(Context cx) {
//        Locale.setDefault(Locale.ENGLISH);
        Configuration config1 = cx.getResources().getConfiguration();
        config1.locale = Locale.ENGLISH;
//        getResources().updateConfiguration(config1, getResources().getDisplayMetrics());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            cx.createConfigurationContext(config1);
//        } else {
        cx.getResources().updateConfiguration(config1, cx.getResources().getDisplayMetrics());
//        }
    }


    {
        PlatformConfig.setWeixin(Contact.wxAppid, "f34c7372bd7afbb0a17a68cefcdede08");
        PlatformConfig.setQQZone("1106258275", "rXQ80ScY10J9fg2w");
        PlatformConfig.setSinaWeibo("2878974282", "5caf05f1304e26ee985741fbdf468670", "http://zhongyaojixie.com");
    }

    public boolean isLogin() {
        if (userBean != null) {
            return true;
        }
        return false;
    }

    //返回环信的本人id
    public String getHxId() {
        if (userBean != null) {
            return "zyjx" + userBean.getUid();
        }
        return "";
    }

    /**
     * 退出登录
     */
    public void exitLogin() {
        SharedUtils.putBoolean(SharedUtils.USER_LOGIN_STATUE, false, this);
        PushAgent.getInstance(this).removeAlias(userBean.getUid(), Contact.TUISONG_ALISA, new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean isSuccess, String message) {

            }
        });
        userBean = null;
        EMClient.getInstance().logout(true, new EMCallBack() {

            @Override
            public void onSuccess() {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgress(int progress, String status) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onError(int code, String message) {
                // TODO Auto-generated method stub
            }
        });

    }

    //给服务器推一条消息
    private void pushMessage() {
        AndroidNetworking.get(Contact.app_push).build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });
    }

    //添加推送的别名
    private void addTuiSongAlias(String uid) {
//        PushAgent.getInstance(this)
        PushAgent.getInstance(this).addExclusiveAlias(uid, Contact.TUISONG_ALISA, new UTrack.ICallBack() {
            @Override
            public void onMessage(boolean isSuccess, String message) {
            }
        });
    }






    
}
