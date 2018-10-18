package com.qmwl.zyjx.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.BlackBean;
import com.qmwl.zyjx.utils.Contact;

/**
 * Created by Administrator on 2017/8/9.
 */

public class ShoppingSpinnerPinpaiAdapter extends MyBaseAdapter<BlackBean> {

    @Override
    protected View getItemView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_spinner_head_item, null);
        ImageView iv = (ImageView) convertView.findViewById(R.id.shopping_spinner_pinpai_head_item_iv);
        BlackBean item = getItem(position);
        openImage(parent, Contact.httpaddress + "/" + item.getBrand_pic(), iv);
        return convertView;
    }
}
