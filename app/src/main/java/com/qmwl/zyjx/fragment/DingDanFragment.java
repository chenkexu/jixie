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

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.activity.NewsDetailsActivity;
import com.qmwl.zyjx.activity.WoDeDingDanActivity;
import com.qmwl.zyjx.adapter.DingDanAdapter;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.DingDanBean;
import com.qmwl.zyjx.bean.ShoppingBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;

import org.json.JSONObject;

import java.util.ArrayList;
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

    public void setData(final List<DingDanBean> alist,int index) {
        if (adapter != null) {

            if (index==5){
               /* this.list = list;
                adapter.setData(this.list);*/
                AndroidNetworking.get(Contact.wodedingdan_list + "?uid=" + MyApplication.getIntance().userBean.getUid()+"?status=-1")
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                final List<DingDanBean> dingDanBeen = JsonUtils.parseDingDan(response);
                                final List<DingDanBean> mlist  = new ArrayList<DingDanBean>();
                              for (DingDanBean bean : dingDanBeen) {
                                     bean.setDingdan_statue_code(-1);
                                     mlist.add(bean);
                                   }
                                 list = mlist;
                                adapter.setData( list);
                            }

                            @Override
                            public void onError(ANError anError) {
                                 list = alist;
                                adapter.setData( list);
                            }
                        });


            }else{
                this.list = alist;
                adapter.setData(this.list);
            }


        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        DingDanBean item = adapter.getItem(position);



        if (item != null && item.getShopList() != null && item.getShopList().size() > 0) {
            if(item.getDingdan_statue_code()==-1){
                ShoppingBean shoppingBean = item.getShopList().get(0);
                Intent intent = new Intent(getContext(), NewsDetailsActivity.class);
                intent.putExtra(NewsDetailsActivity.DETAILS_TYPE, NewsDetailsActivity.TYPE_DINGDAN);
                intent.putExtra(NewsDetailsActivity.DETAILS_URL, "http://app.qmnet.com.cn/index.php/api/order/orderTuiDetail?orderId="+item.getOrder_id());
                startActivity(intent);
            }else{

                if (item.getMa()==1||item.getMa()==2||item.getMa()==3){
                    ShoppingBean shoppingBean = item.getShopList().get(0);
                    Intent intent = new Intent(getContext(), NewsDetailsActivity.class);
                    intent.putExtra(NewsDetailsActivity.DETAILS_TYPE, NewsDetailsActivity.TYPE_DINGDAN);
                    intent.putExtra(NewsDetailsActivity.DETAILS_URL, "http://app.qmnet.com.cn/index.php/api/order/orderTuiDetail?orderId="+item.getOrder_id());
                    startActivity(intent);
                }else {
                    ShoppingBean shoppingBean = item.getShopList().get(0);
                    Intent intent = new Intent(getContext(), NewsDetailsActivity.class);
                    intent.putExtra(NewsDetailsActivity.DETAILS_TYPE, NewsDetailsActivity.TYPE_DINGDAN);
                    intent.putExtra(NewsDetailsActivity.DETAILS_URL, shoppingBean.getDingdanUrl());
                    startActivity(intent);
                }
            }

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
