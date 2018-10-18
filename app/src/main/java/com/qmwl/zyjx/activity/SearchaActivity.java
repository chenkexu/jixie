package com.qmwl.zyjx.activity;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.TagBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;

import org.json.JSONObject;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/8/3.
 * 执行搜索产品的页面
 */

public class SearchaActivity extends BaseActivity {
    public static final String SEARCH_TYPE = "com.gh.search_type";
    public static final int SEARCH_SHOPPING = 0;//商品搜素
    public static final int SEARCH_TIEZI = 1;//帖子搜索
    private ViewGroup biaoqianContainer;
    private EditText searchEdittext;
    private ListView mLv;
    private int flag = 0;//默认的是首页商品搜索

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.search_activity_layout);
    }

    @Override
    protected void initView() {
        biaoqianContainer = (ViewGroup) findViewById(R.id.search_layout_linearlayout);
        searchEdittext = (EditText) findViewById(R.id.base_top_bar_search);
        mLv = (ListView) findViewById(R.id.search_layout_listview);
        findViewById(R.id.base_top_bar_right).setOnClickListener(this);
        findViewById(R.id.base_top_bar_search_iv).setOnClickListener(this);
        focusKeywordView(searchEdittext);
//        searchEdittext.addTextChangedListener(new EditTextWatcher());
    }

    private void setFlagData(List<TagBean> data) {
        if (data == null) {
            return;
        }
        TagOnClickListener tagListener = new TagOnClickListener();
        for (int i = 0; i < data.size(); i++) {
            View v = getLayoutInflater().inflate(R.layout.biaoqian_container_layout, null);
            TextView tv = (TextView) v.findViewById(R.id.biaoqian_textview_tv);
            tv.setOnClickListener(tagListener);
            tv.setText(data.get(i).name);
            tv.setTag(data.get(i));
            biaoqianContainer.addView(v);
        }
    }

    private void search_search(String keyname) {
        AndroidNetworking.get(Contact.search_search)
                .addPathParameter("post_name", keyname)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {
        AndroidNetworking.post(Contact.search_flag)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<TagBean> strings = JsonUtils.parseSearchFlag(response);
                        setFlagData(strings);
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

    }

    @Override
    protected void onMyClick(View v) {
        switch (v.getId()) {
            case R.id.base_top_bar_search_iv:
                String trim = searchEdittext.getText().toString().trim();
                if ("".equals(trim)) {
                    Toast.makeText(this, getString(R.string.qingshurusousuodeguanjianzi), Toast.LENGTH_SHORT).show();
                    return;
                }
                submitSearch(trim);
                break;
            case R.id.base_top_bar_right:
                if (!MyApplication.getIntance().isLogin()) {
                    Intent intent = new Intent(this, LoginActivity.class);
                    startActivityForResult(intent, 0);
                    return;
                }
                Intent intent = new Intent(this, MessagesActivity.class);
                startActivity(intent);
                break;

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

    //标签的点击事件
    class TagOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
//            TextView a = (TextView) v;
//            String s = a.getText().toString();
//            submitSearch(s);
            TagBean bean = (TagBean) v.getTag();
            Intent intent = new Intent(SearchaActivity.this, ShoppingThreadActivity.class);
            intent.putExtra(ShoppingSecondActivity.Category_name, bean.name);
            intent.putExtra(ShoppingSecondActivity.Category_id, bean.id);
            startActivity(intent);
//            searchEdittext.setText(s);
//            searchEdittext.setSelection(searchEdittext.getText().length());
//            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

        }
    }

    //输入框的文字监听
    class EditTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() == 0) {
                biaoqianContainer.setVisibility(View.VISIBLE);
                mLv.setVisibility(View.GONE);
            } else {
                search_search(s.toString());
                biaoqianContainer.setVisibility(View.GONE);
                mLv.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {

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

    private void submitSearch(String key) {
        Intent intent = new Intent(this, ShoppingThreadActivity.class);
        intent.putExtra(ShoppingThreadActivity.SEARCH_KEY, key);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_in_anim, R.anim.activity_out_anim);
        finish();
    }

}
