package com.qmwl.zyjx.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.MessageBean;
import com.qmwl.zyjx.bean.NewsBean;
import com.qmwl.zyjx.utils.DateUtils;

/**
 * Created by Administrator on 2017/7/24.
 */

public class MessageAdapter extends MyBaseAdapter<MessageBean> {

    @Override
    protected View getItemView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.messages_layout_item_layout, null);
        }
        ViewHolder holder = getHolder(convertView);
        MessageBean item = getItem(position);
        openImage(parent, item.getAvatar(), holder.iv);
        holder.titleTv.setText(item.getNotice());
        holder.contentTv.setText(item.getMessage());
        holder.timeTv.setText(DateUtils.formatDate(item.getSend_time()));
        if (item.getIs_read() == 0) {
            holder.isread.setVisibility(View.VISIBLE);
        } else {
            holder.isread.setVisibility(View.GONE);
        }

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
        ViewHolder(View convertView) {
            iv = (ImageView) convertView.findViewById(R.id.message_item_iv);
            timeTv = (TextView) convertView.findViewById(R.id.message_item_time);
            titleTv = (TextView) convertView.findViewById(R.id.message_item_title);
            contentTv = (TextView) convertView.findViewById(R.id.message_item_content);
            isread = convertView.findViewById(R.id.message_item_isread);
        }

        View isread;
        ImageView iv;
        TextView titleTv, contentTv, timeTv;
    }
}
