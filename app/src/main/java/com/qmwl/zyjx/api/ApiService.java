package com.qmwl.zyjx.api;


import com.qmwl.zyjx.bean.CancelOrderBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

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

  /*  4.//发票申请
    URL："/index.php/api/order/invoice?orderId"
    参数：orderId 订单ID  taitou 发票抬头  itemCheck  1为企业 2为个人  isdianzi 1为电子 2为纸制  price 价格  order_no 订单号 shibiehao 识别号
    content 内容 email 邮箱 name 姓名 tel 电话 address 地址 des 备注
    */




    //发票申请
    @FormUrlEncoded
    @POST("/index.php/api/order/invoice")
    Observable<ApiResponse<Object>> insert_invoice(@Field("uid") String uid,
            @Field("orderId") String orderId, @Field("taitou") String taitou,
                                                   @Field("itemCheck") String itemCheck, @Field("isdianzi") String isdianzi,
                                                   @Field("price") String price, @Field("order_no") String order_no, @Field("shibiehao") String shibiehao,
                                                   @Field("content") String content, @Field("email") String email,@Field("name") String name,
                                                   @Field("tel") String tel, @Field("address") String address,
                                                   @Field("des") String des
                                                   );

}