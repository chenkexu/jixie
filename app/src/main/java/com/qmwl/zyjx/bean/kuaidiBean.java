package com.qmwl.zyjx.bean;

import java.io.Serializable;

/**
 * Created by ckx on 2018/10/21.
 */

public class kuaidiBean implements Serializable {

    private static final long serialVersionUID = 1171312979710356530L;


    /**
     * code : 0
     * message : success
     * data : {"niu_index_response":{"SF":"顺丰","STO":"申通","YD":"韵达快运","HHTT":"天天快递","YTO":"圆通速递","ZTO":"中通速递","EMS":"ems快递","QFKD":"全峰快递","ZJS":"宅急送","DBL":"德邦物流","GTO":"国通快递","FAST":"快捷速递","UAPEX":"全一快递","SURE":"速尔物流","UC":"优速物流","ZTKY":"中铁快运","HTKY":"百世快递","YZBK":"邮政国内标快","JD":"京东","XFEX":"信丰","KYSY":"跨越速运","ANE":"安能小包","YZPY":"邮政快递包裹"}}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * niu_index_response : {"SF":"顺丰","STO":"申通","YD":"韵达快运","HHTT":"天天快递","YTO":"圆通速递","ZTO":"中通速递","EMS":"ems快递","QFKD":"全峰快递","ZJS":"宅急送","DBL":"德邦物流","GTO":"国通快递","FAST":"快捷速递","UAPEX":"全一快递","SURE":"速尔物流","UC":"优速物流","ZTKY":"中铁快运","HTKY":"百世快递","YZBK":"邮政国内标快","JD":"京东","XFEX":"信丰","KYSY":"跨越速运","ANE":"安能小包","YZPY":"邮政快递包裹"}
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
             * SF : 顺丰
             * STO : 申通
             * YD : 韵达快运
             * HHTT : 天天快递
             * YTO : 圆通速递
             * ZTO : 中通速递
             * EMS : ems快递
             * QFKD : 全峰快递
             * ZJS : 宅急送
             * DBL : 德邦物流
             * GTO : 国通快递
             * FAST : 快捷速递
             * UAPEX : 全一快递
             * SURE : 速尔物流
             * UC : 优速物流
             * ZTKY : 中铁快运
             * HTKY : 百世快递
             * YZBK : 邮政国内标快
             * JD : 京东
             * XFEX : 信丰
             * KYSY : 跨越速运
             * ANE : 安能小包
             * YZPY : 邮政快递包裹
             */

            private String SF;
            private String STO;
            private String YD;
            private String HHTT;
            private String YTO;
            private String ZTO;
            private String EMS;
            private String QFKD;
            private String ZJS;
            private String DBL;
            private String GTO;
            private String FAST;
            private String UAPEX;
            private String SURE;
            private String UC;
            private String ZTKY;
            private String HTKY;
            private String YZBK;
            private String JD;
            private String XFEX;
            private String KYSY;
            private String ANE;
            private String YZPY;

            public String getSF() {
                return SF;
            }

            public void setSF(String SF) {
                this.SF = SF;
            }

            public String getSTO() {
                return STO;
            }

            public void setSTO(String STO) {
                this.STO = STO;
            }

            public String getYD() {
                return YD;
            }

            public void setYD(String YD) {
                this.YD = YD;
            }

            public String getHHTT() {
                return HHTT;
            }

            public void setHHTT(String HHTT) {
                this.HHTT = HHTT;
            }

            public String getYTO() {
                return YTO;
            }

            public void setYTO(String YTO) {
                this.YTO = YTO;
            }

            public String getZTO() {
                return ZTO;
            }

            public void setZTO(String ZTO) {
                this.ZTO = ZTO;
            }

            public String getEMS() {
                return EMS;
            }

            public void setEMS(String EMS) {
                this.EMS = EMS;
            }

            public String getQFKD() {
                return QFKD;
            }

            public void setQFKD(String QFKD) {
                this.QFKD = QFKD;
            }

            public String getZJS() {
                return ZJS;
            }

            public void setZJS(String ZJS) {
                this.ZJS = ZJS;
            }

            public String getDBL() {
                return DBL;
            }

            public void setDBL(String DBL) {
                this.DBL = DBL;
            }

            public String getGTO() {
                return GTO;
            }

            public void setGTO(String GTO) {
                this.GTO = GTO;
            }

            public String getFAST() {
                return FAST;
            }

            public void setFAST(String FAST) {
                this.FAST = FAST;
            }

            public String getUAPEX() {
                return UAPEX;
            }

            public void setUAPEX(String UAPEX) {
                this.UAPEX = UAPEX;
            }

            public String getSURE() {
                return SURE;
            }

            public void setSURE(String SURE) {
                this.SURE = SURE;
            }

            public String getUC() {
                return UC;
            }

            public void setUC(String UC) {
                this.UC = UC;
            }

            public String getZTKY() {
                return ZTKY;
            }

            public void setZTKY(String ZTKY) {
                this.ZTKY = ZTKY;
            }

            public String getHTKY() {
                return HTKY;
            }

            public void setHTKY(String HTKY) {
                this.HTKY = HTKY;
            }

            public String getYZBK() {
                return YZBK;
            }

            public void setYZBK(String YZBK) {
                this.YZBK = YZBK;
            }

            public String getJD() {
                return JD;
            }

            public void setJD(String JD) {
                this.JD = JD;
            }

            public String getXFEX() {
                return XFEX;
            }

            public void setXFEX(String XFEX) {
                this.XFEX = XFEX;
            }

            public String getKYSY() {
                return KYSY;
            }

            public void setKYSY(String KYSY) {
                this.KYSY = KYSY;
            }

            public String getANE() {
                return ANE;
            }

            public void setANE(String ANE) {
                this.ANE = ANE;
            }

            public String getYZPY() {
                return YZPY;
            }

            public void setYZPY(String YZPY) {
                this.YZPY = YZPY;
            }
        }
    }
}
