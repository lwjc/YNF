package com.bwie.zhangtianchengyunifang.AvtivityDemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bwie.zhangtianchengyunifang.R;

import java.util.ArrayList;

public class LunchActivity extends FragmentActivity implements View.OnClickListener {

    private ViewPager vp;
    private ArrayList<View> list;
    private SharedPreferences sp;
    private LinearLayout image1;
    private RelativeLayout image2;
    private Button button;
    private TextView textView;
    private boolean boo = true;
    private int i = 3;
    Handler handler2 = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            image1.setVisibility(View.GONE);
            vp.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return 4;
                }

                @Override
                public boolean isViewFromObject(View view, Object object) {
                    return view == object;
                }

                @Override
                public Object instantiateItem(ViewGroup container, int position) {
                    container.addView(list.get(position));
                    return list.get(position);
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView(list.get(position));
                }
            });
            image2_vp4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TranslateAnimation translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0);
                    translateAnimation.setDuration(4000);
                    translateAnimation.setFillAfter(true);
                    image2_vp4.startAnimation(translateAnimation);
                    translateAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            AlphaAnimation  alphaAnimation=new AlphaAnimation(1.0f,0);
                            alphaAnimation.setDuration(2000);
                            image1_vp4.startAnimation(alphaAnimation);
                            alphaAnimation.setFillAfter(true);
                            alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                                @Override
                                public void onAnimationStart(Animation animation) {

                                }

                                @Override
                                public void onAnimationEnd(Animation animation) {
                                    Intenttiao();
                                }

                                @Override
                                public void onAnimationRepeat(Animation animation) {

                                }
                            });
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });

                }
            });

        }
    };
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    image1.setVisibility(View.GONE);
                    image2.setVisibility(View.VISIBLE);
                    button.setVisibility(View.VISIBLE);
                    textView.setVisibility(View.VISIBLE);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            for (i = i; i > -1; i--) {
                                SystemClock.sleep(1000);
                                handler.obtainMessage(1, i).sendToTarget();
                            }
                        }
                    }).start();
                    break;
                case 1:
                    if (boo) {
                        int tiem = (int) msg.obj;
                        textView.setText(tiem + "s");
                        if (tiem == 0) {
                            Intenttiao();
                        }
                    }
                    break;
            }


        }
    };
    private ImageView image1_vp4;
    private ImageView image2_vp4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_lunch);
        image1 = (LinearLayout) findViewById(R.id.luch1);
        image2 = (RelativeLayout) findViewById(R.id.luch2);
        button = (Button) findViewById(R.id.button_lunch);
        textView = (TextView) findViewById(R.id.textView_lun);
        vp = (ViewPager) findViewById(R.id.vp_luch);
        sp = getSharedPreferences("name", MODE_PRIVATE);
        boolean flag = sp.getBoolean("is", false);
        button.setOnClickListener(this);
        if (flag) {
            //3秒跳过
            handler.sendEmptyMessageDelayed(0, 3000);

        } else {
            handler2.sendEmptyMessageDelayed(0, 3000);

            SharedPreferences.Editor edit = sp.edit();
            edit.putBoolean("is", true);
            edit.commit();

        }
        init();
    }

    private void init() {
        list = new ArrayList<>();
        View view1 = View.inflate(LunchActivity.this, R.layout.viewpage1, null);
        View view2 = View.inflate(LunchActivity.this, R.layout.viewpage2, null);
        View view3 = View.inflate(LunchActivity.this, R.layout.viewpage3, null);
        View view4 = View.inflate(LunchActivity.this, R.layout.viewpage4, null);
        image1_vp4 = (ImageView) view4.findViewById(R.id.image1_vp4);
        image2_vp4 = (ImageView) view4.findViewById(R.id.image2_vp4);
        list.add(view1);
        list.add(view2);
        list.add(view3);
        list.add(view4);
    }

    @Override
    public void onClick(View v) {
        boo = false;
        Intenttiao();
    }

    public void tiao(View v) {
        Intenttiao();
    }

    private void Intenttiao() {
        Intent in = new Intent(LunchActivity.this, MainActivity.class);
        startActivity(in);
    }


}
