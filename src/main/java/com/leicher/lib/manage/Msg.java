package com.leicher.lib.manage;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/22.
 */

public class Msg implements Serializable{

    private int id;

    private int size;

    private int type;

    private String data;

    public Msg(int type) {
        this.type = type;
    }



    public Msg(int type, int id ) {
        this.type = type;
        this.id = id;
    }

    public Msg(int id, int size, int type) {
        this.id = id;
        this.size = size;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "[id=" + id +",type=" + type + ",size=" +
                size + ",data=" + data + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Msg)) return false;

        Msg msg = (Msg) o;

        if (size != msg.size) return false;
        if (id != msg.id) return false;
        return type == msg.type;

    }

}
