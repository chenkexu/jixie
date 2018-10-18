package com.qmwl.zyjx.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.ZulingBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.GlideUtils;
import com.qmwl.zyjx.utils.JsonUtils;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/9/6.
 * 租赁个人信息上传页面
 */

public class ZuLinGeRenMessageActivity extends BaseActivity {
    public static final int IMAGE_CODE = 2;
    private String json = "";
    private ImageView iv;
    private TextView qishishijianTv;
    private TextView jieshushijianTv;
    private EditText nameEditText;
    private EditText callEditText;
    private EditText addressEditText;
    private EditText shenfenzhenghaoEditText;
    private String imagePath = "";

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.zulinggeren_message_layout);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.gerenxinxi);
        json = getIntent().getStringExtra(ZuLinXieYiActivity.DATA);
        iv = (ImageView) findViewById(R.id.zulinggerenxinxi_layout_iv);
        iv.setOnClickListener(this);
        findViewById(R.id.zulinggerenxinxi_layout_submit).setOnClickListener(this);
        nameEditText = (EditText) findViewById(R.id.zulinggerenxinxi_layout_name_edittext);
        callEditText = (EditText) findViewById(R.id.zulinggerenxinxi_layout_call_edittext);
        qishishijianTv = (TextView) findViewById(R.id.zulinggerenxinxi_layout_qizushijian_tv);
        addressEditText = (EditText) findViewById(R.id.zulinggerenxinxi_layout_address_edittext);
        jieshushijianTv = (TextView) findViewById(R.id.zulinggerenxinxi_layout_jieshushijian_tv);
        findViewById(R.id.zulinggerenxinxi_layout_qizushijian_container).setOnClickListener(this);
        findViewById(R.id.zulinggerenxinxi_layout_jieshushijian_container).setOnClickListener(this);
        shenfenzhenghaoEditText = (EditText) findViewById(R.id.zulinggerenxinxi_layout_shenfenzhenghao_edittext);

    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {

    }

    @Override
    protected void onMyClick(View v) {
        switch (v.getId()) {
            case R.id.zulinggerenxinxi_layout_qizushijian_container:
                showQiShiShijian();
                break;
            case R.id.zulinggerenxinxi_layout_jieshushijian_container:
                String qishishijian = qishishijianTv.getText().toString().trim();
                if ("".equals(qishishijian) || TextUtils.isEmpty(qishishijian)) {
                    Toast.makeText(this, getString(R.string.qingxianxuanzeqishishijian), Toast.LENGTH_SHORT).show();
                    return;
                }
                String[] split = qishishijian.split("-");
                showJieShuShijian(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                break;
            case R.id.zulinggerenxinxi_layout_iv:
                startPhoto(IMAGE_CODE);
                break;
            case R.id.zulinggerenxinxi_layout_submit:
                submitData();
                break;
        }
    }


    private void showQiShiShijian() {
        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(ZuLinGeRenMessageActivity.this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                String time = getTime(date);
                qishishijianTv.setText(time);
            }
        }).setType(new boolean[]{true, true, true, false, false, false}).build();

        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();

    }

    private void showJieShuShijian(int y, int m, int d) {
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        startDate.set(y, m - 1, d);
        endDate.set(y + 5, 12, 31);
        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(ZuLinGeRenMessageActivity.this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                String time = getTime(date);
                jieshushijianTv.setText(time);
            }
        }).setType(new boolean[]{true, true, true, false, false, false}).setRangDate(startDate, endDate).build();

//        pvTime.setDate(startDate);//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();

    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
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
                case IMAGE_CODE:
                    //营业执照
                    this.imagePath = imagePath;
                    GlideUtils.openImagePhoto(this, imagePath, iv);
                    break;
            }
        } else {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }
    }

    private void submitData() {
        String nameStr = nameEditText.getText().toString().trim();
        String callStr = callEditText.getText().toString().trim();
        String addressStr = addressEditText.getText().toString().trim();
        String shenfenzhengStr = shenfenzhenghaoEditText.getText().toString().trim();
        String qishishijianStr = qishishijianTv.getText().toString().trim();
        String jieshushijianStr = jieshushijianTv.getText().toString().trim();

        if (isStringsNull(new String[]{imagePath, nameStr, callStr, addressStr, shenfenzhengStr, qishishijianStr, jieshushijianStr})) {
            Toast.makeText(this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
            return;
        }
        if (qishishijianStr.equals(jieshushijianStr)) {
            Toast.makeText(this, getString(R.string.qishiriqiyujieshushijianxiangtong), Toast.LENGTH_SHORT).show();
            return;
        }
        ZulingBean zulingBean = JsonUtils.parseZulinMessage(json);
        File file = new File(imagePath);
        AndroidNetworking.upload(Contact.upZulinMessage)
                .addMultipartParameter("shop_id", zulingBean.getSeller_id())
                .addMultipartParameter("shop_name", zulingBean.getSeller_name())
                .addMultipartParameter("goods_id", zulingBean.getPost_id())
                .addMultipartParameter("uid", MyApplication.getIntance().userBean.getUid())
                .addMultipartParameter("goods_model", zulingBean.getPro_model())
                .addMultipartParameter("goods_num", zulingBean.getNumber())
                .addMultipartParameter("lease_price", zulingBean.getPrice())
                .addMultipartParameter("deposit", zulingBean.getPledge())
                .addMultipartParameter("name", nameStr)
                .addMultipartParameter("phone", callStr)
                .addMultipartParameter("address", addressStr)
                .addMultipartParameter("card", shenfenzhengStr)
                .addMultipartParameter("lease_start", qishishijianStr)
                .addMultipartParameter("lease_end", jieshushijianStr)
                .addMultipartFile("myfile", file)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (JsonUtils.isSuccess(response)) {
                                JSONObject data = response.getJSONObject("data");
                                String no = data.getString("niu_index_response");
                                Intent intent = new Intent(ZuLinGeRenMessageActivity.this, ZuLinXieYiActivity.class);
                                intent.putExtra(ZuLinXieYiActivity.URL, Contact.zulinhetong_url + "?uid=" + MyApplication.getIntance().userBean.getUid());
                                intent.putExtra(ZuLinXieYiActivity.TYPE, true);
                                intent.putExtra(ZuLinXieYiActivity.DINGDAN_NO, no);
                                startActivity(intent);

                            } else {
                                Toast.makeText(ZuLinGeRenMessageActivity.this, getString(R.string.dingdantijiaoshibai), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ZuLinGeRenMessageActivity.this, getString(R.string.dingdantijiaoshibai), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(ZuLinGeRenMessageActivity.this, getString(R.string.dingdantijiaoshibai_wangluo), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
