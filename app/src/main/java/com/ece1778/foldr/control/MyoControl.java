package com.ece1778.foldr.control;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.thalmic.myo.AbstractDeviceListener;
import com.thalmic.myo.Hub;
import com.thalmic.myo.Myo;
import com.thalmic.myo.Pose;
import com.thalmic.myo.scanner.ScanActivity;

/**
 * Created by Ding on 4/1/2015.
 */
public class MyoControl extends AbstractDeviceListener {
    private final Context context;
    private final Hub hub;
    private boolean isInitialized;

    public MyoControl(Context context){
        this.context = context;
        this.hub = Hub.getInstance();
        this.isInitialized = hub.init(this.context);
    }

    public void connect(){
        if(this.isInitialized){
            this.hub.setLockingPolicy(Hub.LockingPolicy.NONE);
            this.hub.addListener(this);
        }
    }

    public void disconnect(){
        if(this.isInitialized){
            this.hub.removeListener(this);
        }
    }

    @Override
    public void onConnect(Myo myo, long timestamp) {
        Toast.makeText(this.context, "Myo Connected!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDisconnect(Myo myo, long timestamp) {
        Toast.makeText(this.context, "Myo Disconnected!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPose(Myo myo, long timestamp, Pose pose) {
        Log.i("MYO", pose.toString());
    }
}
