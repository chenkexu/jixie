package com.qmwl.zyjx.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.activity.YunDanDetailsActivity;
import com.qmwl.zyjx.adapter.WoDeYunDanAdapter;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.YunDanBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.utils.ListViewPullListener;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 * 我的运单fragment
 */

public class WoDeYunDanFragment extends Fragment implements AdapterView.OnItemClickListener, ListViewPullListener.ListViewPullOrLoadMoreListener {
    private WoDeYunDanAdapter adapter;
    private boolean isChengYunFang = false;
    private int statue;
    private ListViewPullListener listViewPullListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.zulin_fragment_layout, null);
        initView(inflate);

        return inflate;
    }

    private void initView(View inflate) {
        receiver = new MyUpDateReceiver();
        IntentFilter intentFilter = new IntentFilter(UPDATE_DATA);
        getActivity().registerReceiver(receiver,intentFilter);

        ListView mLv = (ListView) inflate.findViewById(R.id.wodezuling_layout_listview);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) inflate.findViewById(R.id.yundan_layout_swiprefreshlayout);

        listViewPullListener = new ListViewPullListener(mLv, swipeRefreshLayout, this);
        Bundle arguments = getArguments();
        statue = arguments.getInt("statue");
        isChengYunFang = arguments.getBoolean("isyundan", false);
        adapter = new WoDeYunDanAdapter(getContext());

        if (isChengYunFang) {
            adapter = new WoDeYunDanAdapter(getContext(), this);
            adapter.setChengYunFang(isChengYunFang);
            getChengYunYunDanData(statue);
        } else {
            getData(statue);
        }
        mLv.setAdapter(adapter);
        mLv.setOnItemClickListener(this);
    }

    public void update() {
        if (isChengYunFang) {
            getChengYunYunDanData(statue);
        } else {
            getData(statue);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (adapter != null) {
            adapter.cancelRunnable();
        }
        if (receiver!=null){
            getActivity().unregisterReceiver(receiver);
        }
    }

    private void getData(final int status) {
        String url = "";
        if (status == 4) {
            url = Contact.wodeyundan_huozhu + "?uid=" + MyApplication.getIntance().userBean.getUid() + "&page=" + page;
        } else {
            url = Contact.wodeyundan_huozhu + "?uid=" + MyApplication.getIntance().userBean.getUid() + "&status=" + status + "&page=" + page;
        }
        AndroidNetworking.get(url)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listViewPullListener.cancelPullRefresh();
                        List<YunDanBean> list = JsonUtils.parseYunDanJson(response);
                        if (isLoadMore) {
                            if (list==null){
                                page--;
                            }else if(list.size()==0){
                                page--;
                            }
                            listViewPullListener.openLoadMore();
                            isLoadMore = false;
                            adapter.addData(list);
                        }else{
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

    //获取(承运方)运单数据
    private void getChengYunYunDanData(final int statue) {
        String url = "";
        if (statue == 10) {
            url = Contact.yunshu_chengyun_chengyunfang_wodeyundan + "?uid=" + MyApplication.getIntance().userBean.getUid() + "&page=" + page;
//            url = Contact.yunshu_chengyun_chengyunfang_wodeyundan + "?uid=" + "371";
        } else {
            url = Contact.yunshu_chengyun_chengyunfang_wodeyundan + "?uid=" + MyApplication.getIntance().userBean.getUid() + "&status=" + statue + "&page=" + page;
//            url = Contact.yunshu_chengyun_chengyunfang_wodeyundan + "?uid=" + "371" + "&status=" + statue;
        }
        AndroidNetworking.get(url)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listViewPullListener.cancelPullRefresh();
                        List<YunDanBean> list = JsonUtils.parseChengYunYunDanJson(response);
                        if (isLoadMore) {
                            if (list==null){
                                page--;
                            }else if(list.size()==0){
                                page--;
                            }
                            listViewPullListener.openLoadMore();
                            isLoadMore = false;
                            adapter.addData(list);
                        } else {
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
        YunDanBean item = adapter.getItem(position);
        Intent intent = new Intent(getContext(), YunDanDetailsActivity.class);
        intent.putExtra("isyundandetails", true);
        if (isChengYunFang){
            intent.putExtra("tid", item.getId());
        }else{
            intent.putExtra("tid", item.getTid());
        }
        intent.putExtra("ischengyunfang",isChengYunFang);
        startActivity(intent);
    }

    private int page = 1;//分页

    @Override
    public void onPullRefresh() {
        if (!isLoadMore) {
            page = 1;
            update();
        }
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
//        getData(statue);
        update();
    }


    public static final String UPDATE_DATA = "com.wodeyundan_chengyunfang_receiver";
    private MyUpDateReceiver receiver;

    class MyUpDateReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            page=1;
            update();
        }
    }
}
