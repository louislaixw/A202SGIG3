package com.example.yogaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {

    String buttonValue;
    Button startBtn;
    TextView mTextView;
    private CountDownTimer cdTimer;
    private boolean MTimeRunning;
    private long MTimeLeftMilli;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Intent intent = getIntent();
        buttonValue = intent.getStringExtra("value");

        int intValue = Integer.valueOf(buttonValue);

        switch (intValue){
            case 1:
                setContentView(R.layout.activity_bow);
                break;

            case 2:
                setContentView(R.layout.activity_bridge);
                break;

            case 3:
                setContentView(R.layout.activity_chain);
                break;

            case 4:
                setContentView(R.layout.activity_child);
                break;

            case 5:
                setContentView(R.layout.activity_cobbler);
                break;

            case 6:
                setContentView(R.layout.activity_cow);
                break;

            case 7:
                setContentView(R.layout.activity_playji);
                break;

            case 8:
                setContentView(R.layout.activity_pauseji);
                break;

            case 9:
                setContentView(R.layout.activity_plank);
                break;

            case 10:
                setContentView(R.layout.activity_crunches);
                break;

            case 11:
                setContentView(R.layout.activity_situp);
                break;

            case 12:
                setContentView(R.layout.activity_rotation);
                break;

            case 13:
                setContentView(R.layout.activity_twist);
                break;

            case 14:
                setContentView(R.layout.activity_windmill);
                break;

            case 15:
                setContentView(R.layout.activity_legup);
                break;

        }

        startBtn = findViewById(R.id.startbutton);
        mTextView = findViewById(R.id.time);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MTimeRunning){
                    stopTimer();
                }
                else {
                    startTimer();
                }
            }
        });
    }

    private void stopTimer() {
        cdTimer.cancel();
        MTimeRunning = false;
        startBtn.setText("START");
    }
    private void startTimer() {
        final CharSequence value1 = mTextView.getText();
        String num1 = value1.toString();
        String num2 = num1.substring(0,2);
        String num3 = num1.substring(3,5);

        final int number = Integer.valueOf(num2)*60+Integer.valueOf(num3);
        MTimeLeftMilli = number*1000;

        cdTimer = new CountDownTimer(MTimeLeftMilli, 1000) {
            @Override
            public void onTick(long msTillEnd) {

                MTimeLeftMilli = msTillEnd;
                updateTimer();

            }

            @Override
            public void onFinish() {

                int newValue = Integer.valueOf(buttonValue)+1;
                if(newValue<=7){
                    Intent intent = new Intent(ThirdActivity.this, ThirdActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("value",String.valueOf(newValue));
                    startActivity(intent);
                }

                else {
                    newValue = 1;
                    Intent intent = new Intent(ThirdActivity.this, ThirdActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("value",String.valueOf(newValue));
                    startActivity(intent);
                }

            }
        }.start();
        startBtn.setText("Pause");
        MTimeRunning=true;
    }

    private void updateTimer() {
        int minutes = (int) MTimeLeftMilli/60000;
        int seconds = (int) MTimeLeftMilli%60000/1000;

        String timeLeftText="";
        if(minutes < 10)
            timeLeftText="0";
        timeLeftText = timeLeftText+minutes+":";
        if(seconds < 10)
            timeLeftText+="0";
        timeLeftText+=seconds;
        mTextView.setText(timeLeftText);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}