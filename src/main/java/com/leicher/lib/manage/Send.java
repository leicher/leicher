package com.leicher.lib.manage;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/5/22.
 */

public class Send {

    private Send() {
    }

    private static SocketManager manager = SocketManager.init();

    private static ExecutorService executor = Executors.newCachedThreadPool();

    public static void send(final int id , final Msg msg){
        executor.submit(new Runnable() {
            @Override
            public void run() {
                SocketThread socket = manager.get(id);
                socket.write(msg);
            }
        });
    }


}
