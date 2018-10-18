package com.qmwl.zyjx.adapter;

import android.app.Dialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.BaoJiaBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.view.CommomDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/8/29.
 * 报价adapter
 */

public class BaoJiaAdapter extends MyBaseAdapter<BaoJiaBean> {

    @Override
    protected View getItemView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.baojia_item_layout, null);
        }
        ViewHolder holder = getHolder(convertView);
        BaoJiaBean item = getItem(position);
        holder.nameTv.setText(getResouseString(parent, R.string.xingming) + ":" + item.getName());
        holder.cellTv.setText(getResouseString(parent, R.string.shoujihao) + ":" + item.getTel());
        holder.typeTv.setText(getResouseString(parent, R.string.chexing) + ":" + item.getVehicle());
        holder.cheChangTv.setText(getResouseString(parent, R.string.chechang) + ":" + item.getChechang());

        if ("0".equals(item.getStatus())) {
            holder.selecter.setVisibility(View.VISIBLE);
            holder.yixuan.setVisibility(View.GONE);
        } else {
            holder.selecter.setVisibility(View.GONE);
            holder.yixuan.setVisibility(View.VISIBLE);
        }
        holder.priceTv.setText(getResouseString(parent, R.string.baojia) + ":" + item.getPrice());
        holder.onClick.setData(item, holder);
        return convertView;
    }

    class MyOnClick implements View.OnClickListener {
        BaoJiaBean bean;
        ViewHolder holder;

        void setData(BaoJiaBean bean, ViewHolder holder) {
            this.bean = bean;
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {
            if (bean == null) {
                return;
            }
            new CommomDialog(v.getContext(), R.style.dialog, getResouseString(v, R.string.quedingxuanzejiagewei) + ":" + bean.getPrice(), new CommomDialog.OnCloseListener() {
                @Override
                public void onClick(Dialog dialog, boolean confirm) {
                    if (confirm) {
                        postSelecter(bean, holder);
                    }
                    dialog.dismiss();
                }
            }).show();

        }
    }

    //上传选择报价item
    private void postSelecter(BaoJiaBean bean, final ViewHolder holder) {
        AndroidNetworking.get(Contact.postXuanZeBaoJia + "?tid=" + bean.getTid() + "&id=" + bean.getId())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (JsonUtils.is100Success(response)) {
//                        new CommomDialog(holder.cellTv.getContext(), R.style.dialog, getResouseString(holder.cellTv, R.string.xuanzebaojiachenggong
                                new CommomDialog(holder.cellTv.getContext(), R.style.dialog, getResouseString(holder.cellTv,
                                        R.string.xuanzebaojiachenggong), new CommomDialog.OnCloseListener() {
                                    @Override
                                    public void onClick(Dialog dialog, boolean confirm) {
                                        
                                    }
                                }).setHideCancelButton().show();

                            } else {
                                String message = response.getString("message");
                                new CommomDialog(holder.cellTv.getContext(), R.style.dialog, message).setHideCancelButton().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            new CommomDialog(holder.cellTv.getContext(), R.style.dialog, getResouseString(holder.cellTv,
                                    R.string.xuanzebaojiashibai)).setHideCancelButton().show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        new CommomDialog(holder.cellTv.getContext(), R.style.dialog, getResouseString(holder.cellTv,
                                R.string.xuanzebaojiashibai)).setHideCancelButton().show();
                    }
                });
    }

    private ViewHolder getHolder(View convertView) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (holder == null) {
            holder = new ViewHolder(convertView);
        }
        return holder;
    }

    class ViewHolder {
        TextView nameTv, cellTv, typeTv, cheChangTv, priceTv, selecter, yixuan;
        MyOnClick onClick;

        ViewHolder(View convertView) {
            nameTv = (TextView) convertView.findViewById(R.id.baojia_item_layout_name);
            cellTv = (TextView) convertView.findViewById(R.id.baojia_item_layout_cell);
            typeTv = (TextView) convertView.findViewById(R.id.baojia_item_layout_chexing);
            cheChangTv = (TextView) convertView.findViewById(R.id.baojia_item_layout_chechang);
            priceTv = (TextView) convertView.findViewById(R.id.baojia_item_layout_baojia);
            selecter = (TextView) convertView.findViewById(R.id.baojia_item_layout_xuanzebaojia);
            yixuan = (TextView) convertView.findViewById(R.id.baojia_item_layout_yixuan);
            onClick = new MyOnClick();
            selecter.setOnClickListener(onClick);
            convertView.setTag(this);
        }

    }
}
