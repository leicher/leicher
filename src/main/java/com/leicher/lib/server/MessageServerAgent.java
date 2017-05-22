package com.leicher.lib.server;

import com.leicher.lib.manage.SocketThread;

import java.net.Socket;

/**
 * Created by Administrator on 2017/5/22.
 */

public class MessageServerAgent implements SocketThread {

    private Socket socket;

    public MessageServerAgent(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {

    }

    @Override
    public boolean isClosed() {
        return false;
    }

    @Override
    public boolean shutDown() {
        return false;
    }

    @Override
    public boolean shutDownNow() {
        return false;
    }

    @Override
    public void run() {

    }


}
