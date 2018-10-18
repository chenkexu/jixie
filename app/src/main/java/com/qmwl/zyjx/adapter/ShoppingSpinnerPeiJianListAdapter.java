package com.qmwl.zyjx.adapter;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.qmwl.zyjx.R;
import com.qmwl.zyjx.activity.ShoppingPeiJianThreadActivity;
import com.qmwl.zyjx.activity.ShoppingThreadActivity;
import com.qmwl.zyjx.base.MyBaseAdapter;
import com.qmwl.zyjx.bean.BlackBean;
import com.qmwl.zyjx.utils.CharacterParser;
import com.qmwl.zyjx.utils.PinyinComparator;

import java.util.Collections;
import java.util.List;


public class ShoppingSpinnerPeiJianListAdapter extends MyBaseAdapter<BlackBean> {
    private CharacterParser characterParser;
    private PinyinComparator comparator;
    private boolean isDiQu = false;
    private Context cx;
    private String quanbudiqu = "";
    private String quanbupinpai = "";
    private ShoppingPeiJianThreadActivity activity;


    public ShoppingSpinnerPeiJianListAdapter(Context cx) {
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        comparator = new PinyinComparator();
        this.cx = cx;
        quanbupinpai = getResouseString(cx, R.string.quanbupinpai);
        quanbudiqu = getResouseString(cx, R.string.quanbudiqu);
    }

    public ShoppingSpinnerPeiJianListAdapter(Context cx, ShoppingPeiJianThreadActivity activity) {
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        comparator = new PinyinComparator();
        this.cx = cx;
        quanbupinpai = getResouseString(cx, R.string.quanbupinpai);
        quanbudiqu = getResouseString(cx, R.string.quanbudiqu);
        this.activity = activity;
    }

    public ShoppingSpinnerPeiJianListAdapter(boolean isDiQu, ShoppingPeiJianThreadActivity activity) {
        //实例化汉字转拼音类
        characterParser = CharacterParser.getInstance();
        comparator = new PinyinComparator();
        this.isDiQu = isDiQu;
        this.activity = activity;
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
                BlackBean bean = new BlackBean();
                bean.setSortLetters("#");
                bean.setId("");
                if (isDiQu) {
//                    bean.setName("全部地区");
//                    bean.setName(quanbudiqu);
                } else {
                    bean.setName(quanbupinpai);
                    mList.add(0, bean);
                }
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shopping_spinner_pinpaiordiqu_item, null);
        CheckBox duihao = (CheckBox) view.findViewById(R.id.black_layout_item_duihao);
        TextView page = (TextView) view.findViewById(R.id.black_layout_item_page);
        TextView nameTv = (TextView) view.findViewById(R.id.black_layout_item_name);

        BlackBean item = getItem(position);
        String name1 = item.getName();
        nameTv.setText(name1);
        if (isDiQu) {
            if (activity != null) {
                String qiQuCode = activity.getQiQuCode();
                if (!"".equals(qiQuCode) && !"-1".equals(qiQuCode)) {
                    if (qiQuCode.equals(item.getId())) {
                        duihao.setChecked(true);
                    } else {
                        duihao.setChecked(false);
                    }
                }
            }
        } else {
            duihao.setChecked(item.isSelecter());
        }


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
