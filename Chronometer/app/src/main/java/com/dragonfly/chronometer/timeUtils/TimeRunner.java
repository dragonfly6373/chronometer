package com.dragonfly.chronometer.timeUtils;

public class TimeRunner implements Runnable {
    public static final long SECOND_VIEW = 999;
    public static final long MILISECOND_VIEW = 0;

    long step;
    TimeEventHandle eventHandle;
    public TimeRunner(TimeEventHandle eventHandle, long step) {
        this.eventHandle = eventHandle;
        this.step = step;
    }

    @Override
    public void run() {
        eventHandle.onTimeChange();
        eventHandle.getHandler().postDelayed(this, step);
    }

}
