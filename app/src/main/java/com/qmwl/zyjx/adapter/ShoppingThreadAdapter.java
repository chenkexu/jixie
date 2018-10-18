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
 * Created by Administrator on 2017/7/26.
 * 商品页面bean
 */

public class ShoppingThreadAdapter extends MyBaseAdapter<ShoppingBean> {
    @Override
    protected View getItemView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_thread_item_layout, null);
        }
        ViewHolder holder = getHolder(convertView);
        ShoppingBean item = getItem(position);
        holder.titleTv.setText(item.getName());
        holder.priceTv.setText(String.valueOf(item.getPrice()));
        holder.addressTv.setText(item.getAddress());
        openImage(parent, item.getIv_pic(), holder.iv);
        panduanbiaoqian(holder, item, parent);


        return convertView;
    }

    private void panduanbiaoqian(ViewHolder holder, ShoppingBean item, ViewGroup parent) {
        if (item.getIs_parts() == 1) {
            //配件
            holder.biaoqian.setText(parent.getContext().getString(R.string.peijian));
        } else if (item.getIs_lease() == 1) {
            //租赁
            holder.biaoqian.setText(parent.getContext().getString(R.string.zulin));
        } else if (item.getIsNew() == 0) {
            //二手机
            holder.biaoqian.setText(parent.getContext().getString(R.string.ershouji));
        } else {
            //新机
            holder.biaoqian.setText(parent.getContext().getString(R.string.xinji));
        }

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
            biaoqian = (TextView) convertView.findViewById(R.id.shopping_thread_item_biaoqian);
            convertView.setTag(this);
        }

        ImageView iv;
        TextView titleTv, priceTv, addressTv, biaoqian;
    }

}
