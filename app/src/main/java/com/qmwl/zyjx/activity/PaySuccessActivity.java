package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.PaySucAdapter;
import com.qmwl.zyjx.api.ApiManager;
import com.qmwl.zyjx.api.ApiResponse;
import com.qmwl.zyjx.api.BaseObserver;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.Constant;
import com.qmwl.zyjx.bean.PaySucBean;
import com.qmwl.zyjx.bean.PaySucOutBean;
import com.qmwl.zyjx.bean.RemindSendGoodsBean;
import com.qmwl.zyjx.bean.ShoppingBean;
import com.qmwl.zyjx.utils.EventManager;
import com.qmwl.zyjx.utils.GsonUtils;
import com.qmwl.zyjx.utils.RxUtil;
import com.qmwl.zyjx.utils.ToastUtils;
import com.qmwl.zyjx.view.MyTitle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaySuccessActivity extends BaseActivity {
    @BindView(R.id.rv_rec)
    RecyclerView mRvRec;


    @Override
    protected void setLayout() {
        setContentLayout(R.layout.activity_pay_sucess);
        ButterKnife.bind(this);
    }


    @Override
    protected void initView() {
        TextView titleTv = (TextView) findViewById(R.id.base_top_bar_title);
        titleTv.setText(getString(R.string.buy_suc));
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {
        ApiManager.getInstence().getApiService().getPaySucDetail(0)
                .compose(RxUtil.<ApiResponse<PaySucOutBean>>rxSchedulerHelper())
                .subscribe(new BaseObserver<PaySucOutBean>() {
                    @Override
                    protected void onSuccees(ApiResponse<PaySucOutBean> t) {
                       // ToastUtils.showShort(context.getResources().getString(R.string.shanchuchenggong));
                      //  EventManager.post("refresh","");
                        Log.d("huangrui","获取订单数据成功:"+t.getData().getNiu_index_response());
                        List<PaySucBean> mListPayBean=new ArrayList<>();
                        PaySucAdapter mAdapter=new PaySucAdapter(mListPayBean);
                        mRvRec.setAdapter(mAdapter);

                    }

                    @Override
                    protected void onFailure(String errorInfo, boolean isNetWorkError) {
                        ToastUtils.showShort(errorInfo+"");
                    }
                });

    }

     @OnClick({R.id.btn_returnhome, R.id.btn_lookdetail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_returnhome:
                finish();
                break;
            case R.id.btn_lookdetail:
               /* ShoppingBean shoppingBean = item.getShopList().get(0);
                Intent intent = new Intent(getContext(), NewsDetailsActivity.class);
                intent.putExtra(NewsDetailsActivity.DETAILS_TYPE, NewsDetailsActivity.TYPE_DINGDAN);
                intent.putExtra(NewsDetailsActivity.DETAILS_URL, shoppingBean.getDingdanUrl());
                startActivity(intent);*/
                break;

        }
    }

    @Override
    protected void onMyClick(View v) {

    }
}
