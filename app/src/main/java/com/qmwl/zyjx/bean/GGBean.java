package com.qmwl.zyjx.bean;

/**
 * Created by Administrator on 2017/8/22.
 */

public class GGBean {
    //     "buyer_id": 342,
//        "express_price": "50",
//             "num": 1,
//             "goods_model": "LD540",
//             "price": "15000.00",
//             "cart_id": 43,
//             "shop_id": 0,
//             "pic_cover_mid": "http://shopnc.rabxdl.cn/upload/goods/1503039563832_P_14746519634752.jpg",
//             "goods_id": 421,
//             "goods_name": "LD540打桩机 旋挖钻机",
//             "url": "http://shopnc.rabxdl.cn/index.php/api/index/advUrl?shop_id=0",
//             "shop_name": "临时"
    private String buyer_id;
    private int num;
    private String goods_model;
    private double price;
    private String cart_id;
    private String shop_id;
    private String pic_cover_mid;
    private String goods_id;
    private String goods_name;
    private String url;
    private String shop_name;
    private String goods_url;
    private String shop_logo;
    private double express_price;//运费
    public double getExpress_price() {
        return express_price;
    }
    public void setExpress_price(double express_price) {
        this.express_price = express_price;
    }

    public String getShop_logo() {
        return shop_logo;
    }

    public void setShop_logo(String shop_logo) {
        this.shop_logo = shop_logo;
    }

    public String getGoods_url() {
        return goods_url;
    }

    public void setGoods_url(String goods_url) {
        this.goods_url = goods_url;
    }

    public String getBuyer_id() {
        return buyer_id;
    }

    public void setBuyer_id(String buyer_id) {
        this.buyer_id = buyer_id;
    }

    public String getGoods_model() {
        return goods_model;
    }

    public void setGoods_model(String goods_model) {
        this.goods_model = goods_model;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getPic_cover_mid() {
        return pic_cover_mid;
    }

    public void setPic_cover_mid(String pic_cover_mid) {
        this.pic_cover_mid = pic_cover_mid;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }
}
