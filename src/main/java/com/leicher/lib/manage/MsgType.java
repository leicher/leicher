package com.leicher.lib.manage;

/**
 * Created by Administrator on 2017/5/23.
 */

public interface MsgType {

    int MIN_TYPE = 0x10100;

    int MAX_TYPE = 0x10F00;

    /**
     * client and server send their id,this id only one at server
     */
    int TYPE_ID = 0x10101;




}
