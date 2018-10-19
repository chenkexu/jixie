package com.qmwl.zyjx.api;


import com.qmwl.zyjx.bean.CancelOrderBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

import static com.qmwl.zyjx.utils.Contact.httpaddress;

/**
 * Created by huang on 2018/4/16.
 */

public interface ApiService {

    //获取取消订单的理由
    public static String get_cancel_order_reson = httpaddress.concat("/index.php/api/order/reson");

    //获取退款订单的理由
    public static String get_tuikuan_order_reson = httpaddress.concat("/index.php/api/order/tuiReson");

    //获取退货订单的理由
    public static String get_tuihuo_order_reson = httpaddress.concat("/index.php/api/order/tuiHuoReson");

    //获取取消订单的理由
    @GET("/index.php/api/order/reson")
    Observable<ApiResponse<CancelOrderBean>> get_cancel_order_reson();

    //获取退款订单的理由
    @GET("/index.php/api/order/tuiReson")
    Observable<ApiResponse<CancelOrderBean>> get_tuikuan_order_reson();

    //获取退货订单的理由
    @GET("/index.php/api/order/tuiHuoReson")
    Observable<ApiResponse<CancelOrderBean>> get_tuihuo_order_reson();
}
