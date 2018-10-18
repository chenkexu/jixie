package com.qmwl.zyjx.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.FabuAdapter;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.FabuBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.utils.ListViewPullListener;
import com.qmwl.zyjx.view.CommomDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2017/8/17.
 * 我的发帖
 */

public class WoDeFaTieActivity extends BaseActivity implements AdapterView.OnItemClickListener, ListViewPullListener.ListViewPullOrLoadMoreListener {
    private FabuAdapter adapter;
    private ListViewPullListener listViewPullListener;
    public static final String FATIE_DELETE = "com.gh.wodefatie.delete";

    private int page = 1;
    private boolean isLoadMore;
    private boolean isFirst = true;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.wodefatie_layout);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.wodefatie);
        ListView mLv = (ListView) findViewById(R.id.wodefatie_layout_listview);
        mLv.setOnItemClickListener(this);
        adapter = new FabuAdapter();
        mLv.setAdapter(adapter);
//        mLv.setOnItemClickListener(this);

        listViewPullListener = new ListViewPullListener(mLv, null, this);
//        listViewPullListener.cancelLoadMore();

    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {
        getData();
    }

    private void getData() {
        AndroidNetworking.get(Contact.fatie_list + "?user_id=" + MyApplication.getIntance().userBean.getUid() + "&page=" + page)
//                .addBodyParameter()
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        listViewPullListener.cancelPullRefresh();
                        try {
                            List<FabuBean> fabuBeen = JsonUtils.parseTieZiJson(response);
                            if (isLoadMore) {
                                isLoadMore = false;
                                adapter.addData(fabuBeen);
                            } else {
                                adapter.setData(fabuBeen);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
//                        listViewPullListener.cancelPullRefresh();
                    }
                });
    }

    private DeleteReceiver receiver;

    @Override
    protected void onResume() {
        super.onResume();
        page = 1;
        getData();
        receiver = new DeleteReceiver();
        IntentFilter intentFilter = new IntentFilter(FATIE_DELETE);
        registerReceiver(receiver, intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
        }
    }

    @Override
    protected void onMyClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        Intent intent = new Intent(parent.getContext(), NewsDetailsActivity.class);
//        intent.putExtra(NewsDetailsActivity.DETAILS_URL, adapter.getItem(position).getUrl());
//        intent.putExtra(NewsDetailsActivity.DETAILS_TYPE, NewsDetailsActivity.TYPE_TIEZI);
//        startActivity(intent);

        FabuBean item = adapter.getItem(position);
        Intent intent = new Intent(parent.getContext(), FaTieActivity.class);
        intent.putExtra(FaTieActivity.POST_ID, item.getPost_id());
        intent.putExtra(FaTieActivity.IS_EDIT, true);
        startActivity(intent);
    }

    @Override
    public void onPullRefresh() {

    }

    @Override
    public void onLoadMore() {
        isLoadMore = true;
        page++;
        getData();
    }

    class DeleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            final String post_id = intent.getStringExtra("post_id");

            new CommomDialog(context, R.style.dialog, getString(R.string.quedingshanchugaitie), new CommomDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    if (confirm) {
                        delete(post_id);
                    }
                }
            }).show();

        }
    }

    private void delete(String post_id) {
        AndroidNetworking.get(Contact.tiezi_delete_url + "?uid=" + MyApplication.getIntance().userBean.getUid() + "&post_id=" + post_id)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        try {
                            if (JsonUtils.isSuccess(response)) {
                                page = 1;
                                isLoadMore = false;
                                getData();
                                Toast.makeText(WoDeFaTieActivity.this, getString(R.string.shanchuchenggong), Toast.LENGTH_SHORT).show();
//                                new CommomDialog(WoDeFaTieActivity.this, R.style.dialog, getString(R.string.shanchuchenggong)).setHideCancelButton().show();
                            } else {
                                Toast.makeText(WoDeFaTieActivity.this, getString(R.string.shanchushibai_chognshi), Toast.LENGTH_SHORT).show();
//                                new CommomDialog(WoDeFaTieActivity.this, R.style.dialog, getString(R.string.shanchushibai_chognshi)).setHideCancelButton().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(WoDeFaTieActivity.this, getString(R.string.shanchushibai_chognshi), Toast.LENGTH_SHORT).show();
//                            new CommomDialog(WoDeFaTieActivity.this, R.style.dialog, getString(R.string.shanchushibai_chognshi)).setHideCancelButton().show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                        new CommomDialog(WoDeFaTieActivity.this, R.style.dialog, getString(R.string.wangluocuowu)).setHideCancelButton().show();
                    }
                });
        showLoadingDialog();
    }

}
