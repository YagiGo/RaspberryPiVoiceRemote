package com.example.zhaoxinwu.remote;

import android.content.Context;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class LampActivity extends AppCompatActivity {

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp);
        context = getApplicationContext();
        ImageButton buttonPower = findViewById(R.id.button_lamp_power);
        ImageButton buttonBrightlessUP = findViewById(R.id.button_lamp_increase_brightless);
        ImageButton buttonBrightlessDOWN = findViewById(R.id.button_lamp_decrease_brightless);
        Button buttonWarmLight = findViewById(R.id.button_lamp_warm);
        Button buttonColdLight = findViewById(R.id.button_lamp_cold);

        buttonPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Lamp will be powered on/off", Toast.LENGTH_SHORT).show();
            }
        });
        buttonBrightlessUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Lamp will be brighter", Toast.LENGTH_SHORT).show();
            }
        });
        buttonBrightlessDOWN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Lamp will be dimmer", Toast.LENGTH_SHORT).show();
            }
        });
        buttonWarmLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Warm Light!", Toast.LENGTH_SHORT).show();
            }
        });
        buttonColdLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Cold Light", Toast.LENGTH_SHORT).show();
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