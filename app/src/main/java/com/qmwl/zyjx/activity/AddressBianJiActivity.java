package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.AddressBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/8/1.
 * 编辑收货地址(包含购物的收货地址,运输的收货地址,运输的发货地址)
 */

public class AddressBianJiActivity extends BaseActivity {

    public static final String BIANJI_ADRESS_VALUE = "com.gh.bianjidizhi";
    public static final String TYPE = "com.gh.address_bianji_type";//类别
    public static final boolean BIANJI_ADDRESS = true;//默认为新增地址，设置type后是编辑地址
    public static final int BIANJI_ADDRESS_BIANJI_CODE = 1;
    public static final int BIANJI_ADDRESS_ADD_CODE = 2;

    private boolean isBianJi;//tru为编辑地址,
    private TextView shengTv;
    private TextView shiTv;
    private EditText nameEdittext;
    private EditText callEdittext;
    private EditText addressEdittext;
    private CheckBox defaultCheckBox;
    //省的id
    private String id;
    //市的id
    private String shiId;
    private AddressBean bean;
    //当前页面的所属（参考页面说明）
    private int type = AddressActivity.GOUWU_ADDRESS;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.address_bianji_layout);
    }

    @Override
    protected void initView() {
        //获取页面的类别,默认为购物的收货地址
        type = getIntent().getIntExtra(AddressActivity.ADDRESS_ADDRESS_TYPE, AddressActivity.GOUWU_ADDRESS);
        setTitleContent(R.string.bianjishouhuodizhi);
        setRightText(R.string.baocun);
        isBianJi = getIntent().getBooleanExtra(TYPE, false);

        nameEdittext = (EditText) findViewById(R.id.address_layout_bianji_nameedittext);
        callEdittext = (EditText) findViewById(R.id.address_layout_bianji_lianxidianhua);
        addressEdittext = (EditText) findViewById(R.id.address_layout_bianji_address_address);
        defaultCheckBox = (CheckBox) findViewById(R.id.address_bianji_default_iv);

        findViewById(R.id.base_top_bar_righttext).setOnClickListener(this);
        shengTv = (TextView) findViewById(R.id.address_layout_bianji_sheng_tv);
        shiTv = (TextView) findViewById(R.id.address_layout_bianji_shi_tv);
        findViewById(R.id.address_layout_bianji_sheng_container).setOnClickListener(this);
        findViewById(R.id.address_layout_bianji_shi_container).setOnClickListener(this);
        dealData();
    }


    private void dealData() {
        if (isBianJi) {
            bean = (AddressBean) getIntent().getSerializableExtra(BIANJI_ADRESS_VALUE);
            if (bean == null) {
                Toast.makeText(this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            nameEdittext.setText(bean.getName());
            callEdittext.setText(bean.getMobile());
            addressEdittext.setText(bean.getAddress());
            shengTv.setText(bean.getSheng_name());
            shiTv.setText(bean.getShi_name());
            id = bean.getSheng();
            shiId = bean.getShi();
            defaultCheckBox.setChecked(bean.isDefault());
        }
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {

    }

    @Override
    protected void onMyClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.address_layout_bianji_sheng_container:
                intent = new Intent(this, BlackListActivity.class);
                intent.putExtra(BlackListActivity.BLACK_TYPE, BlackListActivity.BLACK_SHENG);
                startActivityForResult(intent, BlackListActivity.BLACK_SHENG);
                break;

            case R.id.address_layout_bianji_shi_container:
                if (getString(R.string.qingxuanze).equals(shengTv.getText().toString()) && "".equals(id)) {
                    Toast.makeText(this, getString(R.string.qingxuanzesheng), Toast.LENGTH_SHORT).show();
                    return;
                }
                intent = new Intent(this, BlackListActivity.class);
                intent.putExtra(BlackListActivity.BLACK_TYPE, BlackListActivity.BLACK_SHI);
                intent.putExtra(BlackListActivity.BLACK_VALUE_ID, id);
                startActivityForResult(intent, BlackListActivity.BLACK_SHI);
                break;

            case R.id.base_top_bar_righttext:
                baocun();
                break;
        }
    }

    //保存按钮
    private void baocun() {
        String callStr = callEdittext.getText().toString().trim();
        String nameStr = nameEdittext.getText().toString().trim();
        String shengStr = shengTv.getText().toString().trim();
        String shiStr = shiTv.getText().toString().trim();
        String addressStr = addressEdittext.getText().toString().trim();
        boolean defaultChecked = defaultCheckBox.isChecked();

        if ("".equals(callStr) || "".equals(nameStr) || "".equals(shengStr) || "".equals(shiStr) || "".equals(addressStr)) {
            Toast.makeText(this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
            return;
        }
        if (bean == null) {
            bean = new AddressBean();
        }
        bean.setDefault(defaultChecked);
        bean.setAddress(addressStr);
        bean.setName(nameStr);
        bean.setSheng(id);
        bean.setShi(shiId);
        bean.setMobile(callStr);
        postData(bean);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        String value = data.getStringExtra(BlackListActivity.BLACK_VALUE);

        switch (resultCode) {
            case BlackListActivity.BLACK_SHENG:
                id = data.getStringExtra(BlackListActivity.BLACK_VALUE_ID);
                shengTv.setText(value);
                break;
            case BlackListActivity.BLACK_SHI:
                shiId = data.getStringExtra(BlackListActivity.BLACK_VALUE_ID);
                shiTv.setText(value);
                break;

        }
    }

    void postData(AddressBean bean) {
        if (bean == null) {
            Toast.makeText(this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
            return;
        }
        ANRequest.PostRequestBuilder post = null;
        if (isBianJi) {
            switch (type) {
                case AddressActivity.GOUWU_ADDRESS:
                    post = AndroidNetworking.post(Contact.address_update);
                    break;
                case AddressActivity.YUNSHU_ADDRESS:
                    post = AndroidNetworking.post(Contact.yunshu_address_edit);
                    break;
                case AddressActivity.YUNSHU_FAHUO_ADDRESS:
                    post = AndroidNetworking.post(Contact.yunshu_fahuo_address_edit);
                    break;
                default:
                    post = AndroidNetworking.post(Contact.address_add);
                    break;
            }
            post.addBodyParameter("id", bean.getId());
        } else {
            switch (type) {
                case AddressActivity.GOUWU_ADDRESS:
                    post = AndroidNetworking.post(Contact.address_add);
                    break;
                case AddressActivity.YUNSHU_ADDRESS:
                    post = AndroidNetworking.post(Contact.yunshu_address_add);
                    break;
                case AddressActivity.YUNSHU_FAHUO_ADDRESS:
                    post = AndroidNetworking.post(Contact.yunshu_fahuo_address_add);
                    break;
                default:
                    post = AndroidNetworking.post(Contact.address_add);
                    break;
            }
        }
        post.addBodyParameter("uid", MyApplication.getIntance().userBean.getUid())
                .addBodyParameter("user_id", MyApplication.getIntance().userBean.getUid())
                .addBodyParameter("consigner", bean.getName())
                .addBodyParameter("mobile", bean.getMobile())
                .addBodyParameter("phone", bean.getMobile())
                .addBodyParameter("is_default", String.valueOf(bean.getDefaultCode()))
                .addBodyParameter("address", bean.getAddress())
                .addBodyParameter("province", bean.getSheng())
                .addBodyParameter("city", bean.getShi())
                .addBodyParameter("district", "")
                .addBodyParameter("zip_code", "")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();

                        try {
//                            if (type == AddressActivity.GOUWU_ADDRESS || type == AddressActivity.YUNSHU_FAHUO_ADDRESS) {
                                if (JsonUtils.is100Success(response)) {
                                    Toast.makeText(AddressBianJiActivity.this, getString(R.string.baocunchenggong), Toast.LENGTH_SHORT).show();
                                    setResult(BIANJI_ADDRESS_BIANJI_CODE);
                                    finish();
                                } else {
                                    Toast.makeText(AddressBianJiActivity.this, getString(R.string.baocunshibaiqingchongshi), Toast.LENGTH_SHORT).show();
                                }
//                            } else {
//                                if (JsonUtils.isSuccess(response)) {
//                                    Toast.makeText(AddressBianJiActivity.this, getString(R.string.baocunchenggong), Toast.LENGTH_SHORT).show();
//                                    setResult(BIANJI_ADDRESS_BIANJI_CODE);
//                                    finish();
//                                } else {
//                                    Toast.makeText(AddressBianJiActivity.this, getString(R.string.baocunshibaiqingchongshi), Toast.LENGTH_SHORT).show();
//                                }
//                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AddressBianJiActivity.this, getString(R.string.baocunshibaiqingchongshi), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                        Toast.makeText(AddressBianJiActivity.this, getString(R.string.tijiaoshibai_wangluo), Toast.LENGTH_SHORT).show();
                    }
                });
        showLoadingDialog();
    }

}
