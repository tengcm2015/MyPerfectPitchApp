package com.example.train.myperfectpitchapp;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Created by train on 2017/06/30.
 */

public class QuestionActivity extends MainActivity implements View.OnClickListener{

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
        int level = intent.getIntExtra("level", 0);
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

        //再帰的に問題を出題・回答
        subfunc_question();
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button_question_1:
                //難易度選択に戻る
                finish();
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

    public void subfunc_question(){

        //キーボードの値リセット
        Arrays.fill(inputted_key,0);

        //ダイアログを出す
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.now_play)
                .setPositiveButton(R.string.close, null);
        AlertDialog dialog = builder.show();

        //音を出す
        ans = subfunc_question3_makesound();

        //ダイアログを閉じる
        Button button = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //閉じられてなかったらダイアログを閉じる
                dialog.dismiss();
            }
        });

        //入力する
        for(int i = 0; i < 12; i++){
            buttons[i] = getResources().getIdentifier("button_question_" + (i+3), "id", getPackageName());
            Button tmp_button = (Button)findViewById(buttons[i]);
            tmp_button.setOnClickListener(this);
        }

        //決定する
        Button button_deside = (Button)findViewById(R.id.button_question_15);
        button_deside.setOnClickListener(this);
    }

    public void subfunc_question2(){
        //ログ出力
        TextView levelText = (TextView)findViewById(R.id.textView_question_2);
        levelText.setText("入力値 : " + Arrays.toString(inputted_key));

        //正解不正解の判定・結果ダイアログを出す
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(Arrays.equals(ans,inputted_key)){
            result[2] += 1;
            builder.setMessage(R.string.correct)
                    .setPositiveButton(R.string.next, null);
        }else{
            builder.setMessage(R.string.wrong)
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

    public int[] subfunc_question3_makesound(){
        // C D E F G A B C# D# F# G# A#の順
        int[] result = new int[12];
        Arrays.fill(result,0);
        //なんかの基準で1を付ける
        //音を鳴らす
        return result;
    }
}
