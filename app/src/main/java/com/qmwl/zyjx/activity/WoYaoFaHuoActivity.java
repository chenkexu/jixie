package com.qmwl.zyjx.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bigkoo.pickerview.TimePickerView;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.DateUtils;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.view.CommomDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/8/30.
 * 我要发货编辑页
 */

public class WoYaoFaHuoActivity extends BaseActivity {
    private TextView fahuoshijianTv;
    private TextView shouhuoshijianTextView;
    private EditText huowumingchengEditText;
    private EditText guigeEdittext;
    private EditText huowuzhongliangEdittext;
    private EditText huowudizhiEdittext;
    private EditText fahuorenEdittext;
    private EditText fahuorenlianxidianhuaEdittext;
    private EditText chexingyaoqiuEdittext;
    private EditText chezhangyaoqiuEdittext;
    private EditText lianxirenshouhuoEdittext;
    private EditText lianxidianhuashouhuoEdittext;
    private RadioButton radioButtonYes;
    private RadioButton radioButtonNo;
    private EditText mudidiEdittext;
    private EditText xiangxidizhiEdittext;
    private EditText beizhuxinxi;
    private void submitData() {
        String huowumingchengStr = huowumingchengEditText.getText().toString().trim();
        String guigeStr = guigeEdittext.getText().toString().trim();
        String huowuzhongliangStr = huowuzhongliangEdittext.getText().toString().trim();
        String fahuodizhiStr = huowudizhiEdittext.getText().toString().trim();
        String fahuorenStr = fahuorenEdittext.getText().toString().trim();
        String fahuorenlianxidianhuaStr = fahuorenlianxidianhuaEdittext.getText().toString().trim();
        String chexingyaoqiuStr = chexingyaoqiuEdittext.getText().toString().trim();
        String chechangyaoqiuStr = chezhangyaoqiuEdittext.getText().toString().trim();
        String shouhuolianxirenStr = lianxirenshouhuoEdittext.getText().toString().trim();
        String shouhuolianxidianhuaStr = lianxidianhuashouhuoEdittext.getText().toString().trim();
        String mudidiStr = mudidiEdittext.getText().toString().trim();
        String xiangxidizhiStr = xiangxidizhiEdittext.getText().toString().trim();
        String fahuoshijianStr = fahuoshijianTv.getText().toString();
        String shouhuoshijianStr = shouhuoshijianTextView.getText().toString();
        String beizhuxinxiStr = beizhuxinxi.getText().toString().trim();

        if (isStringsNull(new String[]{huowumingchengStr, guigeStr, huowuzhongliangStr, fahuodizhiStr, fahuorenStr
                , fahuorenlianxidianhuaStr, chexingyaoqiuStr, chechangyaoqiuStr, shouhuolianxirenStr
                , shouhuolianxidianhuaStr, mudidiStr, xiangxidizhiStr, fahuoshijianStr, shouhuoshijianStr})) {
            Toast.makeText(this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
            return;
        }
        int shifoujiaji = 0;
        if (radioButtonYes.isChecked()) {
            shifoujiaji = 1;
        }

        AndroidNetworking.post(Contact.woyaofahuo)
//        AndroidNetworking.post("http://shopnc.rabxdl.cn/index.php/api/transport/addHyinfo")
                .addBodyParameter("t_name", huowumingchengStr)
                .addBodyParameter("uid", MyApplication.getIntance().userBean.getUid())
                .addBodyParameter("format", guigeStr)
                .addBodyParameter("t_weight", huowuzhongliangStr)
                .addBodyParameter("t_address", fahuodizhiStr)
                .addBodyParameter("f_name", fahuorenStr)
                .addBodyParameter("f_tel", fahuorenlianxidianhuaStr)
                .addBodyParameter("models_type", chexingyaoqiuStr)
                .addBodyParameter("vehicle_length", chechangyaoqiuStr)
                .addBodyParameter("s_name", shouhuolianxirenStr)
                .addBodyParameter("s_tel", shouhuolianxidianhuaStr)
                .addBodyParameter("s_mdd", mudidiStr)
                .addBodyParameter("s_address", xiangxidizhiStr)
                .addBodyParameter("delivery_time", fahuoshijianStr)
                .addBodyParameter("receipt_time", shouhuoshijianStr)
                .addBodyParameter("urgent", String.valueOf(shifoujiaji))
                .addBodyParameter("content",beizhuxinxiStr)
//                .addBodyParameter("addtime", String.valueOf(System.currentTimeMillis()))
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (JsonUtils.is100Success(response)) {
                                dismissLoadingDialog();
                                new CommomDialog(WoYaoFaHuoActivity.this, R.style.dialog, getString(R.string.tijiaochenggong), new CommomDialog.OnCloseListener() {
                                    @Override
                                    public void onClick(Dialog dialog, boolean confirm) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                }).setHideCancelButton().show();
                            } else {
                                new CommomDialog(WoYaoFaHuoActivity.this, R.style.dialog, getString(R.string.tijiaoshibai_chongshi), new CommomDialog.OnCloseListener() {
                                    @Override
                                    public void onClick(Dialog dialog, boolean confirm) {
                                        dialog.dismiss();
                                    }
                                }).setHideCancelButton().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            new CommomDialog(WoYaoFaHuoActivity.this, R.style.dialog, getString(R.string.tijiaoshibai_chongshi), new CommomDialog.OnCloseListener() {
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
                        new CommomDialog(WoYaoFaHuoActivity.this, R.style.dialog, getString(R.string.tijiaoshibai_chongshi), new CommomDialog.OnCloseListener() {
                            @Override
                            public void onClick(Dialog dialog, boolean confirm) {
                                dialog.dismiss();
                            }
                        }).setHideCancelButton().show();
                    }
                });
        showLoadingDialog();
    }


    @Override
    protected void setLayout() {
        setContentLayout(R.layout.woyaofahuo_layout);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.woyaofahuo);

        findViewById(R.id.wyaofahuo_layout_submit).setOnClickListener(this);
        findViewById(R.id.wyaofahuo_layout_textview_fahuoshijian_iv).setOnClickListener(this);
        findViewById(R.id.wyaofahuo_layout_imageview_shouhuoshijian_iv).setOnClickListener(this);
        huowumingchengEditText = (EditText) findViewById(R.id.wyaofahuo_layout_edittext_huowuname);
        fahuoshijianTv = (TextView) findViewById(R.id.wyaofahuo_layout_textview_fahuoshijian);
        guigeEdittext = (EditText) findViewById(R.id.wyaofahuo_layout_edittext_guige);
        huowuzhongliangEdittext = (EditText) findViewById(R.id.wyaofahuo_layout_edittext_zhongliang);
        huowudizhiEdittext = (EditText) findViewById(R.id.wyaofahuo_layout_edittext_huowudizhi);
        fahuorenEdittext = (EditText) findViewById(R.id.wyaofahuo_layout_edittext_lianxiren_fahuoren);
        fahuorenlianxidianhuaEdittext = (EditText) findViewById(R.id.wyaofahuo_layout_edittext_lianxidianhua_fahuoren);
        chexingyaoqiuEdittext = (EditText) findViewById(R.id.wyaofahuo_layout_edittext_chexingyaoqiu);
        chezhangyaoqiuEdittext = (EditText) findViewById(R.id.wyaofahuo_layout_edittext_chezhangyaoqiu);

        beizhuxinxi = (EditText) findViewById(R.id.wyaofahuo_layout_edittext_beizhuxinxi);

        chexingyaoqiuEdittext.setOnClickListener(this);
        chezhangyaoqiuEdittext.setOnClickListener(this);
        chexingyaoqiuEdittext.setFocusable(false);
        chezhangyaoqiuEdittext.setFocusable(false);
        radioButtonYes = (RadioButton) findViewById(R.id.wyaofahuo_layout_radiobutton_yes);
        radioButtonNo = (RadioButton) findViewById(R.id.wyaofahuo_layout_radiobutton_no);


        lianxirenshouhuoEdittext = (EditText) findViewById(R.id.wyaofahuo_layout_edittext_lianxiren_shouhuoren);
        lianxidianhuashouhuoEdittext = (EditText) findViewById(R.id.wyaofahuo_layout_edittext_lianxidianhua_shouhuoren);
        shouhuoshijianTextView = (TextView) findViewById(R.id.wyaofahuo_layout_textview_shouhuoshijian);
        mudidiEdittext = (EditText) findViewById(R.id.wyaofahuo_layout_edittext_mudidi);
        xiangxidizhiEdittext = (EditText) findViewById(R.id.wyaofahuo_layout_edittext_xiangxidizhi);
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {

    }
    //隐藏虚拟键盘
    public static void HideKeyboard(View v)
    {
        InputMethodManager imm = (InputMethodManager)v.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );
        if ( imm.isActive( ) ) {
            imm.hideSoftInputFromWindow( v.getApplicationWindowToken( ) , 0 );

        }
    }
    @Override
    protected void onMyClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.wyaofahuo_layout_textview_fahuoshijian_iv:
                //发货时间iv
                HideKeyboard(v);
                selecterTimePicker(false);
                break;
            case R.id.wyaofahuo_layout_imageview_shouhuoshijian_iv:
                //收货时间iv
                HideKeyboard(v);
                selecterTimePicker(true);
                break;
            case R.id.wyaofahuo_layout_submit:
                //提交
                submitData();
                break;
            case R.id.wyaofahuo_layout_edittext_chexingyaoqiu:
                //车型
                intent = new Intent(this, XuanZeCheXingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivityForResult(intent, BaoJiaBianJiActivity.CHEXING_CODE);
                break;
            case R.id.wyaofahuo_layout_edittext_chezhangyaoqiu:
                //车长
                intent = new Intent(this, XuanZeCheXingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.putExtra(XuanZeCheXingActivity.TYPE, true);
                startActivityForResult(intent, BaoJiaBianJiActivity.CHECHANG_CODE);
                break;
        }
    }


    private void selecterTimePicker(final boolean isShouHuoTime) {
        Calendar startDate = Calendar.getInstance();
        //startDate.set(2013,1,1);
        Calendar endDate = Calendar.getInstance();
        //endDate.set(2020,1,1);
        int year =Integer.parseInt(DateUtils.getYear());
        int month = Integer.parseInt(DateUtils.getMonth())-1;
        int day = Integer.parseInt(DateUtils.getDay());
        startDate.set(year,month,day);
        endDate.set(year+10,month,day);
        //时间选择器
        TimePickerView pvTime = new TimePickerView.Builder(WoYaoFaHuoActivity.this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                String time = getTime(date);
                if (isShouHuoTime) {
                    shouhuoshijianTextView.setText(time);
                } else {
                    fahuoshijianTv.setText(time);

                }
            }
        }).setType(new boolean[]{true, true, true, false, false, false}).setRangDate(startDate,endDate).build();

//        pvTime.setDate(Calendar.getInstance());//注：根据需求来决定是否使用该方法（一般是精确到秒的情况），此项可以在弹出选择器的时候重新设置当前时间，避免在初始化之后由于时间已经设定，导致选中时间与当前时间不匹配的问题。
        pvTime.show();

    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            switch (requestCode) {
                case BaoJiaBianJiActivity.CHEXING_CODE:
                    String chexingStr = data.getStringExtra(XuanZeCheXingActivity.DATA);
                    chexingyaoqiuEdittext.setText(chexingStr);
                    break;
                case BaoJiaBianJiActivity.CHECHANG_CODE:
                    String chechangStr = data.getStringExtra(XuanZeCheXingActivity.DATA);
                    chezhangyaoqiuEdittext.setText(chechangStr);
                    break;
            }

        }
    }
}
