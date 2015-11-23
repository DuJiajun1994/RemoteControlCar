package sjtudeveloper.remotecontroller;

/**
 * Created by zhongjw on 15-11-21.
 */
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
//import com.iflytek.cloud.SpeechListener;
import com.iflytek.cloud.SpeechRecognizer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VoiceRecognizer{
    private final String JSON = "JSON";

    private String engin = "iat";
    private String resultType = "json";
    private String order;

    private BluetoothController btc;
    private SpeechRecognizer speechRecognizer;
//    private InitListener initListener = new InitListener() {
//        @Override
//        public void onInit(int i) {
//            Log.d(RECOGNIZERTAG,"SpeechRecognizer init() code = "+ i);
//            if(i!= ErrorCode.SUCCESS){
//                Log.e(RECOGNIZERTAG,"error code is "+i);
//            }
//        }
//    };
    private RecognizerListener recognizerListener = new RecognizerListener(){
        @Override
        public void onBeginOfSpeech(){
        }
        @Override
        public void onEndOfSpeech(){

        }
        @Override
        public void onError(SpeechError error){
            Log.d("SpeechRecognizer"," error happend error code is "+error.getErrorCode());
        }
        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj){

        }
        @Override
        public void onResult(RecognizerResult result,boolean islast){
            String resultText = result.getResultString();
            try{
                JSONObject jsonObject;
                JSONArray jsonArray = new JSONObject(resultText).getJSONArray("ws");
                for (int i=0;i<jsonArray.length();++i){
                    jsonObject = (JSONObject) jsonArray.get(i);
                    JSONArray jsonArray1 = jsonObject.getJSONArray("cw");
                    JSONObject jsonObject1;
                    for (int j=0;j<jsonArray1.length();++j){
                        jsonObject1 = (JSONObject) jsonArray1.get(j);
                        order = jsonObject1.getString("w");
                        Log.d(JSON,order);
                        if (order.indexOf("前进")!=-1||order.indexOf("直走")!=-1||order.indexOf("往前")!=-1)btc.sendOrder("U");
                        else if (order.indexOf("后退")!=-1||order.indexOf("向后")!=-1||order.indexOf("倒退")!=-1)btc.sendOrder("D");
                        else if (order.indexOf("左")!=-1)btc.sendOrder("L");
                        else if (order.indexOf("右")!=-1)btc.sendOrder("R");
                        else if (order.indexOf("停")!=-1||order.indexOf("结束")!=-1)btc.sendOrder("S");
                    }
                }
            }catch (JSONException je){
                Log.e(JSON,"josn Exception happened");
                je.printStackTrace();
            }
        }
        @Override
        public void onVolumeChanged(int volume,byte [] data){
            Log.d("VolumeChanged", "volume is "+volume);
        }
    };
    VoiceRecognizer(Context context,BluetoothController bct){
        speechRecognizer = SpeechRecognizer.createRecognizer(context,null);
        speechRecognizer.setParameter(SpeechConstant.DOMAIN, engin);
        speechRecognizer.setParameter(SpeechConstant.RESULT_TYPE, resultType);
        speechRecognizer.setParameter(SpeechConstant.ENGINE_TYPE,"cloud");
        this.btc = bct;

    }

    public void start(){
        int er;
        er = speechRecognizer.startListening(recognizerListener);
        if (er!=ErrorCode.SUCCESS){
            Log.e("startListener","error code is "+er);
        }
    }

    public void stop(){
        if (speechRecognizer.isListening()){
            Log.d("stopListener","stop recognizerListener");
            speechRecognizer.stopListening();
        }
        else
            Log.d("stopListener","speechllistener is not start");
    }

//    public void unRegisterUser(){
//        SpeechUtility.getUtility().destroy();
//    }

//    private SpeechListener listener = new SpeechListener() {
//        @Override
//        public void onEvent(int i, Bundle bundle) {
//
//        }
//
//        @Override
//        public void onBufferReceived(byte[] bytes) {
//
//        }
//
//        @Override
//        public void onCompleted(SpeechError speechError) {
//            if(speechError != null) {
//                Log.d(LISTENERTAG," sign in failed");
//            }
//        }
//    };
}