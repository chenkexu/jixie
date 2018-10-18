package com.qmwl.zyjx.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.TransportBean;
import com.qmwl.zyjx.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/28.
 * 运输板块adapter
 */

public class TransportAdapter extends MyBaseAdapter<TransportBean> {
    private String tian = "";
    private String yijieshu = "";
    private String xiaoshi = "";
    private String fenzhong = "";
    private String miao = "";
    private List<ViewHolder> textViews = new ArrayList<>();

    private long index = 0;

    public TransportAdapter(Context cx) {
        tian = getResouseString(cx, R.string.tian);
        yijieshu = getResouseString(cx, R.string.yijieshu);
        xiaoshi = getResouseString(cx, R.string.xiaoshi);
        fenzhong = getResouseString(cx, R.string.fenzhong);
        miao = getResouseString(cx, R.string.miao);
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            index++;
            dealTextView();
            mHandler.sendEmptyMessageDelayed(1, 1000);
        }
    };

    private void dealTextView() {
        for (int i = 0; i < textViews.size(); i++) {
            textViews.get(i).dealBeanTime();
        }
    }


    @Override
    public void setData(List mList) {
        textViews.clear();
        index = 0;
        cancelRunnable();
        mHandler.sendEmptyMessageDelayed(1, 1000);
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void cancelRunnable() {
        mHandler.removeMessages(1);
    }

    @Override
    protected View getItemView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.huoyuan_layout_item, null);
        }
        ViewHolder holder = getHolder(convertView);
        TransportBean item = getItem(position);
        holder.setData(item);
        holder.fahuodiTv.setText(item.getT_address());
        holder.shouHuoDiTv.setText(item.getS_mdd());
        holder.zhuanghuoTimeTv.setText(item.getDelivery_time());
        holder.ShopTypeOrWeightTv.setText(item.gettName() + "    " + item.getT_weight());


        if (item.getRemaining() > 10) {
            textViews.add(holder);
        } else {
            textViews.remove(holder);
            holder.daojishi.setText(yijieshu);
        }

        return convertView;
    }

    ViewHolder getHolder(View convertView) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (holder == null) {
            holder = new ViewHolder(convertView);
        }
        return holder;
    }

    class ViewHolder {
        TransportBean bean;

        void setData(TransportBean bean) {
            this.bean = bean;
        }

        ViewHolder(View convertView) {
            span = new SpannableStringBuilder();
            daojishi = (TextView) convertView.findViewById(R.id.huoyuan_item_shengyushijian);
            fahuodiTv = (TextView) convertView.findViewById(R.id.huoyuan_item_fahuodi);
            shouHuoDiTv = (TextView) convertView.findViewById(R.id.huoyuan_item_shouhuodi);
            ShopTypeOrWeightTv = (TextView) convertView.findViewById(R.id.huoyuan_item_huowuleixing);
            zhuanghuoTimeTv = (TextView) convertView.findViewById(R.id.huoyuan_item_zhuanghuoshijian);
            colorSpan4 = new ForegroundColorSpan(Color.RED);
            colorSpan1 = new ForegroundColorSpan(Color.RED);
            colorSpan2 = new ForegroundColorSpan(Color.RED);
            colorSpan3 = new ForegroundColorSpan(Color.RED);
//            textViews.add(this);
            convertView.setTag(this);
        }

        void dealBeanTime() {
            if (bean == null) {
                return;
            }
//            long l = bean.getTime() - index * 1000;
            String s = DateUtils.timeCha(bean.getTime() - index, tian, xiaoshi, fenzhong, miao);
            setDaojishi(s.trim());
        }

        void setDaojishi(String str) {
            span.clear();
            span.append(str);
            if ("".equals(str) || TextUtils.isEmpty(str)) {
                daojishi.setText(yijieshu);
                return;
            }
            String tianStr = tian;
            String hoursStr = xiaoshi;
            String minStr = fenzhong;
            String miaoStr = miao;
            int tianIndex = str.indexOf(tianStr);
            int hoursIndex = str.indexOf(hoursStr);
            int minIndex = str.indexOf(minStr);
            int miaoIndex = str.indexOf(miaoStr);
            try {
                span.setSpan(colorSpan4, 0, tianIndex, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                span.setSpan(colorSpan1, tianIndex + tianStr.length(), hoursIndex, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                span.setSpan(colorSpan2, hoursIndex + hoursStr.length(), minIndex, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                span.setSpan(colorSpan3, minIndex + minStr.length(), miaoIndex, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);

                daojishi.setText(span);
            } catch (IndexOutOfBoundsException e) {
                Log.e("TAG", "倒计时的字符串长度不够");
            }
        }

        TextView daojishi, fahuodiTv, shouHuoDiTv, ShopTypeOrWeightTv, zhuanghuoTimeTv;
        ForegroundColorSpan colorSpan1;
        ForegroundColorSpan colorSpan2;
        ForegroundColorSpan colorSpan3;
        ForegroundColorSpan colorSpan4;
        SpannableStringBuilder span;
    }
}
