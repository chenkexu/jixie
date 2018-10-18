package com.qmwl.zyjx.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;
import com.androidnetworking.interfaces.UploadProgressListener;
import com.bumptech.glide.Glide;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.FaTieGridViewAdapter;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.TieZiBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.ITextUtils;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.view.CommomDialog;
import com.yuyh.library.imgsel.ImageLoader;
import com.yuyh.library.imgsel.ImgSelConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 * 发布帖子页面
 */

public class FaTieActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    public static final String IS_EDIT = "com.gh.fatie.edit";
    public static final String POST_ID = "com.gh.fatie.id";
    private static int REQUEST_CODE = 0;//多选
    private int SINGLE_REQUEST_CODE = 1;//单选
    public static final int MAX_NUM = 9;

    //是否是编辑状态
    private boolean isEdit;
    private String postId = "";
    private ImageView iv1;
    private GridView mGd;
    private FaTieGridViewAdapter adapter;
    private EditText addressEditText;
    private EditText tellEditText;
    private EditText titleEditText;
    private EditText contentEditText;

    private List<String> list = new ArrayList<String>();

    private boolean isAdd = false;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.fatie_activity_layout);
    }

    @Override
    protected void initView() {
        View back = findViewById(R.id.base_top_bar_back);
        ImageView heimingdan = (ImageView) findViewById(R.id.base_top_bar_right2);
        back.setVisibility(View.VISIBLE);
//        heimingdan.setVisibility(View.VISIBLE);
//        heimingdan.setImageResource(R.mipmap.heimingdan);
        TextView titleTv = (TextView) findViewById(R.id.base_top_bar_title);
        findViewById(R.id.fatie_layout_submit).setOnClickListener(this);
        titleTv.setText(getResources().getString(R.string.fatie));
        back.setOnClickListener(this);
        mGd = (GridView) findViewById(R.id.fatie_activity_gridview);
        adapter = new FaTieGridViewAdapter();
        mGd.setAdapter(adapter);
        mGd.setOnItemClickListener(this);
        addressEditText = (EditText) findViewById(R.id.fatie_activity_edittext_address);
        tellEditText = (EditText) findViewById(R.id.fatie_activity_edittext_tellphone);
        titleEditText = (EditText) findViewById(R.id.fatie_activity_edittext_title);
        contentEditText = (EditText) findViewById(R.id.fatie_activity_edittext_content);
        addData();
        isEdit = getIntent().getBooleanExtra(IS_EDIT, false);
        if (isEdit) {
            postId = getIntent().getStringExtra(POST_ID);
            getTieZiData();
        }
    }

    private void showFinish() {
        new CommomDialog(FaTieActivity.this, R.style.dialog, getString(R.string.tiezibucunzai), new CommomDialog.OnCloseListener() {
            @Override
            public void onClick(Dialog dialog, boolean confirm) {
                FaTieActivity.this.finish();
            }
        }).setHideCancelButton().show();
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
            case R.id.fatie_layout_submit:
                if (isEdit) {
                    postEditData();
                } else {
                    postTieZiData();
                }

                break;
        }
    }

    //编辑帖子时获取帖子数据
    private void getTieZiData() {
        AndroidNetworking.get(Contact.get_tiezi_data + "?post_id=" + postId)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        TieZiBean tieZiBean = JsonUtils.parseTieZiDetails(response);
                        if (tieZiBean == null) {
                            showFinish();
                        } else {
                            titleEditText.setText(tieZiBean.getTitle());
                            contentEditText.setText(tieZiBean.getContent());
                            List<String> imageList = tieZiBean.getImageList();
                            if (imageList == null) {
                                imageList = new ArrayList<String>();
                            }
                            imageList.add("");
                            adapter.setData(imageList);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                        showFinish();
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (isEdit) {
            //如果是编辑帖子
            if (requestCode == SINGLE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
                List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
                String url = "";
                for (String path : pathList) {
                    url = path;
                }
                uploadSingleImage(url);
                return;
            }
        }
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
        } else if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data == null) {
            Toast.makeText(this, "未选择图片", Toast.LENGTH_SHORT).show();
        }
        //单选的
        if (requestCode == SINGLE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImgSelActivity.INTENT_RESULT);
            String url = "";
            for (String path : pathList) {
                url = path;
            }
            adapter.getData().remove(SINGLE_REQUEST_CODE);
            adapter.getData().add(SINGLE_REQUEST_CODE, url);
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (isEdit) {
            if (adapter.getData().size() == 1 && position == adapter.getData().size() - 1) {
                isAdd = true;
                danxuan(position);
            } else if (position == adapter.getData().size() - 1) {
                isAdd = true;
                danxuan(position);
            } else {
                isAdd = false;
                danxuan(position);
            }
        } else {
            if (list.size() == 1 && position == list.size() - 1) {
                //如果没有一张图片,就多选
                duoxuan();
            } else if (position == list.size() - 1) {
                duoxuan();
            } else {
                danxuan(position);
            }
        }
    }

    private void uploadSingleImage(String path) {
        ANRequest.MultiPartBuilder upload = AndroidNetworking.upload(Contact.tiezi_updata_singleimage);
        upload.addMultipartFile("img", new File(path)).build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        String s = JsonUtils.parseSingleImage(response);
                        if ("".equals(s)) {
                            Toast.makeText(FaTieActivity.this, getString(R.string.wangluocuowu), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        List<String> data = adapter.getData();
                        if (isAdd) {
                            data.add(data.size() - 1, s);
                        } else {
                            data.remove(SINGLE_REQUEST_CODE);
                            data.add(SINGLE_REQUEST_CODE, s);
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                    }
                });
        showLoadingDialog();
    }

    public void postEditData() {
        String titleStr = titleEditText.getText().toString().trim();
        String contentStr = contentEditText.getText().toString().trim();

        if ("".equals(titleStr)) {
            Toast.makeText(this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
            return;
        }
        List<String> data = adapter.getData();
        data.remove("");
        JSONArray array = new JSONArray(data);

        AndroidNetworking.post(Contact.tiezi_updata_updata)
                .addBodyParameter("post_id", postId)
                .addBodyParameter("uid", MyApplication.getIntance().userBean.getUid())
                .addBodyParameter("title", titleStr)
                .addBodyParameter("content", contentStr)
                .addBodyParameter("pic", array.toString())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        adapter.getData().add("");
                        try {
                            if (JsonUtils.isSuccess(response)) {
                                new CommomDialog(FaTieActivity.this, R.style.dialog, getString(R.string.xiugaichenggong), new CommomDialog.OnCloseListener() {
                                    @Override
                                    public void onClick(Dialog dialog, boolean confirm) {
                                        FaTieActivity.this.finish();
                                    }
                                }).setHideCancelButton().show();
                            } else {
                                new CommomDialog(FaTieActivity.this, R.style.dialog, getString(R.string.xiugaishibai)).setHideCancelButton().show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            new CommomDialog(FaTieActivity.this, R.style.dialog, getString(R.string.xiugaishibai)).setHideCancelButton().show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                        adapter.getData().add("");
                        new CommomDialog(FaTieActivity.this, R.style.dialog, getString(R.string.xiugaishibai) + "," + getString(R.string.wangluocuowu)).setHideCancelButton().show();
                    }
                });
        showLoadingDialog();
    }

    //发帖的接口
    public void postTieZiData() {
//        String addressStr = addressEditText.getText().toString().trim();
//        String phoneStr = tellEditText.getText().toString().trim();
        String titleStr = titleEditText.getText().toString().trim();
        String contentStr = contentEditText.getText().toString().trim();

        if ("".equals(titleStr)) {
            Toast.makeText(this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
            return;
        }

//        if (ITextUtils.validatePhoneNumber(phoneStr)) {
//            Toast.makeText(this, getString(R.string.qingshuruzhengquedeyanzhengma), Toast.LENGTH_SHORT).show();
//            return;
//        }

        ANRequest.MultiPartBuilder upload = AndroidNetworking.upload(Contact.tiezi_scend);
        if (list.size() <= 1) {
            upload.addMultipartParameter("user_id", MyApplication.getIntance().userBean.getUid())
                    .addMultipartParameter("title", titleStr)
                    .addMultipartParameter("content", contentStr);
        } else {
            int flag = 0;
            File file = null;
            for (int i = 0; i < list.size() - 1; i++) {
                flag++;
                String s = list.get(i);
                file = new File(s);
                if (i == 0) {
                    upload.addMultipartFile("myfile", file);
                } else {
                    upload.addMultipartFile("myfile" + i, file);
                }
            }

            upload.addMultipartParameter("user_id", MyApplication.getIntance().userBean.getUid())
                    .addMultipartParameter("title", titleStr)
                    .addMultipartParameter("content", contentStr);
        }

        upload.build().setUploadProgressListener(new UploadProgressListener() {
            @Override
            public void onProgress(long bytesUploaded, long totalBytes) {

            }
        }).getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                dismissLoadingDialog();
                Log.i("TAG", "发贴回调:" + response.toString());
                try {
                    if (JsonUtils.isSuccess(response)) {
                        Toast.makeText(FaTieActivity.this, getString(R.string.fatiechenggong), Toast.LENGTH_SHORT).show();
                        setResult(MainActivity.TIEZI);
                        finish();
                    } else {
                        String message = response.getString("message");
                        Toast.makeText(FaTieActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(FaTieActivity.this, getString(R.string.fatieshibai), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(ANError anError) {
                dismissLoadingDialog();
                Toast.makeText(FaTieActivity.this, getString(R.string.fatieshibaijianchawangluo), Toast.LENGTH_SHORT).show();
            }
        });
        showLoadingDialog();
    }

    private void danxuan(int position) {
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
                .maxNum(1)
                .build();
        SINGLE_REQUEST_CODE = position;
        // 跳转到图片选择器
        ImgSelActivity.startActivity(this, config, SINGLE_REQUEST_CODE);
    }

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
                .maxNum(MAX_NUM)
                .build();

        // 跳转到图片选择器
        ImgSelActivity.startActivity(this, config, REQUEST_CODE);

    }
}
