package sjtudeveloper.remotecontroller;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import java.net.ServerSocket;

/**
 * Created by zhongjw on 2015/12/25.
 */
public class NetWorkController {
    private WifiController wifiController;
    private WifiManager.WifiLock multicastLock;

    private ServerSocket LocalServerSocket = null;

    private static int LocalServerPort = 15420;

    private int localIP;
    private String StringIP;

    private Thread readDispathThread = null;

    public NetWorkController(Context context){
        initWifi(context);
    }
    private void initWifi(Context context){
        wifiController = new WifiController(context);
        if(wifiController.checkState()== WifiManager.WIFI_STATE_DISABLED){
            Toast.makeText(context, "当前wifi关闭，尝试自动打开", Toast.LENGTH_SHORT).show();
            wifiController.openWifi();
        }
        else if(wifiController.checkState()== WifiManager.WIFI_STATE_ENABLED){
            Toast.makeText(context,"当前wifi已经打开",Toast.LENGTH_SHORT).show();
            if(wifiController.isWifiConnected()){Toast.makeText(context,"wifi已连接",Toast.LENGTH_SHORT).show();}
            else {Toast.makeText(context,"wifi未连接",Toast.LENGTH_SHORT).show();}
        }
        localIP = wifiController.getIpAddress();
        StringIP = IP2String(localIP);
        Toast.makeText(context,"IP地址是"+localIP,Toast.LENGTH_SHORT).show();
        multicastLock = wifiController.getWifiLock();
        multicastLock.setReferenceCounted(false);
    }
    private void initListener(){
        if(!wifiController.isWifiable())
            return ;
        if(LocalServerSocket!=null&&!LocalServerSocket.isClosed())
            return ;
        try{
            LocalServerSocket = new ServerSocket(LocalServerPort);
        }catch (Exception e) {
            Log.e("ServerSocket", e.getMessage());
            try{
                LocalServerSocket.close();
            }catch (Exception e2) {
                Log.e("ServerSocket", e2.getMessage());
            }finally {
                LocalServerSocket=null;
            }
        }
        readDispathThread = new Thread(){
            @Override
            public void run() {
                while(!this.isInterrupted()){

                }
            }
        };
    }

    private String IP2String(int ip){
        int[] IPs = new int[4];
        for (int i=0; i<4; ++i) {
            IPs[i] = (ip >> (i*8)) & 0xFF;
        }
        return String.format("%d.%d.%d.%d",
                IPs[0], IPs[1], IPs[2], IPs[3]);
    }
}
