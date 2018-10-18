package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

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
import com.qmwl.zyjx.view.GridViewWithHeaderAndFooter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 * 评价成功
 */

public class PingJiaSuccessActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private LiuLanAdapter adapter;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.pingjia_success_layout);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.pingjia_success);
        GridViewWithHeaderAndFooter gdView = (GridViewWithHeaderAndFooter) findViewById(R.id.pingjia_layout_gridview);

        adapter = new LiuLanAdapter();
        View headView = getLayoutInflater().inflate(R.layout.pingjia_success_head_layout, null);
        gdView.addHeaderView(headView);
        gdView.setAdapter(adapter);
        headView.findViewById(R.id.pingjia_success_head_chakanwodepingjia).setOnClickListener(this);
        gdView.setOnItemClickListener(this);
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {
        getData();
    }

    @Override
    protected void onMyClick(View v) {
        switch (v.getId()) {
            case R.id.pingjia_success_head_chakanwodepingjia:
                Intent intent = new Intent(this, WoDePingJiaActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void getData() {
        AndroidNetworking.get(Contact.pingjiachenggong)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            List<ShoppingBean> shoppingBeanList = JsonUtils.parseShoppingJson(response);
                            adapter.setIsLiuLan();
                            adapter.setData(shoppingBeanList);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String uid = "";
        if (MyApplication.getIntance().isLogin()) {
            uid = MyApplication.getIntance().userBean.getUid();
        }
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(WebViewActivity.SHOPURL, adapter.getItem(position).getUrl() + "&uid=" + uid);
        startActivity(intent);
    }
}
