package com.leicher.lib.server;


import com.leicher.lib.manage.SocketThread;
import com.leicher.lib.util.CloseUtil;
import com.leicher.lib.util.Constants;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2017/5/16.
 */

public class FileServerSocket implements SocketThread {

    private ServerSocket socket;


    private String mIp;

    public FileServerSocket(String ip) {
        this.mIp = ip;
    }


    @Override
    public void run() {
        if (!isClosed()){
            try {
                socket.accept();








            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onCreate() {
        try {
            socket = new ServerSocket(Constants.FILE_PORT);
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
    public int id() {
        return 0;
    }


}
