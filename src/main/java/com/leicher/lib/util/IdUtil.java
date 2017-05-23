package com.leicher.lib.util;

import java.util.Vector;

/**
 * Created by Administrator on 2017/5/23.
 */

public class IdUtil {



    private IdUtil(){}

    private volatile static int id = 0;

    public synchronized static int createId(){
        id ++;
        ids.add(id);
        return id ;
    }

    private static Vector<Integer> ids = new Vector<>();

    public synchronized static void destroyId(int id){
        ids.remove(Integer.valueOf(id));
    }

}
