package com.qmwl.zyjx.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/20.
 * 订单的bean
 */

public class DingDanBean implements Serializable {
    private String order_id;
    //订单号
    private String order_no;
    //商家名字
    private String shop_name;
    //订单状态
    private String dingdan_statue;
    //订单状态码
    private int dingdan_statue_code;
    //商家电话
    private String receiver_mobile;
    //运费
    private double shipping_money;
    //订单价钱
    private String price;
    //商家手机号
    private String shop_phone;
    //是否已经评价 0 未评价 1已评价
    private int is_evaluate;
    //商品集合
    private List<ShoppingBean> shopList;
    private String shop_logo;

    private double order_money;

    public double getOrder_money() {
        return order_money;
    }

    public void setOrder_money(double order_money) {
        this.order_money = order_money;
    }

    public String getShop_logo() {
        return shop_logo;
    }

    public void setShop_logo(String shop_logo) {
        this.shop_logo = shop_logo;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    //获取商品数量
    public int getNum() {
        if (shopList == null) {
            return 0;
        }
        int num = 0;
        for (int i = 0; i < shopList.size(); i++) {
            ShoppingBean shoppingBean = shopList.get(i);
            num += shoppingBean.getNumber();
        }
        return num;
    }

    //获取商品总价
    public double getZongPrice() {
//        if (shopList == null) {
//            return 0;
//        }
//        double num = 0;
//        for (int i = 0; i < shopList.size(); i++) {
//            ShoppingBean shoppingBean = shopList.get(i);
//            num += shoppingBean.getPrice() * shoppingBean.getNumber();
//        }
        return order_money;
    }

    public int getDingdan_statue_code() {
        return dingdan_statue_code;
    }

    public void setDingdan_statue_code(int dingdan_statue_code) {
        this.dingdan_statue_code = dingdan_statue_code;
    }

    public String getShop_phone() {
        return shop_phone;
    }

    public void setShop_phone(String shop_phone) {
        this.shop_phone = shop_phone;
    }

    public double getShipping_money() {
        return shipping_money;
    }

    public void setShipping_money(double shipping_money) {
        this.shipping_money = shipping_money;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public String getDingdan_statue() {
        return dingdan_statue;
    }

    public void setDingdan_statue(String dingdan_statue) {
        this.dingdan_statue = dingdan_statue;
    }

    public String getReceiver_mobile() {
        return receiver_mobile;
    }

    public void setReceiver_mobile(String receiver_mobile) {
        this.receiver_mobile = receiver_mobile;
    }

    public List<ShoppingBean> getShopList() {
        return shopList;
    }

    public void setShopList(List<ShoppingBean> shopList) {
        this.shopList = shopList;
    }

    public int getIs_evaluate() {
        return is_evaluate;
    }

    public void setIs_evaluate(int is_evaluate) {
        this.is_evaluate = is_evaluate;
    }
}
