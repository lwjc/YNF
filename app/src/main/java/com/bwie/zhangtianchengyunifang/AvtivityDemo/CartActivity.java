package com.bwie.zhangtianchengyunifang.AvtivityDemo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.Window;
import android.widget.RadioButton;

import com.bwie.zhangtianchengyunifang.R;
import com.zhy.autolayout.AutoLinearLayout;

public class CartActivity extends FragmentActivity implements View.OnClickListener {

    private RadioButton button1;
    private RadioButton button2;
    private AutoLinearLayout layout1;
    private AutoLinearLayout layout2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_cart);
        button1 = (RadioButton) findViewById(R.id.rbut_login_ynf);
        button2 = (RadioButton) findViewById(R.id.rbut_login_phone);
        layout1 = (AutoLinearLayout) findViewById(R.id.ll_ynf_login);
        layout2 = (AutoLinearLayout) findViewById(R.id.ll_phone_login);
        
        button1.setOnClickListener(this);
        button2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rbut_login_ynf:
                layout1.setVisibility(View.VISIBLE);
                layout2.setVisibility(View.GONE);
                break;
            case  R.id.rbut_login_phone:
                layout1.setVisibility(View.GONE);
                layout2.setVisibility(View.VISIBLE);

                break;
        }
    }
}
