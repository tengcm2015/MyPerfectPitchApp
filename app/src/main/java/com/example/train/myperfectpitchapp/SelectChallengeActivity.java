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

        Button challengeButton1 = (Button)findViewById(R.id.button_challenge_1);
        Button challengeButton2 = (Button)findViewById(R.id.button_challenge_2);
        Button returnButton1 = (Button)findViewById(R.id.button_challenge_5);
        Button returnButton2 = (Button)findViewById(R.id.button_challenge_6);
        challengeButton1.setOnClickListener(this);
        challengeButton2.setOnClickListener(this);
        returnButton1.setOnClickListener(this);
        returnButton2.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        int challenge = 3;
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
                finish();
                break;
            case R.id.button_challenge_6:
                intent = new Intent(getApplication(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
        }
    }
}
