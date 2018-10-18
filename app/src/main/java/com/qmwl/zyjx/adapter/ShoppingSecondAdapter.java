package com.qmwl.zyjx.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.activity.ShoppingSecondActivity;
import com.qmwl.zyjx.activity.ShoppingThreadActivity;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.ShoppSecondZongHeBean;
import com.qmwl.zyjx.bean.ShoppingSecondBean;

/**
 * Created by Administrator on 2017/8/7.
 * 商品二级页面
 */

public class ShoppingSecondAdapter extends MyBaseAdapter<ShoppSecondZongHeBean> {
    private int width = 0;
    private int height = 0;

    public ShoppingSecondAdapter(int width) {
        super();
        this.width = (width - 60) / 2;
        this.height = (int) (this.width * 0.82);
    }

    @Override
    protected View getItemView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_second_layoute_item, null);
        }
        ViewHolder holder = getHolder(convertView);
        ShoppSecondZongHeBean item = getItem(position);
        ShoppingSecondBean bean1 = item.getBean1();
//        holder.tv1.setText(bean1.getGoods_name());
        holder.onClickListener.setBean1(bean1);
        openImage(parent, bean1.getCategory_pic(), holder.iv1);

        ShoppingSecondBean bean2 = item.getBean2();
        if (bean2 != null) {
            holder.onClickListener.setBean2(bean2);
//            holder.tv2.setVisibility(View.VISIBLE);
            holder.iv2.setVisibility(View.VISIBLE);
//            holder.tv2.setText(bean2.getGoods_name());
            openImage(parent, bean2.getCategory_pic(), holder.iv2);

        } else {
//            holder.tv2.setVisibility(View.GONE);
            holder.iv2.setVisibility(View.GONE);
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
//            container1 = convertView.findViewById(R.id.shopping_layout_item_container1);
//            container2 = convertView.findViewById(R.id.shopping_layout_item_container2);
//            tv1 = (TextView) convertView.findViewById(R.id.shopping_layout_item_tv1);
//            tv2 = (TextView) convertView.findViewById(R.id.shopping_layout_item_tv2);
            iv1 = (ImageView) convertView.findViewById(R.id.shopping_layout_item_iv1);
            iv2 = (ImageView) convertView.findViewById(R.id.shopping_layout_item_iv2);
            ViewGroup.LayoutParams layoutParams = iv1.getLayoutParams();
            layoutParams.height = height;
            layoutParams.width = width;
            iv1.setLayoutParams(layoutParams);
            iv2.setLayoutParams(layoutParams);

            onClickListener = new OnClickListener(convertView.getContext());
            iv1.setOnClickListener(onClickListener);
            iv2.setOnClickListener(onClickListener);
        }

        View container1, container2;
        TextView tv1, tv2;
        ImageView iv1, iv2;
        OnClickListener onClickListener;
    }

    class OnClickListener implements View.OnClickListener {
        ShoppingSecondBean bean1;
        ShoppingSecondBean bean2;
        Context cx;

        OnClickListener(Context cx) {
            this.cx = cx;
        }

        void setBean1(ShoppingSecondBean bean1) {
            this.bean1 = bean1;
        }

        void setBean2(ShoppingSecondBean bean2) {
            this.bean2 = bean2;
        }


        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()) {
                case R.id.shopping_layout_item_iv1:
                    if (bean1 != null) {
                        intent = new Intent(cx, ShoppingThreadActivity.class);
                        intent.putExtra(ShoppingSecondActivity.Category_name, bean1.getGoods_name());
                        intent.putExtra(ShoppingSecondActivity.Category_id, bean1.getCategory_id());
                    }
                    break;
                case R.id.shopping_layout_item_iv2:
                    if (bean2 != null) {
                        intent = new Intent(cx, ShoppingThreadActivity.class);
                        intent.putExtra(ShoppingSecondActivity.Category_name, bean2.getGoods_name());
                        intent.putExtra(ShoppingSecondActivity.Category_id, bean2.getCategory_id());
                    }
                    break;
            }
            cx.startActivity(intent);
        }
    }
}
