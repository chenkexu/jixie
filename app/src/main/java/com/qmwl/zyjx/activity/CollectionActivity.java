package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.LiuLanAdapter;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.ShoppingBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 * 我的收藏页面
 */

public class CollectionActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private LiuLanAdapter adapter;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.collection_activity_layout);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.collection);
        GridView gdView = (GridView) findViewById(R.id.collection_layout_gridview);

        adapter = new LiuLanAdapter();
        gdView.setAdapter(adapter);
        gdView.setOnItemClickListener(this);
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {
        AndroidNetworking.post(Contact.wodeshoucang)
                .addBodyParameter("uid", MyApplication.getIntance().userBean.getUid())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                        List<ShoppingBean> shoppingBeanList = JsonUtils.parseCollection(response);
                        adapter.setData(shoppingBeanList);
                        if(shoppingBeanList==null){
                            Toast.makeText(CollectionActivity.this, getString(R.string.shujuweikong), Toast.LENGTH_SHORT).show();
                        }else if(shoppingBeanList.size()==0){
                            Toast.makeText(CollectionActivity.this, getString(R.string.shujuweikong), Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(CollectionActivity.this, getString(R.string.wangluocuowu), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onMyClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(WebViewActivity.SHOPURL, adapter.getItem(position).getUrl());
        startActivity(intent);
    }

}
