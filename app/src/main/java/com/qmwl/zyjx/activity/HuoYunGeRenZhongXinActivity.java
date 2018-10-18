package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.GlideUtils;
import com.qmwl.zyjx.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/8/30.
 * 货运的个人中心
 */

public class HuoYunGeRenZhongXinActivity extends BaseActivity {
    private View conteiner;
    private PopupWindow popupWindow;
    //承运认证状态(-50未认证，10个人 20企业)
    private int statueCode = -1;
    private ImageView iv;
    private TextView nickNameTv;
    private TextView chengYunStatueTv;
    private TextView huozhuStatueTv;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.huoyungerenzhongxin_activity_layout);
    }

    @Override
    protected void initView() {
        conteiner = findViewById(R.id.huoyungerenzhongxin_container);

        iv = (ImageView) findViewById(R.id.huoyun_wode_layout_iv);
        nickNameTv = (TextView) findViewById(R.id.huoyun_wode_nickname);
        huozhuStatueTv = (TextView) findViewById(R.id.huoyun_wode_gerenrenzheng_shifourenzheng);
        chengYunStatueTv = (TextView) findViewById(R.id.huoyun_wode_cengyunrenrenzheng_shifourenzheng);

        findViewById(R.id.huoyun_wode_wodeyundan).setOnClickListener(this);
        findViewById(R.id.huoyun_wode_qiyerenzheng).setOnClickListener(this);
        findViewById(R.id.huoyun_wode_xitongtuijian).setOnClickListener(this);
        findViewById(R.id.huoyun_wode_cheliangguanli).setOnClickListener(this);
        findViewById(R.id.huoyun_wode_huozhurenzheng).setOnClickListener(this);
        findViewById(R.id.huoyun_wode_fahuorenguanli).setOnClickListener(this);
        findViewById(R.id.huoyun_wode_shouhuorenguanli).setOnClickListener(this);
        findViewById(R.id.huoyun_wode_wodeyundan_chengyunfang).setOnClickListener(this);

        if (MyApplication.getIntance().isLogin()) {
//            getStatueChengYun();
            String headImg = MyApplication.getIntance().userBean.getHeadImg();
            if (!"".equals(headImg) && !TextUtils.isEmpty(headImg)) {
                GlideUtils.openHeadImage(this, headImg, iv);
            }
            nickNameTv.setText(MyApplication.getIntance().userBean.getNickName());
        }
        getUserData();
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (MyApplication.getIntance().isLogin()) {
            getStatue();
        }
    }

    @Override
    protected void onMyClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.huoyun_wode_qiyerenzheng:
                if (statueCode == -50) {
                    shwoPopuWindow();
                } else if (statueCode == 10) {
                    startGeRen();
                } else if (statueCode == 20) {
                    startQiye();
                }
                break;
            case R.id.huoyun_wode_guanbidialog:
                if (popupWindow != null && popupWindow.isShowing()) {
                    popupWindow.dismiss();
                }
                break;
            case R.id.huoyun_wode_wodeyundan:
                //货主我的运单
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(this, LoginActivity.class);
                    startActivityForResult(intent, 0);
                    return;
                }
                intent = new Intent(this, WoDeYunDanActivity.class);
                startActivity(intent);
                break;
            case R.id.huoyun_wode_qiyechengyun:
                startQiye();
                break;
            case R.id.huoyun_wode_getichengyun:
                startGeRen();
                break;
            case R.id.huoyun_wode_huozhurenzheng:
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(this, LoginActivity.class);
                    startActivityForResult(intent, 0);
                    return;
                }
                intent = new Intent(this, HuoZhuRenZhengActivity.class);
                startActivity(intent);
                break;
            case R.id.huoyun_wode_shouhuorenguanli:
                //收货人管理
                intent = new Intent(this, AddressActivity.class);
                intent.putExtra(AddressActivity.ADDRESS_ADDRESS_TYPE, AddressActivity.YUNSHU_ADDRESS);
                startActivity(intent);
                break;
            case R.id.huoyun_wode_fahuorenguanli:
                //发货人管理
                intent = new Intent(this, AddressActivity.class);
                intent.putExtra(AddressActivity.ADDRESS_ADDRESS_TYPE, AddressActivity.YUNSHU_FAHUO_ADDRESS);
                startActivity(intent);
                break;
            case R.id.huoyun_wode_wodeyundan_chengyunfang:
                //承运方我的运单
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(this, LoginActivity.class);
                    startActivityForResult(intent, 0);
                    return;
                }
                intent = new Intent(this, WoDeYunDanChengYunFangActivity.class);
                startActivity(intent);
                break;
            case R.id.huoyun_wode_xitongtuijian:
                //系统推荐
                intent = new Intent(this, HuoYuanActivity.class);
//                intent.putExtra(HuoYuanActivity.IS_TUIJIAN, true);
                startActivity(intent);

                break;
            case R.id.huoyun_wode_cheliangguanli:
                //车辆管理
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(this, LoginActivity.class);
                    startActivityForResult(intent, 0);
                    return;
                }
                intent = new Intent(this, CheLiangGuanLiActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void startQiye() {
        Intent intent;
        if (!MyApplication.getIntance().isLogin()) {
            intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, 0);
            return;
        }
        intent = new Intent(this, ChengYunFangRenZhengActivity.class);
        startActivity(intent);
    }

    private void startGeRen() {
        Intent intent;
        if (!MyApplication.getIntance().isLogin()) {
            intent = new Intent(this, LoginActivity.class);
            startActivityForResult(intent, 0);
            return;
        }
        intent = new Intent(this, ChengYunRenRenZhengActivity.class);
        startActivity(intent);
    }

    private void shwoPopuWindow() {
        if (popupWindow == null) {
            popupWindow = new PopupWindow(getWidth(), getHeight());
            View contentView = getLayoutInflater().inflate(R.layout.chengyunrenrenzheng_dialog_layout, null);
            contentView.findViewById(R.id.huoyun_wode_qiyechengyun).setOnClickListener(this);
            contentView.findViewById(R.id.huoyun_wode_getichengyun).setOnClickListener(this);
            contentView.findViewById(R.id.huoyun_wode_guanbidialog).setOnClickListener(this);
            popupWindow.setContentView(contentView);
            popupWindow.setFocusable(true);
            popupWindow.setBackgroundDrawable(new BitmapDrawable());
        }
        popupWindow.showAtLocation(conteiner, Gravity.TOP, 0, 0);
    }

    //承运人认证审核状态
    private void getStatueChengYun() {
        AndroidNetworking.get(Contact.yunshu_panduanchengyunren_renzheng + "?uid=" + MyApplication.getIntance().userBean.getUid())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            if (code == -50) {
                                statueCode = -50;
                                chengYunStatueTv.setText(getString(R.string.qingrenzheng));
                            } else if (code == 10) {
                                //个人认证
                                statueCode = 10;
                                getchengyunrenrenzheng();
//                                chengYunStatueTv.setText(getString(R.string.yirenzheng));
                            } else if (code == 20) {
                                //企业认证
                                statueCode = 20;
                                getchengyunfangrenzheng();
//                                chengYunStatueTv.setText(getString(R.string.yirenzheng));
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

    //获取货主认证状态和承运人认证状态
    private void getStatue() {
        getStatueChengYun();
        getHuoZhuState();
    }

    //获取用户信息
    private void getUserData() {
        if (!MyApplication.getIntance().isLogin()) {
            iv.setImageResource(R.mipmap.morentouxiang);
            nickNameTv.setText(getString(R.string.weidenglu));
            return;
        }
        AndroidNetworking.get(Contact.user_info + "?user_id=" + MyApplication.getIntance().userBean.getUid())
//                .addBodyParameter("user_id", MyApplication.getIntance().userBean.getUid())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JsonUtils.parseUserData(response);
                        nickNameTv.setText(MyApplication.getIntance().userBean.getNickName());
                        GlideUtils.openHeadImage(HuoYunGeRenZhongXinActivity.this, MyApplication.getIntance().userBean.getHeadImg(), iv);
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });
    }

    //货主认证审核状态
    private void getHuoZhuState() {
        AndroidNetworking.get(Contact.huozhurenzhengstate + "?uid=" + MyApplication.getIntance().userBean.getUid())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        int state = JsonUtils.parseShenHeState(response);
                        //0为审核中，1成功，2失败
                        switch (state) {
                            case -1:
                                huozhuStatueTv.setText(getString(R.string.qingrenzheng));
                                break;
                            case 0:
                                huozhuStatueTv.setText(getString(R.string.shenhezhong));
                                break;
                            case 1:
                                huozhuStatueTv.setText(getString(R.string.yirenzheng));
                                break;
                            case 2:
                                huozhuStatueTv.setText(getString(R.string.weitongguo));
                                break;
                            default:
                                huozhuStatueTv.setText(getString(R.string.qingrenzheng));
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });
    }

    int statue = 0;

    //承运方审核状态
    private void getchengyunfangrenzheng() {
//        AndroidNetworking.get(Contact.chengyunfangrenzhengstate + "?uid=" + MyApplication.getIntance().userBean.getUid())
        AndroidNetworking.get(Contact.yunshu_chengyun_qieye_statue + "?uid=" + MyApplication.getIntance().userBean.getUid())
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
                            setChengYunStatue(statue);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(HuoYunGeRenZhongXinActivity.this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(HuoYunGeRenZhongXinActivity.this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    //承运人个人认证
    private void getchengyunrenrenzheng() {
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
                            setChengYunStatue(statue);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(HuoYunGeRenZhongXinActivity.this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(HuoYunGeRenZhongXinActivity.this, getString(R.string.xinxibuwanzheng), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

    private void setChengYunStatue(int statue) {
        switch (statue) {
            case 0:
                chengYunStatueTv.setText(getString(R.string.shenhezhong));
                break;
            case 1:
                chengYunStatueTv.setText(getString(R.string.yirenzheng));
                break;
            case 2:
                chengYunStatueTv.setText(getString(R.string.weitongguo));
                break;
            case 3:
            default:
                chengYunStatueTv.setText(getString(R.string.qingrenzheng));

        }
    }
}
