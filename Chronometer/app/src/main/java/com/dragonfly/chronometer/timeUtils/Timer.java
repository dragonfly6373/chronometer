package com.dragonfly.chronometer.timeUtils;

import android.os.Handler;
import android.os.SystemClock;

public abstract class Timer implements TimeEventHandle {

    Handler handler;
    private long timeout;
    private long startTime;
    private long step;

    public Timer(long msecond) {
        this.timeout = msecond;
        this.step = TimeRunner.SECOND_VIEW;
        this.handler = new Handler();
    }

    public Timer(int hour, int minute, int second, int msecond) {
        this.timeout = 0;
        this.timeout += msecond;
        this.timeout += second * 1000;
        this.timeout += minute * 60 * 1000;
        this.timeout += hour * 60 * 60 * 1000;
        this.step = TimeRunner.SECOND_VIEW;
        this.handler = new Handler();
    }

    public abstract void onTimeChange();

    public abstract void onTimeOut();

    public void setStep(long step) {
        this.step = step;
    }

    @Override
    public Handler getHandler() {
        return handler;
    }

    public void start() {
        startTime = SystemClock.uptimeMillis();
        handler.postDelayed(new TimeRunner(this, step), step);
    }

}
