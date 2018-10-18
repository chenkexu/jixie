package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.MyPingjiaAdapter;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.PingJiabean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2017/7/25.
 * 我的评价页面
 */

public class WoDePingJiaActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private ListView mLv;
    private MyPingjiaAdapter adapter;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.wodepingjia_layout);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.wodepingjia);
        setRightImageView(R.mipmap.message);
        mLv = (ListView) findViewById(R.id.wodepingjia_layout_listview);

        adapter = new MyPingjiaAdapter();
        mLv.setAdapter(adapter);
        mLv.setOnItemClickListener(this);
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {
        showLoadingDialog();
        AndroidNetworking.post(Contact.wodepingjia_list)
                .addBodyParameter("uid", MyApplication.getIntance().userBean.getUid())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        List<PingJiabean> pingJiabeen = JsonUtils.parsePingJiaList(response);
                        if (pingJiabeen == null) {
                            Toast.makeText(WoDePingJiaActivity.this, getString(R.string.shujuweikong), Toast.LENGTH_SHORT).show();
                        }
                        adapter.setData(pingJiabeen);
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                        Toast.makeText(WoDePingJiaActivity.this, getString(R.string.tijiaoshibai_wangluo), Toast.LENGTH_SHORT).show();
                    }
                });
        showLoadingDialog();
    }

    @Override
    protected void onMyClick(View v) {
        switch (v.getId()) {
            case R.id.base_top_bar_right:
                Intent intent = new Intent(this, MessagesActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PingJiabean item = adapter.getItem(position);
        Intent intent = new Intent(this,WebViewActivity.class);
//        intent.putExtra(WebViewActivity.SHOPURL)
    }
}
