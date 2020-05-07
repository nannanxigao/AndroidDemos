package com.example.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Scroller;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    GestureDetector mGestureDetector;

    /**
     * 手势检测
     * 单击、滑动、长按、双击
     */
    public void gestureDetector() {
        mGestureDetector = new GestureDetector(this, new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                Log.e("@@","gestureDetector onDown");

                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {
                Log.e("@@","gestureDetector onShowPress");

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Log.e("@@","gestureDetector onSingleTapUp");

                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                Log.e("@@","gestureDetector onScroll");

                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                Log.e("@@","gestureDetector onLongPress");

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.e("@@","gestureDetector onFling");

                return false;
            }
        });

        mGestureDetector.setOnDoubleTapListener(new GestureDetector.OnDoubleTapListener() {
            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                Log.e("@@","OnDoubleTapListener onSingleTapConfirmed");

                return false;
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                Log.e("@@","OnDoubleTapListener onDoubleTap");

                return false;
            }

            @Override
            public boolean onDoubleTapEvent(MotionEvent e) {
                Log.e("@@","OnDoubleTapListener onDoubleTapEvent");

                return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mGestureDetector.onTouchEvent(event);

//        Log.e("@@","ACTIONevent.getX():"+event.getX());
//        Log.e("@@","ACTIONevent.getY():"+event.getY());
//        Log.e("@@","ACTIONevent.getRawX():"+event.getRawX());
//        Log.e("@@","ACTIONevent.getRawY():"+event.getRawY());

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("@@","ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("@@","ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e("@@","ACTION_UP");
                break;
        }
        return super.onTouchEvent(event);
    }

    /** mGestureDetector写到onTouch这里并设置setclickable，会拦截ontouchevent
       mGestureDetector写到onTouch这里并不设置setclickable，这里mGestureDetecto只执行ondown onpress onlangpress ontouch 其他失效，并执行ontouchevent
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
//        velocity(event);
//        mGestureDetector.onTouchEvent(event);
        return false;
    }

    /**
     * 速度
     * @param event
     */
    public void velocity(MotionEvent event) {
        VelocityTracker velocityTracker = VelocityTracker.obtain();
        velocityTracker.addMovement(event);
        velocityTracker.computeCurrentVelocity(3000); // 1000ms之内计算 可以随意改
        int x = (int) velocityTracker.getXVelocity(); // 左滑速度>0 右滑>0  （终点-起点）/ 时间段
        int y = (int) velocityTracker.getXVelocity();
        Log.e("@@","velocity: x:" + x + " y:" + y);

        //不使用时：
//        velocityTracker.clear();
//        velocityTracker.recycle();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyView view = (MyView) findViewById(R.id.myView);
        gestureDetector(); // 创建手势检测
        view.setOnTouchListener(this); // 重写ontouchevent 使手势检测生效
//        view.setClickable(true);  // 重新ontouch而不是ontouchevent时，只有设置此项才可以
//        view.setLongClickable(true); // 重新ontouch而不是ontouchevent时，只有设置此项才可以



    }

}
