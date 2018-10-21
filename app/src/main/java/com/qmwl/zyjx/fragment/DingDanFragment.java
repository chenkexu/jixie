package com.qmwl.zyjx.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.activity.NewsDetailsActivity;
import com.qmwl.zyjx.activity.WoDeDingDanActivity;
import com.qmwl.zyjx.adapter.DingDanAdapter;
import com.qmwl.zyjx.bean.DingDanBean;
import com.qmwl.zyjx.bean.ShoppingBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 * 我的订单
 */

public class DingDanFragment extends Fragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {
    private DingDanAdapter adapter;
    private View inflate;
    private List<DingDanBean> list;
    private SwipeRefreshLayout swipeRefreshLayout;




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.zulin_fragment_layout, null);
        initView(inflate);
        return inflate;
    }







    private void initView(View rootView) {
        ListView mLv = (ListView) rootView.findViewById(R.id.wodezuling_layout_listview);
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.yundan_layout_swiprefreshlayout);
        swipeRefreshLayout.setOnRefreshListener(this);
        adapter = new DingDanAdapter(inflate,getActivity());
        mLv.setAdapter(adapter);
        mLv.setOnItemClickListener(this);
    }

    public void setData(List<DingDanBean> list) {
        if (adapter != null) {
            this.list = list;
            adapter.setData(this.list);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        DingDanBean item = adapter.getItem(position);
        if (item != null && item.getShopList() != null && item.getShopList().size() > 0) {
            ShoppingBean shoppingBean = item.getShopList().get(0);
            Intent intent = new Intent(getContext(), NewsDetailsActivity.class);
            intent.putExtra(NewsDetailsActivity.DETAILS_TYPE, NewsDetailsActivity.TYPE_DINGDAN);
            intent.putExtra(NewsDetailsActivity.DETAILS_URL, shoppingBean.getDingdanUrl());
            startActivity(intent);
        }
    }

    @Override
    public void onRefresh() {
        WoDeDingDanActivity activity = (WoDeDingDanActivity) getActivity();
        activity.getData();
    }

    public void cancelRefresh() {
        if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
            swipeRefreshLayout.setRefreshing(false);
        }
    }



}
