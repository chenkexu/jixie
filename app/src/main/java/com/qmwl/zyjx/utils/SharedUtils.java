package com.qmwl.zyjx.utils;

import android.content.Context;

import com.hyphenate.easeui.domain.EaseUser;
import com.qmwl.zyjx.bean.HxUserData;

/**
 * Created by Administrator on 2017/9/14.
 */

public class SharedUtils {
    public static final String LANGUAGE = "com.gh.language.str";
    public static final String LANGUAGE_TYPE = "com.gh.language.type";
    //用户信息
    public static final String USER_DATA = "com.gh.language.userdata";
    //用户名
    public static final String USER_NAME = "com.gh.username.key";
    //用户昵称
    public static final String USER_NICKNAME = "com.gh.usernickname.key";
    //用户头像
    public static final String USER_IMAGE = "com.gh.userimage.key";
    //用户密码
    public static final String USER_PASSWORD = "com.gh.userpassword.key";
    //用户uid
    public static final String USER_UID = "com.gh.useruid.key";
    //是否是登录状态
    public static final String USER_LOGIN_STATUE = "com.gh.loginstatue.key";

    /**
     * 存储语言类型s
     *
     * @param language
     * @param cx
     */
    public static void putLanguage(String language, Context cx) {
        cx.getSharedPreferences(LANGUAGE, Context.MODE_PRIVATE).edit().putString(LANGUAGE_TYPE, language).commit();
    }

    /**
     * 获取语言类型
     *
     * @param cx
     * @return
     */
    public static String getLanguage(Context cx) {
        return cx.getSharedPreferences(LANGUAGE, Context.MODE_PRIVATE).getString(LANGUAGE_TYPE, "");
    }


    public static void putString(String key, String value, Context cx) {
        cx.getSharedPreferences(LANGUAGE, Context.MODE_PRIVATE).edit().putString(key, value).commit();
    }


    public static String getString(String key, Context cx) {
        return cx.getSharedPreferences(LANGUAGE, Context.MODE_PRIVATE).getString(key, "");
    }

    public static void putBoolean(String key, boolean value, Context cx) {
        cx.getSharedPreferences(LANGUAGE, Context.MODE_PRIVATE).edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(String key, Context cx) {
        return cx.getSharedPreferences(LANGUAGE, Context.MODE_PRIVATE).getBoolean(key, false);
    }

    //获取用户信息
    public static HxUserData getUserName(String key, Context cx) {
        String string = cx.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).getString(key, "");
        String[] split = string.split(",");
        if (split == null) {
            return null;
        }
        HxUserData user = new HxUserData();
        user.setUserName(split[0]);
        user.setUserImage(split[1]);
        return user;
    }

    //存储用户信息
    public static void putUserName(String key, Context cx, String nickName, String image) {
        cx.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit().putString(key, nickName + "," + image).commit();
    }

    public static void putWxPayStatue(Context cx,boolean isKai) {
        cx.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit().putBoolean("iskaidian", isKai).commit();
    }

    public static boolean getWxPayStatue(Context cx) {
        return cx.getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).getBoolean("iskaidian", false);
    }
}
