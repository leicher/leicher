package com.leicher.lib.server;

import com.leicher.lib.manage.BaseSocket;
import com.leicher.lib.manage.Msg;
import com.leicher.lib.manage.Receive;
import com.leicher.lib.util.CloseUtil;
import com.leicher.lib.util.IdUtil;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by Administrator on 2017/5/22.
 */

public class MessageServerAgent extends BaseSocket {

    private Socket socket;

    private Receive receive;

    private DataOutputStream dos ;

    public MessageServerAgent(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void onCreate() {
        receive = Receive.get(this);
        if (!isClosed()) {
            try {
                dos = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
                CloseUtil.close(dos);
                CloseUtil.close(socket);
            }
            write(new Msg(TYPE_ID,id()));
        }
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
    public int id() {
        return id;
    }

    @Override
    public synchronized void write(Msg msg) {
        if (!isClosed() && msg != null && dos != null){
            try {
               write(msg,dos);
            } catch (Exception e) {
                e.printStackTrace();
                CloseUtil.close(dos);
                CloseUtil.close(socket);
            }
        }
    }

    @Override
    public void run() {
        if (!isClosed()){
            DataInputStream dis = null;
            try {
                dis =  new DataInputStream(socket.getInputStream());
                read(dis,receive);
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                CloseUtil.close(dis);
            }
        }
    }


    @Override
    protected boolean createId() {
        return true;
    }
}
