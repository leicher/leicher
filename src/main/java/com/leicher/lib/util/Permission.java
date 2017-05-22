package com.leicher.lib.util;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

/**
 * Created by Administrator on 2017/5/17.
 */

public class Permission {



    public static boolean checkSelfPermission(Context context,String permission){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(context,permission) == PackageManager.PERMISSION_GRANTED;
        }
//        PackageManager packageManager = context.getPackageManager();
//        return packageManager.checkPermission(permission,context.getPackageName()) == PackageManager.PERMISSION_GRANTED;
        return true;
    }

    public static void requestPermission(@NonNull Activity activity, @NonNull String[] permissions, int requestCode){
        ActivityCompat.requestPermissions(activity,permissions,requestCode);
    }


}
