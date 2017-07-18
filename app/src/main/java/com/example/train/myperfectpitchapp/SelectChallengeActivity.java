package com.example.train.myperfectpitchapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by train on 2017/07/18.
 */

public class SelectChallengeActivity extends MainActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectchallenge);

        Button levelButton1 = (Button)findViewById(R.id.button_level_1);
        Button levelButton2 = (Button)findViewById(R.id.button_level_2);
        Button returnButton1 = (Button)findViewById(R.id.button_level_5);
        Button returnButton2 = (Button)findViewById(R.id.button_level_6);
        levelButton1.setOnClickListener(this);
        levelButton2.setOnClickListener(this);
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
                /**************************************************************
                intent = new Intent(getApplication(),QuestionActivity.class);
                intent.putExtra("level",level);
                startActivity(intent);
                **************************************************************/
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
