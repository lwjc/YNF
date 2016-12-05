package com.bwie.zhangtianchengyunifang.Application;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

import com.bwie.zhangtianchengyunifang.Utils.ImageLoaderUtils;
import com.zhy.autolayout.config.AutoLayoutConifg;

import org.xutils.x;

/**
 * Created by lwj on 2016/11/28.
 */
public class MyApplication extends Application {

    private static Context context;
    private static Handler handler;
    private static int mainThreadId;

    @Override
    public void onCreate() {
        //上下文对象
        context = getApplicationContext();
        //ImageLoaderUtils的初始化
        ImageLoaderUtils.initConfiguration(getContext());
        //xutils的初始化工作
        x.Ext.init(this);
        x.Ext.setDebug(true);
        //获取主线程的线程号
        mainThreadId = Process.myTid();

        handler = new Handler();
        AutoLayoutConifg.getInstance().useDeviceSize();
        super.onCreate();

    }

    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static int getMainThreadId() {
        return mainThreadId;
    }

    public static Thread getMainThread() {
        return Thread.currentThread();
    }
}
