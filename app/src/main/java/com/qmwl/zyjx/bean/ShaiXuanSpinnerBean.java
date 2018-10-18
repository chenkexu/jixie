package com.qmwl.zyjx.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/1.
 */

public class ShaiXuanSpinnerBean {
    //标题
    private String attr_value_name;
    private List<ShaiXuanItemBean> value_items;

    public String getAttr_value_name() {
        return attr_value_name;
    }

    public void setAttr_value_name(String attr_value_name) {
        this.attr_value_name = attr_value_name;
    }

    public List<ShaiXuanItemBean> getValue_items() {
        return value_items;
    }

    public void setValue_items(List<ShaiXuanItemBean> value_items) {
        this.value_items = value_items;
    }
}
