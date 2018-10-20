package com.qmwl.zyjx.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qmwl.zyjx.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangRui on 2018/4/20.
 * https://blog.csdn.net/qq1271396448/article/details/80539042
 * 申请退款dialog
 */
public class ReturnPayAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    private Context mContext;

    private Map<Integer, Boolean> map = new HashMap<>();
    private int checkedPosition = -1;
    private boolean onBind;


    public ReturnPayAdapter(@Nullable List<String> data, Context mContext) {
        super(R.layout.item_return_pay, data);
        this.mContext = mContext;
    }


    @Override
    protected void convert(final BaseViewHolder helper, final String item) {
        helper.setText(R.id.content, item);

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((CheckBox) helper.getView(R.id.cb_select)).setChecked(true);
            }
        });
        ((CheckBox) helper.getView(R.id.cb_select)).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked == true) {
                    map.clear();
                    map.put(helper.getAdapterPosition(), true);
                    checkedPosition = helper.getAdapterPosition();
                } else {
                    map.remove(helper.getAdapterPosition());
                    if (map.size() == 0) {
                        checkedPosition = -1; //-1 代表一个都未选择
                    }
                }
                if (!onBind) {
                    notifyDataSetChanged();
                }
            }
        });
        onBind = true;
        if (map != null && map.containsKey(helper.getAdapterPosition())) {
            ((CheckBox) helper.getView(R.id.cb_select)).setChecked(true);
        } else {
            ((CheckBox) helper.getView(R.id.cb_select)).setChecked(false);
        }
        onBind = false;
    }


    //得到当前选中的位置
    public int getCheckedPosition() {
        return checkedPosition;
    }
}

