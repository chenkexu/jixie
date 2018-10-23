package com.qmwl.zyjx.bean;

import java.io.Serializable;

/**
 * Created by ckx on 2018/10/21.
 */

public class ChinaPayOrder implements Serializable {

    private static final long serialVersionUID = -3673686188085742937L;


        /**
         * niu_index_response : {"MerId":"481611808090001","MerOrderNo":"6948261540191453","OrderAmt":20000000,"TranDate":"20181022","TranTime":"145733","TranType":"0001","BusiType":"0001","Version":"20140728","CurryNo":"CNY","AccessType":"0","MerPageUrl":"http://app.qmnet.com.cn/index.php/pay/unionpay_url","MerBgUrl":"http://app.qmnet.com.cn/index.php/pay/unionpay_url","MerResv":"MerResv","Signature":"TzB+RM/NJ1fzqwAAitActAYPjfMcRWUKpaEPhmFRGtvrGg37gDFBO1QtRAgwiGI/4IPcaCoTAS46Er5btVvpke0gKyc+CMQoX/L5Zf8pTzzlt6JrS1cyOreY+W619ij6J0COR33+X1dwdeGDHTeJltC9VnlHBHepyrWOcm6CXLBjP6vwi78NGT2NodyRvaEKspR6wKH0W2GWnF+L4O/FgBAMT47xJR9mhYrLnzRFcwGWAiCWp0BrGUeYdKFrt3tERmLclPpiK08MHZW3Q/kOsrXrY5o93hDOb9i7KdcUJWqi6SPEmvpTa+F8W9hHGIdzFxpYHoNJUduYvwx5RefxuA=="}
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
             * MerId : 481611808090001
             * MerOrderNo : 6948261540191453
             * OrderAmt : 20000000
             * TranDate : 20181022
             * TranTime : 145733
             * TranType : 0001
             * BusiType : 0001
             * Version : 20140728
             * CurryNo : CNY
             * AccessType : 0
             * MerPageUrl : http://app.qmnet.com.cn/index.php/pay/unionpay_url
             * MerBgUrl : http://app.qmnet.com.cn/index.php/pay/unionpay_url
             * MerResv : MerResv
             * Signature : TzB+RM/NJ1fzqwAAitActAYPjfMcRWUKpaEPhmFRGtvrGg37gDFBO1QtRAgwiGI/4IPcaCoTAS46Er5btVvpke0gKyc+CMQoX/L5Zf8pTzzlt6JrS1cyOreY+W619ij6J0COR33+X1dwdeGDHTeJltC9VnlHBHepyrWOcm6CXLBjP6vwi78NGT2NodyRvaEKspR6wKH0W2GWnF+L4O/FgBAMT47xJR9mhYrLnzRFcwGWAiCWp0BrGUeYdKFrt3tERmLclPpiK08MHZW3Q/kOsrXrY5o93hDOb9i7KdcUJWqi6SPEmvpTa+F8W9hHGIdzFxpYHoNJUduYvwx5RefxuA==
             */

            private String MerId;
            private String MerOrderNo;
            private int OrderAmt;
            private String TranDate;
            private String TranTime;
//            private String TranType;
            private String BusiType;
            private String Version;
            private String CurryNo;
            private String AccessType;
            private String MerPageUrl;
            private String MerBgUrl;
            private String MerResv;
            private String Signature;

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

            public int getOrderAmt() {
                return OrderAmt;
            }

            public void setOrderAmt(int OrderAmt) {
                this.OrderAmt = OrderAmt;
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

//            public String getTranType() {
//                return TranType;
//            }
//
//            public void setTranType(String TranType) {
//                this.TranType = TranType;
//            }

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

            public String getCurryNo() {
                return CurryNo;
            }

            public void setCurryNo(String CurryNo) {
                this.CurryNo = CurryNo;
            }

            public String getAccessType() {
                return AccessType;
            }

            public void setAccessType(String AccessType) {
                this.AccessType = AccessType;
            }

            public String getMerPageUrl() {
                return MerPageUrl;
            }

            public void setMerPageUrl(String MerPageUrl) {
                this.MerPageUrl = MerPageUrl;
            }

            public String getMerBgUrl() {
                return MerBgUrl;
            }

            public void setMerBgUrl(String MerBgUrl) {
                this.MerBgUrl = MerBgUrl;
            }

            public String getMerResv() {
                return MerResv;
            }

            public void setMerResv(String MerResv) {
                this.MerResv = MerResv;
            }

            public String getSignature() {
                return Signature;
            }

            public void setSignature(String Signature) {
                this.Signature = Signature;
            }
        }
    }

