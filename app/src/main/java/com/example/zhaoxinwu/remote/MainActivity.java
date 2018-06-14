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

        if(checkWiFiConnection()) {
            ServerIPListener.getInstance().start_listening(this);
            while(!ServerIPListener.getInstance().hasServerIP()) { }

            ImageButton tvRemoteSwitch = findViewById(R.id.button_tv_remote);
            tvRemoteSwitch.setOnClickListener((View v) -> {
                    Intent intent = new Intent(getApplicationContext(), TVActivity.class);
                    startActivity(intent);
            });

            ImageButton lampRemoteSwitch = findViewById(R.id.button_lamp_remote);
            lampRemoteSwitch.setOnClickListener((View v) -> {
                    Intent intent = new Intent(getApplicationContext(), LampActivity.class);
                    startActivity(intent);
            });

            ImageButton acRemoteSwitch = findViewById(R.id.button_ac_remote);
            acRemoteSwitch.setOnClickListener((View v) -> {
                    Intent intent = new Intent(getApplicationContext(), ACActivity.class);
                    startActivity(intent);
            });
            Log.i("MAIN ACTIVITY", ServerIPListener.getInstance().getServerIP());
        }
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
