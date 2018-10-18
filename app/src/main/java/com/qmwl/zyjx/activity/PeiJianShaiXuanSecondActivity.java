package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.PeiJianShaiXuanBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/3/30/030.
 * 配件筛选二级页面
 */

public class PeiJianShaiXuanSecondActivity extends BaseActivity {

    private ListView rightListView;
    private ListView leftListView;
    private LeftAdapter leftAdapter;
    private RightAdapter rightAdapter;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.activity_layout_peijian_shaixuan);
    }

    @Override
    protected void initView() {
        setTitleContent(R.string.peijian);
        leftListView = (ListView) findViewById(R.id.id_peijian_shaixuan_left_listview);
        rightListView = (ListView) findViewById(R.id.id_peijian_shaixuan_right_listview);
        leftAdapter = new LeftAdapter();
        leftListView.setAdapter(leftAdapter);
        rightAdapter = new RightAdapter();
        rightListView.setAdapter(rightAdapter);
        leftListView.setOnItemClickListener(new LeftItemListener());
        addJia();
    }

    private void addJia() {
        List<PeiJianShaiXuanBean> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            PeiJianShaiXuanBean bean = new PeiJianShaiXuanBean("张三" + i, i + "");
            list.add(bean);
        }
        leftAdapter.setData(list);
    }

    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {

    }

    @Override
    protected void onMyClick(View v) {

    }

    class LeftItemListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            for (int i = 0; i < leftAdapter.getCount(); i++) {
                PeiJianShaiXuanBean item = leftAdapter.getItem(i);
                item.isSelecter = false;
            }
            PeiJianShaiXuanBean item = leftAdapter.getItem(position);
            item.isSelecter = true;
            leftAdapter.notifyDataSetChanged();
        }
    }

    /**
     *
     */
    class RightItemListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

        }
    }

    /**
     *
     */
    class RightRightItemListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intent = new Intent(PeiJianShaiXuanSecondActivity.this, ShoppingPeiJianThreadActivity.class);
            startActivity(intent);
        }
    }


    class LeftAdapter extends MyBaseAdapter<PeiJianShaiXuanBean> {

        @Override
        protected View getItemView(int position, View convertView, ViewGroup parent) {
            LeftHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_peijian_shaixuan_left, parent, false);
                holder = new LeftHolder(convertView);
            } else {
                holder = (LeftHolder) convertView.getTag();
            }
            PeiJianShaiXuanBean item = getItem(position);
            if (item.isSelecter) {
                holder.yuandian.setVisibility(View.VISIBLE);
                holder.shuxian.setVisibility(View.GONE);
                holder.tv.setBackgroundColor(holder.pressColor);
            } else {
                holder.yuandian.setVisibility(View.GONE);
                holder.shuxian.setVisibility(View.VISIBLE);
                holder.tv.setBackgroundColor(holder.noPressColor);

            }
            return convertView;
        }
    }

    class RightAdapter extends MyBaseAdapter<PeiJianShaiXuanBean> {
        @Override
        public int getCount() {
            return 10;
        }

        @Override
        protected View getItemView(int position, View convertView, ViewGroup parent) {
            RightHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_peijianshaixuan_scend, parent, false);
                holder = new RightHolder(convertView);
            } else {
                holder = (RightHolder) convertView.getTag();
            }
//            PeiJianShaiXuanBean item = getItem(position);


            return convertView;
        }
    }


    class RightScendAdapter extends MyBaseAdapter<PeiJianShaiXuanBean> {
        @Override
        public int getCount() {
            return 10;
        }

        @Override
        protected View getItemView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_peijianshaixuan_scend_scend, null);
            }
            return convertView;
        }
    }


    class LeftHolder {
        LeftHolder(View convertView) {
            tv = (TextView) convertView.findViewById(R.id.id_item_peijian_shaixuan_left_tv);
            yuandian = convertView.findViewById(R.id.id_item_peijian_shaixuan_left_yuandian);
            shuxian = convertView.findViewById(R.id.id_item_peijian_shaixuan_right_shuxian);
            pressColor = ContextCompat.getColor(convertView.getContext(), R.color.cf7f7f7);
            noPressColor = ContextCompat.getColor(convertView.getContext(), R.color.white);
            convertView.setTag(this);
        }

        int pressColor;
        int noPressColor;
        TextView tv;
        View yuandian, shuxian;
    }

    class RightHolder {
        RadioButton button;
        ListView listView;
        RightScendAdapter scendAdapter;
        RightRightItemListener itemListener;

        RightHolder(View convertView) {
            button = (RadioButton) convertView.findViewById(R.id.item_id_peijianshaixuan_scend_button);
            listView = (ListView) convertView.findViewById(R.id.item_id_peijianshaixuan_scend_listview);
            scendAdapter = new RightScendAdapter();
            listView.setAdapter(scendAdapter);
            itemListener = new RightRightItemListener();
            listView.setOnItemClickListener(itemListener);
            convertView.setTag(this);
        }


    }
}
