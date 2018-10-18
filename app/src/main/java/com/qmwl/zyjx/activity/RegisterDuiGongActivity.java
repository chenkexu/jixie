package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.RegisterDuiGongAdapter;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.bean.RegisterDuiGongBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 郭辉 on 2018/1/18 9:46.
 * TODO:对公列表
 */

public class RegisterDuiGongActivity extends BaseActivity {

    private RegisterDuiGongAdapter adapter;
    private String apply_id;
    private boolean isWaiHui;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.activity_layout_register_duigong);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.zhifufangshi);
        apply_id = getIntent().getStringExtra("apply_id");
        isWaiHui = getIntent().getBooleanExtra("iswaihui", false);
        ListView mLv = (ListView) findViewById(R.id.id_regisger_duigong_listview);
        adapter = new RegisterDuiGongAdapter();
        mLv.setAdapter(adapter);
        findViewById(R.id.id_register_duigong_next).setOnClickListener(this);
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
            case R.id.id_register_duigong_next:
                Intent intent = new Intent(this, RegisterDuiGongTishiActivity.class);
                intent.putExtra("apply_id", apply_id);
                intent.putExtra("iswaihui", isWaiHui);
                startActivity(intent);
                break;
        }
    }

    private void getData() {
        String url = Contact.duigongfukuanlist;
        if (isWaiHui) {
            url = url.concat("?type=2");
        } else {
            url = url.concat("?type=1");
        }
        AndroidNetworking.get(url)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        try {
                            if (JsonUtils.isSuccess(response)) {
                                List<RegisterDuiGongBean> list = new ArrayList<RegisterDuiGongBean>();
                                JSONObject data1 = response.getJSONObject("data");
                                JSONArray data = data1.getJSONArray("niu_index_response");
                                for (int i = 0; i < data.length(); i++) {
                                    RegisterDuiGongBean bean = new RegisterDuiGongBean();
                                    JSONObject obj = data.getJSONObject(i);
                                    String bank_name = obj.getString("bank_name");
                                    String bank_num = obj.getString("bank_num");
                                    String company = obj.getString("company");
                                    bean.setKaihuhang(bank_name);
                                    bean.setZhanghao(bank_num);
                                    bean.setTitle(company);
                                    list.add(bean);
                                }
                                adapter.setData(list);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                    }
                });
        showLoadingDialog();
    }
}
