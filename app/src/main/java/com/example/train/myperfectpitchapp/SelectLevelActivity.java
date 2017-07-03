package com.example.train.myperfectpitchapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by train on 2017/06/30.
 */

public class SelectLevelActivity extends MainActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectlevel);

        Button levelButton1 = (Button)findViewById(R.id.button_level_1);
        Button levelButton2 = (Button)findViewById(R.id.button_level_2);
        Button levelButton3 = (Button)findViewById(R.id.button_level_3);
        Button levelButton4 = (Button)findViewById(R.id.button_level_4);
        Button returnButton1 = (Button)findViewById(R.id.button_level_5);
        Button returnButton2 = (Button)findViewById(R.id.button_level_6);
        levelButton1.setOnClickListener(this);
        levelButton2.setOnClickListener(this);
        levelButton3.setOnClickListener(this);
        levelButton4.setOnClickListener(this);
        returnButton1.setOnClickListener(this);
        returnButton2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        int level = 5;
        Intent intent;
        switch (view.getId()){
            case R.id.button_level_1:
                level -= 1;
            case R.id.button_level_2:
                level -= 1;
            case R.id.button_level_3:
                level -= 1;
            case R.id.button_level_4:
                level -= 1;
                intent = new Intent(getApplication(),QuestionActivity.class);
                intent.putExtra("level",level);
                startActivity(intent);
                break;
            case R.id.button_level_5:
                finish();
                break;
            case R.id.button_level_6:
                intent = new Intent(getApplication(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
        }
    }
}
