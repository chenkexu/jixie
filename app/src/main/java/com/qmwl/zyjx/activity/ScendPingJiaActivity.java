package com.qmwl.zyjx.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.hedgehog.ratingbar.RatingBar;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.GridImageAdapter;
import com.qmwl.zyjx.api.ApiManager;
import com.qmwl.zyjx.api.ApiResponse;
import com.qmwl.zyjx.api.BaseObserver;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.CancelOrderBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.FileUtils;
import com.qmwl.zyjx.utils.GlideUtils;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.utils.PictureSelectorUtil;
import com.qmwl.zyjx.utils.RxUtil;
import com.qmwl.zyjx.view.AskRetunPayDialog;
import com.qmwl.zyjx.view.FullyGridLayoutManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by Administrator on 2017/7/31.
 * 发表评价
 */

public class ScendPingJiaActivity extends BaseActivity {
    private static int REQUEST_CODE = 0;
    //订单ID
    public static final String order_id_data = "com.gh.pingjia_order_id";
    //订单号
    public static final String order_no_data = "com.gh.pingjia_order_no";
    //订单商品序列ID
    public static final String order_goods_id_data = "com.gh.pingjia_order_goods_id";
    //商品ID
    public static final String goods_id_data = "com.gh.pingjia_goods_id";
    //商品名称
    public static final String goods_name_data = "com.gh.pingjia_goods_name";
    //价格
    public static final String price_data = "com.gh.pingjia_price_data";
    //商铺ID
    public static final String shop_id_data = "com.gh.pingjia_shop_id";
    //商品图片
    public static final String shop_iv = "com.gh.pingjia_shop_iv";

    //商家用户名
    public static final String shop_name = "shop_name";
    @BindView(R.id.shop_name)
    TextView shopName;

    @BindView(R.id.business_name)
    TextView business_name;
    @BindView(R.id.rv_photo)
    RecyclerView rvPhoto;
    @BindView(R.id.dianpu_star)
    RatingBar dianpuStar;
    @BindView(R.id.server_star)
    RatingBar serverStar;

    private String order_id, order_no, order_goods_id, goods_id, goods_name, price, shop_id, shop_iv_url;

    private ImageView imageView;
    private RadioGroup radioGroup;
    private EditText pingjiaContent;
    private CheckBox nimingCheck;
    private RadioButton haopingRadioButton;
    private RadioButton zhongpingRadioButton;
    private RadioButton chapingPingRadioButton;

    private int dianpuStarNum = 0 ;
    private int serverStarNum = 0 ;



    private GridImageAdapter mAdapter;
    // 图片列表
    private List<LocalMedia> selectList = new ArrayList<>();
    private Dialog picDialog;

private Context mContext;



    @Override
    protected void setLayout() {
        setContentLayout(R.layout.scend_pingjia_layout);
        ButterKnife.bind(this);
        mContext=this;
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.fabiaopingjia);
        setRightText(R.string.fabu);
        imageView = (ImageView) findViewById(R.id.scend_pingjia_layout_iv);
        radioGroup = (RadioGroup) findViewById(R.id.scend_pingjia_layout_radiogroup);
        haopingRadioButton = (RadioButton) findViewById(R.id.scend_pingjia_layout_haoping);
        zhongpingRadioButton = (RadioButton) findViewById(R.id.scend_pingjia_layout_zhongping);
        chapingPingRadioButton = (RadioButton) findViewById(R.id.scend_pingjia_layout_chaping);
        pingjiaContent = (EditText) findViewById(R.id.scend_pingjia_layout_pingjiacontent);
        nimingCheck = (CheckBox) findViewById(R.id.scend_pingjia_layout_shifouniming);
        dianpuStar.setStar(0);
        serverStar.setStar(0);
        //评价
        dianpuStar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float RatingCount) {
                dianpuStarNum = (int) RatingCount;
            }
        });
        serverStar.setOnRatingChangeListener(new RatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(float RatingCount) {
                serverStarNum = (int) RatingCount;
            }
        });

        getData();
    }

    private void getData() {
        Intent intent = getIntent();
        order_id = intent.getStringExtra(order_id_data);
        order_no = intent.getStringExtra(order_no_data);
        order_goods_id = intent.getStringExtra(order_goods_id_data);
        goods_id = intent.getStringExtra(goods_id_data);
        goods_name = intent.getStringExtra(goods_name_data);
        price = intent.getStringExtra(price_data);
        shop_id = intent.getStringExtra(shop_id_data);
        shop_iv_url = intent.getStringExtra(shop_iv);

        String shop_nameStr = intent.getStringExtra(shop_name);

        GlideUtils.openImage(this, shop_iv_url, imageView);
        shopName.setText(goods_name);
        business_name.setText(shop_nameStr);


        FullyGridLayoutManager manager = new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        rvPhoto.setLayoutManager(manager);

        mAdapter = new GridImageAdapter(mContext, 3, new GridImageAdapter.OnAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                showPicChooseDialog();
            }
        });

        rvPhoto.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
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

            case R.id.base_top_bar_righttext:
                submitData();
                break;

        }
    }

    private void submitData() {
        String contentString = pingjiaContent.getText().toString().trim();
        if ("".equals(contentString) || TextUtils.isEmpty(contentString)) {
            Toast.makeText(this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
            return;
        }
        int pingjiaCode = getPingJiaCode();
        int nimingCode = getNimingCode();

        HashMap<String,Object> map=new HashMap<String,Object> ();

        map.put("uid", MyApplication.getIntance().userBean.getUid());
        map.put("order_id", order_id);
        map.put("order_no", order_no);
        map.put("order_goods_id", order_goods_id);
        map.put("goods_id", goods_id);
        map.put("goods_name", goods_name);
        map.put("price", price);
        map.put("shop_id", shop_id);
        map.put("explain_type", String.valueOf(pingjiaCode));
        map.put("content", contentString);
        map.put("is_anonymous", String.valueOf(nimingCode));

        File file=null;
        File file1=null;
        File file2=null;
        //图片申请
       // File imageFiles = new HashMap<>();
        //LocalMedia localMedia : selectList
        for (int i=0;i<selectList.size();i++) {
            if (i==0){
                String path;
                if (selectList.get(i).isCompressed()) {
                    // 如果压缩
                    path = selectList.get(i).getCompressPath();
                } else {
                    path = selectList.get(i).getPath();
                }
                file = new File(path);
            }else if(i==1){
                String path;
                if (selectList.get(i).isCompressed()) {
                    // 如果压缩
                    path = selectList.get(i).getCompressPath();
                } else {
                    path = selectList.get(i).getPath();
                }
                file1 = new File(path);
            }else if(i==2){
                String path;
                if (selectList.get(i).isCompressed()) {
                    // 如果压缩
                    path = selectList.get(i).getCompressPath();
                } else {
                    path = selectList.get(i).getPath();
                }
                file2 = new File(path);
            }

        }


       // map.put("myfile", new File(""));

      /*  ApiManager.getInstence().getApiService().fabiaopingjia(map,imageFiles)
                .compose(RxUtil.<ApiResponse<Object>>rxSchedulerHelper())
                .subscribe(new BaseObserver<Object>() {
                    @Override
                    protected void onSuccees(ApiResponse<Object> t) {
                        Log.d("huangrui","上传失败");
                        dismissLoadingDialog();
                        //try {
                            //if (JsonUtils.isSuccess(response)) {
                                Toast.makeText(ScendPingJiaActivity.this, getString(R.string.pingjiachenggong), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ScendPingJiaActivity.this, PingJiaSuccessActivity.class);
                                startActivity(intent);
                                finish();

                            //}
                       *//* } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ScendPingJiaActivity.this, getString(R.string.tijiaoshibai), Toast.LENGTH_SHORT).show();
                        }*//*

                    }

                    @Override
                    protected void onFailure(String errorInfo, boolean isNetWorkError) {
                        Log.d("huangrui","上传失败");
                        dismissLoadingDialog();
                        Toast.makeText(ScendPingJiaActivity.this, getString(R.string.pingjiashibai), Toast.LENGTH_SHORT).show();
                    }
                });*/


        AndroidNetworking.upload(Contact.fabiaopingjia)
               .addMultipartParameter("uid", MyApplication.getIntance().userBean.getUid())
                .addMultipartParameter("order_id", order_id)
                .addMultipartParameter("order_no", order_no)
                .addMultipartParameter("order_goods_id", order_goods_id)
                .addMultipartParameter("goods_id", goods_id)
                .addMultipartParameter("goods_name", goods_name)
                .addMultipartParameter("price", price)
                .addMultipartParameter("shop_id", shop_id)
                .addMultipartParameter("explain_type", String.valueOf(pingjiaCode))
                .addMultipartParameter("content", contentString)
                .addMultipartParameter("wuliuSerive", dianpuStarNum+"")
                .addMultipartParameter("seriveAttibute", serverStarNum+"")

                .addMultipartParameter("is_anonymous", String.valueOf(nimingCode))

                 .addMultipartFile("myfile", file)
                .addMultipartFile("myfile1", file1)
                .addMultipartFile("myfile2", file2)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        dismissLoadingDialog();
                        try {
                            if (JsonUtils.isSuccess(response)) {
                                Toast.makeText(ScendPingJiaActivity.this, getString(R.string.pingjiachenggong), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ScendPingJiaActivity.this, PingJiaSuccessActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(ScendPingJiaActivity.this, getString(R.string.tijiaoshibai), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        dismissLoadingDialog();
                        Log.d("huangrui","失败原因:"+anError.getErrorCode());
                        Toast.makeText(ScendPingJiaActivity.this, getString(R.string.pingjiashibai), Toast.LENGTH_SHORT).show();
                    }
                });
        showLoadingDialog();

    }

    private int getNimingCode() {
        int code = 0;
        if (nimingCheck.isChecked()) {
            code = 1;
        } else {
            code = 0;
        }
        return code;
    }

    //评价的code
    private int getPingJiaCode() {
        int code = 1;
        if (haopingRadioButton.isChecked()) {
            code = 1;
        } else if (zhongpingRadioButton.isChecked()) {
            code = 2;
        } else if (chapingPingRadioButton.isChecked()) {
            code = 3;
        }
        return code;
    }


    /**
     * 底部选图弹窗
     */
    private void showPicChooseDialog() {
        //设置要显示的view
        View view = View.inflate(mContext, R.layout.dialog_personal_head_pop, null);
        //此处可按需求为各控件设置属性
        view.findViewById(R.id.user_personal_pop_take_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 隐藏
                picDialog.dismiss();
                // 拍照
                PictureSelectorUtil.openCameraUseByFeedback((Activity) mContext, selectList);
            }});
        view.findViewById(R.id.user_personal_pop_pick_photo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 隐藏
                picDialog.dismiss();
                // 相册
                PictureSelectorUtil.openGalleryUseByFeedback((Activity) mContext, 3, selectList);
            }});
        view.findViewById(R.id.user_personal_pop_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 隐藏
                picDialog.dismiss();
            }
        });
        picDialog = new Dialog(mContext, R.style.common_pop_dialog);
        picDialog.setContentView(view);
        Window window = picDialog.getWindow();
        //设置弹出窗口大小
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        //设置显示位置
        window.setGravity(Gravity.BOTTOM);
        //设置动画效果
        //window.setWindowAnimations(R.style.AnimBottom);
        picDialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 图片选择结果回调
                    selectList = PictureSelector.obtainMultipleResult(data);
                    for (LocalMedia media : selectList) {
                        Log.i("图片----->", media.getPath());
                    }
                    mAdapter.setList(selectList);
                    mAdapter.notifyDataSetChanged();
                    Log.d("huangrui","huoqv的图片的list"+ Arrays.asList(selectList));
            }
        }
    }

}
