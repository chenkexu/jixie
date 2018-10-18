package com.qmwl.zyjx.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.activity.LoginActivity;
import com.qmwl.zyjx.activity.QueRenDingDanActivity;
import com.qmwl.zyjx.adapter.GouWuCheAdapter;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.GGBean;
import com.qmwl.zyjx.bean.GouWuCheBean;
import com.qmwl.zyjx.bean.ShoppingBean;
import com.qmwl.zyjx.utils.Contact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/7/19.
 * 购物车页面
 */

public class ThreadFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, CompoundButton.OnCheckedChangeListener {
    public static final String REMOVE_SHOPS_LIST = "com.gh.remove.list";
    public static final String SHOP_ID = "com.gh.shop_id";
    public static final String SHOP_TYPE = "com.gh.shop_type";
    public static final int DELETE_SHOP = 1;//删除商品
    public static final int UPDATE_PRICE = 2;//重新计算价格
    private CheckBox quanxuanCheckBox;
    private GouWuCheAdapter adapter;
    private ShoppingBroadCast broadCast;
    private TextView privceTv;//价格框
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DELETE_SHOP:
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                        updatePrice();
                    }
                    break;
                case UPDATE_PRICE:
                    privceTv.setText(getString(R.string.heji) + String.valueOf(price));
                    break;

                case 3:
                    //不全选
                    if (quanxuanCheckBox!=null){
                        quanxuanCheckBox.setChecked(false);
                    }
                    if (adapter!=null){
                        adapter.notifyDataSetChanged();
                    }
                    break;
                case 4:
                    //全选
                    if (quanxuanCheckBox!=null) {
                        quanxuanCheckBox.setChecked(true);
                    }
                    if (adapter!=null){
                        adapter.notifyDataSetChanged();
                    }
                    break;
            }


        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.thread_fragment_layout, null);
        initView(rootView);
        return rootView;
    }
    TextView titleTv;
    private void initView(View rootView) {

        titleTv = (TextView) rootView.findViewById(R.id.base_top_bar_title);
        titleTv.setText(R.string.gouwuche);
        ListView mLv = (ListView) rootView.findViewById(R.id.shopping_layout_listview);
        rootView.findViewById(R.id.shopping_layout_jiesuan).setOnClickListener(this);

        privceTv = (TextView) rootView.findViewById(R.id.shopping_layout_price_num);

        quanxuanCheckBox = (CheckBox) rootView.findViewById(R.id.shopping_layout_quanxuan);
        quanxuanCheckBox.setOnClickListener(this);
//        quanxuanCheckBox.setOnCheckedChangeListener(this);
//        ShoppingAdapter adapter = new ShoppingAdapter();
        adapter = new GouWuCheAdapter();
        mLv.setAdapter(adapter);
        mLv.setOnItemClickListener(this);
        getGouWuCheData();
    }

    @Override
    public void onResume() {
        super.onResume();
//        quanxuanCheckBox.setChecked(false);
        getGouWuCheData();
    }

    /**
     * 获取购物车数据
     */
    private void getGouWuCheData() {
        if (!MyApplication.getIntance().isLogin()) {
//            Toast.makeText(getContext(), "还未登陆", Toast.LENGTH_SHORT).show();
            adapter.setData(null);
            return;
        }
        AndroidNetworking.get(Contact.getgouwuche + MyApplication.getIntance().userBean.getUid())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<GGBean> ggBeen = parseGouWuChe(response);
                        List<GouWuCheBean> gouWuCheBeen = dealGGBeans(ggBeen);
                        adapter.setData(gouWuCheBeen);
                        updatePrice();
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });


    }

    //处理数据格式
    private List<GouWuCheBean> dealGGBeans(List<GGBean> ggBeen) {
        if (ggBeen == null) {
            return null;
        }
        List<GouWuCheBean> gwList = new ArrayList<>();
        List<String> shopidList = new ArrayList<>();
        for (int i = 0; i < ggBeen.size(); i++) {
            GGBean bean = ggBeen.get(i);
            String shop_id = bean.getShop_id();
            GouWuCheBean gouwucheBean = null;
            if (!shopidList.contains(shop_id)) {
                gouwucheBean = new GouWuCheBean();
                gouwucheBean.setShop_id(shop_id);
                gouwucheBean.setShop_name(bean.getShop_name());
                gouwucheBean.setUrl(bean.getUrl());
                gouwucheBean.setLogo(bean.getShop_logo());
                List<ShoppingBean> shoppingBeanList = new ArrayList<>();
                gouwucheBean.setShoppingBeanList(shoppingBeanList);
                shopidList.add(shop_id);
                gwList.add(gouwucheBean);
            } else {
                int i1 = shopidList.indexOf(shop_id);
                gouwucheBean = gwList.get(i1);
            }
            ShoppingBean shoppingBean = new ShoppingBean();
            shoppingBean.setShop_id(bean.getGoods_id());
            shoppingBean.setName(bean.getGoods_name());
            shoppingBean.setUrl(bean.getGoods_url());
            shoppingBean.setNumber(bean.getNum());
            shoppingBean.setPrice(bean.getPrice());
            shoppingBean.setCart_id(bean.getCart_id());
            shoppingBean.setIv_pic(bean.getPic_cover_mid());
            shoppingBean.setModel(bean.getGoods_model());
            shoppingBean.setYunfei(bean.getExpress_price());
            gouwucheBean.getShoppingBeanList().add(shoppingBean);
        }
        return gwList;
    }

    private List<GGBean> parseGouWuChe(JSONObject response) {
        List<GGBean> list = null;
        try {
            JSONObject data = response.getJSONObject("data");
            JSONArray niu_index_response = data.getJSONArray("niu_index_response");
            list = new ArrayList<>();
            for (int i = 0; i < niu_index_response.length(); i++) {
                JSONObject obj = niu_index_response.getJSONObject(i);
                GGBean bean = new GGBean();
                String buyer_id = obj.getString("buyer_id");
                int num = obj.getInt("num");
                double price = obj.getDouble("price");
                String goods_model = obj.getString("goods_model");
                String cart_id = obj.getString("cart_id");
                String shop_id = obj.getString("shop_id");
                String pic_cover_mid = obj.getString("pic_cover_mid");
                String goods_id = obj.getString("goods_id");
                String goods_name = obj.getString("goods_name");
                String url = obj.getString("url");
                String shop_name = obj.getString("shop_name");
                String goods_url = obj.getString("goods_url");
                if (obj.has("shop_logo")) {
                    String shop_logo = obj.getString("shop_logo");
                    bean.setShop_logo(shop_logo);
                }
                if (obj.has("express_price")) {
                    double express_price = obj.getDouble("express_price");
                    bean.setExpress_price(express_price);
                }
                bean.setGoods_url(goods_url);
                bean.setBuyer_id(buyer_id);
                bean.setNum(num);
                bean.setPrice(price);
                bean.setGoods_model(goods_model);
                bean.setCart_id(cart_id);
                bean.setShop_id(shop_id);
                bean.setPic_cover_mid(pic_cover_mid);
                bean.setGoods_id(goods_id);
                bean.setGoods_name(goods_name);
                bean.setUrl(url);
                bean.setShop_name(shop_name);
                list.add(bean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    private ExecutorService threadPool;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        threadPool = Executors.newSingleThreadExecutor();
        broadCast = new ShoppingBroadCast();
        IntentFilter filter = new IntentFilter(REMOVE_SHOPS_LIST);
        getActivity().registerReceiver(broadCast, filter);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (broadCast != null) {
            getActivity().unregisterReceiver(broadCast);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.shopping_layout_jiesuan:
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(getContext(), LoginActivity.class);
                    getActivity().startActivityForResult(intent, 0);
                    return;
                }
                String s = dealJson();//备用多商品
                if (!"".equals(s)) {
                    intent = new Intent(getContext(), QueRenDingDanActivity.class);
                    intent.putExtra(QueRenDingDanActivity.DATA, s);
                    intent.putExtra(QueRenDingDanActivity.TYPE, true);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), getString(R.string.weixuanzeshangpin), Toast.LENGTH_SHORT).show();
                }
//                dealSingleData();

                break;
            case R.id.shopping_layout_quanxuan:
                quanxuanClick(
                        quanxuanCheckBox.isChecked());
                break;
        }
    }

    private void dealSingleData() {

        ShoppingBean bean = null;
        GouWuCheBean gouwuchebean1 = null;
        boolean isSingle = true;
        int num = 0;
        loop:
        if (adapter != null && adapter.getData() != null) {
            for (int i = 0; i < adapter.getData().size(); i++) {
                GouWuCheBean gouWuCheBean = adapter.getData().get(i);
                if (gouWuCheBean != null) {
                    List<ShoppingBean> shoppingBeanList = gouWuCheBean.getShoppingBeanList();
                    if (shoppingBeanList != null) {
                        for (int j = 0; j < shoppingBeanList.size(); j++) {
                            ShoppingBean bean1 = shoppingBeanList.get(j);
                            if (bean == null) {
                                if (bean1.isSelecter()) {
                                    gouwuchebean1 = gouWuCheBean;
                                    bean = bean1;
                                }
                            } else if (bean1.isSelecter()) {
                                isSingle = false;
                                break loop;
                            }
                        }
                    }

                }
            }
        }

        if (!isSingle) {
            Toast.makeText(getContext(), "只能选择一种商品", Toast.LENGTH_SHORT).show();
            return;
        }
        if (gouwuchebean1 == null || bean == null) {
            Toast.makeText(getContext(), "未选择商品", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject obj = new JSONObject();
        try {
            obj.put("event", "");
            obj.put("logo", gouwuchebean1.getPic_url() + "");
            obj.put("seller_name", gouwuchebean1.getShop_name() + "");
            obj.put("seller_id", gouwuchebean1.getShop_id() + "");

            obj.put("price", bean.getPrice() * bean.getNumber() + "");
            obj.put("number", bean.getNumber() + "");
            obj.put("post_id", bean.getShop_id() + "");
            obj.put("pro_name", bean.getName() + "");
            obj.put("pro_model", bean.getModel() + "");
            obj.put("pro_pic", bean.getLogo_pic() + "");

        } catch (JSONException e) {
            e.printStackTrace();
        }
        String s = obj.toString();
        Intent intent = new Intent(getContext(), QueRenDingDanActivity.class);
        intent.putExtra(QueRenDingDanActivity.DATA, s);
        startActivity(intent);

    }

    private String dealJson() {
        String s = "";
        if (adapter != null && adapter.getData() != null) {
            List<GouWuCheBean> data = adapter.getData();
            List<GouWuCheBean> newList = new ArrayList<>();
            for (GouWuCheBean bean : data) {
                if (bean.isChildSelecter()) {
                    newList.add(bean);
                }
            }
            if (newList.size() == 0) {
                return "";
            }
            s = newList.toString();
        }
        return s;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        quanxuanClick(isChecked);
    }

    private void quanxuanClick(boolean isChecked) {
        List<GouWuCheBean> data = adapter.getData();
        if (data != null) {
            for (int i = 0; i < data.size(); i++) {
                GouWuCheBean gouWuCheBean = data.get(i);
                gouWuCheBean.setSelecter(isChecked);

                List<ShoppingBean> shoppingBeanList = gouWuCheBean.getShoppingBeanList();
                if (shoppingBeanList != null) {
                    for (ShoppingBean bean : shoppingBeanList) {
                        bean.setSelecter(isChecked);
                    }
                }
            }
            adapter.notifyDataSetChanged();
        }
        updatePrice();
    }

    class ShoppingBroadCast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int type = intent.getIntExtra(SHOP_TYPE, DELETE_SHOP);
            switch (type) {
                case DELETE_SHOP:
                    //删除商品
                    deleteShop(intent);
                    break;
                case UPDATE_PRICE:
                    //更新价格
                    updatePrice();
                    break;


            }

        }
    }

    private void updatePrice() {
        threadPool.execute(updatePriceRunnable);
    }

    private void deleteShop(Intent intent) {
        String stringExtra = intent.getStringExtra(SHOP_ID);
        if ("".equals(stringExtra) || stringExtra == null) {
            return;
        }
        if (threadPool != null) {
            threadPool.execute(new deleteRunnable(stringExtra));
        }
    }

    //删除商品的任务
    class deleteRunnable implements Runnable {
        String shop_id = "";

        deleteRunnable(String id) {
            this.shop_id = id;
        }

        @Override
        public void run() {
            if (adapter != null && adapter.getData() != null) {
                List<GouWuCheBean> data = adapter.getData();
                loop:
                for (int i = 0; i < data.size(); i++) {
                    GouWuCheBean gouWuCheBean = data.get(i);
                    if (gouWuCheBean != null) {
                        List<ShoppingBean> shoppingBeanList = gouWuCheBean.getShoppingBeanList();
                        if (shoppingBeanList != null) {
                            for (int j = 0; j < shoppingBeanList.size(); j++) {
                                ShoppingBean bean = shoppingBeanList.get(j);
                                if (shop_id.equals(bean.getShop_id())) {
                                    shoppingBeanList.remove(bean);
                                    if (shoppingBeanList.size() == 0) {
                                        data.remove(gouWuCheBean);
                                    }
                                    mHandler.sendEmptyMessage(DELETE_SHOP);
                                    break loop;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private double price = 0;
    //重新计算价格的方法
    Runnable updatePriceRunnable = new Runnable() {

        @Override
        public void run() {
            if (adapter == null) {
                return;
            }
            if (adapter.getData() == null) {
                return;
            }
            price = 0;
            List<GouWuCheBean> data = adapter.getData();
            boolean isAllSelecter = true;
            for (int i = 0; i < data.size(); i++) {
                GouWuCheBean gouWuCheBean = data.get(i);
                List<ShoppingBean> shoppingBeanList = gouWuCheBean.getShoppingBeanList();
                if (shoppingBeanList == null) {
                    return;
                }
                boolean isShangPuAllSelecter = true;
                for (ShoppingBean bean : shoppingBeanList) {
                    if (bean.isSelecter()) {
                        price += (bean.getPrice() * bean.getNumber()+bean.getYunfei()*bean.getNumber());
                    }else{
                        isShangPuAllSelecter = false;
                    }
                }
                gouWuCheBean.setSelecter(isShangPuAllSelecter);
                if (!isShangPuAllSelecter){
                    isAllSelecter = false;
                }
            }
            if (isAllSelecter){
                mHandler.sendEmptyMessage(4);
            }else{
                mHandler.sendEmptyMessage(3);
            }
            mHandler.sendEmptyMessage(UPDATE_PRICE);
        }
    };
}
