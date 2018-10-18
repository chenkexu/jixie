package com.qmwl.zyjx.adapter;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.ShoppingBean;
import com.qmwl.zyjx.fragment.ThreadFragment;
import com.qmwl.zyjx.utils.Contact;

import org.json.JSONObject;

/**
 * Created by Administrator on 2017/7/28.
 * 购物车的子adapter(购物车两层adapter)
 */

public class ShoppingAdapter extends MyBaseAdapter<ShoppingBean> {

    @Override
    protected View getItemView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_layout_item, null);
        ViewHolder holder = getHolder(convertView);
        ShoppingBean item = getItem(position);
        holder.name.setText(item.getName());
        holder.jine.setText("￥" + item.getPrice());
        holder.numTv.setText(String.valueOf(item.getNumber()));
        holder.type.setText(getResouseString(parent.getContext(), R.string.shangpinleixing) + item.getModel());
        holder.checkBox.setChecked(item.isSelecter());
        holder.checkListener.setData(item);
        holder.viewOnClick.setData(item, holder);
        holder.textWatcher.setData(item, parent);
        holder.yunfei.setText(getResouseString(parent,R.string.yunfei)+":￥"+item.getYunfei());
        openImage(parent, item.getIv_pic(), holder.iv);
        return convertView;
    }

    private ViewHolder getHolder(View convertView) {
        ViewHolder holder = (ViewHolder) convertView.getTag();
        if (holder == null) {
            holder = new ViewHolder(convertView);
        }
        return holder;
    }


    class ViewHolder {
        CheckBox checkBox;
        ImageView iv;
        TextView name, type, jine,yunfei;
        EditText numTv;
        View delete, jian, jia;
        ShoppingCheckListener checkListener;
        ViewOnclick viewOnClick;
        MyTextWatcher textWatcher;

        ViewHolder(View convertView) {
            checkBox = (CheckBox) convertView.findViewById(R.id.shopping_item_checkbox);
            iv = (ImageView) convertView.findViewById(R.id.shopping_item_iv);
            name = (TextView) convertView.findViewById(R.id.shopping_item_name);
            type = (TextView) convertView.findViewById(R.id.shopping_item_type);
            jine = (TextView) convertView.findViewById(R.id.shopping_item_jine);
            yunfei = (TextView) convertView.findViewById(R.id.shopping_item_yunfei);
            numTv = (EditText) convertView.findViewById(R.id.shopping_item_number);
            delete = convertView.findViewById(R.id.shopping_item_delete);
            jian = convertView.findViewById(R.id.shopping_item_jian);
            jia = convertView.findViewById(R.id.shopping_item_jia);
            checkListener = new ShoppingCheckListener();
            viewOnClick = new ViewOnclick();
            textWatcher = new MyTextWatcher();
            jia.setOnClickListener(viewOnClick);
            jian.setOnClickListener(viewOnClick);
            delete.setOnClickListener(viewOnClick);
            checkBox.setOnCheckedChangeListener(checkListener);
            numTv.addTextChangedListener(textWatcher);
            convertView.setTag(this);
        }
    }

    class MyTextWatcher implements TextWatcher {
        ShoppingBean bean = null;
        ViewGroup parent = null;

        void setData(ShoppingBean bean, ViewGroup parent) {
            this.bean = bean;
            this.parent = parent;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (bean == null || parent == null) {
                return;
            }
            String s1 = s.toString();
            if (TextUtils.isEmpty(s1) || "".equals(s1)) {
                return;
            }
            try {
                bean.setNumber(Integer.parseInt(s1));
            } catch (Exception e) {

            }
            updatePrice(parent);
        }
    }

    class ShoppingCheckListener implements CompoundButton.OnCheckedChangeListener {

        ShoppingBean bean = null;

        void setData(ShoppingBean bean) {
            this.bean = bean;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (bean == null) {
                return;
            }
            bean.setSelecter(isChecked);
            updatePrice(buttonView);
        }
    }

    class ViewOnclick implements View.OnClickListener {

        ViewHolder holder = null;
        ShoppingBean bean = null;

        void setData(ShoppingBean bean, ViewHolder holder) {
            this.holder = holder;
            this.bean = bean;
        }

        @Override
        public void onClick(View v) {
            if (bean == null || holder == null) {
                return;
            }
            switch (v.getId()) {
                case R.id.shopping_item_jian:

                    String s = holder.numTv.getText().toString();
                    if (s == null || "".equals(s)) {
                        s = "1";
                    }
                    int i = Integer.parseInt(s);
                    i--;
                    if (i <= 1) {
                        i = 1;
                    }
                    bean.setNumber(i);
                    holder.numTv.setText(String.valueOf(i));
                    //给服务器更新数量
                    updateShopNumBer(bean.getCart_id(), bean.getNumber());
                    updatePrice(v);
                    break;
                case R.id.shopping_item_jia:
                    String jias = holder.numTv.getText().toString();
                    if (jias == null || "".equals(jias)) {
                        jias = "1";
                    }
                    int number = Integer.parseInt(jias);
                    number++;
                    bean.setNumber(number);
                    holder.numTv.setText(String.valueOf(number));
                    //给服务器更新数量
                    updateShopNumBer(bean.getCart_id(), bean.getNumber());
//                    updateShopNumBer()
                    //更新价格
                    updatePrice(v);
                    break;
                case R.id.shopping_item_delete:
                    if (bean == null) {
                        return;
                    }
                    Intent intent = new Intent(ThreadFragment.REMOVE_SHOPS_LIST);
                    intent.putExtra(ThreadFragment.SHOP_TYPE, ThreadFragment.DELETE_SHOP);
                    intent.putExtra(ThreadFragment.SHOP_ID, bean.getShop_id());
                    holder.delete.getContext().sendBroadcast(intent);
                    deleteShop(bean);
                    break;
            }
        }
    }

    //删除商品
    private void deleteShop(ShoppingBean bean) {
        AndroidNetworking.post(Contact.deleteGouWuCheNum)
                .addBodyParameter("cart_id", bean.getCart_id())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });

    }

    //更新价格
    private void updatePrice(View v) {
        Intent intent = new Intent(ThreadFragment.REMOVE_SHOPS_LIST);
        intent.putExtra(ThreadFragment.SHOP_TYPE, ThreadFragment.UPDATE_PRICE);
        v.getContext().sendBroadcast(intent);
    }

    private void updateShopNumBer(String cart_id, int number) {
        if (!MyApplication.getIntance().isLogin()) {
            return;
        }
        AndroidNetworking.get(Contact.updataGouWuCheNum + "?uid=" + MyApplication.getIntance().userBean.getUid() + "&cart_id=" + cart_id + "&num=" + number)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                    }

                    @Override
                    public void onError(ANError anError) {
                    }
                });
    }

}
