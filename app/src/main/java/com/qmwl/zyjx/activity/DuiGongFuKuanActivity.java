package com.qmwl.zyjx.activity;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.view.CommomDialog;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/8/18.
 * 对公付款
 */

public class DuiGongFuKuanActivity extends BaseActivity {
    private TextView name;
    private TextView kaihuhang;
    private TextView zhanghu;
    @Override
    protected void setLayout() {
        setContentLayout(R.layout.duigongfukuan_layout);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.duigongfukuan);
        setRightText(R.string.next);

        name = (TextView) findViewById(R.id.duigongfukuan_layout_gongsimingcheng);
        kaihuhang = (TextView) findViewById(R.id.duigongfukuan_layout_kaihuhang);
        zhanghu = (TextView) findViewById(R.id.duigongfukuan_layout_zhanghu);

    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {
        AndroidNetworking.get(Contact.duigongfukuan_url)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        try {
                            JSONObject data = response.getJSONObject("data");
                            JSONObject niu_index_response = data.getJSONObject("niu_index_response");
                            String web_bank = niu_index_response.getString("web_bank");
                            String web_account = niu_index_response.getString("web_account");
                            String title = niu_index_response.getString("title");
                            name.setText(title);
                            kaihuhang.setText(getString(R.string.kaihuhang)+web_bank);
                            zhanghu.setText(getString(R.string.zhanghu)+web_account);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
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
