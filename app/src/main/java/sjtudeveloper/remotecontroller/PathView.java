package sjtudeveloper.remotecontroller;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by M_D_Luffy on 2015/11/3.
 */
public class PathView extends View {

    private Path path;
    private Paint paint;

    public PathView(Context context){
        super(context);

        path = new Path();
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e){
        int x, y;
        switch(e.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.reset();
                x = (int)e.getX();
                y = (int)e.getY();
                path.moveTo(x, y);
                invalidate();
                Log.i("PathView", "point 1.1");
                break;
            case MotionEvent.ACTION_MOVE:
                x = (int)e.getX();
                y = (int)e.getY();
                path.lineTo(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                invalidate();
                break;
        }
        return true;
    }

}
