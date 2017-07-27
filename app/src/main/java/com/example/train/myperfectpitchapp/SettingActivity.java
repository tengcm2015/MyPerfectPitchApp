package com.example.train.myperfectpitchapp;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.TypefaceProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by train on 2017/07/10.
 */

public class SettingActivity extends MainActivity implements View.OnClickListener{

    UtilCommon common;
    int[] tmp_mode = new int[2];
    List<Integer> sound_buttons = new ArrayList<Integer>(){};
    int max_sound;
    Drawable defaultBackGround;
    Drawable pushedBackGround;
    String[] Language;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        TypefaceProvider.registerDefaultIconSets();
        setContentView(R.layout.activity_setting);

        //とりあえずデフォをゲット
        common = (UtilCommon)getApplication();
        tmp_mode = common.getGlobal();

        // 文字列言語の取得
        Language = getResources().getStringArray(R.array.language);
        max_sound = Language.length;

        //音設定ボタンのセット
        for(int i = 0; i < max_sound; i++){
            sound_buttons.add(getResources().getIdentifier("button_setting_sound_" + Integer.toString(i+1), "id", getPackageName()));
            Button button = (Button)findViewById(sound_buttons.get(sound_buttons.size() - 1));
            if(i == 0){
                //ボタン背景の作成
                defaultBackGround = button.getBackground().getConstantState().newDrawable().mutate();
                pushedBackGround = defaultBackGround.getConstantState().newDrawable().mutate();
                pushedBackGround.setColorFilter(Color.CYAN, PorterDuff.Mode.SRC_OVER);

            }
            button.setOnClickListener(this);
        }

        //リセット・決定・戻るボタンのセット
        BootstrapButton resetButton = (BootstrapButton)findViewById(R.id.button_setting_reset);
        BootstrapButton decideButton = (BootstrapButton)findViewById(R.id.button_setting_decide);
        BootstrapButton backButton = (BootstrapButton)findViewById(R.id.button_setting_back);
        resetButton.setOnClickListener(this);
        decideButton.setOnClickListener(this);
        backButton.setOnClickListener(this);

        //デフォルトボタンの背景変更
        change_background(tmp_mode[1]);
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.button_setting_back:
                finish();
                break;
            case R.id.button_setting_reset:
                tmp_mode[0] = tmp_mode[1] = 0;
                change_background(0);
            case R.id.button_setting_decide:
                common.setGlobal(tmp_mode);
                //決定したっていうトーストを出す
                Toast.makeText(this, "Sound : " + Language[tmp_mode[1]], Toast.LENGTH_SHORT).show();
                break;

            //音設定ボタンだった場合
            default:
                for(int i = 0; i < sound_buttons.size(); i++){
                    if(sound_buttons.get(i) == view.getId()){
                        change_background(i);
                        break;
                    }
                }
                break;
        }
    }

    public void change_background(int after){
        //現状soundのみ対応
        //前回のをデフォ背景に戻す
        Button button2 = (Button)findViewById(sound_buttons.get(tmp_mode[1]));
        button2.setBackground(defaultBackGround);
        //今回の背景を変える
        Button button3 = (Button)findViewById(sound_buttons.get(after));
        button3.setBackground(pushedBackGround);
        //値を変える
        tmp_mode[1] = after;
    }
}
