package com.qmwl.zyjx.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.AddressAdapter;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.AddressBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 * 收货地址页面
 */

public class AddressActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    private AddressAdapter adapter;
    public static final String ADDRESS_ACTION = "com.gh.address.action";
    public static final String ADDRESS_VALUE = "com.gh.address.value";
    public static final String ADDRESS_TYPE = "com.gh.address.type";
    public static final String ADDRESS_UPDATE_VALUE = "com.gh.address_update_value";
    public static final String ADDRESS_UPDATE_VALUE_BOOLEAN = "com.gh.address_update_value_boolean";
    //地址的收货类型
    public static final String ADDRESS_ADDRESS_TYPE = "com.gh.address_shouhuoleixing";
    //adapter的编辑
    public static final int BIANJI = 0;
    //adapter的删除
    public static final int SHANCHU = 1;
    //adapter默认地址
    public static final int MORENDIZHI = 2;
    //购物的收货地址管理
    public static final int GOUWU_ADDRESS = 0;
    //运输的收货地址管理
    public static final int YUNSHU_ADDRESS = 1;
    //运输的发货地址管理
    public static final int YUNSHU_FAHUO_ADDRESS = 2;

    public static final int ACTIVITY_RESULT_CODE = 1;
    private AddressBroadcastReceiver broadcastReceiver;
    private String url = "";
    private boolean isSelecter;
    //当前页面的类型(购物的收货地址,运输的收货地址,运输的发货地址)
    private int address_type = GOUWU_ADDRESS;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.shouhuodizhi_activity_layout);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.shouhuodizhi);
        address_type = getIntent().getIntExtra(ADDRESS_ADDRESS_TYPE, GOUWU_ADDRESS);
        switch (address_type) {
            case GOUWU_ADDRESS:
                url = Contact.address_list + "?user_id=" + MyApplication.getIntance().userBean.getUid();
                break;
            case YUNSHU_ADDRESS:
                url = Contact.yunshu_address_list + "?uid=" + MyApplication.getIntance().userBean.getUid();
                break;
            case YUNSHU_FAHUO_ADDRESS:
                setTitleContent(R.string.fahuorenguanli);
                url = Contact.yunshu_fahuo_address_list + "?uid=" + MyApplication.getIntance().userBean.getUid();
                break;
        }

        ListView mLv = (ListView) findViewById(R.id.shouhuodizhi_layout_address_listview);
        adapter = new AddressAdapter(address_type);
        mLv.setAdapter(adapter);
        mLv.setOnItemClickListener(this);
        View addView = findViewById(R.id.address_layout_add_address);
        addView.setOnClickListener(this);
        isSelecter = getIntent().getBooleanExtra(ADDRESS_UPDATE_VALUE_BOOLEAN, false);
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {
        AndroidNetworking.get(url)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        try {
                            List<AddressBean> addressBeen = JsonUtils.parseAddressJson(response);
                            if (addressBeen == null || addressBeen.size() == 0) {
                                Toast.makeText(AddressActivity.this, getString(R.string.shouhuodizhiweikong), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if (adapter != null)
                                adapter.setData(addressBeen);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AddressActivity.this, getString(R.string.shouhuodizhiweikong), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                        Toast.makeText(AddressActivity.this, getString(R.string.shouhuodizhiweikong), Toast.LENGTH_SHORT).show();
                    }
                });
        showLoadingDialog();
    }

    @Override
    protected void onMyClick(View v) {
        switch (v.getId()) {
            case R.id.address_layout_add_address:
                Intent intent = new Intent(this, AddressBianJiActivity.class);
                intent.putExtra(ADDRESS_ADDRESS_TYPE, address_type);
                startActivityForResult(intent, AddressBianJiActivity.BIANJI_ADDRESS_ADD_CODE);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        broadcastReceiver = new AddressBroadcastReceiver();
        IntentFilter filter = new IntentFilter(ADDRESS_ACTION);
        registerReceiver(broadcastReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (broadcastReceiver != null) {
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        if (data == null) {
//            return;
//        }
//        AddressBean bean = (AddressBean) data.getSerializableExtra(AddressBianJiActivity.BIANJI_ADRESS_VALUE);
        switch (resultCode) {
            case AddressBianJiActivity.BIANJI_ADDRESS_BIANJI_CODE:
//                Log.i("TAG", "address:" + bean.getAddress());
                getInterNetData();
                break;
            case AddressBianJiActivity.BIANJI_ADDRESS_ADD_CODE:
//                Log.i("TAG", "address:" + bean.getAddress());
                getInterNetData();
                break;

        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (isSelecter) {
            return;
        }
        if(address_type==GOUWU_ADDRESS){
            Intent intent = new Intent();
            AddressBean item = adapter.getItem(position);
            intent.putExtra(ADDRESS_VALUE, item);
            setResult(ACTIVITY_RESULT_CODE, intent);
            finish();
        }
    }

    class AddressBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getIntExtra(ADDRESS_TYPE, BIANJI)) {
                case BIANJI:
                    intent.setClass(context, AddressBianJiActivity.class);
                    startActivityForResult(intent, AddressBianJiActivity.BIANJI_ADDRESS_BIANJI_CODE);
                    break;
                case SHANCHU:
                    getInterNetData();
                    break;
                case MORENDIZHI:
                    getInterNetData();
//                    String id = intent.getStringExtra(ADDRESS_UPDATE_VALUE);
//                    boolean isDefault = intent.getBooleanExtra(ADDRESS_UPDATE_VALUE_BOOLEAN, false);
//                    if (TextUtils.isEmpty(id) || "".equals(id)) {
//                        return;
//                    }
//                    for (int i = 0; i < adapter.getData().size(); i++) {
//                        AddressBean addressBean = adapter.getData().get(i);
//                        if (addressBean.getId().equals(id)) {
//                            addressBean.setDefault(isDefault);
//                        } else {
//                            addressBean.setDefault(false);
//                        }
//                        adapter.notifyDataSetChanged();
//                    }

                    break;
            }

        }
    }

}
