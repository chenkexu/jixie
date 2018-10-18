package com.qmwl.zyjx.fragment;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.activity.WebViewActivity;
import com.qmwl.zyjx.adapter.ZuLingAdapter;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.ZuLinListBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 */

@SuppressLint("ValidFragment")
public class ZuLinFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ZuLingAdapter adapter;
    private int index;

    @SuppressLint("ValidFragment")
    public ZuLinFragment(int index) {
        this.index = index;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.zulin_fragment_layout, null);

        initView(inflate);
        return inflate;
    }

    private void initView(View rootView) {
        ListView mLv = (ListView) rootView.findViewById(R.id.wodezuling_layout_listview);

        adapter = new ZuLingAdapter(rootView);
        mLv.setAdapter(adapter);
        mLv.setOnItemClickListener(this);
        getData();
    }

    private void getData() {
        String url = Contact.zulin_list.concat("?uid=" + MyApplication.getIntance().userBean.getUid());
        switch (index) {
            case 0:
//                url = url.concat("&status=0");
                break;
            case 1:
                url = url.concat("&status=0");
                break;
            case 2:
                url = url.concat("&status=2");
                break;
            case 3:
                url = url.concat("&status=3");
                break;
        }
        AndroidNetworking.get(url)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<ZuLinListBean> zuLinListBeen = JsonUtils.parseZuLinList(response);
                        adapter.setData(zuLinListBeen);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(getContext(), getString(R.string.liebiaoweikong), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getContext(), WebViewActivity.class);
        intent.putExtra(WebViewActivity.SHOPURL, adapter.getItem(position).getUrl());
        startActivity(intent);
    }
}
