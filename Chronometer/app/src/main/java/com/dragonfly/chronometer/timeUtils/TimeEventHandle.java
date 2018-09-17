package com.dragonfly.chronometer.timeUtils;

import android.os.Handler;

public interface TimeEventHandle {
    Handler getHandler();
    void onTimeChange();
}
