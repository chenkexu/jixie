package com.qmwl.zyjx.bean;

import java.io.Serializable;

/**
 * Created by ckx on 2018/10/21.
 */

public class ChinaPayOrder implements Serializable {

    private static final long serialVersionUID = -3673686188085742937L;


    /**
     * code : 0
     * message : success
     * data : {"niu_index_response":{"MerId":"481611808090002","MerOrderNo":"9567441540053190","RefundAmt":1,"TranDate":"20181021","TranTime":"003310","OriOrderNo":"1540041688624","OriTranDate":"19700101","TranType":"0401","BusiType":"0001","Version":"20140728","MerBgUrl":"http://app.qmnet.com.cn/index.php/pay/unionpay_tuiurl"}}
     */

        /**
         * niu_index_response : {"MerId":"481611808090002","MerOrderNo":"9567441540053190","RefundAmt":1,"TranDate":"20181021","TranTime":"003310","OriOrderNo":"1540041688624","OriTranDate":"19700101","TranType":"0401","BusiType":"0001","Version":"20140728","MerBgUrl":"http://app.qmnet.com.cn/index.php/pay/unionpay_tuiurl"}
         */

        private NiuIndexResponseBean niu_index_response;

        public NiuIndexResponseBean getNiu_index_response() {
            return niu_index_response;
        }

        public void setNiu_index_response(NiuIndexResponseBean niu_index_response) {
            this.niu_index_response = niu_index_response;
        }

        public static class NiuIndexResponseBean {
            /**
             * MerId : 481611808090002
             * MerOrderNo : 9567441540053190
             * RefundAmt : 1
             * TranDate : 20181021
             * TranTime : 003310
             * OriOrderNo : 1540041688624
             * OriTranDate : 19700101
             * TranType : 0401
             * BusiType : 0001
             * Version : 20140728
             * MerBgUrl : http://app.qmnet.com.cn/index.php/pay/unionpay_tuiurl
             */

            private String MerId;
            private String MerOrderNo;
            private int RefundAmt;
            private String TranDate;
            private String TranTime;
            private String OriOrderNo;
            private String OriTranDate;
            private String TranType;
            private String BusiType;
            private String Version;
            private String MerBgUrl;

            public String getMerId() {
                return MerId;
            }

            public void setMerId(String MerId) {
                this.MerId = MerId;
            }

            public String getMerOrderNo() {
                return MerOrderNo;
            }

            public void setMerOrderNo(String MerOrderNo) {
                this.MerOrderNo = MerOrderNo;
            }

            public int getRefundAmt() {
                return RefundAmt;
            }

            public void setRefundAmt(int RefundAmt) {
                this.RefundAmt = RefundAmt;
            }

            public String getTranDate() {
                return TranDate;
            }

            public void setTranDate(String TranDate) {
                this.TranDate = TranDate;
            }

            public String getTranTime() {
                return TranTime;
            }

            public void setTranTime(String TranTime) {
                this.TranTime = TranTime;
            }

            public String getOriOrderNo() {
                return OriOrderNo;
            }

            public void setOriOrderNo(String OriOrderNo) {
                this.OriOrderNo = OriOrderNo;
            }

            public String getOriTranDate() {
                return OriTranDate;
            }

            public void setOriTranDate(String OriTranDate) {
                this.OriTranDate = OriTranDate;
            }

            public String getTranType() {
                return TranType;
            }

            public void setTranType(String TranType) {
                this.TranType = TranType;
            }

            public String getBusiType() {
                return BusiType;
            }

            public void setBusiType(String BusiType) {
                this.BusiType = BusiType;
            }

            public String getVersion() {
                return Version;
            }

            public void setVersion(String Version) {
                this.Version = Version;
            }

            public String getMerBgUrl() {
                return MerBgUrl;
            }

            public void setMerBgUrl(String MerBgUrl) {
                this.MerBgUrl = MerBgUrl;
            }
        }
    }

