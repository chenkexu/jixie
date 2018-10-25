package com.qmwl.zyjx.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.activity.CollectionActivity;
import com.qmwl.zyjx.activity.KaiDianChongXinTiJiaoActivity;
import com.qmwl.zyjx.activity.LiuLanJiLuActivity;
import com.qmwl.zyjx.activity.LoginActivity;
import com.qmwl.zyjx.activity.RegisterActivity;
import com.qmwl.zyjx.activity.RegisterDuiGongTishiActivity;
import com.qmwl.zyjx.activity.RegisterPersonActivity;
import com.qmwl.zyjx.activity.RegisterQiYeActivity;
import com.qmwl.zyjx.activity.RegisterSelecterActivity;
import com.qmwl.zyjx.activity.TuiKuanShouHouActivity;
import com.qmwl.zyjx.activity.WebViewActivity;
import com.qmwl.zyjx.activity.WebViewShangJiaZhongXinActivity;
import com.qmwl.zyjx.activity.WoDeFaTieActivity;
import com.qmwl.zyjx.activity.SettingActivity;
import com.qmwl.zyjx.activity.WoDeDingDanActivity;
import com.qmwl.zyjx.activity.WoDePingJiaActivity;
import com.qmwl.zyjx.activity.WoDeZuLingActivity;
import com.qmwl.zyjx.activity.YiJianFanKuiActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.EventManager;
import com.qmwl.zyjx.utils.GlideUtils;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.view.CommomDialog;
import com.qmwl.zyjx.view.SelecterLanguageDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.simple.eventbus.Subscriber;
import org.simple.eventbus.ThreadMode;

/**
 * Created by Administrator on 2017/7/19
 * 个人中心页面.
 */

public class FourFragment extends Fragment implements View.OnClickListener {
    private TextView nickNameTv;
    private ImageView iv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.wode_fragment_layout, null);
        initView(rootView);
        getUserData();
        return rootView;
    }

    private void initView(View rootView) {
        EventManager.register(this);
        iv = (ImageView) rootView.findViewById(R.id.wode_layout_iv);
        rootView.findViewById(R.id.wode_layout_iv).setOnClickListener(this);
        rootView.findViewById(R.id.wode_fatie_button).setOnClickListener(this);
        rootView.findViewById(R.id.wode_fragment_yuyan).setOnClickListener(this);
        rootView.findViewById(R.id.wode_dingdan_button).setOnClickListener(this);
        rootView.findViewById(R.id.wode_tuikuan_button).setOnClickListener(this);
        rootView.findViewById(R.id.wode_shoucang_button).setOnClickListener(this);
        rootView.findViewById(R.id.wode_daifahuo_button).setOnClickListener(this);
        rootView.findViewById(R.id.wode_fragment_setting).setOnClickListener(this);
        rootView.findViewById(R.id.wode_daifukuan_button).setOnClickListener(this);
        rootView.findViewById(R.id.wode_fragment_pingjia).setOnClickListener(this);
        rootView.findViewById(R.id.wode_liulanjilu_button).setOnClickListener(this);
        rootView.findViewById(R.id.wode_daipingjia_button).setOnClickListener(this);
        rootView.findViewById(R.id.wode_daishouhuo_button).setOnClickListener(this);
        rootView.findViewById(R.id.wode_fragment_wodezuling).setOnClickListener(this);
        rootView.findViewById(R.id.wode_woyaokaidian_button).setOnClickListener(this);
        rootView.findViewById(R.id.wode_fragment_yijianfankui).setOnClickListener(this);
        rootView.findViewById(R.id.wode_shangjiazhongxin_button).setOnClickListener(this);
        nickNameTv = (TextView) rootView.findViewById(R.id.wode_fragment_layout_nickname);

    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.wode_fragment_setting:
                //设置页面
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(getContext(), LoginActivity.class);
                    startActivityForResult(intent, 0);
                } else {
                    intent = new Intent(getContext(), SettingActivity.class);
                    startActivity(intent);
                }
                break;

            case R.id.wode_fragment_pingjia:
                //我的评价
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(getContext(), LoginActivity.class);
                    startActivityForResult(intent, 0);
                    return;
                }
                intent = new Intent(getContext(), WoDePingJiaActivity.class);
                startActivity(intent);
                break;

            case R.id.wode_fragment_yijianfankui:
                //我的反馈
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(getContext(), LoginActivity.class);
                    startActivityForResult(intent, 0);
                    return;
                }
                intent = new Intent(getContext(), YiJianFanKuiActivity.class);
                startActivity(intent);
                break;

            case R.id.wode_fragment_wodezuling:
                //我的租赁
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(getContext(), LoginActivity.class);
                    startActivityForResult(intent, 0);
                    return;
                }
                intent = new Intent(getContext(), WoDeZuLingActivity.class);
                startActivity(intent);
                break;

            case R.id.wode_dingdan_button:
                //我的订单
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(getContext(), LoginActivity.class);
                    startActivityForResult(intent, 0);
                } else {
                    intent = new Intent(getContext(), WoDeDingDanActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.wode_liulanjilu_button:
                //浏览记录
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(getContext(), LoginActivity.class);
                    startActivityForResult(intent, 0);
                    return;
                }
                intent = new Intent(getContext(), LiuLanJiLuActivity.class);
                startActivity(intent);
                break;
            case R.id.wode_shoucang_button:
                //收藏
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(getContext(), LoginActivity.class);
                    startActivityForResult(intent, 0);
                    return;
                }
                intent = new Intent(getContext(), CollectionActivity.class);
                startActivity(intent);
                break;
            case R.id.wode_daifukuan_button:
                //待付款
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(getContext(), LoginActivity.class);
                    startActivityForResult(intent, 0);
                    return;
                }
                intent = new Intent(getContext(), WoDeDingDanActivity.class);
                intent.putExtra(WoDeDingDanActivity.WODEDINGDAN_INDEX_VALUE, 1);
                startActivity(intent);
                break;
            case R.id.wode_daifahuo_button:
                //待发货
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(getContext(), LoginActivity.class);
                    startActivityForResult(intent, 0);
                    return;
                }
                intent = new Intent(getContext(), WoDeDingDanActivity.class);
                intent.putExtra(WoDeDingDanActivity.WODEDINGDAN_INDEX_VALUE, 2);
                startActivity(intent);
                break;
            case R.id.wode_daishouhuo_button:
                //待收货
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(getContext(), LoginActivity.class);
                    startActivityForResult(intent, 0);
                    return;
                }
                intent = new Intent(getContext(), WoDeDingDanActivity.class);
                intent.putExtra(WoDeDingDanActivity.WODEDINGDAN_INDEX_VALUE, 3);
                startActivity(intent);
                break;
            case R.id.wode_daipingjia_button:
                //待评价
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(getContext(), LoginActivity.class);
                    startActivityForResult(intent, 0);
                    return;
                }
                intent = new Intent(getContext(), WoDeDingDanActivity.class);
                intent.putExtra(WoDeDingDanActivity.WODEDINGDAN_INDEX_VALUE, 4);
                startActivity(intent);
                break;

            case R.id.wode_tuikuan_button:
                //退款
//                Toast.makeText(getContext(), "此功能暂未开放", Toast.LENGTH_SHORT).show();
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(getContext(), LoginActivity.class);
                    getActivity().startActivityForResult(intent, 0);
                    return;
                }
                intent = new Intent(getContext(), WoDeDingDanActivity.class);
                intent.putExtra(WoDeDingDanActivity.WODEDINGDAN_INDEX_VALUE, 5);
                startActivity(intent);
                break;

            case R.id.wode_fatie_button:
                //我的发帖
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(getContext(), LoginActivity.class);
                    getActivity().startActivityForResult(intent, 0);
                    return;
                }
                intent = new Intent(getContext(), WoDeFaTieActivity.class);
                startActivity(intent);
                break;

            case R.id.wode_layout_iv:
                //我的头像
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(getContext(), LoginActivity.class);
                    getActivity().startActivityForResult(intent, 0);
                    return;
                }
                intent = new Intent(getContext(), SettingActivity.class);
//                intent = new Intent(getContext(), GPSNaviActivity.class);
                startActivity(intent);
                break;
            case R.id.wode_fragment_yuyan:
                //选择语言
                showlanguage();
                break;
            case R.id.wode_woyaokaidian_button:
                //我要开店
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(getContext(), LoginActivity.class);
                    getActivity().startActivityForResult(intent, 0);
                    return;
                }
//                if (MyApplication.getIntance().isLogin() && MyApplication.getIntance().is_system == 1) {
//                    Toast.makeText(getContext(), getString(R.string.ninyijingshishangjia), Toast.LENGTH_SHORT).show();
//                    return;
//                }

//                intent = new Intent(getContext(), RegisterActivity.class);
//                startActivity(intent);
                getWoYaokaidianStatue();
                break;
            case R.id.wode_shangjiazhongxin_button:
                //商家中心
//                if (MyApplication.getIntance().is_system == 0) {
//                    Toast.makeText(getContext(), getString(R.string.ninhaibushishangjia), Toast.LENGTH_SHORT).show();
//                    return;
//                }
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(getContext(), LoginActivity.class);
                    getActivity().startActivityForResult(intent, 0);
                    return;
                }
//                getShangJiaZhongXin();
                if (!isLoading) {
                    isLoading = true;
                    getWoYaokaidianStatue1();
                }

                break;
        }
    }

    //显示选择语言的选框
    private void showlanguage() {
        SelecterLanguageDialog selecteLanguage = new SelecterLanguageDialog(getContext(), R.style.dialog, getActivity());
        selecteLanguage.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserData();
    }

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
//                        Glide.with(getContext()).load(MyApplication.getIntance().userBean.getHeadImg()).into(iv);
                        GlideUtils.openHeadImage(getContext(), MyApplication.getIntance().userBean.getHeadImg(), iv);
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });
    }

    private void getWoYaokaidianStatue() {
        AndroidNetworking.get(Contact.woyaokaidian_statue + "?uid=" + MyApplication.getIntance().userBean.getUid())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // 1-审核中 -1-审核失败 2-审核通过开店 -2個人用戶
                        try {
                            JSONObject data = response.getJSONObject("data");
                            JSONObject niu_index_response = data.getJSONObject("niu_index_response");
//                            final int state = data.getInt("niu_index_response");
                            final int state = niu_index_response.getInt("apply_state");
                            String statue = "";
                            if (1 == state) {
                                statue = getString(R.string.shenhezhong);
                            } else if (-1 == state) {
                                statue = getString(R.string.shenheweitongguo) + "," + getString(R.string.chongxintijiaoziliao);
                            } else if (2 == state) {
                                statue = getString(R.string.ninyijingshishangjia);
                            } else if (-2 == state) {
                                Intent intent = new Intent(getContext(), RegisterActivity.class);
                                startActivity(intent);
                                return;
                            } else if (3 == state) {
                                statue = getString(R.string.shangdianyiguanbi);
                            } else {
                                statue = getString(R.string.wangluocuowu);
                            }
                            new CommomDialog(getContext(), R.style.dialog, statue, new CommomDialog.OnCloseListener() {
                                @Override
                                public void onClick(Dialog dialog, boolean confirm) {
                                    if (state == -1 && confirm) {
                                        Intent intent = new Intent(getContext(), KaiDianChongXinTiJiaoActivity.class);
                                        startActivity(intent);
                                    }
                                    dialog.dismiss();
                                }
                            }).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });
    }

    private boolean isLoading;

    //获取个人信息状态
    private void getWoYaokaidianStatue1() {
        AndroidNetworking.get(Contact.kaidian_url + "?user_id=" + MyApplication.getIntance().userBean.getUid())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        isLoading = false;
                        try {
                            //0 未开通 1此用户对公付款 2 此用户是商户 3提交资料，未到支付
                            int code = response.getInt("code");
                                if (code==2){
                                    Intent intent = new Intent(getContext(), WebViewShangJiaZhongXinActivity.class);
                                    intent.putExtra(WebViewActivity.SHOPURL, Contact.shangjiazhongxin_url + "?user_id=" + MyApplication.getIntance().userBean.getUid());
                                    startActivity(intent);
                                }else{
                                    String message = response.getString("message");
                                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                                }

//                            switch (code) {
//                                case 0:
//                                case 1:
//                                    String message = response.getString("message");
//                                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
//                                    break;
//                                case 2:
//                                    Intent intent = new Intent(getContext(), WebViewShangJiaZhongXinActivity.class);
//                                    intent.putExtra(WebViewActivity.SHOPURL, Contact.shangjiazhongxin_url + "?user_id=" + MyApplication.getIntance().userBean.getUid());
//                                    startActivity(intent);
//                                    break;
//                                case 3:
//                                    new CommomDialog(getContext(), R.style.dialog, getString(R.string.ninhaibushishangjia)).setHideCancelButton().show();
//                                    break;
//                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        isLoading = false;
                    }
                });

    }


    private void getShangJiaZhongXin() {
        AndroidNetworking.get(Contact.shangjiazhongxin_statue + "?user_id=" + MyApplication.getIntance().userBean.getUid())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int code = response.getInt("code");
                            if (code == 2) {
                                Intent intent = new Intent(getContext(), WebViewShangJiaZhongXinActivity.class);
                                intent.putExtra(WebViewActivity.SHOPURL, Contact.shangjiazhongxin_url + "?user_id=" + MyApplication.getIntance().userBean.getUid());
                                startActivity(intent);
                            } else if (code == -1) {
                                //审核未通过
                                String statue = getString(R.string.shenheweitongguo) + "," + getString(R.string.chongxintijiaoziliao);
                                new CommomDialog(getContext(), R.style.dialog, statue, new CommomDialog.OnCloseListener() {
                                    @Override
                                    public void onClick(Dialog dialog, boolean confirm) {
                                        Intent intent = new Intent(getContext(), KaiDianChongXinTiJiaoActivity.class);
                                        startActivity(intent);
                                        dialog.dismiss();
                                    }
                                }).show();
                            } else {
                                String message = response.getString("message");
                                new CommomDialog(getContext(), R.style.dialog, message).setHideCancelButton().show();
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


    //收到友盟推送打开店铺
    @Subscriber(tag = "umeng", mode= ThreadMode.MAIN)
    public void getEventMessage(String message) {
     Log.d("huangrui","收到的url连接"+message);
      //  if (!isLoading) {
       //     isLoading = true;
            getWoYaokaidianStatue1();
      //  }
    }
}
