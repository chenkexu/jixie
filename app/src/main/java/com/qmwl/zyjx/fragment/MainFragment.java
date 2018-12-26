package com.qmwl.zyjx.fragment;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.hyphenate.chat.EMClient;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.activity.LoginActivity;
import com.qmwl.zyjx.activity.MainActivity;
import com.qmwl.zyjx.activity.MessagesActivity;
import com.qmwl.zyjx.activity.NewsActivity;
import com.qmwl.zyjx.activity.RegisterActivity;
import com.qmwl.zyjx.activity.SearchaActivity;
import com.qmwl.zyjx.activity.WebViewActivity;
import com.qmwl.zyjx.activity.WebViewShangJiaZhongXinActivity;
import com.qmwl.zyjx.adapter.MainFragmentAdapter;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.Flowbean;
import com.qmwl.zyjx.bean.MainDataBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.utils.SharedUtils;
import com.qmwl.zyjx.view.SelecterLanguageDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/18.
 */

public class MainFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    private View rootView;
    private ListView mLv;
    private View headerView;//头部文件
    private FlowFragment flowFragment;
    private MainFragmentAdapter adapter;
    private ViewFlipper zixunTextView;
    private ViewFlipper tieziTextView;
    //长宽比
    public static final float heightScale = 0.4589372f;
    //语言选择
    private TextView languageTv;
    //消息图标
    private ImageView messageView;
    private ConversationListBroastReciver reciver;
    private String web_phone;
    private Dialog loginStatueStatueDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getFlowImages();
        getShoppingData();
        getReMenNews();
        getDianHua();
        isShowLoginDialog();
        return rootView;
    }

    private void initView() {
        languageTv = (TextView) rootView.findViewById(R.id.base_top_bar_language);
        languageTv.setOnClickListener(this);

        messageView = (ImageView) rootView.findViewById(R.id.base_top_bar_right);
        mLv = (ListView) rootView.findViewById(R.id.main_fragment_listview);
        headerView = LayoutInflater.from(getContext()).inflate(R.layout.main_fragment_header_layout, null);
        headerView.findViewById(R.id.main_fragment_zhongyaozixun).setOnClickListener(this);
        headerView.findViewById(R.id.main_fragment_head_phone).setOnClickListener(this);
        ImageView zixunIv = (ImageView) headerView.findViewById(R.id.main_fragment_zixun_iv);
        ImageView tuangouIv = (ImageView) headerView.findViewById(R.id.main_fragment_tuangou_iv);
        ImageView paimaiIv = (ImageView) headerView.findViewById(R.id.main_fragment_paimai_iv);
        tuangouIv.setOnClickListener(this);
        paimaiIv.setOnClickListener(this);
        ImageView tieziIv = (ImageView) headerView.findViewById(R.id.main_fragment_tiezi_iv);
        if (MyApplication.getIntance().isChina) {
            paimaiIv.setImageResource(R.mipmap.paimai);
            tuangouIv.setImageResource(R.mipmap.tuangou);
            zixunIv.setImageResource(R.mipmap.zhongyaozixun);
            tieziIv.setImageResource(R.mipmap.xinxifabu);
        } else {
            paimaiIv.setImageResource(R.mipmap.paimai_en);
            tuangouIv.setImageResource(R.mipmap.tuangou_en);
            zixunIv.setImageResource(R.mipmap.zhongyaozixun_en);
            tieziIv.setImageResource(R.mipmap.xinxifabu_en);
        }
        if (MyApplication.getIntance().isChina) {
            languageTv.setText(getString(R.string.chinese));
        } else {
            languageTv.setText(getString(R.string.english));
        }
//        EMChatManager.getInstance().getUnreadMsgsCount();
        headerView.findViewById(R.id.main_fragment_zixun_re).setOnClickListener(this);
        zixunIv.setOnClickListener(this);

        zixunTextView = (ViewFlipper) headerView.findViewById(R.id.main_fragment_zixun_tv);
        zixunTextView.setOnClickListener(this);
        headerView.findViewById(R.id.main_fragment_zixun_right).setOnClickListener(this);

        tieziIv.setOnClickListener(this);
        headerView.findViewById(R.id.main_fragment_tiezi_re).setOnClickListener(this);

        tieziTextView = (ViewFlipper) headerView.findViewById(R.id.main_fragment_tiezi_tv);
        tieziTextView.setOnClickListener(this);
        headerView.findViewById(R.id.main_fragment_tiezi_right).setOnClickListener(this);

        View searchView = rootView.findViewById(R.id.base_top_bar_search);
//        View rightIv = rootView.findViewById(R.id.base_top_bar_right);
        messageView.setOnClickListener(this);
        searchView.setFocusable(false);
        searchView.setOnClickListener(this);
        mLv.addHeaderView(headerView);

        int width = getWidth();
        int imageViewHeight = (int) (width * heightScale);
        View flowContainer = headerView.findViewById(R.id.main_fragment_head_flow_container);
        ViewGroup.LayoutParams layoutParams = flowContainer.getLayoutParams();
        layoutParams.height = imageViewHeight;
        flowContainer.setLayoutParams(layoutParams);

        adapter = new MainFragmentAdapter();
        mLv.setAdapter(adapter);
        flowFragment = new FlowFragment();
        mLv.setOnItemClickListener(this);
        getFragmentManager().beginTransaction().add(R.id.main_fragment_head_flow_container, flowFragment).commit();

        //判断未读消息数量是否大于0
        MessageNum();

    }

    private void MessageNum() {
        if (messageView == null) {
            return;
        }
        int unreadMsgCountTotal = getUnreadMsgCountTotal();
        if (unreadMsgCountTotal > 0) {
            messageView.setImageResource(R.mipmap.message_new);
        } else {
            messageView.setImageResource(R.mipmap.message);
        }
    }

    public void mPause() {
        if (tieziTextView != null) {
            tieziTextView.stopFlipping();
        }
        if (zixunTextView != null) {
            zixunTextView.stopFlipping();
        }
    }

    public void mResume() {
        if (tieziTextView != null) {
            tieziTextView.startFlipping();
        }
        if (zixunTextView != null) {
            zixunTextView.startFlipping();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        rootView = inflater.inflate(R.layout.main_fragment_layout, null);
        initView();
    }

    /**
     * 获取所有未读消息数
     *
     * @return
     */
    public int getUnreadMsgCountTotal() {
        return EMClient.getInstance().chatManager().getUnreadMessageCount();
    }

    @Override
    public void onResume() {
        super.onResume();
        MessageNum();
        reciver = new ConversationListBroastReciver();
        IntentFilter intentFilter = new IntentFilter(ImListFragment.CONVER_SATION_LIST_CATION);
        getActivity().registerReceiver(reciver, intentFilter);
        if (flowFragment != null) {
            flowFragment.startFlow();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (flowFragment != null) {
            flowFragment.stopFlow();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (reciver != null && getActivity() != null) {
            getActivity().unregisterReceiver(reciver);
        }
        pauseFlowText();
    }

    //暂停滚动
    private void pauseFlowText() {
        if (tieziTextView != null) {
            tieziTextView.stopFlipping();
        }
        if (zixunTextView != null) {
            zixunTextView.stopFlipping();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.main_fragment_zhongyaozixun:
                intent = new Intent(getContext(), NewsActivity.class);
                startActivity(intent);
                break;

            case R.id.main_fragment_zixun_re:
            case R.id.main_fragment_zixun_tv:
            case R.id.main_fragment_zixun_right:
            case R.id.main_fragment_zixun_iv:
                //新闻列表
                intent = new Intent(getContext(), NewsActivity.class);
                startActivity(intent);
                break;

            case R.id.main_fragment_tiezi_re:
            case R.id.main_fragment_tiezi_tv:
            case R.id.main_fragment_tiezi_right:
            case R.id.main_fragment_tiezi_iv:
                //帖子列表
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.setCurrItem(1);
                break;
            case R.id.base_top_bar_search:
                intent = new Intent(getContext(), SearchaActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.activity_in_anim, R.anim.activity_out_anim);
                break;

            case R.id.base_top_bar_right:
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(getContext(), LoginActivity.class);
                    getActivity().startActivityForResult(intent, 0);
                    return;
                }
                intent = new Intent(getContext(), MessagesActivity.class);
                startActivity(intent);
                break;
            case R.id.base_top_bar_language:
                showlanguage();
                break;

            case R.id.main_fragment_tuangou_iv:
                //团购
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(getContext(), LoginActivity.class);
                    getActivity().startActivityForResult(intent, 0);
                    return;
                }
                intent = new Intent(getContext(), WebViewShangJiaZhongXinActivity.class);
                String url = Contact.paimaiOrTuangou + "/mobile/user.php" + "?act=act_login&username=" + MyApplication.getIntance().userBean.getUserName() + "&password=" + MyApplication.getIntance().userBean.getUserPassword() + "&back_act=" + Contact.paimaiOrTuangou + "/mobile/group_buy.php";
                intent.putExtra(WebViewActivity.SHOPURL, url);

                startActivity(intent);
                break;
            case R.id.main_fragment_paimai_iv:
                //拍卖
                if (!MyApplication.getIntance().isLogin()) {
                    intent = new Intent(getContext(), LoginActivity.class);
                    getActivity().startActivityForResult(intent, 0);
                    return;
                }
                intent = new Intent(getContext(), WebViewShangJiaZhongXinActivity.class);
                String url1 = Contact.paimaiOrTuangou + "/mobile/user.php" + "?act=act_login&username=" + MyApplication.getIntance().userBean.getUserName() + "&password=" + MyApplication.getIntance().userBean.getUserPassword() + "&back_act=" + Contact.paimaiOrTuangou + "/mobile/auction.php";
                intent.putExtra(WebViewActivity.SHOPURL, url1);
                startActivity(intent);
                break;
            case R.id.main_fragment_head_phone:
                tel(web_phone);
                break;
            case R.id.main_dialog_login_button:
                intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                loginStatueStatueDialog.dismiss();
                break;
            case R.id.main_dialog_register_button:
                intent = new Intent(getContext(), RegisterActivity.class);
                intent.putExtra(RegisterActivity.TYPE, true);
                startActivity(intent);
                loginStatueStatueDialog.dismiss();
                break;
            case R.id.dialog_show_loging_guanbi:
                if (loginStatueStatueDialog != null) {
                    loginStatueStatueDialog.dismiss();
                }
                break;

        }
    }

    public void tel(String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    //显示选择语言的选框
    private void showlanguage() {
        SelecterLanguageDialog selecteLanguage = new SelecterLanguageDialog(getContext(), R.style.dialog, getActivity());
        selecteLanguage.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    public void refreshHot() {
//        getReMenNews();
    }


    //获取热门咨询和热门新闻
    private void getReMenNews() {
        try {
            AndroidNetworking.post(Contact.main_remen_tiezi)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            List<String> strings = JsonUtils.parseZiXunListJson(response);
                            if (tieziTextView != null) {
                                setFlipperData(strings, tieziTextView);
                            }

                        }

                        @Override
                        public void onError(ANError anError) {

                        }
                    });
        } catch (Exception e) {

        }
        try {
            AndroidNetworking.post(Contact.main_remen_news)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            List<String> dataList = JsonUtils.parseZiXunListJson(response);
                            if (zixunTextView != null) {
                                setFlipperData(dataList, zixunTextView);
                            }
                        }

                        @Override
                        public void onError(ANError anError) {
                        }
                    });
        } catch (Exception e) {

        }
    }

    private void setFlipperData(List<String> dataList, ViewFlipper flipper) {
        if (flipper == null) {
            return;
        }
        flipper.removeAllViews();
        for (int i = 0; i < dataList.size(); i++) {
            TextView tv = new TextView(getContext());
            tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            tv.setText(dataList.get(i));
            tv.setSingleLine();
            tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.START);
            tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
            tv.setTextColor(getResources().getColor(R.color.window_main_text_color));
            if (i == 0) {
                tv.setVisibility(View.VISIBLE);
            } else {
                tv.setVisibility(View.GONE);
            }
            flipper.addView(tv, i);
        }
        flipper.startFlipping();
    }

    public int getWidth() {
        WindowManager wm = getActivity().getWindowManager();
        return wm.getDefaultDisplay().getWidth();
    }

    class ConversationListBroastReciver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //刷新小红点
            MessageNum();
        }
    }


    //获取网络数据
    private void getShoppingData() {
        AndroidNetworking.post(Contact.main_shoopings)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        List<MainDataBean> mainDataBeen = parseShoppingJson(response);
                        List<MainDataBean> mainDataBeen1 = sortList(mainDataBeen);
                        adapter.setData(mainDataBeen1);
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });
    }

    //获取轮播图
    private void getFlowImages() {
        AndroidNetworking.post(Contact.main_guanggao)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // do anything with response
                        List<Flowbean> flowbeen = JsonUtils.parseFlowJson(response);
                        if (flowbeen == null) {
                            Toast.makeText(getContext(), "暂无轮播图", Toast.LENGTH_SHORT).show();
                        } else {
                            flowFragment.setFlowData(flowbeen);
                        }
                    }

                    @Override
                    public void onError(ANError error) {
                        // handle error
                    }
                });

    }

    //解析商品id
    private List<MainDataBean> parseShoppingJson(JSONObject response) {
        List<MainDataBean> ssss = null;
        try {
            String code = response.getString("code");
            if ("0".equals(code)) {
                JSONObject data = response.getJSONObject("data");
                JSONArray niu_index_response = data.getJSONArray("niu_index_response");
                ssss = ssss(niu_index_response);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ssss;
    }

    private List<MainDataBean> ssss(JSONArray array) {
        List<MainDataBean> list = new ArrayList<>();
        JSONObject jsonObject = null;
        for (int i = 0; i < array.length(); i++) {
            try {
                jsonObject = array.getJSONObject(i);
                MainDataBean bean = new MainDataBean();
                String category_id = jsonObject.getString("category_id");
                String pid = jsonObject.getString("pid");
                String category_name = jsonObject.getString("category_name");
                String category_pic = jsonObject.getString("category_pic");
                String type = jsonObject.getString("type");
                String h5 = jsonObject.optString("h5");
                bean.setType(type);
                bean.setCategory_id(category_id);
                bean.setPid(pid);
                bean.setCategory_name(category_name);
                bean.setCategory_pic(category_pic);
                bean.setH5(h5);
                if (jsonObject.has("_child")) {
                    List<MainDataBean> child = ssss(jsonObject.getJSONArray("_child"));
                    if (child != null) {
                        bean.setChilds(child);
                    }
                }
                list.add(bean);
            } catch (JSONException e) {
                Log.d("huanguri","解析异常:"+e.toString() );
                e.printStackTrace();
            }

        }
        return list;
    }

    private List<MainDataBean> sortList(List<MainDataBean> dataList) {
        List<MainDataBean> list = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {

            MainDataBean mainDataBean = dataList.get(i);
            List<MainDataBean> childs = mainDataBean.getChilds();
            if (childs != null) {
                for (int j = 0; j < childs.size(); j++) {
                    MainDataBean childbean = childs.get(j);
                    if (j == 0) {
                        childbean.isFirst = true;
                        childbean.setParentName(mainDataBean.getCategory_name());
                    }
                    list.add(childbean);
                }

            }
        }
        return list;
    }

    private void getDianHua() {
        AndroidNetworking.get(Contact.shouyekefu)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject data = response.getJSONObject("data");
                            web_phone = data.getString("web_phone");

                            SharedUtils.putString("web_phone", web_phone, getContext());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });
    }

    private void isShowLoginDialog() {
        if (!MyApplication.getIntance().isLogin()) {
            showLoginDiaLog();
        }
    }

    public void showLoginDiaLog() {
        View layout = LayoutInflater.from(getContext()).inflate(R.layout.layout_dialog_show_login, mLv, false);
        layout.findViewById(R.id.main_dialog_login_button).setOnClickListener(this);
        layout.findViewById(R.id.main_dialog_register_button).setOnClickListener(this);
        layout.findViewById(R.id.dialog_show_loging_guanbi).setOnClickListener(this);

        // 创建自定义样式dialog
        loginStatueStatueDialog = new Dialog(getContext(), R.style.loading_dialog);
        loginStatueStatueDialog.setContentView(layout);
        loginStatueStatueDialog.show();
    }
}
