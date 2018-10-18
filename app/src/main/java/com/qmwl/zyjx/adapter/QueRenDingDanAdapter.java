package com.qmwl.zyjx.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.ShoppingBean;

/**
 * Created by Administrator on 2017/7/27.
 * //确认订单内部adapter
 */

public class QueRenDingDanAdapter extends MyBaseAdapter<ShoppingBean> {

    @Override
    protected View getItemView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.querendingdan_item_nei, null);
        }
        ViewHolder holder = getHolder(convertView);
        ShoppingBean item = getItem(position);
        holder.name.setText(item.getName());
        holder.number.setText("x"+item.getNumber());
        holder.price.setText("￥" + item.getPrice());
        holder.type.setText(item.getModel());
        holder.yunfei.setText(getResouseString(parent,R.string.yunfei)+":￥"+item.getYunfei());
        openImage(parent, item.getIv_pic(), holder.iv);

        return convertView;
    }

    private ViewHolder getHolder(View convertView) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (holder == null) {
            holder = new ViewHolder(convertView);
        }
        return holder;
    }

    class ViewHolder {
        ImageView iv;
        TextView name, type, number, price,yunfei;

        ViewHolder(View convertView) {
            name = (TextView) convertView.findViewById(R.id.querendingdan_item_item_name);
            type = (TextView) convertView.findViewById(R.id.querendingdan_item_item_type);
            number = (TextView) convertView.findViewById(R.id.querendingdan_item_item_number);
            price = (TextView) convertView.findViewById(R.id.querendingdan_item_item_price);
            iv = (ImageView) convertView.findViewById(R.id.querendingdan_item_iv);
            yunfei = (TextView) convertView.findViewById(R.id.querendingdan_item_item_yunfei);
            convertView.setTag(this);
        }
    }

}
