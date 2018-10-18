package com.qmwl.zyjx.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.GouWuCheBean;

/**
 * Created by Administrator on 2017/8/20.
 */

public class QueRenDingDanWaiAdapter extends MyBaseAdapter<GouWuCheBean> {

    @Override
    protected View getItemView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.querendingdan_tem, null);
        }
        GouWuCheBean item = getItem(position);
        ViewHolder holder = getHolder(convertView);
        holder.name.setText(item.getShop_name());
        holder.price.setText("￥" + item.getPrice());
        holder.number.setText(getResouseString(parent.getContext(), R.string.gong) + item.getNumber() + getResouseString(parent.getContext(), R.string.jianshangpin));
        holder.adapter.setData(item.getShoppingBeanList());
        holder.liuyanEditText.setText(item.getLiuyan());
        holder.liuyanWatcher.setData(item);
        holder.kuaidifangshi.setText("￥"+item.getYunFeiZong());
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
        ListView mLv;
        ImageView logo;
        TextView name, kuaidifangshi, price, number;
        EditText liuyanEditText;
        QueRenDingDanAdapter adapter;
        Liuyan liuyanWatcher;

        ViewHolder(View convertView) {
            mLv = (ListView) convertView.findViewById(R.id.querendingdan_item_listview);
            logo = (ImageView) convertView.findViewById(R.id.querendingdan_item_shangjialogo);
            name = (TextView) convertView.findViewById(R.id.querendingdan_item_shangjianame);
            kuaidifangshi = (TextView) convertView.findViewById(R.id.querendingdan_item_kuaidifangshi);
            price = (TextView) convertView.findViewById(R.id.querendingdan_item_price);
            liuyanEditText = (EditText) convertView.findViewById(R.id.querendingdan_item_liuyan);
            number = (TextView) convertView.findViewById(R.id.querendingdan_item_number);
            adapter = new QueRenDingDanAdapter();
            liuyanWatcher = new Liuyan();
            liuyanEditText.addTextChangedListener(liuyanWatcher);
            mLv.setAdapter(adapter);
            convertView.setTag(this);
        }
    }

    class Liuyan implements TextWatcher {
        GouWuCheBean bean;

        void setData(GouWuCheBean bean) {
            this.bean = bean;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (bean == null) {
                return;
            }
            bean.setLiuyan(s.toString().trim());
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }
}
