package com.example.train.myperfectpitchapp;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.TypefaceProvider;

/**
 * Created by train on 2017/06/30.
 */

public class SelectModeActivity extends MainActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_selectmode);

        BootstrapButton trainButton = (BootstrapButton)findViewById(R.id.button_mode_1);
        trainButton.setOnClickListener(this);

        BootstrapButton challengeButton = (BootstrapButton)findViewById(R.id.button_mode_2);
        challengeButton.setOnClickListener(this);

        BootstrapButton returnButton = (BootstrapButton)findViewById(R.id.button_mode_4);
        returnButton.setOnClickListener(this);

        BootstrapButton settingButton = (BootstrapButton) findViewById(R.id.button_mode_5);
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
                break;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        BootstrapButton button1 = (BootstrapButton)findViewById(R.id.button_mode_1);
        int width = button1.getWidth();
        BootstrapButton button2 = (BootstrapButton)findViewById(R.id.button_mode_2);
        BootstrapButton button3 = (BootstrapButton)findViewById(R.id.button_mode_3);
        button2.setWidth(width);
        button3.setWidth(width);
    }
}
