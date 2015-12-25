package sjtudeveloper.remotecontroller;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener{

    private ImageView motion_button;
    private boolean gravityFlag=false;
    private boolean speechRecognizerFlag=true;
    private GravitySensorManager gravitySensorManager;
    private BluetoothController bct;
    private VoiceRecognizer voiceRecognizer;
    private Button speechRecognizerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        setContentView(new PathView(this));

        motion_button = (ImageView)this.findViewById(R.id.motion_button);
        motion_button.setRotation(90);


        Button btn = (Button)this.findViewById(R.id.stop_button);
        btn.setOnClickListener(this);
        btn = (Button)this.findViewById(R.id.top_button);
        btn.setOnClickListener(this);
        btn = (Button)this.findViewById(R.id.left_button);
        btn.setOnClickListener(this);
        btn = (Button)this.findViewById(R.id.right_button);
        btn.setOnClickListener(this);
        motion_button.setOnTouchListener(this);

        //speechRecognizerButton
        speechRecognizerButton = (Button) findViewById(R.id.speechRecognizer);
        speechRecognizerButton.setOnClickListener(this);

        //use bluetooth to connect the car and mobile phone
        bct = new BluetoothController();

        //gravity sensor part
        if(gravityFlag) {
            gravitySensorManager = new GravitySensorManager(MainActivity.this,bct);
            gravitySensorManager.register();
        }

        //use bluetooth to connect the car and mobile phone
        bct = new BluetoothController();

        if(speechRecognizerFlag){
            SpeechUtility.createUtility(MainActivity.this, SpeechConstant.APPID+"=56501737");
            voiceRecognizer = new VoiceRecognizer(MainActivity.this,bct);
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
                {voiceRecognizer.start();speechRecognizerFlag=false;speechRecognizerButton.setText("Stop Speech");}
                else
                {voiceRecognizer.stop();speechRecognizerFlag=true;speechRecognizerButton.setText("Start Speech");}

                break;
            default:
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event){

        /**
         * information of the picture
         * width: 540px height: 400px
         * center position: center 200px
         *
         */

        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        int screen_width = wm.getDefaultDisplay().getWidth();
        int[] location = new  int[2] ;
        motion_button.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
        int center_X = screen_width / 2;
        int center_Y = 376;                                                                                                              //这个要改

        float p_X = event.getRawX() - center_X;
        float p_Y = event.getRawY() - center_Y;

        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                if(p_Y>=-200&&p_Y<=-120 && p_X>=-65&&p_X<=65) {
                    motion_button.setImageResource(R.drawable.turn_left);
                    bct.sendOrder("L");
                }
                if(p_Y<=200&&p_Y>=120 && p_X>=-65&&p_X<=65){
                    motion_button.setImageResource(R.drawable.turn_right);
                    bct.sendOrder("R");
                }
                if(p_Y>=-90&&p_Y<=90 && p_X>=-60&&p_X<=60){
                    motion_button.setImageResource(R.drawable.stop);
                    bct.sendOrder("S");
                }
                if(p_Y>=-100&&p_Y<=100 && p_X>=80&&p_X<=135){
                    motion_button.setImageResource(R.drawable.speed_up);
                    bct.sendOrder("U");
                }
                if(p_Y>=-100&&p_Y<=100 && p_X<=-80&&p_X>=-135){
                    motion_button.setImageResource(R.drawable.slow_down);
                    bct.sendOrder("D");
                }

                break;
            case MotionEvent.ACTION_UP:
                motion_button.setImageResource(R.drawable.default_stop);
                break;
            default:
                break;
        }
        Log.i("Touch", "Touch raw x: " + event.getRawX());
        Log.i("Touch", "Touch raw y: " + event.getRawY());

        Log.i("Touch", "Touch x: " + event.getX());
        Log.i("Touch", "Touch y: " + event.getY());

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
