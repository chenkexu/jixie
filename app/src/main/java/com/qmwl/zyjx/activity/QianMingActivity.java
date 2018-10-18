package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.view.LinePathView;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;

import static android.os.Environment.getExternalStorageDirectory;
import static com.qmwl.zyjx.activity.ZuLinXieYiActivity.DINGDAN_NO;

/**
 * Created by Administrator on 2017/7/27.
 * 手写签名页面
 */

public class QianMingActivity extends BaseActivity {
    public static final String IMAGE_PATH = "com.gh.qinaming.image_path";
    public static final int SUCCESS_CODE = 1;
    private LinePathView qianmingView;
    private ImageView iv;
    private String dingdanNo;
    private String filePath = Environment.getExternalStorageDirectory().toString() + "/qm.png";

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.qianming_activity_layout);
    }

    @Override
    protected void initView() {
        dingdanNo = getIntent().getStringExtra(DINGDAN_NO);
        setStatueHide(true);
        qianmingView = (LinePathView) findViewById(R.id.qianming_layout_qianming);
        findViewById(R.id.qianming_layout_clear).setOnClickListener(this);
        findViewById(R.id.qianming_layout_save).setOnClickListener(this);

//        iv = (ImageView) findViewById(R.id.qianming_layout_iv);
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
            case R.id.qianming_layout_clear:
                //清空
                qianmingView.clear();
                break;

            case R.id.qianming_layout_save:
                //保存
                try {
                    qianmingView.save(filePath);
                    postQianMing();
//                    Toast.makeText(this, "保存完成", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
//                    finish();
                    Toast.makeText(this, getString(R.string.baocunshibaiqingchongshi), Toast.LENGTH_SHORT).show();
                }
//                Bitmap bitmap = convertViewToBitmap(qianmingView, qianmingView.getWidth(), qianmingView.getHeight());
//                iv.setImageBitmap(bitmap);
//                iv.setImageBitmap(qianmingView.getBitMap());
                break;

        }
    }

    public static Bitmap convertViewToBitmap(View view, int bitmapWidth, int bitmapHeight) {
        Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Bitmap.Config.ARGB_8888);
        view.draw(new Canvas(bitmap));

        return bitmap;
    }

    private void postQianMing() {
        File file = new File(filePath);
        if (file.exists()) {
            AndroidNetworking.upload(Contact.shangchuanqianming)
                    .addMultipartFile("myfile", file)
                    .addMultipartParameter("uid", MyApplication.getIntance().userBean.getUid())
                    .addMultipartParameter("order_id", dingdanNo)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            setResult(SUCCESS_CODE);
                            finish();
                        }

                        @Override
                        public void onError(ANError anError) {
                        }
                    });
        }
    }
}
