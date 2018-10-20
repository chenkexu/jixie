package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.DingDanBean;
import com.qmwl.zyjx.utils.CommonUtils;
import com.qmwl.zyjx.utils.RxUtil;
import com.qmwl.zyjx.utils.ToastUtils;
import com.qmwl.zyjx.view.MyTitle;

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
    @BindView(R.id.et_content)
    EditText tvContent; //发票内容
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


    @BindView(R.id.ll_invoice_id)
    LinearLayout ll_invoice_id;


    @BindView(R.id.ll_main)
    LinearLayout llMain;
    @BindView(R.id.textView4)
    TextView textView4;
    @BindView(R.id.ll_p_consignee_information)
    LinearLayout llPConsigneeInformation;

    private int isdianzi  = 1;//1电子发票，2，纸质发票。默认选择电子发票
    private int isEnterpriseOrpersonal_itemCheck = 1;//1 企业 2 个人 ，默认选择企业抬头
    private int invoice_type;
    private DingDanBean dingDanBean;
    private String eTtitleStr;
    private String etIdStr;
    private String etPersonNameStr;
    private String etPersonPhoneStr;
    private String etAreaStr;
    private String etAddressStr;
    private String etEmailStr;
    private String etRemarkStr;
    private String tvMoneyStr;
    private String tvContentStr;


    @Override
    protected void setLayout() {
        setContentLayout(R.layout.activity_apply_elect_invoice);
        ButterKnife.bind(this);
    }



    @Override
    protected void initView() {
        myTitle.setImageBack(this);

        Intent intent = getIntent();
        invoice_type = intent.getIntExtra(Constant.invoice_type,1);
        dingDanBean = (DingDanBean) intent.getSerializableExtra(Constant.order);

        String string = getResources().getString(R.string.invoice_money, dingDanBean.getOrder_money()+"");
        tvMoney.setText(Html.fromHtml(string));

        if (invoice_type == 1) { //电子
            llPConsigneeInformation.setVisibility(View.GONE);
            myTitle.setTitleName(getResources().getString(R.string.apply_elec_invoice));
        }else{
            llPConsigneeInformation.setVisibility(View.VISIBLE);
            myTitle.setTitleName(getResources().getString(R.string.apply_paper_invoice));
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
                ll_invoice_id.setVisibility(View.VISIBLE);
                break;
            case R.id.rl_invoice_title_personal://个人单位
                ll_invoice_id.setVisibility(View.GONE);
                isEnterpriseOrpersonal_itemCheck = 1;
                ivInvoiceTitlePersonal.setBackgroundResource(R.mipmap.check_invoice);
                ivInvoiceTitleEnterprise.setBackgroundResource(R.mipmap.no_check_invoice);
                break;
            case R.id.tv_submit:
                checkContent();
                break;
        }
    }



    private void checkContent(){
        //抬头
        eTtitleStr = etTitle.getText().toString();
        //税号
        etIdStr = etId.getText().toString();
        //发票内容
        tvContentStr = tvContent.getText().toString();
        //金额
        tvMoneyStr = tvMoney.getText().toString();
        //备注
        etRemarkStr = etRemark.getText().toString();
        //收件人
        etPersonNameStr = etPersonName.getText().toString();
        //电话
        etPersonPhoneStr = etPersonPhone.getText().toString();
        //地区
        etAreaStr = etArea.getText().toString();
        //详细地址
        etAddressStr = etAddress.getText().toString();
        //电子邮件
        etEmailStr = etEmail.getText().toString();




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
        submit();

    }



    /*参数：orderId 订单ID  taitou 发票抬头  itemCheck  1为企业 2为个人  isdianzi 1为电子 2为纸制  price 价格  order_no 订单号 shibiehao 识别号
    content 内容 email 邮箱 name 姓名 tel 电话 address 地址 des 备注*/
    private void submit(){
//        HashMap<String, Object> map = new HashMap<>();
        showLoadingDialog();
        ApiManager.getInstence().getApiService()
                .insert_invoice(MyApplication.getIntance().userBean.getUid(),dingDanBean.getOrder_id(),eTtitleStr,isEnterpriseOrpersonal_itemCheck+"",isdianzi+"",
                        dingDanBean.getOrder_money()+"",dingDanBean.getOrder_no()+"",etIdStr,
                        tvContentStr, etEmailStr, etPersonNameStr, etPersonNameStr,
                        etAreaStr + etAddressStr, etRemarkStr)
                .compose(RxUtil.<ApiResponse<Object>>rxSchedulerHelper())
                .subscribe(new BaseObserver<Object>() {
                    @Override
                    protected void onSuccees(ApiResponse t) {
                        dismissLoadingDialog();
                        showSuccessDialog();
                    }

                    @Override
                    protected void onFailure(String errorInfo, boolean isNetWorkError) {
                        ToastUtils.showShort(errorInfo);
                        dismissLoadingDialog();
                    }
                });
    }


    private void showSuccessDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(InvoiceActivity.this);
        View view = View
                .inflate(InvoiceActivity.this, R.layout.invoice_submit_success, null);
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
