package com.qmwl.zyjx.utils;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;

import com.qmwl.zyjx.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/9/19.
 * 用来倒计时的帮助类,不推荐采取单例模式，所以需要每次去new
 */

public class GetCodeUtils {

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    button.setText(preTime + "s");
                    break;
                case 2:
                    timer.cancel();
                    button.setEnabled(true);
                    button.setText(endStatueText);
                    button.setBackgroundResource(R.drawable.xiaojiao_5dp_button_background);
                    button.setTextColor(Color.parseColor("#ffffff"));
                    break;
                case 3:
                    timer.cancel();
                    button.setText(firstStatueText);
                    button.setEnabled(true);
                    button.setBackgroundResource(R.drawable.xiaojiao_5dp_button_background);
                    button.setTextColor(Color.parseColor("#ffffff"));
                    break;
            }
        }
    };

    //倒计时时间(秒)
    private int countTime = 60;
    //当前的倒计时
    private int preTime = 0;

    //需要显示倒计时的button
    private Button button;
    //初始状态的文字，例如：(获取验证码)
    private String firstStatueText;
    //倒计时结束状态中的文字,例如：(重新获取验证码)
    private String endStatueText;

    private Timer timer;

    public GetCodeUtils(Button button, String firstStatueText, String endStatueText) {
        timer = new Timer(true);
        this.button = button;
        this.firstStatueText = firstStatueText;
        this.endStatueText = endStatueText;
    }

    private TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            if (preTime > 0) {
                preTime--;
                mHandler.sendEmptyMessage(1);
            } else {
                mHandler.sendEmptyMessage(2);
            }

        }
    };

    public void startTimerTask() {
        button.setBackgroundResource(R.drawable.gray_radio_bg);
        button.setTextColor(Color.parseColor("#000000"));
        button.setEnabled(false);
        preTime = countTime;
        timer.schedule(timerTask, 0, 1000);
    }

    public void stopTimerTask(){
        mHandler.sendEmptyMessage(3);
    }

    public void onDestroy() {
        if (timer != null) {
            timer.cancel();
        }
    }

}
