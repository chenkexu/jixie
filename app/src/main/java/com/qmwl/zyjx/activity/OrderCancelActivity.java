package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.api.ApiManager;
import com.qmwl.zyjx.api.ApiResponse;
import com.qmwl.zyjx.api.BaseObserver;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.Constant;
import com.qmwl.zyjx.bean.CancelOrderBean;
import com.qmwl.zyjx.utils.RxUtil;
import com.qmwl.zyjx.view.MyTitle;

import org.greenrobot.eventbus.EventBus;

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


    @Override
    protected void setLayout() {
        setContentLayout(R.layout.activity_order_cancel);
        ButterKnife.bind(this);
    }



    @Override
    protected void initView() {
        myTitle.setImageBack(this);
        myTitle.setTitleName("");
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();
        String stringExtra = intent.getStringExtra(Constant.cancel_order_reson);



        ApiManager.getInstence().getApiService().get_cancel_order_reson()
                .compose(RxUtil.<ApiResponse<CancelOrderBean>>rxSchedulerHelper())
                .subscribe(new BaseObserver<CancelOrderBean>() {
                    @Override
                    protected void onSuccees(ApiResponse<CancelOrderBean> t) {
                        CancelOrderBean data = t.getData();
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
                    protected void onFailure(String errorInfo, boolean isNetWorkError) {

                    }
                });
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {

    }



    @Override
    protected void onMyClick(View v) {
        String cancleStr = cancelResult.get(choosePosition);
        EventBus.getDefault().post(cancleStr);
    }



    @OnClick(R.id.tv_submit)
    public void onViewClicked() {

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
