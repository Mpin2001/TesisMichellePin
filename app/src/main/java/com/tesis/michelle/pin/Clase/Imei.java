package com.tesis.michelle.pin.Clase;

import static android.content.Context.TELEPHONY_SERVICE;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

public class Imei {

    public String getImei(Context context){
        String imei = "";
        try {
            int permissionResult = context.checkCallingOrSelfPermission(Manifest.permission.READ_PHONE_STATE);
            if (permissionResult == PackageManager.PERMISSION_DENIED) {
                permissionResult = context.checkCallingOrSelfPermission("android.permission.READ_PRIVILEGED_PHONE_STATE");
            }
            boolean isPermissionGranted = permissionResult == PackageManager.PERMISSION_GRANTED;

            if (!isPermissionGranted) {
                imei = "DENEGADO";
            } else {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O) {
                    TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
                    imei = tm.getDeviceId();
                    Log.i("IMEI-O", imei);
                } else {
                    TelephonyManager tm = (TelephonyManager) context.getSystemService(TELEPHONY_SERVICE);
                    if (tm != null) {
                        imei = tm.getImei();
                    }
                    Log.i("IMEI-P", imei);
                }
            }
        } catch (Exception e) {
            imei = "-";
            Log.e("IMEI EXCEPTION", e.getMessage());
        }

        return imei;
    }

//    public static String getPhoneIMEI(Context context) {
//        String deviceID = "";
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            int permissionResult = context.checkCallingOrSelfPermission(Manifest.permission.READ_PHONE_STATE);
//            if (permissionResult == PackageManager.PERMISSION_DENIED) {
//                permissionResult = context.checkCallingOrSelfPermission("android.permission.READ_PRIVILEGED_PHONE_STATE");
//            }
//            boolean isPermissionGranted = permissionResult == PackageManager.PERMISSION_GRANTED;
//            if (!isPermissionGranted) {
//                deviceID = getDeviceIDFromReflection(context);
//            } else {
//                deviceID = getDeviceIDFromSystem(context);
//            }
//        } else {
//            deviceID = getDeviceIDFromSystem(context);
//        }
//
//        Log.i("IMEI2","getPhoneIMEI : " + deviceID);
//        return deviceID;
//    }
}
