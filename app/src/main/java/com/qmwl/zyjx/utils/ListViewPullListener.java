package com.qmwl.zyjx.utils;

import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.activity.WoDeFaTieActivity;
import com.qmwl.zyjx.view.GridViewWithHeaderAndFooter;

/**
 * Created by Administrator on 2017/8/15.
 * 上拉加载下拉刷新的帮助类
 */

public class ListViewPullListener implements AbsListView.OnScrollListener, SwipeRefreshLayout.OnRefreshListener {
    private ListView listView;
    private GridViewWithHeaderAndFooter gridView;
    private SwipeRefreshLayout swipRefreshLayout;
    private View footView;
    private ListViewPullOrLoadMoreListener mListener;
    private long preMillion = 0;
    private int JIANGE = 200;//上拉间隔
    private boolean close_loadmore = false;
    private boolean isGridView;
    private boolean isHeadView;

    public interface ListViewPullOrLoadMoreListener {
        void onPullRefresh();//下拉刷新

        void onLoadMore();//上拉加载更多
    }

    public ListViewPullListener(ListView listView, SwipeRefreshLayout swipRefreshLayout, ListViewPullOrLoadMoreListener mListener) {
        this.listView = listView;
        this.swipRefreshLayout = swipRefreshLayout;
        this.mListener = mListener;
        init();
    }

    public ListViewPullListener(ListView listView, SwipeRefreshLayout swipRefreshLayout, boolean isHeadView, ListViewPullOrLoadMoreListener mListener) {
        this.listView = listView;
        this.swipRefreshLayout = swipRefreshLayout;
        this.mListener = mListener;
        this.isHeadView = isHeadView;
        init();
    }

    public ListViewPullListener(GridViewWithHeaderAndFooter gridView, SwipeRefreshLayout swipRefreshLayout, ListViewPullOrLoadMoreListener mListener) {
        this.gridView = gridView;
        this.swipRefreshLayout = swipRefreshLayout;
        this.mListener = mListener;
        initGridView();
    }


    //取消刷新状态
    public void cancelPullRefresh() {
        if (swipRefreshLayout != null) {
            swipRefreshLayout.setRefreshing(false);
        }
    }

    //关闭上拉加载
    public void cancelLoadMore() {
        footView.setVisibility(View.GONE);
        close_loadmore = true;
    }

    //打开上拉加载
    public void openLoadMore() {
        close_loadmore = false;
    }

    private void init() {
        listView.setOnScrollListener(this);
        footView = LayoutInflater.from(listView.getContext()).inflate(R.layout.listview_foot_view, null);
        FrameLayout frameLayout = new FrameLayout(listView.getContext());
        frameLayout.addView(footView);
        footView.setVisibility(View.GONE);
        listView.addFooterView(frameLayout);
        setSwiprefreshlayout();
    }

    private void initGridView() {
        isGridView = true;
        gridView.setOnScrollListener(this);
        footView = LayoutInflater.from(gridView.getContext()).inflate(R.layout.listview_foot_view, null);
        FrameLayout frameLayout = new FrameLayout(gridView.getContext());
        frameLayout.addView(footView);
        footView.setVisibility(View.GONE);
        gridView.addFooterView(frameLayout);
        setSwiprefreshlayout();
    }

    private void setSwiprefreshlayout() {
        // 设置下拉进度的主题颜色
        if (swipRefreshLayout != null) {
            swipRefreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
            swipRefreshLayout.setOnRefreshListener(this);
        }
    }

    @Override
    public void onRefresh() {
        if (mListener != null) {
            mListener.onPullRefresh();
            openLoadMore();
        }
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        try {
            if (firstVisibleItem == 0) {
                View firstVisibleItemView = listView.getChildAt(0);
                if (firstVisibleItemView != null && firstVisibleItemView.getTop() == 0) {
                }
            } else if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
                Log.i("TAG", "上拉totalItemCount  ");
                if (close_loadmore) {
                    return;
                }
                if (isHeadView) {
                    if (mListener != null) {
                        mListener.onLoadMore();
                        return;
                    }
                }
                View lastVisibleItemView;
                int height = 0;
                if (isGridView) {
                    lastVisibleItemView = gridView.getChildAt(gridView.getChildCount() - 1);
                    height = gridView.getHeight();
                } else {
                    lastVisibleItemView = listView.getChildAt(listView.getChildCount() - 1);
                    height = listView.getHeight();
                }

                if (lastVisibleItemView != null && lastVisibleItemView.getBottom() == height) {
                    if (System.currentTimeMillis() - preMillion < JIANGE) {
                        return;
                    }
                    preMillion = System.currentTimeMillis();
                    if (mListener != null) {
                        footView.setVisibility(View.VISIBLE);
                        mListener.onLoadMore();
                        Log.i("TAG", "上拉  ");
                    }
                }
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }


}
