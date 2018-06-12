package com.example.zhaoxinwu.remote;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton tvRemoteSwitch = (ImageButton) findViewById(R.id.button_tv_remote);
        tvRemoteSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TVActivity.class);
                startActivity(intent);
            }
        });

        ImageButton lampRemoteSwitch = findViewById(R.id.button_lamp_remote);
        lampRemoteSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LampActivity.class);
                startActivity(intent);
            }
        });

        ImageButton acRemoteSwitch = findViewById(R.id.button_ac_remote);
        acRemoteSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ACActivity.class);
                startActivity(intent);
            }
        });
    }
}
