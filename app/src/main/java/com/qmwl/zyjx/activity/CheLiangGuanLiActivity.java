package com.qmwl.zyjx.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.CheLiangGuanLiAdapter;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.CheLiangBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.view.CommomDialog;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2017/8/29.
 * 车辆管理页面
 */

public class CheLiangGuanLiActivity extends BaseActivity {
    public static final String CHELIANG_ACTION = "com.gh.cheliangguanli";
    private CheLiangGuanLiAdapter adapter;

    private CheLiangBroastReceiver receiver;
    private boolean isPause = false;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.cheliangguanli_layout);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.cheliangguanli);
        adapter = new CheLiangGuanLiAdapter();
        ListView mLv = (ListView) findViewById(R.id.cheliangguanli_layout_listview);
        findViewById(R.id.cheliangguanli_layout_add).setOnClickListener(this);
        mLv.setAdapter(adapter);
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {
        getDataList();
    }


    @Override
    protected void onMyClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.cheliangguanli_layout_add:
                intent = new Intent(this, AddCheLiangActivity.class);
                startActivity(intent);
                break;

        }
    }

    private void getDataList() {
        AndroidNetworking.get(Contact.chengyun_chelianglist + "?uid=" + MyApplication.getIntance().userBean.getUid())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<CheLiangBean> cheLiangBeen = JsonUtils.parseCheLiangList(response);
                        adapter.setData(cheLiangBeen);
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isPause) {
            isPause = false;
            getDataList();
        }
        receiver = new CheLiangBroastReceiver();
        IntentFilter intentFilter = new IntentFilter(CHELIANG_ACTION);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isPause = true;
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }

    class CheLiangBroastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, final Intent intent) {
            new CommomDialog(context, R.style.dialog, context.getString(R.string.querenshanchu), new CommomDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    if (confirm) {
                        int position = intent.getIntExtra("position", -1);
                        if (position >= 0) {
                            CheLiangBean item = adapter.getData().remove(position);
                            adapter.notifyDataSetChanged();
                            delete(item.getId());
                        }
                        dialog.dismiss();
                    }
                }
            }).show();

        }
    }

    private void delete(String id) {
        AndroidNetworking.get(Contact.cheliang_delete + "?id=" + id + "&uid=" + MyApplication.getIntance().userBean.getUid())
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
