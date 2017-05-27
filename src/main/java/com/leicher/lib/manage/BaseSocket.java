package com.leicher.lib.manage;

import android.text.TextUtils;

import com.leicher.lib.util.IdUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2017/5/23.
 */

public abstract class BaseSocket implements MsgType,SocketThread{

    protected int id;

    protected SocketManager manager;


    public void setId(int id) {
        this.id = id;
    }

    public BaseSocket() {
        if (createId()) {
            id = IdUtil.createId();
        }
        manager = SocketManager.init();
    }

    protected abstract boolean createId();

    private boolean intercept  = false;

    public boolean isIntercept() {
        return intercept;
    }

    public void setIntercept(boolean intercept) {
        this.intercept = intercept;
    }

    /**
     * byte数组中取int数值，本方法适用于(低位在后，高位在前)的顺序
     */
    public static int bytesToInt(byte[] src, int offset) {
        int value;
        value = (int) ( ((src[offset] & 0xFF)<<24)
                |((src[offset+1] & 0xFF)<<16)
                |((src[offset+2] & 0xFF)<<8)
                |(src[offset+3] & 0xFF));
        return value;
    }

    protected Msg obtain(byte[] heard){
        int id = bytesToInt(heard,0);
        int type = bytesToInt(heard, 4);
        int size = bytesToInt(heard, 8);
        return new Msg(id,size,type);
    }

    protected boolean validType(int type){
        return type >= MsgType.MIN_TYPE && type <= MsgType.MAX_TYPE;
    }

    protected void read(DataInputStream dis,Receive receive) throws Exception{
        byte[] heard = new byte[12];
        byte[] data;
        Msg msg = null;
        while (!isIntercept()){
            data = null;
            int len = dis.read(heard);
            if ( len <= 0 ){
                setIntercept(true);
                break;
            }
            msg = obtain(heard);
            if (!validType(msg.getType())){
                setIntercept(true);
                break;
            }
            int size = msg.getSize();
            if (size > 0 ) {
                data = new byte[size];
                dis.read(data);
                msg.setData(new String(data,"UTF-8"));
            }
            receive.receive(msg);
        }
    }

    @Override
    public void shutDown() {
        setIntercept(true);
        manager.remove(this);
    }



    protected void write(Msg msg, DataOutputStream dos) throws Exception{
        byte[] datas = null;
        String data = msg.getData();
        if (TextUtils.isEmpty(data)){
            msg.setSize(0);
        }else {
            datas = data.getBytes("UTF-8");
            msg.setSize(datas.length);
        }
        dos.write(msg.getId());
        dos.write(msg.getType());
        dos.write(msg.getSize());
        if (datas != null){
            dos.write(datas);
        }
        dos.flush();
    }


    protected void copy(InputStream is, OutputStream os) throws Exception{
        int len;
        byte buf[] = new byte[BUFFER_SIZE];
        while (-1 != (len = is.read(buf))){
            os.write(buf,0,len);
        }
        os.flush();
    }


}
