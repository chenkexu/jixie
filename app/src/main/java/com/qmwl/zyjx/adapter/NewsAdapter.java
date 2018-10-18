package com.qmwl.zyjx.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.NewsBean;

/**
 * Created by Administrator on 2017/7/24.
 */

public class NewsAdapter extends MyBaseAdapter<NewsBean> {
    @Override
    protected View getItemView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_layout_item, null);
        }
        ViewHolder holder = getHolder(convertView);
        NewsBean item = getItem(position);
        openImage(parent, item.getImage(), holder.iv);
        holder.contentTv.setText(item.getTitle());
        holder.timeTv.setText(item.getCreate_time());

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
            timeTv = (TextView) convertView.findViewById(R.id.news_content_time);
            contentTv = (TextView) convertView.findViewById(R.id.news_content_tv);
            iv = (ImageView) convertView.findViewById(R.id.news_item_iv);
        }

        ImageView iv;
        TextView contentTv;
        TextView timeTv;
    }
}
