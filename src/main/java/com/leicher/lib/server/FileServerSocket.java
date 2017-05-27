package com.leicher.lib.server;

import com.leicher.lib.manage.BaseSocket;
import com.leicher.lib.manage.Msg;
import com.leicher.lib.manage.SocketThread;
import com.leicher.lib.util.CloseUtil;
import com.leicher.lib.util.Constants;
import com.leicher.lib.util.IdUtil;

import java.io.IOException;
import java.net.ServerSocket;


/**
 * Created by Administrator on 2017/5/16.
 */

public class FileServerSocket extends BaseSocket {

    private ServerSocket socket;


    private String mIp;

    public FileServerSocket(String ip) {
        this.mIp = ip;
    }


    @Override
    public void run() {
        if (!isClosed()){
            FileServerAgent agent = null;
            while (!isIntercept()) {
                try {
                    agent = new FileServerAgent(socket.accept());
                    manager.put(agent.id(),agent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onCreate() {
        try {
            socket = new ServerSocket(Constants.FILE_PORT);
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
    public void write(Msg msg) {}


    @Override
    protected boolean createId() {
        return true;
    }
}
