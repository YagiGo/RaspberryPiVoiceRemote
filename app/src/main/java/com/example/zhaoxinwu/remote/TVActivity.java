package com.example.zhaoxinwu.remote;

import android.content.Context;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class TVActivity extends AppCompatActivity {
    Context context;
    private String urlTV = "http://192.168.0.3:5000/remote/pioneer.tv/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final RequestQueue queue = Volley.newRequestQueue(this);
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
                /*
                StringRequest stringRequest = new StringRequest(Request.Method.GET, urlTV,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Log.d("Response", response.substring(0, 500));
                            }
                        },

                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Didn","Didn;t work");
                            }
                        }
                );
                queue.add(stringRequest);
                */
                sendRequest(queue, urlTV, "KEY_POWER");

            }
        });

        buttonMute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "TV will be muted", Toast.LENGTH_SHORT).show();
                sendRequest(queue, urlTV, "KEY_MUTE");


            }
        });
        buttonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "TV menu will be opened", Toast.LENGTH_SHORT).show();
                sendRequest(queue, urlTV, "KEY_MENU");

            }
        });
        buttonVolumeUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "TV volume will be turned up", Toast.LENGTH_SHORT).show();
                sendRequest(queue, urlTV, "KEY_VOLUMEUP");

            }
        });
        buttonVolumeDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "TV volume will be turned down", Toast.LENGTH_SHORT).show();
                sendRequest(queue, urlTV, "KEY_VOLUMEDOWN");

            }
        });
        buttonPreviousChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Back to previous channel", Toast.LENGTH_SHORT).show();
                sendRequest(queue, urlTV, "KEY_LEFT");
            }
        });
        buttonNextChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Next channel", Toast.LENGTH_SHORT).show();
                sendRequest(queue, urlTV, "KEY_RIGHT");
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
