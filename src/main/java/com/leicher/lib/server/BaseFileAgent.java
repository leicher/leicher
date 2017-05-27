package com.leicher.lib.server;

import com.leicher.lib.manage.BaseSocket;
import com.leicher.lib.manage.Msg;
import com.leicher.lib.util.CloseUtil;
import com.leicher.lib.util.IdUtil;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Administrator on 2017/5/27.
 */

public abstract class BaseFileAgent extends BaseSocket{

    private Socket socket;

    protected DataOutputStream dos ;

    public BaseFileAgent(Socket socket) {
        this.socket = socket;
    }

    public abstract void write(File file);

    @Override
    public void write(Msg msg) {}

    @Override
    protected boolean createId() {
        return true;
    }


    @Override
    public void onCreate() {
        try {
            dos = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
            CloseUtil.close(dos);
        }
    }

    @Override
    public void onDestroy() {
        if (isClosed()){
            CloseUtil.close(dos);
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
    public void run() {}




}
