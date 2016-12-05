package com.bwie.zhangtianchengyunifang.ViewDemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by lwj on 2016/11/28.
 */
public class NoScrollViewPager extends LazyViewPager{
    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NoScrollViewPager(Context context) {
        super(context);
    }
   //重写两个方法，不消费
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return false;
    }
   //不阻断
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return false;
    }
}
