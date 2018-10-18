package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.adapter.NewsAdapter;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.bean.Flowbean;
import com.qmwl.zyjx.bean.NewsBean;
import com.qmwl.zyjx.fragment.FlowFragment;
import com.qmwl.zyjx.fragment.MainFragment;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/24.
 * //资讯页面
 */

public class NewsActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    NewsAdapter adapter;
    private FlowFragment flowFragment;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.news_activity_layout);
    }

    @Override
    protected void initView() {
        View back = findViewById(R.id.base_top_bar_back);
        TextView title = (TextView) findViewById(R.id.base_top_bar_title);
        title.setText(R.string.zixun);
        back.setVisibility(View.VISIBLE);
        back.setOnClickListener(this);
        ListView mLv = (ListView) findViewById(R.id.news_layout_listview);

        adapter = new NewsAdapter();
        mLv.setAdapter(adapter);
        mLv.setOnItemClickListener(this);

        View headView = getLayoutInflater().inflate(R.layout.flow_container_layout, null);
        mLv.addHeaderView(headView);

        int width = getWidth();
        int imageViewHeight = (int) (width * MainFragment.heightScale);
        View flowContainer = headView.findViewById(R.id.head_view_flow_container);
        ViewGroup.LayoutParams layoutParams = flowContainer.getLayoutParams();
        layoutParams.height = imageViewHeight;
        flowContainer.setLayoutParams(layoutParams);

        flowFragment = new FlowFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.head_view_flow_container, flowFragment).commit();

    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {
        getFlowImages();
        AndroidNetworking.get(Contact.news_list)
                .addPathParameter("page", "1")
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                List<NewsBean> newsBeen = parseNewsJson(response);
                adapter.setData(newsBeen);
            }

            @Override
            public void onError(ANError anError) {
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
                            Toast.makeText(NewsActivity.this, "暂无轮播图", Toast.LENGTH_SHORT).show();
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

    //解析新闻列表
    private List<NewsBean> parseNewsJson(JSONObject response) {
        List<NewsBean> list = null;
        NewsBean bean = null;
        try {
            if (JsonUtils.isSuccess(response)) {
                JSONObject data = response.getJSONObject("data");
                JSONArray niu_index_response = data.getJSONArray("niu_index_response");
                list = new ArrayList<>();
                for (int i = 0; i < niu_index_response.length(); i++) {
                    JSONObject jsonObject = niu_index_response.getJSONObject(i);
                    String article_id = jsonObject.getString("article_id");
                    String title = jsonObject.getString("title");
                    String image = jsonObject.getString("image");
                    String url = jsonObject.getString("url");
                    String create_time = jsonObject.getString("create_time");
                    bean = new NewsBean();
                    bean.setArticle_id(article_id);
                    bean.setTitle(title);
                    bean.setImage(image);
                    bean.setUrl(url);
                    bean.setCreate_time(create_time);

                    list.add(bean);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    protected void onMyClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(parent.getContext(), NewsDetailsActivity.class);
        intent.putExtra(NewsDetailsActivity.DETAILS_URL, adapter.getItem(position-1).getUrl());
        intent.putExtra(NewsDetailsActivity.DETAILS_TYPE, NewsDetailsActivity.TYPE_ZIXUN);
        startActivity(intent);
    }

}
