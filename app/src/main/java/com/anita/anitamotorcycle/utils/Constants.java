package com.anita.anitamotorcycle.utils;

import android.os.Environment;

import com.anita.anitamotorcycle.helps.UserHelper;

/**
 * @author weizhen
 * @description:
 * @date : 2019/10/14 22:57
 */
public class Constants {
    public static final String SD_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/MotorData/";//sd路径
    public static final int MESSAGE_COUNTDOWNTIME = 60; //注册时短信验证码发送倒计时
    public static final String BASEURL = "http://192.168.0.100:8080";   //服务器地址
//    public static final String BASEURL = "http://172.20.10.5:8080";   //服务器地址
    public static final String SHAREDPREFERENCES_NAME = "user";
//    public static final String SHAREDPREFERENCES_MOTORNAME = UserHelper.getInstance().getPhone();

    public static final String INTENT_EXTRA_KEY_QR_SCAN = "qr_scan_result";

}
