package com.leicher.lib.file;

import com.leicher.lib.util.CharSet;
import com.leicher.lib.util.CloseUtil;
import com.leicher.lib.util.PLog;

import static com.leicher.lib.util.PLog.isDebug;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by leicher on 2017/5/16.
 */

public class FileUtil {

    private static final String TAG = "FileUtil";

    private FileUtil() {
        throw new RuntimeException("this class can not be initialized");
    }

    private static final int BUFFER_SIZE = 4096;
    private static final int MAX_BUFFER = 1024 * 1024 * 4;



    /**
     * 以 src(文件或文件夹) 的文件名为名复制到 path 目录下 ,两者皆保存
     * @param src
     * @param path
     */
    public static void copy( File src , String path ){
        if (src == null || !src.exists()  || path == null) {
            if (isDebug())
                PLog.i(TAG,"this params of method is illegal");
            return;
        }
        if (src.isFile()) {

            String name = src.getName();
            File file = new File(path);
            if (file.exists() || file.mkdirs()) {
                file = new File(path, name);
                try {
                    boolean exists = file.exists() || file.createNewFile();
                    if (exists && file.canWrite() && src.canRead()) {
                        try {
                            copy(new FileInputStream(src), new FileOutputStream(file));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }
                        return;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }else if (src.isDirectory()){
            move(src,path,true,false);
            return;
        }
        if (isDebug()) {
            throw new IllegalArgumentException("this path is unavailable");
        }
    }

    /**
     * 读取 文件输入流 到 文件输出流
     * @param fis
     * @param fos
     */
    public static void copy(FileInputStream fis,FileOutputStream fos){
        copy(new DataInputStream(fis),new DataOutputStream(fos));
    }

    /**
     * 读取 输入流 到 输出流
     * @param in
     * @param out
     */
    public static void copy(DataInputStream in, DataOutputStream out){
        if (in == null || out == null) return;
        int len;
        byte[] buf = new byte[BUFFER_SIZE];
        try {
            while (-1 != (len = in.read(buf))){
                out.write(buf,0,len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            CloseUtil.close(in,out);
        }
    }

    /**
     * 复制 src 文件到 des 文件
     * @param src
     * @param des
     */
    public static void copy(File src ,File des){
        if (src == null || !src.exists() || !src.isFile() || des == null ){
            if (isDebug())
                throw new IllegalArgumentException("this src file is not exists");
            return;
        }
        try {
            if (des.exists() || des.createNewFile()) {
                copy(new FileInputStream(src), new FileOutputStream(des));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 在 path 文件夹下 把 src文件 重命名为 des
     * @param path
     * @param src
     * @param des
     */
    public static void rename(String path , String src ,String des){
        if (path == null || src == null || des == null){
            if (isDebug())
                throw new IllegalArgumentException("this params of method can not be null");
            return;
        }
        rename(new File(path,src),des);
    }

    /**
     * 把 src 文件重命名为 des,同一文件下，若需要不同文件夹，请调用 copy 方法
     * @param src
     * @param des
     */
    public static void rename(File src , String des){
        if (src == null || !src.exists() || !src.isFile() || des == null){
            if (isDebug())
                throw new IllegalArgumentException("this params of method can not be null");
            return;
        }
        try {
            File desFile = new File(src.getParent(), des);
            if ( desFile.exists() || desFile.createNewFile() ){
                src.renameTo(desFile);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除一个文件或者文件夹
     * @param file
     */
    public static void delete(File file){
        if(file != null && file.exists()){
            if (file.isFile())
                file.delete();
            else{
                File[] files = file.listFiles();
                for (File f : files){
                    delete(f);
                }
                file.delete();
            }
        }
    }

    /**
     * 删除一个 path 目录下 名为 name 的文件
     * @param path
     * @param name
     */
    public static void delete( String path , String name){
        if (path == null || name == null) {
            if (isDebug())
                throw new IllegalArgumentException("this params of method can not be null");
            return;
        }
        delete(new File(path,name));
    }

    /**
     * 删除一个目录
     * @param path
     */
    public static void delete(String path){
        if (path == null){
            if (isDebug())
                throw new IllegalArgumentException("this params of method can not be null");
            return;
        }
        delete(new File(path));
    }

    /**
     * 创建一个目录
     * @param path
     */
    public static boolean create(String path){
        File file = new File(path);
        if (!file.exists()){
            return file.mkdirs();
        }
        return true;
    }


    /**
     * 创建一个文件
     * @param path
     * @param name
     */
    public static boolean create(String path,String name){
        if (create(path)) {
            File file = new File(path, name);
            if (!file.exists()) {
                boolean newFile = false;
                try {
                    newFile = file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return newFile;
            }
            return true;
        }
        return false;
    }

    /**
     * 移动 一个 文件 或者 文件夹 到 path 目录下
     * @param src
     * @param path
     */
    public static void move(File src , String path){
        move(src,path,true,true);
    }

    /**
     * 移动一个文件 或者 文件夹 到 path 目录下
     * @param src
     * @param path
     * @param keepBlank 是否保留空文件夹
     * @param isDelete  是否删除原文件
     */
    private static void move(File src , String path ,boolean keepBlank ,boolean isDelete){
        if (src != null && src.exists() && path != null){
            if (path.contains(src.getPath()) || path.equals(src.getParent())){ //防止无线创建文件夹或者本文件夹类移动
                return;
            }
            if (src.isFile()){
                copy(src,path);
                if (isDelete)
                    delete(src);
            }else if (src.isDirectory()){
                File[] files = src.listFiles();
                String pa = getPath(path, src.getName());
                if (keepBlank) {
                    create(pa);
                }
                for (File f : files){
                    move(f , pa);
                }
                if (isDelete)
                    delete(src);
            }
        }
    }



    /**
     * 获取 移动 文件时 文件绝对路径
     * @param des
     * @param name
     * @return
     */
    private static String getPath(String des,String name){
        return des + File.separator + name;
    }

    /**
     * 将字符串写入 path 目录下的 name 文件下
     * @param data
     * @param path
     * @param name
     */
    public static void write(String data,String path,String name){
        try {
            write(data.getBytes(CharSet.UTF8.getValue()),path,name);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void write(byte[] buf ,String path ,String name){
        if(create(path,name)){
            File file = new File(path,name);
            if (file.exists() && file.canWrite()){
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(file);
                    fos.write(buf);
                    fos.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    CloseUtil.close(fos);
                }
            }
        }
    }



    /**
     * 读取 path 文件下 name 文件 ，不得超过 MAX_BUFFER
     * @param path
     * @param name
     * @return
     */
    public static String read(String path,String name){
        File file = new File(path,name);
        if (file.exists() && file.canRead()){
            FileInputStream fis = null;
            ByteArrayOutputStream bos = null;
            try {
                fis = new FileInputStream(file);
                bos = new ByteArrayOutputStream();
                int len ;
                byte[] buf = new byte[BUFFER_SIZE];
                while (-1 != (len = fis.read(buf))){
                    bos.write(buf,0,len);
                    if (bos.size() > MAX_BUFFER){
                        throw new IllegalStateException("this file is too big");
                    }
                }
                return bos.toString(CharSet.UTF8.getValue());
            }  catch (IOException e) {
                e.printStackTrace();
            }finally {
                CloseUtil.close(bos,fis);
            }
        }
        return null;
    }




}
