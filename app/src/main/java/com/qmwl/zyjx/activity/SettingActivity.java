package com.qmwl.zyjx.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
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
import com.qmwl.zyjx.bean.UserBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.DateUtils;
import com.qmwl.zyjx.utils.GlideUtils;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.view.SelecterLanguageDialog;
import com.qmwl.zyjx.view.TimeSelecterDialog;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 * 设置页面
 */

public class SettingActivity extends BaseActivity {
    private static int REQUEST_CODE = 0;
    private ImageView iv;
    private TextView userNameTv;
    private TextView nickNameTv;
    private TextView sexNameTv;
    private TextView birthdayTv;
    private WheelView dayWheelView;
    private TimeSelecterDialog dialog;
    private String selecterYear = "";
    private String selecterMonth = "";
    private String selecterDay = "";

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.setting_activity_layout);
    }

    @Override
    protected void initView() {
        View back = findViewById(R.id.base_top_bar_back);
        back.setVisibility(View.VISIBLE);
        TextView title = (TextView) findViewById(R.id.base_top_bar_title);
        title.setText(getResources().getString(R.string.gerenxinxi));

        iv = (ImageView) findViewById(R.id.setting_layout_touxiang_iv);
        back.setOnClickListener(this);
        findViewById(R.id.setting_layout_sex).setOnClickListener(this);
        findViewById(R.id.setting_layout_shengri).setOnClickListener(this);
        findViewById(R.id.setting_layout_address).setOnClickListener(this);
        findViewById(R.id.setting_layout_touxiang).setOnClickListener(this);
        findViewById(R.id.setting_layout_nickname).setOnClickListener(this);
        findViewById(R.id.setting_layout_tuichudenglu).setOnClickListener(this);
        findViewById(R.id.setting_layout_zhanghuanquan).setOnClickListener(this);
        userNameTv = (TextView) findViewById(R.id.setting_layout_username);
        nickNameTv = (TextView) findViewById(R.id.setting_layout_nicknametv);
        sexNameTv = (TextView) findViewById(R.id.setting_layout_sex_tv);
        birthdayTv = (TextView) findViewById(R.id.setting_layout_shengritv);

        setData();
    }

    private void setData() {
        UserBean userBean = MyApplication.getIntance().userBean;
        if (userBean != null && userNameTv != null && nickNameTv != null && sexNameTv != null && birthdayTv != null && iv != null) {
            userNameTv.setText(userBean.getName());
            nickNameTv.setText(userBean.getNickName());
            sexNameTv.setText(getStringSex(userBean.getSex()));
            birthdayTv.setText(userBean.getBirthday());
//            Glide.with(this).load(userBean.getHeadImg()).into(iv);
            GlideUtils.openImage(this, userBean.getHeadImg(), iv);
        }
    }

    private String getStringSex(String sex) {
        String str = "";
        if ("".equals(sex)) {
            str = getString(R.string.baomi);
        } else if ("1".equals(sex)) {
            str = getString(R.string.nan);
        } else if ("2".equals(sex)) {
            str = getString(R.string.nv);
        } else {
            str = getString(R.string.baomi);
        }

        return str;

    }


    //时间选择器
    private void timePicker() {
//        View selecter_content = LayoutInflater.from(this).inflate(R.layout.time_selecter_content, null);
//        WheelView yearWheelView = (WheelView) selecter_content.findViewById(R.id.selecter_time_content_year);
//
//        final String year = DateUtils.getYear();
//        int i1 = Integer.parseInt(year);
//
//        List<String> yearList = new ArrayList<>();
//        for (int i = 1950; i <= i1; i++) {
//            yearList.add(i + "");
//        }
//        yearWheelView.setWheelAdapter(new ArrayWheelAdapter(this));
//        yearWheelView.setSkin(WheelView.Skin.Holo);
//        yearWheelView.setWheelData(yearList);
//        yearWheelView.setOnWheelItemSelectedListener(new YearOnSelecterListener());
//        yearWheelView.setSelection(yearList.size());
//        int textSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 14, getResources().getDisplayMetrics());
//        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics());
//        yearWheelView.setExtraText("年", Color.parseColor("#0288ce"), textSize, margin);
//
//
//        WheelView monthWheelView = (WheelView) selecter_content.findViewById(R.id.selecter_time_content_month);
//
//        List<String> mList = new ArrayList<>();
//        for (int i = 1; i <= 12; i++) {
//            mList.add(i + "");
//        }
//        monthWheelView.setWheelAdapter(new ArrayWheelAdapter(this));
//        monthWheelView.setSkin(WheelView.Skin.Holo);
//        monthWheelView.setWheelData(mList);
//        monthWheelView.setOnWheelItemSelectedListener(new MonthOnSelecterListener());
//        monthWheelView.setLoop(true);
//        monthWheelView.setExtraText("月", Color.parseColor("#0288ce"), textSize, margin);
//
//        //日
//
//        dayWheelView = (WheelView) selecter_content.findViewById(R.id.selecter_time_content_day);
//        List<String> dayList = new ArrayList<>();
//        for (int i = 1; i <= 31; i++) {
//            dayList.add(i + "");
//        }
//        dayWheelView.setWheelAdapter(new ArrayWheelAdapter(this));
//        dayWheelView.setSkin(WheelView.Skin.Holo);
//        dayWheelView.setWheelData(dayList);
//        dayWheelView.setOnWheelItemSelectedListener(new DayOnSelecterListener());
//        dayWheelView.setLoop(true);
//        dayWheelView.setExtraText("日", Color.parseColor("#0288ce"), textSize, margin);
//
//
//        dialog = new TimeSelecterDialog(this, selecter_content, new TimeSelecterDialog.onClickButton() {
//            @Override
//            public void rightClick() {
//                String s = dealmonthOrDay(selecterMonth, selecterDay);
//                postShengRi(s);
//                dialog.dismiss();
//            }
//        });
//        dialog.show();

        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(SettingActivity.this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                String time = getTime(date);
                postShengRi(time);
            }
        }).setType(new boolean[]{true, true, true, false, false, false}).build();

        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();

    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    private String dealmonthOrDay(String mmm, String ddd) {
        int m = Integer.parseInt(mmm);
        int d = Integer.parseInt(ddd);
        String strMonth = selecterMonth;
        String strDay = selecterDay;
        if (m < 10) {
            strMonth = "0" + m;
        }
        if (d < 10) {
            strDay = "0" + d;
        }

        return selecterYear + "-" + strMonth + "-" + strDay;
    }

    void postShengRi(final String birthday) {
        AndroidNetworking.post(Contact.xiugaichushengriqi)
                .addBodyParameter("user_id", MyApplication.getIntance().userBean.getUid())
                .addBodyParameter("birthday", birthday)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (JsonUtils.is100Success(response)) {
                                MyApplication.getIntance().userBean.setBirthday(birthday);
                                String message = response.getString("message");
                                Toast.makeText(SettingActivity.this, "" + message, Toast.LENGTH_SHORT).show();
                                setData();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(SettingActivity.this, getString(R.string.xiugaishibai), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(SettingActivity.this, getString(R.string.tijiaoshibai_wangluo), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {

    }


    @Override
    protected void onMyClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.base_top_bar_back:
                finish();
                break;
            case R.id.setting_layout_shengri:
                timePicker();
                break;

            case R.id.setting_layout_zhanghuanquan:
                //账户与安全
                intent = new Intent(this, ZhangHuAnQuanActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_layout_nickname:
                //昵称
                intent = new Intent(this, ChangedNickNameActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_layout_sex:
                //性别
                intent = new Intent(this, ChangedSexActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_layout_address:
                //收货地址
                intent = new Intent(this, AddressActivity.class);
                intent.putExtra(AddressActivity.ADDRESS_UPDATE_VALUE_BOOLEAN, true);
                startActivity(intent);
                break;
            case R.id.setting_layout_touxiang:
                duoxuan();
                break;
            case R.id.setting_layout_tuichudenglu:
              MyApplication.getIntance().exitLogin();
                finish();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setData();
    }

    class YearOnSelecterListener implements WheelView.OnWheelItemSelectedListener {

        @Override
        public void onItemSelected(int i, Object o) {
            selecterYear = o.toString();
            onYearOrMonth(selecterYear, selecterMonth);
        }
    }

    class MonthOnSelecterListener implements WheelView.OnWheelItemSelectedListener {

        @Override
        public void onItemSelected(int i, Object o) {
            selecterMonth = o.toString();
            onYearOrMonth(selecterYear, selecterMonth);
        }
    }

    class DayOnSelecterListener implements WheelView.OnWheelItemSelectedListener {

        @Override
        public void onItemSelected(int i, Object o) {
            selecterDay = o.toString();
        }
    }

    private void onYearOrMonth(String year, String month) {

        if ("".equals(year) || "".equals(month)) {
            return;
        }
        int daysOfMonth = DateUtils.getDaysOfMonth(year, month);
        List<String> dList = new ArrayList<>();
        for (int i = 1; i <= daysOfMonth; i++) {
            dList.add("" + i);
        }
        dayWheelView.setWheelData(dList);

    }

    private void duoxuan() {
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
                .needCrop(true)
                .cropSize(1, 1, 200, 200)
                // 第一个是否显示相机，默认true
                .needCamera(true)
                // 最大选择图片数量，默认9
                .maxNum(1)
                .build();

// 跳转到图片选择器
        ImgSelActivity.startActivity(this, config, REQUEST_CODE);

    }

    // 自定义图片加载器
    private ImageLoader loader = new ImageLoader() {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 图片选择结果回调
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            String imagepath = "";
            for (String path : pathList) {
                imagepath = path;
            }
//            Glide.with(this).load(imagepath).into(iv);
            MyApplication.getIntance().userBean.setHeadImg(imagepath);
            postImage(imagepath);
            setData();
        } else if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data == null) {
            Toast.makeText(this, "未选择图片", Toast.LENGTH_SHORT).show();
        }
    }

    private void postImage(String path) {
        File file = new File(path);
        AndroidNetworking.upload(Contact.shangchuantouxiang)
                .addMultipartFile("myfile", file)
                .addMultipartParameter("user_id", MyApplication.getIntance().userBean.getUid())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });
    }
}
