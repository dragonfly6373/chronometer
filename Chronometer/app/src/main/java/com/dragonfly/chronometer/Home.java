package com.dragonfly.chronometer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dragonfly.chronometer.timeUtils.StopWatch;
import com.dragonfly.chronometer.timeUtils.TimeRunner;

public class Home extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "INFO";
    StopWatch stopWatch;
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
        stopWatch = new StopWatch(TimeRunner.SECOND_VIEW) {
            @Override
            public void onTimeChange() {
                updateTimeLabel(this.getTimeDelta());
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
                stopWatch.start();
                break;
            case R.id.btnPause:
                Log.d(TAG, "onClick: button pause");
                btnPause.setVisibility(View.GONE);
                btnReset.setVisibility(View.VISIBLE);
                btnResume.setVisibility(View.VISIBLE);
                stopWatch.pause();
                break;
            case R.id.btnResume:
                Log.d(TAG, "onClick: button resume");
                btnResume.setVisibility(View.GONE);
                btnPause.setVisibility(View.VISIBLE);
                btnReset.setVisibility(View.GONE);
                stopWatch.resume();
                break;
            case R.id.btnReset:
                Log.d(TAG, "onClick: button reset");
                btnReset.setVisibility(View.GONE);
                btnResume.setVisibility(View.GONE);
                btnStart.setVisibility(View.VISIBLE);
                stopWatch.reset();
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
        long ms = delta;
        long ss = ms / 1000;
        ms %= 1000;
        long mm = ss / 60;
        ss %= 60;
        long hh = mm / 60;
        mm %= 60;
        timeView.setText(String.format("%02d : %02d : %02d.%03d", hh, mm, ss, ms));
    }
}
