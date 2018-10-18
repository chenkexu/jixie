package com.qmwl.zyjx.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
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
 * 承运人认证
 */

public class ChengYunRenRenZhengActivity extends BaseActivity {

    //驾驶证照片
    public static final int JIASHIZHENG_CODE = 1;
    //道路运输证照片
    public static final int DAOLUYUNSHU_CODE = 2;
    //车前清晰照片
    public static final int CHEQIANZHAO_CODE = 3;
    //车头清晰照片
    public static final int CHETOUZHAO_CODE = 4;
    //挂车清晰照片
    public static final int GUACHEZHAO_CODE = 5;
    //车型选择
    public static final int CHEXING_CODE = 6;
    //车长选择
    public static final int CHECHANG_CODE = 7;

    private ImageView jiashizhengIv;
    private ImageView daoluyunshuzhengIv;
    private ImageView cheqianqingxizhaopianIv;
    private ImageView chetouqingxizhaopianIv;
    private ImageView guacheqingxizhaopianIv;

    private String jiashizhengImageStr = "";
    private String daoluyunshuzhengImageStr = "";
    private String cheqianzhaoImageStr = "";
    private String chetouzhaoImageStr = "";
    private String guachezhaoImageStr = "";


    private ImgSelConfig config;
    private EditText congyezigezhengxinxizhenghaoEdittext;
    private EditText jinjilianxihaomaEdittext;
    private EditText chepaihaoEdittext;
    private EditText daoluyunshuzhneghaoEdittext;
    private EditText chekuanEdittext;
    private EditText chegaoEdittext;
    private EditText zuidachengzaiEdittext;
    private TextView chexingTv;
    private TextView chechangTv;
    private View tijiaoziliaoView;
    private View zhengzaishenheView;
    private View shenhetongguoView;
    private View shenheweitongguo;
    private RadioButton tijiaoziliaoRaiobutton;
    private RadioButton pingtaishenheRaiobutton;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.chengyunrenrenzheng_layout);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.chengyunrenrenzheng);

        congyezigezhengxinxizhenghaoEdittext = (EditText) findViewById(R.id.chengyunrenrenzheng_congyezigexinxizhenghao_edittext);
        jinjilianxihaomaEdittext = (EditText) findViewById(R.id.chengyunrenrenzheng_jinjilianxihaoma_edittext);
        chepaihaoEdittext = (EditText) findViewById(R.id.chengyunrenrenzheng_chepaihao_edittext);
        daoluyunshuzhneghaoEdittext = (EditText) findViewById(R.id.chengyunrenrenzheng_daoluyunshuzhengzihao_edittext);
        chekuanEdittext = (EditText) findViewById(R.id.chengyunrenrenzheng_chekuan_edittext);
        chegaoEdittext = (EditText) findViewById(R.id.chengyunrenrenzheng_chegao_edittext);
        zuidachengzaiEdittext = (EditText) findViewById(R.id.chengyunrenrenzheng_zuidachengzai_edittext);

        jiashizhengIv = (ImageView) findViewById(R.id.chengyunrenrenzheng_jiashizhengzhaopian_imageview);
        daoluyunshuzhengIv = (ImageView) findViewById(R.id.chengyunrenrenzheng_daoluyunshuzhengzhaopian_imageview);
        cheqianqingxizhaopianIv = (ImageView) findViewById(R.id.chengyunrenrenzheng_cheqianqingxizhaopian_imageview);
        chetouqingxizhaopianIv = (ImageView) findViewById(R.id.chengyunrenrenzheng_chetouqingxizhaopian_imageview);
        guacheqingxizhaopianIv = (ImageView) findViewById(R.id.chengyunrenrenzheng_guachexingshizhengqingxizhaopian_imageview);

        jiashizhengIv.setOnClickListener(this);
        daoluyunshuzhengIv.setOnClickListener(this);
        cheqianqingxizhaopianIv.setOnClickListener(this);
        chetouqingxizhaopianIv.setOnClickListener(this);
        guacheqingxizhaopianIv.setOnClickListener(this);
        findViewById(R.id.chengyunrenrenzheng_layout_submit).setOnClickListener(this);
        findViewById(R.id.chengyunrenrenzheng_chexing_container).setOnClickListener(this);
        findViewById(R.id.chengyunrenrenzheng_chechang_container).setOnClickListener(this);

        chexingTv = (TextView) findViewById(R.id.chengyunrenrenzheng_chexing_textview);
        chechangTv = (TextView) findViewById(R.id.chengyunrenrenzheng_chechang_textview);

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
        getShenHeState();
    }

    @Override
    protected void onMyClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.chengyunrenrenzheng_chexing_container:
                //车型
                intent = new Intent(this, XuanZeCheXingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivityForResult(intent, CHEXING_CODE);
                break;
            case R.id.chengyunrenrenzheng_chechang_container:
                //车长
                intent = new Intent(this, XuanZeCheXingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra(XuanZeCheXingActivity.TYPE, true);
                startActivityForResult(intent, CHECHANG_CODE);
                break;
            case R.id.chengyunrenrenzheng_jiashizhengzhaopian_imageview:
                //驾驶证照片
                startPhoto(JIASHIZHENG_CODE);
                break;
            case R.id.chengyunrenrenzheng_daoluyunshuzhengzhaopian_imageview:
                //道路运输证照片
                startPhoto(DAOLUYUNSHU_CODE);
                break;
            case R.id.chengyunrenrenzheng_cheqianqingxizhaopian_imageview:
                //车前照片
                startPhoto(CHEQIANZHAO_CODE);
                break;
            case R.id.chengyunrenrenzheng_chetouqingxizhaopian_imageview:
                //车头行驶证照片
                startPhoto(CHETOUZHAO_CODE);
                break;
            case R.id.chengyunrenrenzheng_guachexingshizhengqingxizhaopian_imageview:
                //挂车行驶证照片
                startPhoto(GUACHEZHAO_CODE);
                break;
            case R.id.chengyunrenrenzheng_layout_submit:
                submitData();
                break;
            case R.id.chengyunfangrenzheng_layout_qujiedan:
                //去接单
                intent = new Intent(this,HuoYuanActivity.class);
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
        AndroidNetworking.get(Contact.chengyunfangrenzhengstate + "?uid=" + MyApplication.getIntance().userBean.getUid())
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
                            Toast.makeText(ChengYunRenRenZhengActivity.this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                        Toast.makeText(ChengYunRenRenZhengActivity.this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
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

    private void submitData() {

        String congyezigezhenghaoStr = congyezigezhengxinxizhenghaoEdittext.getText().toString().trim();
        String lianxirenhaomaStr = jinjilianxihaomaEdittext.getText().toString().trim();
        String chepaihaoStr = chepaihaoEdittext.getText().toString().trim();
        String daoluyunshuzhenghaoStr = daoluyunshuzhneghaoEdittext.getText().toString().trim();
        String chekuanStr = chekuanEdittext.getText().toString().trim();
        String chegaoStr = chegaoEdittext.getText().toString().trim();
        String chexingStr = chexingTv.getText().toString().trim();
        String chechangStr = chechangTv.getText().toString().trim();
        String zuidachengzaiStr = zuidachengzaiEdittext.getText().toString().trim();

        if (isStringsNull(new String[]{lianxirenhaomaStr, jiashizhengImageStr, chepaihaoStr, daoluyunshuzhenghaoStr, daoluyunshuzhengImageStr, chetouzhaoImageStr, guachezhaoImageStr, chexingStr, chechangStr, zuidachengzaiStr})) {
            Toast.makeText(this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
            return;
        }

        ANRequest.MultiPartBuilder upload = AndroidNetworking.upload(Contact.yunshu_chengyunren_renzheng);
        upload.addMultipartParameter("uid", MyApplication.getIntance().userBean.getUid())
                .addMultipartParameter("cy_num", congyezigezhenghaoStr)
                .addMultipartParameter("mobile", lianxirenhaomaStr)
                .addMultipartParameter("car_number", chepaihaoStr)
                .addMultipartParameter("dlys_number", daoluyunshuzhenghaoStr)
                .addMultipartParameter("car_models", chexingStr)
                .addMultipartParameter("car_length", chechangStr)
                .addMultipartParameter("car_weight", chekuanStr)
                .addMultipartParameter("car_height", chegaoStr)
                .addMultipartParameter("bearing_power", zuidachengzaiStr)
                .addMultipartParameter("addtime", String.valueOf(System.currentTimeMillis()))
                .addMultipartFile("driver_pic", new File(jiashizhengImageStr))
                .addMultipartFile("dlys_pic", new File(daoluyunshuzhengImageStr))
                .addMultipartFile("car_headpic", new File(chetouzhaoImageStr))
                .addMultipartFile("trailer_pic", new File(guachezhaoImageStr));
        if (!"".equals(cheqianzhaoImageStr) && !TextUtils.isEmpty(cheqianzhaoImageStr)) {
            upload.addMultipartFile("car_pic", new File(cheqianzhaoImageStr));
        }
        showLoadingDialog();
        upload.build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        try {
                            if (JsonUtils.is100Success(response)) {
                                new CommomDialog(ChengYunRenRenZhengActivity.this, R.style.dialog, getString(R.string.dengdaishenhe_hetong), new CommomDialog.OnCloseListener() {
                                    @Override
                                    public void onClick(Dialog dialog, boolean confirm) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                }).setHideCancelButton().show();
                            } else {
                                new CommomDialog(ChengYunRenRenZhengActivity.this, R.style.dialog, getString(R.string.tijiaoshibai_chongshi), new CommomDialog.OnCloseListener() {
                                    @Override
                                    public void onClick(Dialog dialog, boolean confirm) {
                                        dialog.dismiss();
                                    }
                                }).setHideCancelButton().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            new CommomDialog(ChengYunRenRenZhengActivity.this, R.style.dialog, getString(R.string.tijiaoshibai_chongshi), new CommomDialog.OnCloseListener() {
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
                        new CommomDialog(ChengYunRenRenZhengActivity.this, R.style.dialog, getString(R.string.tijiaoshibai_chongshi), new CommomDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                dialog.dismiss();
                            }
                        }).setHideCancelButton().show();
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
            String imagePath = "";
            if (pathList != null) {
                imagePath = pathList.get(0);
            }
            switch (requestCode) {
                case JIASHIZHENG_CODE:
                    //驾驶证照片
                    jiashizhengImageStr = imagePath;
                    GlideUtils.openImagePhoto(this, imagePath, jiashizhengIv);
                    break;
                case DAOLUYUNSHU_CODE:
                    //道路运输证照片
                    daoluyunshuzhengImageStr = imagePath;
                    GlideUtils.openImagePhoto(this, imagePath, daoluyunshuzhengIv);
                    break;
                case CHEQIANZHAO_CODE:
                    //车前照片
                    cheqianzhaoImageStr = imagePath;
                    GlideUtils.openImagePhoto(this, imagePath, cheqianqingxizhaopianIv);
                    break;
                case CHETOUZHAO_CODE:
                    // 车头照片
                    chetouzhaoImageStr = imagePath;
                    GlideUtils.openImagePhoto(this, imagePath, chetouqingxizhaopianIv);
                    break;
                case GUACHEZHAO_CODE:
                    //挂车照片
                    guachezhaoImageStr = imagePath;
                    GlideUtils.openImagePhoto(this, imagePath, guacheqingxizhaopianIv);
                    break;
                case CHEXING_CODE:
                    String chexingStr = data.getStringExtra(XuanZeCheXingActivity.DATA);
                    chexingTv.setText(chexingStr);
                    break;
                case CHECHANG_CODE:
                    String chechangStr = data.getStringExtra(XuanZeCheXingActivity.DATA);
                    chechangTv.setText(chechangStr);
                    break;
            }
        } else {
            Toast.makeText(this, getString(R.string.weizuochuxuanze), Toast.LENGTH_SHORT).show();
        }
    }

}
