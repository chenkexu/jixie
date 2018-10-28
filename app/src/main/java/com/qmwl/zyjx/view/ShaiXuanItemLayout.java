package com.qmwl.zyjx.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.SpinnerShaiXuanContainerAdapter;
import com.qmwl.zyjx.bean.ShaiXuanItemBean;
import com.qmwl.zyjx.bean.ShaiXuanSpinnerBean;

/**
 * Created by Administrator on 2017/8/4.
 */

public class ShaiXuanItemLayout extends LinearLayout implements View.OnClickListener {

    private GridView mGd;

    TextView titleTv;
    ShaiXuanSpinnerBean bean;
    SpinnerShaiXuanContainerAdapter adapter;

    private boolean isSelecter = false;
    private ImageView jiantouImage;

    public ShaiXuanItemLayout(Context context) {
        super(context);
        init(context);
    }

    public ShaiXuanItemLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ShaiXuanItemLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    public void setData(ShaiXuanSpinnerBean bean) {
        this.bean = bean;
        if (bean == null) {
            return;
        }
        titleTv.setText(bean.getAttr_value_name());
        if (bean.getValue_items() != null) {
            adapter.setData(bean.getValue_items());
        }
    }

    //初始化
    void init(Context context) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.spinner_shaixuan_container, this, false);
        titleTv = (TextView) rootView.findViewById(R.id.spinner_shaixuan_container_title);
        mGd = (GridView) rootView.findViewById(R.id.spinner_shaixuan_container_gridview);
        jiantouImage = (ImageView) rootView.findViewById(R.id.spinner_shaixuan_container_jiantou);
        adapter = new SpinnerShaiXuanContainerAdapter();
        titleTv.setOnClickListener(this);
        jiantouImage.setOnClickListener(this);
        mGd.setAdapter(adapter);
        if (isSelecter) {
            jiantouImage.setImageResource(R.mipmap.icon_shaixuan_xiajiantou);
            mGd.setVisibility(View.VISIBLE);
        } else {
            jiantouImage.setImageResource(R.mipmap.icon_shaixuan_shangjiantou);
            mGd.setVisibility(View.GONE);
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutParams);
        addView(rootView);
    }

    public String getValue() {
        if (adapter != null) {
            if ("".equals(adapter.getValue())) {
                return "";
            }
            return getTextValue() + "," + adapter.getValue();
//            return adapter.getValue();
        }
        return "";
    }

    public String getTextValue() {
        if (adapter != null && !"".equals(adapter.getValue())) {
            return titleTv.getText().toString();
        }
        return "";
    }

    //重置
    public void reset() {
        if (adapter != null) {
            for (ShaiXuanItemBean bean : adapter.getData()) {
                bean.setSelecter(false);
            }
            adapter.resetValue();
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onClick(View view) {
        if (mGd != null) {
            if (isSelecter) {
                mGd.setVisibility(View.GONE);
                jiantouImage.setImageResource(R.mipmap.icon_shaixuan_shangjiantou);
            } else {
                mGd.setVisibility(View.VISIBLE);
                jiantouImage.setImageResource(R.mipmap.icon_shaixuan_xiajiantou);
            }
            isSelecter = !isSelecter;
        }
    }
}
