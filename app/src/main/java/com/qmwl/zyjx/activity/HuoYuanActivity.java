package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.TransportAdapter;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.TransportBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.utils.ListViewPullListener;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2017/8/28.
 * 运输货源页面
 */

public class HuoYuanActivity extends BaseActivity implements AdapterView.OnItemClickListener, ListViewPullListener.ListViewPullOrLoadMoreListener {
    private TransportAdapter adapter;
    public static final String IS_TUIJIAN = "com.gh.huoyuan_xitongtuijian";
    //默认是推荐页面
    private boolean isTuiJian = true;
    private ListViewPullListener listViewPullListener;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.transport_activity_layout);
    }

    @Override
    protected void initView() {
        isTuiJian = getIntent().getBooleanExtra(IS_TUIJIAN, true);
        if (isTuiJian) {
            setTitleContent(R.string.xitongtuijian);
        } else {
            setTitleContent(R.string.huoyuanguangchang);
        }
        adapter = new TransportAdapter(this);
        ListView lv = (ListView) findViewById(R.id.transport_layout_listview);
        lv.setOnItemClickListener(this);
        lv.setAdapter(adapter);

        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.guangchang_layout_swiprefreshlayout);

        listViewPullListener = new ListViewPullListener(lv, swipeRefreshLayout, this);

    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {
        getData();
    }

    @Override
    protected void onMyClick(View v) {

    }

    @Override
    public void finish() {
        super.finish();
        if (adapter != null) {
            adapter.cancelRunnable();
        }
    }

    private void getData() {
        String url = "";
        if (isTuiJian) {
            url = Contact.yunshu_xitongtuijian + "?page=" + page;
        } else {
            url = Contact.huoyuanliebiao_list + "?page=" + page;
        }
        AndroidNetworking.get(url)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listViewPullListener.cancelPullRefresh();
                        List<TransportBean> list = JsonUtils.parseHuoYuanList(response);
                        if (isLoadMore) {
                            listViewPullListener.openLoadMore();
                            isLoadMore = false;
                            adapter.addData(list);
                        } else if (list != null && list.size() > 0) {
                            adapter.setData(list);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        listViewPullListener.cancelPullRefresh();
                        if (isLoadMore) {
                            listViewPullListener.openLoadMore();
                            isLoadMore = false;
                            page--;
                            if (page < 1) {
                                page = 1;
                            }
                        }
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!MyApplication.getIntance().isLogin()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, 0);
            return;
        }
        TransportBean item = adapter.getItem(position);
        Intent intent = new Intent(this, YunDanDetailsActivity.class);
        intent.putExtra("tid", item.getT_id());
        startActivity(intent);
    }


    private int page = 1;//分页

    @Override
    public void onPullRefresh() {
        if (!isLoadMore) {
            page = 1;
            getData();
        }
//        adapter.notifyDataSetChanged();
    }

    private boolean isLoadMore = false;

    @Override
    public void onLoadMore() {
        if (adapter == null || adapter.getData() == null) {
            return;
        }
        isLoadMore = true;
        page++;
        listViewPullListener.cancelLoadMore();
        getData();
    }
}
