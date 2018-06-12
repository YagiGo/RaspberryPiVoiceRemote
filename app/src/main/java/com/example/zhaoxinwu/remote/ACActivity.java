package com.example.zhaoxinwu.remote;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ACActivity extends AppCompatActivity {
    private TextView mACTemp, mRoomTemp;
    private Integer userTemp, initTemp = 26;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac);
        context = getApplicationContext();
        mACTemp = findViewById(R.id.value_ac_temp);
        mRoomTemp = findViewById(R.id.value_room_temp);
        userTemp = initTemp;
        ImageButton buttonACTempUp = findViewById(R.id.button_ac_temp_up);
        ImageButton buttonACTempDown = findViewById(R.id.button_ac_temp_down);
        Button buttonACMode = findViewById(R.id.button_ac_mode_change);
        Button buttonACTimer = findViewById(R.id.button_ac_timer);
        buttonACTempUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userTemp < 30) {
                    userTemp += 1;
                }
                 mACTemp.setText(String.valueOf(userTemp));
            }
        });

        buttonACTempDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userTemp > 16) {
                    userTemp -= 1;
                }
                mACTemp.setText(String.valueOf(userTemp));
            }
        });

        buttonACMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Do something with the mode button */
                Toast.makeText(context, "Change Mode", Toast.LENGTH_SHORT).show();
            }
        });

        buttonACTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Do Something with the timer */
                Toast.makeText(context, "Will set timer", Toast.LENGTH_SHORT).show();
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
