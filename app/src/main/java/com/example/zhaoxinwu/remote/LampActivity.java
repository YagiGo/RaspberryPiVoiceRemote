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
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

public class LampActivity extends AppCompatActivity {

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp);
        context = getApplicationContext();
        final RequestQueue queue = Volley.newRequestQueue(this);
        ImageButton buttonPower = findViewById(R.id.button_lamp_power);
        ImageButton buttonBrightlessUP = findViewById(R.id.button_lamp_increase_brightless);
        ImageButton buttonBrightlessDOWN = findViewById(R.id.button_lamp_decrease_brightless);
        Button buttonWarmLight = findViewById(R.id.button_lamp_warm);
        Button buttonColdLight = findViewById(R.id.button_lamp_cold);

        final String urlLamp = "http://" + ServerIPLinster.getInstance().getServerIP() + ":5000/remote/iris_oyama.light/";
        RxView.clicks(buttonPower).throttleFirst(1000, TimeUnit.MILLISECONDS).subscribe(empty -> {
            Toast.makeText(context, "Lamp will be powered on/off", Toast.LENGTH_SHORT).show();
            sendRequest(queue, urlLamp, "KEY_POWER");
        });

        RxView.clicks(buttonBrightlessUP).throttleFirst(1000, TimeUnit.MILLISECONDS).subscribe(empty -> {
            Toast.makeText(context, "Lamp will be brighter", Toast.LENGTH_SHORT).show();
            sendRequest(queue, urlLamp, "KEY_BRIGHTNESSUP");
        });

        RxView.clicks(buttonBrightlessDOWN).throttleFirst(1000, TimeUnit.MILLISECONDS).subscribe(empty -> {
            Toast.makeText(context, "Lamp will be dimmer", Toast.LENGTH_SHORT).show();
            sendRequest(queue, urlLamp, "KEY_BRIGHTNESSDOWN");
        });

        RxView.clicks(buttonWarmLight).throttleFirst(1000, TimeUnit.MILLISECONDS).subscribe(empty -> {
            Toast.makeText(context, "Warm Light!", Toast.LENGTH_SHORT).show();
            sendRequest(queue, urlLamp, "KEY_RED");
        });

        RxView.clicks(buttonColdLight).throttleFirst(1000, TimeUnit.MILLISECONDS).subscribe(empty -> {
            Toast.makeText(context, "Cold Light", Toast.LENGTH_SHORT).show();
            sendRequest(queue, urlLamp, "KEY_BLUE");
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