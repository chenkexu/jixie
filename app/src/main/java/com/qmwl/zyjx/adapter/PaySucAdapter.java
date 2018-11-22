package com.qmwl.zyjx.adapter;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.LiuLanBean;
import com.qmwl.zyjx.bean.PaySucBean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 */

public class PaySucAdapter extends BaseQuickAdapter<PaySucBean, BaseViewHolder> {

    public PaySucAdapter( @Nullable List<PaySucBean> data) {
        super(R.layout.bussiness_layout_item, data);
    }




    @Override
    protected void convert(BaseViewHolder helper, PaySucBean item) {




    }
}
