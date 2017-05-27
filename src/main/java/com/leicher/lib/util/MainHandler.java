package com.leicher.lib.util;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by Administrator on 2017/3/31.
 */

public class MainHandler extends Handler {


    private static MainHandler handler ;

    public static MainHandler getHandler() {
        if (null == handler){
            synchronized (MainHandler.class){
                if (handler == null){
                    handler = new MainHandler();
                }
            }
        }
        return handler;
    }

    private MainHandler() {
        super(Looper.getMainLooper());
    }
}
