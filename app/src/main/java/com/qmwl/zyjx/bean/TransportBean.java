package com.qmwl.zyjx.bean;

/**
 * Created by Administrator on 2017/8/28.
 * 运输的javabean
 */

public class TransportBean {
    /**
     * 剩余时间
     */
    private long time;
    private String t_id;
    private String delivery_time;
    private String t_address;
    private String format;
    private String t_weight;
    private String s_mdd;
    private String addtime;
    private String tName;
    private long remaining;

    public String gettName() {
        return tName;
    }

    public void settName(String tName) {
        this.tName = tName;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getT_id() {
        return t_id;
    }

    public void setT_id(String t_id) {
        this.t_id = t_id;
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

    public long getRemaining() {
        return remaining;
    }

    public void setRemaining(long remaining) {
        this.remaining = remaining;
    }
}
