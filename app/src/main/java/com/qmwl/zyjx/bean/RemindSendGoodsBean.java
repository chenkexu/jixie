package com.qmwl.zyjx.bean;

import java.io.Serializable;

/**
 * Created by ckx on 2018/10/21.
 */

public class RemindSendGoodsBean implements Serializable {
    private static final long serialVersionUID = -9158640209555589774L;


    /**
     * code : -50
     * message : success
     * data : {"niu_index_response":"24小时内仅可以催发货一次"}
     */




        /**
         * niu_index_response : 24小时内仅可以催发货一次
         */

        private String niu_index_response;

        public String getNiu_index_response() {
            return niu_index_response;
        }

        public void setNiu_index_response(String niu_index_response) {
            this.niu_index_response = niu_index_response;
        }
    }

