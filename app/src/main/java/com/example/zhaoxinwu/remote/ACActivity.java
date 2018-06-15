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
import com.jakewharton.rxbinding2.view.RxView;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.CompositeDisposable;

public class ACActivity extends AppCompatActivity {
    private TextView mACTemp, mRoomTemp, mWindSpeed, mWindDirection, mPower, mMode;
    private ACStat acstat;
    final Map<String, Integer> modeACColorIndicator = new HashMap();

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac);
        context = getApplicationContext();

        final RequestQueue queue = Volley.newRequestQueue(this);
        String urlAC = "http://" + ServerIPListener.getInstance().getServerIP() + ":5000/ac";

        mACTemp = findViewById(R.id.value_ac_temp);
        mRoomTemp = findViewById(R.id.value_room_temp);
        mWindSpeed = findViewById(R.id.value_ac_wind_speed);
        mWindDirection = findViewById(R.id.value_ac_wind_direction);
        mPower = findViewById(R.id.value_ac_power);
        mMode = findViewById(R.id.value_ac_mode);

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

        compositeDisposable.add(RxView.clicks(buttonACPower)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(empty -> {
                    sendPUTRequest(queue, urlAC, "power", "");
                }));

        compositeDisposable.add(RxView.clicks(buttonACTempUp)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(empty -> {
                    sendPUTRequest(queue, urlAC, "temp", "up");
                }));

        compositeDisposable.add(RxView.clicks(buttonACTempDown)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(empty -> {
                    sendPUTRequest(queue, urlAC, "temp", "down");
                }));

        compositeDisposable.add(RxView.clicks(buttonACMode)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(empty -> {
                    sendPUTRequest(queue, urlAC, "mode", "");
                }));

        compositeDisposable.add(RxView.clicks(buttonWindSpeed)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(empty -> {
                    sendPUTRequest(queue, urlAC, "speed", "");
                }));

        compositeDisposable.add(RxView.clicks(buttonWindDirection)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .subscribe(empty -> {
                    sendPUTRequest(queue, urlAC, "dir", "");
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

    private void updateInfoText() {
        mACTemp.setText(acstat.temp);
        mRoomTemp.setText(String.valueOf(Math.round(Double.valueOf(acstat.room_temp))));
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
        StringRequest strRequest = new StringRequest(Request.Method.GET, url,
                (String response) -> {
                    Gson gson = new Gson();
                    acstat = gson.fromJson(response, ACStat.class);
                    updateInfoText();
                },
                (VolleyError error) -> {
                    Log.e("LOG", error.toString());
                });
        queue.add(strRequest);
    }

    public void sendPUTRequest(RequestQueue queue, String url, String name, String param) {
        final Map<String, String> params = new HashMap();
        params.put(name, param);

        StringRequest strRequest = new StringRequest(Request.Method.PUT, url,
            (String response) -> {
                Gson gson = new Gson();
                acstat = gson.fromJson(response, ACStat.class);
                updateInfoText();
            },
           (VolleyError error) -> {
               Log.e("LOG", error.toString());
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
