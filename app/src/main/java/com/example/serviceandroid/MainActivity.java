package com.example.serviceandroid;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText editText;
    private Button start;
    private Button stops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editText);
        start = (Button) findViewById(R.id.start);
        stops = (Button) findViewById(R.id.stops);

        start.setOnClickListener(v -> {
            Song song = new Song("Title", "Minh", R.drawable.musicimage, R.raw.filemusic);

            Intent intent = new Intent(this, MyService.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("song", song);
            intent.putExtras(bundle);

            startService(intent);
        });

        stops.setOnClickListener(v -> {
            Intent intent = new Intent(this, MyService.class);
            stopService(intent);
        });

    }
}