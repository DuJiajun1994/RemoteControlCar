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

public class VoiceRecognizer{
    private final String LISTENERTAG="voiceRecognizerListener";
    private final String RECOGNIZERTAG="InitListener";

    private String engin = "iat";
    private String resultType = "json";

    private SpeechRecognizer speechRecognizer;
    private InitListener initListener = new InitListener() {
        @Override
        public void onInit(int i) {
            Log.d(RECOGNIZERTAG,"SpeechRecognizer init() code = "+ i);
            if(i!= ErrorCode.SUCCESS){
                Log.e(RECOGNIZERTAG,"error code is "+i);
            }
        }
    };
    private RecognizerListener recognizerListener = new RecognizerListener(){
        @Override
        public void onBeginOfSpeech(){
            Log.d(LISTENERTAG,"start speech");
        }
        @Override
        public void onEndOfSpeech(){
            Log.d(LISTENERTAG,"finish speech");
        }
        @Override
        public void onError(SpeechError error){
            Log.d(LISTENERTAG," error happend error code is "+error.getErrorCode());
        }
        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj){

        }
        @Override
        public void onResult(RecognizerResult result,boolean islast){
            String resultText = result.getResultString();
            Log.d(LISTENERTAG,"The result string is "+resultText);
        }
        @Override
        public void onVolumeChanged(int volume,byte [] data){
            Log.d(LISTENERTAG," volume is "+volume);
        }
    };
    VoiceRecognizer(Context context){
        speechRecognizer = SpeechRecognizer.createRecognizer(context,null);
        speechRecognizer.setParameter(SpeechConstant.DOMAIN, engin);
        speechRecognizer.setParameter(SpeechConstant.RESULT_TYPE, resultType);
        speechRecognizer.setParameter(SpeechConstant.ENGINE_TYPE,"cloud");

    }

    public void start(){
        int er;
        er = speechRecognizer.startListening(recognizerListener);
        if (er!=ErrorCode.SUCCESS){
            Log.e("debug","error code is "+er);
        }
    }

    public void stop(){
        if (speechRecognizer.isListening()){
            Log.d("debug","stop recognizerListener");
            speechRecognizer.stopListening();
        }
        else
            Log.d("debug","speechllistener is not start");
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