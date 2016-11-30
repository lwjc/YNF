package com.bwie.zhangtianchengyunifang.ViewDemo;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.bwie.zhangtianchengyunifang.R;
import com.bwie.zhangtianchengyunifang.Utils.CommonUtils;

/**
 * Created by lwj on 2016/11/28.
 */
public abstract class ShowingPage extends FrameLayout implements View.OnClickListener {
    //初始化的物种状态
    public static final int STATE_UNLOAD = 1;
    public static final int STATE_LOADING = 2;
    public static final int STATE_LOAD_ERROR = 3;
    public static final int STATE_LOAD_EMPTY = 4;
    public static final int STATE_LOAD_SUCCESS = 5;
    //定义初始状态
    public int currentState = STATE_UNLOAD;
    private View showingpage_unload;
    private View showingpage_load_error;
    private View showingpage_load_empty;
    private View showingpage_load_loading;
    private View showingpage_success;
    private LayoutParams layoutParams;

    public ShowingPage(Context context) {
        super(context);
        layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        //判断初始化数据
        if (showingpage_load_empty == null) {
            showingpage_load_empty = CommonUtils.inflate(R.layout.showingpage_load_empty);
            this.addView(showingpage_load_empty, layoutParams);
        }
        //初始化正在加载的数据
        if (showingpage_load_loading == null) {
            showingpage_load_loading = CommonUtils.inflate(R.layout.showingpage_loading);
            this.addView(showingpage_load_loading, layoutParams);
        }
        //初始化加载错误的数据
        if (showingpage_load_error == null) {
            showingpage_load_error = CommonUtils.inflate(R.layout.showingpage_load_error);
            Button button = (Button) showingpage_load_error.findViewById(R.id.bt_reload);
            button.setOnClickListener(this);
            this.addView(showingpage_load_error, layoutParams);
        }
      /*  //初始化未加载状态的数据
        if (showingpage_unload == null) {
            showingpage_unload = CommonUtils.inflate(R.layout.showingpage_load_empty);
            this.addView(showingpage_unload, layoutParams);
        }*/
        //添加到layoutParams中展示
        showPage();
        //加载数据
        onLoad();
    }

    private void showPage() {
        //只让它在主线程中显示
        CommonUtils.runOnMainThread(new Runnable() {
            @Override
            public void run() {
                //展示加载页面效果
                showUiPage();
            }
        });

    }

    private void showUiPage() {
        //根据当前状态判断显示进行展示、
    /*    if (showingpage_unload != null) {
            showingpage_unload.setVisibility(currentState == STATE_UNLOAD ? View.VISIBLE : View.GONE);
        }*/
        if (showingpage_load_loading != null) {
            showingpage_load_loading.setVisibility(currentState == STATE_LOADING ||currentState==STATE_UNLOAD? View.VISIBLE : View.GONE);
        }
        if (showingpage_load_error != null) {
            showingpage_load_error.setVisibility(currentState == STATE_LOAD_ERROR ? View.VISIBLE : View.GONE);
        }
        if (showingpage_load_empty != null) {
            showingpage_load_empty.setVisibility(currentState == STATE_LOAD_EMPTY ? View.VISIBLE : View.GONE);
        }
        //如果是成功的状态但是没有成功的页面
        if (showingpage_success == null && currentState == STATE_LOAD_SUCCESS) {
            //重新加载成功的界面
            showingpage_success = creatSuccessView();
            //添加显示
            this.addView(showingpage_success, layoutParams);
        }
        //判断是否是成功状态
        if (showingpage_success!= null) {
            showingpage_success.setVisibility(currentState == STATE_LOAD_SUCCESS ? View.VISIBLE : View.GONE);
        }


    }

    public abstract View creatSuccessView();

    //创建成功的界面
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_reload:
                resetView();
                break;

        }
    }

    //加载重置的界面
    private void resetView() {
        //判断如果不是初始状态那么就重置
        if (currentState != STATE_UNLOAD) {
            currentState = STATE_UNLOAD;
        }
        //展示界面效果
        showPage();
        //重新加载请求网络后的图片
        onLoad();


    }

    //提供一个请求结束之后，设置当前状态，展示界面的方法  6
    public void showCurrentPage(StateType stateType) {
        //获取枚举中的数字,并赋值给当前类型
        this.currentState = stateType.getCurrentState();
        //展示一下
        showPage();
    }

    protected abstract void onLoad();

    //写一个枚举类
    public enum StateType {
        //请求类型
        STATE_LOAD_ERROR(3), STATE_LOAD_EMPTY(4), STATE_LOAD_SUCCESS(5);
        private final int currentState;

        StateType(int currentState) {
            this.currentState = currentState;
        }

        public int getCurrentState() {
            return currentState;
        }
    }

}
