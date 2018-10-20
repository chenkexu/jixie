package com.qmwl.zyjx.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.api.ApiManager;
import com.qmwl.zyjx.api.ApiResponse;
import com.qmwl.zyjx.api.BaseObserver;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.CancelOrderBean;
import com.qmwl.zyjx.bean.DingDanBean;
import com.qmwl.zyjx.bean.ShoppingBean;
import com.qmwl.zyjx.bean.UserBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.utils.MD5Utils;
import com.qmwl.zyjx.utils.RxUtil;
import com.qmwl.zyjx.utils.SharedUtils;
import com.qmwl.zyjx.view.AskRetunPayDialog;
import com.umeng.message.PushAgent;
import com.umeng.message.UTrack;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/20.
 * 申请退货界面
 */

public class AskReturnThingActivity extends BaseActivity {

    @BindView(R.id.dingdan_layout_item_iv)
    ImageView mIv;
    @BindView(R.id.dingdan_layout_item_name)
    TextView mTv;

    private AskRetunPayDialog mAskRpDialog;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.activity_ask_return_thing);
    }

    @Override
    protected void initView() {

        setTitleContent(R.string.returnthing);
        DingDanBean mBean=(DingDanBean)getIntent().getSerializableExtra("DingDanBean");
        ShoppingBean shoppingBean = mBean.getShopList().get(0);
        if ("".equals(shoppingBean.getIv_pic()) || TextUtils.isEmpty(shoppingBean.getIv_pic())) {
            mIv.setImageResource(R.mipmap.small);
        } else {
            Glide.with(this).load(shoppingBean.getIv_pic()).diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.small).into(mIv);
        }
        mTv.setText(shoppingBean.getName());
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {

    }

    @OnClick({R.id.st_tuikuan, R.id.st_tuihuotuikuan})
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
    }


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
