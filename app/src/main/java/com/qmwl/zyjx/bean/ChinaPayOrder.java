package com.qmwl.zyjx.bean;

import java.io.Serializable;

/**
 * Created by ckx on 2018/10/21.
 */

public class ChinaPayOrder implements Serializable {

    private static final long serialVersionUID = -3673686188085742937L;


        /**
         * niu_index_response : {"MerId":"481611808090002","MerOrderNo":"4796381540519598","OrderAmt":1,"TranDate":"20181026","TranTime":"100638","TranType":"0005","BusiType":"0001","Version":"20140728","CurryNo":"CNY","AccessType":"0","MerPageUrl":"http://app.qmnet.com.cn/index.php/pay/unionpay_url","MerBgUrl":"http://app.qmnet.com.cn/index.php/pay/unionpay_url","MerResv":"MerResv","RiskData":"mO0i5n+cv5YwZ/InyCpbjSgXjYfmuxoCzvJOn/AMxJZeXAiFTUB8v3cH+l7SWHNrslJiNRXVWODwMFtSkO7krNIJgeTIR09/xZ34W7SMrudw/Ix3vVPs3L2qE+RZEa/8M/oTZ334e8LDx/jTS9rPZbKXYgNFKye8VChd8wXoll0=","Signature":"X8TTSd3NJ1pzQjuzZ6nMt69nm+/E49OmByOvrFRLUrPObuK/m9Nz74/yG1QWabURf4ECYdu7Ragh7xxEHKHJPaIb6qOvF7kjm/bM9xRMJGO/6PAZA2+MBFhciSzxmVKFRr7tiiZ7AgsZH8yLTKiaGZE1Bw7YzdnviQ+1JU5CDVGyv4Ub2fA96uw/wxktOM0J3P+3xAkbMB72TeN8gusta6K7Fed+FqtIDz3Y5RiK1jJNajSLIf8Q49iK/2QeSzlgScSJfiKdR+C/AGaxzKCNjupyeSKhMDG+H6Zzq/29vsJ/7ir981z+y/6ftDyaVRV+ZmIFeDtfkoPegZbrYQclQA=="}
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
             * MerOrderNo : 4796381540519598
             * OrderAmt : 1
             * TranDate : 20181026
             * TranTime : 100638
             * TranType : 0005
             * BusiType : 0001
             * Version : 20140728
             * CurryNo : CNY
             * AccessType : 0
             * MerPageUrl : http://app.qmnet.com.cn/index.php/pay/unionpay_url
             * MerBgUrl : http://app.qmnet.com.cn/index.php/pay/unionpay_url
             * MerResv : MerResv
             * RiskData : mO0i5n+cv5YwZ/InyCpbjSgXjYfmuxoCzvJOn/AMxJZeXAiFTUB8v3cH+l7SWHNrslJiNRXVWODwMFtSkO7krNIJgeTIR09/xZ34W7SMrudw/Ix3vVPs3L2qE+RZEa/8M/oTZ334e8LDx/jTS9rPZbKXYgNFKye8VChd8wXoll0=
             * Signature : X8TTSd3NJ1pzQjuzZ6nMt69nm+/E49OmByOvrFRLUrPObuK/m9Nz74/yG1QWabURf4ECYdu7Ragh7xxEHKHJPaIb6qOvF7kjm/bM9xRMJGO/6PAZA2+MBFhciSzxmVKFRr7tiiZ7AgsZH8yLTKiaGZE1Bw7YzdnviQ+1JU5CDVGyv4Ub2fA96uw/wxktOM0J3P+3xAkbMB72TeN8gusta6K7Fed+FqtIDz3Y5RiK1jJNajSLIf8Q49iK/2QeSzlgScSJfiKdR+C/AGaxzKCNjupyeSKhMDG+H6Zzq/29vsJ/7ir981z+y/6ftDyaVRV+ZmIFeDtfkoPegZbrYQclQA==
             */

            private String MerId;
            private String MerOrderNo;
            private String OrderAmt;
            private String TranDate;
            private String TranTime;
            private String TranType;
            private String BusiType;
            private String Version;
            private String CurryNo;
            private String AccessType;
            private String MerPageUrl;
            private String MerBgUrl;
            private String MerResv;
            private String RiskData;
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

            public String getOrderAmt() {
                return OrderAmt;
            }

            public void setOrderAmt(String OrderAmt) {
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

            public String getRiskData() {
                return RiskData;
            }

            public void setRiskData(String RiskData) {
                this.RiskData = RiskData;
            }

            public String getSignature() {
                return Signature;
            }

            public void setSignature(String Signature) {
                this.Signature = Signature;
            }
        }
    }


