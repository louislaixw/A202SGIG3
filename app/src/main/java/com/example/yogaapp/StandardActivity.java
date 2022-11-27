package com.example.yogaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class StandardActivity extends AppCompatActivity implements View.OnClickListener{

    int[] intArray;

    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        back = (ImageView) findViewById(R.id.returnToMain);
        back.setOnClickListener(this);

        intArray = new int[]{
                R.id.mountain_climber, R.id.basic_crunches,
                R.id.wall_squat,
                R.id.bicycle_crunch, R.id.legraise, R.id.heeltouch,
                R.id.legupcrunch, R.id.situp, R.id.plank,
                R.id.plankmain,
                R.id.russiantwistpose, R.id.bridge, R.id.legcrunch
        };
    }

    @Override
    public void onClick(View v){
        switch(v.getId()) {
            case R.id.returnToMain:
                toMain();
                break;
        }
    }

    private void toMain(){
        Intent intent = new Intent(StandardActivity.this, MainActivity.class);
        startActivity(intent);
    }


    //After the image button is clicked.
    public void Imagebuttonclicked(View view) {
        for (int i=0; i < intArray.length; i++){
            if (view.getId() == intArray[i]){
                int value = i+1;
                Log.i("FIRST",String.valueOf(value));
                Intent intent = new Intent(StandardActivity.this, StandardActivityList.class);
                intent.putExtra("value", String.valueOf(value));
                startActivity(intent);
            }
        }
    }
}