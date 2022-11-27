package com.example.yogaapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Date;

public class MuscleActivityList extends AppCompatActivity {

    String buttonValue;
    Button startBtn;
    TextView mTextView;
    private CountDownTimer cdTimer;
    private boolean MTimeRunning;
    private long MTimeLeftMilli;

    Button quitSession;
    AlertDialog.Builder builder;

    private DatabaseReference reference;
    private FirebaseUser user;
    private String userId;

    Date currentTime = Calendar.getInstance().getTime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third2);

        Intent intent = getIntent();
        buttonValue = intent.getStringExtra("value");

        int intValue = Integer.valueOf(buttonValue);

        switch (intValue){
            case 1:
                setContentView(R.layout.activity_benchups2);
                break;

            case 2:
                setContentView(R.layout.activity_bodycrunch2);
                break;

            case 3:
                setContentView(R.layout.activity_legraise2);
                break;

            case 4:
                setContentView(R.layout.activity_vcrunch2);
                break;

            case 5:
                setContentView(R.layout.activity_situps2);
                break;

            case 6:
                setContentView(R.layout.activity_bodytwist2);
                break;

            case 7:
                setContentView(R.layout.activity_plank2);
                break;

            case 8:
                setContentView(R.layout.activity_russiantwists2);
                break;

            case 9:
                setContentView(R.layout.activity_heelt2);
                break;

            case 10:
                setContentView(R.layout.activity_windmills2);
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

        quitSession = (Button) findViewById(R.id.quitSession);
        builder = new AlertDialog.Builder(this);

        quitSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("Alert").setMessage("Are you sure you want to quit?").setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent (MuscleActivityList.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }) .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();
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
                    Toast.makeText(MuscleActivityList.this, "Next Workout!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MuscleActivityList.this, MuscleActivityList.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.putExtra("value",String.valueOf(newValue));
                    startActivity(intent);
                }

                else {
                    builder.setTitle("Alert").setIcon(R.drawable.trophy).setMessage("Congratulations! You have finished this workout session!" +
                                    "Would you like to reset the session or return to the main menu?" + " ").setCancelable(true)
                            .setPositiveButton("Return to Main Menu", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    user = FirebaseAuth.getInstance().getCurrentUser();
                                    reference = FirebaseDatabase.getInstance().getReference("Users");
                                    userId = user.getUid();

                                    String complete2 = "Muscle Building Workout - " + currentTime;
                                    reference.child(userId).child("completed2").setValue(complete2);

                                    Intent intent = new Intent(MuscleActivityList.this, MainActivity.class);
                                    startActivity(intent);
                                }
                            }) .setNegativeButton("Reset Session", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(MuscleActivityList.this, MuscleActivityList.class);
                                    startActivity(intent);
                                }
                            });
                    builder.show();
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