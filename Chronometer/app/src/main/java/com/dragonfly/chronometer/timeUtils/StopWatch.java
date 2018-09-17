package com.dragonfly.chronometer.timeUtils;

import android.os.Handler;
import android.os.SystemClock;

import com.dragonfly.chronometer.Home;

public abstract class StopWatch implements TimeEventHandle {

    Handler handler;
    long startTime, counter, step;
    TimeRunner runner;
    public StopWatch(long step) {
        this.step = step;
        handler = new Handler();
        runner = new TimeRunner(this, step);
    }

    public abstract void onTimeChange();

    @Override
    public Handler getHandler() {
        return handler;
    }

    public void start() {
        counter = 0;
        startTime = SystemClock.uptimeMillis();
        onTimeChange();
        handler.postDelayed(runner, step);
    }

    public void pause() {
        handler.removeCallbacks(runner);
        counter += SystemClock.uptimeMillis() - startTime;
    }

    public void resume() {
        startTime = SystemClock.uptimeMillis();
        onTimeChange();
        handler.postDelayed(runner, step);
    }

    public void reset() {
        counter = 0;
        onTimeChange();
    }

    public long getTimeDelta() {
        return counter + SystemClock.uptimeMillis() - startTime;
    }
}
