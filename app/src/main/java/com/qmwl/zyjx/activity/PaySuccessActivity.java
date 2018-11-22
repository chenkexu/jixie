package com.qmwl.zyjx.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.PaySucAdapter;
import com.qmwl.zyjx.api.ApiManager;
import com.qmwl.zyjx.api.ApiResponse;
import com.qmwl.zyjx.api.BaseObserver;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.Constant;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.PaySucBean;
import com.qmwl.zyjx.bean.PaySucOutBean;
import com.qmwl.zyjx.bean.RemindSendGoodsBean;
import com.qmwl.zyjx.bean.ShoppingBean;
import com.qmwl.zyjx.utils.EventManager;
import com.qmwl.zyjx.utils.GsonUtils;
import com.qmwl.zyjx.utils.RxUtil;
import com.qmwl.zyjx.utils.ToastUtils;
import com.qmwl.zyjx.view.DividerGridItemDecoration;
import com.qmwl.zyjx.view.MyTitle;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaySuccessActivity extends BaseActivity {
    @BindView(R.id.rv_rec)
    RecyclerView mRvRec;
    private Context mContext;
    String out_trade_no;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.activity_pay_sucess);
        ButterKnife.bind(this);
    }


    @Override
    protected void initView() {
        mContext=this;
        TextView titleTv = (TextView) findViewById(R.id.base_top_bar_title);
        titleTv.setText(getString(R.string.buy_suc));
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {
        out_trade_no= getIntent().getStringExtra("out_trade_no");
        final List<PaySucBean> mListPayBean= new ArrayList<>();
        final PaySucAdapter mAdapter=new PaySucAdapter(mListPayBean);
        GridLayoutManager layoutManage = new GridLayoutManager(mContext, 2);
        mRvRec.addItemDecoration(new DividerGridItemDecoration(10,Color.parseColor("#F0EFF5")));

        mRvRec.setLayoutManager(layoutManage);
        mRvRec.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String uid = "";
                if (MyApplication.getIntance().isLogin()) {
                    uid = MyApplication.getIntance().userBean.getUid();
                }
                Intent intent = new Intent(mContext, WebViewActivity.class);
                intent.putExtra(WebViewActivity.isGOUWUCHE, true);
                intent.putExtra(WebViewActivity.SHOPURL, ((PaySucBean)adapter.getItem(position)).getUrl() + "&uid=" + uid);
                startActivity(intent);
            }
        });

        ApiManager.getInstence().getApiService().getPaySucDetail(0)
                .compose(RxUtil.<ApiResponse<PaySucOutBean>>rxSchedulerHelper())
                .subscribe(new BaseObserver<PaySucOutBean>() {
                    @Override
                    protected void onSuccees(ApiResponse<PaySucOutBean> t) {
                       // ToastUtils.showShort(context.getResources().getString(R.string.shanchuchenggong));
                      //  EventManager.post("refresh","");
                        Log.d("huangrui","获取订单数据成功:"+t.getData().getNiu_index_response());
                        mAdapter.setNewData(t.getData().getNiu_index_response() );

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

                Intent intent = new Intent(this, NewsDetailsActivity.class);
                intent.putExtra(NewsDetailsActivity.DETAILS_TYPE, NewsDetailsActivity.TYPE_DINGDAN);
                intent.putExtra(NewsDetailsActivity.DETAILS_URL,  "http://app.zhongyaojixie.com/index.php/api/order/orderDetail?orderId="+out_trade_no);
                startActivity(intent);
                break;

        }
    }

    @Override
    protected void onMyClick(View v) {

    }
}
