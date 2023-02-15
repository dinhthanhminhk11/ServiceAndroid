package com.example.serviceandroid;

import static com.example.serviceandroid.MyApplication.CHANNEL_ID;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;

public class MyService extends Service {

    private ImageView image;
    private TextView title;
    private ImageView play;
    private ImageView close;
    private boolean isPlaying;
    private static final int ACTION_PAUSE = 1;
    private static final int ACTION_RESUME = 2;
    private static final int ACTION_CLOSE = 3;
    private MediaPlayer mediaPlayer;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            Song song = (Song) bundle.get("song");
            if (song instanceof Song) {
                sendNotification(song);
                startMusic(song);
            }
        }

        int actionMusic = intent.getIntExtra("action_music" ,0);
        handleActionMusic(actionMusic);
        return START_NOT_STICKY;
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

        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void sendNotification(Song song) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), song.getImage());

        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.layoutnoti);
        remoteViews.setTextViewText(R.id.title, song.getTitle());
        remoteViews.setImageViewBitmap(R.id.image, bitmap);
        remoteViews.setImageViewResource(R.id.play, R.drawable.baseline_pause_24);

        if (isPlaying) {
            remoteViews.setOnClickPendingIntent(R.id.play, getPendingIntent(this, ACTION_PAUSE));
            remoteViews.setImageViewResource(R.id.play, R.drawable.baseline_play_arrow_24);
        } else {
            remoteViews.setOnClickPendingIntent(R.id.play, getPendingIntent(this, ACTION_RESUME));
            remoteViews.setImageViewResource(R.id.play, R.drawable.baseline_pause_24);
        }

        remoteViews.setOnClickPendingIntent(R.id.close, getPendingIntent(this, ACTION_CLOSE));

        // từ phiên bản android 8.1 bắt buộc phải có noti

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID).setSmallIcon(R.drawable.musicimage).setContentIntent(pendingIntent).setCustomContentView(remoteViews).setSound(null).build();

        startForeground(1, notification);
    }

    private PendingIntent getPendingIntent(MyService myService, int actionPause) {
        Intent intent = new Intent(this, MyReceiver.class);
        intent.putExtra("music", actionPause);
        // truyền dữ liệu
        return PendingIntent.getBroadcast(getApplicationContext(), actionPause, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void startMusic(Song song) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), song.getResource());
        }
        isPlaying = true;
        mediaPlayer.start();
    }

    private void pause() {
        if (mediaPlayer != null && isPlaying) {
            mediaPlayer.pause();
            isPlaying = false;
        }
    }

    private void resume() {
        if (mediaPlayer != null && isPlaying) {
            mediaPlayer.pause();
            isPlaying = true;
        }
    }

    private void handleActionMusic(int action) {
        switch (action) {
            case ACTION_PAUSE:
                pause();
                break;
            case ACTION_RESUME:
                resume();
                break;
            case ACTION_CLOSE:
                stopSelf();
                break;
        }
    }
}
