package com.example.zhaoxinwu.remote;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Context context;
    public String ipAddr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        Toast.makeText(context,"Searching for berry...", Toast.LENGTH_SHORT).show();// change to dialog
        new Thread(new UDPPacket()).start(); //Start listening to broadcast!
        while(!IPAddr.getInstance().ipGot) {
            ipAddr = IPAddr.getInstance().ipAddr;// Use singleton to get ip address derived from the broadcast
        }
        Toast.makeText(context,"Berry Connected on " + ipAddr, Toast.LENGTH_SHORT).show();
        ImageButton tvRemoteSwitch = findViewById(R.id.button_tv_remote);
        tvRemoteSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TVActivity.class);
                intent.putExtra("IP_ADDR", ipAddr);
                startActivity(intent);
            }
        });

        ImageButton lampRemoteSwitch = findViewById(R.id.button_lamp_remote);
        lampRemoteSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LampActivity.class);
                intent.putExtra("IP_ADDR", ipAddr);
                startActivity(intent);
            }
        });

        ImageButton acRemoteSwitch = findViewById(R.id.button_ac_remote);
        acRemoteSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ACActivity.class);
                intent.putExtra("IP_ADDR", ipAddr);
                startActivity(intent);
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
