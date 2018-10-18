package com.qmwl.zyjx.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.activity.BlackListActivity;
import com.qmwl.zyjx.activity.FaTieActivity;
import com.qmwl.zyjx.activity.LoginActivity;
import com.qmwl.zyjx.activity.MainActivity;
import com.qmwl.zyjx.activity.NewsDetailsActivity;
import com.qmwl.zyjx.activity.TieZiSearchActivity;
import com.qmwl.zyjx.adapter.FabuAdapter;
import com.qmwl.zyjx.adapter.TieZiListAdapter;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.FabuBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.utils.ListViewPullListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2017/7/19.
 * 帖子列表
 */

public class SecondFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, ListViewPullListener.ListViewPullOrLoadMoreListener {
    private ListView mLv;
    private ListViewPullListener listViewPullListener;
    private TieZiListAdapter adapter;
    private int page = 1;
    private boolean isLoadMore;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fabu_fragment_layout, null);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        mLv = (ListView) rootView.findViewById(R.id.fabu_fragment_listview);
        rootView.findViewById(R.id.fabu_fragment_fabu).setOnClickListener(this);
        rootView.findViewById(R.id.fabu_top_bar_search).setOnClickListener(this);
        rootView.findViewById(R.id.fabu_fragment_heimingdan).setOnClickListener(this);
        rootView.findViewById(R.id.fabu_fragment_layout_fatiezi).setOnClickListener(this);
        rootView.findViewById(R.id.fabu_fragment_fatieradiobutton).setOnClickListener(this);
        rootView.findViewById(R.id.fabu_fragment_layout_heimingdan).setOnClickListener(this);
        rootView.findViewById(R.id.fabu_fragment_heimingdanradiobutton).setOnClickListener(this);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.fabu_fragment_swipeRefreshLayout);

        adapter = new TieZiListAdapter();
        mLv.setAdapter(adapter);
        mLv.setOnItemClickListener(this);
        getTieZiList();
        listViewPullListener = new ListViewPullListener(mLv, swipeRefreshLayout, this);
    }

    //获取帖子列表


    private void getTieZiList() {
        AndroidNetworking.post(Contact.tiezi_list)
                .addBodyParameter("page", String.valueOf(page))
//                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listViewPullListener.cancelPullRefresh();
                        // do anything with response
                        try {
                            List<FabuBean> fabuBeen = JsonUtils.parseTieZiJson(response);
                            if (isLoadMore) {
                                if (fabuBeen == null || fabuBeen.size() == 0) {
                                    listViewPullListener.cancelLoadMore();
                                }
                                adapter.addData(fabuBeen);
                                isLoadMore = false;

                            } else {
                                adapter.setData(fabuBeen);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                        listViewPullListener.cancelPullRefresh();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.fabu_fragment_layout_fatiezi:
            case R.id.fabu_fragment_fabu:
            case R.id.fabu_fragment_fatieradiobutton:
                if (MyApplication.getIntance().isLogin()) {
                    intent = new Intent(getContext(), FaTieActivity.class);
                    getActivity().startActivityForResult(intent, MainActivity.TIEZI);
                } else {
                    intent = new Intent(getContext(), LoginActivity.class);
                    getActivity().startActivityForResult(intent, 0);
                }
                break;
            case R.id.fabu_fragment_layout_heimingdan:
            case R.id.fabu_fragment_heimingdan:
            case R.id.fabu_fragment_heimingdanradiobutton:
                intent = new Intent(getContext(), BlackListActivity.class);
                startActivity(intent);
                break;
            case R.id.fabu_top_bar_search:
                intent = new Intent(getContext(), TieZiSearchActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.activity_in_anim, R.anim.activity_out_anim);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(parent.getContext(), NewsDetailsActivity.class);
        intent.putExtra(NewsDetailsActivity.DETAILS_URL, adapter.getItem(position).getUrl());
        intent.putExtra(NewsDetailsActivity.DETAILS_TYPE, NewsDetailsActivity.TYPE_TIEZI);
        startActivity(intent);
    }

    @Override
    public void onLoadMore() {
        if (adapter != null && adapter.getData() != null) {
            isLoadMore = true;
            page++;
            getTieZiList();
        }

    }

    @Override
    public void onPullRefresh() {
        page = 1;
        getTieZiList();
    }

}
