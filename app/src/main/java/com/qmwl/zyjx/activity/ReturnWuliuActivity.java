package com.qmwl.zyjx.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.api.ApiManager;
import com.qmwl.zyjx.api.ApiResponse;
import com.qmwl.zyjx.api.BaseObserver;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.bean.CancelOrderBean;
import com.qmwl.zyjx.bean.DingDanBean;
import com.qmwl.zyjx.bean.ShoppingBean;
import com.qmwl.zyjx.utils.RxUtil;
import com.qmwl.zyjx.view.AskRetunPayDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/20.
 *物流界面
 */

public class ReturnWuliuActivity extends BaseActivity {

   /* @BindView(R.id.dingdan_layout_item_iv)
    ImageView mIv;
    @BindView(R.id.dingdan_layout_item_name)
    TextView mTv;

    private AskRetunPayDialog mAskRpDialog;
*/
    @Override
    protected void setLayout() {
        setContentLayout(R.layout.activity_wuliu);
    }

    @Override
    protected void initView() {

        setTitleContent(R.string.msg_return_thing_info);

    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {

    }

   /* @OnClick({R.id.st_tuikuan, R.id.st_tuihuotuikuan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.st_tuikuan:
                ApiManager.getInstence().getApiService().get_tuikuan_order_reson()
                        .compose(RxUtil.<ApiResponse<CancelOrderBean>>rxSchedulerHelper())
                        .subscribe(new BaseObserver<CancelOrderBean>() {
                            @Override
                            protected void onSuccees(ApiResponse<CancelOrderBean> t) {
                                CancelOrderBean data = t.getData();
                                 mAskRpDialog=new AskRetunPayDialog();
                                if (!mAskRpDialog.isAdded()) {
                                    Bundle mBundle=new Bundle();
                                    mBundle.putSerializable("data",data);
                                    mAskRpDialog.setArguments(mBundle);
                                    mAskRpDialog.show(getFragmentManager(), "mAskRpDialog");
                                }

                            }

                            @Override
                            protected void onFailure(String errorInfo, boolean isNetWorkError) {

                            }
                        });

                break;
            case R.id.st_tuihuotuikuan:
                ApiManager.getInstence().getApiService().get_tuihuo_order_reson()
                        .compose(RxUtil.<ApiResponse<CancelOrderBean>>rxSchedulerHelper())
                        .subscribe(new BaseObserver<CancelOrderBean>() {
                            @Override
                            protected void onSuccees(ApiResponse<CancelOrderBean> t) {
                                CancelOrderBean data = t.getData();
                                mAskRpDialog=new AskRetunPayDialog();
                                if (!mAskRpDialog.isAdded()) {
                                    Bundle mBundle=new Bundle();
                                    mBundle.putSerializable("data",data);
                                    mAskRpDialog.setArguments(mBundle);
                                    mAskRpDialog.show(getFragmentManager(), "mAskRpDialog");
                                }

                            }

                            @Override
                            protected void onFailure(String errorInfo, boolean isNetWorkError) {

                            }
                        });


                break;

        }
    }*/


    @Override
    protected void onMyClick(View v) {
      /*  Intent intent = null;
        switch (v.getId()) {
            case R.id.login_wanjimima_button:
                intent = new Intent(this, WangJiMiMaActivity.class);
                startActivity(intent);
                break;
            case R.id.login_login_button:
                login_login();
//                Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent1);
                break;
        }*/
    }


}
