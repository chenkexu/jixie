package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.NormalListDialog;
import com.orhanobut.logger.Logger;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.api.ApiManager;
import com.qmwl.zyjx.api.ApiResponse;
import com.qmwl.zyjx.api.BaseObserver;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.bean.DingDanBean;
import com.qmwl.zyjx.bean.KuaidiListBean;
import com.qmwl.zyjx.utils.RxUtil;
import com.qmwl.zyjx.utils.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2017/7/20.
 * 物流界面
 */

public class ReturnWuliuActivity extends BaseActivity {




    // 服务选择弹窗
    OptionsPickerView serviceOptions;
    @BindView(R.id.base_top_bar_back)
    ImageView baseTopBarBack;
    @BindView(R.id.base_top_bar_title)
    TextView baseTopBarTitle;
    @BindView(R.id.base_top_bar_right2)
    ImageView baseTopBarRight2;
    @BindView(R.id.base_top_bar_right)
    ImageView baseTopBarRight;
    @BindView(R.id.base_top_bar_righttext)
    TextView baseTopBarRighttext;
    @BindView(R.id.et_wuliu)
    EditText etWuliu;
    @BindView(R.id.et_wuliu_id)
    EditText etWuliu_id;
    private DingDanBean dingDanBean;
    private List<String> wuliu_name;
    private List<String> wuliu_word;
    private int choosePosition = 0;
    private boolean isClickH5=false;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.activity_wuliu);
        ButterKnife.bind(this);
    }



    @Override
    protected void initView() {
        setTitleContent(R.string.msg_return_thing_info);
        etWuliu.setCursorVisible(false);
        etWuliu.setFocusable(false);
        etWuliu.setFocusableInTouchMode(false);
        Intent intent = getIntent();
        dingDanBean = (DingDanBean) intent.getSerializableExtra("DingDanBean");
        isClickH5=intent.getBooleanExtra("isH5",false);
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {

    }




    @OnClick({R.id.et_wuliu,R.id.btn_user_feedback_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_wuliu:
                showLoadingDialog();
                ApiManager.getInstence().getApiService()
                        .kuaidiList()
                        .compose(RxUtil.<ApiResponse<KuaidiListBean>>rxSchedulerHelper())
                        .subscribe(new BaseObserver<KuaidiListBean>() {
                            @Override
                            protected void onSuccees(ApiResponse<KuaidiListBean> t) {
                                dismissLoadingDialog();
                                KuaidiListBean data = t.getData();
                                List<List<String>> niu_index_response = data.getNiu_index_response();
                                wuliu_word = niu_index_response.get(0);
                                wuliu_name = niu_index_response.get(1);

                                final String[] items = wuliu_name.toArray(new String[wuliu_name.size()]);

                                final NormalListDialog normalListDialog = new NormalListDialog(ReturnWuliuActivity.this, items);
                                String titleStr = ReturnWuliuActivity.this.getResources().getString(R.string.choose_wuliu_title);
                                normalListDialog.title(titleStr);
                                normalListDialog.show();
                                normalListDialog.setOnOperItemClickL(new OnOperItemClickL() {
                                    @Override
                                    public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        choosePosition = position;
                                        Logger.d(position);
                                        etWuliu.setText(items[position]);
                                        normalListDialog.dismiss();
                                    }
                                });
                            }


                            @Override
                            protected void onFailure(String errorInfo, boolean isNetWorkError) {
                                dismissLoadingDialog();
                            }
                        });

//                /showServicePickView(listProductData);
                break;
            case R.id.btn_user_feedback_submit:
                String etWuliuStr = etWuliu.getText().toString();

                String titleStr = ReturnWuliuActivity.this.getResources().getString(R.string.wuliu_input_hint);
                if (TextUtils.isEmpty(etWuliuStr)) {
                    ToastUtils.showShort(titleStr);
                    return;
                }


                String etwuliuId = etWuliu_id.getText().toString();
                String titleStr2 = ReturnWuliuActivity.this.getResources().getString(R.string.input_wuliu_id);

                if (TextUtils.isEmpty(etwuliuId)) {
                    ToastUtils.showShort(titleStr2);
                    return;
                }


                showLoadingDialog();
                String s = wuliu_word.get(choosePosition);
                String mOid="";
                if (isClickH5){
                    mOid=getIntent().getStringExtra("orderId");
                }else {
                    mOid=dingDanBean.getOrder_id();
                }

                ApiManager.getInstence().getApiService()
                        .addTuiHuoWuLiu(mOid,etwuliuId,s)
                        .compose(RxUtil.<ApiResponse<Object>>rxSchedulerHelper())
                        .subscribe(new BaseObserver<Object>() {
                            @Override
                            protected void onSuccees(ApiResponse<Object> t) {
                                dismissLoadingDialog();
                                ToastUtils.showLong(ReturnWuliuActivity.this.getResources().getString(R.string.wuliu_submit_success));
                                finish();
                            }

                            @Override
                            protected void onFailure(String errorInfo, boolean isNetWorkError) {
                                dismissLoadingDialog();
                                ToastUtils.showShort(errorInfo);
                            }
                        });
                break;

        }
    }




    @Override
    protected void onMyClick(View v) {


    }












    private void showPicView() {
        serviceOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {

            }
        }).setTitleText(getString(R.string.wuliugsmc))
                .setContentTextSize(12)//设置滚轮文字大小
                .setTitleSize(14) // 标题文字大小
                .setSubCalSize(14) // 取消确定按钮文字大小
                .setDividerColor(Color.LTGRAY)//设置分割线的颜色
                .setSelectOptions(0)//默认选中项

                .setLineSpacingMultiplier(3.0F) // 行间距
                .setBgColor(Color.WHITE)
                .setTitleBgColor(Color.WHITE)
                .setTitleColor(getResources().getColor(R.color.c666666))
                .setCancelColor(getResources().getColor(R.color.c666666))
                .setSubmitColor(getResources().getColor(R.color.c666666))
                .setTextColorCenter(getResources().getColor(R.color.c666666))
                //  .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                //.setBackgroundId(getResources().getColor(R.color.tr)) //设置外部遮罩颜色
                .build();
        //  serviceOptions.setPicker(data);//一选择器
        serviceOptions.show();
    }




}
