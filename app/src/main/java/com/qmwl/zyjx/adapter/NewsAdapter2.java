package com.qmwl.zyjx.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.bean.NewsBean;
import com.qmwl.zyjx.utils.GlideUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 */

public class NewsAdapter2 extends BaseQuickAdapter<NewsBean,BaseViewHolder> {
    private Context context;
    public NewsAdapter2(Context context,@Nullable List<NewsBean> data) {
        super(R.layout.message_layout_item, data);
        this.context = context;
    }



//    public NewsAdapter2(@Nullable List<BaseViewHolder> data) {
//        super(R.layout.message_layout_item, data);
//    }


    @Override
    protected void convert(BaseViewHolder helper, NewsBean item) {
//        openImage(parent, item.getImage(), holder.iv);
        helper.setText(R.id.news_content_tv,item.getTitle());
        helper.setText(R.id.news_content_time,item.getCreate_time());
        ImageView view = helper.getView(R.id.news_item_iv);
        GlideUtils.openImage(context,item.getImage(),view);
    }
}
