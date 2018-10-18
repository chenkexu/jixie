package com.qmwl.zyjx.activity;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.TuiKuanAdapter;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.TuiKuanBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 * 退款售后
 */

public class TuiKuanShouHouActivity extends BaseActivity {
    private TuiKuanAdapter adapter;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.tuikuanshouhou_layout);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.tuikuanshouhou);
        ListView mLv = (ListView) findViewById(R.id.tuikuanxiangqing_layout_listview);
        adapter = new TuiKuanAdapter();
        mLv.setAdapter(adapter);
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
        AndroidNetworking.get(Contact.tuikuanshouhou + "?uid=" + MyApplication.getIntance().userBean.getUid())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        List<TuiKuanBean> tuiKuanBeen = JsonUtils.parseTuiKuan(response);
                        adapter.setData(tuiKuanBeen);
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });

    }


}
