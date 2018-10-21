package com.qmwl.zyjx.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.activity.AskReturnThingActivity;
import com.qmwl.zyjx.activity.AskWeiQuanActivity;
import com.qmwl.zyjx.activity.DuiGongFuKuanActivity;
import com.qmwl.zyjx.activity.OrderCancelActivity;
import com.qmwl.zyjx.activity.ReturnWuliuActivity;
import com.qmwl.zyjx.activity.ScendPingJiaActivity;
import com.qmwl.zyjx.activity.SelecterWuLiuActivity;
import com.qmwl.zyjx.activity.WoDeDingDanActivity;
import com.qmwl.zyjx.api.ApiManager;
import com.qmwl.zyjx.api.ApiResponse;
import com.qmwl.zyjx.api.BaseObserver;
import com.qmwl.zyjx.base.Constant;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.CancelOrderBean;
import com.qmwl.zyjx.bean.DingDanBean;
import com.qmwl.zyjx.bean.ShoppingBean;
import com.qmwl.zyjx.dialog.InvoiceDialog;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.utils.PoPuWindowUtils;
import com.qmwl.zyjx.utils.RxUtil;
import com.qmwl.zyjx.utils.ToastUtils;
import com.qmwl.zyjx.view.AskRetunPayDialog;
import com.qmwl.zyjx.view.CommomDialog;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * Created by Administrator on 2017/7/25.
 */

public class DingDanAdapter extends MyBaseAdapter<DingDanBean> {

    private View parentView;
    private DingDanBean item;
    private Context context;



    public DingDanAdapter(View parentView,Context context) {
        this.parentView = parentView;
        this.context = context;
    }


    @Override
    protected View getItemView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dingdan_layout_item, null);
        }
        ViewHolder holder = getHolder(convertView);
        item = getItem(position);
        holder.shangjiaName.setText(item.getShop_name());
        holder.shangpinStatue.setText(getStatueString(parent.getContext(), item.getDingdan_statue_code()));
        hideBottomView(holder, item.getDingdan_statue_code(), item.getIs_evaluate());
        Context context = parent.getContext();
        holder.zonggong.setText(getResouseString(context, R.string.gong) + item.getNum() + getResouseString(context, R.string.jianshangpin));
        holder.heji.setText("￥" + item.getZongPrice());
        holder.yunfei.setText(getResouseString(context, R.string.hanyunfei) + item.getShipping_money() + ")");
        holder.onClickListener.setData(item);
        openImage(parent, item.getShop_logo(), holder.shangjiaiv);
        if (item.getNum() >= 1) {
            ShoppingBean shoppingBean = item.getShopList().get(0);
            openImage(parent, shoppingBean.getIv_pic(), holder.shangpiniv);
            holder.shangpinname.setText(shoppingBean.getName());
            holder.shangpinPrice.setText("￥" + shoppingBean.getPrice());
            holder.shangpinNum.setText("x" + shoppingBean.getNumber());
        }
        return convertView;
    }

    private void hideBottomView(ViewHolder holder, int code, int is_evaluate) {
        holder.pingjia.setVisibility(View.GONE);
        holder.querenshouhuo.setVisibility(View.GONE);
        holder.chakanwuliu.setVisibility(View.GONE);
        holder.tuikuan.setVisibility(View.GONE);
        holder.fukuan.setVisibility(View.GONE);
        holder.quxiaodingdan.setVisibility(View.GONE);
        holder.lianximaijia.setVisibility(View.GONE);
        //AddByHr
        holder.shenqingtuikuan.setVisibility(View.GONE);
        holder.shenqingfapiao.setVisibility(View.GONE);
        holder.tixingfahuo.setVisibility(View.GONE);
        holder.shenqingtuiuhuo.setVisibility(View.GONE);
        holder.shenqingweiquan.setVisibility(View.GONE);
        holder.tuihuowuliu.setVisibility(View.GONE);
        holder.tv_tip.setText("");
        switch (code) {
            case 0:
                //待付款
                holder.lianximaijia.setVisibility(View.VISIBLE);
                holder.quxiaodingdan.setVisibility(View.VISIBLE);
                holder.fukuan.setVisibility(View.VISIBLE);
                holder.fukuan.setSelected(true);
                break;
            case 1:
                //statue =待发货
                holder.lianximaijia.setVisibility(View.VISIBLE);
                //holder.tuikuan.setVisibility(View.VISIBLE);
                holder.shenqingtuikuan.setVisibility(View.VISIBLE);
                holder.shenqingfapiao.setVisibility(View.VISIBLE);
                holder.tixingfahuo.setVisibility(View.VISIBLE);



                if (item.getFapiao()==0){
                    holder.shenqingfapiao.setVisibility(View.VISIBLE);
                }else{
                    holder.shenqingfapiao.setVisibility(View.GONE);
                }

                if (item.getMa()==1){
                    //申请退款
                    holder.tv_tip.setText(item.getMsg()+"");
                    holder.shenqingtuikuan.setVisibility(View.GONE);
                    holder.shenqingfapiao.setVisibility(View.GONE);
                    holder.tixingfahuo.setVisibility(View.GONE);
                }else if(item.getMa()==2){
                    //申请退款不通过
                    holder.tv_tip.setText(item.getMsg()+"");
                }else{
                    holder.tv_tip.setText("");
                }

                break;
            case 2:
//                statue = 待收货
                holder.lianximaijia.setVisibility(View.VISIBLE);
                holder.chakanwuliu.setVisibility(View.VISIBLE);
                holder.querenshouhuo.setVisibility(View.VISIBLE);
                holder.shenqingtuiuhuo.setVisibility(View.VISIBLE);
                //显示申请维权,根据判断条件
              //  holder.shenqingweiquan.setVisibility(View.VISIBLE);
                if (item.getMa()==1){
                    //申请退款
                    holder.tv_tip.setText(item.getMsg()+"");
                    holder.shenqingtuiuhuo.setVisibility(View.GONE);
                    holder.chakanwuliu.setVisibility(View.GONE);
                    holder.querenshouhuo.setVisibility(View.GONE);
                }else if(item.getMa()==2){
                    holder.shenqingweiquan.setVisibility(View.VISIBLE);
                    //申请退款不通过
                    holder.tv_tip.setText(item.getMsg()+"");
                }else if(item.getMa()==3){
                    // 3 等待卖家退款
                    holder.lianximaijia.setVisibility(View.GONE);
                    holder.shenqingtuiuhuo.setVisibility(View.GONE);
                    holder.chakanwuliu.setVisibility(View.GONE);
                    holder.querenshouhuo.setVisibility(View.GONE);
                    holder.shenqingweiquan.setVisibility(View.GONE);
                    holder.tuihuowuliu.setVisibility(View.VISIBLE);
                    //申请退款不通过
                    holder.tv_tip.setText(item.getMsg()+"");
                    Log.d("huangrui","item.getTui_xu()的值"+item.getTui_xu());
                    if (("1").equals(item.getTui_xu())){
                        Log.d("huangrui","进来l了"+item.getTui_xu());
                        holder.tuihuowuliu.setVisibility(View.VISIBLE);
                    }else{
                        holder.tuihuowuliu.setVisibility(View.GONE);
                    }



                }else if(item.getMa()==4){
                    holder.lianximaijia.setVisibility(View.VISIBLE);
                    holder.shenqingtuiuhuo.setVisibility(View.GONE);
                    holder.chakanwuliu.setVisibility(View.GONE);
                    holder.querenshouhuo.setVisibility(View.GONE);
                    //维权申请中
                    holder.tv_tip.setText(item.getMsg()+"");

                }else {
                    holder.tv_tip.setText("");
                }

                break;
            case 3:
//                statue = 已完成
//                holder.chakanwuliu.setVisibility(View.VISIBLE);
                if (is_evaluate == 0) {
                    holder.pingjia.setVisibility(View.VISIBLE);
                }
                holder.shenqingtuiuhuo.setVisibility(View.VISIBLE);
                holder.chakanwuliu.setVisibility(View.VISIBLE);
                holder.dingdan_layout_item_shanchudingdan.setVisibility(View.VISIBLE);
                break;
            case 4:
//                statue = 已完成
//                holder.chakanwuliu.setVisibility(View.VISIBLE);
                if (is_evaluate == 0) {
                    holder.pingjia.setVisibility(View.VISIBLE);
                }
                holder.shenqingtuiuhuo.setVisibility(View.VISIBLE);
                holder.chakanwuliu.setVisibility(View.VISIBLE);
                holder.dingdan_layout_item_shanchudingdan.setVisibility(View.VISIBLE);
                break;
            case -1:
                //待退款退货订单
                Log.d("huangrui","状态时-1");
                holder.lianximaijia.setVisibility(View.VISIBLE);
                if(item.getMa()==7){
                    holder.dingdan_layout_item_shanchudingdan.setVisibility(View.VISIBLE);
                }
                break;
            case 6:
                //异常订单
                //if (item.getMa()==5){
                    holder.tv_tip.setText(item.getMsg()+"");
                    holder.lianximaijia.setVisibility(View.VISIBLE);
               // }

                break;
        }
    }

    //获取订单状态
    private String getStatueString(Context context, int dingdan_statue_code) {
        String statue = "";
        switch (dingdan_statue_code) {
            case 0:
//                statue = "未付款";
                statue = context.getString(R.string.daifukuan);
                break;
            case 1:
//                statue = "已付款";
                statue = context.getString(R.string.daifahuo);
                break;
            case 2:
//                statue = "已发货";
                statue = context.getString(R.string.daishouhuo);
                break;
            case 3:
//                statue = "已完成";
                statue = context.getString(R.string.yiwancheng);
                break;
            default:
                statue = context.getString(R.string.yiguanbi);
                break;
        }
        return statue;
    }

    private ViewHolder getHolder(View convertView) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (holder == null) {
            holder = new ViewHolder(convertView);
        }
        return holder;
    }

    class ViewHolder {
        ImageView shangjiaiv, shangpiniv;
        TextView shangjiaName, shangpinStatue, shangpinname, shangpinPrice, shangpinType, shangpinNum, zonggong, heji, yunfei,tv_tip;
        View lianximaijia, quxiaodingdan, fukuan, tuikuan, chakanwuliu, querenshouhuo, pingjia;
        //AddByHr
        View shenqingtuikuan, shenqingfapiao,tixingfahuo,shenqingtuiuhuo,shenqingweiquan,tuihuowuliu,dingdan_layout_item_shanchudingdan;
        MyViewOnClickListener onClickListener;

        ViewHolder(View convertView) {
            heji = (TextView) convertView.findViewById(R.id.dingdan_layout_item_heji);
            yunfei = (TextView) convertView.findViewById(R.id.dingdan_layout_item_yunfeni);
            shangpiniv = (ImageView) convertView.findViewById(R.id.dingdan_layout_item_iv);
            shangpinNum = (TextView) convertView.findViewById(R.id.dingdan_layout_item_num);
            zonggong = (TextView) convertView.findViewById(R.id.dingdan_layout_item_zonghe);
            shangpinname = (TextView) convertView.findViewById(R.id.dingdan_layout_item_name);
            shangpinType = (TextView) convertView.findViewById(R.id.dingdan_layout_item_type);
            shangjiaiv = (ImageView) convertView.findViewById(R.id.dingdan_layout_item_shangjiaiv);
            shangjiaName = (TextView) convertView.findViewById(R.id.dingdan_layout_item_shangjianame);
            shangpinPrice = (TextView) convertView.findViewById(R.id.dingdan_layout_item_danjia_price);
            shangpinStatue = (TextView) convertView.findViewById(R.id.dingdan_layout_item_shangjiastatue);
            tv_tip = (TextView) convertView.findViewById(R.id.tv_tip);



            lianximaijia = convertView.findViewById(R.id.dingdan_layout_item_lianximaijia);
            quxiaodingdan = convertView.findViewById(R.id.dingdan_layout_item_quxiaodingdan);
            fukuan = convertView.findViewById(R.id.dingdan_layout_item_fukuan);
            tuikuan = convertView.findViewById(R.id.dingdan_layout_item_tuikuan);
            chakanwuliu = convertView.findViewById(R.id.dingdan_layout_item_chakanwuliu);
            querenshouhuo = convertView.findViewById(R.id.dingdan_layout_item_querenshouhuo);
            pingjia = convertView.findViewById(R.id.dingdan_layout_item_pingjia);
            //@AddByHr
            //申请退款
            shenqingtuikuan = convertView.findViewById(R.id.dingdan_layout_item_shenqingtuikuan);
            //申请发票
            shenqingfapiao = convertView.findViewById(R.id.dingdan_layout_item_shenqingfapiao);
            //提醒发货
            tixingfahuo = convertView.findViewById(R.id.dingdan_layout_item_tixingfahuo);
            //申请退货
            shenqingtuiuhuo = convertView.findViewById(R.id.dingdan_layout_item_shenqingtuihuo);
            //申请维权
            shenqingweiquan = convertView.findViewById(R.id.dingdan_layout_item_shenqingweiquan);
            //退货物流
            tuihuowuliu = convertView.findViewById(R.id.dingdan_layout_item_tuihuowuliu);
            //删除订单
            dingdan_layout_item_shanchudingdan = convertView.findViewById(R.id.dingdan_layout_item_shanchudingdan);

            onClickListener = new MyViewOnClickListener();
            View[] v = new View[]{pingjia, querenshouhuo, chakanwuliu, tuikuan, fukuan, quxiaodingdan, lianximaijia,shenqingtuikuan,shenqingfapiao,tixingfahuo,shenqingtuiuhuo,shenqingweiquan,tuihuowuliu,dingdan_layout_item_shanchudingdan};
            setViewOnClick(v, onClickListener);
            convertView.setTag(this);
        }
    }

    private void setViewOnClick(View[] params, MyViewOnClickListener onClickListener) {
        if (params == null) {
            return;
        }
        for (View v : params) {
            v.setOnClickListener(onClickListener);
        }
    }








    class MyViewOnClickListener implements View.OnClickListener {
        DingDanBean item = null;
        void setData(DingDanBean item) {
            this.item = item;
        }

        @Override
        public void onClick(View v) {
            if (item == null) {
                return;
            }
            Intent intent = null;
            switch (v.getId()) {
                case R.id.dingdan_layout_item_lianximaijia:
                    //联系卖家
                    intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + item.getShop_phone()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getContext().startActivity(intent);
                    break;
                case R.id.dingdan_layout_item_quxiaodingdan:
                    //取消订单
//                    quxiaodingdan(v.getContext(), item);
                    // TODO: 2018/10/19 进入取消订单页面
                    intent = new Intent(context, OrderCancelActivity.class);
                    intent.putExtra(Constant.cancel_order_reson, Constant.get_cancel_order_reson);
                    intent.putExtra("order", item);
                    context.startActivity(intent);
                    break;
                case R.id.dingdan_layout_item_fukuan:
//                    Log.i("TAG", "付款");
                    fukuan(v.getContext(), item);
                    break;
                case R.id.dingdan_layout_item_tuikuan:
                    //退款
                    tuikuan(v.getContext(), item);
                    break;
                case R.id.dingdan_layout_item_chakanwuliu:
                    //查看物流
                    intent = new Intent(v.getContext(), SelecterWuLiuActivity.class);
                    intent.putExtra(SelecterWuLiuActivity.ID, item.getOrder_no());
                    v.getContext().startActivity(intent);
                    break;
                case R.id.dingdan_layout_item_querenshouhuo:
                    //确认收货
                    querenshouhuo(v.getContext(), item);
                    break;
                case R.id.dingdan_layout_item_pingjia:
                    //评价
                    intent = new Intent(v.getContext(), ScendPingJiaActivity.class);
                    intent.putExtra(ScendPingJiaActivity.order_id_data, item.getOrder_id());
                    intent.putExtra(ScendPingJiaActivity.order_no_data, item.getOrder_no());
                    ShoppingBean shopBean = item.getShopList().get(0);
                    intent.putExtra(ScendPingJiaActivity.order_goods_id_data, shopBean.getOrder_goods_id());
                    intent.putExtra(ScendPingJiaActivity.goods_id_data, shopBean.getOrder_goods_id());
                    intent.putExtra(ScendPingJiaActivity.goods_name_data, shopBean.getName());
                    intent.putExtra(ScendPingJiaActivity.price_data, shopBean.getPrice() + "");
                    intent.putExtra(ScendPingJiaActivity.shop_id_data, shopBean.getShop_id());
                    intent.putExtra(ScendPingJiaActivity.shop_iv, shopBean.getIv_pic());
                    intent.putExtra(ScendPingJiaActivity.shop_name, shopBean.getShopName());
                    Log.d("huangrui","传过去的商品名称:"+shopBean.getShopName());
                    v.getContext().startActivity(intent);
                    break;
                case R.id.dingdan_layout_item_shenqingtuikuan:
                    //申请退款
                    ApiManager.getInstence().getApiService().get_tuikuan_order_reson()
                            .compose(RxUtil.<ApiResponse<CancelOrderBean>>rxSchedulerHelper())
                            .subscribe(new BaseObserver<CancelOrderBean>() {
                                @Override
                                protected void onSuccees(ApiResponse<CancelOrderBean> t) {
                                    CancelOrderBean data = t.getData();
                                    AskRetunPayDialog mAskRpDialog=new AskRetunPayDialog();
                                    if (!mAskRpDialog.isAdded()) {
                                        Bundle mBundle=new Bundle();
                                        mBundle.putSerializable("data",data);
                                        mBundle.putSerializable("DingDanBean",item);
                                        mAskRpDialog.setArguments(mBundle);
                                        mAskRpDialog.show(((Activity)context).getFragmentManager(), "mAskRpDialog");
                                    }

                                }

                                @Override
                                protected void onFailure(String errorInfo, boolean isNetWorkError) {

                                }
                            });


                    break;
                case R.id.dingdan_layout_item_shenqingfapiao:
                    //申请发票
                    InvoiceDialog invoiceDialog = new InvoiceDialog(context,item);
                    invoiceDialog.show();
                    break;
                case R.id.dingdan_layout_item_tixingfahuo:
                    //提醒发货

                    break;
                case R.id.dingdan_layout_item_shenqingtuihuo:
                    //申请退货
                    Intent mIntent = new Intent(context, AskReturnThingActivity.class);
                    mIntent.putExtra("DingDanBean",item);
                    context.startActivity(mIntent);
                    break;

                case R.id.dingdan_layout_item_shenqingweiquan:
                    //申请维权
                   Intent mIntent2=new Intent(context, AskWeiQuanActivity.class);
                     /*Intent mIntent2=new Intent(context, ReturnWuliuActivity.class);*/
                    mIntent2.putExtra("DingDanBean",item);
                    context.startActivity(mIntent2);
                    break;
                case R.id.dingdan_layout_item_tuihuowuliu:
                    //申请维权
                    Intent mIntent3=new Intent(context, ReturnWuliuActivity.class);
                    /*Intent mIntent2=new Intent(context, ReturnWuliuActivity.class);*/
                    mIntent3.putExtra("DingDanBean",item);
                    context.startActivity(mIntent3);
                    break;
                case R.id.dingdan_layout_item_shanchudingdan:
                    //删除订单

                    new CommomDialog(context, R.style.dialog, context.getString(R.string.shanchu), new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(final Dialog dialog, boolean confirm) {
                            if (confirm) {
                                AndroidNetworking.get(Contact.shanchudingdan + "?orderId=" + item.getOrder_id())
                                        .build()
                                        .getAsJSONObject(new JSONObjectRequestListener() {
                                            @Override
                                            public void onResponse(JSONObject response) {
                                              //  Log.d("huangrui","删除成功");
                                                ToastUtils.show(context.getResources().getString(R.string.delete_suc),1000);
                                                dialog.dismiss();
                                            }

                                            @Override
                                            public void onError(ANError anError) {
                                            //    Log.d("huangrui","删除失败");
                                                ToastUtils.show(context.getResources().getString(R.string.delete_fail),1000);
                                                dialog.dismiss();
                                            }
                                        });
                            }else{
                                dialog.dismiss();
                            }

                        }
                    }).setTitle(context.getString(R.string.tishi)).show();

                   break;
            }
        }
    }

    //确认收货
    private void querenshouhuo(final Context context, final DingDanBean item) {
        new CommomDialog(context, R.style.dialog, context.getString(R.string.shifouyijingshouhuo), new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    querenshouhuoplay(context, item);
                }
                dialog.dismiss();
            }
        }).setTitle(context.getString(R.string.tishi)).show();
    }

    private void querenshouhuoplay(final Context context, DingDanBean item) {
        AndroidNetworking.get(Contact.querenshouhuo + "?orderId=" + item.getOrder_id())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (JsonUtils.isSuccess(response)) {
                                WoDeDingDanActivity.refreshData(context);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.i("TAG", "确认订单:" + response.toString());
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });
    }



    //退款
    private void tuikuan(final Context context, final DingDanBean item) {
        new CommomDialog(context, R.style.dialog, context.getString(R.string.shifousheqingtuikuan), new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    tuikuanPlay(context, item,"");
                }
                dialog.dismiss();
            }
        }).setTitle(context.getString(R.string.tishi)).show();
    }



    public void tuikuanPlay(final Context context, DingDanBean item,String content) {
        AndroidNetworking.get(Contact.tuikuan + "?orderId=" + item.getOrder_id()
                + "&goods_id=" + item.getShopList().get(0).getGoods_id()
                +"&content" + content)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (JsonUtils.isSuccess(response)) {
                                WoDeDingDanActivity.refreshData(context);
                                new CommomDialog(context, R.style.dialog, context.getString(R.string.tuikuanchenggong), new CommomDialog.OnCloseListener() {
                                    @Override
                                    public void onClick(Dialog dialog, boolean confirm) {
                                        dialog.dismiss();
                                    }
                                }).setTitle(context.getString(R.string.tishi)).setHideCancelButton().show();
                            } else {
                                showTishiDialog(context, R.string.tuikuanshibai);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            showTishiDialog(context, R.string.tuikuanshibai);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        showTishiDialog(context, R.string.quxiaodingdanshibai);
                    }
                });
    }


    //取消订单
    private void quxiaodingdan(final Context context, final DingDanBean item) {
        new CommomDialog(context, R.style.dialog, context.getString(R.string.shifouquxiaodingdan), new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    Intent intent = new Intent(context, OrderCancelActivity.class);
                    intent.putExtra(Constant.cancel_order_reson, Constant.get_cancel_order_reson);
                    intent.putExtra("order", item);
                    context.startActivity(intent);
                }
                dialog.dismiss();
            }
        }).setTitle(context.getString(R.string.tishi)).show();
    }








    //执行取消订单的方法
    private void cancelDingDan(final Context context, DingDanBean item,String content) {
        AndroidNetworking.get(Contact.quxiaodingdan + "?orderId=" + item.getOrder_id()+"&content="+content)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (JsonUtils.isSuccess(response)) {
                                WoDeDingDanActivity.refreshData(context);
                                new CommomDialog(context, R.style.dialog, context.getString(R.string.quxiaodingdanchenggong), new CommomDialog.OnCloseListener() {
                                    @Override
                                    public void onClick(Dialog dialog, boolean confirm) {
                                        dialog.dismiss();
                                    }
                                }).setTitle(context.getString(R.string.tishi)).setHideCancelButton().show();
                            } else {
                                showTishiDialog(context, R.string.quxiaodingdanshibai);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            showTishiDialog(context, R.string.quxiaodingdanshibai);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        showTishiDialog(context, R.string.quxiaodingdanshibai);
                    }
                });
    }


    private void showTishiDialog(Context context, int stringId) {
        new CommomDialog(context, R.style.dialog, context.getString(stringId), new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                dialog.dismiss();
            }
        }).setTitle(context.getString(R.string.tishi)).setHideCancelButton().show();
    }

    private void fukuan(final Context context, final DingDanBean item) {
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
                Intent intent = new Intent(context, DuiGongFuKuanActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                context.startActivity(intent);
                PoPuWindowUtils.getIntance().dismissPopuWindow();
            }

            @Override
            public void onSmallDaikuan() {
                ToastUtils.showShort(getResouseString(context,R.string.No_opening));
            }
             });

          /*  @Override
          public void onZaiXianZhifu() {
               Intent intent = new Intent(context, ZaiXianZhiFuActivity.class);
               intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra(ZaiXianZhiFuActivity.OUT_TRADE_NO_DATA, item.getOrder_id());
               intent.putExtra(ZaiXianZhiFuActivity.PRICE_DATA, String.valueOf(item.getZongPrice()));
                intent.putExtra(ZaiXianZhiFuActivity.GOODSNAME_DATA, item.getShopList().get(0).getName());
               context.startActivity(intent);
               PoPuWindowUtils.getIntance().dismissPopuWindow();
           }

           @Override
            public void onDuiGongFuKuan() {
               Intent intent = new Intent(context, DuiGongFuKuanActivity.class);
              intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                context.startActivity(intent);
                PoPuWindowUtils.getIntance().dismissPopuWindow();
           }*/
    }
}
