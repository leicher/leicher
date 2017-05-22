package com.leicher.lib.manage;

import android.util.SparseArray;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by leicher on 2017/5/17.
 */

public class SocketManager {

    private SocketManager() {
        mSockets = new SparseArray<>();
        executor = Executors.newFixedThreadPool(10);
    }

    private ExecutorService executor ;


    private static SocketManager instance;

    private SparseArray<SocketThread> mSockets ;


    /**
     * 使用单例模式,获取SocketManager对象,管理Socket.
     * @return
     */
    public static SocketManager init(){
        if (instance == null){
            synchronized (SocketManager.class){
                if (instance == null){
                    instance = new SocketManager();
                }
            }
        }
        return instance;
    }

    public SocketManager put(int id , SocketThread socket){
        mSockets.put(id,socket);
        return instance;
    }


    private SocketThread get(int id){
        return mSockets.get(id);
    }

    public SocketManager remove(int id){
        mSockets.remove(id);
        return instance;
    }

    public SocketManager start(int id){
        SocketThread socket = get(id);
        if (socket != null){
            executor.submit(socket);
        }
        return instance;
    }

    public SocketManager start(int id,SocketThread socket){
        put(id,socket).start(id);
        return instance;
    }

    public SocketManager shutDown(int id){
        SocketThread socket = get(id);
        if (socket != null){
            socket.shutDown();
        }
        return instance;
    }

    public SocketManager shutDownNow(int id){
        SocketThread socket = get(id);
        if (socket != null){
            socket.shutDownNow();
        }
        return instance;
    }

    public SocketManager shutDownAll(){
        for (int i = 0 ; i < mSockets.size() ; i++){
            SocketThread socket = mSockets.valueAt(i);
            if (socket != null){
                socket.shutDownNow();
            }
            mSockets.removeAt(i);
        }
        executor.shutdown();
        return instance;
    }


}
