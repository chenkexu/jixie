package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.ShoppingSpinnerListAdapter;
import com.qmwl.zyjx.adapter.ShoppingSpinnerPeiJianListAdapter;
import com.qmwl.zyjx.adapter.ShoppingSpinnerPinpaiAdapter;
import com.qmwl.zyjx.adapter.ShoppingThreadAdapter;
import com.qmwl.zyjx.adapter.SpinnerZongHeAdapter;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.BlackBean;
import com.qmwl.zyjx.bean.ShaiXuanSpinnerBean;
import com.qmwl.zyjx.bean.ShoppingBean;
import com.qmwl.zyjx.bean.SpinnerZongHeBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.utils.ListViewPullListener;
import com.qmwl.zyjx.view.GridViewWithHeaderAndFooter;
import com.qmwl.zyjx.view.ShaiXuanItemLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/3.
 * 配件商品页面
 */

public class ShoppingPeiJianThreadActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener, AdapterView.OnItemClickListener {

    public static final String SEARCH_KEY = "com.gh.iskey";
    private String searchKey = "";//模糊搜索的
    private boolean isKey = false;//是否是搜索过来的

    private View zongheContainer;
    private CheckBox zongheButton;//综合筛选按钮
    private CheckBox pinpaiButton;//品牌筛选按钮
    private CheckBox diquButton;//地区筛选按钮
    private CheckBox shaixuanButton;//筛选按钮
    private PopupWindow zonghePopupWindow;
    private PopupWindow pinpaiPopupWindow;//品牌popupWindow
    private PopupWindow diquPopupWindow;//品牌popupWindow
    private PopupWindow shaixuanPopupWindow;
    private PopupWindowdismissListener popuDismissListener;//popuWindow的dismiss监听，只要触发，就重置所有按钮的状态
    private boolean ifOnlyPopupWindowShow = false;//是否刚刚有一个popuWindow消失
    private ShoppingThreadAdapter adapter;
    private ListViewPullListener listViewPullListener;
    private int page = 1;
    private boolean isLoadMore;

    private String radioStr = "/index";
    private static final String radio_qunabu = "/index";
    private static final String radio_xinji = "/index_1";
    private static final String radio_ershouji = "/index_2";
    private static final String radio_peijian = "/index_4";
    private static final String radio_zulin = "/index_3";
    private String name;
    private String id;
    ShoppingSpinnerListAdapter pinpaiAdapter;
    ShoppingSpinnerPinpaiAdapter pinpaiGridViewAdapter;
    ShoppingSpinnerPeiJianListAdapter diquAdapter;
    SpinnerZongHeAdapter zongheAdapter;
    //筛选框
    private LinearLayout shaixuanContainer;
    private String shaixuanTiaoJian = "";


    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            ifOnlyPopupWindowShow = false;
        }
    };

    private List<ShoppingBean> allList = null;
    private List<ShoppingBean> childList = null;
    private GridViewWithHeaderAndFooter gridView;
    private TextView quanbudiquTv;
    private TextView diquFanhui;
    private ListView diquListview;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.shopping_thread_activity);
    }

    @Override
    protected void initView() {
        searchKey = getIntent().getStringExtra(SEARCH_KEY);
        if ("".equals(searchKey) || searchKey == null) {
            isKey = false;
        } else {
            isKey = true;
        }
        if (!isKey) {
            if (TextUtils.isEmpty(name) || "".equals(name)) {
                name = getIntent().getStringExtra(ShoppingSecondActivity.Category_name);
                setTitleContent(name);
                id = getIntent().getStringExtra(ShoppingSecondActivity.Category_id);
            }
        }

        zongheContainer = findViewById(R.id.shopping_thread_zonghe_container);
        zongheButton = (CheckBox) findViewById(R.id.shopping_thread_zonghe);
        shaixuanButton = (CheckBox) findViewById(R.id.shopping_thread_shaixuan);
        pinpaiButton = (CheckBox) findViewById(R.id.shopping_thread_pinpai);
        diquButton = (CheckBox) findViewById(R.id.shopping_thread_diqu);
        gridView = (GridViewWithHeaderAndFooter) findViewById(R.id.shopping_thread_gridview);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.shopping_thread_swiperefreshlayout);
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.shopping_thread_radiobutton_container);


        listViewPullListener = new ListViewPullListener(gridView, swipeRefreshLayout, new ListViewPullListener.ListViewPullOrLoadMoreListener() {
            @Override
            public void onPullRefresh() {
                listViewPullListener.openLoadMore();
                page = 1;
                getData();
            }

            @Override
            public void onLoadMore() {
                if (!isLoadMore) {
                    isLoadMore = true;
                    page++;
                    getData();
                }
            }
        });
        radioGroup.setOnCheckedChangeListener(this);
        zongheButton.setOnClickListener(this);
        pinpaiButton.setOnClickListener(this);
        diquButton.setOnClickListener(this);
        shaixuanButton.setOnClickListener(this);

        adapter = new ShoppingThreadAdapter();
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

        popuDismissListener = new PopupWindowdismissListener();
        showShaiXuanPopuWindow(false);
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {
        getData();
    }

    private void getData() {
        if (!isKey) {
            getIdData();
        } else {
            searchKey(searchKey);
        }
    }

    private void getIdData() {
        getShaiXuanData();
//        radioStr = "/index";
        String url = Contact.thread_shops + radioStr;
        String shaixuan = shaixuanId.toString().trim();
        ANRequest.PostRequestBuilder postRequestBuilder = AndroidNetworking.post(url)
//        AndroidNetworking.post(url)
                .addBodyParameter("category_id", id)
                .addBodyParameter("page", String.valueOf(page))
                .addBodyParameter("brand", pinpaiId)
                .addBodyParameter("order", zongheId)
                .addBodyParameter("attr", shaixuan);

        if (!"-1".equals(quId)) {
            postRequestBuilder.addBodyParameter("district", quId);
        } else if (!"-1".equals(shiId)) {
            postRequestBuilder.addBodyParameter("city", shiId);
        } else if (!"-1".equals(shengId)) {
            postRequestBuilder.addBodyParameter("place", shengId);
        }
        postRequestBuilder.build()
                .getAsJSONObject(jsonobjRequestListener);
        if (!isLoadMore) {
            showLoadingDialog();
        }
    }

    //结果回调
    private JSONObjectRequestListener jsonobjRequestListener = new JSONObjectRequestListener() {
        @Override
        public void onResponse(JSONObject response) {
            Log.i("TAG", "商品列表:" + response.toString());
            dismissLoadingDialog();
            listViewPullListener.cancelPullRefresh();
            try {
                allList = JsonUtils.parseShoppingJson(response);
                if (allList == null) {
                    if (!isLoadMore) {
                        Toast.makeText(ShoppingPeiJianThreadActivity.this, getString(R.string.wucifenlei), Toast.LENGTH_SHORT).show();
                        adapter.setData(allList);
                    }
                } else {
                    if (allList.size() == 0) {
                        if (!isLoadMore) {
                            Toast.makeText(ShoppingPeiJianThreadActivity.this, getString(R.string.wucifenlei), Toast.LENGTH_SHORT).show();
                            adapter.setData(allList);
                        }
                    }
                }
                if (isLoadMore) {
                    isLoadMore = false;
                    if (allList != null && allList.size() > 0) {
                        adapter.addData(allList);
                    } else {
                        listViewPullListener.cancelLoadMore();
                    }
                } else {
                    adapter.setData(allList);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                if (isLoadMore) {
                    isLoadMore = false;
                }
                Toast.makeText(ShoppingPeiJianThreadActivity.this, getString(R.string.wucifenlei), Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(ANError anError) {
            dismissLoadingDialog();
            listViewPullListener.cancelPullRefresh();
            Toast.makeText(ShoppingPeiJianThreadActivity.this, getString(R.string.wucifenlei), Toast.LENGTH_SHORT).show();
            if (isLoadMore) {
                isLoadMore = false;
                page--;
                if (page < 1) {
                    page = 1;
                }
            }
        }
    };

    private void searchKey(String key) {
        setTitleContent(key);
        AndroidNetworking.get(Contact.search_search + "?goods_name=" + key + "&page=" + page)
//        AndroidNetworking.post(Contact.search_search)
//                .addBodyParameter("goods_name", key)
//                .addBodyParameter("page", String.valueOf(page))
//                .addPathParameter("goods_name", key)
                .build()
                .getAsJSONObject(jsonobjRequestListener);
    }

    @Override
    protected void onMyClick(View v) {
        switch (v.getId()) {
            case R.id.shopping_thread_zonghe:
                if (!ifOnlyPopupWindowShow) {
                    showZonghePopuWindow();
                } else {
                    resetButtonStatue();
                }

                break;

            case R.id.shopping_thread_pinpai:
                if (!ifOnlyPopupWindowShow) {
                    showPinPaiPopuWindow();
                } else {
                    resetButtonStatue();
                }

                break;
            case R.id.shopping_thread_diqu:
                if (!ifOnlyPopupWindowShow) {
                    showDiQuPopuWindow();
                } else {
                    resetButtonStatue();
                }
                break;
            case R.id.shopping_thread_shaixuan:
                showShaiXuanPopuWindow();
                break;
            case R.id.shopping_thread_shaixuan_queren:
                String value = "";
                for (int i = 0; i < shaixuanContainer.getChildCount(); i++) {
                    ShaiXuanItemLayout itemLayout = (ShaiXuanItemLayout) shaixuanContainer.getChildAt(i);
                    if (i == 0) {
                        value = itemLayout.getValue();
                    } else {
                        if (!"".equals(itemLayout.getValue())) {
                            value += "," + itemLayout.getValue();
                        }
                    }
                }
                dissmissShaiXuanPopuWindow();
                shaixuanId = value;
//                getIdData();
                getData();
                break;
            case R.id.shopping_thread_shaixuan_chongzhi:
                for (int i = 0; i < shaixuanContainer.getChildCount(); i++) {
                    ShaiXuanItemLayout itemLayout = (ShaiXuanItemLayout) shaixuanContainer.getChildAt(i);
                    itemLayout.reset();
                }

                break;
            case R.id.shopping_thread_shaixuan_close:
                dissmissShaiXuanPopuWindow();
                break;
            case R.id.black_layout_item_name:
                //全部地区/全省/市
                dealAllDiQu();
                break;
            case R.id.black_layout_item_cancel:
                //地区列表的返回按钮
                dealQuXiao();
                break;
        }
    }

    private void resetButtonStatue() {
        ifButtonStatue(zongheId, zongheButton);
        ifButtonStatue(pinpaiId, pinpaiButton);
        ifButtonStatue(shengId, diquButton);
        ifButtonStatue(shaixuanId, shaixuanButton);
    }

    private void ifButtonStatue(String keyId, CheckBox button) {
        if ("".equals(keyId)) {
            button.setChecked(false);
        } else {
            button.setChecked(true);
        }
    }


    //显示综合筛选的popuWindow
    private void showZonghePopuWindow() {
        if (zonghePopupWindow == null) {
            View popuView = getLayoutInflater().inflate(R.layout.shopping_zonghe_popu_layout, null);
            ListView diquListview = (ListView) popuView.findViewById(R.id.shopping_spinner_diqu_listview);
            diquListview.setOnItemClickListener(this);
            initZongHeListViewData(diquListview);
            zonghePopupWindow = new PopupWindow(popuView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            zonghePopupWindow.setAnimationStyle(R.style.take_photo_anim);
            // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框。不知道是什么原因
            zonghePopupWindow.setBackgroundDrawable(new BitmapDrawable());
            // 设置点击窗口外边窗口消失
            zonghePopupWindow.setOutsideTouchable(true);
            zonghePopupWindow.setFocusable(true);
            zonghePopupWindow.setOnDismissListener(popuDismissListener);
        }
        zonghePopupWindow.showAsDropDown(zongheContainer, 0, 20);
        zongheAdapter.notifyDataSetChanged();
    }

    private String zongheId = "";//筛选条件:综合
    private String pinpaiId = "";//筛选条件:品牌
    private String shengId = "-1";//筛选条件:省
    private String shiId = "-1";//筛选条件:市地
    private String quId = "-1";//筛选条件:区县
    private String shaixuanId = "";//筛选条件:筛选

    //0代表选择省，1代表选择市，2代表选择县,3代表选择出区县了
    private int diquCode = 0;

    //初始化综合框的数据
    private void initZongHeListViewData(ListView diquListview) {
        zongheAdapter = new SpinnerZongHeAdapter();
        diquListview.setAdapter(zongheAdapter);
        String[] stringArray = getResources().getStringArray(R.array.shaixuan_zonghe_array);
        List<SpinnerZongHeBean> diquStrings = new ArrayList<>();
        for (int i = 0; i < stringArray.length; i++) {
            SpinnerZongHeBean bean = new SpinnerZongHeBean();
            bean.setName(stringArray[i]);
            bean.setId(i);
            diquStrings.add(bean);
        }
        zongheAdapter.setData(diquStrings);
    }

    private void dissmissZongHePopuWindow() {
        if (zonghePopupWindow != null && zonghePopupWindow.isShowing()) {
            zonghePopupWindow.dismiss();
        }
    }

    private void dissmissPinPaiPopuWindow() {
        if (pinpaiPopupWindow != null && pinpaiPopupWindow.isShowing()) {
            pinpaiPopupWindow.dismiss();
        }
    }


    private void dissmissDiQuPopuWindow() {
        if (diquPopupWindow != null && diquPopupWindow.isShowing()) {
            diquPopupWindow.dismiss();
        }
    }

    private void dissmissShaiXuanPopuWindow() {
        if (shaixuanPopupWindow != null && shaixuanPopupWindow.isShowing()) {
            shaixuanPopupWindow.dismiss();
        }
    }

    //显示品牌的popuWindow
    private void showPinPaiPopuWindow() {
        if (pinpaiPopupWindow == null) {
            View popuView = getLayoutInflater().inflate(R.layout.shopping_spinner_pinpai_layout, null);
//            View headView = getLayoutInflater().inflate(R.layout.shopping_spinner_pinpaiordiqu_item_head, null);
//            GridView headGridView = (GridView) headView.findViewById(R.id.shopping_spinner_pinpai_item_head_gridview);
            ListView pinpaiListview = (ListView) popuView.findViewById(R.id.shopping_spinner_pinpai_listview);
//            headGridView.setOnItemClickListener(this);
            pinpaiListview.setOnItemClickListener(this);
//            pinpaiListview.addHeaderView(headView);
//            initPinPaiListViewData(pinpaiListview, headGridView);
            initPinPaiListViewData(pinpaiListview, null);
            int[] index = new int[2];
            zongheContainer.getLocationOnScreen(index);
            pinpaiPopupWindow = new PopupWindow(popuView, getWidth(), getHeight() - index[1] - zongheContainer.getHeight());
//            pinpaiPopupWindow = new PopupWindow(popuView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            pinpaiPopupWindow.setAnimationStyle(R.style.take_photo_anim);
            // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框。不知道是什么原因
            pinpaiPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            // 设置点击窗口外边窗口消失
            pinpaiPopupWindow.setOutsideTouchable(true);
            pinpaiPopupWindow.setFocusable(true);
            pinpaiPopupWindow.setOnDismissListener(popuDismissListener);
        }
        pinpaiPopupWindow.showAsDropDown(zongheContainer, 0, 5);
    }

    //初始化品牌的Spinner数据
    private void initPinPaiListViewData(ListView diquListview, GridView headGridView) {
        getPinPaiDataList();
        pinpaiAdapter = new ShoppingSpinnerListAdapter(this);
        pinpaiGridViewAdapter = new ShoppingSpinnerPinpaiAdapter();
//        headGridView.setAdapter(pinpaiGridViewAdapter);
        diquListview.setAdapter(pinpaiAdapter);
    }

    //显示地区的popuWindow
    private void showDiQuPopuWindow() {
        if (diquPopupWindow == null) {
            View popuView = getLayoutInflater().inflate(R.layout.shaixuan_diqu_listview, null);
            diquListview = (ListView) popuView.findViewById(R.id.shopping_spinner_diqu_listview_vv);
            View diquHeadView = getLayoutInflater().inflate(R.layout.shopping_spinner_pinpaiordiqu_item, gridView, false);
            quanbudiquTv = (TextView) diquHeadView.findViewById(R.id.black_layout_item_name);
            int color = ContextCompat.getColor(this, R.color.window_system_color);
            quanbudiquTv.setTextColor(color);
            quanbudiquTv.setOnClickListener(this);
            diquHeadView.findViewById(R.id.black_layout_item_duihao).setVisibility(View.GONE);
            diquFanhui = (TextView) diquHeadView.findViewById(R.id.black_layout_item_cancel);
            diquFanhui.setVisibility(View.VISIBLE);
            diquFanhui.setTextColor(color);
            diquFanhui.setOnClickListener(this);
            quanbudiquTv.setText(R.string.quanbudiqu);
            diquListview.addHeaderView(diquHeadView);
            initDiQuListViewData(diquListview);
            int[] index = new int[2];
            zongheContainer.getLocationOnScreen(index);
            diquPopupWindow = new PopupWindow(popuView, getWidth(), getHeight() - index[1] - zongheContainer.getHeight());
//            pinpaiPopupWindow = new PopupWindow(popuView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            diquPopupWindow.setAnimationStyle(R.style.take_photo_anim);
            // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框。不知道是什么原因
            diquPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            // 设置点击窗口外边窗口消失
            diquPopupWindow.setOutsideTouchable(true);
            diquPopupWindow.setFocusable(true);
            diquPopupWindow.setOnDismissListener(popuDismissListener);
        }
        diquPopupWindow.showAsDropDown(zongheContainer, 0, 20);
    }

    //点击地区的item方法
    private void dealDiQuItem(int position) {
        if (position == 0) {
            return;
        }
        BlackBean diqubean = diquAdapter.getItem(position - 1);
        if (diqubean == null) {
            return;
        }
        if (diquCode == 0) {
            diquCode = 1;
            shengId = diqubean.getId();
            getShiData();
        } else if (diquCode == 1) {
            diquCode = 2;
            shiId = diqubean.getId();
            getQuXianData();
        } else if (diquCode == 2) {
            diquCode = 2;
            quId = diqubean.getId();
            dissmissDiQuPopuWindow();
            getData();
        }
    }

    //返回地区code
    public String getQiQuCode() {
        String id = "-1";
        switch (diquCode) {
            case 0:
                id = shengId;
                break;
            case 1:
                id = shiId;
                break;
            case 2:
                id = quId;
                break;
        }
        return id;
    }


    private void dealAllDiQu() {
        if (diquCode == 0) {
            //全部地区
            shengId = "-1";
            shiId = "-1";
            quId = "-1";
        } else if (diquCode == 1) {
            //全省
            shiId = "-1";
            quId = "-1";
        } else if (diquCode == 2) {
            //全市
            quId = "-1";
        }
        getData();
        dissmissDiQuPopuWindow();
    }

    private void dealQuXiao() {
        if (diquCode == 2) {
            //当前是区县，回退到市
            diquCode = 1;
            getShiData();
        } else if (diquCode == 1) {
            //当前是市,回退到省
            getShengData();
            diquCode = 0;
        }
    }

    //初始化地区的数据
    private void initDiQuListViewData(ListView diquListview) {
        diquAdapter = new ShoppingSpinnerPeiJianListAdapter(true, this);
        diquListview.setAdapter(diquAdapter);
        diquListview.setOnItemClickListener(this);
        getShengData();
    }

    //筛选按钮
    private void showShaiXuanPopuWindow() {
        if (shaixuanPopupWindow == null) {
            View popuView = getLayoutInflater().inflate(R.layout.shopping_shaixuan_popu_layout, null);
            initShaiXuanPopup(popuView);
            shaixuanPopupWindow = new PopupWindow(popuView, getWidth() - 200, getHeight() - getStatusBarHeight(this));
            shaixuanPopupWindow.setAnimationStyle(R.style.shaixuan_take_photo_anim);
            // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框。不知道是什么原因
            shaixuanPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            // 设置点击窗口外边窗口消失
            shaixuanPopupWindow.setOutsideTouchable(true);
            shaixuanPopupWindow.setFocusable(true);
            shaixuanPopupWindow.setOnDismissListener(popuDismissListener);
        }
        shaixuanPopupWindow.showAtLocation(getContainerView(), Gravity.LEFT, 200, 300);
    }

    //筛选按钮
    private void showShaiXuanPopuWindow(boolean b) {
        if (shaixuanPopupWindow == null) {
            View popuView = getLayoutInflater().inflate(R.layout.shopping_shaixuan_popu_layout, null);
            initShaiXuanPopup(popuView);
            shaixuanPopupWindow = new PopupWindow(popuView, getWidth() - 200, getHeight() - getStatusBarHeight(this));
            shaixuanPopupWindow.setAnimationStyle(R.style.shaixuan_take_photo_anim);
            // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框。不知道是什么原因
            shaixuanPopupWindow.setBackgroundDrawable(new BitmapDrawable());
            // 设置点击窗口外边窗口消失
            shaixuanPopupWindow.setOutsideTouchable(true);
            shaixuanPopupWindow.setFocusable(true);
            shaixuanPopupWindow.setOnDismissListener(popuDismissListener);
        }
//        shaixuanPopupWindow.showAtLocation(getContainerView(), Gravity.LEFT, 200, 300);
    }


    private void initShaiXuanPopup(View popuView) {
        shaixuanContainer = (LinearLayout) popuView.findViewById(R.id.shopping_thread_shaixuan_container);
        popuView.findViewById(R.id.shopping_thread_shaixuan_queren).setOnClickListener(this);
        popuView.findViewById(R.id.shopping_thread_shaixuan_chongzhi).setOnClickListener(this);
        popuView.findViewById(R.id.shopping_thread_shaixuan_close).setOnClickListener(this);
//        ShaiXuanItemLayout shaiXuanItemLayout = new ShaiXuanItemLayout(this);
//        shaixuanContainer.addView(shaiXuanItemLayout);
//        shaixuanContainer.addView(new ShaiXuanItemLayout(this));
//        shaixuanContainer.addView(new ShaiXuanItemLayout(this));
        getShaiXuanData();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        childShaiXuan(checkedId);
    }

    private void childShaiXuan(int id) {
//        if (allList == null) {
//            Toast.makeText(this, getString(R.string.wucifenlei), Toast.LENGTH_SHORT).show();
//            return;
//        }
        if (isKey && allList == null) {
            return;
        }
        if (childList == null) {
            childList = new ArrayList<>();
//            return;
        } else {
            childList.clear();
        }
        switch (id) {
            case R.id.shopping_thread_radiobutton_quanbu:
                if (isKey) {
                    adapter.setData(allList);
                } else {
                    radioStr = radio_qunabu;
                    page = 1;
                    shaixuanTiaoJian = "";
                    shaixuanId = "";
                    getIdData();
                }
                break;
            case R.id.shopping_thread_radiobutton_xinji:
                if (isKey) {
                    for (int i = 0; i < allList.size(); i++) {
                        ShoppingBean shoppingBean = allList.get(i);
                        if (shoppingBean.getIsNew() == 1) {
                            childList.add(shoppingBean);
                        }
                    }
                } else {
                    radioStr = radio_xinji;
                    page = 1;
                    shaixuanTiaoJian = "1";
                    shaixuanId = "";
                    getIdData();
                }
                break;
            case R.id.shopping_thread_radiobutton_ershouji:
                if (isKey) {
                    for (int i = 0; i < allList.size(); i++) {
                        ShoppingBean shoppingBean = allList.get(i);
                        if (shoppingBean.getIsNew() == 0) {
                            childList.add(shoppingBean);
                        }
                    }
                    adapter.setData(childList);
                } else {
                    radioStr = radio_ershouji;
                    page = 1;
                    shaixuanTiaoJian = "2";
                    shaixuanId = "";
                    getIdData();
                }
                break;
            case R.id.shopping_thread_radiobutton_peijian:
                if (isKey) {
                    for (int i = 0; i < allList.size(); i++) {
                        ShoppingBean shoppingBean = allList.get(i);
                        if (shoppingBean.getIs_parts() == 1) {
                            childList.add(shoppingBean);
                        }
                    }
                    adapter.setData(childList);
                } else {
                    radioStr = radio_peijian;
                    page = 1;
                    shaixuanTiaoJian = "3";
                    shaixuanId = "";
                    getIdData();
                }
                break;
            case R.id.shopping_thread_radiobutton_zulin:
                if (isKey) {
                    for (int i = 0; i < allList.size(); i++) {
                        ShoppingBean shoppingBean = allList.get(i);
                        if (shoppingBean.getIs_lease() == 1) {
                            childList.add(shoppingBean);
                        }
                    }
                    adapter.setData(childList);
                } else {
                    radioStr = radio_zulin;
                    page = 1;
                    shaixuanTiaoJian = "";
                    shaixuanId = "";
                    getIdData();
                }
                break;
        }
//        if (id == R.id.shopping_thread_radiobutton_quanbu) {
//            adapter.setData(allList);
//        } else if (childList.size() == 0) {
//            adapter.setData(childList);
//        } else {
//            adapter.setData(childList);
//        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.shopping_thread_gridview:
                String uid = "";
                if (MyApplication.getIntance().isLogin()) {
                    uid = MyApplication.getIntance().userBean.getUid();
                }
                Intent intent = new Intent(this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.isGOUWUCHE, true);
                intent.putExtra(WebViewActivity.SHOPURL, adapter.getItem(position).getUrl() + "&uid=" + uid);
                startActivity(intent);
                break;

            case R.id.shopping_spinner_diqu_listview:
                //综合的筛选
                if (zongheAdapter != null) {
                    SpinnerZongHeBean item = zongheAdapter.getItem(position);
                    zongheId = item.getId() + "";
                    List<SpinnerZongHeBean> data = zongheAdapter.getData();
                    for (SpinnerZongHeBean bean : data) {
                        bean.setSelecter(false);
                    }
                    item.setSelecter(true);
                    zongheAdapter.notifyDataSetChanged();
                    dissmissZongHePopuWindow();
                    getData();
                }
                break;

            case R.id.shopping_spinner_pinpai_item_head_gridview:
                //gridView 品牌
//                BlackBean item = pinpaiGridViewAdapter.getItem(position);
//                pinpaiId = item.getId();
//                for (BlackBean b : pinpaiAdapter.getData()) {
//                    b.setSelecter(false);
//                }
//                dissmissPinPaiPopuWindow();
//                getData();
                break;

            case R.id.shopping_spinner_pinpai_listview:
                //listView 品牌

                BlackBean listbean = pinpaiAdapter.getItem(position);
                pinpaiId = listbean.getId();
                for (BlackBean b : pinpaiAdapter.getData()) {
                    b.setSelecter(false);
                }
                listbean.setSelecter(true);
                pinpaiAdapter.notifyDataSetChanged();
                dissmissPinPaiPopuWindow();
                getData();
                break;

            case R.id.shopping_spinner_diqu_listview_vv:
                dealDiQuItem(position);
//                try {
//                    BlackBean diqubean = diquAdapter.getItem(position - 1);
//                    if (diqubean == null) {
//                        return;
//                    }
//                    shengId = diqubean.getId();
////                    for (BlackBean b : diquAdapter.getData()) {
////                        b.setSelecter(false);
////                    }
////                    diqubean.setSelecter(true);
////                    diquAdapter.notifyDataSetChanged();
////                    dissmissDiQuPopuWindow();
////                    getData();
//
//                } catch (Exception e) {
//
//                }
                break;
        }
    }

    //所有筛选弹窗的消失监听,重置所有按钮的状态
    class PopupWindowdismissListener implements PopupWindow.OnDismissListener {

        @Override
        public void onDismiss() {
            resetButtonStatue();
            ifOnlyPopupWindowShow = true;
            mHandler.sendEmptyMessageDelayed(1, 200);
        }
    }

    private void getPinPaiDataList() {
        AndroidNetworking.get(Contact.getpinpai_list + "?category_id=" + id)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("TAG", "品牌:" + response.toString());
                        List<BlackBean> list = JsonUtils.parseSpinnerPinPai(response);
                        if (list != null) {
                            pinpaiAdapter.setData(list);
                            List<BlackBean> gridBeans = new ArrayList<BlackBean>();
                            for (BlackBean bean : list) {
                                if (bean.getBrand_recommend() == 1) {
                                    gridBeans.add(bean);
                                }
                            }
                            pinpaiGridViewAdapter.setData(gridBeans);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    private void getShengData() {
        showLoadingDialog();
        AndroidNetworking.post(Contact.getsheng)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("TAG", "省列表:" + response.toString());
                        dismissLoadingDialog();
                        List<BlackBean> list = JsonUtils.parseSheng(response);
                        diquAdapter.setData(list);
                        if (quanbudiquTv != null) {
                            quanbudiquTv.setText(R.string.quanbudiqu);
                        }
                        if (diquFanhui != null) {
                            diquFanhui.setVisibility(View.GONE);
                        }
                        if (diquListview != null) {
                            diquListview.smoothScrollToPosition(0);
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                    }
                });
    }

    //获取城市列表
    private void getShiData() {
        showLoadingDialog();
        AndroidNetworking.post(Contact.getshi)
                .addBodyParameter("province_id", shengId)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("TAG", "城市列表:" + response.toString());
                        dismissLoadingDialog();
                        List<BlackBean> list = JsonUtils.parseShi(response);
                        diquAdapter.setData(list);
                        if (quanbudiquTv != null) {
                            quanbudiquTv.setText(R.string.quansheng);
                        }
                        if (diquFanhui != null) {
                            diquFanhui.setVisibility(View.VISIBLE);
                        }
                        if (diquListview != null) {
                            diquListview.smoothScrollToPosition(0);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                    }
                });
    }

    //获取区县列表
    private void getQuXianData() {
        showLoadingDialog();
        AndroidNetworking.post(Contact.huoquxianliebiao)
//                .addBodyParameter("city_id", "108")
                .addBodyParameter("city_id", shiId)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("TAG", "区县列表:" + response.toString());
                        dismissLoadingDialog();
                        List<BlackBean> list = JsonUtils.parseXian(response);
                        if (list == null) {
                            return;
                        }
                        diquAdapter.setData(list);
                        if (quanbudiquTv != null) {
                            quanbudiquTv.setText(R.string.quanshi);
                        }
                        if (diquFanhui != null) {
                            diquFanhui.setVisibility(View.VISIBLE);
                        }
                        if (diquListview != null) {
                            diquListview.smoothScrollToPosition(0);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                    }
                });
    }

    private void getShaiXuanData() {
        showLoadingDialog();
        AndroidNetworking.post(Contact.getshaixuan_list)
                .addBodyParameter("category_id", id)
                .addBodyParameter("status",shaixuanTiaoJian)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        List<ShaiXuanSpinnerBean> shaiXuanSpinnerBeens = JsonUtils.parseShaiXuanShaiXuan(response);
                        if (shaiXuanSpinnerBeens != null && shaixuanContainer != null) {
                            shaixuanContainer.removeAllViews();
                            for (ShaiXuanSpinnerBean shaixuanBean : shaiXuanSpinnerBeens) {
                                ShaiXuanItemLayout shaiXuanItemLayout = new ShaiXuanItemLayout(ShoppingPeiJianThreadActivity.this);
                                shaiXuanItemLayout.setData(shaixuanBean);
                                shaixuanContainer.addView(shaiXuanItemLayout);
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                    }
                });
    }
}
