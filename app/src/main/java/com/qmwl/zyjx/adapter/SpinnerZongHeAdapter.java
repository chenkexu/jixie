package com.qmwl.zyjx.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.SpinnerZongHeBean;

/**
 * Created by Administrator on 2017/8/4.
 * 综合的spinner
 */

public class SpinnerZongHeAdapter extends MyBaseAdapter<SpinnerZongHeBean> {

    @Override
    protected View getItemView(int position, View convertView, ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_diqu_item, null);
        TextView tv = (TextView) inflate.findViewById(R.id.spinner_diqu_item_button);
        ImageView iv = (ImageView) inflate.findViewById(R.id.spinner_diqu_item_iv);
        SpinnerZongHeBean item = getItem(position);
        tv.setText(item.getName());
//        tv.setChecked(item.isSelecter());
        tv.setSelected(item.isSelecter());
        iv.setSelected(item.isSelecter());
        if (item.isSelecter()) {
            tv.setTextColor((parent.getContext().getResources().getColor(R.color.window_system_color)));
            iv.setImageResource(R.mipmap.shaixuan_duihao);
        } else {
            tv.setTextColor((parent.getContext().getResources().getColor(R.color.c151515)));
            iv.setImageResource(R.mipmap.white);
        }


        return inflate;
    }


    class OnClickView implements View.OnClickListener {
        SpinnerZongHeBean item;

        void setData(SpinnerZongHeBean item) {
            this.item = item;
        }

        @Override
        public void onClick(View v) {
            if (item == null) {
                return;
            }


        }
    }

}
