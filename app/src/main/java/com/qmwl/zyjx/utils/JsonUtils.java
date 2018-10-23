package com.qmwl.zyjx.utils;

import android.util.Log;

import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.AddressBean;
import com.qmwl.zyjx.bean.BaoJiaBean;
import com.qmwl.zyjx.bean.BlackBean;
import com.qmwl.zyjx.bean.CheLiangBean;
import com.qmwl.zyjx.bean.CheXingBean;
import com.qmwl.zyjx.bean.DingDanBean;
import com.qmwl.zyjx.bean.FabuBean;
import com.qmwl.zyjx.bean.Flowbean;
import com.qmwl.zyjx.bean.GouWuCheBean;
import com.qmwl.zyjx.bean.HotBean;
import com.qmwl.zyjx.bean.MapBean;
import com.qmwl.zyjx.bean.PingJiabean;
import com.qmwl.zyjx.bean.ShaiXuanItemBean;
import com.qmwl.zyjx.bean.ShaiXuanSpinnerBean;
import com.qmwl.zyjx.bean.ShangJiaBean;
import com.qmwl.zyjx.bean.ShoppingBean;
import com.qmwl.zyjx.bean.TagBean;
import com.qmwl.zyjx.bean.TieZiBean;
import com.qmwl.zyjx.bean.TransportBean;
import com.qmwl.zyjx.bean.TuiKuanBean;
import com.qmwl.zyjx.bean.UserBean;
import com.qmwl.zyjx.bean.WuLiuBean;
import com.qmwl.zyjx.bean.WuLiuItemBean;
import com.qmwl.zyjx.bean.YunDanBean;
import com.qmwl.zyjx.bean.ZuLinListBean;
import com.qmwl.zyjx.bean.ZulingBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/11.
 */

public class JsonUtils {
    //成功的码
    private static final String SUCCESS_CODE = "0";

    /**
     * 判断jsonCode是否是正确的验证码
     *
     * @param jsonObject
     * @return
     */
    public static boolean isSuccess(JSONObject jsonObject) throws JSONException {
        String code = jsonObject.getString("code");
        if (SUCCESS_CODE.equals(code)) {
            return true;
        }
        return false;
    }

    /**
     * 判断jsonCode是否是正确的验证码
     * ok = 100
     *
     * @param jsonObject
     * @return
     */
    public static boolean is100Success(JSONObject jsonObject) throws JSONException {
        String code = jsonObject.getString("code");
        if ("100".equals(code)) {
            return true;
        }
        return false;
    }

    /**
     * 判断jsonCode是否是正确的验证码
     * ok = 60
     *
     * @param jsonObject
     * @return
     */
    public static boolean is60Success(JSONObject jsonObject) throws JSONException {
        String code = jsonObject.getString("code");
        if ("60".equals(code)) {
            return true;
        }
        return false;
    }

    /**
     * 解析array类型的
     *
     * @param jsonObject
     * @return
     * @throws JSONException
     */
    public static JSONArray parseJsonArray(JSONObject jsonObject) throws JSONException {
        if (isSuccess(jsonObject)) {
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray niu_index_response = data.getJSONArray("niu_index_response");
            return niu_index_response;
        }
        return null;
    }

    /**
     * 解析帖子列表
     */
    public static List<FabuBean> parseTieZiJson(JSONObject jsonObject) throws JSONException {
        List<FabuBean> list = null;
        if (isSuccess(jsonObject)) {
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray data1 = data.getJSONArray("niu_index_response");
            list = new ArrayList<>();
            for (int i = 0; i < data1.length(); i++) {
                JSONObject obj = data1.getJSONObject(i);
                String post_id = obj.getString("post_id");
                String title = obj.getString("title");
                String content = obj.getString("content");
//                String is_system = obj.getString("is_system");
                String create_time = obj.getString("create_time");
                String url = obj.getString("url");
                FabuBean bean = new FabuBean();
                bean.setPost_id(post_id);
                bean.setTitle(title);
                bean.setContent(content);
                bean.setCreate_time(create_time);
                bean.setUrl(url);
                list.add(bean);
            }

        }
        return list;
    }

    /**
     * 解析搜索帖子列表
     */
    public static List<FabuBean> parseSearchTieZiJson(JSONObject jsonObject) throws JSONException {
        List<FabuBean> list = null;
        if (isSuccess(jsonObject)) {
            JSONObject data = jsonObject.getJSONObject("data");
            JSONObject data2 = data.getJSONObject("niu_index_response");
            JSONArray data1 = data2.getJSONArray("data");
            list = new ArrayList<>();
            for (int i = 0; i < data1.length(); i++) {
                JSONObject obj = data1.getJSONObject(i);
                String post_id = obj.getString("post_id");
                String title = obj.getString("title");
                String content = obj.getString("content");
//                String is_system = obj.getString("is_system");
                String create_time = obj.getString("create_time");
                FabuBean bean = new FabuBean();
                if (obj.has("url")) {
                    String url = obj.getString("url");
                    bean.setUrl(url);
                }
                bean.setPost_id(post_id);
                bean.setTitle(title);
                bean.setContent(content);
                bean.setCreate_time(create_time);
                list.add(bean);
            }

        }
        return list;
    }


    /**
     * 解析商品列表
     */
    public static List<ShoppingBean> parseShoppingJson(JSONObject jsonObject) throws JSONException {
        List<ShoppingBean> list = null;
        if (isSuccess(jsonObject)) {
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray data1 = data.getJSONArray("niu_index_response");
            list = new ArrayList<>();
            for (int i = 0; i < data1.length(); i++) {
                JSONObject obj = data1.getJSONObject(i);
                ShoppingBean bean = new ShoppingBean();
                String goods_id = obj.getString("goods_id");
                String goods_name = obj.getString("goods_name");
                double price = obj.getDouble("price");
                if (obj.has("is_new")) {
                    int is_new = obj.getInt("is_new");//1是新 0是旧  { 0)二手机  1）新机  2）配件}
                    if (is_new == 2) {
                        bean.setIs_parts(1);
                    } else {
                        bean.setIsNew(is_new);
                    }
                }
                //已废，但是javabean没有更改,所以要结合上面的is_new 来配合
//                if (obj.has("is_parts")) {
//                    int is_parts = obj.getInt("is_parts");//1是配件 0不是
//                    bean.setIs_parts(is_parts);
//                }
                if (obj.has("is_lease")) {
                    int is_lease = obj.getInt("is_lease");//租赁
                    bean.setIs_lease(is_lease);
                }

                String pic_cover_mid = obj.getString("pic_cover_mid");
                String url = obj.getString("url");
                if (obj.has("address")) {
                    String address = obj.getString("address");
                    bean.setAddress(address);
                }
                bean.setShop_id(goods_id);
                bean.setName(goods_name);
                bean.setPrice(price);
                bean.setIv_pic(pic_cover_mid);


                bean.setUrl(url);
                list.add(bean);
            }
        }
        return list;
    }

    public static List<TagBean> parseSearchFlag(JSONObject response) {
        List<TagBean> list = null;
        try {
            if (isSuccess(response)) {
                list = new ArrayList<>();
                JSONObject data = response.getJSONObject("data");
                JSONArray niu_index_response = data.getJSONArray("niu_index_response");
                for (int i = 0; i < niu_index_response.length(); i++) {
                    JSONObject jsonObject = niu_index_response.getJSONObject(i);
                    TagBean bean = new TagBean();
                    String category_name = jsonObject.getString("category_name");
                    String category_id = jsonObject.getString("category_id");
                    bean.name = category_name;
                    bean.id = category_id;
                    list.add(bean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 解析商家中心信息
     *
     * @param response
     */
    public static ShangJiaBean parseShangJiaMessage(JSONObject response) {
        ShangJiaBean bean = null;
        try {
            if (isSuccess(response)) {
                JSONObject data = response.getJSONObject("data");
                JSONObject obj = data.getJSONObject("niu_index_response");
                bean = new ShangJiaBean();
                String shop_id = obj.getString("shop_id");
                String shop_company_name = obj.getString("shop_company_name");
                String shop_avatar = obj.getString("shop_avatar");
                String shop_description = obj.getString("shop_description");
                int shop_type = obj.getInt("shop_type");
                bean.setShop_id(shop_id);
                bean.setShop_company_name(shop_company_name);
                bean.setShop_avatar(shop_avatar);
                bean.setShop_description(shop_description);
                bean.setShop_type(shop_type);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 解析商家中心产品列表
     *
     * @param response
     */
    public static List<ShoppingBean> parseShangJiaShops(JSONObject response) {
        List<ShoppingBean> list = null;
        try {
            if (isSuccess(response)) {
                JSONObject data = response.getJSONObject("data");
                JSONArray array = data.getJSONArray("niu_index_response");
                list = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject obj = array.getJSONObject(i);
                    String goods_id = obj.getString("goods_id");
                    String goods_name = obj.getString("goods_name");
                    String pic_cover_mid = obj.getString("pic_cover_mid");
                    double price = obj.getDouble("price");
                    String url = obj.getString("url");
                    ShoppingBean bean = new ShoppingBean();
                    bean.setShop_id(goods_id);
                    bean.setName(goods_name);
                    bean.setIv_pic(pic_cover_mid);
                    bean.setPrice(price);
                    bean.setUrl(url);
                    list.add(bean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<MapBean> parseMapData(String json) {
        List<MapBean> list = null;
        MapBean bean = null;
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray map = jsonObject.getJSONArray("map");
            list = new ArrayList<>();
            for (int i = 0; i < map.length(); i++) {
                JSONObject obj = map.getJSONObject(i);
                double lat_x = obj.getDouble("Lat");
                double lat_y = obj.getDouble("Long");
                String address = obj.getString("add");
                String logo = obj.getString("logo");
                String title = obj.getString("title");
                bean = new MapBean();
                if (obj.has("phone")) {
                    String phone = obj.getString("phone");
                    bean.setPhone(phone);
                }
                bean.setLat_x(lat_x);
                bean.setLay_y(lat_y);
                bean.setAddress(address);
                bean.setLogo(logo);
                bean.setTitle(title);
                list.add(bean);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 解析收货地址列表
     */
    public static List<AddressBean> parseAddressJson(JSONObject jsonObject) throws JSONException {
        List<AddressBean> list = null;
        if (isSuccess(jsonObject)) {
            JSONObject data = jsonObject.getJSONObject("data");
            JSONArray data1 = data.getJSONArray("niu_index_response");
            list = new ArrayList<>();
            for (int i = 0; i < data1.length(); i++) {
                JSONObject obj = data1.getJSONObject(i);
                String id = obj.getString("id");
                AddressBean bean = new AddressBean();
                if (obj.has("phone")) {
                    String mobile = obj.getString("phone");
                    bean.setMobile(mobile);
                }
                if (obj.has("mobile")) {
                    String mobile = obj.getString("mobile");
                    bean.setMobile(mobile);
                }
                if (obj.has("province_name")) {
                    String province_name = obj.getString("province_name");
                    bean.setSheng_name(province_name);
                }
                if (obj.has("city_name")) {
                    String city_name = obj.getString("city_name");
                    bean.setShi_name(city_name);
                }


                int isDefault = obj.getInt("is_default");
                String address = obj.getString("address");
                String sheng = obj.getString("province");
                String city = obj.getString("city");
                String consigner = obj.getString("consigner");
                bean.setSheng(sheng);
                bean.setShi(city);
                bean.setId(id);
                bean.setDefault(isDefault);
                bean.setAddress(address);
                bean.setName(consigner);
                list.add(bean);
            }

        }
        return list;
    }

    /**
     * 解析订单列表
     *
     * @param response
     * @return
     */
    public static List<DingDanBean> parseDingDan(JSONObject response) {
        List<DingDanBean> list = null;
        try {
            JSONObject data = response.getJSONObject("data");
            JSONObject niu_index_response = data.getJSONObject("niu_index_response");
            JSONArray array = niu_index_response.getJSONArray("data");
            list = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                DingDanBean bean = new DingDanBean();
                String order_id = obj.getString("order_id");
                String order_no = obj.getString("order_no");
                bean.setOrder_no(order_no);
                bean.setOrder_id(order_id);
                String shop_name = obj.getString("shop_name");
                String pay_money = obj.getString("pay_money");
                String shop_logo = obj.getString("shop_logo");
                String msg = obj.getString("msg");
                int ma = obj.getInt("ma");
                int fapiao = obj.getInt("fapiao");
                String tui_xu = obj.getString("tui_xu");
                String shop_phone = obj.getString("shop_phone");
                int order_status = obj.getInt("order_status");
                int is_evaluate1 = obj.getInt("is_evaluate");
                double shipping_money = obj.getDouble("shipping_money");
                double order_money = obj.getDouble("order_money");

                bean.setOrder_money(order_money);
                bean.setIs_evaluate(is_evaluate1);
                bean.setShop_phone(shop_phone);
                bean.setShipping_money(shipping_money);
                bean.setShop_name(shop_name);
                bean.setPrice(pay_money);
                bean.setDingdan_statue_code(order_status);
                bean.setShop_logo(shop_logo);
                bean.setMa(ma);
                bean.setMsg(msg);
                bean.setFapiao(fapiao);
                bean.setTui_xu(tui_xu);
                JSONArray order_item_list = obj.getJSONArray("order_item_list");
                List<ShoppingBean> shoppingBeanList = new ArrayList<>();
                for (int j = 0; j < order_item_list.length(); j++) {
                    JSONObject object = order_item_list.getJSONObject(j);
                    ShoppingBean shoppingBean = new ShoppingBean();
                    String goods_name1 = object.getString("goods_name");
                    double price = object.getDouble("price");
                    String num = object.getString("num");
                    String goods_url = object.getString("goods_url");
                    int is_evaluate = object.getInt("is_evaluate");
                    String order_goods_id = object.getString("order_goods_id");
                    String goods_id = object.getString("goods_id");
                    String shop_id = object.getString("shop_id");

                    shoppingBean.setOrder_goods_id(order_goods_id);
                    shoppingBean.setGoods_id(goods_id);
                    shoppingBean.setShop_id(shop_id);
                    shoppingBean.setIs_evaluate(is_evaluate);
                    shoppingBean.setDingdanUrl(goods_url);
                    JSONObject pictureObj = object.getJSONObject("picture");
                    String pic_cover_mid = pictureObj.getString("pic_cover_mid");
                    shoppingBean.setName(goods_name1);
                    shoppingBean.setPrice(price);
                    shoppingBean.setIv_pic(pic_cover_mid);
                    shoppingBean.setNumber(Integer.parseInt(num));
                    shoppingBeanList.add(shoppingBean);
                }
                bean.setShopList(shoppingBeanList);
                list.add(bean);
            }
        } catch (JSONException e) {
            Log.d("huangrui","json解析异常原因:+"+e.toString());

            e.printStackTrace();
        }
        return list;
    }

    public static void parseUserData(JSONObject response) {
        try {
            if (JsonUtils.isSuccess(response)) {
                JSONObject data = response.getJSONObject("data");
                JSONObject niu_index_response = data.getJSONObject("niu_index_response");
                String user_headimg = niu_index_response.getString("user_headimg");
                String user_name = niu_index_response.getString("user_name");
                String nick_name = niu_index_response.getString("nick_name");
                String sex = niu_index_response.getString("sex");
                String birthday = niu_index_response.getString("birthday");
                UserBean userBean = MyApplication.getIntance().userBean;
                userBean.setBirthday(birthday);
                userBean.setHeadImg(user_headimg);
                userBean.setName(user_name);
                userBean.setNickName(nick_name);
                userBean.setSex(sex);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    //解析热门咨询和热门新闻
    public static HotBean parseZiXunJson(JSONObject response) {
        HotBean bean = null;
        try {
            if (JsonUtils.isSuccess(response)) {
                JSONObject data = response.getJSONObject("data");
                JSONArray niu_index_response = data.getJSONArray("niu_index_response");
                if (niu_index_response != null && niu_index_response.length() > 0) {
                    JSONObject jsonObject = niu_index_response.getJSONObject(0);
                    bean = new HotBean();
                    String title = jsonObject.getString("title");
                    String url = jsonObject.getString("url");
                    bean.setUrl(url);
                    bean.setTitle(title);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean;
    }

    public static List<String> parseZiXunListJson(JSONObject response) {
        List<String> list = null;
        try {
            if (JsonUtils.isSuccess(response)) {
                list = new ArrayList<>();
                JSONObject data = response.getJSONObject("data");
                JSONArray niu_index_response = data.getJSONArray("niu_index_response");
                for (int i = 0; i < niu_index_response.length(); i++) {
                    JSONObject jsonObject = niu_index_response.getJSONObject(i);
                    String title = jsonObject.getString("title");
                    list.add(title);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<BlackBean> parseSheng(JSONObject jsonObject) {
        List<BlackBean> list = null;
        try {
            if (isSuccess(jsonObject)) {
                JSONObject data = jsonObject.getJSONObject("data");
                JSONArray niu_index_response = data.getJSONArray("niu_index_response");
                list = new ArrayList<>();
                for (int i = 0; i < niu_index_response.length(); i++) {
                    JSONObject obj = niu_index_response.getJSONObject(i);
                    BlackBean bean = new BlackBean();
                    bean.setName(obj.getString("province_name"));
                    bean.setId(obj.getString("province_id"));
                    list.add(bean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    //解析市列表
    public static List<BlackBean> parseShi(JSONObject jsonObject) {
        List<BlackBean> list = null;
        try {
            if (isSuccess(jsonObject)) {
                JSONObject data = jsonObject.getJSONObject("data");
                JSONArray niu_index_response = data.getJSONArray("niu_index_response");
                list = new ArrayList<>();
                for (int i = 0; i < niu_index_response.length(); i++) {
                    JSONObject obj = niu_index_response.getJSONObject(i);
                    BlackBean bean = new BlackBean();
                    bean.setName(obj.getString("city_name"));
                    bean.setId(obj.getString("city_id"));
                    list.add(bean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    //解析县列表
    public static List<BlackBean> parseXian(JSONObject jsonObject) {
        List<BlackBean> list = null;
        try {
            if (isSuccess(jsonObject)) {
                JSONObject data = jsonObject.getJSONObject("data");
                JSONArray niu_index_response = data.getJSONArray("niu_index_response");
                list = new ArrayList<>();
                for (int i = 0; i < niu_index_response.length(); i++) {
                    JSONObject obj = niu_index_response.getJSONObject(i);
                    BlackBean bean = new BlackBean();
                    bean.setName(obj.getString("district_name"));
                    bean.setId(obj.getString("district_id"));
                    list.add(bean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
//         "pic_cover_mid": "http:\/\/zyjx.rabxdl.cn\/upload\/goods\/1502179365pro2.png",

    //                "picture": "http://zyjx.rabxdl.cn/upload/goods/1503302662卡特HEUI工具套装32.jpg",
//                        "shop_logo": "http://zyjx.rabxdl.cn/upload/common/1502881930.jpg",
//                        "shop_name": "临时",
    //解析收藏列表
    public static List<ShoppingBean> parseCollection(JSONObject response) {
        List<ShoppingBean> list = null;
        try {
            if (isSuccess(response)) {
                JSONObject data = response.getJSONObject("data");
                JSONArray niu_index_response = data.getJSONArray("niu_index_response");
                list = new ArrayList<>();
                for (int i = 0; i < niu_index_response.length(); i++) {
                    JSONObject obj = niu_index_response.getJSONObject(i);
                    ShoppingBean bean = new ShoppingBean();
                    String goods_id = obj.getString("goods_id");
                    String goods_name = obj.getString("goods_name");
                    double price = obj.getDouble("price");
                    String url = obj.getString("url");
                    if (obj.has("pic_cover_mid")) {
                        String pic_cover_mid = obj.getString("pic_cover_mid");
                        bean.setIv_pic(pic_cover_mid);
                    }
                    if (obj.has("picture")) {
                        String pic_cover_mid = obj.getString("picture");
                        bean.setIv_pic(pic_cover_mid);
                    }
                    if (obj.has("shop_logo")) {
                        String shop_logo = obj.getString("shop_logo");
                        bean.setLogo_pic(shop_logo);
                    }
                    if (obj.has("shop_name")) {
                        String shop_name = obj.getString("shop_name");
                        bean.setShopName(shop_name);
                    }
                    bean.setName(goods_name);
                    bean.setShop_id(goods_id);
                    bean.setPrice(price);
                    bean.setUrl(url);
                    list.add(bean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 解析筛选的品牌列表
     *
     * @param jsonObject
     * @return
     */
    public static List<BlackBean> parseSpinnerPinPai(JSONObject jsonObject) {
        List<BlackBean> list = null;
        try {
            if (isSuccess(jsonObject)) {
                JSONObject data = jsonObject.getJSONObject("data");
                JSONArray niu_index_response = data.getJSONArray("niu_index_response");
                list = new ArrayList<>();
                for (int i = 0; i < niu_index_response.length(); i++) {
                    JSONObject obj = niu_index_response.getJSONObject(i);
                    BlackBean bean = new BlackBean();
                    String brand_id = obj.getString("brand_id");
                    String brand_name = obj.getString("brand_name");
                    String brand_pic = obj.getString("brand_pic");
                    int brand_recommend = obj.getInt("brand_recommend");
                    bean.setId(brand_id);
                    bean.setName(brand_name);
                    bean.setBrand_recommend(brand_recommend);
                    bean.setBrand_pic(brand_pic);
                    list.add(bean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 解析筛选的筛选列表
     *
     * @param jsonObject
     * @return
     */
    public static List<ShaiXuanSpinnerBean> parseShaiXuanShaiXuan(JSONObject jsonObject) {
        List<ShaiXuanSpinnerBean> list = null;
        try {
            if (isSuccess(jsonObject)) {
                JSONObject data = jsonObject.getJSONObject("data");
                JSONArray niu_index_response = data.getJSONArray("niu_index_response");
                list = new ArrayList<>();
                for (int i = 0; i < niu_index_response.length(); i++) {
                    JSONObject obj = niu_index_response.getJSONObject(i);
                    ShaiXuanSpinnerBean bean = new ShaiXuanSpinnerBean();
                    String attr_value_name = obj.getString("attr_value_name");
                    bean.setAttr_value_name(attr_value_name);
                    JSONArray value_items = obj.getJSONArray("value_items");
                    List<ShaiXuanItemBean> childList = new ArrayList<>();
                    for (int j = 0; j < value_items.length(); j++) {
                        ShaiXuanItemBean childBean = new ShaiXuanItemBean();
                        String value = value_items.getString(j);
                        childBean.setName(value);
                        childList.add(childBean);
                    }
                    bean.setValue_items(childList);
                    list.add(bean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 解析黑名单列表
     *
     * @param jsonObject
     * @return
     */
    public static List<BlackBean> parseBlackList(JSONObject jsonObject) {
        List<BlackBean> list = null;
        try {
            if (isSuccess(jsonObject)) {
                JSONObject data = jsonObject.getJSONObject("data");
                JSONArray niu_index_response = data.getJSONArray("niu_index_response");
                list = new ArrayList<>();
                for (int i = 0; i < niu_index_response.length(); i++) {
                    BlackBean bean = new BlackBean();
                    bean.setName(niu_index_response.getString(i));
                    list.add(bean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 解析物流详情
     *
     * @param jsonObject
     * @return
     */
    public static WuLiuBean parseWuLiu(JSONObject jsonObject) {
        WuLiuBean bean = null;
        try {
            if (JsonUtils.isSuccess(jsonObject)) {
                JSONObject data = jsonObject.getJSONObject("data");
                JSONObject obj = data.getJSONObject("niu_index_response");
                bean = new WuLiuBean();
                String number = obj.getString("number");
                String image = obj.getString("image");
                String issign = obj.getString("issign");
                String express_company = obj.getString("express_company");
                bean.setNumber(number);
                bean.setIssign(issign);
                bean.setImage(image);
                bean.setExpress_company(express_company);
                JSONArray list = obj.getJSONArray("list");
                List<WuLiuItemBean> itemList = new ArrayList<>();
                for (int i = 0; i < list.length(); i++) {
                    JSONObject jsonObject1 = list.getJSONObject(i);
                    WuLiuItemBean b = new WuLiuItemBean();
                    String time = jsonObject1.getString("time");
                    String status = jsonObject1.getString("status");
                    b.setContent(status);
                    b.setTime(time);
                    itemList.add(b);
                }
                bean.setList(itemList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean;
    }


    /**
     * 解析我的评价列表
     *
     * @param jsonObject
     * @return
     */
    public static List<PingJiabean> parsePingJiaList(JSONObject jsonObject) {
        List<PingJiabean> list = null;
        try {
            if (isSuccess(jsonObject)) {
                JSONObject data = jsonObject.getJSONObject("data");
                JSONArray niu_index_response = data.getJSONArray("niu_index_response");
                list = new ArrayList<>();
                for (int i = 0; i < niu_index_response.length(); i++) {
                    JSONObject obj = niu_index_response.getJSONObject(i);
                    PingJiabean bean = new PingJiabean();
                    String goods_id = obj.getString("goods_id");
                    String goods_name = obj.getString("goods_name");
                    String member_name = obj.getString("member_name");
                    String content = obj.getString("content");
                    String explain_type = obj.getString("explain_type");
                    String goods_image = obj.getString("goods_image");
                    String goods_model = obj.getString("goods_model");
                    String goods_price = obj.getString("goods_price");
                    String user_headimg = obj.getString("user_headimg");

                    bean.setGoods_id(goods_id);
                    bean.setGoods_name(goods_name);
                    bean.setName(member_name);
                    bean.setContent(content);
                    bean.setExplain_type(explain_type);
                    bean.setGoods_image(goods_image);
                    bean.setGoods_model(goods_model);
                    bean.setGoods_price(goods_price);
                    bean.setUser_headimg(user_headimg);
                    list.add(bean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    //解析租赁信息
    public static ZulingBean parseZulinMessage(String json) {
        ZulingBean bean = null;
        try {
            JSONObject obj = new JSONObject(json);
            String event = obj.getString("event");
            String price = obj.getString("price");
            String number = obj.getString("number");
            String post_id = obj.getString("post_id");
            String logo = obj.getString("logo");
            String seller_name = obj.getString("seller_name");
            String pro_name = obj.getString("pro_name");
            String pro_model = obj.getString("pro_model");
            String methods = obj.getString("methods");
            String pro_pic = obj.getString("pro_pic");
            String seller_id = obj.getString("seller_id");
            String pledge = obj.getString("pledge");
            bean = new ZulingBean();
            bean.setEvent(event);
            bean.setPrice(price);
            bean.setNumber(number);
            bean.setPost_id(post_id);
            bean.setLogo(logo);
            bean.setSeller_name(seller_name);
            bean.setSeller_id(seller_id);
            bean.setPro_name(pro_name);
            bean.setPro_model(pro_model);
            bean.setMethods(methods);
            bean.setPro_pic(pro_pic);
            bean.setPledge(pledge);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 解析租赁订单列表
     *
     * @param response
     * @return
     */
    public static List<ZuLinListBean> parseZuLinList(JSONObject response) {
        List<ZuLinListBean> list = null;
        try {
            if (isSuccess(response)) {
                JSONObject data = response.getJSONObject("data");
                JSONArray niu_index_response = data.getJSONArray("niu_index_response");
                list = new ArrayList<>();
                for (int i = 0; i < niu_index_response.length(); i++) {
                    JSONObject obj = niu_index_response.getJSONObject(i);
                    String goods_id = obj.getString("goods_id");
                    String goods_name = obj.getString("goods_name");
                    String shop_id = obj.getString("shop_id");
                    String shop_name = obj.getString("shop_name");
                    String goods_model = obj.getString("goods_model");
                    String order_id = obj.getString("order_id");
                    String lease_day = obj.getString("lease_day");
                    String deposit = obj.getString("deposit");
                    String shop_phone = obj.getString("shop_phone");
                    String shop_logo = obj.getString("shop_logo");
                    String url = obj.getString("url");
                    String pic_cover_mid = obj.getString("pic_cover_mid");
                    int express_price = obj.getInt("express_price");
                    int total_amount = obj.getInt("total_amount");
                    String goods_num = obj.getString("goods_num");
                    int order_status = obj.getInt("order_status");
                    ZuLinListBean bean = new ZuLinListBean();
                    bean.setOrder_id(order_id);
                    bean.setGoods_id(goods_id);
                    bean.setGoods_name(goods_name);
                    bean.setShop_id(shop_id);
                    bean.setShop_name(shop_name);
                    bean.setGoods_model(goods_model);
                    bean.setGoods_num(goods_num);
                    bean.setLease_day(lease_day);
                    bean.setDeposit(deposit);
                    bean.setShop_phone(shop_phone);
                    bean.setShop_logo(shop_logo);
                    bean.setExpress_price(express_price);
                    bean.setTotal_amount(total_amount);
                    bean.setOrder_status(order_status);
                    bean.setUrl(url);
                    bean.setPic_cover_mid(pic_cover_mid);
                    list.add(bean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 解析退款列表
     *
     * @param response
     * @return
     */
    public static List<TuiKuanBean> parseTuiKuan(JSONObject response) {
        List<TuiKuanBean> list = null;
        try {
            if (isSuccess(response)) {
                JSONObject data = response.getJSONObject("data");
                JSONArray niu_index_response = data.getJSONArray("niu_index_response");
                list = new ArrayList<>();
                for (int i = 0; i < niu_index_response.length(); i++) {
                    JSONObject obj = niu_index_response.getJSONObject(i);
                    String order_goods_id = obj.getString("order_goods_id");
                    String goods_id = obj.getString("goods_id");
                    String goods_name = obj.getString("goods_name");
                    String shop_id = obj.getString("shop_id");
                    String shop_name = obj.getString("shop_name");
                    String goods_model = obj.getString("goods_model");
                    String goods_num = obj.getString("goods_num");
                    String shop_phone = obj.getString("shop_phone");
                    int refund_status = obj.getInt("refund_status");
                    String price = obj.getString("price");
                    String shop_logo = obj.getString("shop_logo");
                    String url = obj.getString("url");
                    String pic_cover_mid = obj.getString("pic_cover_mid");
                    TuiKuanBean bean = new TuiKuanBean();
                    bean.setShop_logo(shop_logo);
                    bean.setShop_name(shop_name);
                    bean.setGoods_name(goods_name);
                    bean.setGoods_model(goods_model);
                    bean.setPrice(price);
                    bean.setRefund_status(refund_status);
                    bean.setPic_cover_mid(pic_cover_mid);
                    list.add(bean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 解析全部评价
     *
     * @param response
     * @return
     */
    public static List<PingJiabean> parseAllPingJia(JSONObject response) {
        List<PingJiabean> list = null;
        try {
            JSONObject data = response.getJSONObject("data");
            JSONArray niu_index_response = data.getJSONArray("niu_index_response");
            list = new ArrayList<>();
            for (int i = 0; i < niu_index_response.length(); i++) {
                JSONObject obj = niu_index_response.getJSONObject(i);
                PingJiabean bean = new PingJiabean();
                String user_img = obj.getString("user_img");
                String user_name = obj.getString("user_name");
                String date = obj.getString("date");
                String goods_model = obj.getString("goods_model");
                String content = obj.getString("content");
                int is_anonymous = obj.getInt("is_anonymous");
                bean.setUser_headimg(user_img);
                bean.setName(user_name);
                bean.setTime(date);
                bean.setGoods_model(goods_model);
                bean.setContent(content);
                bean.setIs_anonymous(is_anonymous);
                list.add(bean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 解析货源列表
     *
     * @param response
     * @return
     */
    public static List<TransportBean> parseHuoYuanList(JSONObject response) {
        List<TransportBean> list = null;
        try {
            JSONObject data = response.getJSONObject("data");
            JSONArray niu_index_response = data.getJSONArray("niu_index_response");
            list = new ArrayList<>();
            for (int i = 0; i < niu_index_response.length(); i++) {
                JSONObject obj = niu_index_response.getJSONObject(i);
                TransportBean bean = new TransportBean();
                String t_id = obj.getString("tid");
                String delivery_time = obj.getString("delivery_time");
                String t_address = obj.getString("t_address");
                String format = obj.getString("format");
                String t_weight = obj.getString("t_weight");
                String s_mdd = obj.getString("s_mdd");
                String addtime = obj.getString("addtime");
                long remaining = obj.getLong("remaining");
                bean.settName(obj.getString("t_name"));
                bean.setT_id(t_id);
                bean.setDelivery_time(delivery_time);
                bean.setT_address(t_address);
                bean.setFormat(format);
                bean.setT_weight(t_weight);
                bean.setS_mdd(s_mdd);
                bean.setAddtime(addtime);
                bean.setTime(remaining);
                bean.setRemaining(remaining);
                list.add(bean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    //解析查看租赁订单
    public static String parseChaKanZuLinDingDan(JSONObject response) {
        String url = "";
        try {
            if (JsonUtils.isSuccess(response)) {
                JSONObject data = response.getJSONObject("data");
                String niu_index_response = data.getString("niu_index_response");
                return niu_index_response;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return url;
    }

    /**
     * 解析车型
     *
     * @param response
     * @return
     */
    public static List<CheXingBean> parseCheXingJson(JSONObject response) {
        List<CheXingBean> list = null;
        try {
            JSONObject data = response.getJSONObject("data");
            JSONArray niu_index_response = data.getJSONArray("niu_index_response");
            list = new ArrayList<>();
            for (int i = 0; i < niu_index_response.length(); i++) {
                JSONObject jsonObject = niu_index_response.getJSONObject(i);
                CheXingBean bean = new CheXingBean();
                String models = jsonObject.getString("models");
                bean.setName(models);
                list.add(bean);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 解析车长(用的javabean是车型的bean)
     *
     * @param response
     * @return
     */
    public static List<CheXingBean> parseCheChang(JSONObject response) {
        List<CheXingBean> list = null;
        try {
            JSONObject data = response.getJSONObject("data");
            JSONArray niu_index_response = data.getJSONArray("niu_index_response");
            list = new ArrayList<>();
            for (int i = 0; i < niu_index_response.length(); i++) {
                JSONObject jsonObject = niu_index_response.getJSONObject(i);
                CheXingBean bean = new CheXingBean();
                String models = jsonObject.getString("length");
                bean.setName(models);
                list.add(bean);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    //解析购物车多商品
    public static List<GouWuCheBean> parseGouWuCheDataToDingDan(String stringExtra) {
        List<GouWuCheBean> list = null;
        try {
            JSONArray array = new JSONArray(stringExtra);
            list = new ArrayList<>();
            for (int i = 0; i < array.length(); i++) {
                GouWuCheBean bean = new GouWuCheBean();
                JSONObject jsonObject = array.getJSONObject(i);
                String shop_id = jsonObject.getString("shop_id");
                String shop_name = jsonObject.getString("shop_name");
//                String logo_pic = jsonObject.getString("logo_pic");
                bean.setShop_id(shop_id);
                bean.setShop_name(shop_name);
//                bean.setLogo(logo_pic);
                JSONArray jsonArray = jsonObject.getJSONArray("goods");
                List<ShoppingBean> shoppingBeanList = new ArrayList<>();
                for (int j = 0; j < jsonArray.length(); j++) {
                    JSONObject obj = jsonArray.getJSONObject(j);
                    ShoppingBean shoppingBean = new ShoppingBean();
                    double price = obj.getDouble("price");
                    String goods_id = obj.getString("goods_id");
                    String goods_name = obj.getString("goods_name");
                    int goods_num = obj.getInt("goods_num");
                    String goods_model = obj.getString("goods_model");
                    String goods_iv = obj.getString("goods_iv");
                    shoppingBean.setYunfei(obj.getDouble("yunfei"));
                    shoppingBean.setSelecter(true);
                    shoppingBean.setName(goods_name);
                    shoppingBean.setPrice(price);
                    shoppingBean.setShop_id(goods_id);
//                    shoppingBean.setGoods_id(goods_id);
                    shoppingBean.setNumber(goods_num);
                    shoppingBean.setModel(goods_model);
                    shoppingBean.setIv_pic(goods_iv);
                    shoppingBeanList.add(shoppingBean);
                }
                bean.setShoppingBeanList(shoppingBeanList);
                list.add(bean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 解析审核状态的code
     *
     * @param response
     * @return
     */
    public static int parseShenHeState(JSONObject response) {
        try {
            if (-50 == response.getInt("code")) {
                return 3;
            }
            JSONObject data = response.getJSONObject("data");
            JSONObject niu_index_response = data.getJSONObject("niu_index_response");
            int state = niu_index_response.getInt("state");
            return state;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 解析车辆列表
     *
     * @param response
     * @return
     */
    public static List<CheLiangBean> parseCheLiangList(JSONObject response) {
        List<CheLiangBean> list = null;

        try {
            if (isSuccess(response)) {
                JSONObject data = response.getJSONObject("data");
                JSONArray niu_index_response = data.getJSONArray("niu_index_response");
                list = new ArrayList<>();
                for (int i = 0; i < niu_index_response.length(); i++) {
                    CheLiangBean bean = new CheLiangBean();
                    JSONObject obj = niu_index_response.getJSONObject(i);
                    String id = obj.getString("id");
                    String plate_number = obj.getString("plate_number");
                    String road_number = obj.getString("road_number");
                    bean.setRoad_number(road_number);
                    String road_pic = obj.getString("road_pic");
                    bean.setRoad_pic(road_pic);
                    String head_pic = obj.getString("head_pic");
                    bean.setHead_pic(head_pic);
                    String travel_permit = obj.getString("travel_permit");
                    bean.setTravel_permit(travel_permit);
                    String trailer_permit = obj.getString("trailer_permit");
                    bean.setTrailer_permit(trailer_permit);
                    String car_models = obj.getString("car_models");
                    bean.setCar_models(car_models);
                    String car_long = obj.getString("car_long");
                    bean.setCar_long(car_long);
                    String car_width = obj.getString("car_width");
                    bean.setCar_width(car_width);
                    String car_height = obj.getString("car_height");
                    bean.setCar_height(car_height);
                    String max_bearing = obj.getString("max_bearing");
                    bean.setMax_bearing(max_bearing);
                    String driver = obj.getString("driver");
                    bean.setDriver(driver);
                    String driver_phone = obj.getString("driver_phone");
                    bean.setDriver_phone(driver_phone);
                    bean.setId(id);
                    bean.setPlate_number(plate_number);
                    list.add(bean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return list;
    }

    /**
     * 解析单个车辆
     *
     * @param response
     * @return
     */
    public static CheLiangBean parseCheliangSingle(JSONObject response) {
        CheLiangBean bean = null;
        try {
            if (isSuccess(response)) {
                JSONObject data = response.getJSONObject("data");
                JSONObject obj = data.getJSONObject("niu_index_response");
                bean = new CheLiangBean();
                String id = obj.getString("id");
                String plate_number = obj.getString("plate_number");
                String road_number = obj.getString("road_number");
                bean.setRoad_number(road_number);
                String road_pic = obj.getString("road_pic");
                bean.setRoad_pic(road_pic);
                String head_pic = obj.getString("head_pic");
                bean.setHead_pic(head_pic);
                String travel_permit = obj.getString("travel_permit");
                bean.setTravel_permit(travel_permit);
                String trailer_permit = obj.getString("trailer_permit");
                bean.setTrailer_permit(trailer_permit);
                String car_models = obj.getString("car_models");
                bean.setCar_models(car_models);
                String car_long = obj.getString("car_long");
                bean.setCar_long(car_long);
                String car_width = obj.getString("car_width");
                bean.setCar_width(car_width);
                String car_height = obj.getString("car_height");
                bean.setCar_height(car_height);
                String max_bearing = obj.getString("max_bearing");
                bean.setMax_bearing(max_bearing);
                String driver = obj.getString("driver");
                bean.setDriver(driver);
                String driver_phone = obj.getString("driver_phone");
                bean.setDriver_phone(driver_phone);
                bean.setId(id);
                bean.setPlate_number(plate_number);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean;
    }

    /**
     * 解析货主运单列表
     *
     * @param response
     * @return
     */
    public static List<YunDanBean> parseYunDanJson(JSONObject response) {
        JSONObject data = null;
        try {
            data = response.getJSONObject("data");
            JSONArray niu_index_response = data.getJSONArray("niu_index_response");
            return parseYunShuYunDan(niu_index_response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析承运方运单列表
     *
     * @param response
     * @return
     */
    public static List<YunDanBean> parseChengYunYunDanJson(JSONObject response) {
        JSONObject data = null;
        try {
            data = response.getJSONObject("data");
            JSONArray data1 = data.getJSONArray("niu_index_response");
            return parseYunShuYunDan(data1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<YunDanBean> parseYunShuYunDan(JSONArray niu_index_response) {
        List<YunDanBean> list = null;
        try {
            list = new ArrayList<>();
            for (int i = 0; i < niu_index_response.length(); i++) {
                YunDanBean bean = new YunDanBean();
                JSONObject obj = niu_index_response.getJSONObject(i);
                if (obj.has("id")) {
                    String id = obj.getString("id");
                    bean.setId(id);
                }
                if (obj.has("tid")) {
                    String tid = obj.getString("tid");
                    bean.setTid(tid);
                }
                if (obj.has("cid")) {
                    String cid = obj.getString("cid");
                    bean.setCid(cid);
                }
                if (obj.has("pay")) {
                    int pay = obj.getInt("pay");
                    bean.setPayCode(pay);
                }
                String t_name = obj.getString("t_name");
                String delivery_time = obj.getString("delivery_time");
                String t_address = obj.getString("t_address");
                String format = obj.getString("format");
                if (obj.has("t_weight")) {
                    String t_weight = obj.getString("t_weight");
                    bean.setT_weight(t_weight);
                }
                String s_mdd = obj.getString("s_mdd");
                if (obj.has("addtime")) {
                    String addtime = obj.getString("addtime");
                    bean.setAddtime(addtime);
                }
                if (obj.has("remaining")) {
                    int remaining = obj.getInt("remaining");
                    bean.setRemaining(remaining);
                }
                if (obj.has("price")) {
                    String price = obj.getString("price");
                    bean.setPrice(price);
                }

                int status = obj.getInt("status");
                int num = obj.getInt("num");

                bean.setNum(num);

                bean.setT_name(t_name);
                bean.setDelivery_time(delivery_time);
                bean.setT_address(t_address);
                bean.setFormat(format);
                bean.setS_mdd(s_mdd);

                bean.setStatus(status);
                list.add(bean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 报价的解析
     *
     * @param response
     */
    public static List<BaoJiaBean> parseBaoJia(JSONObject response) {
        List<BaoJiaBean> list = null;
        try {
            JSONObject data = response.getJSONObject("data");
            JSONObject niu_index_response = data.getJSONObject("niu_index_response");
            JSONArray data1 = niu_index_response.getJSONArray("data");
            list = new ArrayList<>();
            for (int i = 0; i < data1.length(); i++) {
                JSONObject obj = data1.getJSONObject(i);
                BaoJiaBean bean = new BaoJiaBean();
                bean.setId(obj.getString("id"));
                bean.setAddtime(obj.getString("addtime"));
                bean.setPrice(obj.getString("price"));
                bean.setStatus(obj.getString("status"));
                bean.setVehicle(obj.getString("vehicle"));
                bean.setChechang(obj.getString("length"));
                bean.setTel(obj.getString("tel"));
                bean.setName(obj.getString("name"));
                bean.setTid(obj.getString("tid"));
                list.add(bean);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 解析帖子详情信息
     *
     * @param jsonObject
     * @return
     */
    public static TieZiBean parseTieZiDetails(JSONObject jsonObject) {
        TieZiBean bean = null;
        try {
            JSONObject data = jsonObject.getJSONObject("data");
            JSONObject niu_index_response = data.getJSONObject("niu_index_response");
            bean = new TieZiBean();

            String title = niu_index_response.getString("title");
            String content = niu_index_response.getString("content");
            JSONArray imgList = niu_index_response.getJSONArray("imgList");
            List<String> images = new ArrayList<>();
            for (int i = 0; i < imgList.length(); i++) {
                images.add(imgList.getString(i));
            }
            bean.setTitle(title);
            bean.setContent(content);
            bean.setImageList(images);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return bean;
    }

    /**
     * 解析单张上传图片
     *
     * @param response
     * @return
     */
    public static String parseSingleImage(JSONObject response) {
        String url = "";
        try {
            JSONObject data = response.getJSONObject("data");
            url = data.getString("niu_index_response");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return url;
    }


    //解析轮播图数据
    public static List<Flowbean> parseFlowJson(JSONObject response) {
        List<Flowbean> list = null;
        try {
            String code = response.getString("code");
            if ("0".equals(code)) {
                JSONObject data = response.getJSONObject("data");
                JSONArray niu_index_response = data.getJSONArray("niu_index_response");
                list = new ArrayList<>();
                for (int i = 0; i < niu_index_response.length(); i++) {
                    JSONObject obj = niu_index_response.getJSONObject(i);
                    Flowbean bean = new Flowbean();
                    int ap_id = obj.getInt("ap_id");
                    int adv_id = obj.getInt("adv_id");
                    String adv_title = obj.getString("adv_title");
                    String adv_url = obj.getString("adv_url");
                    String adv_image = obj.getString("adv_image");
                    bean.setAp_id(ap_id);
                    bean.setAdv_id(adv_id);
                    bean.setAdv_title(adv_title);
                    bean.setAdv_url(adv_url);
                    bean.setAdv_image(adv_image);
                    list.add(bean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

}
