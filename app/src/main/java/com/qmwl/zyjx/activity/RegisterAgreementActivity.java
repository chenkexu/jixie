package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by 郭辉 on 2018/1/17 14:46.
 * TODO:我要开店协议页面
 */

public class RegisterAgreementActivity extends BaseActivity implements CompoundButton.OnCheckedChangeListener {

    private TextView contentTv;
    private CheckBox agreenButton;
    private Button nextButton;
    //是否是企业跳转
    private boolean isQiye;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.activity_layout_register_agreement);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.kaidianxieyi);
        isQiye = getIntent().getBooleanExtra("type", false);
        contentTv = (TextView) findViewById(R.id.id_register_agreement_content);
        agreenButton = (CheckBox) findViewById(R.id.id_register_agreement_gouxuan);
        agreenButton.setOnCheckedChangeListener(this);
        nextButton = (Button) findViewById(R.id.id_register_agreement_next);
        nextButton.setOnClickListener(this);
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {
        if (isQiye) {
            getQiye();
        } else {
            getGeRen();
        }
    }

    private void getGeRen() {
        showLoadingDialog();
        AndroidNetworking.get(Contact.woyaokaidian_gerenxieyi)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        deal(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                    }
                });
    }

    private void deal(JSONObject response) {
        try {
            if (JsonUtils.isSuccess(response)) {
                JSONObject data = response.getJSONObject("data");
                String niu_index_response = data.getString("niu_index_response");
                CharSequence charSequence;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                    charSequence = Html.fromHtml(niu_index_response, Html.FROM_HTML_MODE_LEGACY);
                } else {
                    charSequence = Html.fromHtml(niu_index_response);
                }

                contentTv.setText(charSequence);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void getQiye() {
        showLoadingDialog();
        AndroidNetworking.get(Contact.woyaokaidian_qiyexieyi)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        deal(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                    }
                });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            nextButton.setBackground(ContextCompat.getDrawable(this, R.drawable.tiezi_details_fasong_bg));
        } else {
            nextButton.setBackground(ContextCompat.getDrawable(this, R.drawable.tiezi_details_fasong_gray_bg));
        }
    }

    @Override
    protected void onMyClick(View v) {
        switch (v.getId()) {
            case R.id.id_register_agreement_next:
                if (!agreenButton.isChecked()) {
                    Toast.makeText(this, getString(R.string.qingyueduxieyibinggouxuan), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isQiye) {
                    Intent intent = new Intent(this, RegisterQiYeActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(this, RegisterPersonActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

}
