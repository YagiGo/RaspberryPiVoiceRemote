package com.example.zhaoxinwu.remote;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Context context;

    private boolean checkWiFiConnection() {
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (!mWifi.isConnected()) {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(this);
            }
            builder.setTitle("WiFi")
                    .setMessage("This App requires Wifi to work")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            finishAndRemoveTask();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return false;
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        Toast.makeText(context,"Searching for raspberry...", Toast.LENGTH_SHORT);

        if(checkWiFiConnection()) {
            ServerIPLinster.getInstance().start_listening();
            while(!ServerIPLinster.getInstance().hasServerIP()) { }

            ImageButton tvRemoteSwitch = findViewById(R.id.button_tv_remote);
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
            Log.i("MAIN ACTIVITY", ServerIPLinster.getInstance().getServerIP());
        }
    }
    @Override
    protected void onResume(){
        ServerIPLinster.getInstance().start_listening();
        super.onResume();
    }

    @Override
    protected void onPause(){
        ServerIPLinster.getInstance().stop_listening();
        super.onPause();
    }
}
