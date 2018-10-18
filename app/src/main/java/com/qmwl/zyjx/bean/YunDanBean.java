package com.qmwl.zyjx.bean;

/**
 * Created by Administrator on 2017/9/22.
 */

public class YunDanBean {
    private String tid;
    private String t_name;
    private String delivery_time;
    private String t_address;
    private String format;
    private String t_weight;
    private String s_mdd;
    private String addtime;
    private int remaining;
    private String id;
    private int status;
    private int num;
    private String price;
    private String cid;
    private int payCode;//0是未付，1为已付

    public int getPayCode() {
        return payCode;
    }

    public void setPayCode(int payCode) {
        this.payCode = payCode;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public String getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(String delivery_time) {
        this.delivery_time = delivery_time;
    }

    public String getT_address() {
        return t_address;
    }

    public void setT_address(String t_address) {
        this.t_address = t_address;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getT_weight() {
        return t_weight;
    }

    public void setT_weight(String t_weight) {
        this.t_weight = t_weight;
    }

    public String getS_mdd() {
        return s_mdd;
    }

    public void setS_mdd(String s_mdd) {
        this.s_mdd = s_mdd;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public int getRemaining() {
        return remaining;
    }

    public void setRemaining(int remaining) {
        this.remaining = remaining;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
