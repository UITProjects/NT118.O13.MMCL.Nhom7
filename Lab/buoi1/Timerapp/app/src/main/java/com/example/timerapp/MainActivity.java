package com.example.timerapp;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView timer_tv;
    Button start_btn;
    Button reset_btn;
    CountDownTimer countDownTimer;
    Boolean counterIsActive = false;
    ProgressBar timer_pg;
    MediaPlayer mediaPlayer;

    public long TimeLeft = 30000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer_pg = findViewById(R.id.progressBar);
        timer_pg.setMax(30);
        timer_pg.setProgress(0);
        timer_tv = findViewById(R.id.timer_tv);
        start_btn = findViewById(R.id.start_btn);
        reset_btn = findViewById(R.id.reset_btn);
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm);
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimeReset();

            }
        });
    }


    private void update(int progress){
        int minutes = progress / 60;
        int seconds = progress % 60;
        String secondsFinal = "";
        if(seconds <= 9){
            secondsFinal = "0" + seconds;
        }
        else{
            secondsFinal = "" + seconds;
        }
        timer_pg.setProgress(progress);
        timer_tv.setText("" + minutes + ":" + secondsFinal);
    }

    public void start_timer(View view){
        if(counterIsActive == false){
            counterIsActive = true;
            start_btn.setText("STOP");
            countDownTimer = new CountDownTimer(TimeLeft, 1000) {
                @Override
                public void onTick(long l) {
                    TimeLeft = l;
                    update((int) l/ 1000);
                }

                @Override
                public void onFinish() {
                    stop();
                    int count = 0;
                    if(mediaPlayer != null)
                        mediaPlayer.start();
                    while (count>5){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        count+=1;
                        if (count==3)
                        {
                            mediaPlayer.stop();
                            break;
                        }

                    }
                }
            }.start();
        }else{
            stop();
        }
    }

    private void stop(){
        //timer_tv.setText("0:30");
        timer_pg.setProgress(0);
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        start_btn.setText("START");
        timer_pg.setEnabled(true);
        counterIsActive = false;
    }
    public void TimeReset(){
        TimeLeft = 30000;
        counterIsActive = false;
        timer_tv.setText("00:30");
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(counterIsActive){
            countDownTimer.cancel();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(counterIsActive){
            countDownTimer.cancel();
        }
    }
}