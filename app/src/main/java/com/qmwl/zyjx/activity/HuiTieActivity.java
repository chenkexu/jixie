package com.qmwl.zyjx.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.bumptech.glide.Glide;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.FaTieGridViewAdapter;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 * 回帖页面
 */

public class HuiTieActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    public static final String FATIEREN_ID = "com.gh.huitie.fatierenid";
    public static final String TIEZI_ID = "com.gh.huitie.tieziid";

    private List<String> list = new ArrayList<String>();
    private static int REQUEST_CODE = 0;
    private FaTieGridViewAdapter adapter;
    private EditText contentEdittext;

    private String fatierenid = "";
    private String tiezi_id = "";


    @Override
    protected void setLayout() {
        setContentLayout(R.layout.huitie_layout);
    }

    @Override
    protected void initView() {
        fatierenid = getIntent().getStringExtra(FATIEREN_ID);
        tiezi_id = getIntent().getStringExtra(TIEZI_ID);
        setTitleContent(R.string.faxiaoxi);
        contentEdittext = (EditText) findViewById(R.id.huitie_layout_content);
        GridView mGd = (GridView) findViewById(R.id.huitie_activity_gridview);
        adapter = new FaTieGridViewAdapter();
        mGd.setAdapter(adapter);
        mGd.setOnItemClickListener(this);
        findViewById(R.id.huitie_layout_submit).setOnClickListener(this);
        addData();
    }

    private void addData() {
        list.add("");
        adapter.setData(list);
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
            case R.id.huitie_layout_submit:
                postData();
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        duoxuan();
    }

    private void postData() {
        String content = contentEdittext.getText().toString().trim();
        String uid = MyApplication.getIntance().userBean.getUid();
        if ("".equals(tiezi_id) || "".equals(content) || "".equals(uid)) {
            Toast.makeText(this, R.string.xinxibuwanzheng, Toast.LENGTH_SHORT).show();
            return;
        }
        ANRequest.MultiPartBuilder upload = AndroidNetworking.upload(Contact.huitie);
        if (list.size() <= 1) {
            upload.addMultipartParameter("uid", fatierenid)
                    .addMultipartParameter("post_id", tiezi_id)
                    .addMultipartParameter("reply_id", uid)
                    .addMultipartParameter("comment", content);
        } else {
            File file = null;
            for (int i = 0; i < list.size() - 1; i++) {
                String s = list.get(i);
                file = new File(s);
                switch (i) {
                    case 0:
                        upload.addMultipartFile("myfile", file);
                        break;
                    case 1:
                        upload.addMultipartFile("myfile1", file);
                        break;
                    case 2:
                        upload.addMultipartFile("myfile2", file);
                        break;
                }
            }
//            upload.addMultipartFile("myfile", file)
            upload.addMultipartParameter("uid", fatierenid)
                    .addMultipartParameter("post_id", tiezi_id)
                    .addMultipartParameter("reply_id", uid)
                    .addMultipartParameter("comment", content);
        }

        upload.build().setUploadProgressListener(new UploadProgressListener() {
            @Override
            public void onProgress(long bytesUploaded, long totalBytes) {

            }
        }).getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                dismissLoadingDialog();
                try {
                    if (JsonUtils.isSuccess(response)) {
                        Toast.makeText(HuiTieActivity.this, getString(R.string.huitiechenggong), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        String message = "";
                        if (response.has("message")) {
                            message = response.getString("message");
                        } else {
                            message = getString(R.string.huitieshibai);
                        }

                        Toast.makeText(HuiTieActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(HuiTieActivity.this, getString(R.string.huitieshibai), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onError(ANError anError) {
                dismissLoadingDialog();
                Toast.makeText(HuiTieActivity.this, getString(R.string.huitieshibaiqingjiancha), Toast.LENGTH_SHORT).show();
            }
        });
        showLoadingDialog();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 图片选择结果回调
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            list.clear();
            for (String path : pathList) {
//                Glide.with(FaTieActivity.this).load(path).into(iv1);
                list.add(path);
            }
            list.add("");
            adapter.setData(list);
        }
    }

    // 自定义图片加载器
    private ImageLoader loader = new ImageLoader() {
        @Override
        public void displayImage(Context context, String path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    };

    private void duoxuan() {
        // 自由配置选项
        ImgSelConfig config = new ImgSelConfig.Builder(this, loader)
                // 是否多选, 默认true
                .multiSelect(true)
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
                .maxNum(FaTieActivity.MAX_NUM)
                .build();

        // 跳转到图片选择器
        ImgSelActivity.startActivity(this, config, REQUEST_CODE);
    }
}
