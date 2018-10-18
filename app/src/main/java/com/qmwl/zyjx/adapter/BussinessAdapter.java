package com.qmwl.zyjx.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.LiuLanBean;

/**
 * Created by Administrator on 2017/7/26.
 */

public class BussinessAdapter extends MyBaseAdapter<LiuLanBean> {
    @Override
    public int getCount() {
        return 10;
    }

    @Override
    protected View getItemView(int position, View convertView, ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.bussiness_layout_item, null);
        return inflate;
    }
}
