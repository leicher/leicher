package com.leicher.lib.util;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Administrator on 2017/5/16.
 */

public class CloseUtil {

    private CloseUtil() {
        throw new RuntimeException("this class can not be initialized");
    }

    public static void close(Closeable... closeables){
        for (Closeable c : closeables){
            try {
                if (c != null) {
                    c.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void closeSocket(Socket... sockets){
        for (Socket s:sockets){
            try {
                if (s != null && !s.isClosed()){
                    s.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void closeServerSocket(ServerSocket... sockets){
        for (ServerSocket s:sockets){
            try {
                if (s != null && !s.isClosed()){
                    s.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
