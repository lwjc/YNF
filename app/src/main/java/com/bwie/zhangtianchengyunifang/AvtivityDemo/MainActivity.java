package com.bwie.zhangtianchengyunifang.AvtivityDemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bwie.zhangtianchengyunifang.Factory.FragmentFactory;
import com.bwie.zhangtianchengyunifang.R;
import com.bwie.zhangtianchengyunifang.ViewDemo.LazyViewPager;
import com.zhy.autolayout.AutoLayoutActivity;

public class MainActivity extends AutoLayoutActivity{

    private LazyViewPager vp;
    private RadioGroup rg;
    private RadioButton rb_cart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        //找到控件
        vp = (LazyViewPager) findViewById(R.id.vp_main);
        rg = (RadioGroup) findViewById(R.id.rg_main);
        rb_cart = (RadioButton) findViewById(R.id.rb_cart);
        rb_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(MainActivity.this,CartActivity.class);
                startActivity(in);
            }
        });
        //设置ViewPager的适配器
        vp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return FragmentFactory.getFragment(position);
            }

            @Override
            public int getCount() {
                return 4;
            }
        });
        //对按钮进行监听
        //对radioGroup设置监听
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                //设置当前条目索引
//                viewPager.setCurrentItem(i);
                switch (i) {
                    case R.id.rb_home_page:
                        vp.setCurrentItem(0);
                        break;
                    case R.id.rb_category:
                        vp.setCurrentItem(1);
                        break;
                    case R.id.rb_cart:
                        vp.setCurrentItem(2);
                        break;
                    case R.id.rb_mine:
                        vp.setCurrentItem(3);
                        break;

                }
            }
        });
    }
}
