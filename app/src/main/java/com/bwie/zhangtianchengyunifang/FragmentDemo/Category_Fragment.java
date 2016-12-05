package com.bwie.zhangtianchengyunifang.FragmentDemo;

import android.view.View;

import com.bwie.zhangtianchengyunifang.BaseDataDemo.BaseData;
import com.bwie.zhangtianchengyunifang.BaseDataDemo.BaseFragment;
import com.bwie.zhangtianchengyunifang.R;
import com.bwie.zhangtianchengyunifang.Utils.URLUtils;
import com.bwie.zhangtianchengyunifang.ViewDemo.ShowingPage;

/**
 * Created by lwj on 2016/11/28.
 */
public class Category_Fragment extends BaseFragment {
    public String data;
    private MyHomeData myHomeData;
    @Override
    protected void onLoad() {
        myHomeData = new MyHomeData();
        myHomeData.getData(URLUtils.homeUrl,URLUtils.homeArgs,0, BaseData.NORMALTIME);

    }

    @Override
    public View creatSuccessView() {
      View view=View.inflate(getActivity(), R.layout.fragment_classify_layout,null);
        return view;
    }
    class MyHomeData extends BaseData{

        @Override
        public void setResultData(String data) {
            //先设置数据
            Category_Fragment.this.data=data;
            //data有可能为空

            //再展示
            Category_Fragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);

        }

        @Override
        protected void setResulttError(ShowingPage.StateType stateLoadError) {
            //
        }
    }
}
