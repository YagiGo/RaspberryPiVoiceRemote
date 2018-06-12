package com.example.zhaoxinwu.remote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ACActivity extends AppCompatActivity {
    private TextView mACTemp, mRoomTemp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ac);
        mACTemp = (TextView)findViewById(R.id.value_ac_temp);

        Button tempUp = (Button)findViewById(R.id.button_)

    }
}
