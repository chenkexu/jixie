package com.qmwl.zyjx.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.DingDanAdapter;
import com.qmwl.zyjx.api.ApiManager;
import com.qmwl.zyjx.api.ApiResponse;
import com.qmwl.zyjx.api.BaseObserver;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.Constant;
import com.qmwl.zyjx.bean.CancelOrderBean;
import com.qmwl.zyjx.bean.DingDanBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.utils.RxUtil;
import com.qmwl.zyjx.view.CommomDialog;
import com.qmwl.zyjx.view.MyTitle;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


//订单取消
public class OrderCancelActivity extends BaseActivity {


    @BindView(R.id.my_title)
    MyTitle myTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private int choosePosition;



//    private String[] cancleStrs = {
//            getResources().getString(R.string.goods_not_cancel),
//            getResources().getString(R.string.goods_not_want_buy),
//            getResources().getString(R.string.goods_info_error),
//            getResources().getString(R.string.goods_offline),
//            getResources().getString(R.string.goods_descrip_error),
//            getResources().getString(R.string.goods_size_error),
//            getResources().getString(R.string.goods_has_problem),
//            getResources().getString(R.string.goods_color_error),
//            getResources().getString(R.string.goods_less_or_breakage),
//            getResources().getString(R.string.goods_other),
//
//    };


    private Adapter adaptersex;
    private List<String> cancelResult;
    private DingDanBean dingDanBean;
    private String stringExtra;
    private DingDanAdapter dingDanAdapter;


    @Override
    protected void setLayout() {
        setContentLayout(R.layout.activity_order_cancel);
        ButterKnife.bind(this);
    }



    @Override
    protected void initView() {
        myTitle.setImageBack(this);
        myTitle.setTitleName("");
        dingDanAdapter = new DingDanAdapter(getContainerView(), this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        stringExtra = intent.getStringExtra(Constant.cancel_order_reson);

        if (stringExtra.equals(Constant.get_cancel_order_reson)) { //取消订单
            get_cancel_order_reson();
        }else if(stringExtra.equals(Constant.get_tuihuo_order_reson)){ //退货
            get_tuihuo_order_reson();
        }else{ // //退款
            get_tuikuan_order_reson();
        }
        dingDanBean = (DingDanBean)intent.getSerializableExtra("order");
    }

    //获取取消订单的理由
    private void get_cancel_order_reson(){
        ApiManager.getInstence().getApiService().get_cancel_order_reson()
                .compose(RxUtil.<ApiResponse<CancelOrderBean>>rxSchedulerHelper())
                .subscribe(new BaseObserver<CancelOrderBean>() {
                    @Override
                    protected void onSuccees(ApiResponse<CancelOrderBean> t) {
                        CancelOrderBean data = t.getData();
                        setData(data);
                    }

                    @Override
                    protected void onFailure(String errorInfo, boolean isNetWorkError) {

                    }
                });
    }


    //获取退款订单的理由
    private void get_tuikuan_order_reson(){
        ApiManager.getInstence().getApiService().get_tuikuan_order_reson()
                .compose(RxUtil.<ApiResponse<CancelOrderBean>>rxSchedulerHelper())
                .subscribe(new BaseObserver<CancelOrderBean>() {
                    @Override
                    protected void onSuccees(ApiResponse<CancelOrderBean> t) {
                        CancelOrderBean data = t.getData();
                        setData(data);
                    }

                    @Override
                    protected void onFailure(String errorInfo, boolean isNetWorkError) {

                    }
                });
    }



    //获取退货订单的理由
    private void get_tuihuo_order_reson(){
        ApiManager.getInstence().getApiService().get_tuihuo_order_reson()
                .compose(RxUtil.<ApiResponse<CancelOrderBean>>rxSchedulerHelper())
                .subscribe(new BaseObserver<CancelOrderBean>() {
                    @Override
                    protected void onSuccees(ApiResponse<CancelOrderBean> t) {
                        CancelOrderBean data = t.getData();
                        setData(data);
                    }

                    @Override
                    protected void onFailure(String errorInfo, boolean isNetWorkError) {

                    }
                });
    }




    private void setData(CancelOrderBean data){
        cancelResult = data.getNiu_index_response();
        if (cancelResult !=null) {
            adaptersex = new Adapter(cancelResult);
            recyclerView.setAdapter(adaptersex);
            adaptersex.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    choosePosition = position;
                    adaptersex.notifyDataSetChanged();
                }
            });
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

    }



    @OnClick(R.id.tv_submit)
    public void onViewClicked() {
        if (choosePosition == 0) {
            finish();
            return;
        }
        String cancleStr = cancelResult.get(choosePosition);
        if (stringExtra.equals(Constant.get_cancel_order_reson)) { //取消订单
            cancelDingDan(this, dingDanBean, cancleStr);
        }else if(stringExtra.equals(Constant.get_tuihuo_order_reson)){ //退货

        }else{ // //退款
            tuikuanPlay(this,dingDanBean,cancleStr);
        }

    }



    public void tuikuanPlay(final Context context, DingDanBean item,String content) {
        AndroidNetworking.get(Contact.tuikuan + "?orderId=" + item.getOrder_id()
                + "&goods_id=" + item.getShopList().get(0).getGoods_id()
                +"&content" + content)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (JsonUtils.isSuccess(response)) {
                                WoDeDingDanActivity.refreshData(context);
                                new CommomDialog(context, R.style.dialog, context.getString(R.string.tuikuanchenggong), new CommomDialog.OnCloseListener() {
                                    @Override
                                    public void onClick(Dialog dialog, boolean confirm) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                }).setTitle(context.getString(R.string.tishi)).setHideCancelButton().show();
                            } else {
                                showTishiDialog(context, R.string.tuikuanshibai);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            showTishiDialog(context, R.string.tuikuanshibai);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        showTishiDialog(context, R.string.quxiaodingdanshibai);
                    }
                });
    }











    //执行取消订单的方法
    private void cancelDingDan(final Context context, DingDanBean item, String content) {
        AndroidNetworking.get(Contact.quxiaodingdan + "?orderId=" + item.getOrder_id()+"&content="+content)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (JsonUtils.isSuccess(response)) {
                                WoDeDingDanActivity.refreshData(context);
                                new CommomDialog(context, R.style.dialog, context.getString(R.string.quxiaodingdanchenggong), new CommomDialog.OnCloseListener() {
                                    @Override
                                    public void onClick(Dialog dialog, boolean confirm) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                }).setTitle(context.getString(R.string.tishi)).setHideCancelButton().show();
                            } else {
                                showTishiDialog(context, R.string.quxiaodingdanshibai);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            showTishiDialog(context, R.string.quxiaodingdanshibai);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        showTishiDialog(context, R.string.quxiaodingdanshibai);
                    }
                });
    }

    private void showTishiDialog(Context context, int stringId) {
        new CommomDialog(context, R.style.dialog, context.getString(stringId), new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                dialog.dismiss();
            }
        }).setTitle(context.getString(R.string.tishi)).setHideCancelButton().show();
    }












    public class Adapter extends BaseQuickAdapter<String,BaseViewHolder> {


        public Adapter(@Nullable List<String> data) {
            super(R.layout.item_rv_cancel_order, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            int layoutPosition = helper.getLayoutPosition();
            helper.setText(R.id.tv_cancel_name,item);
            if (choosePosition == layoutPosition) {
                helper.setImageResource(R.id.iv_choose, R.mipmap.radio_choose);
            }else{
                helper.setImageResource(R.id.iv_choose, R.mipmap.radio_choose_no);
            }

        }
    }
}
