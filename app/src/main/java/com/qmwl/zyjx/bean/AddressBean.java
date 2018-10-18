package com.qmwl.zyjx.bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/8/1.
 * 收货地址
 */

public class AddressBean implements Serializable {
    //    String id = obj.getString("id");
//    String mobile = obj.getString("mobile");
//    int isDefault = obj.getInt("is_default");
//    String address = obj.getString("address");
//    String consigner = obj.getString("consigner");
    private String id;
    private String name;
    private String mobile;
    private String address;
    private boolean isDefault = false;
    private String sheng;//id
    private String shi;//id

    private String sheng_name;//name
    private String shi_name;//name

    public String getSheng_name() {
        return sheng_name;
    }

    public void setSheng_name(String sheng_name) {
        this.sheng_name = sheng_name;
    }

    public String getShi_name() {
        return shi_name;
    }

    public void setShi_name(String shi_name) {
        this.shi_name = shi_name;
    }

    public String getSheng() {
        return sheng;
    }

    public void setSheng(String sheng) {
        this.sheng = sheng;
    }

    public String getShi() {
        return shi;
    }

    public void setShi(String shi) {
        this.shi = shi;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(int aDefault) {
        if (aDefault == 1) {
            isDefault = true;
        } else {
            isDefault = false;
        }
    }

    public int getDefaultCode() {
        if (isDefault) {
            return 1;
        } else {
            return 0;
        }

    }

    public void setDefault(boolean aDefault) {
        this.isDefault = aDefault;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
