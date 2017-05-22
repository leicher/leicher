package com.leicher.lib.manage;

/**
 * Created by Administrator on 2017/5/17.
 */

public interface SocketThread extends Runnable {

    void onCreate();

    void onDestroy();

    boolean isClosed();

    boolean shutDown();

    boolean shutDownNow();

    int id();

}
