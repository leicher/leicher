package com.leicher.lib.client;

import com.leicher.lib.manage.BaseSocket;
import com.leicher.lib.manage.Msg;
import com.leicher.lib.manage.Receive;
import com.leicher.lib.manage.SocketThread;
import com.leicher.lib.util.CloseUtil;
import com.leicher.lib.util.Constants;
import com.leicher.lib.util.IdUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by Administrator on 2017/5/16.
 */

public class MessageSocket extends BaseSocket {

    private String ip;


    private Socket socket;

    private Receive receive;


    private DataOutputStream dos ;

    public MessageSocket(String ip) {
        this.ip = ip;
    }

    @Override
    public void onCreate() {
        socket = new Socket();
        receive = Receive.get(this);
    }



    @Override
    public void onDestroy() {
        if (!isClosed()){
            CloseUtil.close(socket);
        }
        if (createId()){
            IdUtil.destroyId(id());
        }

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
        if (dos == null){
            try {
                dos = new DataOutputStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (dos != null && !isClosed() && msg != null){
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
        DataInputStream dis = null;
        try {
            socket.setKeepAlive(true);
            socket.setSoTimeout(5000);
            socket.connect(new InetSocketAddress(ip,Constants.MSG_PORT));
            dis = new DataInputStream(socket.getInputStream());
            read(dis,receive);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            CloseUtil.close(socket);
        }
    }

    @Override
    protected boolean createId() {
        return false;
    }
}
