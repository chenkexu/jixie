package com.qmwl.zyjx.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.BaseActivity;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.PeiJianShaiXuanBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;

import org.json.JSONObject;

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
    private String id;

    @Override
    protected void setLayout() {
        setContentLayout(R.layout.activity_layout_peijian_shaixuan);
    }

    @Override
    protected void initView() {
        id = getIntent().getStringExtra("id");
        if (TextUtils.isEmpty(id)) {
            finish();
            return;
        }
        setTitleContent(R.string.peijian);
        leftListView = (ListView) findViewById(R.id.id_peijian_shaixuan_left_listview);
        rightListView = (ListView) findViewById(R.id.id_peijian_shaixuan_right_listview);
        leftAdapter = new LeftAdapter();
        leftListView.setAdapter(leftAdapter);
        rightAdapter = new RightAdapter();
        rightListView.setAdapter(rightAdapter);
        leftListView.setOnItemClickListener(new LeftItemListener());
    }


    @Override
    protected void onListener() {

    }

    @Override
    protected void getInterNetData() {
        getData(id);
    }

    @Override
    protected void onMyClick(View v) {

    }

    private void getData(String id) {
        AndroidNetworking.post(Contact.peijianshaixuanyi)
                .addBodyParameter("category_id", id)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("TAG","一级:"+response.toString());
                List<PeiJianShaiXuanBean> peiJianShaiXuanBeans = JsonUtils.parsePeiJianShaiXuan(response);
                leftAdapter.setData(peiJianShaiXuanBeans);
                if (leftAdapter.getCount() > 0) {
                    PeiJianShaiXuanBean item = leftAdapter.getItem(0);
                    moniLeftOnclick(item);
                }
            }

            @Override
            public void onError(ANError anError) {
                Log.i("TAG", "一级:" + anError.getErrorCode());
            }
        });
    }

    private void getTowData(String id) {
        String url = Contact.peijianshaixuaner;

        AndroidNetworking.post(url)
                .addBodyParameter("category_id", id)
                .build().getAsJSONObject(new JSONObjectRequestListener() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("TAG", "二:" + response.toString());
                List<PeiJianShaiXuanBean> peiJianShaiXuanBeans = JsonUtils.parsePeiJianShaiXuanScend(response);
                rightAdapter.setData(peiJianShaiXuanBeans);
            }

            @Override
            public void onError(ANError anError) {
                Log.i("TAG", "二:" + anError.getErrorCode());
            }
        });
    }

    class LeftItemListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            for (int i = 0; i < leftAdapter.getCount(); i++) {
                PeiJianShaiXuanBean item = leftAdapter.getItem(i);
                item.isSelecter = false;
            }
            PeiJianShaiXuanBean item = leftAdapter.getItem(position);
            moniLeftOnclick(item);
        }
    }

    private void moniLeftOnclick(PeiJianShaiXuanBean item) {
        item.isSelecter = true;
        leftAdapter.notifyDataSetChanged();
        getTowData(item.id);
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
            PeiJianShaiXuanBean item = (PeiJianShaiXuanBean) view.getTag();
            Intent intent = new Intent(PeiJianShaiXuanSecondActivity.this, ShoppingPeiJianThreadActivity.class);
            intent.putExtra(ShoppingSecondActivity.Category_name, item.name);
            intent.putExtra(ShoppingSecondActivity.Category_id, item.id);
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
            holder.tv.setText(item.name);
            return convertView;
        }
    }

    class RightAdapter extends MyBaseAdapter<PeiJianShaiXuanBean> {

        @Override
        protected View getItemView(int position, View convertView, ViewGroup parent) {
            RightHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_peijianshaixuan_scend, parent, false);
                holder = new RightHolder(convertView);
            } else {
                holder = (RightHolder) convertView.getTag();
            }
            PeiJianShaiXuanBean item = getItem(position);
            holder.button.setText(item.name);
            holder.scendAdapter.setData(item.childList);


            return convertView;
        }
    }


    class RightScendAdapter extends MyBaseAdapter<PeiJianShaiXuanBean> {

        @Override
        protected View getItemView(int position, View convertView, ViewGroup parent) {
//            RightHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_peijianshaixuan_scend_scend, null);
//                holder = new RightHolder(convertView);
            } else {
//                holder = (RightHolder) convertView.getTag();
            }
            PeiJianShaiXuanBean item = getItem(position);
//            holder.button.setText(item.name);
            TextView textView = (TextView) convertView.findViewById(R.id.item_id_peijianshaixuan_scend_scend_tv);
            textView.setText(item.name);
            convertView.setTag(item);

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
        ImageView image;
        ListView listView;
        RightScendAdapter scendAdapter;
        RightRightItemListener itemListener;
        ScendButtonOnclick buttonOnclick;

        RightHolder(View convertView) {
            button = (RadioButton) convertView.findViewById(R.id.item_id_peijianshaixuan_scend_button);
            image = (ImageView) convertView.findViewById(R.id.item_id_peijianshaixuan_scend_image);
            listView = (ListView) convertView.findViewById(R.id.item_id_peijianshaixuan_scend_listview);
            scendAdapter = new RightScendAdapter();
            listView.setAdapter(scendAdapter);
            itemListener = new RightRightItemListener();
            listView.setOnItemClickListener(itemListener);
            buttonOnclick = new ScendButtonOnclick(this);
            button.setOnClickListener(buttonOnclick);
            convertView.setTag(this);
        }
    }

    class ScendButtonOnclick implements View.OnClickListener {
        RightHolder holder;
        int nopressColor = 0;
        int pressColor = 0;

        ScendButtonOnclick(RightHolder holder) {
            this.holder = holder;
            nopressColor = ContextCompat.getColor(holder.button.getContext(), R.color.c4c4c4c);
            pressColor = ContextCompat.getColor(holder.button.getContext(), R.color.window_system_color);
        }

        @Override
        public void onClick(View view) {
            if (holder == null) {
                return;
            }
            if (holder.listView.getVisibility() == View.VISIBLE) {
                holder.listView.setVisibility(View.GONE);
                holder.button.setTextColor(nopressColor);
                holder.image.setImageResource(R.mipmap.icon_peijian_shaixuan_xia);
            } else {
                holder.listView.setVisibility(View.VISIBLE);
                holder.button.setTextColor(pressColor);
                holder.image.setImageResource(R.mipmap.icon_peijian_shaixuan_shang);
            }
        }
    }
}
