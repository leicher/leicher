package com.leicher.lib.server;

import com.leicher.lib.manage.BaseSocket;
import com.leicher.lib.manage.Msg;
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

public class MessageServerSocket extends BaseSocket {

    private ServerSocket socket;


    @Override
    public void onCreate() {
        try {
            socket = new ServerSocket(Constants.MSG_PORT);
        } catch (IOException e) {
            e.printStackTrace();
            CloseUtil.close(socket);
        }
    }

    @Override
    public void onDestroy() {
        if (!isClosed()){
            CloseUtil.close(socket);
        }
        IdUtil.destroyId(id());
    }

    @Override
    public boolean isClosed() {
        return socket == null || socket.isClosed();
    }


    @Override
    public int id() {
        return id;
    }

    @Override
    public synchronized void write(Msg msg) {

    }

    @Override
    public void run() {
        if (!isClosed()){
            while (!isIntercept()){
                try {
                    MessageServerAgent agent = new MessageServerAgent(socket.accept());
                    manager.put(agent.id(),agent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    @Override
    protected boolean createId() {
        return true;
    }
}
