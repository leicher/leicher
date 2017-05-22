package com.leicher.lib.server;

import com.leicher.lib.manage.SocketThread;
import com.leicher.lib.util.CloseUtil;
import com.leicher.lib.util.Constants;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2017/5/16.
 */

public class MessageServerSocket implements SocketThread{

    private ServerSocket socket;


    @Override
    public void onCreate() {
        try {
            socket = new ServerSocket(Constants.MSG_PORT);
        } catch (IOException e) {
            e.printStackTrace();
            CloseUtil.closeServerSocket(socket);
        }
    }

    @Override
    public void onDestroy() {
        if (!isClosed()){
            CloseUtil.closeServerSocket(socket);
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
        if (!isClosed()){
            while (true){
                try {
                    new MessageServerAgent(socket.accept());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
