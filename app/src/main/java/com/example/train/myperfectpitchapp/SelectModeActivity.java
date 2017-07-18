package com.example.train.myperfectpitchapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by train on 2017/06/30.
 */

public class SelectModeActivity extends MainActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectmode);

        Button trainButton = (Button)findViewById(R.id.button_mode_1);
        trainButton.setOnClickListener(this);

        Button challengeButton = (Button)findViewById(R.id.button_mode_2);
        challengeButton.setOnClickListener(this);

        Button returnButton = (Button)findViewById(R.id.button_mode_4);
        returnButton.setOnClickListener(this);

        Button settingButton = (Button)findViewById(R.id.button_mode_5);
        settingButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.button_mode_1:
                intent = new Intent(getApplication(), SelectLevelActivity.class);
                startActivity(intent);
                break;
            case R.id.button_mode_2:
                intent = new Intent(getApplication(), SelectChallengeActivity.class);
                startActivity(intent);
                break;
            case R.id.button_mode_4:
                finish();
                break;
            case R.id.button_mode_5:
                intent = new Intent(getApplication(), SettingActivity.class);
                startActivity(intent);
        }
    }
}
