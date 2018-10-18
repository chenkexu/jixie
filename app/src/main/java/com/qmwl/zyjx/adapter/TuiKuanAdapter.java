package com.qmwl.zyjx.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.TuiKuanBean;

/**
 * Created by Administrator on 2017/8/18.
 */

public class TuiKuanAdapter extends MyBaseAdapter<TuiKuanBean> {

    @Override
    protected View getItemView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tuikuan_layout_item, null);
        }
        ViewHolder holder = getHolder(convertView);
        TuiKuanBean item = getItem(position);
        openImage(parent, item.getShop_logo(), holder.headIv);
        openImage(parent, item.getPic_cover_mid(), holder.iv);

        holder.nickNameTv.setText(item.getShop_name());
        holder.shopNameTv.setText(item.getGoods_name());
        holder.shopModelTv.setText(item.getGoods_model());
        holder.tuiKuanTv.setText(parent.getContext().getString(R.string.tuikuanjine) + item.getPrice());
        getStatue(holder, item);

        return convertView;
    }

    private void getStatue(ViewHolder holder, TuiKuanBean item) {
        String statue = "";
        switch (item.getRefund_status()) {
            case 1:
                statue = getString(holder.statueTv.getContext(), R.string.shenhe);
                break;
            case 4:
                statue = getString(holder.statueTv.getContext(), R.string.tuikuanzhong);
                break;
            case 5:
                statue = getString(holder.statueTv.getContext(), R.string.tuikuanwancheng);
                break;
            case -3:
                statue = getString(holder.statueTv.getContext(), R.string.jujuetuikuan);
                break;
        }
        holder.statueTv.setText(statue);

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
        ImageView headIv, iv;
        TextView nickNameTv, shopNameTv, shopModelTv, tuiKuanTv, statueTv;

        ViewHolder(View convertView) {
            headIv = (ImageView) convertView.findViewById(R.id.tuikuan_item_head_iv);
            iv = (ImageView) convertView.findViewById(R.id.tuikuan_item_iv);
            nickNameTv = (TextView) convertView.findViewById(R.id.tuikuan_item_nickname);
            shopNameTv = (TextView) convertView.findViewById(R.id.tuikuan_item_shop_name);
            shopModelTv = (TextView) convertView.findViewById(R.id.tuikuan_item_shop_model);
            tuiKuanTv = (TextView) convertView.findViewById(R.id.tuikuan_item_tuikuanjine);
            statueTv = (TextView) convertView.findViewById(R.id.tuikuan_item_statue);
            convertView.setTag(this);
        }
    }
}
