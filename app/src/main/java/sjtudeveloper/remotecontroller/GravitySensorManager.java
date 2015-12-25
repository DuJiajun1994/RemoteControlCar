package sjtudeveloper.remotecontroller;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * Created by zhongjw on 15-10-28.
 */
public class GravitySensorManager{
    private SensorManager sensorManager;
    private Sensor sensor;
    private SensorEventListener gravityListener;
    private String DEBUGTAG = "debugTag";
    GravitySensorManager(final Context ctx, final BluetoothController btc) {
        sensorManager = (SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gravityListener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                Float x = event.values[0];
                Float y = event.values[1];
                Float z = event.values[2];
                Float [] args = {x,y,z};
                checkDirect(args);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }

            private void checkDirect(Float [] args){
                Integer x = Math.round(args[0]);
                Integer y = Math.round(args[1]);

                if (-5<=x&&x<=-2){
                    Log.d(DEBUGTAG,"U");
                    btc.sendOrder("U");

                }else if(-1<x&&x<=2){
                    Log.d(DEBUGTAG,"S");
                    btc.sendOrder("S");
                }else if (3<=x&&x<5){
                    Log.d(DEBUGTAG,"D");
                    btc.sendOrder("D");
                }


                if (-5<=y&&y<=-2){
                    Log.d(DEBUGTAG,"L");
                    btc.sendOrder("L");
                }else if (2<=y&&y<=5){
                    Log.d(DEBUGTAG,"R");
                    btc.sendOrder("R");
                }
//                switch (x){
//                    //U stands for speed up, D stands for slow down, B stands for go back
//                    case -5:Log.d(DEBUGTAG,"U5");break;
//                    case -4:Log.d(DEBUGTAG,"U4");break;
//                    case -3:Log.d(DEBUGTAG,"U3");break;
//                    case -2:Log.d(DEBUGTAG,"U2");break;
//                    case -1:Log.d(DEBUGTAG,"U1");break;
//                    case 0:Log.d(DEBUGTAG,"U0");break;
//                    case 1:Log.d(DEBUGTAG,"D1");break;
//                    case 2:Log.d(DEBUGTAG,"D2");break;
//                    case 3:Log.d(DEBUGTAG,"B3");break;
//                    case 4:Log.d(DEBUGTAG,"B4");break;
//                    case 5:Log.d(DEBUGTAG,"B5");break;
//                }
//                switch (y){
//                    //L stands for turn left, R stands for turn right
//                    case -5:Log.d(DEBUGTAG,"L5");break;
//                    case -4:Log.d(DEBUGTAG,"L4");break;
//                    case -3:Log.d(DEBUGTAG,"L3");break;
//                    case -2:Log.d(DEBUGTAG,"L2");break;
//                    case -1:Log.d(DEBUGTAG,"L1");break;
//                    case 0:Log.d(DEBUGTAG,"L0");break;
//                    case 1:Log.d(DEBUGTAG,"R1");break;
//                    case 2:Log.d(DEBUGTAG,"R2");break;
//                    case 3:Log.d(DEBUGTAG,"R3");break;
//                    case 4:Log.d(DEBUGTAG,"R4");break;
//                    case 5:Log.d(DEBUGTAG,"R5");break;
//                }
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