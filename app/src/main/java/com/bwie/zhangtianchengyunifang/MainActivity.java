package com.bwie.zhangtianchengyunifang;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.bwie.zhangtianchengyunifang.Factory.FragmentFactory;

public class MainActivity extends AppCompatActivity {

    private ViewPager vp;
    private RadioGroup rg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //找到控件
        vp = (ViewPager) findViewById(R.id.vp_main);
        rg = (RadioGroup) findViewById(R.id.rg_main);
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
