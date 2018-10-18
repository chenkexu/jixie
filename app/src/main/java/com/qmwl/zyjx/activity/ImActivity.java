package com.qmwl.zyjx.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.easeui.EaseConstant;
import com.hyphenate.easeui.ui.EaseChatFragment;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.fragment.ImFragment;
import com.qmwl.zyjx.fragment.ImListFragment;
import com.qmwl.zyjx.utils.AndroidBug5497Workaround;

/**
 * Created by User on 2017/10/24.
 * 聊天页面
 */

public class ImActivity extends BaseActivity {

    public static final String IM_ID = "com.gh.im_id";
    @Override
    protected void setLayout() {
        //解决输入法遮挡输入框的问题
        AndroidBug5497Workaround.assistActivity(findViewById(android.R.id.content));
        setContentLayout(R.layout.im_activity);
        String id = getIntent().getStringExtra(IM_ID);
        ImFragment chatFragment = new ImFragment();
        //传入参数
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID,id);
        chatFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.im_container, chatFragment).commit();

//        ImListFragment huihua =   new ImListFragment();
//        huihua.setArguments(args);
//        getSupportFragmentManager().beginTransaction().add(R.id.im_container, huihua).commit();
    }

    @Override
    protected void initView() {

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

    @Override
    public void finish() {
        super.finish();
//        EMClient.getInstance().logout(true, new EMCallBack() {
//
//            @Override
//            public void onSuccess() {
//                // TODO Auto-generated method stub
//                Log.i("TAG","退出成功");
//            }
//
//            @Override
//            public void onProgress(int progress, String status) {
//                // TODO Auto-generated method stub
//
//            }
//
//            @Override
//            public void onError(int code, String message) {
//                // TODO Auto-generated method stub
//                Log.i("TAG","退出失敗");
//            }
//        });

//        new Thread(){
//            @Override
//            public void run() {
//                EMClient.getInstance().logout(true);
//            }
//        }.start();
    }
}
