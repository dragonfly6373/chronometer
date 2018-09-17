package com.dragonfly.chronometer.timeUtils;

public class TimeRunner implements Runnable {
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
