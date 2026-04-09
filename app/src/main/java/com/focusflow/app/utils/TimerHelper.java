package com.focusflow.app.utils;

import android.os.CountDownTimer;

public class TimerHelper {

    public interface TimerCallback {
        void onTick(long millisUntilFinished);
        void onFinish();
    }

    private CountDownTimer timer;

    public void startTimer(long millisInFuture, long countDownInterval, TimerCallback callback) {
        timer = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (callback != null) {
                    callback.onTick(millisUntilFinished);
                }
            }

            @Override
            public void onFinish() {
                if (callback != null) {
                    callback.onFinish();
                }
            }
        };
        timer.start();
    }

    public void cancelTimer() {
        if (timer != null) {
            timer.cancel();
        }
    }
}
