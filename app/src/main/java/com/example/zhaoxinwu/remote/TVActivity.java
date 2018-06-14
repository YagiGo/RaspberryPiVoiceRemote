package com.example.zhaoxinwu.remote;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

import io.reactivex.disposables.CompositeDisposable;


public class TVActivity extends AppCompatActivity {
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);
        context = getApplicationContext();

        final RequestQueue queue = Volley.newRequestQueue(this);
        String urlTV = "http://" + ServerIPListener.getInstance().getServerIP() + ":5000/remote/pioneer.tv/";

        ImageButton buttonPower = findViewById(R.id.button_tv_power_off);
        ImageButton buttonMute = findViewById(R.id.button_tv_mute);
        ImageButton buttonMenu = findViewById(R.id.button_tv_menu);
        ImageButton buttonVolumeUp = findViewById(R.id.button_tv_vol_up);
        ImageButton buttonVolumeDown = findViewById(R.id.button_tv_vol_down);
        ImageButton buttonLeft = findViewById(R.id.button_tv_left);
        ImageButton buttonRight = findViewById(R.id.button_tv_right);
        ImageButton buttonUp = findViewById(R.id.button_tv_up);
        ImageButton buttonDown = findViewById(R.id.button_tv_down);

        compositeDisposable.add(RxView.clicks(buttonPower)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(empty -> {
                    sendRequest(queue, urlTV, "KEY_POWER");
                }));

        compositeDisposable.add(RxView.clicks(buttonMute)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(empty -> {
                    sendRequest(queue, urlTV, "KEY_MUTE");
                }));

        compositeDisposable.add(RxView.clicks(buttonMenu)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(empty -> {
                    sendRequest(queue, urlTV, "KEY_MENU");
                }));

        compositeDisposable.add(RxView.clicks(buttonVolumeUp)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(empty -> {
                    sendRequest(queue, urlTV, "KEY_VOLUMEUP");
                }));

        compositeDisposable.add(RxView.clicks(buttonVolumeDown)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(empty -> {
                    sendRequest(queue, urlTV, "KEY_VOLUMEDOWN");
                }));

        compositeDisposable.add(RxView.clicks(buttonUp)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(empty -> {
                    sendRequest(queue, urlTV, "KEY_UP");
                }));

        compositeDisposable.add(RxView.clicks(buttonLeft)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(empty -> {
                    sendRequest(queue, urlTV, "KEY_LEFT");
                }));

        compositeDisposable.add(RxView.clicks(buttonRight)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(empty -> {
                    sendRequest(queue, urlTV, "KEY_RIGHT");
                }));

        compositeDisposable.add(RxView.clicks(buttonDown)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(empty -> {
                    sendRequest(queue, urlTV, "KEY_DOWN");
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
                url + param,
                (String response) -> {
                        Log.d("RESPONSE", response);
                },
                (VolleyError error) -> {
                        Log.d("ERROR",error.toString());
        });
        queue.add(putRequest);
    }

}
