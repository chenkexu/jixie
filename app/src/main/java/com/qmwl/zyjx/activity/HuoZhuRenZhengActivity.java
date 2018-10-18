package com.qmwl.zyjx.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.GlideUtils;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.view.CommomDialog;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2017/8/29.
 * 货主认证
 */

public class HuoZhuRenZhengActivity extends BaseActivity {
    private ImageView shenfenzhengIv;
    private RadioButton tijiaoziliaoRadiobutton;
    private RadioButton pingtaishenheRadiobutton;
    private EditText nameEditText;
    private EditText callEditText;
    private EditText shenfenzhengEditText;
    private String shenfenzhengImagePath = "";

    private View tianxieziliaoView;
    private View zhengzaishenheView;
    private View shenhetongguoView;
    private View shenheweitongguoView;
    private ImgSelConfig config;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.huozhurenzheng_activity_layout);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.huozhurenzheng);
        findViewById(R.id.huozhurenzheng_layout_submit).setOnClickListener(this);
        shenfenzhengIv = (ImageView) findViewById(R.id.huozhurenzheng_layout_shenfenzhengzhao);
        shenfenzhengIv.setOnClickListener(this);

        tijiaoziliaoRadiobutton = (RadioButton) findViewById(R.id.huozhurenzheng_tijiaoziliao_button);
        pingtaishenheRadiobutton = (RadioButton) findViewById(R.id.huozhurenzheng_pingtaishenhe_button);
        nameEditText = (EditText) findViewById(R.id.huozhurenzheng_edittext_name);
        callEditText = (EditText) findViewById(R.id.huozhurenzheng_edittext_call);
        shenfenzhengEditText = (EditText) findViewById(R.id.huozhurenzheng_edittext_shenfenzhenghao);

        tianxieziliaoView = findViewById(R.id.huozhurenzheng_container_tianxieziliao);
        zhengzaishenheView = findViewById(R.id.huozhurenzheng_container_zhengzaishenhe);
        shenhetongguoView = findViewById(R.id.huozhurenzheng_container_shenghetongguo);
        shenheweitongguoView = findViewById(R.id.huozhurenzheng_container_shengheweitongguo);

        findViewById(R.id.huozhurenzheng_layout_qufahuo).setOnClickListener(this);
        findViewById(R.id.huozhurenzheng_layout_chongxintijiaoziliao).setOnClickListener(this);
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {
        getState();
    }

    @Override
    protected void onMyClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.huozhurenzheng_layout_submit:
                submit();
                break;
            case R.id.huozhurenzheng_layout_shenfenzhengzhao:
                startPhoto(2);
                break;
            case R.id.huozhurenzheng_layout_qufahuo:
                //去发货
                intent = new Intent(this, WoYaoFaHuoActivity.class);
                startActivity(intent);
                break;
            case R.id.huozhurenzheng_layout_chongxintijiaoziliao:
                //重新提交资料
                shenheweitongguoView.setVisibility(View.GONE);
                tijiaoziliaoRadiobutton.setChecked(true);
                tianxieziliaoView.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void showStateContainer(int code) {
//        0为审核中，1成功，2失败
        switch (code) {
            case 0:
                pingtaishenheRadiobutton.setChecked(true);
                zhengzaishenheView.setVisibility(View.VISIBLE);
                break;
            case 1:
                pingtaishenheRadiobutton.setChecked(true);
                shenhetongguoView.setVisibility(View.VISIBLE);
                break;
            case 2:
                pingtaishenheRadiobutton.setChecked(true);
                shenheweitongguoView.setVisibility(View.VISIBLE);
                break;
            case 3:
                tijiaoziliaoRadiobutton.setChecked(true);
                tianxieziliaoView.setVisibility(View.VISIBLE);
                break;
        }
    }

    private void getState() {
        showLoadingDialog();
        AndroidNetworking.get(Contact.huozhurenzhengstate + "?uid=" + MyApplication.getIntance().userBean.getUid())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        int state = JsonUtils.parseShenHeState(response);
                        showStateContainer(state);
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                    }
                });
    }

    // 自定义图片加载器
    private ImageLoader loader = new ImageLoader() {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            // TODO 在这边可以自定义图片加载库来加载ImageView，例如Glide、Picasso、ImageLoader等
            Glide.with(context).load(path).into(imageView);
        }
    };

    //选择照片
    private void startPhoto(int code) {
        // 自由配置选项
        if (config == null) {
            config = new ImgSelConfig.Builder(this, loader)
                    // 是否多选, 默认true
                    .multiSelect(false)
                    // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                    .rememberSelected(false)
                    // “确定”按钮背景色
                    .btnBgColor(Color.GRAY)
                    // “确定”按钮文字颜色
                    .btnTextColor(Color.BLUE)
                    // 使用沉浸式状态栏
                    .statusBarColor(Color.parseColor("#3F51B5"))
                    // 返回图标ResId
                    .backResId(R.mipmap.left_btn_white)
                    // 标题
                    .title("图片")
                    // 标题文字颜色
                    .titleColor(Color.WHITE)
                    // TitleBar背景色
                    .titleBgColor(Color.parseColor("#3F51B5"))
                    // 裁剪大小。needCrop为true的时候配置
                    .cropSize(1, 1, 200, 200)
                    .needCrop(false)
                    // 第一个是否显示相机，默认true
                    .needCamera(true)
                    // 最大选择图片数量，默认9
                    .maxNum(1)
                    .build();
        }
        ImgSelActivity.startActivity(this, config, code);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            String imagePath = pathList.get(0);
            switch (requestCode) {
                case 2:
                    shenfenzhengImagePath = imagePath;
                    GlideUtils.openImagePhoto(this, shenfenzhengImagePath, shenfenzhengIv);
                    break;

            }
        }
    }

    //提交资料
    private void submit() {
        String nameStr = nameEditText.getText().toString().trim();
        String callStr = callEditText.getText().toString().trim();
        String shenfenzhenghaoStr = shenfenzhengEditText.getText().toString().trim();
        if (isStringsNull(new String[]{nameStr, callStr, shenfenzhenghaoStr, shenfenzhengImagePath})) {
            Toast.makeText(this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
            return;
        }
        File file = new File(shenfenzhengImagePath);
        ANRequest.MultiPartBuilder upload = AndroidNetworking.upload(Contact.huozhurenzheng);
//        ANRequest.MultiPartBuilder upload = AndroidNetworking.upload("http://shopnc.rabxdl.cn/index.php/api/transport/ownerAuthen");
        upload.addMultipartParameter("uid", MyApplication.getIntance().userBean.getUid())
                .addMultipartParameter("name", nameStr)
                .addMultipartParameter("mobile", callStr)
                .addMultipartParameter("card", shenfenzhenghaoStr)
                .addMultipartParameter("addtime", String.valueOf(System.currentTimeMillis()))
                .addMultipartFile("myfile", file)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        try {
                            if (JsonUtils.is100Success(response)) {
                                new CommomDialog(HuoZhuRenZhengActivity.this, R.style.dialog, getString(R.string.dengdaishenhe_hetong), new CommomDialog.OnCloseListener() {
                                    @Override
                                    public void onClick(Dialog dialog, boolean confirm) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                }).setHideCancelButton().show();
                            } else {
                                new CommomDialog(HuoZhuRenZhengActivity.this, R.style.dialog, getString(R.string.tijiaoshibai_chongshi), new CommomDialog.OnCloseListener() {
                                    @Override
                                    public void onClick(Dialog dialog, boolean confirm) {
                                        dialog.dismiss();
                                    }
                                }).setHideCancelButton().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            new CommomDialog(HuoZhuRenZhengActivity.this, R.style.dialog, getString(R.string.tijiaoshibai_chongshi), new CommomDialog.OnCloseListener() {
                                @Override
                                public void onClick(Dialog dialog, boolean confirm) {
                                    dialog.dismiss();
                                }
                            }).setHideCancelButton().show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                        new CommomDialog(HuoZhuRenZhengActivity.this, R.style.dialog, getString(R.string.tijiaoshibai_chongshi), new CommomDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                dialog.dismiss();
                            }
                        }).setHideCancelButton().show();
                    }
                });
        showLoadingDialog();
    }

}
