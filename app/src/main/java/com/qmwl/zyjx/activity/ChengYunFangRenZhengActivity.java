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
 * 承运方认证企业
 */

public class ChengYunFangRenZhengActivity extends BaseActivity {

    public static final int YINGYEZHIZHAO_CODE = 1;
    public static final int DAOLUXUKEZHNEG_CODE = 2;
    private ImageView yingyezhizhaoIv;
    private ImageView daoluxukezhengIv;
    private EditText lianxirenEdittext;
    private EditText jinjihaomaEdittext;
    private EditText qiyemingchengEdittext;
    private EditText daoluyunshuxukezhenghaoEdittext;
    private String yingyezhizhaoImagePath = "";
    private String daoluyunshuzhengImagePath = "";
    private View tijiaoziliaoView;
    private View zhengzaishenheView;
    private View shenhetongguoView;
    private View shenheweitongguo;
    private RadioButton tijiaoziliaoRaiobutton;
    private RadioButton pingtaishenheRaiobutton;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.chengyunfangrenzheng_qiye);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.chengyunfangrenzheng);

        lianxirenEdittext = (EditText) findViewById(R.id.chengyunfangrenzheng_layout_lianxiren);
        jinjihaomaEdittext = (EditText) findViewById(R.id.chengyunfangrenzheng_layout_jinjilianxihaoma);
        qiyemingchengEdittext = (EditText) findViewById(R.id.chengyunfangrenzheng_layout_qiyemingcheng);
        daoluyunshuxukezhenghaoEdittext = (EditText) findViewById(R.id.chengyunfangrenzheng_layout_daoluxukezhenghao);
        findViewById(R.id.chengyunfangrenzheng_layout_submit).setOnClickListener(this);

        yingyezhizhaoIv = (ImageView) findViewById(R.id.chengyunfangrenzheng_layout_yingyezhizhao_iv);
        daoluxukezhengIv = (ImageView) findViewById(R.id.chengyunfangrenzheng_layout_daoluxukezheng_iv);
        yingyezhizhaoIv.setOnClickListener(this);
        daoluxukezhengIv.setOnClickListener(this);

        tijiaoziliaoRaiobutton = (RadioButton) findViewById(R.id.chengyunfangrenzheng_radiobutton_tijiaoziliao);
        pingtaishenheRaiobutton = (RadioButton) findViewById(R.id.chengyunfangrenzheng_radiobutton_pingtaishenhe);

        tijiaoziliaoView = findViewById(R.id.chengyunfangrenzheng_container_tijiaoziliao);
        zhengzaishenheView = findViewById(R.id.chengyunfangrenzheng_container_zhengzaishenhe);
        shenhetongguoView = findViewById(R.id.chengyunfangrenzheng_container_shenghetongguo);
        shenheweitongguo = findViewById(R.id.chengyunfangrenzheng_container_shengheweitongguo);

        findViewById(R.id.chengyunfangrenzheng_layout_qujiedan).setOnClickListener(this);
        findViewById(R.id.chengyunfangrenzheng_layout_chongxintijiaoziliao).setOnClickListener(this);

    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {
        //获取认证状态
        getShenHeState();
    }


    @Override
    protected void onMyClick(View v) {
        switch (v.getId()) {
            case R.id.chengyunfangrenzheng_layout_submit:
                //提交资料
                submit();
                break;
            case R.id.chengyunfangrenzheng_layout_yingyezhizhao_iv:
                //营业执照
                startPhoto(YINGYEZHIZHAO_CODE);
                break;
            case R.id.chengyunfangrenzheng_layout_daoluxukezheng_iv:
                //道路许可证
                startPhoto(DAOLUXUKEZHNEG_CODE);
                break;
            case R.id.chengyunfangrenzheng_layout_qujiedan:
                //去接单
                Intent intent = new Intent(this, HuoYuanActivity.class);
                intent.putExtra(HuoYuanActivity.IS_TUIJIAN,false);
                startActivity(intent);
                finish();
                break;
            case R.id.chengyunfangrenzheng_layout_chongxintijiaoziliao:
                //重新提交
                showStateContainer(3);
                break;
        }
    }

    //认证状态
    private int statue = -1;

    private void getShenHeState() {
//        AndroidNetworking.get(Contact.chengyunfangrenzhengstate + "?uid=" + MyApplication.getIntance().userBean.getUid())
        AndroidNetworking.get(Contact.yunshu_chengyun_qieye_statue + "?uid=" + MyApplication.getIntance().userBean.getUid())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        try {
                            int code = response.getInt("code");
                            if (code == -50) {
                                statue = 3;
                            } else {
                                JSONObject data = response.getJSONObject("data");
                                JSONObject niu_index_response = data.getJSONObject("niu_index_response");
                                statue = niu_index_response.getInt("state");
                            }
                            showStateContainer(statue);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ChengYunFangRenZhengActivity.this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                        Toast.makeText(ChengYunFangRenZhengActivity.this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
        showLoadingDialog();
    }

    private void showStateContainer(int code) {
//        状态state 0 审核 1成功，2不通过
        switch (code) {
            case 0:
                pingtaishenheRaiobutton.setChecked(true);
                zhengzaishenheView.setVisibility(View.VISIBLE);
                break;
            case 2:
                pingtaishenheRaiobutton.setChecked(true);
                shenheweitongguo.setVisibility(View.VISIBLE);
                break;
            case 1:
                pingtaishenheRaiobutton.setChecked(true);
                shenhetongguoView.setVisibility(View.VISIBLE);
                break;
            case 3:
                tijiaoziliaoRaiobutton.setChecked(true);
                tijiaoziliaoView.setVisibility(View.VISIBLE);
                break;
        }

    }

    private void submit() {
        String lianxirenStr = lianxirenEdittext.getText().toString().trim();
        String jinjilianxiStr = jinjihaomaEdittext.getText().toString().trim();
        String qiyemingchengStr = qiyemingchengEdittext.getText().toString().trim();
        String daoluyunshuxukehzhengStr = daoluyunshuxukezhenghaoEdittext.getText().toString().trim();
        if (isStringsNull(new String[]{lianxirenStr, jinjilianxiStr, qiyemingchengStr, daoluyunshuxukehzhengStr, yingyezhizhaoImagePath, daoluyunshuzhengImagePath})) {
            Toast.makeText(this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
            return;
        }
        AndroidNetworking.upload(Contact.yunshu_chengyun_qiye_renzheng)
                .addMultipartParameter("uid", MyApplication.getIntance().userBean.getUid())
                .addMultipartParameter("name", lianxirenStr)
                .addMultipartParameter("mobile", jinjilianxiStr)
                .addMultipartParameter("cname", qiyemingchengStr)
                .addMultipartParameter("allownum", daoluyunshuxukehzhengStr)
                .addMultipartFile("licpic", new File(yingyezhizhaoImagePath))
                .addMultipartFile("allowpic", new File(daoluyunshuzhengImagePath))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        try {
                            if (JsonUtils.is100Success(response)) {
                                new CommomDialog(ChengYunFangRenZhengActivity.this, R.style.dialog, getString(R.string.dengdaishenhe_hetong), new CommomDialog.OnCloseListener() {
                                    @Override
                                    public void onClick(Dialog dialog, boolean confirm) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                }).setHideCancelButton().show();
                            } else {
                                new CommomDialog(ChengYunFangRenZhengActivity.this, R.style.dialog, getString(R.string.tijiaoshibai_chongshi), new CommomDialog.OnCloseListener() {
                                    @Override
                                    public void onClick(Dialog dialog, boolean confirm) {
                                        dialog.dismiss();
                                    }
                                }).setHideCancelButton().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            new CommomDialog(ChengYunFangRenZhengActivity.this, R.style.dialog, getString(R.string.tijiaoshibai_chongshi), new CommomDialog.OnCloseListener() {
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
                        new CommomDialog(ChengYunFangRenZhengActivity.this, R.style.dialog, getString(R.string.tijiaoshibai_chongshi), new CommomDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                dialog.dismiss();
                            }
                        }).setHideCancelButton().show();
                    }
                });
        showLoadingDialog();
    }


    // 自定义图片加载器
    private ImageLoader loader = new ImageLoader() {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            // TODO 在这边可以自定义图片加载库来加载ImageView，例如Glide、Picasso、ImageLoader等
            Glide.with(context).load(path).into(imageView);
        }
    };

    ImgSelConfig config;

    //选择照片
    private void startPhoto(int code) {
        // 自由配置选项
        if (config == null) {
            config = new ImgSelConfig.Builder(this, loader)
                    // 是否多选, 默认true
                    .multiSelect(false)
                    // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                    .rememberSelected(true)
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
//            String imagePath   = data.getStringExtra(ImgSelActivity.INTENT_RESULT);

            switch (requestCode) {
                case YINGYEZHIZHAO_CODE:
                    //营业执照
                    yingyezhizhaoImagePath = imagePath;
                    GlideUtils.openImagePhoto(this, imagePath, yingyezhizhaoIv);
                    break;
                case DAOLUXUKEZHNEG_CODE:
                    //道路运输证许可证
                    daoluyunshuzhengImagePath = imagePath;
                    GlideUtils.openImagePhoto(this, imagePath, daoluxukezhengIv);
                    break;
            }
        } else {
            Toast.makeText(this, "未选择图片", Toast.LENGTH_SHORT).show();
        }
    }

}
