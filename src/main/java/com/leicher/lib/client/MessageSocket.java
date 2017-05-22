package com.leicher.lib.client;

import com.leicher.lib.manage.SocketThread;
import com.leicher.lib.util.CloseUtil;
import com.leicher.lib.util.Constants;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by Administrator on 2017/5/16.
 */

public class MessageSocket implements SocketThread{

    private String ip;


    private Socket socket;

    public MessageSocket(String ip) {
        this.ip = ip;
    }

    @Override
    public void onCreate() {
        socket = new Socket();
    }



    @Override
    public void onDestroy() {
        if (!isClosed()){
            CloseUtil.closeSocket(socket);
        }
    }

    @Override
    public boolean isClosed() {
        return socket == null || socket.isClosed();
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
