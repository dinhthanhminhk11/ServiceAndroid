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
            Intent intent = new Intent(this, MyService.class);
            intent.putExtra("key", editText.getText().toString());
            startService(intent);
        });

        stops.setOnClickListener(v -> {
            Intent intent = new Intent(this, MyService.class);
            stopService(intent);
        });

    }
}