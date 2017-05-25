package com.leicher.lib.manage;

import static com.leicher.lib.manage.MsgType.*;
/**
 * Created by Administrator on 2017/5/23.
 */

public class Receive {

    private BaseSocket baseSocket;


    private Receive(BaseSocket baseSocket) {
        this.baseSocket = baseSocket;
    }

    public static Receive get(BaseSocket baseSocket){
        return new Receive(baseSocket);
    }

    public void receive(Msg msg){
        switch (msg.getType()){
            case MAX_TYPE:
                break;
            case TYPE_ID:
                baseSocket.setId(msg.getId());
                break;
        }



    }


}
