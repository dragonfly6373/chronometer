package com.dragonfly.chronometer;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Home extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "INFO";
    private static final long SECOND_VIEW = 999;
    private static final long MILISECOND_VIEW = 0;
    Handler handler;
    Runner runner;
    long startTime, counter;
    TextView timeView;
    Button btnStart, btnPause, btnResume, btnReset, btnLap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        timeView = (TextView) findViewById(R.id.lblView);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnPause = (Button) findViewById(R.id.btnPause);
        btnResume = (Button) findViewById(R.id.btnResume);
        btnReset = (Button) findViewById(R.id.btnReset);
        btnLap = (Button) findViewById(R.id.btnLap);
        btnStart.setOnClickListener(this);
        btnPause.setOnClickListener(this);
        btnResume.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        btnLap.setOnClickListener(this);
        handler = new Handler();
        runner = new Runner(SECOND_VIEW) {
            @Override
            public void onTimeUpdate(long currentTime) {
                long timeDelta = currentTime - startTime;
                updateTimeLabel(timeDelta);
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                Log.d(TAG, "onClick: button start");
                btnStart.setVisibility(View.GONE);
                btnPause.setVisibility(View.VISIBLE);
                startTime = SystemClock.uptimeMillis();
                counter = 0;
                handler.postDelayed(runner, 0);
                break;
            case R.id.btnPause:
                Log.d(TAG, "onClick: button pause");
                btnPause.setVisibility(View.GONE);
                btnReset.setVisibility(View.VISIBLE);
                btnResume.setVisibility(View.VISIBLE);
                handler.removeCallbacks(runner);
                counter += SystemClock.uptimeMillis() - startTime;
                Log.d(TAG, "counter = " + counter);
                break;
            case R.id.btnResume:
                Log.d(TAG, "onClick: button resume");
                btnResume.setVisibility(View.GONE);
                btnPause.setVisibility(View.VISIBLE);
                btnReset.setVisibility(View.GONE);
                startTime = SystemClock.uptimeMillis();
                handler.postDelayed(runner, 0);
                break;
            case R.id.btnReset:
                Log.d(TAG, "onClick: button reset");
                btnReset.setVisibility(View.GONE);
                btnResume.setVisibility(View.GONE);
                btnStart.setVisibility(View.VISIBLE);
                counter = 0;
                updateTimeLabel(0);
                break;
            case R.id.btnLap:
                Log.d(TAG, "onClick: button lap");
                break;
            default:
                break;
        }
    }

    private void updateTimeLabel(long delta) {
        Log.d(TAG, "updateTimeLabel: " + delta);
        long ms = counter + delta;
        long ss = ms / 1000;
        ms %= 1000;
        long mm = ss / 60;
        ss %= 60;
        long hh = mm / 60;
        mm %= 60;
//        timeView.setText(new SimpleDateFormat("hh : mm : ss.SSS").format(new Date(counter + delta)));
        timeView.setText(String.format("%02d : %02d : %02d.%03d", hh, mm, ss, ms));
    }

    interface RunnerHandle {
        void onTimeUpdate(long currentTime);
    }

    abstract class Runner implements Runnable, RunnerHandle {
        private long step;
        public Runner(long step) {
            this.step = step;
        }
        @Override
        public void run() {
            long currentTime = SystemClock.uptimeMillis();
            handler.postDelayed(this, step);
            onTimeUpdate(currentTime);
        }
    }
}
