package com.qmwl.zyjx.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.PingJiabean;

/**
 * Created by Administrator on 2017/7/25.
 */

public class MyPingjiaAdapter extends MyBaseAdapter<PingJiabean> {

    @Override
    protected View getItemView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.wodepinglun_layout_item, null);
        }
        ViewHolder holder = getHolder(convertView);
        PingJiabean item = getItem(position);
        openImage(parent, item.getUser_headimg(), holder.headView);
        openImage(parent, item.getGoods_image(), holder.goodsImageView);
        holder.pingjiaIv.setImageResource(getPingJia(item.getExplain_type()));
        holder.userName.setText(item.getName());
        holder.content.setText(item.getContent());
        holder.goodsName.setText(item.getGoods_name());
        holder.goodsModle.setText(item.getGoods_model());
        holder.goodsPrice.setText("￥" + item.getGoods_price());

        return convertView;
    }

    private ViewHolder getHolder(View convertView) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (holder == null) {
            holder = new ViewHolder(convertView);
        }
        return holder;
    }

    private int getPingJia(String type) {
        if ("1".equals(type)) {
            return R.mipmap.haoping;
        } else if ("2".equals(type)) {
            return R.mipmap.chaping;
        } else {
            return R.mipmap.chaping;
        }
    }

    class ViewHolder {
        ImageView headView, goodsImageView, pingjiaIv;
        TextView userName, content, goodsName, goodsModle, goodsPrice;

        ViewHolder(View convertView) {
            headView = (ImageView) convertView.findViewById(R.id.wodepingjia_item_headiv);
            pingjiaIv = (ImageView) convertView.findViewById(R.id.wodepingjia_item_pingjia_type);
            goodsImageView = (ImageView) convertView.findViewById(R.id.wodepingjia_item_goods_iv);
            userName = (TextView) convertView.findViewById(R.id.wodepingjia_item_username);
            content = (TextView) convertView.findViewById(R.id.wodepingjia_item_content);
            goodsName = (TextView) convertView.findViewById(R.id.wodepingjia_item_goods_name);
            goodsModle = (TextView) convertView.findViewById(R.id.wodepingjia_item_goods_type);
            goodsPrice = (TextView) convertView.findViewById(R.id.wodepingjia_item_goods_price);
            convertView.setTag(this);
        }
    }
}
