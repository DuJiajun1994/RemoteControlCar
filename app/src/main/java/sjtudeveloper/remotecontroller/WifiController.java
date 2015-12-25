package sjtudeveloper.remotecontroller;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.util.List;

/**
 * Created by zhongjw on 2015/12/25.
 */
public class WifiController {
    private Context mContext;
    //定义一个WifiManager对象
    private WifiManager wifiManager;
    //定义一个WifiInfo对象
    private WifiInfo wifiInfo;
    //扫描出的网络连接列表
    private List<ScanResult> myWifiLists;
    //网络连接列表
    private List<WifiConfiguration> myWifiConfigurations;
    WifiManager.WifiLock wifiLock;

    public WifiController(Context context){
        mContext = context;
        //取得WifiManager对象
        wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        //取得WifiInfo对象
        wifiInfo = wifiManager.getConnectionInfo();
    }
    //打开wifi
    public void openWifi(){
        if(!wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(true);
        }
    }
    //关闭wifi
    public void closeWifi(){
        if(wifiManager.isWifiEnabled()){
            wifiManager.setWifiEnabled(false);
        }
    }
    // 检查当前wifi状态
    public int checkState(){
        return wifiManager.getWifiState();
    }
    public boolean isWifiConnected(){
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
    }
    public boolean isWifiable(){
        return wifiManager.isWifiEnabled();
    }
    //锁定wifiLock
    public void acquireWifiLock(){
        wifiLock.acquire();
    }
    //解锁wifiLock
    public void releaseWifiLock(){
        if(wifiLock.isHeld()){
            wifiLock.release();
        }
    }
    //创建一个wifiLock
    private void createWifiLock(){
        wifiLock = wifiManager.createWifiLock("wifilock");
    }
    //得到一个wifiLock
    public WifiManager.WifiLock getWifiLock(){
        createWifiLock();
        return wifiLock;
    }
    //得到配置好的网络
    public List<WifiConfiguration> getConfiguration(){
        return myWifiConfigurations;
    }
    //指定配置好的网络进行连接
    public void connetionConfiguration(int index){
        if(index>myWifiConfigurations.size()){
            return ;
        }
        //连接配置好指定ID的网络
        wifiManager.enableNetwork(myWifiConfigurations.get(index).networkId, true);
    }
    public void startScan(){
        wifiManager.startScan();
        //得到扫描结果
        myWifiLists=wifiManager.getScanResults();
        //得到配置好的网络连接
        myWifiConfigurations=wifiManager.getConfiguredNetworks();
    }
    //得到网络列表
    public List<ScanResult> getWifiList(){
        return myWifiLists;
    }
    public String getMacAddress(){
        return (wifiInfo==null)?"NULL":wifiInfo.getMacAddress();
    }
    public String getBSSID(){
        return (wifiInfo==null)?"NULL":wifiInfo.getBSSID();
    }
    public int getIpAddress(){
        return (wifiInfo==null)?0:wifiInfo.getIpAddress();
    }
    //得到连接的ID
    public int getNetWordId(){
        return (wifiInfo==null)?0:wifiInfo.getNetworkId();
    }
    //得到wifiInfo的所有信息
    public String getWifiInfo(){
        return (wifiInfo==null)?"NULL":wifiInfo.toString();
    }
    //添加一个网络并连接
    public void addNetWork(WifiConfiguration configuration){
        int wcgId=wifiManager.addNetwork(configuration);
        wifiManager.enableNetwork(wcgId, true);
    }
    //断开指定ID的网络
    public void disConnectionWifi(int netId){
        wifiManager.disableNetwork(netId);
        wifiManager.disconnect();
    }
}
