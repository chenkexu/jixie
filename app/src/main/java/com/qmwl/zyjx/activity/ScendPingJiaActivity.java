package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.hedgehog.ratingbar.RatingBar;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.GlideUtils;
import com.qmwl.zyjx.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/7/31.
 * 发表评价
 */

public class ScendPingJiaActivity extends BaseActivity {
    private static int REQUEST_CODE = 0;
    //订单ID
    public static final String order_id_data = "com.gh.pingjia_order_id";
    //订单号
    public static final String order_no_data = "com.gh.pingjia_order_no";
    //订单商品序列ID
    public static final String order_goods_id_data = "com.gh.pingjia_order_goods_id";
    //商品ID
    public static final String goods_id_data = "com.gh.pingjia_goods_id";
    //商品名称
    public static final String goods_name_data = "com.gh.pingjia_goods_name";
    //价格
    public static final String price_data = "com.gh.pingjia_price_data";
    //商铺ID
    public static final String shop_id_data = "com.gh.pingjia_shop_id";
    //商品图片
    public static final String shop_iv = "com.gh.pingjia_shop_iv";

    //商家用户名
    public static final String shop_name = "shop_name";
    @BindView(R.id.shop_name)
    TextView shopName;

    @BindView(R.id.business_name)
    TextView business_name;
    @BindView(R.id.rv_photo)
    RecyclerView rvPhoto;
    @BindView(R.id.dianpu_star)
    RatingBar dianpuStar;
    @BindView(R.id.server_star)
    RatingBar serverStar;

    private String order_id, order_no, order_goods_id, goods_id, goods_name, price, shop_id, shop_iv_url;

    private ImageView imageView;
    private RadioGroup radioGroup;
    private EditText pingjiaContent;
    private CheckBox nimingCheck;
    private RadioButton haopingRadioButton;
    private RadioButton zhongpingRadioButton;
    private RadioButton chapingPingRadioButton;

    private int dianpuStarNum = 0 ;
    private int serverStarNum = 0 ;





    @Override
    protected void setLayout() {
        setContentLayout(R.layout.scend_pingjia_layout);
        ButterKnife.bind(this);

    }

    @Override
    protected void initView() {
        setTitleContent(R.string.fabiaopingjia);
        setRightText(R.string.fabu);
        imageView = (ImageView) findViewById(R.id.scend_pingjia_layout_iv);
        radioGroup = (RadioGroup) findViewById(R.id.scend_pingjia_layout_radiogroup);
        haopingRadioButton = (RadioButton) findViewById(R.id.scend_pingjia_layout_haoping);
        zhongpingRadioButton = (RadioButton) findViewById(R.id.scend_pingjia_layout_zhongping);
        chapingPingRadioButton = (RadioButton) findViewById(R.id.scend_pingjia_layout_chaping);
        pingjiaContent = (EditText) findViewById(R.id.scend_pingjia_layout_pingjiacontent);
        nimingCheck = (CheckBox) findViewById(R.id.scend_pingjia_layout_shifouniming);
        dianpuStar.setStar(0);
        serverStar.setStar(0);
        //评价
        dianpuStar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float RatingCount) {
                dianpuStarNum = (int) RatingCount;
            }
        });
        serverStar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float RatingCount) {
                serverStarNum = (int) RatingCount;
            }
        });

        getData();
    }

    private void getData() {
        Intent intent = getIntent();
        order_id = intent.getStringExtra(order_id_data);
        order_no = intent.getStringExtra(order_no_data);
        order_goods_id = intent.getStringExtra(order_goods_id_data);
        goods_id = intent.getStringExtra(goods_id_data);
        goods_name = intent.getStringExtra(goods_name_data);
        price = intent.getStringExtra(price_data);
        shop_id = intent.getStringExtra(shop_id_data);
        shop_iv_url = intent.getStringExtra(shop_iv);

        String shop_nameStr = intent.getStringExtra(shop_name);

        GlideUtils.openImage(this, shop_iv_url, imageView);
        shopName.setText(goods_name);
        business_name.setText(shop_nameStr);
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

            case R.id.base_top_bar_righttext:
                submitData();
                break;

        }
    }

    private void submitData() {
        String contentString = pingjiaContent.getText().toString().trim();
        if ("".equals(contentString) || TextUtils.isEmpty(contentString)) {
            Toast.makeText(this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
            return;
        }
        int pingjiaCode = getPingJiaCode();
        int nimingCode = getNimingCode();

        AndroidNetworking.post(Contact.fabiaopingjia).addBodyParameter("uid", MyApplication.getIntance().userBean.getUid())
                .addBodyParameter("order_id", order_id)
                .addBodyParameter("order_no", order_no)
                .addBodyParameter("order_goods_id", order_goods_id)
                .addBodyParameter("goods_id", goods_id)
                .addBodyParameter("goods_name", goods_name)
                .addBodyParameter("price", price)
                .addBodyParameter("shop_id", shop_id)
                .addBodyParameter("explain_type", String.valueOf(pingjiaCode))
                .addBodyParameter("content", contentString)
                .addBodyParameter("is_anonymous", String.valueOf(nimingCode))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        try {
                            if (JsonUtils.isSuccess(response)) {
                                Toast.makeText(ScendPingJiaActivity.this, getString(R.string.pingjiachenggong), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ScendPingJiaActivity.this, PingJiaSuccessActivity.class);
                                startActivity(intent);
                                finish();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ScendPingJiaActivity.this, getString(R.string.tijiaoshibai), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                        Toast.makeText(ScendPingJiaActivity.this, getString(R.string.pingjiashibai), Toast.LENGTH_SHORT).show();
                    }
                });
        showLoadingDialog();

    }

    private int getNimingCode() {
        int code = 0;
        if (nimingCheck.isChecked()) {
            code = 1;
        } else {
            code = 0;
        }
        return code;
    }

    //评价的code
    private int getPingJiaCode() {
        int code = 1;
        if (haopingRadioButton.isChecked()) {
            code = 1;
        } else if (zhongpingRadioButton.isChecked()) {
            code = 2;
        } else if (chapingPingRadioButton.isChecked()) {
            code = 3;
        }
        return code;
    }


}
