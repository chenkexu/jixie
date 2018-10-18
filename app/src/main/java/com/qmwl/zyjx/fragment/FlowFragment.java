package com.qmwl.zyjx.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.activity.ShangJiaZhongXinActivity;
import com.qmwl.zyjx.adapter.FlowPageAdapter;
import com.qmwl.zyjx.bean.Flowbean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/19.
 */

public class FlowFragment extends Fragment implements ViewPager.OnPageChangeListener, View.OnClickListener {

    private int delayedTime = 3000;//滚动间隔
    private boolean isFlow = false;//是否已经开始滚动任务
    private List<Flowbean> mList;//数据集合
    private int maxNum = -1;//最大数量
    private int index = 0;//当前下标
    private LinearLayout pointLayout;//指示器的集合

    private Drawable whileDrawable;
    private Drawable redDrawable;
    private ViewPager mVp;
    private Handler mHandler = new Handler();


    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (index >= maxNum - 1) {
                index = 0;
            } else {
                index++;
            }
            if (mVp != null) {
                mVp.setCurrentItem(index);
                mHandler.postDelayed(runnable, delayedTime);
            }
        }
    };


    public void setData(List<Flowbean> list) {
        this.mList = list;
        maxNum = list.size();
        stopFlow();
        startFlow();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        RelativeLayout view = initView();
//        setData();
//        addChildView();
//        addPoint();
        return view;
    }

    private RelativeLayout initView() {

        RelativeLayout rootView = new RelativeLayout(getContext());
        whileDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.point_white_drawble, null);
        redDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.point_red_drawble, null);
        mVp = new ViewPager(getContext());
        ViewGroup.LayoutParams rootViewLayoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        rootView.setLayoutParams(rootViewLayoutParams);
        RelativeLayout.LayoutParams vpLayoutparams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mVp.setLayoutParams(vpLayoutparams);
        rootView.addView(mVp);

        pointLayout = new LinearLayout(getContext());
        RelativeLayout.LayoutParams linearViewLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        linearViewLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
        linearViewLayoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        pointLayout.setOrientation(LinearLayout.HORIZONTAL);
        linearViewLayoutParams.bottomMargin = 34;
        pointLayout.setLayoutParams(linearViewLayoutParams);
        rootView.addView(pointLayout);
        adapter = new FlowPageAdapter();
        mVp.setAdapter(adapter);
        mVp.addOnPageChangeListener(this);

        return rootView;
    }

    private FlowPageAdapter adapter;

    public void setFlowData(List<Flowbean> list) {
        if (list == null) {
            return;
        }
        stopFlow();
        mList = list;
        maxNum = mList.size();
        addChildView(list);
        addPoint();
        startFlow();
    }

    private void addChildView(List<Flowbean> dataList) {
        if (mList == null) {
            return;
        }
        List<View> list = new ArrayList<>();
        for (int i = 0; i < mList.size(); i++) {
//            TextView iv1 = new TextView(getContext());
//            iv1.setText(i + "");
            ImageView iv1 = new ImageView(getContext());
            iv1.setScaleType(ImageView.ScaleType.FIT_XY);
            Flowbean flowbean = dataList.get(i);
            iv1.setOnClickListener(this);
            Glide.with(getContext()).load(flowbean.getAdv_image()).into(iv1);
//            iv1.setTag(flowbean.getAdv_url());
            list.add(iv1);
//            iv1.setBackgroundColor(Color.parseColor("#440000ff"));
        }
        adapter.setData(list);
    }

    private void addPoint() {
        if (mList == null) {
            return;
        }
        pointLayout.removeAllViews();
        for (int i = 0; i < mList.size(); i++) {
            View view = new View(getContext());
            LinearLayout.LayoutParams viewParams = new LinearLayout.LayoutParams(30, 30);
            viewParams.leftMargin = 10;
            viewParams.rightMargin = 10;
            view.setLayoutParams(viewParams);
            view.setBackground(whileDrawable);
            pointLayout.addView(view);
        }
        try {
            pointLayout.getChildAt(0).setBackground(redDrawable);
        } catch (NullPointerException e) {
        }
    }

    public void startFlow() {
        if (!isFlow) {
            isFlow = true;
            mHandler.postDelayed(runnable, delayedTime);
        }
    }

    public void stopFlow() {
        isFlow = false;
        mHandler.removeCallbacks(runnable);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    private int preIndex = 0;//上一个

    @Override
    public void onPageSelected(int position) {
        index = position;
        if (pointLayout != null) {
            try {
                View childAt = pointLayout.getChildAt(position);
                childAt.setBackground(redDrawable);
                View childAt1 = pointLayout.getChildAt(preIndex);
                childAt1.setBackground(whileDrawable);
            } catch (NullPointerException e) {

            }
        }
        preIndex = position;
        stopFlow();
        startFlow();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onResume() {
        super.onResume();
//        startFlow();
    }

    @Override
    public void onPause() {
        super.onPause();
//        stopFlow();
    }

    @Override
    public void onClick(View v) {
        if (mList != null) {
            Flowbean flowbean = mList.get(mVp.getCurrentItem());
            if (flowbean == null) {
                return;
            }
            if (flowbean.getAdv_url()==null||"".equals(flowbean.getAdv_url())){
                return;
            }
            Intent intent = new Intent(getContext(), ShangJiaZhongXinActivity.class);
            intent.putExtra(ShangJiaZhongXinActivity.SHOP_ID, flowbean.getAdv_url());
            startActivity(intent);
        }
    }

}
