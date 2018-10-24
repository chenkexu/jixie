package com.qmwl.zyjx.activity;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.qmwl.zyjx.utils.ToastUtils;
import com.qmwl.zyjx.view.AskRetunPayDialog;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/20.
 * 申请维权界面
 */

public class AskWeiQuanActivity extends BaseActivity {


    @BindView(R.id.et_user_feedback_content)
    EditText mEt;

    private DingDanBean mBean;

    private boolean isClickH5=false;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.activity_ask_weiquan);
    }

    @Override
    protected void initView() {

        setTitleContent(R.string.shensuweiquan);
        mBean=(DingDanBean)getIntent().getSerializableExtra("DingDanBean");
        isClickH5=getIntent().getBooleanExtra("isH5",false);

    }
    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {

    }

    @OnClick({R.id.btn_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:

                String mOid="";
                if (isClickH5){
                    mOid=getIntent().getStringExtra("orderId");
                }else {
                    mOid=mBean.getOrder_id();
                }
                ApiManager.getInstence().getApiService()
                        .shenqingweiquan(mOid,mEt.getText().toString())
                        .compose(RxUtil.<ApiResponse<Object>>rxSchedulerHelper())
                        .subscribe(new BaseObserver<Object>() {
                            @Override
                            protected void onSuccees(ApiResponse t) {
                                dismissLoadingDialog();
                              //  showSuccessDialog();
                                ToastUtils.showShort(getResources().getString(R.string.ask_suc));
                            }

                            @Override
                            protected void onFailure(String errorInfo, boolean isNetWorkError) {
                                ToastUtils.showShort(errorInfo);
                                dismissLoadingDialog();
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


    private void showSuccessDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(AskWeiQuanActivity.this);
        View view = View
                .inflate(AskWeiQuanActivity.this, R.layout.invoice_submit_success, null);
        Button tvSubmit= (Button) view
                .findViewById(R.id.tv_submit);//设置标题
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }


}
