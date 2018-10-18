package com.qmwl.zyjx.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 * 购物车bean
 */

public class GouWuCheBean implements Serializable {

    private String shop_id;
    private String shop_name;
    private String pic_url;
    private boolean isSelecter;
    private String url;
    private String liuyan;
    private String logo;
    private List<ShoppingBean> shoppingBeanList;


    //总价
    public double getPrice() {
        double price = 0;
        if (shoppingBeanList != null) {
            for (ShoppingBean bean : shoppingBeanList) {
                price += (bean.getPrice() * bean.getNumber()+bean.getYunfei()*bean.getNumber());
            }
        }
        return price;
    }

    public double getYunFeiZong(){
        double price = 0;
        if (shoppingBeanList != null) {
            for (ShoppingBean bean : shoppingBeanList) {
                price += (bean.getYunfei()*bean.getNumber());
            }
        }
        return price;
    }

    public boolean isChildSelecter() {
        if (shoppingBeanList != null) {
            for (ShoppingBean bean : shoppingBeanList) {
                if (bean.isSelecter()) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getLiuyan() {
        return liuyan;
    }

    public void setLiuyan(String liuyan) {
        this.liuyan = liuyan;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPic_url() {
        return pic_url;
    }

    public void setPic_url(String pic_url) {
        this.pic_url = pic_url;
    }

    public int getNumber() {
        return shoppingBeanList == null ? 0 : shoppingBeanList.size();
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getShop_name() {
        return shop_name;
    }

    public void setShop_name(String shop_name) {
        this.shop_name = shop_name;
    }

    public boolean isSelecter() {
        return isSelecter;
    }

    public void setSelecter(boolean selecter) {
        isSelecter = selecter;
    }

    public List<ShoppingBean> getShoppingBeanList() {
        return shoppingBeanList;
    }

    public void setShoppingBeanList(List<ShoppingBean> shoppingBeanList) {
        this.shoppingBeanList = shoppingBeanList;
    }

    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        JSONArray array = null;
        try {
            jsonObject.put("shop_id", shop_id);
            jsonObject.put("shop_name", shop_name);
            jsonObject.put("order_price", getPrice());
            jsonObject.put("logo_pic", logo);
            jsonObject.put("liuyan", liuyan);
            array = new JSONArray();
            if (shoppingBeanList != null) {
                for (ShoppingBean bean : shoppingBeanList) {
                    if (bean.isSelecter()) {
                        JSONObject obj = new JSONObject();
                        obj.put("price", bean.getPrice());
                        obj.put("yunfei",bean.getYunfei());
                        obj.put("goods_id", bean.getShop_id());
                        obj.put("goods_name", bean.getName());
                        obj.put("goods_num", bean.getNumber());
                        obj.put("goods_model", bean.getModel());
                        obj.put("goods_iv", bean.getIv_pic());
                        array.put(obj);
                    }
                }
            }
            jsonObject.put("goods", array);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }
}
