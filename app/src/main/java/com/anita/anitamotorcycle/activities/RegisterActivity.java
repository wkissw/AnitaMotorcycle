package com.anita.anitamotorcycle.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.anita.anitamotorcycle.R;
import com.anita.anitamotorcycle.helps.MotorHelper;
import com.anita.anitamotorcycle.helps.UserHelper;
import com.anita.anitamotorcycle.utils.ClientUtils;
import com.anita.anitamotorcycle.utils.Constants;
import com.anita.anitamotorcycle.utils.UserUtils;

import org.json.JSONObject;

//import cn.smssdk.EventHandler;
//import cn.smssdk.SMSSDK;
//import cn.smssdk.utils.SMSLog;

//需要先判断输入的手机号是否已被注册（后台接口实现）
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "RegisterActivity";
    int time = Constants.MESSAGE_COUNTDOWNTIME; //短信验证码发送倒计时
    private String mPhone;
    private EditText mEt_verificationCode, mEt_phone;
    private Button mBtn_getCode, mBtn_registerNext;
    private TextView mTv_sendMessage;
//    EventHandler mEventHandler;
    private ImageView mIv_back;
    private ImageView mIv_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView(); //初始化view
        initTextChanged();  //注册手机号EditText输入监听

//        MobSDK.submitPolicyGrantResult(granted, null);
//        initEventHandler(); //注册监听回调事件

    }

    /**
     * 初始化view
     */
    private void initView() {
//        后退
        mIv_back = findViewById(R.id.iv_back);
        mIv_back.setOnClickListener(this);
//        手机号、验证码输入框
        mEt_phone = findViewById(R.id.et_phone);
        mEt_verificationCode = findViewById(R.id.et_verificationCode);
        mIv_right = findViewById(R.id.iv_right);
        mIv_right.setVisibility(View.INVISIBLE);
//        获取验证码按钮
        mBtn_getCode = findViewById(R.id.btn_getCode);
        mBtn_getCode.setOnClickListener(this);
        mBtn_getCode.setEnabled(false); //未输入手机号，按钮不可点击
//        验证码提示信息
        mTv_sendMessage = findViewById(R.id.tv_sendMessage);
        mTv_sendMessage.setVisibility(View.INVISIBLE);
//        下一步按钮
        mBtn_registerNext = findViewById(R.id.btn_registerNext);
        mBtn_registerNext.setOnClickListener(this);
    }


    /**
     * 手机号输入监听
     */
    private void initTextChanged() {
        mEt_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPhone = mEt_phone.getText().toString();
                Log.d(TAG, "afterTextChanged: mEt_phone.getText().toString().length()==" + mPhone.length());
                if (mPhone.length() == 11) {
                    boolean isRegister = ClientUtils.validatePhone(mPhone);
                    if (isRegister) {
                        mIv_right.setVisibility(View.INVISIBLE);
                        Toast.makeText(getApplicationContext(), "该手机号已注册", Toast.LENGTH_LONG).show();
                    } else {
                        mBtn_getCode.setEnabled(true);
                        mIv_right.setVisibility(View.VISIBLE);
                    }
                } else {
                    mBtn_getCode.setEnabled(false);
                    mIv_right.setVisibility(View.INVISIBLE);
                }

            }
        });
    }

    public void onClick(View v) {
        switch (v.getId()) {
//            “后退”点击事件
            case R.id.iv_back:
                onBackPressed();    //后退操作
                break;
//            "获取验证码"点击事件
            case R.id.btn_getCode:
                mPhone = mEt_phone.getText().toString();
                Log.d(TAG, "onClick: 获取验证码，mPhone==" + mPhone);
                boolean result = UserUtils.validatePhone(this, mPhone);    //验证手机号
                if (!result) return;
                messageCountDown();    //倒计时
                //判断网络状态
                mTv_sendMessage.setVisibility(View.INVISIBLE);
//                SMSSDK.getVerificationCode("86", mPhone);    //请求发送验证码的服务
                Log.d(TAG, "onClick: 获取验证码，mPhone==" + mPhone);
                break;
//                "注册"按钮点击事件
            case R.id.btn_registerNext:
                Log.d(TAG, "onClick: -注册--");
                mTv_sendMessage.setVisibility(View.INVISIBLE);
                mPhone = mEt_phone.getText().toString();
                Log.d(TAG, "onClick: mPhone==" + mPhone);
                boolean validate = UserUtils.validatePhone(this, mPhone);    //验证手机号
                if (validate) {
//                    SMSSDK.submitVerificationCode("86", mPhone, mEt_verificationCode.getText().toString());  //提交验证码
                    Toast.makeText(getApplicationContext(), "验证成功", Toast.LENGTH_LONG).show();
//                  保存用户标记，在全局单例类UserHelp之中
                    UserHelper.getInstance().setPhone(mPhone);
                    startActivity(new Intent(RegisterActivity.this, SetPasswordActivity.class));    //跳转到设置登录密码界面
                }
                break;
            default:
                break;
        }
    }

//    private void initEventHandler() {
//        mEventHandler = new EventHandler() {
//            @Override
//            public void afterEvent(int event, int result, Object data) {
//                // TODO 此处不可直接处理UI线程，处理后续操作需传到主线程中操作
//                Message msg = new Message();
//                msg.arg1 = event;
//                msg.arg2 = result;
//                msg.obj = data;
//                mHandler.sendMessage(msg);
//
//            }
//        };
//        SMSSDK.registerEventHandler(mEventHandler);    //注册一个事件回调监听
//    }
/*
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;

            Log.e("event", "event==" + event + "result==" + result);
            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                Log.d(TAG, "handleMessage: --回调完成--");
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    //提交验证码成功
                    Log.d(TAG, "handleMessage: 提交验证码成功");
                    Toast.makeText(getApplicationContext(), "验证成功", Toast.LENGTH_LONG).show();
//                  保存用户标记，在全局单例类UserHelp之中
                    UserHelper.getInstance().setPhone(mPhone);
                    startActivity(new Intent(RegisterActivity.this, SetPasswordActivity.class));    //跳转到设置登录密码界面
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取验证码成功
                    Log.d(TAG, "handleMessage: 获取验证码成功，验证码已发送");
                    Toast.makeText(getApplicationContext(), "验证码已发送，请注意查收", Toast.LENGTH_SHORT).show();
                    messageCountDown();    //倒计时
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                    Log.d(TAG, "handleMessage: 获取国家列表成功");
                } else {
                    Log.d(TAG, "handleMessage: 捕捉");
                }
            } else {
                int status = 0;
                try {
                    Throwable throwable = (Throwable) data;
                    throwable.printStackTrace();
                    JSONObject object = new JSONObject(throwable.getMessage());
                    status = object.optInt("status");//错误代码
                    Log.d(TAG, "handleMessage: -回调失败--");
                    Log.d(TAG, "handleMessage: data==" + data + " status==" + status);
                } catch (Exception e) {
                    //do something
                    SMSLog.getInstance().w(e);
                }
                if (status == 468) {
                    Log.d(TAG, "handleMessage: 验证码输入错误，请检查");
                    mTv_sendMessage.setText("验证码输入错误，请检查");
                    mTv_sendMessage.setVisibility(View.VISIBLE);
                } else if (status == 466) {
                    Log.d(TAG, "handleMessage: 验证码为空");
                    mTv_sendMessage.setText("验证码为空");
                    mTv_sendMessage.setVisibility(View.VISIBLE);
                } else if (status == 462) {
                    mTv_sendMessage.setText("当前操作过于频繁");
                    mTv_sendMessage.setVisibility(View.VISIBLE);
                } else if (status == 467) {
                    mTv_sendMessage.setText("验证失败3次，验证码失效，请重新请求");
                    mTv_sendMessage.setVisibility(View.VISIBLE);
                } else if (status == 477) {
                    mTv_sendMessage.setText("当前手机号发送短信的数量超过限额");
                    mTv_sendMessage.setVisibility(View.VISIBLE);
                } else {
                    Log.d(TAG, "handleMessage: 错误" + status);
                }

            }

        }
    };

*/
    /**
     * 发送短信验证码60s倒计时
     * millisInFuture 倒计时总时长
     * countDownInterval 间隔时间
     */
    private void messageCountDown() {
        mBtn_getCode.setEnabled(false); //“获取验证码”按钮不可点击
        CountDownTimer countDownTimer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(TAG, "onTick: time==" + time);
                mBtn_getCode.setText(time-- + "秒后再次获取");
            }

            @Override
            public void onFinish() {
                mTv_sendMessage.setVisibility(View.INVISIBLE);
                mBtn_getCode.setText("获取验证码");
                mBtn_getCode.setEnabled(true);
            }
        };
        countDownTimer.start();
    }

    /**
     * 注销监听，避免内存泄露
     */
    /*protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(mEventHandler);
    }*/
}
