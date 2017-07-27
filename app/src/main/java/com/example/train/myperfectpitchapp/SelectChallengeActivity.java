package com.example.train.myperfectpitchapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.TypefaceProvider;

/**
 * Created by train on 2017/07/18.
 */

public class SelectChallengeActivity extends MainActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_selectchallenge);

        BootstrapButton challengeButton1 = (BootstrapButton)findViewById(R.id.button_challenge_1);
        BootstrapButton challengeButton2 = (BootstrapButton)findViewById(R.id.button_challenge_2);
        BootstrapButton settingButton = (BootstrapButton)findViewById(R.id.button_challenge_5);
        BootstrapButton returnButton1 = (BootstrapButton)findViewById(R.id.button_challenge_6);
        BootstrapButton returnButton2 = (BootstrapButton)findViewById(R.id.button_challenge_7);
        challengeButton1.setOnClickListener(this);
        challengeButton2.setOnClickListener(this);
        settingButton.setOnClickListener(this);
        returnButton1.setOnClickListener(this);
        returnButton2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        int challenge = 7;
        Intent intent;
        switch (view.getId()){
            case R.id.button_challenge_1:
                challenge -= 1;
            case R.id.button_challenge_2:
                challenge -= 1;
                intent = new Intent(getApplication(),ChallengeActivity.class);
                intent.putExtra("challenge",challenge);
                startActivity(intent);
                break;
            case R.id.button_challenge_5:
                intent = new Intent(getApplication(), SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.button_challenge_6:
                intent = new Intent(getApplication(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.button_challenge_7:
                finish();
                break;
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        BootstrapButton button1 = (BootstrapButton)findViewById(R.id.button_challenge_1);
        int width = button1.getWidth();
        BootstrapButton button2 = (BootstrapButton)findViewById(R.id.button_challenge_2);
        button2.setWidth(width);
    }
}
