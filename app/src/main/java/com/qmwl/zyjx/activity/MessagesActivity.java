package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.hyphenate.easeui.EaseConstant;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.MessageAdapter;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.bean.MessageBean;
import com.qmwl.zyjx.bean.NewsBean;
import com.qmwl.zyjx.fragment.ImListFragment;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;
import com.qmwl.zyjx.utils.ListViewPullListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/7.
 * 消息页面
 */

public class MessagesActivity extends BaseActivity implements AdapterView.OnItemClickListener, ListViewPullListener.ListViewPullOrLoadMoreListener {
    private ListView mLv;
    private MessageAdapter adapter;
    private ListViewPullListener listViewPullListener;

    @Override
    protected void setLayout() {
//        setContentLayout(R.layout.messages_activity_layout);
        setContentLayout(R.layout.im_message_list_layout);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        getData();
    }

    @Override
    protected void initView() {
        ImListFragment imListFragment = new ImListFragment();
        Bundle args = new Bundle();
        args.putInt(EaseConstant.EXTRA_CHAT_TYPE, EaseConstant.CHATTYPE_SINGLE);
        args.putString(EaseConstant.EXTRA_USER_ID, MyApplication.getIntance().getHxId());
        imListFragment.setArguments(args);
        getSupportFragmentManager().beginTransaction().add(R.id.im_list_layout_container, imListFragment).commit();




//        setTitleContent(R.string.message);
//        mLv = (ListView) findViewById(R.id.messages_layout_listview);
//        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.message_layout_swiprefreshlayout);
//        adapter = new MessageAdapter();
//        mLv.setAdapter(adapter);
//        mLv.setOnItemClickListener(this);
//
//        listViewPullListener = new ListViewPullListener(mLv, swipeRefreshLayout, this);


    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {
//        getData();
    }

    private int page = 1;//分页

    private void getData() {
        AndroidNetworking.get(Contact.message_list + "?uid=" + MyApplication.getIntance().userBean.getUid() + "&page=" + page)
//                .addPathParameter("uid", "140")
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                listViewPullListener.cancelPullRefresh();
                List<MessageBean> newsBeen = parseMessageJson(response);
                if (isLoadMore) {
                    isLoadMore = false;
                    if (newsBeen == null || newsBeen.size() == 0) {
                        listViewPullListener.cancelLoadMore();
                    } else {
                        adapter.addData(newsBeen);
                    }
                } else {
                    adapter.setData(newsBeen);
                }
            }

            @Override
            public void onError(ANError anError) {
                listViewPullListener.cancelPullRefresh();
                if (isLoadMore) {
                    isLoadMore = false;
                    page--;
                    if (page < 1) {
                        page = 1;
                    }
                }
            }
        });
    }

    @Override
    protected void onMyClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, NewsDetailsActivity.class);
        intent.putExtra(NewsDetailsActivity.DETAILS_URL, adapter.getItem(position).getUrl());
        intent.putExtra(NewsDetailsActivity.DETAILS_TYPE, NewsDetailsActivity.TYPE_XIAOXI);
        startActivity(intent);
    }

    @Override
    public void onPullRefresh() {
        page = 1;
        getData();
    }

    private boolean isLoadMore = false;

    @Override
    public void onLoadMore() {
        if (adapter == null || adapter.getData() == null) {
            return;
        }
        isLoadMore = true;
        page++;
        getData();
    }

    //解析消息列表
    private List<MessageBean> parseMessageJson(JSONObject response) {
        List<MessageBean> list = null;
        MessageBean bean = null;
        try {
            JSONArray jsonArray = JsonUtils.parseJsonArray(response);
            if (jsonArray != null) {
                list = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    bean = new MessageBean();
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String message = jsonObject.getString("message");
                    long message_id = jsonObject.getLong("message_id");
                    long send_time = jsonObject.getLong("send_time");
                    String avatar = jsonObject.getString("avatar");
                    int is_read = jsonObject.getInt("is_read");
                    String notice = jsonObject.getString("notice");
                    String url = jsonObject.getString("url");
                    bean.setAvatar(avatar);
                    bean.setMessage(message);
                    bean.setMessage_id(message_id);
                    bean.setSend_time(send_time);
                    bean.setIs_read(is_read);
                    bean.setNotice(notice);
                    bean.setUrl(url);
                    list.add(bean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

}
