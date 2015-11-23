package sjtudeveloper.remotecontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener{

    private ImageView motion_button;
    private boolean gravityFlag=false;
    private boolean speechRecognizerFlag=true;
    private GravitySensorManager gravitySensorManager;
    private BluetoothController bct;
    private VoiceRecognizer voiceRecognizer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        motion_button = (ImageView)this.findViewById(R.id.motion_button);

        Button btn = (Button)this.findViewById(R.id.stop_button);
        btn.setOnClickListener(this);
        btn = (Button)this.findViewById(R.id.top_button);
        btn.setOnClickListener(this);
        btn = (Button)this.findViewById(R.id.left_button);
        btn.setOnClickListener(this);
        btn = (Button)this.findViewById(R.id.right_button);
        btn.setOnClickListener(this);
        btn = (Button) findViewById(R.id.speechRecognizer);
        btn.setOnClickListener(this);
        motion_button.setOnTouchListener(this);

        //use bluetooth to connect the car and mobile phone
        bct = new BluetoothController();

        //gravity sensor part
        if(gravityFlag) {
            gravitySensorManager = new GravitySensorManager(MainActivity.this,bct);
            gravitySensorManager.register();
        }
        if(speechRecognizerFlag){
            SpeechUtility.createUtility(MainActivity.this, SpeechConstant.APPID+"=56501737");
            voiceRecognizer = new VoiceRecognizer(MainActivity.this);
            Log.e("debug", "position");
        }
    }

    @Override
    public void onClick(View v){

        ImageView iv;
        switch(v.getId())
        {
            case R.id.stop_button:
                bct.sendOrder("D");
                iv = (ImageView)this.findViewById(R.id.motion_button);
                iv.setImageResource(R.drawable.stop);
                Log.i("MainActivity", "Width: " + iv.getHeight());
                break;
            case R.id.top_button:
                bct.sendOrder("U");
                iv = (ImageView)this.findViewById(R.id.motion_button);
                iv.setImageResource(R.drawable.speed_up);
                break;
            case R.id.left_button:
                bct.sendOrder("L");
                break;
            case R.id.right_button:
                bct.sendOrder("R");
                break;
            case R.id.speechRecognizer:
                if(speechRecognizerFlag)
                {voiceRecognizer.start();speechRecognizerFlag=false;}
                else
                {voiceRecognizer.stop();speechRecognizerFlag=true;}

                break;
            default:
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event){
        switch(event.getAction())
        {
        case MotionEvent.ACTION_DOWN:
                motion_button.setImageResource(R.drawable.speed_up);
                break;
            case MotionEvent.ACTION_UP:
                motion_button.setImageResource(R.drawable.default_stop);
                break;
            default:
                break;
        }
        Log.i("MainActivity", "Touch x: " + event.getX());
        Log.i("MainActivity", "Touch y: " + event.getY());
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(gravitySensorManager!=null){
            gravitySensorManager.unregister();}
        if(SpeechUtility.getUtility()!=null){
            SpeechUtility.getUtility().destroy();
        }
    }
}
