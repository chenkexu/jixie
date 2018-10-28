package com.qmwl.zyjx.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.activity.BaoXianActivity;
import com.qmwl.zyjx.activity.LoginActivity;
import com.qmwl.zyjx.activity.PeiJianShaiXuanFirstActivity;
import com.qmwl.zyjx.activity.RegisterDuiGongTishiActivity;
import com.qmwl.zyjx.activity.RegisterPersonActivity;
import com.qmwl.zyjx.activity.RegisterQiYeActivity;
import com.qmwl.zyjx.activity.RegisterSelecterActivity;
import com.qmwl.zyjx.activity.SeriverYeActivity;
import com.qmwl.zyjx.activity.ShoppingSecondActivity;
import com.qmwl.zyjx.activity.TransportActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.MainDataBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.view.CommomDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2017/7/19.
 * 首页的适配器
 */

public class MainFragmentAdapter extends MyBaseAdapter<MainDataBean> {
    private boolean isLoading = false;

    @Override
    protected View getItemView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_fragment_item, null);
        }
        ViewHolder holder = getHolder(convertView);
        MainDataBean item = getItem(position);

        holder.listener1.setName(item.getCategory_name());
        holder.listener1.setId(item.getCategory_id());
        holder.listener1.setTypeId(item.getType());
        holder.listener1.setType(true);
        openImage(parent, item.getCategory_pic(), holder.iv1);
//        Glide.with(parent).load(item.getCategory_pic()).into(holder.iv1);
        List<MainDataBean> childs = item.getChilds();
        holder.iv2.setVisibility(View.GONE);
        holder.iv3.setVisibility(View.GONE);
        if (childs != null) {
            for (int i = 0; i < childs.size(); i++) {
                MainDataBean mainDataBean = childs.get(i);
                switch (i) {
                    case 0:
                        holder.iv2.setVisibility(View.VISIBLE);
                        holder.listener2.setName(mainDataBean.getCategory_name());
                        holder.listener2.setId(mainDataBean.getCategory_id());
                        holder.listener2.setTypeId(mainDataBean.getType());
                        holder.listener2.setType(false);
//                        Glide.with(parent).load(mainDataBean.getCategory_pic()).into(holder.iv2);
                        openImage(parent, mainDataBean.getCategory_pic(), holder.iv2);
                        break;
                    case 1:
                        holder.iv3.setVisibility(View.VISIBLE);
//                        ViewGroup.LayoutParams layoutParams3 = holder.iv3.getLayoutParams();
//                        int v3 = (int) (holder.iv3.getWidth() * 0.82);
//                        layoutParams3.height = v3;
//                        holder.iv3.setLayoutParams(layoutParams3);

                        holder.listener3.setName(mainDataBean.getCategory_name());
                        holder.listener3.setId(mainDataBean.getCategory_id());
                        holder.listener3.setTypeId(mainDataBean.getType());
                        holder.listener3.setType(false);
//                        Glide.with(parent).load(mainDataBean.getCategory_pic()).into(holder.iv3);
                        openImage(parent, mainDataBean.getCategory_pic(), holder.iv3);
                        break;
                }
            }

        }

        if (position == 0) {
            holder.title.setVisibility(View.GONE);
        } else if (item.isFirst) {
            if (holder.qita.equals(item.getParentName())) {
                String qitaStr = holder.qita;
                if (!MyApplication.getIntance().isChina) {
                    qitaStr = getResouseString(parent.getContext(), R.string.qita_en);
                }
                qitaOrYanshenfuwu(parent, holder, qitaStr);

            } else if (holder.yanshenfuwu.equals(item.getParentName())) {
                String yanshenfuwuStr = holder.yanshenfuwu;
                if (!MyApplication.getIntance().isChina) {
                    yanshenfuwuStr = getResouseString(parent.getContext(), R.string.yanshenfuwu_en);
                }
                qitaOrYanshenfuwu(parent, holder, yanshenfuwuStr);

            }else{
                qitaOrYanshenfuwu(parent, holder, item.getParentName());
            }

        } else {
            holder.title.setVisibility(View.GONE);
        }
        return convertView;
    }

    private void qitaOrYanshenfuwu(ViewGroup parent, ViewHolder holder, String qitaStr) {
        holder.title.setVisibility(View.VISIBLE);
        String leftkuohao = parent.getContext().getString(R.string.zuokuobao);
        String rightkuohao = parent.getContext().getString(R.string.youkuohao);
        holder.title.setText(leftkuohao + qitaStr + rightkuohao);
    }

    private ViewHolder getHolder(View convertView) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (holder == null) {
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.main_fragment_item_title);
            holder.iv1 = (ImageView) convertView.findViewById(R.id.main_fragment_item_iv1);
            holder.iv2 = (ImageView) convertView.findViewById(R.id.main_fragment_item_iv2);
            holder.iv3 = (ImageView) convertView.findViewById(R.id.main_fragment_item_iv3);
            holder.listener1 = new viewOnItemListener(convertView.getContext());
            holder.listener2 = new viewOnItemListener(convertView.getContext());
            holder.listener3 = new viewOnItemListener(convertView.getContext());
            holder.iv1.setOnClickListener(holder.listener1);
            holder.iv2.setOnClickListener(holder.listener2);
            holder.iv3.setOnClickListener(holder.listener3);
            holder.qita = getResouseString(convertView.getContext(), R.string.qita);
            holder.yanshenfuwu = getResouseString(convertView.getContext(), R.string.yanshenfuwu);

//            ViewGroup.LayoutParams layoutParams = holder.iv1.getLayoutParams();
//            int v = (int) (holder.iv1.getWidth() * 0.8);
//            layoutParams.height = v;
//            holder.iv1.setLayoutParams(layoutParams);
//
//            ViewGroup.LayoutParams layoutParams2 = holder.iv2.getLayoutParams();
//            int v2 = (int) (holder.iv2.getWidth() * 0.82);
//            layoutParams2.height = v2;
//            holder.iv2.setLayoutParams(layoutParams2);
//            holder.iv3.setLayoutParams(layoutParams2);

            convertView.setTag(holder);
        }
        return holder;

    }

    class ViewHolder {
        TextView title, name1, name2, name3;
        ImageView iv1, iv2, iv3;
        viewOnItemListener listener1, listener2, listener3;
        String qita, yanshenfuwu;
    }

    class viewOnItemListener implements View.OnClickListener {
        String id;
        String name;
        Context cx;
        String type;
        boolean isMain = false;//是否是大图，往二级页面跳

        viewOnItemListener(Context cx) {
            this.cx = cx;
        }

        void setId(String id) {
            this.id = id;
        }

        void setTypeId(String type) {
            this.type = type;
        }

        void setName(String name) {
            this.name = name;
        }

        void setType(boolean isMain) {
            this.isMain = isMain;
        }

        @Override
        public void onClick(View v) {
            Intent intent = null;
            //type=1 金融，type=2  服务， type=3  招聘培训  4：保险  5：运输维修  6：我要开店, 7:配件
            if ("5".equals(type)) {
                //运输页面
                intent = new Intent(cx, TransportActivity.class);
                cx.startActivity(intent);
                return;
            } else if ("6".equals(type)) {
                //我要开店
//                if (MyApplication.getIntance().isLogin() && MyApplication.getIntance().is_system == 1) {
//                    Toast.makeText(v.getContext(), getResouseString(v, R.string.ninyijingshishangjia), Toast.LENGTH_SHORT).show();
//                    return;
//                }
//                intent = new Intent(v.getContext(), RegisterActivity.class);
//                v.getContext().startActivity(intent);
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(cx, LoginActivity.class);
                    cx.startActivity(intent);
                    return;
                }
                if (!isLoading) {
                    isLoading = true;
                    getWoYaokaidianStatue();
                }
                return;
            } else if ("2".equals(type)) {
                //服务
                intent = new Intent(cx, SeriverYeActivity.class);
                intent.putExtra(SeriverYeActivity.SERIVERYEACTIVITY_TYPE, SeriverYeActivity.TYPE_FUWU);
                cx.startActivity(intent);
                return;
            } else if ("3".equals(type)) {
                //招聘培训
                intent = new Intent(cx, SeriverYeActivity.class);

                intent.putExtra(SeriverYeActivity.SERIVERYEACTIVITY_TYPE, SeriverYeActivity.TYPE_ZHAOPINPEIXUN);
                cx.startActivity(intent);
                return;
            } else if ("1".equals(type)) {
                //金融
                intent = new Intent(cx, SeriverYeActivity.class);
                intent.putExtra(SeriverYeActivity.SERIVERYEACTIVITY_TYPE, SeriverYeActivity.TYPE_JINRONG);
                cx.startActivity(intent);
                return;
            } else if ("4".equals(type)) {
                //保险
                intent = new Intent(cx, BaoXianActivity.class);
                cx.startActivity(intent);
                return;
            } else if ("7".equals(type)) {
                //配件
                intent = new Intent(cx, PeiJianShaiXuanFirstActivity.class);
                intent.putExtra("id", id);
//                intent.putExtra("id", "484");
                cx.startActivity(intent);
//                Toast.makeText(cx, "暂未开放", Toast.LENGTH_SHORT).show();
                return;
            }
            //此为区分首页每item的大小图点击的区别(现已废弃，现在逻辑为直接进入二级商品列表)
//            if (isMain) {
            intent = new Intent(cx, ShoppingSecondActivity.class);
//            } else {
//                intent = new Intent(cx, ShoppingThreadActivity.class);
//            }
            intent.putExtra(ShoppingSecondActivity.Category_id, id);
            intent.putExtra(ShoppingSecondActivity.Category_name, name);
            cx.startActivity(intent);
        }

        //获取个人信息状态
        private void getWoYaokaidianStatue() {
            AndroidNetworking.get(Contact.kaidian_url + "?user_id=" + MyApplication.getIntance().userBean.getUid())
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            isLoading = false;
                            try {
                                //0 未开通 1此用户对公付款 2 此用户是商户 3提交资料，未到支付 ,4:外汇支付
                                int code = response.getInt("code");
                                Intent intent = null;
                                switch (code) {
                                    case 0:
                                        intent = new Intent(cx, RegisterSelecterActivity.class);
                                        cx.startActivity(intent);
                                        break;
                                    case 1:
                                        intent = new Intent(cx, RegisterDuiGongTishiActivity.class);
                                        cx.startActivity(intent);
                                        break;
                                    case 2:
                                    case 5:
                                        new CommomDialog(cx, R.style.dialog, getResouseString(cx, R.string.ninyijingshishangjia)).setHideCancelButton().show();
                                        break;
                                    case 3:
                                        JSONObject data1 = response.getJSONObject("data");
                                        JSONObject data = data1.getJSONObject("niu_index_response");
                                        String type = data.getString("type");
                                        if ("1".equals(type)) {
                                            intent = new Intent(cx, RegisterPersonActivity.class);
                                            intent.putExtra("type", true);
                                        } else {
                                            intent = new Intent(cx, RegisterQiYeActivity.class);
                                            intent.putExtra("type", true);
                                        }
                                        cx.startActivity(intent);
                                        break;
                                    case 4:
                                        intent = new Intent(cx, RegisterDuiGongTishiActivity.class);
                                        intent.putExtra("iswaihui", true);
                                        cx.startActivity(intent);
                                        break;
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                            isLoading = false;
                        }
                    });
        }
    }
}
