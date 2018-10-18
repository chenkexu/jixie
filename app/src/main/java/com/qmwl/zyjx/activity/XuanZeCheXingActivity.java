package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.XuanZeCheXingAdapter;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.bean.CheXingBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2017/8/29.
 */

public class XuanZeCheXingActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    public static final String TYPE = "com.gh.chexing.type";
    public static final String DATA = "com.gh.chexing.data";
    private XuanZeCheXingAdapter adapter;
    private boolean isCheChang;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.xuanzechexing_activity_layout);
    }

    @Override
    protected void initView() {
        isCheChang = getIntent().getBooleanExtra(TYPE, false);
        setRightText(R.string.ok);
        if (isCheChang) {
            setTitleContent(R.string.chechang);
        } else {
            setTitleContent(R.string.chexing);
        }
        adapter = new XuanZeCheXingAdapter();
        GridView mgridView = (GridView) findViewById(R.id.xuanzechexing_layout_gridview);
        mgridView.setAdapter(adapter);
        mgridView.setOnItemClickListener(this);
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {
        String url = "";
        if (isCheChang) {
            url = Contact.xuanzechechang;
        } else {
            url = Contact.xuanzechexing;
        }
//        AndroidNetworking.get(Contact.xuanzechexing)
        AndroidNetworking.get(url)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        List<CheXingBean> data = null;
                        if (isCheChang) {
                            data = JsonUtils.parseCheChang(response);
                        } else {
                            data = JsonUtils.parseCheXingJson(response);
                        }
                        adapter.setData(data);
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                    }
                });
        showLoadingDialog();
    }

    @Override
    protected void onMyClick(View v) {

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CheXingBean item = adapter.getItem(position);
        if (item != null) {
            Intent intent = new Intent();
            intent.putExtra(DATA, item.getName());
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
