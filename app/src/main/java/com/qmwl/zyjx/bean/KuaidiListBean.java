package com.qmwl.zyjx.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ckx on 2018/10/21.
 */

public class KuaidiListBean implements Serializable {


    private static final long serialVersionUID = -8559384574501158080L;
    /**
     * code : 0
     * message : success
     * data : {"niu_index_response":[["SF","STO","YD","HHTT","YTO","ZTO","EMS","QFKD","ZJS","DBL","GTO","FAST","UAPEX","SURE","UC","ZTKY","HTKY","YZBK","JD","XFEX","KYSY","ANE","YZPY"],["顺丰","申通","韵达快运","天天快递","圆通速递","中通速递","ems快递","全峰快递","宅急送","德邦物流","国通快递","快捷速递","全一快递","速尔物流","优速物流","中铁快运","百世快递","邮政国内标快","京东","信丰","跨越速运","安能小包","邮政快递包裹"]]}
     */


    private List<List<String>> niu_index_response;

    public List<List<String>> getNiu_index_response() {
        return niu_index_response;
    }

    public void setNiu_index_response(List<List<String>> niu_index_response) {
        this.niu_index_response = niu_index_response;
    }
}

