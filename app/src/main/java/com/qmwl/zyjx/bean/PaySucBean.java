package com.qmwl.zyjx.bean;

public class PaySucBean {




    private String pic_cover;
    private String goods_name;
    private String price;
    private String place_city;
    private String url;


    public String getPic_cover() {
        return pic_cover;
    }

    public void setPic_cover(String pic_cover) {
        this.pic_cover = pic_cover;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPlace_city() {
        return place_city;
    }

    public void setPlace_city(String place_city) {
        this.place_city = place_city;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public String toString() {
        return "PaySucBean{" +
                "pic_cover='" + pic_cover + '\'' +
                ", goods_name='" + goods_name + '\'' +
                ", price='" + price + '\'' +
                ", place_city='" + place_city + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
