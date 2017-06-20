package com.zhayh.weixinhookdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView tv_imei = null;
    private TextView tv_passwordKey;
    private String uin = "91120166";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TelephonyManager telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        String imei = telephonyManager.getDeviceId();

        tv_imei = (TextView) findViewById(R.id.tv_imei);
        tv_passwordKey = (TextView) findViewById(R.id.tv_password);
        tv_imei.append(imei);

        String str = WeixinMD5.n((imei + uin).getBytes()).substring(0,7);

        tv_passwordKey.append(str);

    }
}
