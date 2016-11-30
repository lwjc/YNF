package com.bwie.zhangtianchengyunifang.FragmentDemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.zhangtianchengyunifang.R;

/**
 * Created by lwj on 2016/11/28.
 */
public class Mine_Fragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_user_layout,null);
        return view;
    }
}
