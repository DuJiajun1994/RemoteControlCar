package sjtudeveloper.remotecontroller;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by zhongjw on 15-10-28.
 */
public class GravitySensorManager{
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener gravityListener;
    GravitySensorManager(final Context ctx) {
        sensorManager = (SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gravityListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
    }

    void register(){
        sensorManager.registerListener(gravityListener,sensor,SensorManager.SENSOR_DELAY_GAME);
    }
    void unregister(){
        sensorManager.unregisterListener(gravityListener);
    }
}