package com.qmwl.zyjx.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 郭辉 on 2018/1/17 15:34.
 * TODO:个体户开店
 */

public class RegisterPersonActivity extends BaseActivity {
    @BindView(R.id.card_number)
    EditText cardNumber;
    @BindView(R.id.card_name)
    EditText cardName;
    @BindView(R.id.open_bank)
    EditText openBank;
    @BindView(R.id.open_bank_address)
    EditText openBankAddress;


    private String zhengmianPath = "";
    private String fanmianPath = "";
    private ImageView fanmian;
    private ImageView zhengmian;
    //是否是重新提交数据
    private boolean isRest;
    private EditText dianpumingchengET;
    private EditText lianxidianhuaET;
    private EditText lianxirenET;
    private EditText youxiangET;
    private View chongxuanZhengMian;
    private View chongxuanFanMian;
    private ImageView zhengmianshili;
    private ImageView fanmianshili;


    @Override
    protected void setLayout() {
        setContentLayout(R.layout.activity_layout_register_person);
        ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.gerenkaidian);
        zhengmian = (ImageView) findViewById(R.id.id_register_person_shenfenzhengzhengmian);
        fanmian = (ImageView) findViewById(R.id.id_register_person_shenfenzhengfanmian);

        dianpumingchengET = (EditText) findViewById(R.id.id_register_person_dianpumingcheng);
        lianxidianhuaET = (EditText) findViewById(R.id.id_register_person_dianpudianhua);
        lianxirenET = (EditText) findViewById(R.id.id_register_person_lianxiren);
        youxiangET = (EditText) findViewById(R.id.id_register_person_youxiang);

        chongxuanZhengMian = findViewById(R.id.id_register_person_shenfenzhengzhengmian_chongxinxuanze);
        chongxuanFanMian = findViewById(R.id.id_register_person_shenfenzhengfanmian_chongxinxuanze);

        zhengmianshili = (ImageView) findViewById(R.id.id_register_person_shenfenzhengzhengmian_shili);
        fanmianshili = (ImageView) findViewById(R.id.id_register_person_shenfenzhengfanmian_shili);

        zhengmian.setOnClickListener(this);
        fanmian.setOnClickListener(this);
        findViewById(R.id.id_register_person_next).setOnClickListener(this);
        isRest = getIntent().getBooleanExtra("type", false);
        //是否是重新提交资料
        if (isRest) {
            getContentData();
        }
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {
        getShiLi();
    }

    @Override
    protected void onMyClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.id_register_person_next:
//                intent = new Intent(this, RegisterSelecterPayActivity.class);
//                startActivity(intent);
                postContent();
                break;
            case R.id.id_register_person_shenfenzhengzhengmian:
                duoxuan(RegisterQiYeActivity.ZHENGMIAN);
                break;
            case R.id.id_register_person_shenfenzhengfanmian:
                duoxuan(RegisterQiYeActivity.FANMIAN);
                break;
        }
    }

    //重新提交
    private void getContentData() {
        //获取个人信息状态
        AndroidNetworking.get(Contact.kaidian_url + "?user_id=" + MyApplication.getIntance().userBean.getUid())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        try {
                            //0 未开通 1此用户对公付款 2 此用户是商户 3提交资料，未到支付
                            String code = response.getString("code");
                            if ("3".equals(code)) {
                                JSONObject data1 = response.getJSONObject("data");
                                JSONObject data = data1.getJSONObject("niu_index_response");
                                String shop_name = data.getString("shop_name");
                                String phone = data.getString("phone");
//                                String company_name = data.getString("company_name");
                                String contacts_name = data.getString("contacts_name");
                                String zheng = data.getString("zheng");
                                String fan = data.getString("fan");
//                                String yyzz = data.getString("yyzz");
                                String email = data.getString("email");
                                dianpumingchengET.setText(shop_name);
                                lianxidianhuaET.setText(phone);
                                lianxirenET.setText(contacts_name);
                                youxiangET.setText(email);
                                GlideUtils.openImage(RegisterPersonActivity.this, zheng, zhengmian);
                                GlideUtils.openImage(RegisterPersonActivity.this, fan, fanmian);


                                setKongBaiBg(zhengmian, R.mipmap.icon_shenfenzheng_zhengmian_kong);
                                chongxuanZhengMian.setVisibility(View.VISIBLE);

                                setKongBaiBg(fanmian, R.mipmap.icon_shenfenzheng_zhengmian_kong);
                                chongxuanFanMian.setVisibility(View.VISIBLE);
                            }
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

    private void postContent() {
        String dianpumingchengStr = dianpumingchengET.getText().toString().trim();
        String lianxidianhuaStr = lianxidianhuaET.getText().toString().trim();
        String lianxirenStr = lianxirenET.getText().toString().trim();
        String youxiangStr = youxiangET.getText().toString().trim();

        String cardNameStr = cardName.getText().toString().trim();
        String cardNumberStr = cardNumber.getText().toString().trim();
        String openBankStr = openBank.getText().toString().trim();
        String openBankAddressStr = openBankAddress.getText().toString().trim();



        if (isStringsNull(new String[]{dianpumingchengStr,
                cardNameStr,cardNumberStr,openBankStr,openBankAddressStr,
                lianxidianhuaStr, lianxirenStr})) {
            Toast.makeText(this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
            return;
        }
        if (!isRest) {
            if (isStringsNull(new String[]{zhengmianPath, fanmianPath})) {
                Toast.makeText(this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
                return;
            }
        }
        ANRequest.MultiPartBuilder up = AndroidNetworking.upload(Contact.tijiaokaidinaziliao)
                .addMultipartParameter("apply_type", "1")
                .addMultipartParameter("uid", MyApplication.getIntance().userBean.getUid())
                .addMultipartParameter("shop_name", dianpumingchengStr)
                .addMultipartParameter("contacts_name", lianxirenStr)
                .addMultipartParameter("company_phone", lianxidianhuaStr);

//        if (!TextUtils.isEmpty(youxiangStr)) {
        up.addMultipartParameter("email", youxiangStr);
//        }

        if (isRest) {
            up.addMultipartParameter("class", "2");
            if (!TextUtils.isEmpty(zhengmianPath)) {
                up.addMultipartFile("zheng", new File(zhengmianPath));
            }
            if (!TextUtils.isEmpty(fanmianPath)) {
                up.addMultipartFile("fan", new File(fanmianPath));
            }
        } else {
            up.addMultipartParameter("class", "1");
            up.addMultipartFile("zheng", new File(zhengmianPath));
            up.addMultipartFile("fan", new File(fanmianPath));
        }
        up.build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        try {
                            if (JsonUtils.isSuccess(response)) {
                                JSONObject data = response.getJSONObject("data");
                                JSONObject niu_index_response = data.getJSONObject("niu_index_response");
                                String apply_id = niu_index_response.getString("apply_id");
                                String price = niu_index_response.getString("price");
                                Intent intent = new Intent(RegisterPersonActivity.this, RegisterSelecterPayActivity.class);
                                intent.putExtra("apply_id", apply_id);
                                intent.putExtra("price", price);
                                startActivity(intent);
                            } else {
                                Toast.makeText(RegisterPersonActivity.this, getString(R.string.tijiaoshibai), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            Toast.makeText(RegisterPersonActivity.this, getString(R.string.tijiaoshibai), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                        Toast.makeText(RegisterPersonActivity.this, getString(R.string.tijiaoshibai), Toast.LENGTH_SHORT).show();
                    }
                });
        showLoadingDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RegisterQiYeActivity.ZHENGMIAN && resultCode == RESULT_OK && data != null) {
            zhengmianPath = getImageurl(data);
            chongxuanZhengMian.setVisibility(View.VISIBLE);
            setKongBaiBg(zhengmian, R.mipmap.icon_shenfenzheng_zhengmian_kong);
            GlideUtils.openImagePhoto(this, zhengmianPath, zhengmian);
        } else if (requestCode == RegisterQiYeActivity.FANMIAN && resultCode == RESULT_OK && data != null) {
            fanmianPath = getImageurl(data);
            chongxuanFanMian.setVisibility(View.VISIBLE);
            setKongBaiBg(fanmian, R.mipmap.icon_shenfenzheng_zhengmian_kong);
            GlideUtils.openImagePhoto(this, fanmianPath, fanmian);
        }
    }

    //设置已选择图片背景
    private void setKongBaiBg(ImageView iv, int resourceid) {
        iv.setBackgroundResource(resourceid);
    }

    private String getImageurl(Intent data) {
        List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
        String url = "";
        for (String path : pathList) {
            url = path;
        }
        return url;
    }

    private void duoxuan(int type) {
        // 自由配置选项
        ImgSelConfig config = new ImgSelConfig.Builder(this, loader)
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
                .maxNum(1)
                .build();

        // 跳转到图片选择器
        ImgSelActivity.startActivity(this, config, type);
    }

    // 自定义图片加载器
    private ImageLoader loader = new ImageLoader() {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            // TODO 在这边可以自定义图片加载库来加载ImageView，例如Glide、Picasso、ImageLoader等
            Glide.with(context).load(path).into(imageView);
        }
    };


    private void getShiLi() {
        AndroidNetworking.get(Contact.huoqushenfenzhengheyingyezhizhao)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (JsonUtils.isSuccess(response)) {
                                JSONObject data = response.getJSONObject("data");
                                JSONObject niu_index_response = data.getJSONObject("niu_index_response");
                                String case_zheng = niu_index_response.getString("case_zheng");
                                String case_fan = niu_index_response.getString("case_fan");
//                                String case_yyzz = niu_index_response.getString("case_yyzz");

                                GlideUtils.openImage(RegisterPersonActivity.this, case_zheng, zhengmianshili);
                                GlideUtils.openImage(RegisterPersonActivity.this, case_fan, fanmianshili);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });
    }



}
