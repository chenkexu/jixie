package com.qmwl.zyjx.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mcxtzhang.swipemenulib.SwipeMenuLayout;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.activity.FaTieActivity;
import com.qmwl.zyjx.activity.NewsDetailsActivity;
import com.qmwl.zyjx.activity.WoDeFaTieActivity;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.FabuBean;

/**
 * Created by Administrator on 2017/7/19.
 * 我的发贴帖子列表的adapter
 */

public class FabuAdapter extends MyBaseAdapter<FabuBean> {
    @Override
    protected View getItemView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.wodefatie_layout_item, null);
        }
        ViewHolder holder = getHolder(convertView);
        FabuBean item = getItem(position);
        holder.timeTv.setText(item.getCreate_time());
        holder.titleTv.setText(item.getTitle());
        holder.contentTv.setText(item.getContent());
        holder.onclick.setData(item, holder);

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
        View deleteView, editView, contentContainer;
        MyClick onclick = new MyClick();
        SwipeMenuLayout swipeMenuLayout;

        ViewHolder(View convertView) {
            ivTv = (TextView) convertView.findViewById(R.id.wode_fatie_fragment_item_iv);
            titleTv = (TextView) convertView.findViewById(R.id.wode_fatie_fragment_item_title);
            contentTv = (TextView) convertView.findViewById(R.id.wode_fatie_fragment_item_content);
            timeTv = (TextView) convertView.findViewById(R.id.wode_fatie_fragment_item_time);
            editView = convertView.findViewById(R.id.wode_fatie_item_edit);
            deleteView = convertView.findViewById(R.id.wode_fatie_item_delete);
            contentContainer = convertView.findViewById(R.id.wode_fatie_content_container);
            swipeMenuLayout = (SwipeMenuLayout) convertView.findViewById(R.id.wode_fatie_content_SwipeMenuLayout);
            editView.setOnClickListener(onclick);
            deleteView.setOnClickListener(onclick);
            contentContainer.setOnClickListener(onclick);
        }

    }

    class MyClick implements View.OnClickListener {
        FabuBean item = null;
        ViewHolder holder = null;

        void setData(FabuBean item, ViewHolder holder) {
            this.item = item;
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {
            if (item == null) {
                return;
            }
            Intent intent = null;
            switch (v.getId()) {
                case R.id.wode_fatie_content_container:
                    intent = new Intent(v.getContext(), NewsDetailsActivity.class);
                    intent.putExtra(NewsDetailsActivity.DETAILS_URL, item.getUrl());
                    intent.putExtra(NewsDetailsActivity.DETAILS_TYPE, NewsDetailsActivity.TYPE_TIEZI);
                    v.getContext().startActivity(intent);
                    break;
                case R.id.wode_fatie_item_edit:
                    holder.swipeMenuLayout.quickClose();
                    intent = new Intent(v.getContext(), FaTieActivity.class);
                    intent.putExtra(FaTieActivity.POST_ID, item.getPost_id());
                    intent.putExtra(FaTieActivity.IS_EDIT, true);
                    v.getContext().startActivity(intent);
                    break;
                case R.id.wode_fatie_item_delete:
                    holder.swipeMenuLayout.quickClose();
                    intent = new Intent(WoDeFaTieActivity.FATIE_DELETE);
                    intent.putExtra("post_id", item.getPost_id());
                    v.getContext().sendBroadcast(intent);

                    break;
            }
        }
    }

}
