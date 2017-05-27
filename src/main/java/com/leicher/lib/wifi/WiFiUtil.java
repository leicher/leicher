package com.leicher.lib.wifi;

import android.Manifest;
import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;

import com.leicher.lib.util.Permission;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Administrator on 2017/5/17.
 */

public class WiFiUtil {

    private WiFiUtil() {
        throw new RuntimeException("this class can not be initialized");
    }

    public static WifiManager wifiManager(Context context){
        return (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
    }

    public static boolean connectWiFi(Context context,String name){
        WifiManager wifiManager = wifiManager(context);
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        if (wifiInfo != null && wifiInfo.getSSID() != null){
            String ssid = wifiInfo.getSSID();
            ssid = ssid.replace("\"", "");
            if (ssid.equals(name)){
                return true;
            }
        }
        final WifiConfiguration wifiConfig = getSaveWifConfiguration(wifiManager, name);
        if (wifiConfig == null) {
            return false;
        }
        wifiManager.disconnect();
        int netWorkId = wifiConfig.networkId;
        if (netWorkId == -1) {
            netWorkId = wifiManager.addNetwork(wifiConfig);
            if (netWorkId != -1) {
                Method connectMethod = connectWifi(wifiManager,netWorkId);
                if (connectMethod == null) {
                    return wifiManager.enableNetwork(wifiConfig.networkId, true);
                }
            }
        } else {
            Method connectMethod = connectWifi(wifiManager,netWorkId);
            if (connectMethod == null) {
                return wifiManager.enableNetwork(wifiConfig.networkId, true);
            }
        }
        return true;
    }

    public static Method connectWifi(WifiManager wifiManager, int netId) {
        Method connectMethod = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            for (Method method : wifiManager.getClass().getDeclaredMethods()) {
                if ("connect".equalsIgnoreCase(method.getName())) {
                    Class<?>[] types = method.getParameterTypes();
                    if (types != null && types.length > 0) {
                        if ("int".equalsIgnoreCase(types[0].getName())) {
                            connectMethod = method;
                        }
                    }
                }
            }
            if (connectMethod != null) {
                try {
                    connectMethod.invoke(wifiManager, netId, null);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN) {
            // 反射方法: connect(Channel c, int networkId, ActionListener listener)
            // 暂时不处理4.1的情况 , 4.1 == phone's android version
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            // 反射方法：connectNetwork(int networkId) ,
            // 4.0 <= phone's android version < 4.1
            for (Method methodSub : wifiManager.getClass()
                    .getDeclaredMethods()) {
                if ("connectNetwork".equalsIgnoreCase(methodSub.getName())) {
                    Class<?>[] types = methodSub.getParameterTypes();
                    if (types != null && types.length > 0) {
                        if ("int".equalsIgnoreCase(types[0].getName())) {
                            connectMethod = methodSub;
                        }
                    }
                }
            }
            if (connectMethod != null) {
                try {
                    connectMethod.invoke(wifiManager, netId);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        } else {
            return null;
        }
        return connectMethod;
    }

    public static WifiConfiguration getSaveWifConfiguration(WifiManager wifiManager,String name) {
        WifiConfiguration wifiConfiguration = null;
        List<WifiConfiguration> configuredNetworks = wifiManager.getConfiguredNetworks();
        if (configuredNetworks != null) {
            for (WifiConfiguration configuration : configuredNetworks) {
                if (configuration.SSID.replace("\"", "").equals(name.replace("\"", ""))) {
                    wifiConfiguration = configuration;
                    break;
                }
            }
        }
        return wifiConfiguration;
    }

    public static void removeNetwork(Context context,String name){
        WifiManager wifiManager = wifiManager(context);
        if(Permission.checkSelfPermission(context, Manifest.permission.CHANGE_WIFI_STATE)
                && Permission.checkSelfPermission(context,Manifest.permission.ACCESS_WIFI_STATE)) {
            if (!wifiManager.isWifiEnabled()) {
                wifiManager.setWifiEnabled(true);
            }
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if (wifiInfo != null && wifiInfo.getSSID() != null) {
                String ssid = wifiInfo.getSSID();
                if (ssid.equals(name)) {
                    wifiManager.disconnect();
                }
            }
            WifiConfiguration configuredNetworks = getSaveWifConfiguration(wifiManager, name);
            if (configuredNetworks != null) {
                wifiManager.removeNetwork(wifiManager.addNetwork(configuredNetworks));
            }
        }
    }



}
