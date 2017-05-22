package com.leicher.lib.manage;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/22.
 */

public class Msg implements Serializable{

    private int size;

    private int type;

    private String data;

    public Msg(int type) {
        this.type = type;
    }

    public Msg(int size, int type, String data) {
        this.size = size;
        this.type = type;
        this.data = data;
    }

    public Msg() {
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "[type=" + type + ",size=" +
                size + ",data=" + data + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Msg)) return false;

        Msg msg = (Msg) o;

        if (size != msg.size) return false;
        return type == msg.type;

    }

}
