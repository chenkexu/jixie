package com.qmwl.zyjx.utils;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.qmwl.zyjx.R;

/**
 * Created by Administrator on 2017/9/5.
 * popuWindow的工具类
 */

public class PoPuWindowUtils {
    public interface selecterPayTypeListener {

//        void onZaiXianZhifu();
//
//        void onDuiGongFuKuan();



        void onAlipay();
        void onWechat();
        void onYinlian();
        void onZhuanzhang();
        void onSmallDaikuan();


    }

    PopupWindow popupWindow;

    private PoPuWindowUtils() {
    }

    private static PoPuWindowUtils intance;

    public static PoPuWindowUtils getIntance() {
            intance = new PoPuWindowUtils();
        return intance;
    }

    public void dismissPopuWindow() {
        if (popupWindow != null) {
            if (popupWindow.isShowing()) {
                popupWindow.dismiss();
            }
        }
    }

    public void showSelecterPayType(Context cx, View parentView, final selecterPayTypeListener listener) {
        if (popupWindow == null) {
            View popuView = LayoutInflater.from(cx).inflate(R.layout.selecter_pay_type_layout, null);


            popuView.findViewById(R.id.st_aplipay).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //支付宝支付
                    if (listener != null) {
                        listener.onAlipay();
                    }
                }
            });


            popuView.findViewById(R.id.st_wechat).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //微信
                    if (listener != null) {
                        listener.onWechat();
                    }
                }
            });


            popuView.findViewById(R.id.st_yilian).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //对公付款
                    if (listener != null) {
                        listener.onYinlian();
                    }
                }
            });


            popuView.findViewById(R.id.st_small_daikuan).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //对公付款
                    if (listener != null) {
                        listener.onSmallDaikuan();
                    }
                }
            });


//            popuView.findViewById(R.id.selecter_pay_layout_duigongfukuan).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //对公付款
//                    if (listener != null) {
//                        listener.onDuiGongFuKuan();
//                    }
//                }
//            });












            popupWindow = new PopupWindow(popuView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setAnimationStyle(R.style.take_photo_anim);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            // 设置点击窗口外边窗口消失
            popupWindow.setOutsideTouchable(true);
            popupWindow.setFocusable(true);
        }
        popupWindow.showAtLocation(parentView, Gravity.BOTTOM, 0, 0);
    }
}
