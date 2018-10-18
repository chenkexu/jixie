package com.qmwl.zyjx.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.activity.FaTieActivity;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.utils.GlideUtils;
import com.yuyh.library.imgsel.common.Constant;

/**
 * Created by Administrator on 2017/8/8.
 */

public class FaTieGridViewAdapter extends MyBaseAdapter<String> {

    @Override
    public int getCount() {
        if (mList == null) {
            return 0;
        } else if (mList.size() == FaTieActivity.MAX_NUM + 1) {
            return FaTieActivity.MAX_NUM;
        }

        return super.getCount();
    }

    @Override
    protected View getItemView(final int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fatie_activity_gridview_item, null);
        ImageView iv = (ImageView) v.findViewById(R.id.fatie_item_photo_iv);
        View container = v.findViewById(R.id.fatie_item_photo);
        TextView numTv = (TextView) v.findViewById(R.id.fatie_layout_image_tv);
        View remove = v.findViewById(R.id.fatie_item_remove);
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String remove1 = mList.remove(position);
                Constant.imageList.remove(remove1);
                notifyDataSetChanged();
            }
        });
        hideorshow(iv, container, numTv, remove, position, parent);

        return v;
    }

    private void hideorshow(ImageView iv, View container, TextView numTv, View remove, int position, ViewGroup parent) {
        if (mList == null || mList.size() == 1) {
            iv.setVisibility(View.GONE);
            remove.setVisibility(View.GONE);
            container.setVisibility(View.VISIBLE);
            numTv.setText(0 + "/" + FaTieActivity.MAX_NUM);
            return;
        }
        if (mList.size() - 1 == position) {
            iv.setVisibility(View.GONE);
            remove.setVisibility(View.GONE);
            container.setVisibility(View.VISIBLE);
            numTv.setText(String.valueOf(mList.size() - 1) + "/" + FaTieActivity.MAX_NUM);
            return;
        }
        iv.setVisibility(View.VISIBLE);
        remove.setVisibility(View.VISIBLE);
        container.setVisibility(View.GONE);
        numTv.setText(String.valueOf(mList.size() - 1) + "/" + FaTieActivity.MAX_NUM);
//        Glide.with(parent.getContext()).load(getItem(position)).into(iv);
        GlideUtils.openImage(parent.getContext(),getItem(position),iv);
    }
}
