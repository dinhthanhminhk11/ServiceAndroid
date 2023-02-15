package com.example.serviceandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        int actionMusic = intent.getIntExtra("music", 0);

        Intent intent1 = new Intent(context, MyService.class);
        intent1.putExtra("action_music" ,actionMusic);

        context.startService(intent1);

    }
}
