package com.example.yogaapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MuscleActivity extends AppCompatActivity implements View.OnClickListener{

    int[] intArray;

    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second2);

        Toolbar toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        back = (ImageView) findViewById(R.id.returnToMain);
        back.setOnClickListener(this);

        intArray = new int[]{
                R.id.benchdips, R.id.bicyclecrunch,
                R.id.legraises, R.id.legupcrunches, R.id.situppose,
                R.id.plankrotatepose,
                R.id.planklegpose, R.id.russiantwistpose, R.id.bridges,
                R.id.windmillpose
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
        Intent intent = new Intent(MuscleActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void Imagebuttonclicked(View view) {

        for (int i=0; i < intArray.length; i++){
            if (view.getId() == intArray[i]){
                int value = i+1;
                Intent intent = new Intent(MuscleActivity.this, MuscleActivityList.class);
                intent.putExtra("value", String.valueOf(value));
                startActivity(intent);
            }
        }
    }
}