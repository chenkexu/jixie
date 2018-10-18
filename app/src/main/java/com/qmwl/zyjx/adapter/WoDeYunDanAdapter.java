package com.qmwl.zyjx.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.activity.BaoJiaActivity;
import com.qmwl.zyjx.activity.ZaiXianZhiFuActivity2;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.YunDanBean;
import com.qmwl.zyjx.fragment.WoDeYunDanFragment;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.DateUtils;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.view.CommomDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 * 我的运单adapter
 */

public class WoDeYunDanAdapter extends MyBaseAdapter<YunDanBean> {
    //是否是承运方运单的adapter
    private boolean isYunDan = false;
    private Context cx;
    private WoDeYunDanFragment fragment;

    private List<ViewHolder> textViews = new ArrayList<>();

    private long index = 0;
    private String tian = "";
    private String yijieshu = "";
    private String xiaoshi = "";
    private String fenzhong = "";
    private String miao = "";
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
        this.mList = mList;
        mHandler.sendEmptyMessageDelayed(1, 1000);
        notifyDataSetChanged();
    }

    public void cancelRunnable() {
        mHandler.removeMessages(1);
    }


    public WoDeYunDanAdapter(Context cx) {
        this.cx = cx;
        initString();
    }

    public WoDeYunDanAdapter(Context cx, WoDeYunDanFragment fragment) {
        this.fragment = fragment;
        this.cx = cx;
        initString();
    }

    private void initString() {
        tian = getResouseString(cx, R.string.tian);
        yijieshu = getResouseString(cx, R.string.yijieshu);
        xiaoshi = getResouseString(cx, R.string.xiaoshi);
        fenzhong = getResouseString(cx, R.string.fenzhong);
        miao = getResouseString(cx, R.string.miao);
    }

    public void setChengYunFang(boolean isChengyun) {
        isYunDan = isChengyun;
        notifyDataSetChanged();
    }

    @Override
    protected View getItemView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.wodeyundan_item_layout, null);
        }
        ViewHolder holder = getHolder(convertView);
        YunDanBean item = getItem(position);
        holder.fahuodiTv.setText(item.getT_address());
        holder.shouhuodiTv.setText(item.getS_mdd());
//        holder.shopTypeTv.setText(item.getFormat() + "    " + item.getT_weight());
        holder.shopTypeTv.setText(item.getT_name() + "    " + item.getFormat());
        holder.zhuanghuoshijianTv.setText(item.getDelivery_time());

        int status = item.getStatus();

        if (item.getRemaining() > 10) {
            textViews.add(holder);
        } else {
            textViews.remove(holder);
            holder.daojishi.setText(yijieshu);
        }

        if (isYunDan) {
            //承运方运单
            holder.baojiaContainer.setVisibility(View.VISIBLE);
            holder.baojia.setVisibility(View.GONE);
//            holder.baojiaNum.setVisibility(View.GONE);
            holder.iv.setImageResource(R.mipmap.baojia);
            holder.statueTv.setText(getResouseString(parent, R.string.baojia));
            holder.priceTv.setVisibility(View.VISIBLE);
            holder.priceTv.setText(item.getPrice() + getResouseString(parent, R.string.yuan));
            hideBottomView(holder, status,item.getPayCode());
        } else {
            //货主运单
//            holder.daojishi.setText(item.getRemaining()+" ");
            holder.iv.setImageResource(getStatueImageView(status));
            holder.statueTv.setText(getStatueName(status, parent));
            holder.baojiaNum.setText(String.valueOf(item.getNum()));
            if (status == 0) {
                holder.baojiaContainer.setVisibility(View.VISIBLE);
                holder.chakanbaojia.setVisibility(View.VISIBLE);
                holder.fukuanTv.setVisibility(View.GONE);
                holder.yifukuanTv.setVisibility(View.GONE);
                holder.baojia.setVisibility(View.VISIBLE);
                holder.baojiaNum.setVisibility(View.VISIBLE);

            } else if (status == 1) {
                holder.baojiaContainer.setVisibility(View.VISIBLE);
                holder.chakanbaojia.setVisibility(View.GONE);
//                holder.fukuanTv.setVisibility(View.VISIBLE);
//                holder.yifukuanTv.setVisibility(View.VISIBLE);
                holder.baojia.setVisibility(View.GONE);
                holder.baojiaNum.setVisibility(View.GONE);
                if (item.getPayCode() == 0) {
                    holder.fukuanTv.setVisibility(View.VISIBLE);
                    holder.yifukuanTv.setVisibility(View.GONE);
                } else if (item.getPayCode() == 1) {
                    holder.fukuanTv.setVisibility(View.GONE);
                    holder.yifukuanTv.setVisibility(View.VISIBLE);
                }
            } else {
                holder.baojiaContainer.setVisibility(View.GONE);
            }
        }
        holder.onClick.setData(item);
        holder.setData(item);

        return convertView;
    }


    //设置底部按钮是否显示
    private void hideBottomView(ViewHolder holder, int status,int payCode) {
        holder.chakanbaojia.setVisibility(View.GONE);
        holder.dengdaixuanze.setVisibility(View.GONE);
        holder.querenzhuanghuo.setVisibility(View.GONE);
        holder.querenxiehuo.setVisibility(View.GONE);
        holder.shenqingjiesuan.setVisibility(View.GONE);
        holder.yiwancheng.setVisibility(View.GONE);
        holder.huozhuweixuanze.setVisibility(View.GONE);
        holder.jiesuanshenhezhong.setVisibility(View.GONE);
        holder.fukuanTv.setVisibility(View.GONE);
        holder.yifukuanTv.setVisibility(View.GONE);
        holder.weifukuanTv.setVisibility(View.GONE);
        holder.jiesuanshenheweitongguo.setVisibility(View.GONE);
        //sid为状态码状态状态0(等待报价)，1(确认装货)，2确认卸货，3申请结算，9完成 ,未被选择-1
        //5结算审核中，4不通过，9审核通过（完成）
        switch (status) {
            case 0:
                holder.dengdaixuanze.setVisibility(View.VISIBLE);
                break;
            case 1:
                if (payCode==0){
                    holder.weifukuanTv.setVisibility(View.VISIBLE);
                    holder.yifukuanTv.setVisibility(View.GONE);
                }else{
                    holder.weifukuanTv.setVisibility(View.GONE);
                    holder.yifukuanTv.setVisibility(View.VISIBLE);
                }
                holder.querenzhuanghuo.setVisibility(View.VISIBLE);
                break;
            case 2:
                holder.querenxiehuo.setVisibility(View.VISIBLE);
                break;
            case 3:
                holder.shenqingjiesuan.setVisibility(View.VISIBLE);
                break;
            case 4:
                holder.jiesuanshenheweitongguo.setVisibility(View.VISIBLE);
                break;
            case 5:
                holder.jiesuanshenhezhong.setVisibility(View.VISIBLE);
                break;
            case 9:
                holder.yiwancheng.setVisibility(View.VISIBLE);
                break;
            case -1:
                holder.huozhuweixuanze.setVisibility(View.VISIBLE);
                break;
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
        YunDanBean bean;

        View baojiaContainer;
        ImageView iv;
        TextView statueTv, priceTv, baojia, baojiaNum, fahuodiTv, shouhuodiTv, shopTypeTv, fukuanTv, yifukuanTv,weifukuanTv,
                zhuanghuoshijianTv, daojishi, chakanbaojia, dengdaixuanze, querenzhuanghuo, querenxiehuo, shenqingjiesuan, yiwancheng, huozhuweixuanze, jiesuanshenhezhong, jiesuanshenheweitongguo;
        MyOnClick onClick;
        ForegroundColorSpan colorSpan1;
        ForegroundColorSpan colorSpan2;
        ForegroundColorSpan colorSpan3;
        ForegroundColorSpan colorSpan4;
        SpannableStringBuilder span;

        ViewHolder(View convertView) {
            span = new SpannableStringBuilder();
            iv = (ImageView) convertView.findViewById(R.id.wodeyundan_item_iv);
            statueTv = (TextView) convertView.findViewById(R.id.wodeyundan_item_statuetv);
            priceTv = (TextView) convertView.findViewById(R.id.wodeyundan_item_price);
            baojia = (TextView) convertView.findViewById(R.id.wodeyundan_item_baojiashu);
            baojiaNum = (TextView) convertView.findViewById(R.id.wodeyundan_item_baojiashu_num);
            fahuodiTv = (TextView) convertView.findViewById(R.id.huoyuan_item_fahuodi);
            shouhuodiTv = (TextView) convertView.findViewById(R.id.huoyuan_item_shouhuodi);
            shopTypeTv = (TextView) convertView.findViewById(R.id.huoyuan_item_huowuleixing);
            zhuanghuoshijianTv = (TextView) convertView.findViewById(R.id.huoyuan_item_zhuanghuoshijian);
            daojishi = (TextView) convertView.findViewById(R.id.huoyuan_item_shengyushijian);
            chakanbaojia = (TextView) convertView.findViewById(R.id.wodeyundan_item_baojiashu_chakanbaojia);
            fukuanTv = (TextView) convertView.findViewById(R.id.wodeyundan_item_baojiashu_fukuan);
            yifukuanTv = (TextView) convertView.findViewById(R.id.wodeyundan_item_baojiashu_yifukuan);
            weifukuanTv = (TextView) convertView.findViewById(R.id.wodeyundan_item_baojiashu_weifukuan);
            dengdaixuanze = (TextView) convertView.findViewById(R.id.wodeyundan_item_baojiashu_dengdaixuanze);
            querenzhuanghuo = (TextView) convertView.findViewById(R.id.wodeyundan_item_baojiashu_querenzhuanghuo);
            querenxiehuo = (TextView) convertView.findViewById(R.id.wodeyundan_item_baojiashu_querenxiehuo);
            shenqingjiesuan = (TextView) convertView.findViewById(R.id.wodeyundan_item_baojiashu_shenqingjiesuan);
            jiesuanshenheweitongguo = (TextView) convertView.findViewById(R.id.wodeyundan_item_baojiashu_jiesuanshenweihezhong);
            yiwancheng = (TextView) convertView.findViewById(R.id.wodeyundan_item_baojiashu_yiwancheng);
            huozhuweixuanze = (TextView) convertView.findViewById(R.id.wodeyundan_item_baojiashu_huozhuweixuanze);
            jiesuanshenhezhong = (TextView) convertView.findViewById(R.id.wodeyundan_item_baojiashu_jiesuanshenhezhong);
            onClick = new MyOnClick();
            fukuanTv.setOnClickListener(onClick);
            chakanbaojia.setOnClickListener(onClick);
            dengdaixuanze.setOnClickListener(onClick);
            querenzhuanghuo.setOnClickListener(onClick);
            querenxiehuo.setOnClickListener(onClick);
            shenqingjiesuan.setOnClickListener(onClick);
            yiwancheng.setOnClickListener(onClick);
            huozhuweixuanze.setOnClickListener(onClick);
            baojiaContainer = convertView.findViewById(R.id.wodeyundan_item_baojiashu_container);

            colorSpan1 = new ForegroundColorSpan(Color.RED);
            colorSpan2 = new ForegroundColorSpan(Color.RED);
            colorSpan3 = new ForegroundColorSpan(Color.RED);
            colorSpan4 = new ForegroundColorSpan(Color.RED);
//            textViews.add(this);

            convertView.setTag(this);
        }

        void setData(YunDanBean item) {
            bean = item;
        }

        void dealBeanTime() {
            if (bean == null) {
                return;
            }
//            long l = bean.getTime() - index * 1000;
            String s = DateUtils.timeCha(bean.getRemaining() - index, tian, xiaoshi, fenzhong, miao);
            setDaojishi(s);
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


    }

    class MyOnClick implements View.OnClickListener {
        YunDanBean bean;

        void setData(YunDanBean bean) {
            this.bean = bean;
        }

        @Override
        public void onClick(View v) {
            if (bean == null) {
                return;
            }
            switch (v.getId()) {
                case R.id.wodeyundan_item_baojiashu_chakanbaojia:
                    //查看报价
                    Intent intent = new Intent(v.getContext(), BaoJiaActivity.class);
                    intent.putExtra("tid", bean.getTid());
                    v.getContext().startActivity(intent);
                    break;
                case R.id.wodeyundan_item_baojiashu_querenzhuanghuo:
                    //确认装货
                    new CommomDialog(cx, R.style.dialog, getResouseString(v, R.string.querenzhuanghuo) + "?", new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm) {
                                querenzhuanghuo(bean.getId());
                                dialog.dismiss();
                            }
                        }
                    }).show();
                    break;
                case R.id.wodeyundan_item_baojiashu_querenxiehuo:
                    //确认卸货
                    new CommomDialog(cx, R.style.dialog, getResouseString(v, R.string.querenxiehuo) + "?", new CommomDialog.OnCloseListener() {
                        @Override
                        public void onClick(Dialog dialog, boolean confirm) {
                            if (confirm) {
                                querenxiehuo(bean.getId());
                                dialog.dismiss();
                            }
                        }
                    }).show();
                    break;
                case R.id.wodeyundan_item_baojiashu_shenqingjiesuan:
                    //申请结算
                    shenqingjiesuan(bean.getId());
                    break;
                case R.id.wodeyundan_item_baojiashu_fukuan:
                    //付款
                    Intent intent2 = new Intent(v.getContext(), ZaiXianZhiFuActivity2.class);
                    intent2.putExtra(ZaiXianZhiFuActivity2.DINGDANHAO_CID, bean.getCid());
                    v.getContext().startActivity(intent2);
                    break;
            }
        }
    }

    private void querenzhuanghuo(String id) {
        String sid = "2";
        AndroidNetworking.get(Contact.yunshu_yundan_caozuo + "?id=" + id + "&uid=" + MyApplication.getIntance().userBean.getUid() + "&sid=" + sid)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String content = "";
                        try {
                            if (JsonUtils.is100Success(response)) {
                                if (fragment != null) {
                                    fragment.update();
                                }
                                content = getResouseString(cx, R.string.tijiaochenggong);
                            } else {
                                content = getResouseString(cx, R.string.tijiaoshibai);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            content = getResouseString(cx, R.string.tijiaoshibai);
                        }
                        new CommomDialog(cx, R.style.dialog, content, new CommomDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                Intent intent = new Intent(WoDeYunDanFragment.UPDATE_DATA);
                                cx.sendBroadcast(intent);
                                dialog.dismiss();
                            }
                        }).setHideCancelButton().show();
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });

    }

    //卸货
    private void querenxiehuo(String id) {
        String sid = "3";
        AndroidNetworking.get(Contact.yunshu_yundan_caozuo + "?id=" + id + "&uid=" + MyApplication.getIntance().userBean.getUid() + "&sid=" + sid)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String content = "";
                        try {
                            if (JsonUtils.is100Success(response)) {
                                if (fragment != null) {
                                    fragment.update();
                                }
                                content = getResouseString(cx, R.string.tijiaochenggong);
                            } else {
                                content = getResouseString(cx, R.string.tijiaoshibai);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            content = getResouseString(cx, R.string.tijiaoshibai);
                        }
                        new CommomDialog(cx, R.style.dialog, content, new CommomDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                Intent intent = new Intent(WoDeYunDanFragment.UPDATE_DATA);
                                cx.sendBroadcast(intent);
                                dialog.dismiss();
                            }
                        }).setHideCancelButton().show();
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });

    }

    //申请结算
    private void shenqingjiesuan(String id) {
        String sid = "5";
        AndroidNetworking.get(Contact.yunshu_yundan_caozuo + "?id=" + id + "&uid=" + MyApplication.getIntance().userBean.getUid() + "&sid=" + sid)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String content = "";
                        try {
                            if (JsonUtils.is100Success(response)) {
                                if (fragment != null) {
                                    fragment.update();
                                }
                                content = getResouseString(cx, R.string.tijiaochenggong);
                            } else {
                                content = getResouseString(cx, R.string.tijiaoshibai);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            content = getResouseString(cx, R.string.tijiaoshibai);
                        }
                        new CommomDialog(cx, R.style.dialog, content, new CommomDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                Intent intent = new Intent(WoDeYunDanFragment.UPDATE_DATA);
                                cx.sendBroadcast(intent);
                                dialog.dismiss();
                            }
                        }).setHideCancelButton().show();
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });

    }


    private String getStatueName(int statue, View parent) {
        String statueStr = "";
        switch (statue) {
            case 0:
                statueStr = getResouseString(parent.getContext(), R.string.zhengzaijinxingzhong);
                break;
            case 1:
                statueStr = getResouseString(parent.getContext(), R.string.daifahuo);
                break;
            case 2:
                statueStr = getResouseString(parent.getContext(), R.string.daishouhuo);
                break;
            case 3:
                statueStr = getResouseString(parent.getContext(), R.string.yiwancheng);
                break;
            default:
                statueStr = getResouseString(parent.getContext(), R.string.all);
                break;
        }
        return statueStr;
    }

    private int getStatueImageView(int statue) {
        int id = 0;
        switch (statue) {
            case 0:
                id = R.mipmap.huoyun_jinxingzhong;
                break;
            case 1:
                id = R.mipmap.huoyun_daifahuo;
                break;
            case 2:
                id = R.mipmap.huoyun_daishouhuo;
                break;
            case 3:
                id = R.mipmap.huoyun_yiwancheng;
                break;
            default:
                id = R.mipmap.huoyun_yiwancheng;
                break;
        }
        return id;

    }
}
