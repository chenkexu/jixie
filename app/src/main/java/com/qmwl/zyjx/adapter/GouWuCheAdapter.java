package com.qmwl.zyjx.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.activity.WebViewActivity;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.GouWuCheBean;
import com.qmwl.zyjx.bean.ShoppingBean;
import com.qmwl.zyjx.fragment.ThreadFragment;

import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 * 购物车的外层 adapter
 */

public class GouWuCheAdapter extends MyBaseAdapter<GouWuCheBean> {

    @Override
    protected View getItemView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.gouwuch_item_layout, null);
//        }
        ViewHolder holder = getHolder(convertView);
        GouWuCheBean item = getItem(position);
        holder.adapter.setData(item.getShoppingBeanList());
        holder.nameTv.setText(item.getShop_name());
        holder.checkBox.setChecked(item.isSelecter());
        holder.checkBoxListener.setBean(item, holder);
        holder.onItemListener.setViewHolder(holder);
//        setChildCheckBox(item);
        holder.adapter.notifyDataSetChanged();
        openImage(parent, item.getLogo(), holder.iv);
        return convertView;
    }

    private void setChildCheckBox(GouWuCheBean item) {
        List<ShoppingBean> shoppingBeanList = item.getShoppingBeanList();
        if (shoppingBeanList != null) {
            for (int i = 0; i < shoppingBeanList.size(); i++) {
                shoppingBeanList.get(i).setSelecter(item.isSelecter());
            }
        }
    }

    private ViewHolder getHolder(View convertView) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (holder == null) {
            holder = new ViewHolder(convertView);
        }
        return holder;
    }


    class ViewHolder {
        ViewHolder(View converView) {
            list = (ListView) converView.findViewById(R.id.gouwuche_item_childs_listview);
            checkBox = (CheckBox) converView.findViewById(R.id.gouwuche_item_checkbox);
            nameTv = (TextView) converView.findViewById(R.id.gouwuche_item_name);
            iv = (ImageView) converView.findViewById(R.id.gouwuche_item_iv);
            adapter = new ShoppingAdapter();
            list.setAdapter(adapter);
            checkBoxListener = new CheckBoxListener();
            onItemListener = new OnItemListener();
            list.setOnItemClickListener(onItemListener);
            checkBox.setOnCheckedChangeListener(checkBoxListener);
            converView.setTag(this);
        }

        TextView nameTv;
        CheckBox checkBox;
        ListView list;
        ShoppingAdapter adapter;
        ImageView iv;
        CheckBoxListener checkBoxListener;
        OnItemListener onItemListener;

    }

    class OnItemListener implements AdapterView.OnItemClickListener {
        ViewHolder holder = null;

        void setViewHolder(ViewHolder holder) {
            this.holder = holder;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (holder != null) {
                ShoppingBean item = holder.adapter.getItem(position);
                Intent intent = new Intent(parent.getContext(), WebViewActivity.class);
                intent.putExtra(WebViewActivity.SHOPURL, item.getUrl());
                parent.getContext().startActivity(intent);
            }

        }
    }


    class CheckBoxListener implements CompoundButton.OnCheckedChangeListener {
        GouWuCheBean bean;
        ViewHolder holder;

        void setBean(GouWuCheBean bean, ViewHolder holder) {
            this.bean = bean;
            this.holder = holder;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (bean == null || holder == null) {
                return;
            }
            bean.setSelecter(isChecked);
            List<ShoppingBean> shoppingBeanList = bean.getShoppingBeanList();
            for (int i = 0; i < shoppingBeanList.size(); i++) {
                shoppingBeanList.get(i).setSelecter(isChecked);
            }
            holder.adapter.notifyDataSetChanged();
            //更新价格
            Intent intent = new Intent(ThreadFragment.REMOVE_SHOPS_LIST);
            intent.putExtra(ThreadFragment.SHOP_TYPE, ThreadFragment.UPDATE_PRICE);
            buttonView.getContext().sendBroadcast(intent);
        }
    }

    class MyItemOnClickListener implements AdapterView.OnItemClickListener {
        GouWuCheBean bean;
        ViewHolder holder;

        void setBean(GouWuCheBean bean, ViewHolder holder) {
            this.bean = bean;
            this.holder = holder;
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (bean == null || holder == null) {

                return;
            }
            Intent intent = new Intent(parent.getContext(), WebViewActivity.class);
            intent.putExtra(WebViewActivity.SHOPURL, holder.adapter.getItem(position).getUrl());
            parent.getContext().startActivity(intent);
        }
    }
}
