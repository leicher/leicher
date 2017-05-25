package com.leicher.lib.manage;

/**
 * Created by Administrator on 2017/5/17.
 */

public interface SocketThread extends Runnable {

    void onCreate();

    void onDestroy();

    boolean isClosed();

    void shutDown();

    int id();

    void write(Msg msg);

}
