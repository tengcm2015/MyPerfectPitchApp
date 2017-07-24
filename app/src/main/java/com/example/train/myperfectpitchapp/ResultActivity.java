package com.example.train.myperfectpitchapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by train on 2017/07/03.
 */

public class ResultActivity extends MainActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        //結果取得
        Intent intent = getIntent();
        int[] result = intent.getIntArrayExtra("result");

        //レベル文字列取得・配置
        int resid = getResources().getIdentifier("difficulty_" + String.valueOf(result[0]),"string",getPackageName());
        TextView textView = (TextView)findViewById(R.id.textView_result_1);
        textView.setText(resid);

        //結果配置
        textView = (TextView)findViewById(R.id.textView_result_2);
        textView.setText(String.valueOf(result[2]) + " / " + String.valueOf(result[1]));

        //コメント配置
        String[] comment = getResources().getStringArray(R.array.result_comment);
        int comment_num = (int)(((float)result[2] / (float)result[1]) * (float)(comment.length - 1));
        textView = (TextView)findViewById(R.id.textView_result_3);
        textView.setText(comment[comment_num]);

        //ボタンonClick用
        Button returnButton1 = (Button)findViewById(R.id.button_result_1);
        returnButton1.setOnClickListener(this);
    }

    @Override
    public void onClick(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.button_result_1:
                intent = new Intent(getApplication(),MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
                default:
                    break;
        }
    }
}
