package com.example.zhaoxinwu.remote;

import android.content.Context;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class LampActivity extends AppCompatActivity {

    private Context context;
    private String ipAddr, urlLamp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp);
        Bundle bundle = getIntent().getExtras();
        ipAddr = bundle.getString("IP_ADDR");
        urlLamp = "http://" + ipAddr + ":5000" + "/remote/iris_oyama.light/";

        Log.i("LAMP ACTIVITY", ipAddr); // To see the address has been transferred correctly
        context = getApplicationContext();
        final RequestQueue queue = Volley.newRequestQueue(this);
        ImageButton buttonPower = findViewById(R.id.button_lamp_power);
        ImageButton buttonBrightlessUP = findViewById(R.id.button_lamp_increase_brightless);
        ImageButton buttonBrightlessDOWN = findViewById(R.id.button_lamp_decrease_brightless);
        Button buttonWarmLight = findViewById(R.id.button_lamp_warm);
        Button buttonColdLight = findViewById(R.id.button_lamp_cold);

        buttonPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Lamp will be powered on/off", Toast.LENGTH_SHORT).show();
                sendRequest(queue, urlLamp, "KEY_POWER");
            }
        });
        buttonBrightlessUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Lamp will be brighter", Toast.LENGTH_SHORT).show();
                sendRequest(queue, urlLamp, "KEY_BRIGHTNESSUP");
            }
        });
        buttonBrightlessDOWN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Lamp will be dimmer", Toast.LENGTH_SHORT).show();
                sendRequest(queue, urlLamp, "KEY_BRIGHTNESSDOWN");
            }
        });
        buttonWarmLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Warm Light!", Toast.LENGTH_SHORT).show();
                sendRequest(queue, urlLamp, "KEY_RED");
            }
        });
        buttonColdLight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Cold Light", Toast.LENGTH_SHORT).show();
                sendRequest(queue, urlLamp, "KEY_BLUE");
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