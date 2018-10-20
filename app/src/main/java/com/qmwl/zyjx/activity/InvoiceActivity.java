package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.api.ApiManager;
import com.qmwl.zyjx.api.ApiResponse;
import com.qmwl.zyjx.api.BaseObserver;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.Constant;
import com.qmwl.zyjx.utils.CommonUtils;
import com.qmwl.zyjx.utils.RxUtil;
import com.qmwl.zyjx.utils.ToastUtils;
import com.qmwl.zyjx.view.MyTitle;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ckx on 2018/10/20.
 * 开发票
 */

public class InvoiceActivity extends BaseActivity {

    @BindView(R.id.my_title)
    MyTitle myTitle;
    @BindView(R.id.iv_invoice_title_enterprise)
    ImageView ivInvoiceTitleEnterprise;
    @BindView(R.id.tv_invoice_title_enterprise)
    TextView tvInvoiceTitleEnterprise;
    @BindView(R.id.rl_invoice_title_enterprise)
    RelativeLayout rlInvoiceTitleEnterprise;
    @BindView(R.id.iv_invoice_title_personal)
    ImageView ivInvoiceTitlePersonal;
    @BindView(R.id.tv_invoice_title_personal)
    TextView tvInvoiceTitlePersonal;
    @BindView(R.id.rl_invoice_title_personal)
    RelativeLayout rlInvoiceTitlePersonal;



    @BindView(R.id.et_title)
    EditText etTitle;//发票抬头
    @BindView(R.id.et_id)
    EditText etId; //纳税人税号
    @BindView(R.id.tv_content)
    TextView tvContent; //发票内容
    @BindView(R.id.tv_money)
    TextView tvMoney; //发票金额
    @BindView(R.id.et_remark)
    EditText etRemark; //发票备注
    @BindView(R.id.et_email)
    EditText etEmail;//电子邮件
    @BindView(R.id.et_person_name)
    EditText etPersonName; //收件人
    @BindView(R.id.et_person_phone)
    EditText etPersonPhone;//联系电话
    @BindView(R.id.et_area)
    EditText etArea;//地区
    @BindView(R.id.et_address)
    EditText etAddress;//地址



    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.ll_p_consignee_information)
    LinearLayout llPConsigneeInformation;

    private int isdianzi  = 1;//1电子发票，2，纸质发票。默认选择电子发票
    private int isEnterpriseOrpersonal_itemCheck = 1;//1 企业 2 个人 ，默认选择企业抬头
    private int invoice_type;


    @Override
    protected void setLayout() {
        setContentView(R.layout.activity_apply_elect_invoice);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        myTitle.setImageBack(this);
        Intent intent = getIntent();
        invoice_type = intent.getIntExtra(Constant.invoice_type,1);
        if (invoice_type == 1) { //电子
            llPConsigneeInformation.setVisibility(View.GONE);
        }else{
            llPConsigneeInformation.setVisibility(View.VISIBLE);
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






    @OnClick({R.id.rl_invoice_title_enterprise, R.id.rl_invoice_title_personal, R.id.tv_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_invoice_title_enterprise://企业单位
                isEnterpriseOrpersonal_itemCheck = 1;
                ivInvoiceTitleEnterprise.setBackgroundResource(R.mipmap.check_invoice);
                ivInvoiceTitlePersonal.setBackgroundResource(R.mipmap.no_check_invoice);
                break;
            case R.id.rl_invoice_title_personal://个人单位
                isEnterpriseOrpersonal_itemCheck = 1;
                ivInvoiceTitlePersonal.setBackgroundResource(R.mipmap.check_invoice);
                ivInvoiceTitleEnterprise.setBackgroundResource(R.mipmap.no_check_invoice);
                break;
            case R.id.tv_submit:

                break;
        }
    }


    private void checkContent(){
        String eTtitleStr = etTitle.getText().toString();//抬头
        String etIdStr = etId.getText().toString();//税号
        String tvContentStr = tvContent.getText().toString();//发票内容
        String tvMoneyStr = tvMoney.getText().toString();//金额
        String etRemarkStr = etRemark.getText().toString();//备注

        String etPersonNameStr = etPersonName.getText().toString();//收件人
        String etPersonPhoneStr = etPersonPhone.getText().toString();//电话
        String etAreaStr = etArea.getText().toString();//地区
        String etAddressStr = etAddress.getText().toString();//详细地址
        String etEmailStr = etEmail.getText().toString();//电子邮件



        if (TextUtils.isEmpty(eTtitleStr)) {
            ToastUtils.showShort("发票抬头不能为空");
            return;
        }
        if (TextUtils.isEmpty(etIdStr)) {
            ToastUtils.showShort("税号不能为空");
            return;
        }


        if (invoice_type == 1) {

        }else{
            if (TextUtils.isEmpty(etPersonNameStr)) {
                ToastUtils.showShort("收件人不能为空");
                return;
            }
            if (TextUtils.isEmpty(etPersonPhoneStr)) {
                ToastUtils.showShort("联系方式不能为空");
                return;
            }
            if (TextUtils.isEmpty(etAreaStr)) {
                ToastUtils.showShort("地区不能为空");
                return;
            }
            if (TextUtils.isEmpty(etAddressStr)) {
                ToastUtils.showShort("地址不能为空");
                return;
            }
        }




        if (!CommonUtils.isEmail(etEmailStr)){
            ToastUtils.showShort("请输入正确的电子邮箱地址");
            return;
        }

    }


    private void submit(){
        HashMap<String, Object> map = new HashMap<>();
        ApiManager.getInstence().getApiService()
                .insert_invoice(map)
                .compose(RxUtil.<ApiResponse<Object>>rxSchedulerHelper())
                .subscribe(new BaseObserver<Object>() {
                    @Override
                    protected void onSuccees(ApiResponse t) {

                    }

                    @Override
                    protected void onFailure(String errorInfo, boolean isNetWorkError) {

                    }
                });
    }
}
