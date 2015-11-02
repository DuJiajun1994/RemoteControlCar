package sjtudeveloper.remotecontroller;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.util.Log;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

/**
 * Created by M_D_Luffy on 2015/11/2.
 */
public class BluetoothController {
    private BluetoothAdapter bta;
    private BluetoothSocket bts;
    private BluetoothDevice btd;
    private static final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private OutputStream ops;

    BluetoothController(){
        bta = BluetoothAdapter.getDefaultAdapter();
        connect();
    }

    public void connect() {

        Set<BluetoothDevice> devices = bta.getBondedDevices();
        for(int i = 0 ; i < devices.size() ; i ++){
            BluetoothDevice device = (BluetoothDevice)
                    devices.toArray()[i];
            if(device.getName().equals("HC-06")){     //GT-I8262D HC-06
                btd = device;
                break;
            }
        }

        if(btd == null){
            Log.i("BluetoothController", "error 1.1");
            return;
        }

        try {
            // MY_UUID is the app's UUID string, also used by the server code
            bts = btd.createRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) {
            Log.e("BluetoothController", "error 1.2");
        }
        if(isConnected()) return;
        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            bts.connect();
            Log.i("BluetoothController", "connected");
        } catch (IOException connectException) {
            Log.e("BluetoothController", "error 2: unable to connect, have to close the socket");

            try {
                bts.close();
            } catch (IOException closeException) {
                Log.e("BluetoothController", "error 3");
            }
            return;
        }

        try {
            ops = bts.getOutputStream();
        } catch (IOException e1) {
            Log.e("BluetoothController", "error 1");
        }

        return;
    }

    public boolean isConnected(){
        return bts.isConnected();  //some exception not handled
    }

    public void run() {
        try {
            Log.i("BluetoothController", "send order");
            ops.write("U".getBytes());
        } catch (IOException e) {
            Log.e("BluetoothController", "error 5");
        }
    }

    public void back() {
        try {
            ops.write("D".getBytes());
        } catch (IOException e) {
            Log.e("BluetoothController", "error 5");
        }
    }

    public void turnLeft() {
        try {
            ops.write("L".getBytes());
        } catch (IOException e) {
            Log.e("BluetoothController", "error 5");
        }
    }

    public void turnRight() {
        try {
            ops.write("R".getBytes());
        } catch (IOException e) {
            Log.e("BluetoothController", "error 5");
        }
    }

    public void stop() {
        try {
            ops.write("S".getBytes());
        } catch (IOException e) {
            Log.e("BluetoothController", "error 5");
        }
    }

    public void rotateLeft() {
        try {
            ops.write("Y".getBytes());
        } catch (IOException e) {
            Log.e("BluetoothController", "error 5");
        }
    }

    public void rotateRight() {
        try {
            ops.write("Z".getBytes());
        } catch (IOException e) {
            Log.e("BluetoothController", "error 5");
        }
    }
}
