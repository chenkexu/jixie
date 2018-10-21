package com.qmwl.zyjx.activity;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.orhanobut.logger.Logger;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.Constant;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.ToastUtils;
import com.qmwl.zyjx.view.CommomDialog;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/8/18.
 * 对公付款
 */

public class DuiGongFuKuanActivity extends BaseActivity {
    @BindView(R.id.tv_open_hang)
    TextView tvOpenHang;
    @BindView(R.id.tv_kaihu_account)
    TextView tvKaihuAccount;
    @BindView(R.id.tv_account_id)
    TextView tvAccountId;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    private TextView name;
    private TextView kaihuhang;
    private TextView zhanghu;
    private String order_id;


    @Override
    protected void setLayout() {
        setContentLayout(R.layout.duigongfukuan_layout);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.duigongfukuan);
        Intent intent = getIntent();
        order_id = intent.getStringExtra(Constant.order_id);
    }


    @Override
    protected void onListener() {

    }


    @OnClick(R.id.btn_submit)
    public void onViewClicked() {
        showTiShi();
    }


    @Override
    protected void getInterNetData() {
        AndroidNetworking.get(Contact.duigongfukuan_url + "?orderId=" + order_id)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Logger.d("对公付款信息:" + response);
                        dismissLoadingDialog();
                        try {
                            JSONObject data = response.getJSONObject("data");
                            JSONObject niu_index_response = data.getJSONObject("niu_index_response");
                            String web_bank = niu_index_response.getString("web_bank");
                            String web_account = niu_index_response.getString("web_account");
                            String title = niu_index_response.getString("title");

                            String order_no = niu_index_response.getString("order_no");
                            // TODO: 2018/10/21 去设置订单编号 order_no
                            tvKaihuAccount.setText(web_account);
                            tvAccountId.setText(order_no);
                            tvOpenHang.setText(web_bank);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        ToastUtils.showShort(anError.getMessage().toString());
                        dismissLoadingDialog();
                    }
                });
        showLoadingDialog();
    }


    @Override
    protected void onMyClick(View v) {
        switch (v.getId()) {
            case R.id.base_top_bar_righttext:
                showTiShi();
                break;

        }
    }

    private void showTiShi() {
//        TextView tv = new TextView(this);
//        tv.setText(R.string.dingdanyitijiaochenggong);
//        tv.setPadding(100, 100, 100, 100);
//        MyDialog dialog = new MyDialog(this, tv, new MyDialog.onClickButton() {
//            @Override
//            public void rightClick() {
//                Intent intent = new Intent(DuiGongFuKuanActivity.this, MainActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                intent.putExtra(MainActivity.MAIN_INDEX, MainActivity.WODE);
//                startActivity(intent);
//            }
//        });
//        dialog.show();

        new CommomDialog(this, R.style.dialog, getString(R.string.dingdanyitijiaochenggong), new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                if (confirm) {
                    Intent intent = new Intent(DuiGongFuKuanActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra(MainActivity.MAIN_INDEX, MainActivity.WODE);
                    startActivity(intent);
                } else {
                    dialog.dismiss();
                }
            }
        }).show();

    }



}
