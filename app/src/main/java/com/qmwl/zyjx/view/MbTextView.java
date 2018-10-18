package com.qmwl.zyjx.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/7/19.
 */

public class MbTextView extends TextView {
    public MbTextView(Context context) {
        super(context);
    }

    public MbTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    TextPaint strokePaint = null;

    @Override
    protected void onDraw(Canvas canvas) {
        // lazy load
        if (strokePaint == null) {
            strokePaint = new TextPaint();
        }
        // 复制原来TextViewg画笔中的一些参数
        TextPaint paint = getPaint();
        strokePaint.setTextSize(paint.getTextSize());
        strokePaint.setTypeface(paint.getTypeface());
        strokePaint.setFlags(paint.getFlags());
        strokePaint.setAlpha(paint.getAlpha());

        // 自定义描边效果
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(Color.parseColor("#5C5C5C"));
        strokePaint.setStrokeWidth(3);

        String text = getText().toString();
        //在文本底层画出带描边的文本
        canvas.drawText(text, (getWidth() - strokePaint.measureText(text)) / 2,
                getBaseline(), strokePaint);
        super.onDraw(canvas);
    }
}
