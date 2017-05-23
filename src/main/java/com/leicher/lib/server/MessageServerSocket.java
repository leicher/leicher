package com.leicher.lib.server;

import com.leicher.lib.manage.BaseSocket;
import com.leicher.lib.manage.SocketManager;
import com.leicher.lib.manage.SocketThread;
import com.leicher.lib.util.CloseUtil;
import com.leicher.lib.util.Constants;
import com.leicher.lib.util.IdUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2017/5/16.
 */

public class MessageServerSocket extends BaseSocket implements SocketThread{

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
        IdUtil.destroyId(id());
    }

    @Override
    public boolean isClosed() {
        return socket == null || socket.isClosed();
    }

    @Override
    public void shutDown() {
        setIntercept(true);
        manager.remove(this);
    }

    @Override
    public int id() {
        return id;
    }

    @Override
    public void run() {
        if (!isClosed()){
            while (!isIntercept()){
                try {
                    MessageServerAgent agent = new MessageServerAgent(socket.accept());
                    SocketManager.init().put(agent.id(),agent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
