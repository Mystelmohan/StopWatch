package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements Runnable {

    Button start, stop, reset;
    TextView dis;
    int hour, min, sec, millisec;
    String display;
    boolean on;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        start = findViewById(R.id.start);
        stop = findViewById(R.id.stop);
        reset = findViewById(R.id.reset);
        dis = findViewById(R.id.dis);

        // Set initial display
        changedisplay();
    }

    public void resetTimer() {
        hour = min = sec = millisec = 0;
    }

    public void update() {
        millisec++;
        if (millisec == 1000) {
            millisec = 0;
            sec++;
            if (sec == 60) {
                sec = 0;
                min++;
                if (min == 60) {
                    min = 0;
                    hour++;
                }
            }
        }
    }

    public void changedisplay() {
        display = String.format("%02d:%02d:%02d:%03d", hour, min, sec, millisec);
        handler.post(() -> dis.setText(display));
    }

    @Override
    public void run() {
        while (on) {
            try {
                Thread.sleep(1);
                update();
                changedisplay();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void Start(View view) {
        if (!on) {
            on = true;
            new Thread(this, "stopWatch").start();
        }
    }

    public void Reset(View view) {
        on = false;
        resetTimer();

        changedisplay();

        // Ensure display is updated immediately after reset
    }

    public void Stop(View view) {
        on = false;
    }
}