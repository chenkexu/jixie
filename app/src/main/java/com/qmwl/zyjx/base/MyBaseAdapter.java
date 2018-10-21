package com.qmwl.zyjx.base;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.qmwl.zyjx.R;

public abstract class MyBaseAdapter<T> extends BaseAdapter {
    protected List<T> mList;

    public void setData(List<T> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    public void addData(List<T> addList) {
        if (mList == null || addList == null) {
            return;
        } else {
            mList.addAll(addList);
            notifyDataSetChanged();
        }
    }

    public List<T> getData() {
        return mList;
    }

    protected String getResouseString(Context cx, int id) {
        return cx.getString(id);
    }

    protected String getResouseString(View v, int id) {
        return v.getContext().getString(id);
    }

    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        return getItemView(position, convertView, parent);
    }

    protected void openImage(View parentView, String url, ImageView iv) {
        if ("".equals(url) || TextUtils.isEmpty(url)) {
            iv.setImageResource(R.mipmap.small);
        } else {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.error(R.mipmap.small);
            requestOptions.placeholder(R.mipmap.small);
            Glide.with(parentView.getContext()).setDefaultRequestOptions(requestOptions).load(url).into(iv);
   //         Glide.with(parentView.getContext()).load(url).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.small).into(iv);
        }
    }

    protected abstract View getItemView(int position, View convertView, ViewGroup parent);
}
