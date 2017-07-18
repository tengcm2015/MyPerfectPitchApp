package com.example.train.myperfectpitchapp;

import android.app.Application;

/**
 * Created by train on 2017/07/13.
 */

public class UtilCommon extends Application {

    public int interface_mode = 0;
    public int soundname_mode = 0;

    /**
     * アプリケーションの起動時に呼び出される
     */
    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * アプリケーション終了時に呼び出される
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    public void setGlobal(int[] tmp_mode) {
        interface_mode = tmp_mode[0];
        soundname_mode = tmp_mode[1];
    }

    public int[] getGlobal() {
        int[] result = {interface_mode,soundname_mode};
        return result;
    }
}
