package com.qmwl.zyjx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.PingJiabean;


/**
 * Created by Administrator on 2017/7/28.
 * 全部评价
 */

public class AllPingJiaAdapter extends MyBaseAdapter<PingJiabean> {

    @Override
    protected View getItemView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.allpingjia_layout_item, null);
        }
        ViewHolder holder = getHolder(convertView);
        PingJiabean item = getItem(position);
        if (0 == item.getIs_anonymous()) {
            holder.iv.setImageResource(R.mipmap.morentouxiang);
            holder.nameTv.setText(getString(parent.getContext(), R.string.nimingyonghu));
        } else {
            openImage(parent, item.getUser_headimg(), holder.iv);
            holder.nameTv.setText(item.getName());
        }
        holder.contentTv.setText(item.getContent());
        holder.timeTv.setText(item.getTime());
        holder.modelTv.setText(item.getGoods_model());

        return convertView;
    }

    private String getString(Context cx, int code) {
        return cx.getString(code);
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
        TextView contentTv, timeTv, modelTv, nameTv;

        ViewHolder(View convertView) {
            iv = (ImageView) convertView.findViewById(R.id.allpingjia_layout_item_iv);
            nameTv = (TextView) convertView.findViewById(R.id.allpingjia_layout_item_name);
            timeTv = (TextView) convertView.findViewById(R.id.allpingjia_layout_item_time);
            modelTv = (TextView) convertView.findViewById(R.id.allpingjia_layout_item_model);
            contentTv = (TextView) convertView.findViewById(R.id.allpingjia_layout_item_content);
            convertView.setTag(this);
        }
    }
}
