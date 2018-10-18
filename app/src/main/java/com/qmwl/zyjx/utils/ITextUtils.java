package com.qmwl.zyjx.utils;

import android.text.TextUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/8/24.
 */

public class ITextUtils {

    /**
     * 验证手机号码是否合法
     */
    public static boolean validatePhoneNumber(String mobiles) {
        String telRegex = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$";
        boolean result = !TextUtils.isEmpty(mobiles) && mobiles.matches(telRegex);
        return !result;
//        Pattern p = Pattern.compile("^ ((13[0 - 9]) | (15[ ^ 4,//D])|(18[0,5-9]))//d{8}$");
//        Matcher m = p.matcher(mobiles);
//        return !m.matches();

    }
}
