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
 * 浏览
 */

public class LiuLanAdapter extends MyBaseAdapter<ShoppingBean> {

    private boolean isLiuLan = false;

    public void setIsLiuLan() {
        isLiuLan = true;
        notifyDataSetChanged();
    }

    @Override
    protected View getItemView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.liulanjilu_layout_item, null);
        }
        ShoppingBean item = getItem(position);
        ViewHolder holder = getHolder(convertView);
        holder.nameTv.setText(item.getName());
        holder.priceTv.setText("￥" + item.getPrice());
        openImage(parent, item.getIv_pic(), holder.iv);
        if (isLiuLan) {
            holder.shopName.setText(item.getShopName());
            openImage(parent, item.getLogo_pic(), holder.logoiv);
        }


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
        ImageView iv, logoiv;
        TextView nameTv, priceTv, shopName;
        View headView;

        ViewHolder(View convertView) {
            iv = (ImageView) convertView.findViewById(R.id.liulanjilu_item_iv);
            logoiv = (ImageView) convertView.findViewById(R.id.liulanjilu_layout_headiv);
            headView = convertView.findViewById(R.id.liulanjilu_layout_head_container);
            nameTv = (TextView) convertView.findViewById(R.id.liulanjilu_item_chanpinname);
            priceTv = (TextView) convertView.findViewById(R.id.liulanjilu_item_chanpinjine);
            shopName = (TextView) convertView.findViewById(R.id.liulanjilu_layout_headname);
            priceTv = (TextView) convertView.findViewById(R.id.liulanjilu_item_chanpinjine);
            if (isLiuLan) {
                headView.setVisibility(View.VISIBLE);
            } else {
                headView.setVisibility(View.GONE);
            }
            convertView.setTag(this);
        }
    }

}
