package com.qmwl.zyjx.bean;

/**
 * Created by Administrator on 2017/7/24.
 */

public class BlackBean {

    public BlackBean() {

    }

    //筛选品牌的图片地址
    private String brand_pic;
    //是否是推荐品牌1是 0否
    private int brand_recommend;
    private boolean isSelecter;

    private String sortLetters;  //显示数据拼音的首字母

    public BlackBean(String name) {
        this.name = name;
    }

    private String name;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public String getBrand_pic() {
        return brand_pic;
    }

    public void setBrand_pic(String brand_pic) {
        this.brand_pic = brand_pic;
    }

    public int getBrand_recommend() {
        return brand_recommend;
    }

    public void setBrand_recommend(int brand_recommend) {
        this.brand_recommend = brand_recommend;
    }

    public boolean isSelecter() {
        return isSelecter;
    }

    public void setSelecter(boolean selecter) {
        isSelecter = selecter;
    }
}
