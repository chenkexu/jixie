package com.qmwl.zyjx.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.FabuBean;

/**
 * Created by User on 2017/11/8.
 *  帖子列表的adapter
 */

public class TieZiListAdapter extends MyBaseAdapter<FabuBean> {

    @Override
    protected View getItemView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tiezi_list_item, null);
        }
        ViewHolder holder = getHolder(convertView);
        FabuBean item = getItem(position);
        holder.timeTv.setText(item.getCreate_time());
        holder.titleTv.setText(item.getTitle());
        holder.contentTv.setText(item.getContent());

        return convertView;
    }

    private ViewHolder getHolder(View convertView) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (holder == null) {
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        return holder;
    }

    class ViewHolder {
        TextView titleTv, contentTv, timeTv, ivTv;

        ViewHolder(View convertView) {
            ivTv = (TextView) convertView.findViewById(R.id.fabu_fragment_item_iv);
            titleTv = (TextView) convertView.findViewById(R.id.fabu_fragment_item_title);
            contentTv = (TextView) convertView.findViewById(R.id.fabu_fragment_item_content);
            timeTv = (TextView) convertView.findViewById(R.id.fabu_fragment_item_time);
        }

    }

}
