package com.example.zhaoxinwu.remote;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.CompositeDisposable;

public class LampActivity extends AppCompatActivity {
    Context context;
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lamp);
        context = getApplicationContext();

        final RequestQueue queue = Volley.newRequestQueue(this);
        final String urlLamp = "http://" + ServerIPListener.getInstance().getServerIP() + ":5000/remote/iris_oyama.light/";

        ImageButton buttonPower = findViewById(R.id.button_lamp_power);
        ImageButton buttonBrightnessUP = findViewById(R.id.button_lamp_increase_brightless);
        ImageButton buttonBrightnessDOWN = findViewById(R.id.button_lamp_decrease_brightless);
        Button buttonWarmLight = findViewById(R.id.button_lamp_warm);
        Button buttonColdLight = findViewById(R.id.button_lamp_cold);

        compositeDisposable.add(RxView.clicks(buttonPower)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(empty -> {
                    Toast.makeText(context, "Lamp will be powered on/off", Toast.LENGTH_SHORT).show();
                    sendRequest(queue, urlLamp, "KEY_POWER");
        }));


        compositeDisposable.add(RxView.clicks(buttonBrightnessUP)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(empty -> {
                    Toast.makeText(context, "Lamp will be brighter", Toast.LENGTH_SHORT).show();
                    sendRequest(queue, urlLamp, "KEY_BRIGHTNESSUP");
        }));

        compositeDisposable.add(RxView.clicks(buttonBrightnessDOWN)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(empty -> {
                    Toast.makeText(context, "Lamp will be dimmer", Toast.LENGTH_SHORT).show();
                    sendRequest(queue, urlLamp, "KEY_BRIGHTNESSDOWN");
        }));

        compositeDisposable.add(RxView.clicks(buttonWarmLight)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(empty -> {
                    Toast.makeText(context, "Warm Light!", Toast.LENGTH_SHORT).show();
                    sendRequest(queue, urlLamp, "KEY_RED");
        }));

        compositeDisposable.add(RxView.clicks(buttonColdLight)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(empty -> {
                    Toast.makeText(context, "Cold Light", Toast.LENGTH_SHORT).show();
                    sendRequest(queue, urlLamp, "KEY_BLUE");
        }));

    }
    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onDestroy(){
        compositeDisposable.dispose();
        super.onDestroy();
    }

    public void sendRequest(RequestQueue queue, String url, String param) {
        StringRequest putRequest = new StringRequest(Request.Method.PUT,
                url+param,
                (String response) -> {
                        Log.d("RESPONSE", response);
                },
                (VolleyError error) -> {
                        Log.d("ERROR",error.toString());
                });
        queue.add(putRequest);
    }
}