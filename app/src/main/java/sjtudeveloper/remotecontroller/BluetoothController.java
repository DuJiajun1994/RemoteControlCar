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
    private BluetoothAdapter bta = null;
    private BluetoothSocket bts = null;
    private BluetoothDevice btd = null;
    private static final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private OutputStream ops = null;

    BluetoothController() throws IOException{
        Log.i("BluetoothController", "initial");
        bta = BluetoothAdapter.getDefaultAdapter();
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
        }

        try {
            // MY_UUID is the app's UUID string, also used by the server code
            bts = btd.createRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) {
            Log.e("BluetoothController", "error 1.2");
            throw e;
        }

        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            bts.connect();
            Log.i("BluetoothController", "connected");
            //Toast.makeText(getApplicationContext(), "Connect success!", Toast.LENGTH_SHORT).show();
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

    public void turn_left() {
        try {
            ops.write("L".getBytes());
        } catch (IOException e) {
            Log.e("BluetoothController", "error 5");
        }
    }

    public void turn_right() {
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

    public void rotate_left() {
        try {
            ops.write("Y".getBytes());
        } catch (IOException e) {
            Log.e("BluetoothController", "error 5");
        }
    }

    public void rotate_right() {
        try {
            ops.write("Z".getBytes());
        } catch (IOException e) {
            Log.e("BluetoothController", "error 5");
        }
    }
}
