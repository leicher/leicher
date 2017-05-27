package com.leicher.lib.client;

import com.leicher.lib.manage.BaseSocket;
import com.leicher.lib.manage.Msg;
import com.leicher.lib.util.CloseUtil;
import com.leicher.lib.util.Constants;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by Administrator on 2017/5/16.
 */

public class FileSocket extends BaseSocket{


    private String ip;

    private Socket socket;


    private DataInputStream dis ;

    private File save;

    public FileSocket(String ip) {
        this.ip = ip;
    }

    public FileSocket(String ip, File save) {
        this.ip = ip;
        this.save = save;
    }

    @Override
    public void onCreate() {
        socket = new Socket();
    }

    @Override
    public void onDestroy() {
        if (!isClosed())
            CloseUtil.close(socket);
    }

    @Override
    public boolean isClosed() {
        return socket == null || socket.isClosed();
    }

    @Override
    public int id() {
        return 0;
    }

    @Override
    public void write(Msg msg) {}

    @Override
    protected boolean createId() {
        return false;
    }

    @Override
    public void run() {
        if (!isClosed()){
            if (save != null ) {
                FileOutputStream fos = null;
                try {
                    if (!save.exists() || save.isDirectory())
                        save.createNewFile();
                    socket.setKeepAlive(true);
                    socket.connect(new InetSocketAddress(ip, Constants.FILE_PORT));
                    dis = new DataInputStream(socket.getInputStream());
                    fos = new FileOutputStream(save);
                    copy(dis,fos);
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    CloseUtil.close(fos,dis);
                }
            }
        }
    }
}
