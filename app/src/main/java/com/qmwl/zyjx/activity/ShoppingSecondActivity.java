package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.ShoppingSecondAdapter;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.Flowbean;
import com.qmwl.zyjx.bean.ShoppSecondZongHeBean;
import com.qmwl.zyjx.bean.ShoppingSecondBean;
import com.qmwl.zyjx.fragment.FlowFragment;
import com.qmwl.zyjx.fragment.MainFragment;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.utils.ListViewPullListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/7.
 * 产品二级页面
 */

public class ShoppingSecondActivity extends BaseActivity implements AdapterView.OnItemClickListener, ListViewPullListener.ListViewPullOrLoadMoreListener {
    private ListView mLv;
    private View headerView;//头部文件
    private ShoppingSecondAdapter adapter;
    private FlowFragment flowFragment;
    private String shoppingtype_id;//商品id
    private String shoppingtype_name;//商品名称
    private ViewFlipper tieziTv;
    private ViewFlipper zixunTv;
    public static final String Category_id = "com.qmwl.zyjx.category_id";//id的名称
    public static final String Category_name = "com.qmwl.zyjx.category_name";//商品名的名称
    private ListViewPullListener listViewPullListener;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.shopping_second_layout);
    }

    @Override
    protected void initView() {
        shoppingtype_id = getIntent().getStringExtra(Category_id);
        shoppingtype_name = getIntent().getStringExtra(Category_name);
        setTitleContent(shoppingtype_name);
        mLv = (ListView) findViewById(R.id.second_activity_listview);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.second_activity_swipeRefreshlayout);
        headerView = LayoutInflater.from(this).inflate(R.layout.second_activity_header_layout, null);
//        headerView.findViewById(R.id.main_fragment_zhongyaozixun).setOnClickListener(this);


        tieziTv = (ViewFlipper) headerView.findViewById(R.id.second_activity_tiezi_tv);
        tieziTv.setOnClickListener(this);
        ImageView tieziIv = (ImageView) headerView.findViewById(R.id.second_activity_tiezi_iv);
        tieziIv.setOnClickListener(this);
        headerView.findViewById(R.id.second_activity_tiezi_re).setOnClickListener(this);

        zixunTv = (ViewFlipper) headerView.findViewById(R.id.second_activity_zixun_tv);
        zixunTv.setOnClickListener(this);
        ImageView zixunIv = (ImageView) headerView.findViewById(R.id.second_activity_zixun_iv);
        zixunIv.setOnClickListener(this);
        headerView.findViewById(R.id.second_activity_zixun_re).setOnClickListener(this);
        findViewById(R.id.base_top_bar_title).setOnClickListener(this);
        findViewById(R.id.base_top_bar_right).setOnClickListener(this);

        if (MyApplication.getIntance().isChina) {
            zixunIv.setImageResource(R.mipmap.zhongyaozixun);
            tieziIv.setImageResource(R.mipmap.xinxifabu);
        } else {
            zixunIv.setImageResource(R.mipmap.zhongyaozixun_en);
            tieziIv.setImageResource(R.mipmap.xinxifabu_en);
        }

        View searchView = findViewById(R.id.base_top_bar_search);
        searchView.setFocusable(false);
        searchView.setOnClickListener(this);
        mLv.addHeaderView(headerView);

        int width = getWidth();
        adapter = new ShoppingSecondAdapter(width);
        mLv.setAdapter(adapter);
        flowFragment = new FlowFragment();
        mLv.setOnItemClickListener(this);
        listViewPullListener = new ListViewPullListener(mLv, swipeRefreshLayout, true, this);

        int imageViewHeight = (int) (width * MainFragment.heightScale);
        View flowContainer = headerView.findViewById(R.id.main_fragment_head_flow_container);
        ViewGroup.LayoutParams layoutParams = flowContainer.getLayoutParams();
        layoutParams.height = imageViewHeight;
        flowContainer.setLayoutParams(layoutParams);
        getSupportFragmentManager().beginTransaction().add(R.id.main_fragment_head_flow_container, flowFragment).commit();
        getReMenNews();
    }

    @Override
    protected void onListener() {
    }

    @Override
    protected void getInterNetData() {
        getFlowImages();
        getShops();
    }


    private int page = 1;
    private boolean isLoadMore;

    @Override
    public void onPullRefresh() {
        page = 1;
        huancunList.clear();
        getFlowImages();
        getShops();
    }

    @Override
    public void onLoadMore() {
        if (adapter == null && adapter.getData() == null) {
            return;
        }
        isLoadMore = true;
        page++;
        getShops();
    }

    List<ShoppingSecondBean> huancunList = new ArrayList<>();

    private void getShops() {
        if ("".equals(shoppingtype_id) || shoppingtype_id.isEmpty()) {
            return;
        }
        AndroidNetworking.post(Contact.second_shops)
                .addBodyParameter("category_id", shoppingtype_id)
                .addBodyParameter("page", String.valueOf(page))
//                .addBodyParameter("password", passwordString)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        listViewPullListener.cancelPullRefresh();
                        // do anything with response
                        List<ShoppingSecondBean> beans = parseShops(response);
                        if (beans != null) {
                            huancunList.addAll(beans);
                        }
                        if (beans != null) {
                            if (!isLoadMore) {
                                List<ShoppSecondZongHeBean> shoppSecondZongHeBeen = sortList(beans);
                                adapter.setData(shoppSecondZongHeBeen);
                            } else {
                                isLoadMore = false;
                                List<ShoppSecondZongHeBean> shoppSecondZongHeBeen = sortList(huancunList);
                                if (shoppSecondZongHeBeen != null && shoppSecondZongHeBeen.size() > 0) {
                                    adapter.addData(shoppSecondZongHeBeen);
                                } else {
                                    listViewPullListener.cancelLoadMore();
                                }
                            }
                        } else {
                            if (!isLoadMore) {
                                Toast.makeText(ShoppingSecondActivity.this, getString(R.string.wucifenlei), Toast.LENGTH_SHORT).show();
                            } else {
                                listViewPullListener.cancelLoadMore();
                                isLoadMore = false;
                            }
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        dismissLoadingDialog();
                        listViewPullListener.cancelPullRefresh();
                        Toast.makeText(ShoppingSecondActivity.this, getString(R.string.wucifenlei), Toast.LENGTH_SHORT).show();
                    }
                });
        if (!isLoadMore) {
            showLoadingDialog();
        }
    }

    //排序shoppingSecond
    private List<ShoppSecondZongHeBean> sortList(List<ShoppingSecondBean> beans) {
        List<ShoppSecondZongHeBean> list = new ArrayList<>();
        ShoppSecondZongHeBean bean = null;
        for (int i = 0; i < beans.size(); i++) {
            ShoppingSecondBean childBean = beans.get(i);
            if (i % 2 == 0) {
                bean = new ShoppSecondZongHeBean();
                bean.setBean1(childBean);
                if (i == beans.size() - 1) {
                    list.add(bean);
                }
            } else {
                bean.setBean2(childBean);
                list.add(bean);
            }

        }
        return list;
    }

    //解析商品数据
    private List<ShoppingSecondBean> parseShops(JSONObject response) {
        List<ShoppingSecondBean> list = null;
        try {
            if (JsonUtils.isSuccess(response)) {
                JSONObject data = response.getJSONObject("data");
                JSONArray array = data.getJSONArray("child");
                list = new ArrayList<>();
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    ShoppingSecondBean bean = new ShoppingSecondBean();
                    String category_id = jsonObject.getString("category_id");
                    String category_name = jsonObject.getString("category_name");
                    String category_pic = jsonObject.getString("category_pic");
                    String pid = jsonObject.getString("pid");

                    bean.setCategory_pic(category_pic);
                    bean.setPid(pid);
                    bean.setGoods_name(category_name);
                    bean.setCategory_id(category_id);
                    list.add(bean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    protected void onMyClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.main_fragment_zhongyaozixun:
                intent = new Intent(this, NewsActivity.class);
                startActivity(intent);
                break;
            case R.id.base_top_bar_search:
                intent = new Intent(this, SearchaActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_in_anim, R.anim.activity_out_anim);
                break;
            case R.id.second_activity_zixun_iv:
            case R.id.second_activity_zixun_re:
            case R.id.second_activity_zixun_tv:
                //咨询列表
                intent = new Intent(this, NewsActivity.class);
                startActivity(intent);
                break;
            case R.id.second_activity_tiezi_iv:
            case R.id.second_activity_tiezi_re:
            case R.id.second_activity_tiezi_tv:
                //帖子列表
                intent = new Intent(this, MainActivity.class);
                intent.putExtra(MainActivity.MAIN_INDEX, MainActivity.TIEZI);
                startActivity(intent);
                finish();
                break;

            case R.id.base_top_bar_right:
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(this, LoginActivity.class);
                    startActivity(intent);
                    return;
                }
                intent = new Intent(this, MessagesActivity.class);
                startActivity(intent);
                break;
            case R.id.base_top_bar_title:
                finish();
                break;
        }
    }


    //获取轮播图
    private void getFlowImages() {
        AndroidNetworking.post(Contact.main_guanggao)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        List<Flowbean> flowbeen = parseFlowJson(response);
                        if (flowbeen == null) {
                            Toast.makeText(ShoppingSecondActivity.this, "暂无轮播图", Toast.LENGTH_SHORT).show();
                        } else {
                            flowFragment.setFlowData(flowbeen);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ShoppingThreadActivity.class);
//        intent.putExtra(Category_name);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (flowFragment != null) {
            flowFragment.startFlow();
        }
        if (tieziTv!=null){
            tieziTv.startFlipping();
        }
        if (zixunTv!=null){
            zixunTv.startFlipping();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (flowFragment != null) {
            flowFragment.stopFlow();
        }
        if (tieziTv!=null){
            tieziTv.stopFlipping();
        }
        if (zixunTv!=null){
            zixunTv.stopFlipping();
        }
    }

    //解析轮播图数据
    private List<Flowbean> parseFlowJson(JSONObject response) {
        List<Flowbean> list = null;
        try {
            String code = response.getString("code");
            if ("0".equals(code)) {
                JSONObject data = response.getJSONObject("data");
                JSONArray niu_index_response = data.getJSONArray("niu_index_response");
                list = new ArrayList<>();
                for (int i = 0; i < niu_index_response.length(); i++) {
                    JSONObject obj = niu_index_response.getJSONObject(i);
                    Flowbean bean = new Flowbean();
                    int ap_id = obj.getInt("ap_id");
                    int adv_id = obj.getInt("adv_id");
                    String adv_title = obj.getString("adv_title");
                    String adv_url = obj.getString("adv_url");
                    String adv_image = obj.getString("adv_image");
                    bean.setAp_id(ap_id);
                    bean.setAdv_id(adv_id);
                    bean.setAdv_title(adv_title);
                    bean.setAdv_url(adv_url);
                    bean.setAdv_image(adv_image);
                    list.add(bean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }


    //获取热门咨询和热门新闻
    private void getReMenNews() {
        try {
            AndroidNetworking.post(Contact.main_remen_tiezi)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            List<String> strings = JsonUtils.parseZiXunListJson(response);
                            if (tieziTv!=null){
                                setFlipperData(strings,tieziTv);
                            }
                        }

                        @Override
                        public void onError(ANError anError) {

                        }
                    });
        } catch (Exception e) {

        }
        try {
            AndroidNetworking.post(Contact.main_remen_news)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            List<String> strings = JsonUtils.parseZiXunListJson(response);
                            if (zixunTv!=null){
                                setFlipperData(strings,zixunTv);
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                        }
                    });
        } catch (Exception e) {

        }
    }


    private void setFlipperData(List<String> dataList,ViewFlipper flipper){
        if (flipper==null){
            return;
        }
        flipper.removeAllViews();
        for (int i = 0; i < dataList.size(); i++) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            tv.setText(dataList.get(i));
            tv.setSingleLine();
            tv.setGravity(Gravity.CENTER_VERTICAL|Gravity.START);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,14);
            tv.setTextColor(getResources().getColor(R.color.window_main_text_color));
            if (i == 0) {
                tv.setVisibility(View.VISIBLE);
            } else {
                tv.setVisibility(View.GONE);
            }
            flipper.addView(tv, i);
        }
        flipper.startFlipping();
    }

}
