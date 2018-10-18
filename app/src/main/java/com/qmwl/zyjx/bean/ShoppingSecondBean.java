package com.qmwl.zyjx.bean;

/**
 * Created by Administrator on 2017/8/7.
 * 商品二级bean
 */

public class ShoppingSecondBean {
    private String category_id;//商品ID
    private String goods_name;//商品名称
    private String category_pic;//商品分类图片
    private String price;//商品价格
    private String pid;//父类商品id

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getCategory_pic() {
        return category_pic;
    }

    public void setCategory_pic(String category_pic) {
        this.category_pic = category_pic;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
