package com.example.train.myperfectpitchapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by train on 2017/06/30.
 */

public class SelectModeActivity extends MainActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectmode);

        Button trainButton = (Button)findViewById(R.id.button_mode_1);
        trainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), SelectLevelActivity.class);
                startActivity(intent);
            }
        });

        Button returnButton = (Button)findViewById(R.id.button_mode_4);
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
