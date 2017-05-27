package com.leicher.lib.util;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.leicher.lib.wifi.WiFiUtil;

/**
 * Created by Administrator on 2017/5/27.
 */

public class IPUtil {

    private static final String TAG = "IPUtil";

    private IPUtil() {}


    /**
     * 获取当 连接 wifi 时本机的IP地址
     * @param context
     * @return
     */
    public static String localIp(Context context) {
        WifiManager wifiManager = WiFiUtil.wifiManager(context);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo == null){
            DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
            if (dhcpInfo == null)
                return null;
            else return intIP2StringIP(dhcpInfo.ipAddress);
        }
        return intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
    }

    /**
     * 获取 连接 wifi 时服务器的IP地址
     * @param context
     * @return
     */
    public static String serverIp(Context context){
        WifiManager wifiManager = WiFiUtil.wifiManager(context);
        DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
        if (dhcpInfo == null){
            return null;
        }
        return intIP2StringIP(dhcpInfo.serverAddress);
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }



}
