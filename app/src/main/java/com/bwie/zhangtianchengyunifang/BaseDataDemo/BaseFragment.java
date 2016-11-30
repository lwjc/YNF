package com.bwie.zhangtianchengyunifang.BaseDataDemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.zhangtianchengyunifang.ViewDemo.ShowingPage;

/**
 * Created by lwj on 2016/11/28.
 */
public abstract class BaseFragment extends Fragment {

    private ShowingPage showingPage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //进行加载，因为我们不知道怎么加载所以继续抽象
        //加载成功加载的页面
//进行加载，进行抽象
        showingPage = new ShowingPage(getContext()) {
            //加载成功加载的页面
            @Override
            public View creatSuccessView() {
                //进行加载，进行抽象
                return BaseFragment.this.creatSuccessView();
            }

            @Override
            protected void onLoad() {

            }
        };
        BaseFragment.this.onLoad();
        return showingPage;
    }

    //加载
    protected abstract void onLoad();

    //展示成功界面抽象方法
    public abstract View creatSuccessView();

    //对外提供一个展示界面的一个方法
    public void showCurrentPage(ShowingPage.StateType stateType) {
        //调用showingpage类里面的方法
        if (showingPage != null) {
            showingPage.showCurrentPage(stateType);

        }


    }


}
