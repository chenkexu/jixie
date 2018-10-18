package com.qmwl.zyjx.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.qmwl.zyjx.bean.HxUserData;
import com.qmwl.zyjx.bean.ShoppingBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/27.
 */

public class DBDao {
    private static DBDao dao;
    private SQLiteDatabase db;
    private ContentValues zujiValue = null;

    private DBDao(Context paramContext) {
        this.db = new DBHelper(paramContext).getWritableDatabase();
    }

    public static DBDao getIntance(Context paramContext) {
        if (dao == null)
            dao = new DBDao(paramContext);
        return dao;
    }

    public int delete(String paramString1, String paramString2, String[] paramArrayOfString) {
        return this.db.delete(paramString1, paramString2, paramArrayOfString);
    }
    //插入用户昵称，头像到数据库
    public void add_userData(String userid,String username,String userimage){
        Cursor cursor = db.rawQuery("select * from hx_userdata where user_id =?", new String[]{userid});
        int i = 0;
        while (cursor.moveToNext()){
            i++;
        }
        if (i>0){
            ContentValues value = new ContentValues();
            value.put("user_name", username);
            value.put("user_image",userimage);
            db.update("hx_userdata", value, "user_id=?", new String[]{userid});
        }else{
            ContentValues values = new ContentValues();
            values.put("user_id",userid);
            values.put("user_name",username);
            values.put("user_image",userimage);
            insertValue("hx_userdata",values);
        }
    }

    /**
     * 获取用户信息
     * @param userid
     * @return
     */
    public HxUserData get_userData(String userid){
        Cursor cursor = db.rawQuery("select * from hx_userdata where user_id =?", new String[]{userid});
//        Cursor cursor = db.rawQuery("select * from hx_userdata where user_name =?", new String[]{userid});
        HxUserData bean = null;
        while (cursor.moveToNext()){
            bean = new HxUserData();
            String user_name = cursor.getString(cursor.getColumnIndex("user_name"));
            String user_image = cursor.getString(cursor.getColumnIndex("user_image"));
            bean.setUserName(user_name);
            bean.setUserImage(user_image);
        }

        return bean;
    }

    /**
     * 插入购物车商品(单件)
     * 参数依次为:
     * 1,产品id (productid)
     * 2,商家商标(trademarkimage)
     * 3,商家名称(name)
     * 4,产品图片(producticon)
     * 5,购买数量(number)
     * 6,产品型号(model)
     */
    public long insertShopping(String productid, String trademarkimage, String name, String producticon, int number, String model) {
        ContentValues values = new ContentValues();
        values.put("productid", productid);
        values.put("trademarkimage", trademarkimage);
        values.put("name", name);
        values.put("producticon", producticon);
        values.put("number", number);
        values.put("model", model);
        return insertValue("shoppinglist", values);
    }

    /**
     * 查看购物车列表
     *
     * @return
     */
    public List<ShoppingBean> selecterShoppingList() {
        Cursor cursor = db.rawQuery("select * from shoppinglist", null);
        List<ShoppingBean> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String productid = cursor.getString(cursor.getColumnIndex("productid"));
            String trademarkimage = cursor.getString(cursor.getColumnIndex("trademarkimage"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String producticon = cursor.getString(cursor.getColumnIndex("producticon"));
            int number = cursor.getInt(cursor.getColumnIndex("number"));
            String model = cursor.getString(cursor.getColumnIndex("model"));
            ShoppingBean bean = new ShoppingBean();
            bean.setShop_id(productid);
            bean.setLogo_pic(trademarkimage);
            bean.setName(name);
            bean.setIv_pic(producticon);
            bean.setNumber(number);
            bean.setModel(model);
            list.add(bean);
        }
        return list;
    }

    /**
     * 修改商品数量
     *
     * @param number
     * @param productid
     */
    public void updateShoppingNumber(int number, String productid) {
//        db.execSQL("update shoppinglist set number=" + number + " where productid=" + productid);
        ContentValues value = new ContentValues();
        value.put("number", number);
        db.update("shoppinglist", value, "productid=?", new String[]{productid});
    }

    /**
     * 删除商品
     *
     * @param productid 商品id
     */
    public void deleteShoppingNumber(String productid) {
//        db.execSQL("delete shoppinglist where productid=" + productid);
        db.delete("shoppinglist", "productid=?", new String[]{productid});
    }

    public long insertValue(String tablename, ContentValues paramContentValues) {
        return this.db.insert(tablename, null, paramContentValues);
    }


    public Cursor selectValue(String tablename, String[] paramArrayOfString) {
        return this.db.rawQuery(tablename, paramArrayOfString);
    }

    public Cursor selectValue(String paramString1, String[] paramArrayOfString1, String paramString2, String[] paramArrayOfString2, String paramString3, String paramString4, String paramString5) {
        return this.db.query(paramString1, paramArrayOfString1, paramString2, paramArrayOfString2, paramString3, paramString4, paramString5);
    }

    public int update(String paramString1, ContentValues paramContentValues, String paramString2, String[] paramArrayOfString) {
        return this.db.update(paramString1, paramContentValues, paramString2, paramArrayOfString);
    }
}
