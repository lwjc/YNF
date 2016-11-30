package com.bwie.zhangtianchengyunifang.Factory;

import android.support.v4.app.Fragment;

import com.bwie.zhangtianchengyunifang.FragmentDemo.Cart_Fragment;
import com.bwie.zhangtianchengyunifang.FragmentDemo.Category_Fragment;
import com.bwie.zhangtianchengyunifang.FragmentDemo.Home_Fragment;
import com.bwie.zhangtianchengyunifang.FragmentDemo.Mine_Fragment;

import java.util.HashMap;

/**
 * Created by lwj on 2016/11/28.
 */
public class FragmentFactory {
    //创建一个map结合用来存取值
    public static HashMap<Integer, Fragment> fragmentHashMap = new HashMap<>();

    public static Fragment getFragment(int position) {
        Fragment fragment = fragmentHashMap.get(position);
        if (fragment != null) {
            return fragment;
        }
        switch (position) {
            case 0:
                fragment = new Home_Fragment();
                break;
            case 1:
                fragment = new Category_Fragment();
                break;
            case 2:
                fragment = new Cart_Fragment();
                break;
            case 3:
                fragment = new Mine_Fragment();
                break;
        }
        //添加到集合中
        fragmentHashMap.put(position, fragment);
        return fragment;
    }
}
