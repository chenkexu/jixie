package com.qmwl.zyjx.bean;

/**
 * Created by Administrator on 2017/8/18.
 * //地图页面的数据
 */

public class MapBean {
//           "Lat": "40.104983",
//           "Long": "116.296131",
//           "add": "昌平沙河",
//           "logo": "http://shopnc.rabxdl.cn/upload/common/1502881937.jpg",
//           "title": "北京睿诚捷利机械设备有限公司 ",
//           "phone": "void(0)"

    private double lat_x;
    private double lay_y;
    private String address;
    private String logo;
    private String title;
    private String phone;

    public double getLat_x() {
        return lat_x;
    }

    public void setLat_x(double lat_x) {
        this.lat_x = lat_x;
    }

    public double getLay_y() {
        return lay_y;
    }

    public void setLay_y(double lay_y) {
        this.lay_y = lay_y;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
