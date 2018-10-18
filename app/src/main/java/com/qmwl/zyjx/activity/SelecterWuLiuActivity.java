package com.qmwl.zyjx.activity;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.ChaKanWuliuAdapter;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.bean.WuLiuBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.GlideUtils;
import com.qmwl.zyjx.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/8/18.
 * //查看物流页面
 */

public class SelecterWuLiuActivity extends BaseActivity {
    public static final String ID = "com.gh.selecterwuliu.id";
    private String order_id = "";
    private ChaKanWuliuAdapter chaKanWuliuAdapter;
    private ImageView iv;
    private TextView statueTv;
    private TextView gongsiTv;
    private TextView bianhaoTv;
    private TextView dianhuaTv;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.chakanwuliu_layout);
    }

    @Override
    protected void initView() {
        order_id = getIntent().getStringExtra(ID);
        if ("".equals(order_id) || TextUtils.isEmpty(order_id)) {
            finish();
            return;
        }
        setTitleContent(R.string.wuliuxiangqing);

        iv = (ImageView) findViewById(R.id.chakanwuliu_layout_iv);
        statueTv = (TextView) findViewById(R.id.chakanwuliu_layout_statue);
        gongsiTv = (TextView) findViewById(R.id.chakanwuliu_layout_gongsi);
        bianhaoTv = (TextView) findViewById(R.id.chakanwuliu_layout_bianhao);
        dianhuaTv = (TextView) findViewById(R.id.chakanwuliu_layout_dianhua);

        ListView mLv = (ListView) findViewById(R.id.chakanwuliu_layout_listview);

        chaKanWuliuAdapter = new ChaKanWuliuAdapter();
        mLv.setAdapter(chaKanWuliuAdapter);
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {
        AndroidNetworking.post(Contact.wuliu_list)
                .addBodyParameter("order_id", order_id)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        WuLiuBean wuLiuBean = JsonUtils.parseWuLiu(response);
                        if (wuLiuBean != null) {
                            setData(wuLiuBean);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });
    }

    private void setData(WuLiuBean bean) {
        if (bean == null) {
            return;
        }
        GlideUtils.openImage(this, bean.getImage(), iv);
        statueTv.setText(getString(R.string.wuliuzhuangtai) + ":" + bean.getIssign());
        gongsiTv.setText(getString(R.string.chengyungongsi) + ":" + bean.getExpress_company());
        bianhaoTv.setText(getString(R.string.yundanbianhao) + ":" + bean.getNumber());
        chaKanWuliuAdapter.setData(bean.getList());
    }

    @Override
    protected void onMyClick(View v) {

    }
}
