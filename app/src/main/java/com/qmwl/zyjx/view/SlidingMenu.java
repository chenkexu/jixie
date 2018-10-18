package com.qmwl.zyjx.view;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2017/7/27.
 */

public class SlidingMenu extends FrameLayout {
    public SlidingMenu(@NonNull Context context) {
        super(context);
    }

    public SlidingMenu(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SlidingMenu(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int mWidth = 0;
    private int mHeight = 0;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    View leftView;
    View rightView;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.i("TAG", "onLayout");
        leftView = getChildAt(0);
        rightView = getChildAt(1);

        leftView.layout((int) (0 + offSet), 0, (int) (mWidth + offSet), mHeight);
        rightView.layout((int) (mWidth + offSet), 0, (int) (mWidth + rightView.getWidth() + offSet), mHeight);
    }

    private float preX;
    private float preY;
    private float minValue_X = 100f;//X轴移动300px判定为横向滑动
    private float minValue_Y = 500f;//Y轴移动500px判定为纵向滑动

    private float offSet;//x轴的偏移量


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {

            case MotionEvent.ACTION_DOWN:
                preX = ev.getX();
                preY = ev.getY();
                Log.i("TAG", "preX:  " + preX);

                break;
            case MotionEvent.ACTION_MOVE:
                offSet = ev.getX() - preY;
//                Log.i("TAG", "move:  " + offSet);
                int x = (int) (ev.getX() - preX + leftView.getX());
                int y = (int) ev.getY();
                int r = x + 50;
                int b = y + 50;
                leftView.layout(x, 0, x + mWidth, mHeight);
//                if (Math.abs(offSet) > minValue_X) {
//                    //右滑
////                    int left = (int) (leftView.getX() + offSet);
//                    int v = (int) (ev.getX() - preX);
//                    Log.i("TAG","offSet:"+v);
//                    leftView.layout((int) (leftView.getX()+offSet), 0, (int) (leftView.getX()+offSet+mWidth), mHeight);
//
////                    return true;
//                }
//                else if (offSet <= -minValue_X) {
//                    //左滑
//                    postInvalidate();
//                    return true;
//                }
                break;
            case MotionEvent.ACTION_UP:
                Log.i("TAG", "ACTION_UP:  " + offSet);
                preX = 0;
                preY = 0;
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return super.onInterceptTouchEvent(ev);
    }
}
