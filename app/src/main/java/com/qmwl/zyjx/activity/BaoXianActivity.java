package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.BaoXianAdapter;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.bean.ShoppingBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.GlideUtils;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.utils.ListViewPullListener;
import com.qmwl.zyjx.view.GridViewWithHeaderAndFooter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 郭辉 on 2017/12/18 11:26.
 * TODO:保险页面
 */

public class BaoXianActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private BaoXianAdapter adapter;
    private boolean isLoadMore;
    private ImageView shanghuIv;
    private TextView shanghuName;
    private TextView contentTv;
    private String moreUrl;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.baoxian_activity_layout);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.baoxian);
        GridViewWithHeaderAndFooter gdView = (GridViewWithHeaderAndFooter) findViewById(R.id.baoxian_layout_gridview);
        adapter = new BaoXianAdapter();

        View headView = getLayoutInflater().inflate(R.layout.head_baoxian_layout, null);

        shanghuIv = (ImageView) headView.findViewById(R.id.baoxian_head_iv);
        shanghuName = (TextView) headView.findViewById(R.id.baoxian_head_title);
        contentTv = (TextView) headView.findViewById(R.id.baoxian_head_content);
        View moreView = headView.findViewById(R.id.baoxian_head_gengduo);
        moreView.setOnClickListener(this);

        gdView.addHeaderView(headView);
        gdView.setAdapter(adapter);
        gdView.setOnItemClickListener(this);
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {
        AndroidNetworking.get(Contact.baoxian_url).build()
                .getAsJSONObject(new JSONObjectRequestListener() {

                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (JsonUtils.isSuccess(response)) {
                                JSONObject data = response.getJSONObject("data");
                                JSONObject niu_index_response = data.getJSONObject("niu_index_response");
                                JSONObject info = niu_index_response.getJSONObject("info");
                                String title = info.getString("safe_name");
                                String content = info.getString("description");
                                String picUrl = info.getString("litpic");
                                moreUrl = info.getString("url");

                                shanghuName.setText(title);
                                GlideUtils.openImagePhoto(BaoXianActivity.this, picUrl, shanghuIv);
                                contentTv.setText(content);

                                JSONArray list = niu_index_response.getJSONArray("list");
                                List<ShoppingBean> beanList = new ArrayList<>();
                                for (int i = 0; i < list.length(); i++) {
                                    ShoppingBean bean = new ShoppingBean();
                                    JSONObject obj = list.getJSONObject(i);
                                    bean.setName(obj.getString("title"));
                                    bean.setShop_id(obj.getString("id"));
                                    bean.setUrl(obj.getString("url"));
                                    bean.setIv_pic(obj.getString("imglist"));
                                    beanList.add(bean);
                                }
                                if (adapter != null) {
                                    adapter.setData(beanList);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });

    }

    @Override
    protected void onMyClick(View v) {
        switch (v.getId()) {
            case R.id.baoxian_head_gengduo:
                if ("".equals(moreUrl)) {
                    return;
                }
                Intent intent = new Intent(this, NewsDetailsActivity.class);
                intent.putExtra(NewsDetailsActivity.DETAILS_URL, moreUrl);
                intent.putExtra(NewsDetailsActivity.DETAILS_TYPE, NewsDetailsActivity.TYPE_BAOXIAN);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, NewsDetailsActivity.class);
        intent.putExtra(NewsDetailsActivity.DETAILS_URL, adapter.getItem(position).getUrl());
        intent.putExtra(NewsDetailsActivity.DETAILS_TYPE, NewsDetailsActivity.TYPE_BAOXIAN);
        startActivity(intent);
    }
}
