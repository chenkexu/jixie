package com.qmwl.zyjx.bean;

import java.util.List;

/**
 * Created by ckx on 2018/10/19.
 */

public class ReturnPayOrderBean {

    /**
     * code : 0
     * message : success
     * data : {"niu_index_response":["不取消了","不想买了","信息填写错误"]}
     */


        private List<String> niu_index_response;

        public List<String> getNiu_index_response() {
            return niu_index_response;
        }

        public void setNiu_index_response(List<String> niu_index_response) {
            this.niu_index_response = niu_index_response;
        }
    }
