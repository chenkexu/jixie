package com.qmwl.zyjx.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/9/1.
 */

public class WuLiuBean {
    private String number;
    private String image;
    private String issign;
    private String express_company;
    private List<WuLiuItemBean> list = null;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getIssign() {
        if ("1".equals(issign)) {
            return "已签收";
        } else {
            return "未签收";
        }
    }

    public void setIssign(String issign) {
        this.issign = issign;
    }

    public String getExpress_company() {
        return express_company;
    }

    public void setExpress_company(String express_company) {
        this.express_company = express_company;
    }

    public List<WuLiuItemBean> getList() {
        return list;
    }

    public void setList(List<WuLiuItemBean> list) {
        this.list = list;
    }
}
