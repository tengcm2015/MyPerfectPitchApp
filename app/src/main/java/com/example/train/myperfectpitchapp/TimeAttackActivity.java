package com.example.train.myperfectpitchapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by train on 2017/08/12.
 */

public class TimeAttackActivity extends MainActivity implements View.OnClickListener, SoundPool.OnLoadCompleteListener{

    //グローバル変数用
    UtilCommon common;
    //チャレンジ
    int timeattack = 0;
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
    //出力画面占有状況
    int[][] outputted_pic = new int[3][4];
    //待てる時間
    int waittime_all = 20000;
    int waittime_q = 5000;
    //問題数
    int now_num = 0;
    int question;
    //結果
    int[] result = new int[3];
    //背景
    Drawable defaultBackGround;
    Drawable pushedBackGround;
    //countdowntimer
    Handler handler1 = new Handler();
    Handler handler2 = new Handler();
    CountDownTimer countDownTimer1;
    CountDownTimer countDownTimer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeattack);

        //グローバル変数用
        common = (UtilCommon)getApplication();
        int[] tmp_mode = common.getGlobal();

        //レベル取得
        Intent intent = getIntent();
        timeattack = intent.getIntExtra("timeattack", 0);

        //問題数取得
        int tmp = 40;
        question = tmp;

        //結果変数初期化
        Arrays.fill(result,0);
        result[0] = timeattack;
        result[1] = question * 10;//最大ポイント = 問題数 * 10

        //文字列取得
        String[] Language = getResources().getStringArray(R.array.language);
        soundname = getResources().getStringArray(R.array.default_soundNames);
        set_soundname = getResources().getStringArray(getResources().getIdentifier(Language[tmp_mode[1]] + "_soundNames","array", getPackageName()));

        //レベル表示
        TextView timeattackText = (TextView)findViewById(R.id.textView_timeattack_1);
        timeattackText.setText("timeattack : " + String.valueOf(timeattack));

        //音声ファイルの初期化
        subfunc_timeattack0_initsound();
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button_timeattack_back:
                //難易度選択に戻る
                subfunc_timeattack7_finActivity();
                finish();
                break;
            default:
                //キーボード入力
                for(int i = 0; i < 12; i++){
                    if(view.getId() == buttons[i]){
                        //答えチェック
                        subfunc_timeattack6_anscheck(i);
                        break;
                    }
                }
                //ちょっと待つ
                /******************
                try {
                    Thread.sleep(50); //50ミリ秒Sleepする
                } catch (InterruptedException e) {                }
                 ***************/
                //再度実行
                subfunc_timeattack3_makesound();
                break;
        }
    }

    @Override
    public void onLoadComplete(SoundPool soundPool, int sampleId, int status){
        if (sampleId == soundIds.length){
            //ロード完了
            load_check = true;
            //ロード中ダイアログ消去
            progressDialog.dismiss();
            //再帰的に問題を出題・回答
            subfunc_timeattack();
        }
    }

    public void subfunc_timeattack0_initsound(){

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

        soundPool.setOnLoadCompleteListener(this);

        soundIds[0] = soundPool.load(this, R.raw.piano_c, 1);
        soundIds[1] = soundPool.load(this, R.raw.piano_cs, 1);
        soundIds[2] = soundPool.load(this, R.raw.piano_d, 1);
        soundIds[3] = soundPool.load(this, R.raw.piano_ds, 1);
        soundIds[4] = soundPool.load(this, R.raw.piano_e, 1);
        soundIds[5] = soundPool.load(this, R.raw.piano_f, 1);
        soundIds[6] = soundPool.load(this, R.raw.piano_fs, 1);
        soundIds[7] = soundPool.load(this, R.raw.piano_g, 1);
        soundIds[8] = soundPool.load(this, R.raw.piano_gs, 1);
        soundIds[9] = soundPool.load(this, R.raw.piano_a, 1);
        soundIds[10] = soundPool.load(this, R.raw.piano_as, 1);
        soundIds[11] = soundPool.load(this, R.raw.piano_b, 1);

        //キーボードの値リセット
        Arrays.fill(inputted_key,0);

        //出力画面占有状況値リセット
        for(int i = 0; i < outputted_pic.length; i++){
            Arrays.fill(outputted_pic[i],-1);
            outputted_pic[i][0] = 0;
        }

        //入力キーボーボタン初期化
        for(int i = 0; i < 12; i++){
            buttons[i] = getResources().getIdentifier("button_timeattack_" + soundname[i], "id", getPackageName());
            Button tmp_button = (Button)findViewById(buttons[i]);
            if(i == 0){
                //ボタン背景を作成
                defaultBackGround = tmp_button.getBackground().getConstantState().newDrawable().mutate();
                pushedBackGround = defaultBackGround.getConstantState().newDrawable().mutate();
                pushedBackGround.setColorFilter(Color.CYAN, PorterDuff.Mode.SRC_OVER);
            }
            tmp_button.setText(set_soundname[i]);
            tmp_button.setAllCaps(false);
            tmp_button.setOnClickListener(this);
        }

        //難易度選択に戻るボタン
        BootstrapButton returnButton = (BootstrapButton) findViewById(R.id.button_timeattack_back);
        returnButton.setOnClickListener(this);


    }

    public void subfunc_timeattack(){
        //初回の音を鳴らす
        subfunc_timeattack3_makesound();
        //時間経過で終了
        DonutProgress donutProgress = (DonutProgress) findViewById(R.id.progressBar1);
        //donutProgress.setMax(100);
        int tmp_time = waittime_all/100;
        Log.d("DonutProgress1 tmp1",Long.toString(tmp_time));
        countDownTimer1 = new CountDownTimer(waittime_all,tmp_time) {
            @Override
            public void onTick(long l) {
                // lは残り時間 // 最後max値の100になって終了
                Log.d("DonutProgress1",Long.toString(l));
                //donutProgress.setProgress((int) (waittime_all - l) / 100);
                donutProgress.setProgress(donutProgress.getProgress()+1);
            }

            @Override
            public void onFinish() {
                //時間経過。終了。
                subfunc_timeattack7_finActivity();
                Intent intent = new Intent(getApplication(),ResultActivity.class);
                intent.putExtra("result",result);
                startActivity(intent);
            }
        }.start();
    }

    public void subfunc_timeattack2_checktime(){
        DonutProgress progressBar2 = (DonutProgress) findViewById(R.id.progressBar2);
        //progressBar2.setMax(100);
        int tmp_time2 = waittime_q/100;
        Log.d("DonutProgress2 tmp2",Long.toString(tmp_time2));
        countDownTimer2 = new CountDownTimer(waittime_q,tmp_time2) {
            @Override
            public void onTick(long l) {
                // lは残り時間 // 最後max値の100になって終了
                Log.d("DonutProgress2",Long.toString(l));
                progressBar2.setProgress((int) (waittime_q - l) / 100);
                //progressBar2.setProgress(progressBar2.getProgress()+1);
            }

            @Override
            public void onFinish() {
                //時間経過。終了。
                subfunc_timeattack7_finActivity();
                Intent intent = new Intent(getApplication(),ResultActivity.class);
                intent.putExtra("result",result);
                startActivity(intent);
            }
        }.start();
    }

    public void subfunc_timeattack3_makesound(){
        //乱数作成
        Random rnd = new Random();
        int sound_tmpnum, pic_tmpnum;
        //場所を決める//場所は一か所
        pic_tmpnum = 1;
        //音を決める
        sound_tmpnum = rnd.nextInt(12);
        //配列に格納
        outputted_pic[pic_tmpnum][0] = 1;
        outputted_pic[pic_tmpnum][1] = sound_tmpnum;
        outputted_pic[pic_tmpnum][2] = 1;
        //画面に出力
        //ImageView imageView = (ImageView)findViewById(getResources().getIdentifier("image_timeattack_" + Integer.toString(pic_tmpnum + 1), "id", getPackageName()));
        //imageView.setImageResource(R.drawable.girl_play);
        subfunc_timeattack2_checktime();
        //音の出力
        outputted_pic[pic_tmpnum][3] = soundPool.play(soundIds[sound_tmpnum],1.0f,1.0f,0,10,1.0f);
    }

    public void subfunc_timeattack6_anscheck(int tmp){
        //まずはタイマーを止める
        countDownTimer2.cancel();
        if(outputted_pic[1][1] == tmp){
            //入力が正解
            //画面反映
            ImageView imageView = (ImageView)findViewById(getResources().getIdentifier("image_timeattack_" + Integer.toString(1+1), "id", getPackageName()));
            imageView.setImageResource(R.drawable.girl_ok);
            //結果に追加
            result[2] += 10;
            //音を止める
            soundPool.stop(outputted_pic[1][3]);
            //配列初期化
            Arrays.fill(outputted_pic[1],-1);
            outputted_pic[1][0] = 0;
        }
        else{
            //入力が不正解
            //画面反映
            ImageView imageView = (ImageView)findViewById(getResources().getIdentifier("image_timeattack_" + Integer.toString(1+1), "id", getPackageName()));
            imageView.setImageResource(R.drawable.girl_ng);
            //音を止める
            soundPool.stop(outputted_pic[1][3]);
            //配列初期化
            Arrays.fill(outputted_pic[1],-1);
        }
    }

    public void subfunc_timeattack7_finActivity(){
        countDownTimer1.cancel();
        countDownTimer2.cancel();
        if(outputted_pic[1][3] != -1){
            soundPool.stop(outputted_pic[1][3]);
        }
        load_check = false;
        soundPool.release();
        soundPool = null;
    }
}
