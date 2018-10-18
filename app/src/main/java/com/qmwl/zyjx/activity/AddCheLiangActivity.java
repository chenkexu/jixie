package com.qmwl.zyjx.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.qmwl.zyjx.bean.CheLiangBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.GlideUtils;
import com.qmwl.zyjx.utils.JsonUtils;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 * 运输，添加车辆和修改车辆公用一个页面
 */

public class AddCheLiangActivity extends BaseActivity {
    public static final String XIUGAI = "com.gh.xiugaicheliang_message";
    public static final String ID_ACTION = "com.gh.xiugaicheliang_id";
    //是否是修改页面
    private boolean isXiuGai = false;
    //车辆信息id
    private String id = "";

    public static final int daoluyunshuzheng_code = 1;
    public static final int cheqianzhao_code = 2;
    public static final int chetouzhao_code = 3;
    public static final int guachezhao_code = 4;
    public static final int chexing_code = 5;
    public static final int chechang_code = 6;

    private ImageView daoluyunshuzhengIv;
    private ImageView cheqianqingxizhengIv;
    private ImageView chetouqingxizhaopianIv;
    private ImageView guachexingshizhengzhaopainIv;

    private String daoluyunshuzhengImagePath = "";
    private String cheqianqingxizhengImagePath = "";
    private String chetouqingxizhaopianImagePath = "";
    private String guachexingshizhengzhaopainImagePath = "";

    private EditText chepaihaoEdittext;
    private EditText daoluyunshuzhengzihaoEdittext;
    private EditText chekuanEdittext;
    private EditText chegaoEdittext;
    private EditText zuidachengzailiEdittext;
    private EditText pipeisijiEdittext;
    private EditText sijilianxidianhuaEdittext;
    private TextView chexingTv;
    private TextView chechangTv;
    private Button submitButton;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.addcheliang_layout);
    }

    @Override
    protected void initView() {
        isXiuGai = getIntent().getBooleanExtra(XIUGAI, false);

        chepaihaoEdittext = (EditText) findViewById(R.id.addcheliang_layout_edittext_chepaihao);
        daoluyunshuzhengzihaoEdittext = (EditText) findViewById(R.id.addcheliang_layout_edittext_daoluyunshuzhengzihao);
        chekuanEdittext = (EditText) findViewById(R.id.addcheliang_layout_edittext_chekuan);
        chegaoEdittext = (EditText) findViewById(R.id.addcheliang_layout_edittext_chegao);
        zuidachengzailiEdittext = (EditText) findViewById(R.id.addcheliang_layout_edittext_zuidachengzaidongli);
        pipeisijiEdittext = (EditText) findViewById(R.id.addcheliang_layout_edittext_pipeisiji);
        sijilianxidianhuaEdittext = (EditText) findViewById(R.id.addcheliang_layout_edittext_sijilianxidianhua);

        chexingTv = (TextView) findViewById(R.id.addcheliang_layout_chexing);
        chechangTv = (TextView) findViewById(R.id.addcheliang_layout_chechang);

        findViewById(R.id.addcheliang_layout_chexing_container).setOnClickListener(this);
        findViewById(R.id.addcheliang_layout_chechang_container).setOnClickListener(this);

        submitButton = (Button) findViewById(R.id.addcheliang_layout_submit);
        submitButton.setOnClickListener(this);
        daoluyunshuzhengIv = (ImageView) findViewById(R.id.addcheliang_layout_edittext_daoluyunshuzhengzhaopian_iv);
        cheqianqingxizhengIv = (ImageView) findViewById(R.id.addcheliang_layout_edittext_cheqianqingxizhaopian_iv);
        chetouqingxizhaopianIv = (ImageView) findViewById(R.id.addcheliang_layout_edittext_chetouxingshizhengzhaopian_iv);
        guachexingshizhengzhaopainIv = (ImageView) findViewById(R.id.addcheliang_layout_edittext_guachexingshizhengqingxizhaopian_iv);
        daoluyunshuzhengIv.setOnClickListener(this);
        cheqianqingxizhengIv.setOnClickListener(this);
        chetouqingxizhaopianIv.setOnClickListener(this);
        guachexingshizhengzhaopainIv.setOnClickListener(this);

        if (isXiuGai) {
            id = getIntent().getStringExtra(ID_ACTION);
            submitButton.setText(getString(R.string.ok));
            setTitleContent(R.string.cheliangguanli);
            getData();
        } else {
            setTitleContent(R.string.tianjiacheliang);
        }
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {

    }

    private void getData() {
        AndroidNetworking.get(Contact.cheliangxinxi + "?id=" + id + "&uid=" + MyApplication.getIntance().userBean.getUid())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        CheLiangBean cheLiangBean = JsonUtils.parseCheliangSingle(response);
                        setData(cheLiangBean);
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                    }
                });
        showLoadingDialog();
    }

    //设置车辆修改信息的方法
    private void setData(CheLiangBean bean) {
        if (bean == null) {
            return;
        }
        chepaihaoEdittext.setText(bean.getPlate_number());
        daoluyunshuzhengzihaoEdittext.setText(bean.getRoad_number());
        daoluyunshuzhengImagePath = bean.getRoad_pic();
        GlideUtils.openImagePhoto(this, daoluyunshuzhengImagePath, daoluyunshuzhengIv);
        cheqianqingxizhengImagePath = bean.getHead_pic();
        GlideUtils.openImagePhoto(this, cheqianqingxizhengImagePath, cheqianqingxizhengIv);
        chetouqingxizhaopianImagePath = bean.getTravel_permit();
        GlideUtils.openImagePhoto(this, chetouqingxizhaopianImagePath, chetouqingxizhaopianIv);
        guachexingshizhengzhaopainImagePath = bean.getTrailer_permit();
        GlideUtils.openImagePhoto(this, guachexingshizhengzhaopainImagePath, guachexingshizhengzhaopainIv);

        chexingTv.setText(bean.getCar_models());
        chechangTv.setText(bean.getCar_long());
        chekuanEdittext.setText(bean.getCar_width());
        chegaoEdittext.setText(bean.getCar_height());
        zuidachengzailiEdittext.setText(bean.getMax_bearing());
        pipeisijiEdittext.setText(bean.getDriver());
        sijilianxidianhuaEdittext.setText(bean.getDriver_phone());
    }

    @Override
    protected void onMyClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.addcheliang_layout_edittext_daoluyunshuzhengzhaopian_iv:
                startPhoto(daoluyunshuzheng_code);
                break;
            case R.id.addcheliang_layout_edittext_cheqianqingxizhaopian_iv:
                startPhoto(cheqianzhao_code);
                break;
            case R.id.addcheliang_layout_edittext_chetouxingshizhengzhaopian_iv:
                startPhoto(chetouzhao_code);
                break;
            case R.id.addcheliang_layout_edittext_guachexingshizhengqingxizhaopian_iv:
                startPhoto(guachezhao_code);
                break;
            case R.id.addcheliang_layout_chexing_container:
                //车型
                intent = new Intent(this, XuanZeCheXingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivityForResult(intent, chexing_code);

                break;
            case R.id.addcheliang_layout_chechang_container:
                //车长
                intent = new Intent(this, XuanZeCheXingActivity.class);
                intent.putExtra(XuanZeCheXingActivity.TYPE, true);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivityForResult(intent, chechang_code);
                break;

            case R.id.addcheliang_layout_submit:
                //确定按钮
                addCheLiangmessage();
                break;
        }
    }

    private void addCheLiangmessage() {
        String chepaihaoStr = chepaihaoEdittext.getText().toString().trim();
        String daoluynshuzhengStr = daoluyunshuzhengzihaoEdittext.getText().toString().trim();
        String chekuanStr = chekuanEdittext.getText().toString().trim();
        String chegaoStr = chegaoEdittext.getText().toString().trim();
        String zuidachengzailiStr = zuidachengzailiEdittext.getText().toString().trim();
        String pipeisijiStr = pipeisijiEdittext.getText().toString().trim();
        String sijilianxidianhuaStr = sijilianxidianhuaEdittext.getText().toString().trim();
        String chexingStr = chexingTv.getText().toString().trim();
        String chechangStr = chechangTv.getText().toString().trim();

        if (isStringsNull(new String[]{chepaihaoStr, daoluynshuzhengStr, zuidachengzailiStr,
                pipeisijiStr, sijilianxidianhuaStr, chexingStr, chechangStr, daoluyunshuzhengImagePath, chetouqingxizhaopianImagePath, guachexingshizhengzhaopainImagePath,})) {

            Toast.makeText(this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
            return;
        }
        ANRequest.MultiPartBuilder upload = null;
        if (isXiuGai) {
            upload = AndroidNetworking.upload(Contact.xiugaicheliangxinxi);
        } else {
            upload = AndroidNetworking.upload(Contact.chengyunrentianjiacheliang);
        }
        upload.addMultipartParameter("uid", MyApplication.getIntance().userBean.getUid())
                .addMultipartParameter("plate_number", chepaihaoStr)
                .addMultipartParameter("road_number", daoluynshuzhengStr)
                .addMultipartParameter("car_models", chexingStr)
                .addMultipartParameter("car_long", chechangStr)
                .addMultipartParameter("car_width", chekuanStr)
                .addMultipartParameter("car_height", chegaoStr)
                .addMultipartParameter("max_bearing", zuidachengzailiStr)
                .addMultipartParameter("driver", pipeisijiStr)
                .addMultipartParameter("driver_phone", sijilianxidianhuaStr);
        File daoluFile = new File(daoluyunshuzhengImagePath);
        if (daoluFile.exists()) {
            upload.addMultipartFile("road_pic", daoluFile);
        }
        File cheqianFile = new File(cheqianqingxizhengImagePath);
        if (cheqianFile.exists()) {
            upload.addMultipartFile("head_pic", cheqianFile);
        }
        File chetouFile = new File(chetouqingxizhaopianImagePath);
        if (chetouFile.exists()) {
            upload.addMultipartFile("travel_permit", chetouFile);
        }
        File guacheFile = new File(guachexingshizhengzhaopainImagePath);
        if (guacheFile.exists()) {
            upload.addMultipartFile("trailer_permit", guacheFile);
        }
        upload.build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        Log.i("TAG", "添加车辆：" + response.toString());
                        try {
                            if (JsonUtils.is100Success(response)) {
                                if (isXiuGai) {
                                    Toast.makeText(AddCheLiangActivity.this, getString(R.string.xiugaichenggong), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(AddCheLiangActivity.this, getString(R.string.tianjiachenggong), Toast.LENGTH_SHORT).show();
                                }
                                finish();
                            } else {
                                if (isXiuGai) {
                                    Toast.makeText(AddCheLiangActivity.this, getString(R.string.xiugaishibai), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(AddCheLiangActivity.this, getString(R.string.tianjiashibai), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            if (isXiuGai) {
                                Toast.makeText(AddCheLiangActivity.this, getString(R.string.xiugaishibai), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(AddCheLiangActivity.this, getString(R.string.tianjiashibai), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                        if (isXiuGai) {
                            Toast.makeText(AddCheLiangActivity.this, getString(R.string.xiugaishibai), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddCheLiangActivity.this, getString(R.string.tianjiashibai), Toast.LENGTH_SHORT).show();
                        }
                        Log.e("TAG", "添加车辆：" + anError.getErrorCode());
                    }
                });
        showLoadingDialog();

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
                case daoluyunshuzheng_code:
                    //道路运输证照片
                    daoluyunshuzhengImagePath = imagePath;
                    GlideUtils.openImagePhoto(this, imagePath, daoluyunshuzhengIv);
                    break;
                case cheqianzhao_code:
                    //车前清晰证照片
                    cheqianqingxizhengImagePath = imagePath;
                    GlideUtils.openImagePhoto(this, imagePath, cheqianqingxizhengIv);
                    break;
                case chetouzhao_code:
                    //车头照片
                    chetouqingxizhaopianImagePath = imagePath;
                    GlideUtils.openImagePhoto(this, imagePath, chetouqingxizhaopianIv);
                    break;
                case guachezhao_code:
                    // 挂车照片
                    guachexingshizhengzhaopainImagePath = imagePath;
                    GlideUtils.openImagePhoto(this, imagePath, guachexingshizhengzhaopainIv);
                    break;
                case chexing_code:
                    String chexingStr = data.getStringExtra(XuanZeCheXingActivity.DATA);
                    chexingTv.setText(chexingStr);
                    break;
                case chechang_code:
                    String chechangStr = data.getStringExtra(XuanZeCheXingActivity.DATA);
                    chechangTv.setText(chechangStr);
                    break;
            }
        } else {
//            Toast.makeText(this, "未选择图片", Toast.LENGTH_SHORT).show();
        }
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
                    .title("picture")
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

}
