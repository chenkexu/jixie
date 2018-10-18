package com.qmwl.zyjx.adapter;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.BlackBean;
import com.qmwl.zyjx.utils.CharacterParser;
import com.qmwl.zyjx.utils.PinyinComparator;

import java.util.Collections;
import java.util.List;


public class BlackListAdapter extends MyBaseAdapter<BlackBean> {
    private CharacterParser characterParser;
    private PinyinComparator comparator;

    public BlackListAdapter() {
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        comparator = new PinyinComparator();
    }

    private Handler mHandler = new Handler();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (mList != null) {
                for (int i = 0; i < mList.size(); i++) {
                    BlackBean bean = mList.get(i);
                    String name = bean.getName();
                    //汉字转换成拼音
                    String pinyin = characterParser.getSelling(name);
                    String sortString = pinyin.substring(0, 1).toUpperCase();
                    // 正则表达式，判断首字母是否是英文字母
                    if (sortString.matches("[A-Z]")) {
                        bean.setSortLetters(sortString.toUpperCase());
                    } else {
                        bean.setSortLetters("#");
                    }
                }
                Collections.sort(mList, comparator);
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });

            }
        }
    };


    @Override
    public void setData(List<BlackBean> mList) {
        this.mList = mList;
        new Thread(runnable).start();

    }

    @Override
    protected View getItemView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.blacklist_layout_item, null);
        TextView name = (TextView) view.findViewById(R.id.black_layout_item_name);
        TextView page = (TextView) view.findViewById(R.id.black_layout_item_page);

        BlackBean item = getItem(position);
        String name1 = item.getName();
        name.setText(name1);


        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            page.setVisibility(View.VISIBLE);
            page.setText(item.getSortLetters());
        } else {
            page.setVisibility(View.GONE);
        }
        return view;
    }

    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return mList.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = mList.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

}
