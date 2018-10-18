package com.qmwl.zyjx.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.qmwl.zyjx.R;


/**
 * @author 郭辉
 * @version 创建时间:2015年10月20日上午9:59:53 类说明
 */
public class MyDialog extends AlertDialog implements
        View.OnClickListener {
    /**
     * 最好给的是Activity的context
     */
    private Context cx;

    private TextView titleTv;

    private TextView leftTv;

    private TextView rightTv;

    private FrameLayout contentContainer;
    private View contentView;
    private String title;
    private String leftButtonText;
    private String rightButtonText;
    private String content;
    private onClickButton mListener;

    public interface onClickButton {
        public void rightClick();
    }

    /**
     * @param context 上下文
     */
    public MyDialog(Context context, View contentView) {
        super(context);
        this.cx = context;
        this.contentView = contentView;
    }

    public MyDialog(Context context, View contentView, onClickButton listener) {
        super(context);
        this.cx = context;
        this.contentView = contentView;
        this.mListener = listener;
    }


    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = LayoutInflater.from(cx).inflate(R.layout.mydialog_layout,
                null);
        contentContainer = (FrameLayout) view.findViewById(R.id.mydialog_content_container);
        titleTv = (TextView) view.findViewById(R.id.mydialog_title_tv);
        leftTv = (TextView) view.findViewById(R.id.mydialog_left_tv);
        rightTv = (TextView) view.findViewById(R.id.mydialog_right_tv);
        leftTv.setOnClickListener(this);
        rightTv.setOnClickListener(this);
        View dialogContainer = view.findViewById(R.id.my_dialog_container);
        LayoutParams params = dialogContainer.getLayoutParams();
        int wid = getWinWid(cx) / 4 * 3;
        params.width = wid;
        dialogContainer.setLayoutParams(params);
//        titleTv.setText(title);
        rightTv.setOnClickListener(this);
        if (rightButtonText != null && !rightButtonText.isEmpty()) {
            rightTv.setText(rightButtonText);
        }
        contentContainer.addView(contentView);
        setContentView(view);
//        setCancelable(false);
    }

    /**
     * 获取屏幕宽度
     *
     * @param cx
     * @return
     */
    private int getWinWid(Context cx) {
        DisplayMetrics ds = new DisplayMetrics();
        Activity activity = (Activity) cx;
        activity.getWindowManager().getDefaultDisplay().getMetrics(ds);
        return ds.widthPixels;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.mydialog_left_tv:

                break;
            case R.id.mydialog_right_tv:
                if (mListener == null) {
                    if (isShowing()) {
                        dismiss();
                    }
                } else {
                    mListener.rightClick();
                }
                break;

            default:
                break;
        }

    }

}
