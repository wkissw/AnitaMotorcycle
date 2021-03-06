package com.anita.anitamotorcycle.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

/**
 * @author weizhen
 * @description:权限工具类
 * @date : 2019/10/13 9:33
 */
public class PermissionUtils {
    private static final String TAG = "PermissionUtils";

    /**
     * 动态获取相机权限
     *
     * @param activity
     */
    public static void getCameraPermission(Activity activity) {
        //        sd卡是否可用
        if (!hasSdcard()) {
            return;
        }
        Log.d(TAG, "getCameraPermission: 动态获取相机权限");
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }

    /**
     * 动态获取定位权限
     *
     * @param activity
     */
    public static void getGPSPermission(Activity activity) {
        Log.d(TAG, "getGPSPermission: 动态获取定位权限");
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            //未开启定位权限
            //开启定位权限,200是标识码
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
        } else {
            Log.d(TAG, "getGPSPermission: 已开启定位权限");
        }

    }

    /**
     * 动态获取定位权限
     *
     * @param activity
     */
    public static boolean getGPSPermission1(Activity activity) {
        Log.d(TAG, "getGPSPermission1: 动态获取定位权限");
        int per1 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);
        int per2 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        int per3 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
        int per4 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int per5 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (per1 != PackageManager.PERMISSION_GRANTED || per2 != PackageManager.PERMISSION_GRANTED || per3 != PackageManager.PERMISSION_GRANTED || per4 != PackageManager.PERMISSION_GRANTED || per5 != PackageManager.PERMISSION_GRANTED) {
            //请求权限
            Log.d(TAG, "getGPSPermission1: 请求权限");
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
            return false;
        }else{
            Log.d(TAG, "getGPSPermission1: 已有权限");
            return true;
        }

    }
    /**
     * 动态获取扫描二维码所需权限
     *
     * @param activity
     */
    public static boolean getQRCodePermission(Activity activity) {
        Log.d(TAG, "getQRCodePermission: 动态获取二维码扫描权限");
        int per1 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        int per2 = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (per1 != PackageManager.PERMISSION_GRANTED || per2 != PackageManager.PERMISSION_GRANTED) {
            //请求权限
            Log.d(TAG, "getQRCodePermission: 请求权限");
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, 200);
            return false;
        }else{
            Log.d(TAG, "getQRCodePermission: 已有权限");
            return true;
        }

    }

    /**
     * 检查sd卡是否可用
     * 动态获取sd卡写入权限
     *
     * @param activity
     */
    public static void getSDCardReadPermission(Activity activity) {
        //        sd卡是否可用
        if (!hasSdcard()) {
            return;
        }
        Log.d(TAG, "getCameraPermission: 动态获取读取相册权限");
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        }
    }


    /**
     * 开启手机相机程序，获取并返回图片
     *
     * @param activity
     */
    public static void pickImageFromCamera(Activity activity) {

        try {
//        开启相机应用程序获取并返回图片
            Intent intent = new Intent();
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
//            调用activity的onActivityResult()方法，返回存放返回数据的Intent和requestCode
            activity.startActivityForResult(intent, 1);
        } catch (Exception e) {
            Toast.makeText(activity, "相机无法启动，请到手机系统设置中开启相机权限", Toast.LENGTH_LONG).show();
        }

    }

    /**
     * 手机sd卡是否可用
     *
     * @return
     */
    public static boolean hasSdcard() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 有存储的SDCard
            Log.d(TAG, "hasSdcard: MEDIA_MOUNTED sd卡已挂载，可用");
            return true;
        } else {
            Log.d(TAG, "hasSdcard: sd卡不可用 state==\"+ state");
            return false;
        }
    }

    /**
     * 打开手机相册，获取并返回图片
     *
     * @param activity
     */
    public static void pickImageFromAlbum(final Activity activity) {
        Log.d(TAG, "pickImageFromAlbum: 打开手机相册");
        Intent intent1 = new Intent(Intent.ACTION_PICK, null);//返回被选中项的URI
        intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");//得到所有图片的URI
        activity.startActivityForResult(intent1, 2);
    }
}

