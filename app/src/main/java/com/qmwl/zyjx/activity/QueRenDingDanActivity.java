package com.qmwl.zyjx.activity;

import android.app.Dialog;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.chinapay.mobilepayment.global.CPGlobalInfo;
import com.chinapay.mobilepayment.global.ResultInfo;
import com.chinapay.mobilepayment.utils.Utils;
import com.orhanobut.logger.Logger;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.QueRenDingDanWaiAdapter;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.AddressBean;
import com.qmwl.zyjx.bean.GouWuCheBean;
import com.qmwl.zyjx.bean.ShoppingBean;
import com.qmwl.zyjx.bean.UserBean;
import com.qmwl.zyjx.dialog.ChargePopWindow;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.utils.ToastUtils;
import com.qmwl.zyjx.view.CommomDialog;
import com.qmwl.zyjx.wxapi.WXPayEntryActivity;
import com.qmwl.zyjx.zfb.PayBean;

import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/27.
 * 确认订单
 */
public class QueRenDingDanActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    public static final String DATA = "com.gh.querendingdan_data";
    public static final String TYPE = "com.gh.querendingdan_type";

    private boolean isGouWUChe = false;
    private QueRenDingDanWaiAdapter adapter;
    private View addressContainer;
    private View addressWu;
    private TextView nameTv;
    private TextView callTv;
    private TextView addressTv;
    private String shangpinid = "";
    private AddressBean addressBean = null;
    private TextView zongjiaPrice;
    //    DBDao dbDao;
    int index = 0;
    private View convertView;
    private double zongjiaDouble = 0;//订单总价

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.querendingdan_activity_layout);
//        dbDao = DBDao.getIntance(getApplicationContext());
    }

    @Override
    protected void initView() {
        String stringExtra = getIntent().getStringExtra(DATA);

        setTitleContent(R.string.querendingdan);
        ListView mLv = (ListView) findViewById(R.id.querendingdan_layout_listview);
        adapter = new QueRenDingDanWaiAdapter();
        mLv.setAdapter(adapter);
        mLv.setOnItemClickListener(this);
        View headView = getLayoutInflater().inflate(R.layout.querendingdan_activity_head_layout, null);

        convertView = findViewById(R.id.querendingdan_layout_container);
        addressWu = headView.findViewById(R.id.querendingdan_item_wudizhi);
        addressContainer = headView.findViewById(R.id.shouhuodizi_layout_container);

        zongjiaPrice = (TextView) findViewById(R.id.querendingdan_layout_price);
        addressWu.setOnClickListener(this);
        addressContainer.setOnClickListener(this);

        nameTv = (TextView) headView.findViewById(R.id.shouhuodizhi_layout_name);
        callTv = (TextView) headView.findViewById(R.id.shouhuodizhi_layout_tellphone);
        addressTv = (TextView) headView.findViewById(R.id.shouhuodizhi_layout_address);

        findViewById(R.id.querendingdan_layout_submit).setOnClickListener(this);
        mLv.addHeaderView(headView);
        isGouWUChe = getIntent().getBooleanExtra(TYPE, false);
        if (isGouWUChe) {
            //购物车的商品
            //pass:现在购物车传过来时也是走的单个商品，所以这里先空着
            List<GouWuCheBean> gouWuCheBeen = JsonUtils.parseGouWuCheDataToDingDan(stringExtra);
            zongjiaDouble = 0;
            for (GouWuCheBean bean : gouWuCheBeen) {
                zongjiaDouble += bean.getPrice();
            }
            zongjiaPrice.setText("￥" + zongjiaDouble);
            adapter.setData(gouWuCheBeen);

        } else {
            //单个商品
            if (!"".equals(stringExtra) && !TextUtils.isEmpty(stringExtra)) {
                List<GouWuCheBean> parsejson = parsejson(stringExtra);
                adapter.setData(parsejson);
            }
            double price = 0;
            if (adapter.getData() == null) {
                zongjiaPrice.setText(String.valueOf(price));
                return;
            }
            for (int i = 0; i < adapter.getData().size(); i++) {
                price += adapter.getData().get(i).getPrice();
            }
            zongjiaPrice.setText(String.valueOf(price));
        }
        getDefaultAddress();
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
            case R.id.querendingdan_item_wudizhi:
            case R.id.shouhuodizi_layout_container:
                Intent intent = new Intent(this, AddressActivity.class);
                startActivityForResult(intent, AddressActivity.ACTIVITY_RESULT_CODE);

                break;
            case R.id.querendingdan_layout_submit:
                if (isGouWUChe) {
                    dealData();
                } else {
                    submitData();
                }
                break;

        }

    }

    //提交购物车过来的数据
    private void dealData() {
        if (addressBean == null) {
            Toast.makeText(this, getString(R.string.qingxuanze) + getString(R.string.shouhuodizhi), Toast.LENGTH_SHORT).show();
            return;
        }
        List<GouWuCheBean> data = adapter.getData();
        JSONObject obj = new JSONObject();
        try {
            obj.put("uid", MyApplication.getIntance().userBean.getUid());
            obj.put("price", zongjiaDouble);
            obj.put("user_name", addressBean.getName());
            obj.put("mobile", addressBean.getMobile());
            obj.put("address", addressBean.getAddress());
            obj.put("sheng", addressBean.getSheng());
            obj.put("shi", addressBean.getShi());
            JSONArray jsonArray = new JSONArray(data.toString());
            obj.put("order", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AndroidNetworking.post(Contact.httpaddress.concat("/index.php/api/cart/orderCreate"))
                .addBodyParameter("order", obj.toString())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.d("商品提交订单json："+response);
                        parseJson(response, String.valueOf(zongjiaDouble), getString(R.string.zhongyaojixieshangping));
//                        new CommomDialog(QueRenDingDanActivity.this, R.style.dialog, response.toString(), new CommomDialog.OnCloseListener() {
//                            @Override
//                            public void onClick(Dialog dialog, boolean confirm) {
//                                dialog.dismiss();
//                            }
//                        }).setHideCancelButton().setTitle("服务器回调").show();
                    }

                    @Override
                    public void onError(ANError anError) {
                        new CommomDialog(QueRenDingDanActivity.this, R.style.dialog, anError.getErrorCode() + "", new CommomDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                dialog.dismiss();
                            }
                        }).setHideCancelButton().setTitle("服务器错误").show();
                    }
                });

    }

    private void submitData() {
        if (addressBean == null) {
            Toast.makeText(this, getString(R.string.qingxuanze) + getString(R.string.shouhuodizhi), Toast.LENGTH_SHORT).show();
            return;
        }

        List<GouWuCheBean> data = adapter.getData();
        final GouWuCheBean gouWuCheBean = data.get(0);
        final ShoppingBean shoppingBean = gouWuCheBean.getShoppingBeanList().get(0);

        AndroidNetworking.post(Contact.tijiaodingdan)
                .addBodyParameter("shop_id", gouWuCheBean.getShop_id())
                .addBodyParameter("shop_name", gouWuCheBean.getShop_name())
                .addBodyParameter("uid", MyApplication.getIntance().userBean.getUid())
                .addBodyParameter("buyer_message", gouWuCheBean.getLiuyan() + "")
                .addBodyParameter("receiver_mobile", addressBean.getMobile())
                .addBodyParameter("receiver_province", addressBean.getSheng())
                .addBodyParameter("receiver_city", addressBean.getShi())
                .addBodyParameter("receiver_address", addressBean.getAddress())
                .addBodyParameter("receiver_name", addressBean.getName())
                .addBodyParameter("goods_money", String.valueOf(gouWuCheBean.getPrice()))
                .addBodyParameter("goods_model", shoppingBean.getModel() + "")
                .addBodyParameter("goods_num", String.valueOf(shoppingBean.getNumber()))
                .addBodyParameter("price", String.valueOf(shoppingBean.getPrice()))
                .addBodyParameter("goods_id", shoppingBean.getShop_id())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        Logger.d("确认订单:"+response);
                        parseJson(response, String.valueOf(gouWuCheBean.getPrice()), shoppingBean.getName());
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("huangrui","提交失败信息"+anError.getResponse());
                        dismissLoadingDialog();
                        Toast.makeText(QueRenDingDanActivity.this, getString(R.string.dingdantijiaoshibai_wangluo), Toast.LENGTH_SHORT).show();
                    }
                });
        showLoadingDialog();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {
            return;
        }
        switch (resultCode) {
            case AddressActivity.ACTIVITY_RESULT_CODE:
                AddressBean addressBean = (AddressBean) data.getSerializableExtra(AddressActivity.ADDRESS_VALUE);
                dealAddress(addressBean);
                break;
        }
    }

    private void dealAddress(AddressBean addressBean) {
        if (addressBean != null) {
            addressContainer.setVisibility(View.VISIBLE);
            addressWu.setVisibility(View.GONE);
            setData(addressBean);
        } else {
            addressContainer.setVisibility(View.GONE);
            addressWu.setVisibility(View.VISIBLE);
        }
    }

    private void setData(AddressBean addressBean) {
        this.addressBean = addressBean;
        nameTv.setText(getString(R.string.shouhuoren) + ":" + addressBean.getName());
        callTv.setText(getString(R.string.lianxidianhua) + ":" + addressBean.getMobile());
        addressTv.setText(getString(R.string.shouhuodizhi) + ":" + addressBean.getAddress());
        UserBean userBean = MyApplication.getIntance().userBean;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        GouWuCheBean item = adapter.getItem(position);
//        shangpinid = item.getShop_id();
//        Toast.makeText(getApplicationContext(), "商品id:" + shangpinid, Toast.LENGTH_SHORT).show();

    }

    private void getDefaultAddress() {
        if (!MyApplication.getIntance().isLogin()) {
            Toast.makeText(this, getString(R.string.weidenglu), Toast.LENGTH_SHORT).show();
            return;
        }
        AndroidNetworking.get(Contact.address_default_get + "?uid=" + MyApplication.getIntance().userBean.getUid())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            List<AddressBean> defaultAddresslist = JsonUtils.parseAddressJson(response);
                            if (defaultAddresslist != null) {
                                for (AddressBean b : defaultAddresslist) {
                                    addressBean = b;
                                }
                                dealAddress(addressBean);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });
    }


    private List<GouWuCheBean> parsejson(String stringExtra) {
        List<GouWuCheBean> list = null;
//        "event": "",
//                "logo": "这是图片地址",
//                "seller_name": "灰灰大魔王的商店",
//                "seller_id": "11111",
//                "price": "100.0",
//                "number": "1",
//                "post_id": "10",
//                "pro_name": "商品0",
//                "pro_model": "型号11"
        try {
            JSONObject obj = new JSONObject(stringExtra);
            list = new ArrayList<>();
            GouWuCheBean bean = new GouWuCheBean();
            String shangjiaName = obj.getString("seller_name");
            String shangjiaLogo = obj.getString("logo");
            String shangjiaId = obj.getString("seller_id");
            bean.setShop_name(shangjiaName);
            bean.setPic_url(shangjiaLogo);
            bean.setShop_id(shangjiaId);
            List<ShoppingBean> shopbeanList = new ArrayList<>();
            ShoppingBean shopBean = new ShoppingBean();
            String pro_name = obj.getString("pro_name");
            String pro_model = obj.getString("pro_model");
            String pro_pic = obj.getString("pro_pic");
            String post_id = obj.getString("post_id");
            if (obj.has("yunfei")) {
                double yunfei = obj.getDouble("yunfei");
                shopBean.setYunfei(yunfei);
            }
            int number = obj.getInt("number");
            double price = obj.getDouble("price");
            shopBean.setNumber(number);
            shopBean.setName(pro_name);
            shopBean.setModel(pro_model);
            shopBean.setIv_pic(pro_pic);
            shopBean.setShop_id(post_id);
            shopBean.setPrice(price);
            shopbeanList.add(shopBean);
            bean.setShoppingBeanList(shopbeanList);
            list.add(bean);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    String no;

    /**
     * onResume方法.
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("huangrui","onresume：" );
        //银联支付的回调
        if (Utils.getResultInfo() != null) {
            ResultInfo resultInfo = Utils.getResultInfo();
            Logger.d("银联resultInfo:"+resultInfo.orderInfo);
            ToastUtils.showShort(resultInfo.getRespDesc());
//            if (resultInfo.getRespCode() != null && !resultInfo.getRespCode().equals("")) {
//                if (resultInfo.getRespCode().equals("0000")) {
//                    String orderInfo = resultInfo.getOrderInfo();
//                    if(orderInfo != null){
//                        Utils.showDialogNoFinish(this, "应答码："+resultInfo.getRespCode() + "\n应答描述:" + resultInfo.getRespDesc()+ "\n详细结果：" + orderInfo);}
//                } else {
//                    Utils.showDialogNoFinish(this,
//                            "应答码："+resultInfo.getRespCode() + "\n应答描述:" + resultInfo.getRespDesc());
//                }
//            }
        }	CPGlobalInfo.init();
    }

    private void parseJson(JSONObject response, final String price, final String goodsName) {
        try {
            if (JsonUtils.isSuccess(response)) {
                JSONObject data = response.getJSONObject("data");
                no = data.getString("niu_index_response");

                ChargePopWindow chargePopWindow = new ChargePopWindow(isGouWUChe, this, false,
                        no,price, goodsName);
                chargePopWindow.setOrder_no(no);
                chargePopWindow.show();
//                chargePopWindow.setOrder_no(item.getOrder_no());

//                PoPuWindowUtils.getIntance().showSelecterPayType(this, convertView, new PoPuWindowUtils.selecterPayTypeListener() {
//                    @Override
//                    public void onAlipay() {
//
//                    }
//
//                    @Override
//                    public void onWechat() {
//
//                    }
//
//                    @Override
//                    public void onYinlian() {
//
//                    }
//
//                    @Override
//                    public void onZhuanzhang() {
//
//                    }
//
//                    @Override
//                    public void onSmallDaikuan() {
//                        ToastUtils.showShort(R.string.No_opening);
//                    }
////                    @Override
////                    public void onZaiXianZhifu() {
////                        Intent intent = new Intent(QueRenDingDanActivity.this, ZaiXianZhiFuActivity.class);
////                        intent.putExtra(ZaiXianZhiFuActivity.OUT_TRADE_NO_DATA, no);
////                        intent.putExtra(ZaiXianZhiFuActivity.IS_CAR_SHOPIING, isGouWUChe);
////                        intent.putExtra(ZaiXianZhiFuActivity.PRICE_DATA, price);
////                        intent.putExtra(ZaiXianZhiFuActivity.GOODSNAME_DATA, goodsName);
////                        startActivity(intent);
////                        finish();
////                        PoPuWindowUtils.getIntance().dismissPopuWindow();
////                    }
////
////                    @Override
////                    public void onDuiGongFuKuan() {
////                        Intent intent = new Intent(QueRenDingDanActivity.this, DuiGongFuKuanActivity.class);
////                        startActivity(intent);
////                        finish();
////                        PoPuWindowUtils.getIntance().dismissPopuWindow();
////                    }
//                });

            } else {
                Toast.makeText(this, getString(R.string.dingdantijiaoshibai), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, getString(R.string.dingdantijiaoshibai), Toast.LENGTH_SHORT).show();
        }
    }


    //微信支付的回调
    //在ui线程执行
    @Subscribe
    public void onWeChatCharge(WXPayEntryActivity.wxPayResult payResultResult) {
        Logger.d("收到了微信支付的回调"+payResultResult);
        if (this == null)
            return;
        switch (payResultResult) {
            case success:
//                submit();
                ToastUtils.showShort("支付成功");
                break;
            case cancle:
                ToastUtils.showShort("取消支付");
                break;
            case fail:
                ToastUtils.showShort("支付错误");
                break;
            case error:
                ToastUtils.showShort("支付错误");
                break;
            default:
                break;
        }
    }


    @Subscribe
    public void onZhifubaoCharge(PayBean payBean){
        Logger.d("收到支付宝支付成功的回调");
        //支付宝支付成功
//        submit();
    }


}
