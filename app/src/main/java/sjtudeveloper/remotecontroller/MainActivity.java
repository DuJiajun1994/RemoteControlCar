package sjtudeveloper.remotecontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener{

    private ImageView motion_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        motion_button = (ImageView)this.findViewById(R.id.motion_button);

        Button btn = (Button)this.findViewById(R.id.stop_button);
        btn.setOnClickListener(this);
        btn = (Button)this.findViewById(R.id.top_button);
        btn.setOnClickListener(this);

        motion_button.setOnTouchListener(this);
    }

    @Override
    public void onClick(View v){
        ImageView iv;
        switch(v.getId())
        {
            case R.id.stop_button:
                iv = (ImageView)this.findViewById(R.id.motion_button);
                iv.setImageResource(R.drawable.stop);
                Log.i("MainActivity", "Width: " + iv.getHeight());
                break;
            case R.id.top_button:
                iv = (ImageView)this.findViewById(R.id.motion_button);
                iv.setImageResource(R.drawable.speed_up);
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
}