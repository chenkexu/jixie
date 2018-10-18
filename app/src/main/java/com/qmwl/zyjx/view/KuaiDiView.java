package com.qmwl.zyjx.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/8/18.
 * 物流的view
 */

public class KuaiDiView extends View {
    private static final int BigCircle_padding = 48;//px单位 //内圆38
    private static final int SmallCircle_padding = 20;////单位px 未选中状态的小圆
    private static final int LINE_WIDTH = 10;//线的宽度
    private static final String LINE_COLOR = "#999999";
    private static final String circle_color = "#25ae60";

    private int circle_radial = SmallCircle_padding;//当前圆的半径

    public static final int TOP = 0;
    public static final int CENTER = 1;
    public static final int BOTTOM = 2;

    private boolean isSelecter = false;//是否选中

    private int type = CENTER;//默认为中间的
    private Context cx;
    private int mWidth;
    private int mHeight;
    private Paint mPaint;//我的画笔

    public KuaiDiView(Context context) {
        super(context);
        initView(context);
    }

    public KuaiDiView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }


    public KuaiDiView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        this.cx = context;
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#25ae60"));
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(LINE_WIDTH);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(LINE_WIDTH);
        if (isSelecter) {
            mPaint.setColor(Color.parseColor(circle_color));
        } else {
            mPaint.setColor(Color.parseColor(LINE_COLOR));
        }
        switch (type) {
            case TOP:
                canvas.drawLine(mWidth / 2, mHeight / 2, mWidth / 2, mHeight, mPaint);
                break;
            case CENTER:
                canvas.drawLine(mWidth / 2, 0, mWidth / 2, mHeight, mPaint);
                break;
            case BOTTOM:
                canvas.drawLine(mWidth / 2, 0, mWidth / 2, mHeight / 2, mPaint);
                break;
        }

        canvas.drawCircle(mWidth / 2, mHeight / 2, circle_radial, mPaint);

    }

    public void setType(int type) {
        this.type = type;
        invalidate();
    }

    public void setSelecterStatue(boolean statue) {
        this.isSelecter = statue;
        if (isSelecter) {
//            circle_radial = BigCircle_padding;
            circle_radial = SmallCircle_padding;
        } else {
            circle_radial = SmallCircle_padding;
        }
        invalidate();
    }

}
