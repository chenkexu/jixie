package com.qmwl.zyjx.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.activity.NewsActivity;
import com.qmwl.zyjx.activity.SearchaActivity;
import com.qmwl.zyjx.activity.ShoppingThreadActivity;
import com.qmwl.zyjx.adapter.MainFragmentAdapter;

/**
 * Created by Administrator on 2017/7/18.
 *  产品二级页面
 */

public class SecondActivityFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private View rootView;
    private ListView mLv;
    private View headerView;//头部文件
    private FlowFragment flowFragment;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return rootView;
    }



    private void initView() {
        mLv = (ListView) rootView.findViewById(R.id.main_fragment_listview);
        headerView = LayoutInflater.from(getContext()).inflate(R.layout.main_fragment_header_layout, null);
        headerView.findViewById(R.id.main_fragment_zhongyaozixun).setOnClickListener(this);
        View searchView = rootView.findViewById(R.id.base_top_bar_search);
        TextView barTitleTv = (TextView) rootView.findViewById(R.id.base_top_bar_title);
        searchView.setFocusable(false);
        searchView.setOnClickListener(this);
        mLv.addHeaderView(headerView);
        MainFragmentAdapter adapter = new MainFragmentAdapter();
        mLv.setAdapter(adapter);

        flowFragment = new FlowFragment();
        mLv.setOnItemClickListener(this);
        getFragmentManager().beginTransaction().add(R.id.main_fragment_head_flow_container, flowFragment).commit();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        rootView = inflater.inflate(R.layout.main_fragment_layout, null);
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (flowFragment != null) {
            flowFragment.startFlow();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (flowFragment != null) {
            flowFragment.stopFlow();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.main_fragment_zhongyaozixun:
                intent = new Intent(getContext(), NewsActivity.class);
                startActivity(intent);
                break;
            case R.id.base_top_bar_search:
                intent = new Intent(getContext(), SearchaActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.activity_in_anim, R.anim.activity_out_anim);
                break;

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(parent.getContext(), ShoppingThreadActivity.class);
        startActivity(intent);
    }
}
