package com.leicher.lib.file;

import android.os.Environment;

/**
 * Created by Administrator on 2017/5/16.
 */

public class SDCard {

    private SDCard() {
        throw new RuntimeException("this class can not be initialized");
    }

    public static boolean exists(){
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public static String pathOfSD(){
        if (exists())
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        else
            return null;
    }


}
