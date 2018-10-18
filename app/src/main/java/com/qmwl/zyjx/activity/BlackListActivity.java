package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.BlackListAdapter;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.bean.BlackBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.view.RulerWidget;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 * 黑名单页面or地区选择页面
 */

public class BlackListActivity extends BaseActivity implements RulerWidget.OnTouchingLetterChangedListener, AdapterView.OnItemClickListener {

    public static final String BLACK_TYPE = "com.gh.black_list.type";
    public static final String BLACK_VALUE = "com.gh.black_list.value";
    public static final String BLACK_VALUE_ID = "com.gh.black_list.value_id";
    //黑名单
    public static final int BLACK_LIST = 0;
    //选择省
    public static final int BLACK_SHENG = 1;
    //选择市
    public static final int BLACK_SHI = 2;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.blacklist_activity_layout);
    }

    private int type = BLACK_LIST;//默认为黑名单
    private ListView mLv;
    private BlackListAdapter adapter;

    @Override
    protected void initView() {
        type = getIntent().getIntExtra(BLACK_TYPE, BLACK_LIST);
        RulerWidget rulerWidget = (RulerWidget) findViewById(R.id.black_layout_ruler);
        rulerWidget.setOnTouchingLetterChangedListener(this);
        adapter = new BlackListAdapter();
        mLv = (ListView) findViewById(R.id.black_layout_listview);
        mLv.setAdapter(adapter);
        switch (type) {
            case BLACK_LIST:
                setTitleContent(R.string.heimingdan);
                getBlackList();
                break;

            case BLACK_SHENG:
                setTitleContent(R.string.qingxuanzesheng);
                mLv.setOnItemClickListener(this);
                getsheng();
                break;

            case BLACK_SHI:
                setTitleContent(R.string.qingxuanzeshi);
                mLv.setOnItemClickListener(this);
                String id = getIntent().getStringExtra(BLACK_VALUE_ID);
                getshi(id);
                break;

        }

    }

    private void getBlackList() {
        showLoadingDialog();
        AndroidNetworking.post(Contact.blackList_list)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        List<BlackBean> list = JsonUtils.parseBlackList(response);
                        adapter.setData(list);
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                    }
                });
    }


    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {
    }

    @Override
    protected void onMyClick(View v) {

    }

    @Override
    public void onTouchingLetterChanged(String s) {
        //该字母首次出现的位置
        int position = adapter.getPositionForSection(s.charAt(0));
        if (position != -1) {
            mLv.setSelection(position);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent();
        BlackBean item = adapter.getItem(position);
        intent.putExtra(BLACK_VALUE, item.getName());
        intent.putExtra(BLACK_VALUE_ID, item.getId());
        setResult(type, intent);
        finish();
    }

    private void getsheng() {
        showLoadingDialog();
        AndroidNetworking.post(Contact.getsheng)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        List<BlackBean> list = JsonUtils.parseSheng(response);
                        adapter.setData(list);
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                    }
                });
    }


    private void getshi(String id) {
        showLoadingDialog();
        AndroidNetworking.post(Contact.getshi)
                .addBodyParameter("province_id", id)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        List<BlackBean> list = JsonUtils.parseShi(response);
                        adapter.setData(list);
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                    }
                });
    }
}
