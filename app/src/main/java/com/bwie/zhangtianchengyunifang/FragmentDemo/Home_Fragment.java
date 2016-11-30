package com.bwie.zhangtianchengyunifang.FragmentDemo;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bwie.zhangtianchengyunifang.BaseDataDemo.BaseData;
import com.bwie.zhangtianchengyunifang.BaseDataDemo.BaseFragment;
import com.bwie.zhangtianchengyunifang.Utils.URLUtils;
import com.bwie.zhangtianchengyunifang.ViewDemo.ShowingPage;

/**
 * Created by lwj on 2016/11/28.
 */
public class Home_Fragment extends BaseFragment {
    public String data;
    private MyHomeData myHomeData;
    @Override
    protected void onLoad() {
        myHomeData = new MyHomeData();
        myHomeData.getData(URLUtils.homeUrl,URLUtils.homeArgs,0, BaseData.NORMALTIME);
    }

    @Override
    public View creatSuccessView() {
        TextView textView=new TextView(getActivity());
        textView.setText(data);
        Log.e("aaaaaa", "creatSuccessView: "+data );
        return textView;
    }

    class MyHomeData extends BaseData{

        @Override
        public void setResultData(String data) {
            //先设置数据
            Home_Fragment.this.data=data;
            //data有可能为空

            //再展示
            Home_Fragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);

        }

        @Override
        protected void setResulttError(ShowingPage.StateType stateLoadError) {
        //
        }
    }
}

