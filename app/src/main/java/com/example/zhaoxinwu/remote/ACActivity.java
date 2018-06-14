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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Double.valueOf;

public class ACActivity extends AppCompatActivity {
    private TextView mACTemp, mRoomTemp, mWindSpeed, mWindDirection, mPower, mMode;
    private ACStat acstat;
    private Integer userTemp, initTemp = 26;
    private String[] modeAC = {"AUTO", "COOL", "HEAT", "DRY", "FAN"};

    final Map<String, Integer> modeACColorIndicator = new HashMap();
    private String[] windDirection = {"AUTO", "TEST1", "TEST2", "TEST3", "TEST4"};
    private String[] windSpeed = {"AUTO", "LOW", "MEDIUM", "HIGH"};
    private Integer windDirectionIndicator = 0, windSpeedIndicator = 0, modeIndicator = 0;
    private Boolean timerOn = false;
    private Double userTimer, initTimer = 0.5;

    private String urlAC = "http://" + ServerIPLinster.getInstance().getServerIP() + ":5000/ac";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac);
        context = getApplicationContext();
        final RequestQueue queue = Volley.newRequestQueue(this);
        mACTemp = findViewById(R.id.value_ac_temp);
        mRoomTemp = findViewById(R.id.value_room_temp);
        mWindSpeed = findViewById(R.id.value_ac_wind_speed);
        mWindDirection = findViewById(R.id.value_ac_wind_direction);
        mPower = findViewById(R.id.value_ac_power);
        mMode = findViewById(R.id.value_ac_mode);

        userTemp = initTemp;
        userTimer = initTimer;
        Bundle bundle = getIntent().getExtras();
        String ipAddr = bundle.getString("IP_ADDR");
        urlAC = "http://" + ipAddr + ":5000" + "/ac";

        modeACColorIndicator.put("cool", Color.BLUE);
        modeACColorIndicator.put("heat", Color.RED);
        modeACColorIndicator.put("dry", Color.YELLOW);

        sendGETRequest(queue, urlAC);

        ImageButton buttonACTempUp = findViewById(R.id.button_ac_temp_up);
        ImageButton buttonACTempDown = findViewById(R.id.button_ac_temp_down);
        Button buttonACMode = findViewById(R.id.button_ac_mode_change);
        ImageButton buttonACPower = findViewById(R.id.button_ac_power);
        Button buttonWindSpeed = findViewById(R.id.button_ac_wind_speed);
        Button buttonWindDirection = findViewById(R.id.button_ac_wind_direction);

        buttonACPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPUTRequest(queue, urlAC, "power", "");
            }
        });

        buttonACTempUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPUTRequest(queue, urlAC, "temp", "up");
            }
        });

        buttonACTempDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPUTRequest(queue, urlAC, "temp", "down");
            }
        });

        buttonACMode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Change Mode", Toast.LENGTH_SHORT).show();
                sendPUTRequest(queue, urlAC, "mode", "");
            }
        });

        buttonWindDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Do something with the wind speed setting */
                Toast.makeText(context, "Will adjust the wind speed", Toast.LENGTH_SHORT)
                        .show();
                sendPUTRequest(queue, urlAC, "speed", "");

            }
        });

        buttonWindSpeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Do something with the wind direction setting */
                Toast.makeText(context, "Will adjust the wind direction", Toast.LENGTH_SHORT)
                        .show();
                sendPUTRequest(queue, urlAC, "dir", "");

            }
        });
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

    private void updateInfoText() {
        mACTemp.setText(acstat.temp);
        mRoomTemp.setText(acstat.temp);
        mWindSpeed.setText(acstat.speed);
        mWindDirection.setText(acstat.dir);
        mMode.setText(acstat.mode);
        mPower.setText(acstat.power);

        mWindSpeed.setTextColor(Color.GREEN);
        mMode.setTextColor(modeACColorIndicator.get(acstat.mode));
        mWindDirection.setTextColor(Color.BLUE);
        return;
    }

    public void sendGETRequest(RequestQueue queue, String url) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    Gson gson = new Gson();
                    acstat = gson.fromJson(response.toString(), ACStat.class);
                    updateInfoText();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LOG", error.toString());
            }
        });

        queue.add(jsonObjectRequest);
    }

    public void sendPUTRequest(RequestQueue queue, String url, String name, String param) {
        final Map<String, String> params = new HashMap();
        params.put(name, param);

        StringRequest strRequest = new StringRequest(Request.Method.PUT, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Gson gson = new Gson();
                        acstat = gson.fromJson(response, ACStat.class);
                        updateInfoText();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                return params;
            }
        };

        queue.add(strRequest);
    }
}
