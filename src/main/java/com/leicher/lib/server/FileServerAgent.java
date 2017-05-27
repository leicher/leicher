package com.leicher.lib.server;

import com.leicher.lib.util.CloseUtil;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.Socket;

/**
 * Created by Administrator on 2017/5/27.
 */

public class FileServerAgent extends BaseFileAgent{


    private Socket socket;


    public FileServerAgent(Socket socket) {
        super(socket);
        this.socket = socket;
    }

    @Override
    public synchronized void write(File file) {
        if (isClosed()) return;
        if (dos  == null){
            try {
                dos = new DataOutputStream(socket.getOutputStream());
            } catch (Exception e) {
                e.printStackTrace();
                CloseUtil.close(dos);
                CloseUtil.close(socket);
            }
        }
        if (file == null || !file.exists()) return;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            copy(fis,dos);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            CloseUtil.close(fis,dos);
        }
    }

}
