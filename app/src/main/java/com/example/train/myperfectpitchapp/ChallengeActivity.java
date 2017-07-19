package com.example.train.myperfectpitchapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Arrays;

/**
 * Created by Akira on 2017/07/19.
 */

public class ChallengeActivity extends MainActivity implements View.OnClickListener{

    //グローバル変数用
    UtilCommon common;
    //チャレンジ
    int challenge = 0;
    //処理待ち
    boolean load_check = false;
    ProgressDialog progressDialog;
    //音
    int SOUND_POOL_MAX = 12;
    int[] soundIds = new int[12];
    SoundPool soundPool;
    String[] soundname;
    String[] set_soundname;
    //入力値
    int[] inputted_key = new int[12];
    int[] buttons = new int[12];
    //問題数
    int now_num = 0;
    int question = 5;
    //答え
    int[] ans = new int[12];
    //結果
    int[] result = new int[3];
    //背景
    Drawable defaultBackGround;
    Drawable pushedBackGround;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge);

        //グローバル変数用
        common = (UtilCommon)getApplication();
        int[] tmp_mode = common.getGlobal();

        //レベル取得
        Intent intent = getIntent();
        challenge = intent.getIntExtra("challenge", 0);

        //問題数取得
        int tmp = 5;
        question = tmp;

        //結果変数初期化
        Arrays.fill(result,0);
        result[0] = challenge;
        result[1] = question;

        //文字列取得
        String[] Language = getResources().getStringArray(R.array.language);
        soundname = getResources().getStringArray(R.array.default_soundNames);
        set_soundname = getResources().getStringArray(getResources().getIdentifier(Language[tmp_mode[1]] + "_soundNames","array", getPackageName()));

        //レベル表示
        TextView challengeText = (TextView)findViewById(R.id.textView_question_1);
        challengeText.setText("challenge : " + String.valueOf(challenge));

        //難易度選択に戻るボタン
        Button returnButton = (Button)findViewById(R.id.button_question_1);
        returnButton.setOnClickListener(this);

        //ボタン背景を作成
        defaultBackGround = returnButton.getBackground().getConstantState().newDrawable().mutate();
        pushedBackGround = defaultBackGround.getConstantState().newDrawable().mutate();
        pushedBackGround.setColorFilter(Color.CYAN, PorterDuff.Mode.SRC_OVER);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button_challenge_1:
                break;
        }
    }
}
