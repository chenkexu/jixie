package com.qmwl.zyjx.adapter;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.Constant;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.LiuLanBean;
import com.qmwl.zyjx.bean.PaySucBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.GlideUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/7/26.
 */

public class PaySucAdapter extends BaseQuickAdapter<PaySucBean, BaseViewHolder> {

    public PaySucAdapter( @Nullable List<PaySucBean> data) {
        super(R.layout.paysuc_layout_item, data);
    }




    @Override
    protected void convert(BaseViewHolder helper, PaySucBean item) {

            helper.setText(R.id.tv_name, item.getGoods_name()+"");
            helper.setText(R.id.tv_price, "￥"+item.getPrice()+"");
            GlideUtils.openImage(mContext, Contact.http_yuming+"/"+item.getPic_cover(),(ImageView) helper.getView(R.id.iv_img));
            helper.setText(R.id.tv_address, item.getPlace_city()+"");

    }
}
