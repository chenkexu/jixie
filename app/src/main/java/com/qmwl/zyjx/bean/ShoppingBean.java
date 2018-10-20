package com.qmwl.zyjx.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/7/27.
 * 商品bean
 */

public class ShoppingBean implements Serializable{
    private static final long serialVersionUID = 3131955390654041987L;
    //      * 1,产品id (shop_id)
//     * 2,商家商标(logo_pic)
//     * 3,商家名称(name)
//     * 4,产品图片(iv_pic)
//     * 5,购买数量(number)
//     * 6,产品型号(model)
    private String cart_id;
    private String shop_id;
    private String logo_pic;
    private String name;
    private String iv_pic;
    private double price;
    private int number;
    private String model;
    private String url;
    private int isNew;//0旧机 1新机
    private int is_lease;////租赁
    private int is_parts;////1是配件 0不是
    private boolean isSelecter;
    private String dingdanUrl;
    private String address;
    private String shopName;//商家名字
    private int is_evaluate;//是否评价,0未评价，1已评价
    private String order_goods_id;
    private String goods_id;
    private double yunfei;//运费

    public double getYunfei() {
        return yunfei;
    }

    public void setYunfei(double yunfei) {
        this.yunfei = yunfei;
    }

    public String getOrder_goods_id() {
        return order_goods_id;
    }

    public void setOrder_goods_id(String order_goods_id) {
        this.order_goods_id = order_goods_id;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getDingdanUrl() {
        return dingdanUrl;
    }

    public void setDingdanUrl(String dingdanUrl) {
        this.dingdanUrl = dingdanUrl;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public boolean isSelecter() {
        return isSelecter;
    }

    public void setSelecter(boolean selecter) {
        isSelecter = selecter;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getIs_lease() {
        return is_lease;
    }

    public void setIs_lease(int is_lease) {
        this.is_lease = is_lease;
    }

    public int getIs_parts() {
        return is_parts;
    }

    public void setIs_parts(int is_parts) {
        this.is_parts = is_parts;
    }

    public int getIsNew() {
        return isNew;
    }

    public void setIsNew(int isNew) {
        this.isNew = isNew;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getLogo_pic() {
        return logo_pic;
    }

    public void setLogo_pic(String logo_pic) {
        this.logo_pic = logo_pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIv_pic() {
        return iv_pic;
    }

    public void setIv_pic(String iv_pic) {
        this.iv_pic = iv_pic;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getIs_evaluate() {
        return is_evaluate;
    }

    public void setIs_evaluate(int is_evaluate) {
        this.is_evaluate = is_evaluate;
    }


    @Override
    public String toString() {
        return "ShoppingBean{" +
                "cart_id='" + cart_id + '\'' +
                ", shop_id='" + shop_id + '\'' +
                ", logo_pic='" + logo_pic + '\'' +
                ", name='" + name + '\'' +
                ", iv_pic='" + iv_pic + '\'' +
                ", price=" + price +
                ", number=" + number +
                ", model='" + model + '\'' +
                ", url='" + url + '\'' +
                ", isNew=" + isNew +
                ", is_lease=" + is_lease +
                ", is_parts=" + is_parts +
                ", isSelecter=" + isSelecter +
                ", dingdanUrl='" + dingdanUrl + '\'' +
                ", address='" + address + '\'' +
                ", shopName='" + shopName + '\'' +
                ", is_evaluate=" + is_evaluate +
                ", order_goods_id='" + order_goods_id + '\'' +
                ", goods_id='" + goods_id + '\'' +
                ", yunfei=" + yunfei +
                '}';
    }
}
