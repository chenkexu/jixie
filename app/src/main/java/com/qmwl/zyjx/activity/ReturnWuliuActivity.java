package com.qmwl.zyjx.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bigkoo.pickerview.OptionsPickerView;

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
 *物流界面
 */

public class ReturnWuliuActivity extends BaseActivity {

   /* @BindView(R.id.dingdan_layout_item_iv)
    ImageView mIv;
    @BindView(R.id.dingdan_layout_item_name)
    TextView mTv;

    private AskRetunPayDialog mAskRpDialog;
*/

    // 服务选择弹窗
    OptionsPickerView serviceOptions;
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

    @OnClick({R.id.et_wuliu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.et_wuliu:
                ApiManager.getInstence().getApiService()
                        .kuaidiList()
                        .compose(RxUtil.<ApiResponse<Object>>rxSchedulerHelper())
                        .subscribe(new BaseObserver<Object>() {
                            @Override
                            protected void onSuccees(ApiResponse t) {
                                Log.d("huangrui","快递列表获取成功"+t.getMessage());
                                //  showSuccessDialog();
                                ToastUtils.showShort(getResources().getString(R.string.ask_suc));
                            }

                            @Override
                            protected void onFailure(String errorInfo, boolean isNetWorkError) {
                                //ToastUtils.showShort(errorInfo);
                                Log.d("huangrui","快递列表获取失败");
                            }
                        });
                ///showServicePickView(listProductData);
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

    private void showPicView(){
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
