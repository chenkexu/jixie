package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.TransportAdapter;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.Flowbean;
import com.qmwl.zyjx.bean.TransportBean;
import com.qmwl.zyjx.fragment.FlowFragment;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.GlideUtils;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.utils.ListViewPullListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/28.
 * 运输首页
 */

public class TransportActivity extends BaseActivity implements AdapterView.OnItemClickListener , ListViewPullListener.ListViewPullOrLoadMoreListener {
    private TransportAdapter adapter;
    private float image_scale = 0.4589372f;
    private RelativeLayout imageView;
    private View layoutContainer;
    private PopupWindow popupWindow;
    private ListViewPullListener listViewPullListener;
    private FlowFragment flowFragment;
    @Override
    protected void setLayout() {
        setContentLayout(R.layout.transport_activity_layout);
    }

    @Override
    protected void initView() {
        layoutContainer = findViewById(R.id.transport_layout_container);
        ListView mLv = (ListView) findViewById(R.id.transport_layout_listview);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.guangchang_layout_swiprefreshlayout);
        listViewPullListener = new ListViewPullListener(mLv, swipeRefreshLayout, this);

        adapter = new TransportAdapter(this);
        mLv.setAdapter(adapter);
        mLv.setOnItemClickListener(this);
        View headView = getLayoutInflater().inflate(R.layout.transport_headview_layout, null);

        imageView = (RelativeLayout) headView.findViewById(R.id.transport_headview_iv);
//        imageView.setOnClickListener(this);

        flowFragment = new FlowFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.transport_headview_iv,flowFragment).commit();

        mLv.addHeaderView(headView);
        showBackView(false);
        setTitleContent(R.string.yunshu);
        findViewById(R.id.transport_headview_woyaofahuo).setOnClickListener(this);
        findViewById(R.id.transport_headview_woyaochengyun).setOnClickListener(this);
        findViewById(R.id.transport_headview_huoyuanguangchang).setOnClickListener(this);
        findViewById(R.id.transport_headview_gerenzhongxin).setOnClickListener(this);
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        layoutParams.height = (int) (getWidth() * image_scale);
        imageView.setLayoutParams(layoutParams);
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {
        AndroidNetworking.post(Contact.yunshushouye_list)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listViewPullListener.cancelPullRefresh();
                        List<TransportBean> transportBeen = JsonUtils.parseHuoYuanList(response);
                        adapter.setData(transportBeen);
                    }

                    @Override
                    public void onError(ANError anError) {
                        listViewPullListener.cancelPullRefresh();
                    }
                });
        //获取图片信息
        AndroidNetworking.get(Contact.yunshu_main_image_url)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject data = response.getJSONObject("data");
                            JSONArray niu_index_response = data.getJSONArray("niu_index_response");
                            List<Flowbean> imageList = new ArrayList<Flowbean>();
                            for (int i = 0; i < niu_index_response.length(); i++) {
                                JSONObject obj = niu_index_response.getJSONObject(i);
                                String image = obj.getString("image");
                                Flowbean flowbean = new Flowbean();
                                flowbean.setAdv_image(image);
//                                GlideUtils.openImage(getApplicationContext(), image, imageView);
//                                imageView.setTag(obj.getString(""));
                                imageList.add(flowbean);
                            }
                            flowFragment.setFlowData(imageList);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
        getInterNetData();
    }

    private void addjia() {
//        List<TransportBean> list = new ArrayList<>();
//        for (int i = 0; i < 30; i++) {
//            TransportBean bean = new TransportBean();
//            bean.setTime(60 * 60 * 300);
//            list.add(bean);
//        }
//        adapter.setData(list);
    }


    @Override
    protected void onMyClick(View v) {
        Intent intent = null;
        switch (v.getId()) {

            case R.id.huoyun_wode_guanbidialog:
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                break;
            case R.id.huoyun_wode_qiyechengyun:
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(this, LoginActivity.class);
                    startActivityForResult(intent, 0);
                    return;
                }
                intent = new Intent(this, ChengYunFangRenZhengActivity.class);
                startActivity(intent);
                if (popupWindow!=null){
                    popupWindow.dismiss();
                }
                break;
            case R.id.huoyun_wode_getichengyun:
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(this, LoginActivity.class);
                    startActivityForResult(intent, 0);
                    return;
                }
                intent = new Intent(this, ChengYunRenRenZhengActivity.class);
                startActivity(intent);
                if (popupWindow!=null){
                    popupWindow.dismiss();
                }
                break;

            case R.id.transport_headview_iv:
//                intent = new Intent(this, DaoHangActivity.class);
//                startActivity(intent);
//                String url = (String) imageView.getTag();
//                Toast.makeText(this, "url:" + url, Toast.LENGTH_SHORT).show();
                break;
            case R.id.transport_headview_woyaofahuo:
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(this, LoginActivity.class);
                    startActivityForResult(intent, 0);
                    return;
                }
                getHuoZhuRenZheng();
//                intent = new Intent(this, WoYaoFaHuoActivity.class);
//                startActivity(intent);
                break;
            case R.id.transport_headview_woyaochengyun:
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(this, LoginActivity.class);
                    startActivityForResult(intent, 0);
                    return;
                }
                getStatueChengYun();
                break;
            case R.id.transport_headview_huoyuanguangchang:
                intent = new Intent(this, HuoYuanActivity.class);
                intent.putExtra(HuoYuanActivity.IS_TUIJIAN, false);
                startActivity(intent);
                break;
            case R.id.transport_headview_gerenzhongxin:
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(this, LoginActivity.class);
                    startActivityForResult(intent, 0);
                    return;
                }
                intent = new Intent(this, HuoYunGeRenZhongXinActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        if (adapter!=null){
            adapter.cancelRunnable();
        }
        if (flowFragment!=null){
            flowFragment.stopFlow();
        }
        super.onDestroy();
    }

    private void shwoPopuWindow() {
        if (popupWindow == null) {
            popupWindow = new PopupWindow(getWidth(), getHeight());
            View contentView = getLayoutInflater().inflate(R.layout.chengyunrenrenzheng_dialog_layout, null);
            contentView.findViewById(R.id.huoyun_wode_qiyechengyun).setOnClickListener(this);
            contentView.findViewById(R.id.huoyun_wode_getichengyun).setOnClickListener(this);
            contentView.findViewById(R.id.huoyun_wode_guanbidialog).setOnClickListener(this);
            popupWindow.setContentView(contentView);
            popupWindow.setFocusable(true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
        }
        popupWindow.showAtLocation(layoutContainer, Gravity.TOP, 0, 0);
    }

    private void getStatueChengYun() {
        showLoadingDialog();
        AndroidNetworking.get(Contact.yunshu_panduanchengyunren_renzheng + "?uid=" + MyApplication.getIntance().userBean.getUid())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        try {
//                            JSONObject data = response.getJSONObject("data");
//                            JSONObject niu_index_response = data.getJSONObject("niu_index_response");
                            int code = response.getInt("code");
                            if (code == -50) {
                                shwoPopuWindow();
                            } else if (code == 10) {
                                //个人认证
                                getShenHeState();

                            } else if (code == 20) {
                                //企业认证
                                getQiYeShenHeState();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                    }
                });
    }

    private void getHuoZhuRenZheng() {
        showLoadingDialog();
        AndroidNetworking.get(Contact.huozhurenzhengstate + "?uid=" + MyApplication.getIntance().userBean.getUid())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        int state = JsonUtils.parseShenHeState(response);
                        //0为审核中，1成功，2失败
                        switch (state) {
//                            case -1:
                            case 1:
                                Intent intent = new Intent(TransportActivity.this, WoYaoFaHuoActivity.class);
                                startActivity(intent);
                                break;
                            case 0:
                            case 2:
                            case 3:
                                Intent intent1 = new Intent(TransportActivity.this, HuoZhuRenZhengActivity.class);
                                startActivity(intent1);
                                break;
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (!MyApplication.getIntance().isLogin()) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, 0);
            return;
        }
        if (adapter == null) {
            return;
        }
        TransportBean item = adapter.getItem(position - 1);
        Intent intent = new Intent(this, YunDanDetailsActivity.class);
        intent.putExtra("tid", item.getT_id());
        startActivity(intent);
    }

    @Override
    public void onPullRefresh() {
        getInterNetData();
    }

    @Override
    public void onLoadMore() {

    }



    //个人认证状态
    private int geRenStatue = -1;

    private void getShenHeState() {
//        AndroidNetworking.get(Contact.chengyunfangrenzhengstate + "?uid=" + MyApplication.getIntance().userBean.getUid())
        AndroidNetworking.get(Contact.chengyunfangrenzhengstate + "?uid=" + MyApplication.getIntance().userBean.getUid())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        try {
                            int code = response.getInt("code");
                            if (code == -50) {
                                geRenStatue = 3;
                            } else {
                                JSONObject data = response.getJSONObject("data");
                                JSONObject niu_index_response = data.getJSONObject("niu_index_response");
                                geRenStatue = niu_index_response.getInt("state");
                            }
                            if (geRenStatue==2){
                                Intent intent = new Intent(TransportActivity.this, HuoYuanActivity.class);
                                intent.putExtra(HuoYuanActivity.IS_TUIJIAN, false);
                                startActivity(intent);
                            }else{
                                Intent intent = new Intent(TransportActivity.this, ChengYunRenRenZhengActivity.class);
                                startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(TransportActivity.this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                    }
                });
        showLoadingDialog();
    }
    //认证状态
    private int qiYeStatue = -1;

    private void getQiYeShenHeState() {
//        AndroidNetworking.get(Contact.chengyunfangrenzhengstate + "?uid=" + MyApplication.getIntance().userBean.getUid())
        AndroidNetworking.get(Contact.yunshu_chengyun_qieye_statue + "?uid=" + MyApplication.getIntance().userBean.getUid())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        try {
                            int code = response.getInt("code");
                            if (code == -50) {
                                qiYeStatue = 3;
                            } else {
                                JSONObject data = response.getJSONObject("data");
                                JSONObject niu_index_response = data.getJSONObject("niu_index_response");
                                qiYeStatue = niu_index_response.getInt("state");
                            }
                            if (geRenStatue==2){
                                Intent intent = new Intent(TransportActivity.this, HuoYuanActivity.class);
                                intent.putExtra(HuoYuanActivity.IS_TUIJIAN, false);
                                startActivity(intent);
                            }else{
                                Intent intent = new Intent(TransportActivity.this, ChengYunFangRenZhengActivity.class);
                                startActivity(intent);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(TransportActivity.this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                    }
                });
        showLoadingDialog();
    }

}
