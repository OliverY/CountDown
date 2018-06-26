package com.yxj.countdown;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Author:  Yxj
 * Time:    2018/6/26 下午9:24
 * -----------------------------------------
 * Description:
 */
public class CountDown {

    final int MSG_STEP = 1;
    final int MSG_SUCCESS = 2;

    /*
    1.start正在进行中无法start
    2.start正常成功后不能再继续start
    3.进行中，点reset重置，可以重新start
    4.进行中，点stop停止，不可以重新start
    5.可多次点击restart
     */

    int time;
    int startNum;
    private Handler handler;
    private Timer timer;
    private boolean isRunning;

    public CountDown(int startNum,final Callback callback) {
        this.time = startNum;
        this.startNum = startNum;
        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_STEP:
                        callback.onStep(msg.arg1);
                        break;
                    case MSG_SUCCESS:
                        callback.onSuccess();
//                        isRunning = false;
                        break;
                }
            }
        };
    }

    public void start() {
        if (isRunning) {
            return;
        }
        isRunning = true;

        time = startNum;
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Message msg = Message.obtain();
                msg.what = MSG_STEP;
                msg.arg1 = time;
                handler.sendMessage(msg);

                if (time == 0) {
                    handler.sendEmptyMessage(MSG_SUCCESS);
                    if (timer != null) {
                        timer.cancel();
                        timer = null;
                    }
                    return;
                }
                time--;
            }
        }, 200, 1000);

    }

    /**
     * 重置，可以再start
     */
    public void reset() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        isRunning = false;
    }

    /**
     * 停止，不能直接start，必须reset重置后才能restart
     */
    public void stop() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    /**
     * 再次开始
     */
    public void restart() {
        reset();
        start();
    }

    public interface Callback {
        void onSuccess();

        void onStep(int count);
    }

}
