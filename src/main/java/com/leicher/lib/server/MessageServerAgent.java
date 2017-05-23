package com.leicher.lib.server;

import com.leicher.lib.manage.BaseSocket;
import com.leicher.lib.manage.SocketManager;
import com.leicher.lib.manage.SocketThread;
import com.leicher.lib.util.IdUtil;

import java.net.Socket;

/**
 * Created by Administrator on 2017/5/22.
 */

public class MessageServerAgent extends BaseSocket implements SocketThread {

    private Socket socket;

    public MessageServerAgent(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
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

    }


}
