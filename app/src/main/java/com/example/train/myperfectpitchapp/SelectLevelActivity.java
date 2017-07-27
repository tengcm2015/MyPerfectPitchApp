package com.example.train.myperfectpitchapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.TypefaceProvider;

/**
 * Created by train on 2017/06/30.
 */

public class SelectLevelActivity extends MainActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_selectlevel);

        BootstrapButton levelButton1 = (BootstrapButton)findViewById(R.id.button_level_1);
        BootstrapButton levelButton2 = (BootstrapButton)findViewById(R.id.button_level_2);
        BootstrapButton levelButton3 = (BootstrapButton)findViewById(R.id.button_level_3);
        BootstrapButton levelButton4 = (BootstrapButton)findViewById(R.id.button_level_4);
        BootstrapButton settingButton = (BootstrapButton)findViewById(R.id.button_level_5);
        BootstrapButton returnButton1 = (BootstrapButton)findViewById(R.id.button_level_6);
        BootstrapButton returnButton2 = (BootstrapButton)findViewById(R.id.button_level_7);
        levelButton1.setOnClickListener(this);
        levelButton2.setOnClickListener(this);
        levelButton3.setOnClickListener(this);
        levelButton4.setOnClickListener(this);
        settingButton.setOnClickListener(this);
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
                intent = new Intent(getApplication(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.button_level_6:
                intent = new Intent(getApplication(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.button_level_7:
                finish();
                break;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        BootstrapButton button1 = (BootstrapButton)findViewById(R.id.button_level_1);
        int width = button1.getWidth();
        BootstrapButton button2 = (BootstrapButton)findViewById(R.id.button_level_2);
        BootstrapButton button3 = (BootstrapButton)findViewById(R.id.button_level_3);
        BootstrapButton button4 = (BootstrapButton)findViewById(R.id.button_level_4);
        button2.setWidth(width);
        button3.setWidth(width);
        button4.setWidth(width);
    }
}
