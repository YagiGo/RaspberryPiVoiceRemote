package com.example.zhaoxinwu.remote;

import android.content.Context;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class TVActivity extends AppCompatActivity {
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);
        context = getApplicationContext();
        ImageButton buttonPower = findViewById(R.id.button_tv_power_off);
        ImageButton buttonMute = findViewById(R.id.button_tv_mute);
        ImageButton buttonMenu = findViewById(R.id.button_tv_menu);
        ImageButton buttonVolumeUp = findViewById(R.id.button_tv_vol_up);
        ImageButton buttonVolumeDown = findViewById(R.id.button_tv_vol_down);
        ImageButton buttonPreviousChannel = findViewById(R.id.button_tv_previous);
        ImageButton buttonNextChannel = findViewById(R.id.button_tv_next);

        buttonPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "TV will be powered on/off", Toast.LENGTH_SHORT).show();

            }
        });

        buttonMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "TV will be muted", Toast.LENGTH_SHORT).show();

            }
        });
        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "TV menu will be opened", Toast.LENGTH_SHORT).show();

            }
        });
        buttonVolumeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "TV volume will be turned up", Toast.LENGTH_SHORT).show();

            }
        });
        buttonVolumeDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "TV volume will be turned down", Toast.LENGTH_SHORT).show();

            }
        });
        buttonPreviousChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Back to previous channel", Toast.LENGTH_SHORT).show();

            }
        });
        buttonNextChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Next channel", Toast.LENGTH_SHORT).show();

            }
        });
    }
    @Override
    protected void onResume(){
        super.onResume();
    }
    @Override
    protected void onPause(){
        super.onPause();
    }
}
