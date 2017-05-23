package com.leicher.lib.manage;

import com.leicher.lib.util.IdUtil;

/**
 * Created by Administrator on 2017/5/23.
 */

public class BaseSocket {

    protected int id;

    protected SocketManager manager;

    public BaseSocket() {
        id = IdUtil.createId();
        manager = SocketManager.init();
    }

    private boolean intercept  = false;

    public boolean isIntercept() {
        return intercept;
    }

    public void setIntercept(boolean intercept) {
        this.intercept = intercept;
    }
}
