package com.todolist.reminder;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jackandphantom.circularprogressbar.CircleProgressbar;

public class StatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CircleProgressbar circleProgressbar = (CircleProgressbar)findViewById(R.id.myProgress);
        circleProgressbar.setForegroundProgressColor(Color.RED);
        circleProgressbar.setBackgroundProgressWidth(50);
        circleProgressbar.setForegroundProgressWidth(40);
        circleProgressbar.enabledTouch(true);
        circleProgressbar.setRoundedCorner(true);
        circleProgressbar.setClockwise(true);
        int animationDuration = 2500; // 2500ms = 2,5s
        circleProgressbar.setProgressWithAnimation(65, animationDuration);

    }

}
