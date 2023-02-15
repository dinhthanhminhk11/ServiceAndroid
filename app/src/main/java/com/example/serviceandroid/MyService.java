package com.example.serviceandroid;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

//gọi startService dẫn dến onStartCommand

//Bound Service mới con bind unbind còn UnBound Không có
public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
//        khi gọi bindService() bên activity thì sẽ vào hàm này,
//        sửa dụng foreground với background thì không hàm này trả về null
    return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /*      muốn dừng service này gọi hàm stopService() bên activity
                hoặc gọi luôn hàm stopSelf(); ở đây luôn
                nếu dùng Bound service thì không lên dùng hàm này
        */
        return super.onStartCommand(intent, flags, startId); // hàm này sẽ chạy vào đầu tiên sao khi mình gọi hàm startService
    }

    @Override
    public void onCreate() {
        super.onCreate();
        // gọi khi service khởi tạo chạy sau onStartCommand , hoặc onBind
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // gọi hàm này khi service bị huỷ
    }
}
