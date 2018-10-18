package com.qmwl.zyjx.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.FabuAdapter;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.bean.FabuBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/8/16.
 * 搜索帖子页面
 */

public class TieZiSearchActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    EditText searchEdittext;
    FabuAdapter adapter;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.tiezi_search_layout);
    }

    @Override
    protected void initView() {
        findViewById(R.id.fabu_top_bar_search_iv).setOnClickListener(this);
        searchEdittext = (EditText) findViewById(R.id.fabu_top_bar_search);
        focusKeywordView(searchEdittext);
        ListView mLv = (ListView) findViewById(R.id.fabu_fragment_listview);

        adapter = new FabuAdapter();
        mLv.setAdapter(adapter);
        mLv.setOnItemClickListener(this);
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {

    }

    @Override
    protected void onMyClick(View v) {
        switch (v.getId()) {
            case R.id.fabu_top_bar_search_iv:
                searchTieZi();
                break;
        }
    }


    private void searchTieZi() {
        String key = searchEdittext.getText().toString().trim();
        if ("".equals(key)) {
            Toast.makeText(this, getString(R.string.qingshurusousuodeguanjianzi), Toast.LENGTH_SHORT).show();
            return;
        }
        AndroidNetworking.get(Contact.search_tiezi + "?post_name=" + key)
//                .addPathParameter("post_name", key)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            List<FabuBean> fabuBeen = JsonUtils.parseSearchTieZiJson(response);
                            adapter.setData(fabuBeen);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }


                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }


    /**
     * 将焦点移到输入框，弹起输入法
     */
    public void focusKeywordView(EditText edt) {
        if (edt != null) {
            edt.requestFocus();
            edt.setSelection(getKeywordText(edt).length());
            showInputMethod(edt, true, 300);
        }
    }

    /**
     * 得到输入框的文字
     *
     * @return
     */
    public String getKeywordText(EditText edt) {
        return edt.getText().toString().trim();
    }

    /**
     * 弹起输入法
     *
     * @param edit
     * @param delay
     * @param delayTime
     */
    private void showInputMethod(final EditText edit, boolean delay, int delayTime) {
        if (delay) {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    InputMethodManager imm = (InputMethodManager) getSystemService(
                            Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.showSoftInput(edit, 0);
                    }
                }
            }, delayTime);
        } else {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(edit, 0);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                finish();
                overridePendingTransition(R.anim.activity_in_anim, R.anim.activity_out_anim);
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(parent.getContext(), NewsDetailsActivity.class);
        intent.putExtra(NewsDetailsActivity.DETAILS_TYPE, NewsDetailsActivity.TYPE_TIEZI);
        intent.putExtra(NewsDetailsActivity.DETAILS_URL, adapter.getItem(position).getUrl());
        startActivity(intent);
    }
}
