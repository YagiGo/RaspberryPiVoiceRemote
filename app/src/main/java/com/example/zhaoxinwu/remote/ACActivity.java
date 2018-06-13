package com.example.zhaoxinwu.remote;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import static java.lang.Double.valueOf;

public class ACActivity extends AppCompatActivity {
    private TextView mACTemp, mRoomTemp, mWindSpeed, mWindDirection, mTimer, mMode;
    private Integer userTemp, initTemp = 26;
    private String[] modeAC = {"AUTO", "COOL", "HEAT", "DRY", "FAN"};
    private Integer[] modeACColorIndicator = {Color.BLACK, Color.BLUE, Color.RED,
            /* Dark Yellow, 'cause the standard yellow color is a piece of shxt */
            Color.argb(255,194,120,7),
            Color.DKGRAY};
    private String[] windDirection = {"AUTO", "TEST1", "TEST2", "TEST3", "TEST4"};
    private String[] windSpeed = {"AUTO", "LOW", "MEDIUM", "HIGH"};
    private Integer windDirectionIndicator = 0, windSpeedIndicator = 0, modeIndicator = 0;
    private Boolean timerOn = false;
    private Double userTimer, initTimer = 0.5;
    private String urlAC = "http://192.168.0.3:5000//"; // I don't know yet.
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac);
        context = getApplicationContext();
        mACTemp = findViewById(R.id.value_ac_temp);
        mRoomTemp = findViewById(R.id.value_room_temp);
        mWindSpeed = findViewById(R.id.value_ac_wind_speed);
        mWindDirection = findViewById(R.id.value_ac_wind_direction);
        mTimer = findViewById(R.id.value_ac_timer);
        mMode = findViewById(R.id.value_ac_mode);
        userTemp = initTemp;
        userTimer = initTimer;
        ImageButton buttonACTempUp = findViewById(R.id.button_ac_temp_up);
        ImageButton buttonACTempDown = findViewById(R.id.button_ac_temp_down);
        Button buttonACMode = findViewById(R.id.button_ac_mode_change);
        Button buttonACTimer = findViewById(R.id.button_ac_timer);
        Button buttonWindSpeed = findViewById(R.id.button_ac_wind_speed);
        Button buttonWindDirection = findViewById(R.id.button_ac_wind_direction);

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
                modeIndicator = (modeIndicator + 1) % 5;
                mMode.setText(String.valueOf(modeAC[modeIndicator]));
                mMode.setTextColor(modeACColorIndicator[modeIndicator]);
                //Log.d("INDICATOR", String.valueOf(modeIndicator));
                //Log.d("INDICATOR", String.valueOf(modeAC[modeIndicator]));

            }
        });

        buttonACTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Do Something with the timer */
                // Toast.makeText(context, "Will set timer", Toast.LENGTH_SHORT).show();
                if(!timerOn) {
                    mTimer.setText(String.valueOf(userTimer));
                    mTimer.setTextColor(Color.BLUE);
                    userTimer += 0.5;
                    timerOn = true;
                }
                else if (timerOn) {
                    if(userTimer <= 12) {
                        mTimer.setText(String.valueOf(userTimer));
                        mTimer.setTextColor(Color.BLUE);
                        userTimer += 0.5;
                    }
                    else {
                        timerOn = false;
                        userTimer = 0.5;
                        mTimer.setText("OFF");
                        mTimer.setTextColor(Color.BLACK);
                    }
                }
            }
        });

        buttonWindDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Do something with the wind speed setting */
                Toast.makeText(context, "Will adjust the wind speed", Toast.LENGTH_SHORT)
                        .show();
                windSpeedIndicator = (windSpeedIndicator + 1) % 4;
                mWindSpeed.setText(windSpeed[windSpeedIndicator]);
                mWindSpeed.setTextColor(Color.GREEN);
            }
        });

        buttonWindSpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Do something with the wind direction setting */
                Toast.makeText(context, "Will adjust the wind direction", Toast.LENGTH_SHORT)
                        .show();
                windDirectionIndicator = (windDirectionIndicator + 1) % 5;
                mWindDirection.setText(windDirection[windDirectionIndicator]);
                mWindDirection.setTextColor(Color.BLUE);
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
    public void sendRequest(RequestQueue queue, String url, String param) {
        StringRequest putRequest = new StringRequest(Request.Method.PUT,
                url+param,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("RESPONSE", response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR",error.toString());
                    }
                });

        queue.add(putRequest);
    }
}
