package com.qmwl.zyjx.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.ANRequest;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.qmwl.zyjx.R;
import com.qmwl.zyjx.activity.AddressActivity;
import com.qmwl.zyjx.activity.AddressBianJiActivity;
import com.qmwl.zyjx.base.MyApplication;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.AddressBean;
import com.qmwl.zyjx.utils.Contact;
import com.qmwl.zyjx.utils.JsonUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/8/1.
 * 收货地址的adapter
 */

public class AddressAdapter extends MyBaseAdapter<AddressBean> {
    private int type = AddressActivity.GOUWU_ADDRESS;

    public AddressAdapter(int type) {
        this.type = type;
    }

    @Override
    protected View getItemView(int position, View convertView, ViewGroup parent) {
//        if (convertView == null) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shouhuodizhi_layout_item, null);
//        }
        ViewHolder holder = getHolder(convertView);
        AddressBean item = getItem(position);
        Context context = parent.getContext();
        holder.name.setText(context.getString(R.string.shouhuoren) + ":" + item.getName());
        holder.call.setText(context.getString(R.string.lianxidianhua) + ":" + item.getMobile());
        holder.address.setText(context.getString(R.string.shouhuodizhi) + ":" + item.getAddress());
        holder.onClickListener.setData(item);
        holder.onCheckListener.setData(item);
        holder.checkBox.setChecked(item.isDefault());

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
        ViewHolder(View convertView) {
            address = (TextView) convertView.findViewById(R.id.address_item_address);
            call = (TextView) convertView.findViewById(R.id.address_item_call);
            name = (TextView) convertView.findViewById(R.id.address_item_name);
            bianji = convertView.findViewById(R.id.address_item_bianji);
            delete = convertView.findViewById(R.id.address_item_delete);
            checkBox = (CheckBox) convertView.findViewById(R.id.address_item_default_button);
            onClickListener = new MyOnClickListener();
            onCheckListener = new MyOnCheckListener();
            bianji.setOnClickListener(onClickListener);
            delete.setOnClickListener(onClickListener);
            checkBox.setOnClickListener(onClickListener);
//            checkBox.setOnCheckedChangeListener(onCheckListener);
            convertView.setTag(this);
        }

        TextView name, call, address;
        View bianji, delete;
        CheckBox checkBox;
        MyOnClickListener onClickListener;
        MyOnCheckListener onCheckListener;
    }

    class MyOnClickListener implements View.OnClickListener {
        AddressBean bean = null;

        void setData(AddressBean bean) {
            this.bean = bean;
        }

        @Override
        public void onClick(View v) {
            if (bean == null) {
                return;
            }
            Intent intent = null;
            switch (v.getId()) {
                case R.id.address_item_bianji:
                    intent = new Intent(AddressActivity.ADDRESS_ACTION);
                    intent.putExtra(AddressActivity.ADDRESS_TYPE, AddressActivity.BIANJI);
                    intent.putExtra(AddressBianJiActivity.TYPE, AddressBianJiActivity.BIANJI_ADDRESS);
                    intent.putExtra(AddressBianJiActivity.BIANJI_ADRESS_VALUE, bean);
                    intent.putExtra(AddressActivity.ADDRESS_ADDRESS_TYPE, type);
                    v.getContext().sendBroadcast(intent);

                    break;
                case R.id.address_item_delete:
                    delete(bean, v.getContext());
                    break;

                case R.id.address_item_default_button:
                    CheckBox button = (CheckBox) v;
                    button.setChecked(!button.isChecked());
                    postData(bean, v.getContext());

                    break;

            }

        }
    }

    class MyOnCheckListener implements CompoundButton.OnCheckedChangeListener {
        AddressBean bean = null;

        void setData(AddressBean bean) {
            this.bean = bean;
        }

        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (bean == null) {
                return;
            }
            bean.setDefault(isChecked);
            postData(bean, buttonView.getContext());
        }
    }

    private void delete(AddressBean bean, final Context cx) {
        if (bean == null) {
            return;
        }
        String url = "";
        switch (type) {
            case AddressActivity.GOUWU_ADDRESS:
                url = Contact.address_delete + "?id=" + bean.getId();
                break;
            case AddressActivity.YUNSHU_ADDRESS:
                url = Contact.yunshu_address_delete + "?id=" + bean.getId();
                break;
            case AddressActivity.YUNSHU_FAHUO_ADDRESS:
                url = Contact.yunshu_fahuo_address_delete + "?id=" + bean.getId();
                break;
            default:
                url = Contact.address_delete + "?id=" + bean.getId();
        }
        AndroidNetworking.get(url)
//                .addBodyParameter("id", bean.getId())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (JsonUtils.is100Success(response)) {
                                Intent intent = new Intent(AddressActivity.ADDRESS_ACTION);
                                intent.putExtra(AddressActivity.ADDRESS_TYPE, AddressActivity.SHANCHU);
                                cx.sendBroadcast(intent);
                            } else {
                                Toast.makeText(cx, "删除失败", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(cx, "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(cx, "删除失败", Toast.LENGTH_SHORT).show();
                    }
                });


    }


    void postData(final AddressBean bean, final Context cx) {
        if (bean == null) {
            return;
        }
        String url = "";
        switch (type) {
            case AddressActivity.GOUWU_ADDRESS:
                url = Contact.address_update_default;
                break;
            case AddressActivity.YUNSHU_ADDRESS:
//                url = Contact.yunshu_address_delete + "?id=" + bean.getId();
                break;
            case AddressActivity.YUNSHU_FAHUO_ADDRESS:
//                url = Contact.yunshu_fahuo_address_delete + "?id=" + bean.getId();
                break;
            default:
                url = Contact.address_update_default;
        }

        ANRequest.PostRequestBuilder post = null;
        post = AndroidNetworking.post(url);
        post.addBodyParameter("id", bean.getId())
                .addBodyParameter("uid", MyApplication.getIntance().userBean.getUid())
//                .addBodyParameter("consigner", bean.getName())
//                .addBodyParameter("mobile", bean.getMobile())
//                .addBodyParameter("phone", "")
//                .addBodyParameter("address", bean.getAddress())
//                .addBodyParameter("province", bean.getSheng())
//                .addBodyParameter("is_default", String.valueOf(bean.getDefaultCode()))
//                .addBodyParameter("city", bean.getShi())
//                .addBodyParameter("zip_code", "")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (JsonUtils.isSuccess(response)) {
                                Intent intent = new Intent(AddressActivity.ADDRESS_ACTION);
                                intent.putExtra(AddressActivity.ADDRESS_TYPE, AddressActivity.MORENDIZHI);
                                intent.putExtra(AddressActivity.ADDRESS_UPDATE_VALUE, bean.getId());
                                intent.putExtra(AddressActivity.ADDRESS_UPDATE_VALUE_BOOLEAN, bean.isDefault());
                                cx.sendBroadcast(intent);
                            } else {
                                Toast.makeText(cx, cx.getString(R.string.baocunshibaiqingchongshi), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(cx, cx.getString(R.string.baocunshibaiqingchongshi), Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        Toast.makeText(cx, cx.getString(R.string.tijiaoshibai_wangluo), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
