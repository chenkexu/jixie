package com.qmwl.zyjx.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.WuLiuItemBean;
import com.qmwl.zyjx.view.KuaiDiView;

/**
 * Created by Administrator on 2017/8/18.
 * 物流详情
 */

public class ChaKanWuliuAdapter extends MyBaseAdapter<WuLiuItemBean> {

    @Override
    protected View getItemView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.chakanwuliu_item_layout, null);
        }
        ViewHolder holder = getHolder(convertView);
        WuLiuItemBean item = getItem(position);
        holder.timeTv.setText(item.getTime());
        holder.contenttv.setText(item.getContent());
        if (position == 0) {
            holder.kuaiDiView.setSelecterStatue(true);
            holder.kuaiDiView.setType(KuaiDiView.TOP);
            holder.timeTv.setTextColor(parent.getContext().getResources().getColor(R.color.c252c69));
            holder.contenttv.setTextColor(parent.getContext().getResources().getColor(R.color.c252c69));
        } else if (position + 1 == mList.size()) {
            holder.kuaiDiView.setType(KuaiDiView.BOTTOM);
            holder.kuaiDiView.setSelecterStatue(false);
        } else {
            holder.kuaiDiView.setSelecterStatue(false);
            holder.kuaiDiView.setType(KuaiDiView.CENTER);
            holder.timeTv.setTextColor(parent.getContext().getResources().getColor(R.color.c999999));
            holder.contenttv.setTextColor(parent.getContext().getResources().getColor(R.color.c999999));
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
        KuaiDiView kuaiDiView;
        TextView contenttv, timeTv;

        ViewHolder(View convertView) {
            kuaiDiView = (KuaiDiView) convertView.findViewById(R.id.chakanwuliu_item_kuaidiview);
            contenttv = (TextView) convertView.findViewById(R.id.chakanwuliu_item_statue);
            timeTv = (TextView) convertView.findViewById(R.id.chakanwuliu_item_time);
            convertView.setTag(this);
        }

    }

}
