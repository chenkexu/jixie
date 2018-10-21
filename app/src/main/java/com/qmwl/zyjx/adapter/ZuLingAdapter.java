package com.qmwl.zyjx.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.activity.SelecterImageActivity;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.ZuLinListBean;
import com.qmwl.zyjx.dialog.ChargePopWindow;
import com.qmwl.zyjx.utils.PoPuWindowUtils;
import com.qmwl.zyjx.utils.ToastUtils;


/**
 * Created by Administrator on 2017/7/25.
 */

public class ZuLingAdapter extends MyBaseAdapter<ZuLinListBean> {
    private View parentView;
    private Context context;

    public ZuLingAdapter(Context context,View parentView) {
        this.context = context;
        this.parentView = parentView;
    }

    @Override
    protected View getItemView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.zuling_layout_item, null);
        }
        ViewHolder holder = getViewHolder(convertView);
        ZuLinListBean item = getItem(position);
        holder.onClick.setData(item);
        shwoButton(holder, item.getOrder_status());
        openImage(parent, item.getShop_logo(), holder.headIv);
        openImage(parent, item.getPic_cover_mid(), holder.shopIv);
        holder.nicknameTv.setText(item.getShop_name());
        holder.shopNameTv.setText(item.getGoods_name());
        holder.shopModelTv.setText(item.getGoods_model());
        holder.shopNumtv.setText("x".concat(item.getGoods_num()));
        holder.yajinTv.setText("￥" + item.getDeposit());
        holder.zuqiTv.setText(item.getLease_day() + parent.getContext().getString(R.string.tian));
        holder.countTv.setText(parent.getContext().getString(R.string.gong) + item.getGoods_num() + parent.getContext().getString(R.string.jianshangpin));
        holder.hejiTv.setText("￥" + item.getTotal_amount());
        holder.yunfeiTv.setText(parent.getContext().getString(R.string.hanyunfei) + item.getExpress_price() + ")");


        return convertView;
    }

    private void shwoButton(ViewHolder holder, int order_status) {
        holder.pay.setVisibility(View.GONE);
        holder.no_pay.setVisibility(View.GONE);
        holder.chakanhetongTv.setVisibility(View.GONE);
        switch (order_status) {
            case 0:
                holder.statueTv.setText(holder.statueTv.getContext().getString(R.string.zhengzaishenhe));
                holder.no_pay.setVisibility(View.VISIBLE);
                break;
            case 2:
                holder.statueTv.setText(holder.statueTv.getContext().getString(R.string.daifukuan));
                holder.pay.setVisibility(View.VISIBLE);
                holder.chakanhetongTv.setVisibility(View.VISIBLE);
                break;
            case 3:
                holder.statueTv.setText(holder.statueTv.getContext().getString(R.string.yizulin));
                holder.chakanhetongTv.setVisibility(View.VISIBLE);
                break;

        }

    }

    private ViewHolder getViewHolder(View convertView) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (holder == null) {
            holder = new ViewHolder(convertView);
        }
        return holder;
    }

    class myViewOnClick implements View.OnClickListener {
        ZuLinListBean bean;

        void setData(ZuLinListBean bean) {
            this.bean = bean;
        }

        @Override
        public void onClick(View v) {
            if (bean == null) {
                return;
            }
            switch (v.getId()) {
                case R.id.zuling_layout_item_lianximaijia:
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + bean.getShop_phone()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intent);
                    break;
                case R.id.zuling_layout_item_pay_pay:

                    ChargePopWindow chargePopWindow = new ChargePopWindow(false, context, true,
                            bean.getOrder_id(),String.valueOf(bean.getTotal_amount()), bean.getGoods_name());
                    chargePopWindow.show();

//                    fukuan(v.getContext(), bean);
                    break;
                case R.id.zuling_layout_item_chakanhetong:
                    Intent intent1 = new Intent(v.getContext(), SelecterImageActivity.class);
                    intent1.putExtra(SelecterImageActivity.URL_DATA, bean.getOrder_id());
                    intent1.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    v.getContext().startActivity(intent1);
                    break;
            }
        }
    }

    private void fukuan(final Context context, final ZuLinListBean item) {
        PoPuWindowUtils.getIntance().showSelecterPayType(context, parentView, new PoPuWindowUtils.selecterPayTypeListener() {
            @Override
            public void onAlipay() {

            }

            @Override
            public void onWechat() {

            }

            @Override
            public void onYinlian() {

            }

            @Override
            public void onZhuanzhang() {

            }

            @Override
            public void onSmallDaikuan() {
                ToastUtils.showShort(R.string.No_opening);
            }

//            @Override
//            public void onZaiXianZhifu() {
//                Intent intent = new Intent(context, ZaiXianZhiFuActivity.class);
////                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                intent.putExtra(ZaiXianZhiFuActivity.OUT_TRADE_NO_DATA, item.getOrder_id());
//                intent.putExtra(ZaiXianZhiFuActivity.PRICE_DATA, String.valueOf(item.getTotal_amount()));
//                intent.putExtra(ZaiXianZhiFuActivity.GOODSNAME_DATA, item.getGoods_name());
//                intent.putExtra(ZaiXianZhiFuActivity.PAY_TYPE, true);
//                context.startActivity(intent);
//                PoPuWindowUtils.getIntance().dismissPopuWindow();
//            }
//
//            @Override
//            public void onDuiGongFuKuan() {
//                Intent intent = new Intent(context, DuiGongFuKuanActivity.class);
////                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
//                context.startActivity(intent);
//                PoPuWindowUtils.getIntance().dismissPopuWindow();
//            }
        });

    }

    class ViewHolder {
        ImageView headIv, shopIv;
        TextView nicknameTv, statueTv, shopNameTv, shopPriceTv, chakanhetongTv,
                shopModelTv, shopNumtv, yajinTv, zuqiTv, countTv, hejiTv, yunfeiTv;
        View lianximaijia, pay, no_pay;
        myViewOnClick onClick;

        ViewHolder(View convertView) {
            headIv = (ImageView) convertView.findViewById(R.id.zuling_layout_item_head_iv);
            shopIv = (ImageView) convertView.findViewById(R.id.zuling_layout_item_iv);
            nicknameTv = (TextView) convertView.findViewById(R.id.zuling_layout_item_nickname);
            statueTv = (TextView) convertView.findViewById(R.id.zuling_layout_item_dingdan_statue);
            shopNameTv = (TextView) convertView.findViewById(R.id.zuling_layout_item_name);
            shopPriceTv = (TextView) convertView.findViewById(R.id.zuling_layout_item_shop_price);
            shopModelTv = (TextView) convertView.findViewById(R.id.zuling_layout_item_type);
            shopNumtv = (TextView) convertView.findViewById(R.id.zuling_layout_item_count);
            yajinTv = (TextView) convertView.findViewById(R.id.zuling_layout_item_yajin);
            zuqiTv = (TextView) convertView.findViewById(R.id.zuling_layout_item_zuqi);
            countTv = (TextView) convertView.findViewById(R.id.zuling_layout_item_shop_num);
            hejiTv = (TextView) convertView.findViewById(R.id.zuling_layout_item_hejijine);
            yunfeiTv = (TextView) convertView.findViewById(R.id.zuling_layout_item_yunfei);
            lianximaijia = convertView.findViewById(R.id.zuling_layout_item_lianximaijia);
            chakanhetongTv = (TextView) convertView.findViewById(R.id.zuling_layout_item_chakanhetong);
            pay = convertView.findViewById(R.id.zuling_layout_item_pay_pay);
            no_pay = convertView.findViewById(R.id.zuling_layout_item_no_pay);
            onClick = new myViewOnClick();
            lianximaijia.setOnClickListener(onClick);
            pay.setOnClickListener(onClick);
            chakanhetongTv.setOnClickListener(onClick);
            convertView.setTag(this);
        }
    }
}
