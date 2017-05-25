package com.leicher.lib.util;

/**
 * Created by Administrator on 2017/5/23.
 */

public class Tools {

    private Tools() {}


    public static boolean isNull(String src){
        return src == null || "".equals(src) || "null".equals(src);
    }

    public static boolean notNull(String src){
        return src != null && !"".equals(src) && !"null".equals(src);
    }


}
