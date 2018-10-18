package com.qmwl.zyjx.activity;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.AllPingJiaAdapter;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.bean.PingJiabean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 * 全部评价页面
 */

public class AllPingJiaActivity extends BaseActivity {

    public static final String SHOP_ID = "com.gh.pingjia.shop_id";
    private ListView mLv;
    private String shop_id = "";
    private AllPingJiaAdapter adapter;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.allpingjia_layout);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.allpingjia);
        mLv = (ListView) findViewById(R.id.allpingjia_layout_listview);

        adapter = new AllPingJiaAdapter();
        mLv.setAdapter(adapter);
        shop_id = getIntent().getStringExtra(SHOP_ID);
        getShopId(shop_id);
    }

    private void getShopId(String shop_id) {
        String data = getIntent().getStringExtra(SHOP_ID);
        parseShopId(data);
    }

    private void parseShopId(String data) {
        try {
            JSONObject obj = new JSONObject(data);
            shop_id = obj.getString("post_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    }

    private void getData() {
        AndroidNetworking.get(Contact.all_pingjia + "?goods_id=" + shop_id)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<PingJiabean> pingJiabeen = JsonUtils.parseAllPingJia(response);
                        adapter.setData(pingJiabeen);
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });
    }
}
