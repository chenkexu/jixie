package com.qmwl.zyjx.bean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/30/030.
 */

public class PeiJianShaiXuanBean {
    public String name;
    public String id;
    public boolean isSelecter;
    public List<PeiJianShaiXuanBean> childList;

    public PeiJianShaiXuanBean() {
    }

    public PeiJianShaiXuanBean(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public PeiJianShaiXuanBean(String name, String id, List<PeiJianShaiXuanBean> childList) {
        this.name = name;
        this.id = id;
        this.childList = childList;
    }

}
