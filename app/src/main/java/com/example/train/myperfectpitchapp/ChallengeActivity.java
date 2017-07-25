package com.example.train.myperfectpitchapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Created by Akira on 2017/07/19.
 */

public class ChallengeActivity extends MainActivity implements View.OnClickListener, SoundPool.OnLoadCompleteListener{

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
    //int[] inputted_key = new int[12];
    int[] buttons = new int[12];
    //出力画面占有状況
    int[][] outputted_pic = new int[5][4];
    //待てる時間(カウント数)
    int waitcount = 5;
    //問題数
    int now_num = 0;
    int question;
    //結果
    int[] result = new int[3];
    //背景
    Drawable defaultBackGround;
    Drawable pushedBackGround;
    /** スレッドUI操作用ハンドラ */
    private Handler mHandler = new Handler();
    /** テキストオブジェクト */
    private Runnable updatePicAndSound;

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
        int tmp = 20;
        question = tmp;

        //結果変数初期化
        Arrays.fill(result,0);
        result[0] = challenge;
        result[1] = question * 10;//最大ポイント = 問題数 * 10

        //文字列取得
        String[] Language = getResources().getStringArray(R.array.language);
        soundname = getResources().getStringArray(R.array.default_soundNames);
        set_soundname = getResources().getStringArray(getResources().getIdentifier(Language[tmp_mode[1]] + "_soundNames","array", getPackageName()));

        //レベル表示
        TextView challengeText = (TextView)findViewById(R.id.textView_challenge_1);
        challengeText.setText("challenge : " + String.valueOf(challenge));

        //難易度選択に戻るボタン
        Button returnButton = (Button)findViewById(R.id.button_challenge_back);
        returnButton.setOnClickListener(this);

        //ボタン背景を作成
        defaultBackGround = returnButton.getBackground().getConstantState().newDrawable().mutate();
        pushedBackGround = defaultBackGround.getConstantState().newDrawable().mutate();
        pushedBackGround.setColorFilter(Color.CYAN, PorterDuff.Mode.SRC_OVER);

        //音声ファイルの初期化
        subfunc_challenge0_initsound();
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button_challenge_back:
                //難易度選択に戻る
                subfunc_challenge7_finActivity();
                finish();
                break;
                default:
                    //キーボード入力
                    for(int i = 0; i < 12; i++){
                        if(view.getId() == buttons[i]){
                            subfunc_challenge6_anscheck(i);
                            /*************************************************:
                            Button button = (Button)findViewById(view.getId());
                            if(inputted_key[i] == 1){
                                inputted_key[i] = 0;
                                button.setBackground(defaultBackGround);
                            }else{
                                inputted_key[i] = 1;
                                button.setBackground(pushedBackGround);
                            }break;
                             ****************************************************/
                        }
                    }
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
            subfunc_challenge();
        }
    }

    public void subfunc_challenge0_initsound(){

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
        //Arrays.fill(inputted_key,0);

        //出力画面占有状況値リセット
        for(int i = 0; i < outputted_pic.length; i++){
            Arrays.fill(outputted_pic[i],-1);
            outputted_pic[i][0] = 0;
        }

        //入力キーボーボタン初期化
        for(int i = 0; i < 12; i++){
            buttons[i] = getResources().getIdentifier("button_challenge_" + soundname[i], "id", getPackageName());
            Button tmp_button = (Button)findViewById(buttons[i]);
            tmp_button.setText(set_soundname[i]);
            tmp_button.setAllCaps(false);
            tmp_button.setOnClickListener(this);
        }
    }

    public void subfunc_challenge(){
        //一秒毎に追加するシステム
        updatePicAndSound = new Runnable() {
            public void run() {
                //全マスが埋まっていないかチェック
                ArrayList<Integer> check = subfunc_challenge2_check();
                if(check.size() != 5){
                    //埋まっていない・一定時間経過で音追加
                    subfunc_challenge3_makesound(check);
                    now_num += 1;
                }
                //埋まっているマスが一定時間経過でマス封印
                subfunc_challenge4_timepass();
                //全マスが埋まる・全マス封印で終了
                if(subfunc_challenge5_fincheck() || (now_num == question)){
                    //終了
                    subfunc_challenge7_finActivity();
                    Intent intent = new Intent(getApplication(),ResultActivity.class);
                    intent.putExtra("result",result);
                    startActivity(intent);
                }else{
                    //継続
                    //mHandler.removeCallbacks(updatePicAndSound);
                    mHandler.postDelayed(updatePicAndSound, 2000);
                }
            }
        };
        mHandler.postDelayed(updatePicAndSound, 2000);
    }

    public ArrayList<Integer> subfunc_challenge2_check(){
        ArrayList<Integer> array = new ArrayList<Integer>();
        for(int i = 0; i < outputted_pic.length; i++){
            if(outputted_pic[i][0] == 1){
                //表示されている
                array.add(outputted_pic[i][1]);
                outputted_pic[i][2] += 1;
            }
        }
        return array;
    }

    public void subfunc_challenge3_makesound(ArrayList<Integer> tmp_check){
        //乱数作成
        Random rnd = new Random();
        int sound_tmpnum, pic_tmpnum;
        //場所を決める
        do {
            pic_tmpnum = rnd.nextInt(5);
        }while (outputted_pic[pic_tmpnum][0] != 0);
        //音を決める
        do {
            sound_tmpnum = rnd.nextInt(12);
        }while (tmp_check.contains(sound_tmpnum));
        //配列に格納
        outputted_pic[pic_tmpnum][0] = 1;
        outputted_pic[pic_tmpnum][1] = sound_tmpnum;
        outputted_pic[pic_tmpnum][2] = 1;
        //画面に出力
        ImageView imageView = (ImageView)findViewById(getResources().getIdentifier("image_challenge_" + Integer.toString(pic_tmpnum + 1), "id", getPackageName()));
        imageView.setImageResource(R.drawable.girl_sleep);
        //音の出力
        outputted_pic[pic_tmpnum][3] = soundPool.play(soundIds[sound_tmpnum],1.0f,1.0f,0,100,1.0f);
    }

    public void subfunc_challenge4_timepass(){
        for(int i = 0; i < outputted_pic.length; i++){
            if(outputted_pic[i][2] > waitcount){
                //正しい音が入力されなかった
                //画面反映
                ImageView imageView = (ImageView)findViewById(getResources().getIdentifier("image_challenge_" + Integer.toString(i+1), "id", getPackageName()));
                imageView.setImageResource(R.drawable.girl_ng);
                //音を止める
                soundPool.stop(outputted_pic[i][3]);
                //配列初期化
                Arrays.fill(outputted_pic[i],-1);
            }
        }
    }

    public boolean subfunc_challenge5_fincheck(){
        for(int i = 0; i < outputted_pic.length; i++){
            if(outputted_pic[i][0] != -1){
                //まだマスが閉じられていない
                return false;
            }
        }
        return true;
    }

    public void subfunc_challenge6_anscheck(int tmp){
        for(int i = 0; i < 5; i++){
            if(outputted_pic[i][1] == tmp){
                //入力はi番目で正解だった
                //画面反映
                ImageView imageView = (ImageView)findViewById(getResources().getIdentifier("image_challenge_" + Integer.toString(i+1), "id", getPackageName()));
                imageView.setImageResource(R.drawable.girl_ok);
                //結果に追加
                result[2] += 10;
                //音を止める
                soundPool.stop(outputted_pic[i][3]);
                //配列初期化
                Arrays.fill(outputted_pic[i],-1);
                outputted_pic[i][0] = 0;
            }
        }
    }

    public void subfunc_challenge7_finActivity(){
        mHandler.removeCallbacks(updatePicAndSound);
        for(int i = 0; i < 5; i++){
            if(outputted_pic[i][3] != -1){
                soundPool.stop(outputted_pic[i][3]);
            }
        }
        load_check = false;
        soundPool.release();
        soundPool = null;
    }
}
