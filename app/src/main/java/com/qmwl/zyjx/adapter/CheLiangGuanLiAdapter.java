package com.qmwl.zyjx.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidnetworking.internal.InternalNetworking;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.activity.AddCheLiangActivity;
import com.qmwl.zyjx.activity.CheLiangGuanLiActivity;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.CheLiangBean;

/**
 * Created by Administrator on 2017/8/29.
 * 车辆管理适配器
 */

public class CheLiangGuanLiAdapter extends MyBaseAdapter<CheLiangBean> {

    @Override
    protected View getItemView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cheliangguanli_item_layout, null);
        }
        ViewHolder holder = getHolder(convertView);
        CheLiangBean item = getItem(position);

        holder.chepaihaoTv.setText(item.getPlate_number());
        holder.yunshuzhenghaoTv.setText(getResouseString(parent, R.string.daoluyunshuzhengzihao) + ":" + item.getRoad_number());
        holder.chexingTv.setText(getResouseString(parent, R.string.chexing) + ":" + item.getCar_models());
        holder.chechangTv.setText(getResouseString(parent, R.string.chechang) + ":" + item.getCar_long() + getResouseString(parent, R.string.mi));
        holder.zuidachengzailiTv.setText(getResouseString(parent, R.string.zuidachengzaidongli) + ":" + item.getMax_bearing() + getResouseString(parent, R.string.dun));
        holder.sijiTv.setText(getResouseString(parent, R.string.pipeisiji) + ":" + item.getDriver());
        holder.dianhuaTv.setText(getResouseString(parent, R.string.lianxidianhua) + ":" + item.getDriver_phone());
        holder.onClickListener.setData(holder, item, position);
        return convertView;
    }

    private ViewHolder getHolder(View convertView) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (holder == null) {
            holder = new ViewHolder(convertView);
        }
        return holder;
    }

    class MyOnClickListener implements View.OnClickListener {
        CheLiangBean bean;
        int position;
        ViewHolder holder;

        void setData(ViewHolder holder, CheLiangBean bean, int position) {
            this.bean = bean;
            this.position = position;
            this.holder = holder;
        }

        @Override
        public void onClick(View v) {
            if (bean == null) {
                return;
            }
            switch (v.getId()) {
                case R.id.cheliangguanli_item_xiugai_button:
                    Context context = holder.xiugaiTv.getContext();
                    Intent intent = new Intent(context, AddCheLiangActivity.class);
                    intent.putExtra(AddCheLiangActivity.XIUGAI, true);
                    intent.putExtra(AddCheLiangActivity.ID_ACTION, bean.getId());
                    context.startActivity(intent);
                    break;
                case R.id.cheliangguanli_item_delete_button:
                    delete(holder, bean, position);
                    break;
            }

        }
    }

    private void delete(ViewHolder holder, CheLiangBean bean, int position) {
        Intent intent = new Intent(CheLiangGuanLiActivity.CHELIANG_ACTION);
        intent.putExtra("position", position);
        holder.shanchuTv.getContext().sendBroadcast(intent);
    }

    class ViewHolder {
        TextView chepaihaoTv, yunshuzhenghaoTv, chexingTv, chechangTv, zuidachengzailiTv, sijiTv, dianhuaTv, xiugaiTv, shanchuTv;
        MyOnClickListener onClickListener;

        ViewHolder(View convertView) {
            chepaihaoTv = (TextView) convertView.findViewById(R.id.cheliangguanli_item_chepaihao);
            yunshuzhenghaoTv = (TextView) convertView.findViewById(R.id.cheliangguanli_item_daoluyunshuzhenghao);
            chexingTv = (TextView) convertView.findViewById(R.id.cheliangguanli_item_chexing);
            chechangTv = (TextView) convertView.findViewById(R.id.cheliangguanli_item_chechang);
            zuidachengzailiTv = (TextView) convertView.findViewById(R.id.cheliangguanli_item_zuidachengzaili);
            sijiTv = (TextView) convertView.findViewById(R.id.cheliangguanli_item_siji);
            dianhuaTv = (TextView) convertView.findViewById(R.id.cheliangguanli_item_lianxidianhua);
            xiugaiTv = (TextView) convertView.findViewById(R.id.cheliangguanli_item_xiugai_button);
            shanchuTv = (TextView) convertView.findViewById(R.id.cheliangguanli_item_delete_button);
            onClickListener = new MyOnClickListener();
            xiugaiTv.setOnClickListener(onClickListener);
            shanchuTv.setOnClickListener(onClickListener);

            convertView.setTag(this);
        }
    }


}
