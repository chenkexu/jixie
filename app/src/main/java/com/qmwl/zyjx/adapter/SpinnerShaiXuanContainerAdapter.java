package com.qmwl.zyjx.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.ShaiXuanItemBean;

/**
 * Created by Administrator on 2017/8/4.
 */

public class SpinnerShaiXuanContainerAdapter extends MyBaseAdapter<ShaiXuanItemBean> {
    private String value = "";

    public String getValue() {
        return value;
    }

    public void resetValue() {
        this.value = "";
    }

    @Override
    protected View getItemView(int position, View convertView, ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.spinner_shaixuan_container_item, parent, false);
        CheckBox fuxuan = (CheckBox) inflate.findViewById(R.id.spinner_shaixuan_container_gridview_item_button);
        ShaiXuanItemBean item = getItem(position);
        fuxuan.setText(item.getName());
        fuxuan.setChecked(item.isSelecter());
        MyClickView clickView = new MyClickView();
        fuxuan.setOnClickListener(clickView);
        clickView.setData(item);
//        unDisplayViewSize(parent);
//        unDisplayViewSize(fuxuan);
//        int[] ints = unDisplayViewSize(fuxuan);
//        int lineCount = fuxuan.getLayout().getLineCount();
//
//        Log.i("TAG", "获取的text行数:" + lineCount + "    " + item.getName());
//        if (item.getName().length()>6){
//
//            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ints[1] * 2);
//            fuxuan.setLayoutParams(layoutParams);
//        }else {
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ints[1] * 2);
//        fuxuan.setLayoutParams(layoutParams);
//        unDisplayViewSize(fuxuan);
//        }
//        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,lineHeight);
        return inflate;
    }

    public int[] unDisplayViewSize(View view) {
        int size[] = new int[2];
        int width = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.EXACTLY);
        int height = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(width, height);
        size[0] = view.getMeasuredWidth();
        size[1] = view.getMeasuredHeight();
        return size;
    }


    class MyClickView implements View.OnClickListener {
        ShaiXuanItemBean item;

        void setData(ShaiXuanItemBean item) {
            this.item = item;
        }

        @Override
        public void onClick(View v) {
            if (item == null || mList == null) {
                return;
            }
            for (ShaiXuanItemBean bean : mList) {
                if (item == bean) {
                    continue;
                }
                bean.setSelecter(false);
            }
            item.setSelecter(!item.isSelecter());
            if (item.isSelecter()) {
                value = item.getName();
            } else {
                value = "";
            }
            notifyDataSetChanged();

        }
    }
}
