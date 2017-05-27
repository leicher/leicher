package com.leicher.lib.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/3/31.
 */

public class ThreadUtils {

    private ThreadUtils() {
    }
    private static final int THREAD_POOL_SIZE = 5;

    private static ExecutorService EXECUTOR = null;

    private static void init(){
        if (EXECUTOR == null){
            synchronized (ThreadUtils.class){
                if (EXECUTOR == null){
                    EXECUTOR = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
                }
            }
        }
    }

    public static void runOnThread(Runnable runnable){
        init();
        EXECUTOR.submit(runnable);
    }

    public static void runOnUiThread(Runnable runnable){
        MainHandler.getHandler().post(runnable);
    }

    public static void runOnUiThreadDelayed(Runnable runnable,long delayMillis){
        MainHandler.getHandler().postDelayed(runnable,delayMillis);
    }
}
