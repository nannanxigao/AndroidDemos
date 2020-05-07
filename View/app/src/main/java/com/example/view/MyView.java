package com.example.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.Scroller;

import androidx.annotation.Nullable;

public class MyView extends View {

    Scroller mScroller;
    Context mContext;
    public MyView(Context context) {
        super(context);
        mContext = context;
    }


    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs, 0);
        mScroller = new Scroller(context);
        mContext = context;
    }



    @Override
    public void computeScroll() {
        // 第三步，重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }


}
