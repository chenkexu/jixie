package com.qmwl.zyjx.bean;

import java.util.List;

public class PaySucOutBean {




     List<PaySucBean>  niu_index_response;


    public List<PaySucBean> getNiu_index_response() {
        return niu_index_response;
    }

    public void setNiu_index_response(List<PaySucBean> niu_index_response) {
        this.niu_index_response = niu_index_response;
    }


    @Override
    public String toString() {
        return "PaySucOutBean{" +
                "niu_index_response=" + niu_index_response +
                '}';
    }
}
