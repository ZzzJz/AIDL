package com.bignerdranch.android.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bignerdranch.android.aidl.IMyAidlInterface;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditTextNum1;
    private EditText mEditTextNum2;
    private EditText mEditTextRes;

    private Button btnAdd;
    private IMyAidlInterface iMyAidlInterface;

    private ServiceConnection conn = new ServiceConnection() {
        //绑定服务时
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);


        }

        //断开服务时
        @Override
        public void onServiceDisconnected(ComponentName name) {

            //回收资源
            iMyAidlInterface = null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        //当程序启动时就绑定服务
        bindService();
    }

    private void initView() {
        mEditTextNum1 = (EditText) findViewById(R.id.et_num1);
        mEditTextNum2 = (EditText) findViewById(R.id.et_num2);
        mEditTextRes = (EditText) findViewById(R.id.et_res);

        btnAdd = (Button) findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {


        int num1 = Integer.parseInt(mEditTextNum1.getText().toString());
        int num2 = Integer.parseInt(mEditTextNum2.getText().toString());
        try {
            //调用远程服务
            int res = iMyAidlInterface.add(num1,num2);
            mEditTextRes.setText(res+"");
        } catch (RemoteException e) {
            e.printStackTrace();
            mEditTextRes.setText("错误了");
        }

    }

    private void bindService() {
        //获取服务端
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.bignerdranch.android.aidl", "com.bignerdranch.android.aidl.IRemoteService"));

        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
