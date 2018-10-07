package com.example.hunter.alarmservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {

    Intent intent;
    private Switch onSwitch;
    private TimePicker timePicker;
    private Button setTime;
    int hour;
    int min;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onSwitch = findViewById(R.id.onSwitch);
        timePicker = findViewById(R.id.timePicker);
        setTime = findViewById(R.id.setTime);

        setTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hour = timePicker.getHour();
                min = timePicker.getMinute();
            }
        });

        onSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    hour = timePicker.getHour();
                    min = timePicker.getMinute();
                    intent = new Intent(getApplicationContext(), AlarmService.class);
                    intent.putExtra("hour", hour);
                    intent.putExtra("min", min);
                    startService(intent);
                }
                else{
                    stopService(intent);
                }
            }
        });
    }

}
