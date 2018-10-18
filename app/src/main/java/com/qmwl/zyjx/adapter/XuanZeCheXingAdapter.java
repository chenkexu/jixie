package com.qmwl.zyjx.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.CheXingBean;

/**
 * Created by Administrator on 2017/8/29.
 */

public class XuanZeCheXingAdapter extends MyBaseAdapter<CheXingBean> {

    @Override
    protected View getItemView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.xuanzechexing_item, null);
        TextView nameTv = (TextView) convertView.findViewById(R.id.xuanzechexing_item_textview);
        CheXingBean item = getItem(position);
        nameTv.setText(item.getName());
        return convertView;
    }
}
