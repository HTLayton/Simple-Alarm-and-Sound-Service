package com.example.hunter.alarmservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;


public class AlarmService extends Service {


    private Calendar cal;
    private Intent sound;
    private boolean soundOn = false;
    private boolean isOn;
    private int hour;
    private int min;
    private String Tag = "Alarm Service,";

    class AlarmServiceBinder extends Binder {
        public AlarmService getService(){
            return AlarmService.this;
        }
    }

    private IBinder binder = new AlarmServiceBinder();

    public int onStartCommand(Intent intent, int flags, int startId){
        hour = intent.getExtras().getInt("hour");
        min = intent.getExtras().getInt("min");
        isOn = true;

        new Thread(new Runnable() {
            @Override
            public void run() {
                startService();
            }
        }).start();

        return START_STICKY;
    }

    public void startService(){
        while(isOn){
            cal = Calendar.getInstance();
            int hourLeft = hour - cal.get(Calendar.HOUR_OF_DAY);
            Log.i(Tag, "Hour: "+ hour + " Calendar Hour: "+ Calendar.HOUR +" Hour Left: " + hourLeft);
            int minLeft = min - cal.get(Calendar.MINUTE);
            Log.i(Tag, "Min: "+ min + " Calendar Min: "+ Calendar.MINUTE + " Min Left: " + minLeft);
            if(hourLeft > 0){
                try{
                    Log.i(Tag, "More than an hour");
                    Thread.sleep(1800000);
                } catch (InterruptedException e) {
                    Log.i(Tag, "Thread Interrupted");
                }
            }
            else if( minLeft > 30){
                try{
                    Log.i(Tag, "More than 30 min");
                    Thread.sleep(900000);
                } catch (InterruptedException e) {
                    Log.i(Tag, "Thread Interrupted");
                }
            }
            else if(minLeft > 5){
                try{
                    Log.i(Tag, "More than 5 min");
                    Thread.sleep(300000);
                } catch (InterruptedException e) {
                    Log.i(Tag, "Thread Interrupted");
                }
            }
            else if(minLeft >= 1){
                try{
                    Log.i(Tag, "More than 1 min");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Log.i(Tag, "Thread Interrupted");
                }
            }
            else{
                Log.i(Tag, "Less than a min");
                    isOn = false;
                    sound = new Intent(getApplicationContext(), SoundService.class);
                    startService(sound);
                    soundOn = true;
            }
        }
    }

    public void stopService(){
        isOn = false;
    }


    @Override
    public void onDestroy() {
        if(soundOn)
            stopService(sound);

        super.onDestroy();
        stopService();
        Log.i(Tag, "Service Destroyed");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
