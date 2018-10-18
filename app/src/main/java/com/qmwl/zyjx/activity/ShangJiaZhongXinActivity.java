package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.ShangJiaZhongXinAdapter;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.bean.ShangJiaBean;
import com.qmwl.zyjx.bean.ShoppingBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.GlideUtils;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.utils.ListViewPullListener;
import com.qmwl.zyjx.view.GridViewWithHeaderAndFooter;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2017/8/2.
 * 商家中心
 */

public class ShangJiaZhongXinActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    public static final String SHOP_ID = "com.gh.shop_id";

    private String shopid = "";
    private ShangJiaZhongXinAdapter adapter;
    private ImageView shanghuIv;
    private TextView shanghuName;
    private TextView contentTv;
    private LinearLayout xingjiContainer;
    private static final int scale = 4;//
    private int page = 1;
    private boolean isLoadMore;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.business_layout);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.bussiness);
        GridViewWithHeaderAndFooter gdView = (GridViewWithHeaderAndFooter) findViewById(R.id.bussiness_layout_gridview);
        adapter = new ShangJiaZhongXinAdapter();
        View headView = getLayoutInflater().inflate(R.layout.business_head_layout, null);

        shanghuIv = (ImageView) headView.findViewById(R.id.business_layout_iv);
        shanghuName = (TextView) headView.findViewById(R.id.business_layout_name);
        contentTv = (TextView) headView.findViewById(R.id.business_layout_contenttv);
        xingjiContainer = (LinearLayout) headView.findViewById(R.id.business_layout_xingji_container);

        ListViewPullListener listViewPullListener = new ListViewPullListener(gdView, null, new ListViewPullListener.ListViewPullOrLoadMoreListener() {
            @Override
            public void onPullRefresh() {

            }

            @Override
            public void onLoadMore() {
                page++;
                isLoadMore = true;
                getshoppings(shopid);
            }
        });
        gdView.addHeaderView(headView);
        gdView.setAdapter(adapter);
        gdView.setOnItemClickListener(this);
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {
        shopid = getIntent().getStringExtra(SHOP_ID);
        if ("".equals(shopid) || TextUtils.isEmpty(shopid)) {
            Toast.makeText(this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
            return;
        }
        getShopData(shopid);
    }

    @Override
    protected void onMyClick(View v) {

    }

    private void getShopData(String shopid) {
        AndroidNetworking.get(Contact.shangjiazhongxin + "?shop_id=" + shopid)
//                .addPathParameter("shop_id", shopid)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ShangJiaBean shangJiaBean = JsonUtils.parseShangJiaMessage(response);
                        setData(shangJiaBean);
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });

        getshoppings(shopid);

    }

    //获取商品列表
    private void getshoppings(String shopid) {
        AndroidNetworking.get(Contact.shangjiazhongxin_shops + "?shop_id=" + shopid + "&page=" + page)
//                .addPathParameter("shop_id", shopid)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        List<ShoppingBean> shoppingBeen = JsonUtils.parseShangJiaShops(response);
                        if (isLoadMore) {
                            isLoadMore = false;
                            adapter.addData(shoppingBeen);
                        } else {
                            adapter.setData(shoppingBeen);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                    }
                });
        showLoadingDialog();
    }

    private void setData(ShangJiaBean bean) {
        if (bean == null) {
            Toast.makeText(this, getString(R.string.wucishangjia), Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        GlideUtils.openImage(this, Contact.httpaddress + "/" + bean.getShop_avatar(), shanghuIv);
        shanghuName.setText(bean.getShop_company_name());
        contentTv.setText(bean.getShop_description());
        int shop_type = bean.getShop_type();
        int i1 = shop_type / scale;
        int i2 = shop_type % scale;
        for (int i = 0; i < i1; i++) {
            ImageView iv = new ImageView(this);
            iv.setPadding(9, 9, 9, 9);
            iv.setImageResource(R.mipmap.huangguan);
            xingjiContainer.addView(iv);
        }
        for (int i = 0; i < i2; i++) {
            ImageView iv = new ImageView(this);
            iv.setPadding(9, 9, 9, 9);
            iv.setImageResource(R.mipmap.xingxing);
            xingjiContainer.addView(iv);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(WebViewActivity.isGOUWUCHE,true);
        intent.putExtra(WebViewActivity.SHOPURL, adapter.getItem(position).getUrl());
        startActivity(intent);

    }
}
