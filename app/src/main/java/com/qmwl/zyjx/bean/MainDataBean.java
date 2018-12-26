package com.qmwl.zyjx.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/19.
 *
 */

public class MainDataBean {
    private String category_id;
    private String pid;
    private String category_name;
    private String category_pic;
    private List<MainDataBean> childs;
    public boolean isFirst = false;
    private String parentName;

    private String h5;

    //用来区别服务,运输维修，招聘培训，金融,我要开店，保险的标识符
    private String type = "-1";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public String getCategory_pic() {
        return category_pic;
    }

    public void setCategory_pic(String category_pic) {
        this.category_pic = category_pic;
    }

    public List<MainDataBean> getChilds() {
        return childs;
    }

    public void setChilds(List<MainDataBean> childs) {
        this.childs = childs;
    }

    public String getH5() {
        return h5;
    }

    public void setH5(String h5) {
        this.h5 = h5;
    }
}
