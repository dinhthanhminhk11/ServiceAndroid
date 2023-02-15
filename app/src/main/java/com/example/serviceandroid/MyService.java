package com.example.serviceandroid;

import static com.example.serviceandroid.MyApplication.CHANNEL_ID;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class MyService extends Service {
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String data = intent.getStringExtra("key");
        Log.e("Minh", "data " + data);

        sendNotification(data);

        return START_NOT_STICKY;
    }

    private void sendNotification(String data) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);


        // từ phiên bản android 8.1 bắt buộc phải có noti
        final NotificationCompat.Builder notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Loading Dictionary")
                .setOnlyAlertOnce(true)
                .setOngoing(true);

        startForeground(1, notification.build());
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Minh", "onCreate");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("Minh", "onDestroy");
    }
}
