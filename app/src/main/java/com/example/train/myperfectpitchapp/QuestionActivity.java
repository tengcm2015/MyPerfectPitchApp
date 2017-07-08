package com.example.train.myperfectpitchapp;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;

import static java.sql.Types.NULL;

/**
 * Created by train on 2017/06/30.
 */

public class QuestionActivity extends MainActivity implements View.OnClickListener{

    //レベル
    int level = 0;
    //処理待ち
    boolean load_check = false;
    ProgressDialog progressDialog;
    //音
    int[] soundIds = new int[12];
    SoundPool soundPool;
    String[] soundname = {"C","D","E","F","G","A","B","C#","D#","F#","G#","A#"};
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
        setContentView(R.layout.activity_question);

        //レベル取得
        Intent intent = getIntent();
        level = intent.getIntExtra("level", 0);
        //問題数取得
        int tmp = 5;
        question = tmp;
        //結果変数初期化
        Arrays.fill(result,0);
        result[0] = level;
        result[1] = question;

        //レベル表示
        TextView levelText = (TextView)findViewById(R.id.textView_question_1);
        levelText.setText("level : " + String.valueOf(level));

        //難易度選択に戻るボタン
        Button returnButton = (Button)findViewById(R.id.button_question_1);
        returnButton.setOnClickListener(this);

        //ボタン背景を作成
        defaultBackGround = returnButton.getBackground().getConstantState().newDrawable().mutate();
        pushedBackGround = defaultBackGround.getConstantState().newDrawable().mutate();
        pushedBackGround.setColorFilter(Color.CYAN, PorterDuff.Mode.SRC_OVER);

        //音声ファイルの初期化
        subfunc_question0_initsound();

        //再帰的に問題を出題・回答
        subfunc_question();
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button_question_1:
                //難易度選択に戻る
                load_check = false;
                soundPool.release();
                soundPool = null;
                finish();
                break;
            case R.id.button_question_16:
                //再度再生
                subfunc_question3_makesound();
                break;
            case R.id.button_question_15:
                //入力音決定
                subfunc_question2();
                break;
            default:
                //キーボードが押された
                for(int i = 0; i < 12; i++){
                    if(view.getId() == buttons[i]){
                        Button button = (Button)findViewById(view.getId());
                        if(inputted_key[i] == 1){
                            inputted_key[i] = 0;
                            button.setBackground(defaultBackGround);
                        }else{
                            inputted_key[i] = 1;
                            button.setBackground(pushedBackGround);
                        }break;
                    }
                }
                break;
        }
    }

    public void subfunc_question0_initsound(){
        final int SOUND_POOL_MAX = 12;

        //プログレスダイアログを出す
        progressDialog = new ProgressDialog(this);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setMessage("ロード中");
        progressDialog.setCancelable(true);
        progressDialog.show();

        AudioAttributes attr = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        soundPool = new SoundPool.Builder()
                .setAudioAttributes(attr)
                .setMaxStreams(SOUND_POOL_MAX)
                .build();
        soundIds[0] = soundPool.load(this, R.raw.piano_0, 1);
        soundIds[1] = soundPool.load(this, R.raw.piano_1, 1);
        soundIds[2] = soundPool.load(this, R.raw.piano_2, 1);
        soundIds[3] = soundPool.load(this, R.raw.piano_3, 1);
        soundIds[4] = soundPool.load(this, R.raw.piano_4, 1);
        soundIds[5] = soundPool.load(this, R.raw.piano_5, 1);
        soundIds[6] = soundPool.load(this, R.raw.piano_6, 1);
        soundIds[7] = soundPool.load(this, R.raw.piano_7, 1);
        soundIds[8] = soundPool.load(this, R.raw.piano_8, 1);
        soundIds[9] = soundPool.load(this, R.raw.piano_9, 1);
        soundIds[10] = soundPool.load(this, R.raw.piano_10, 1);
        soundIds[11] = soundPool.load(this, R.raw.piano_11, 1);
    }

    public void subfunc_question(){

        //キーボードの値リセット
        Arrays.fill(inputted_key,0);

        //音を決める
        Random rnd = new Random();
        Arrays.fill(ans,0);
        for(int i = 0; i < level; i++){
            ans[rnd.nextInt(12)] = 1;
        }

        //音を出す
        subfunc_question3_makesound();

        //入力する
        for(int i = 0; i < 12; i++){
            buttons[i] = getResources().getIdentifier("button_question_" + (i+3), "id", getPackageName());
            Button tmp_button = (Button)findViewById(buttons[i]);
            tmp_button.setOnClickListener(this);
        }

        //再度再生する
        Button button_again = (Button)findViewById(R.id.button_question_16);
        button_again.setOnClickListener(this);

        //決定する
        Button button_deside = (Button)findViewById(R.id.button_question_15);
        button_deside.setOnClickListener(this);
    }

    public void subfunc_question2(){
        //ログ出力
        //TextView levelText = (TextView)findViewById(R.id.textView_question_2);
        //levelText.setText("入力値 : " + Arrays.toString(inputted_key));

        //正解の音の文字列生成
        String ans_str = "";
        for(int i = 0; i < 12; i++){
            if(ans[i] == 1){
                ans_str = ans_str + " " + soundname[i];
            }
        }

        //正解不正解の判定・結果ダイアログを出す
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(Arrays.equals(ans,inputted_key)){
            result[2] += 1;
            builder.setTitle(R.string.correct)
                    .setMessage("正解 :" + ans_str)
                    .setPositiveButton(R.string.next, null);
        }else{
            builder.setTitle(R.string.wrong)
                    .setMessage("正解 :" + ans_str)
                    .setPositiveButton(R.string.next, null);
        }
        AlertDialog dialog2 = builder.show();

        //次へが押されるまで待つ
        Button button2 = dialog2.getButton(DialogInterface.BUTTON_POSITIVE);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((now_num + 1) < question){
                    now_num += 1;
                    dialog2.dismiss();
                    subfunc_question();
                }else{
                    dialog2.dismiss();
                    //結果へ
                    load_check = false;
                    soundPool.release();
                    soundPool = null;
                    Intent intent = new Intent(getApplication(),ResultActivity.class);
                    intent.putExtra("result",result);
                    startActivity(intent);
                }
            }
        });

        for(int i = 0; i < 12; i++){
            Button button = (Button)findViewById(buttons[i]);
            button.setBackground(defaultBackGround);
        }
    }

    public void subfunc_question3_makesound(){
        // C D E F G A B C# D# F# G# A#の順

        //ロードを待つ
        if(load_check == true){
            //音を鳴らす
            subfunc_question4_makeplayingdialog();
            for(int j = 0; j < 12; j++){
                if(ans[j] == 1){
                    soundPool.play(soundIds[j],1.0f,1.0f,0,0,1.0f);
                    j = j;
                }
            }
            return;
        }
        else {
            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                @Override
                public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                    if(i == soundIds[11]){
                        load_check = true;
                        progressDialog.dismiss();
                        subfunc_question4_makeplayingdialog();
                        for(int j = 0; j < 12; j++){
                            if(ans[j] == 1){
                                soundPool.play(soundIds[j],1.0f,1.0f,0,0,1.0f);
                            }
                        }
                    }
                }
            });
        }
        return;
    }

    public void subfunc_question4_makeplayingdialog(){
        AlertDialog.Builder builder;
        AlertDialog dialog;
        //ダイアログを出す
        builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.now_play)
                .setPositiveButton(R.string.close, null);
        dialog = builder.show();
        //ダイアログを閉じる
        Button button = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //閉じられてなかったらダイアログを閉じる
                dialog.dismiss();
            }
        });
    }
}
