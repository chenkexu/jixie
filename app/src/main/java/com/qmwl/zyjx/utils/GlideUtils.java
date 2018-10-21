package com.qmwl.zyjx.utils;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.qmwl.zyjx.R;

/**
 * Created by Administrator on 2017/8/22.
 * //图片加载
 */

public class GlideUtils {

    //
    public static void openImage(Context cx, String url, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.mipmap.small);
        requestOptions.placeholder(R.mipmap.small);
        Glide.with(cx).setDefaultRequestOptions(requestOptions).load(url).into(imageView);
     //   Glide.with(cx).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.small).into(imageView);
    }

    public static void openImagePhoto(Context cx, String url, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions();
       requestOptions.error(R.mipmap.phone);
       requestOptions.placeholder(R.mipmap.phone);
       Glide.with(cx).setDefaultRequestOptions(requestOptions).load(url).into(imageView);
    //    Glide.with(cx).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.phone).into(imageView);
    }

    public static void openHeadImage(Context cx, String url, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.error(R.mipmap.morentouxiang);
        requestOptions.placeholder(R.mipmap.morentouxiang);
        Glide.with(cx).setDefaultRequestOptions(requestOptions).load(url).into(imageView);
      //  Glide.with(cx).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.morentouxiang).into(imageView);
    }


    /**
     * 加载图片
     */
    public static void loadImage(Context mContext, ImageView imageView, String imgUrl, int errorPicId, int placeholderId) {
        if (!TextUtils.isEmpty(imgUrl)) {
            RequestOptions options = new RequestOptions(); // 加载失败的图片
            Glide.with(imageView.getContext()).load(imgUrl).into(imageView);
        }
    }
}
