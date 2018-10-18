package com.qmwl.zyjx.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.RegisterDuiGongBean;

/**
 * Created by 郭辉 on 2018/1/18 10:12.
 * TODO:对公账号列表adapter
 */

public class RegisterDuiGongAdapter extends MyBaseAdapter<RegisterDuiGongBean> {
    @Override
    protected View getItemView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_register_duigong, null);
        ViewHolder holder = new ViewHolder(convertView);
        RegisterDuiGongBean item = getItem(position);
        holder.title.setText(item.getTitle());
        holder.dizhi.setText(item.getKaihuhang());
        holder.zhanghao.setText(item.getZhanghao());
        holder.position.setText(String.valueOf(position + 1));
        return convertView;
    }

    class ViewHolder {
        TextView position;
        TextView title, zhanghao, dizhi;

        ViewHolder(View itemView) {
            title = (TextView) itemView.findViewById(R.id.id_item_register_duigong_title);
            zhanghao = (TextView) itemView.findViewById(R.id.id_item_register_duigong_zhanghao);
            dizhi = (TextView) itemView.findViewById(R.id.id_item_register_duigong_dizhi);
            position = (TextView) itemView.findViewById(R.id.id_item_register_duigong_position);
            itemView.setTag(this);
        }
    }
}
