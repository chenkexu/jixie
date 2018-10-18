package com.qmwl.zyjx.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.ShoppingBean;
import com.qmwl.zyjx.utils.Contact;

/**
 * Created by Administrator on 2017/7/26.
 * 商家中心adapter
 */

public class ShangJiaZhongXinAdapter extends MyBaseAdapter<ShoppingBean> {
    @Override
    protected View getItemView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shangjiazhongxin_shop_item, null);
        }
        ViewHolder holder = getHolder(convertView);
        ShoppingBean item = getItem(position);
        holder.titleTv.setText(item.getName());
        holder.priceTv.setText(String.valueOf(item.getPrice()));
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
        ViewHolder(View convertView) {
            iv = (ImageView) convertView.findViewById(R.id.shopping_thread_item_iv);
            titleTv = (TextView) convertView.findViewById(R.id.shopping_thread_item_title);
            priceTv = (TextView) convertView.findViewById(R.id.shopping_thread_item_price);
            addressTv = (TextView) convertView.findViewById(R.id.shopping_thread_item_address);
            convertView.setTag(this);
        }

        ImageView iv;
        TextView titleTv, priceTv, addressTv;
    }

}
