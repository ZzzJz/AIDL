package com.bignerdranch.android.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by zhaojiang on 17/7/24.
 */

public class IRemoteService extends Service{

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    private IBinder mIBinder = new IMyAidlInterface.Stub(){

        @Override
        public int add(int num1, int num2) throws RemoteException {

            Log.d("TAG","收到了远程的请求，输入的参数是"+num1+"和"+num2);
            return num1 + num2;
        }
    };
}
