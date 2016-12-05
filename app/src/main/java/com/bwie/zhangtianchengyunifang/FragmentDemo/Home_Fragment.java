package com.bwie.zhangtianchengyunifang.FragmentDemo;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.zhangtianchengyunifang.AvtivityDemo.goods_Web;
import com.bwie.zhangtianchengyunifang.BaseDataDemo.BaseData;
import com.bwie.zhangtianchengyunifang.BaseDataDemo.BaseFragment;
import com.bwie.zhangtianchengyunifang.BeanDemo.Bean1;
import com.bwie.zhangtianchengyunifang.R;
import com.bwie.zhangtianchengyunifang.Utils.CommonUtils;
import com.bwie.zhangtianchengyunifang.Utils.URLUtils;
import com.bwie.zhangtianchengyunifang.ViewDemo.MyRoolViewPager;
import com.bwie.zhangtianchengyunifang.ViewDemo.ShowingPage;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lwj on 2016/11/28.
 */
public class Home_Fragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private MyHomeData myHomeData;
    public String data;
    private Bean1 bean;
    private LinearLayout ll_dots;
    private MyRoolViewPager myRoolViewPager;
    ArrayList<String> imgUrlList = new ArrayList<>();
    ArrayList<ImageView> dotList = new ArrayList<>();
    /**
     * 小点图片数组
     */
    int[] dotArray = new int[]{R.mipmap.page_indicator_focused, R.mipmap.page_indicator_unfocused};
    private LinearLayout linlay;
    private ViewPager viewPager_home;
    private LinearLayout linlay2;
    private LinearLayout linlay3;
    private LinearLayout linlay4;
    private LinearLayout linlay5;
    private GridView gridView;

    @Override
    protected void onLoad() {
        myHomeData = new MyHomeData();
        myHomeData.getData(URLUtils.homeUrl, URLUtils.homeArgs, 0, BaseData.NORMALTIME);
    }

    @Override
    public View creatSuccessView() {
        View view = CommonUtils.inflate(R.layout.fragment_home_layout);
        ll_dots = (LinearLayout) view.findViewById(R.id.home_fragment_point_group);
        myRoolViewPager = (MyRoolViewPager) view.findViewById(R.id.home_fragment_vp);
        linlay = (LinearLayout) view.findViewById(R.id.more_line);
        linlay2 = (LinearLayout) view.findViewById(R.id.more_line2);
        linlay3 = (LinearLayout) view.findViewById(R.id.more_line3);
        linlay4 = (LinearLayout) view.findViewById(R.id.more_line4);
        linlay5 = (LinearLayout) view.findViewById(R.id.more_line5);

        viewPager_home = (ViewPager) view.findViewById(R.id.viewPager_home);
        gridView = (GridView) view.findViewById(R.id.home_fragment_gridView);
        gridView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return bean.getData().getDefaultGoodsList().size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view=View.inflate(getActivity(),R.layout.week_hot_gv_item2,null);
                ImageView imageView = (ImageView) view.findViewById(R.id.weekhot_gv_item_img);
                TextView textView1 = (TextView)view.findViewById(R.id.weekhot_gv_item_tv);
                TextView textView2 = (TextView) view.findViewById(R.id.weekhot_gv_item_tv2);
                TextView textView3 = (TextView) view.findViewById(R.id.weekhot_gv_item_tv3);
                ImageLoader.getInstance().displayImage(bean.getData().getBestSellers().get(0).getGoodsList().get(position).getGoods_img(), imageView);
                if (bean.getData().getDefaultGoodsList().get(position).getGoods_name().length() > 10) {
                    textView1.setText(bean.getData().getDefaultGoodsList().get(position).getGoods_name().substring(0, 10) + "...");
                } else {
                    textView1.setText(bean.getData().getDefaultGoodsList().get(position).getGoods_name());
                }
                textView2.setText(bean.getData().getDefaultGoodsList().get(position).getMarket_price());
                textView3.setText(bean.getData().getDefaultGoodsList().get(position).getShop_price());
                return view;
            }
        });
        gridView.setOnItemClickListener(this);
        //初始化lin数据加载linlay
        getLinLay(linlay);
        //轮播图
        initRoolViewPager();
        //加载第四部分轮播图
        initViewPager();
        //加载lin2数据
        getLinLay2();
        //加载lin3数据
        getLinLay(linlay3);
        getLinLay4();
        getLinLay5();
        return view;
    }

    private void initViewPager() {
        viewPager_home.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return Integer.MAX_VALUE;

            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = View.inflate(getActivity(), R.layout.bigimage_layout, null);
                ImageView big_image = (ImageView) view.findViewById(R.id.big_image);
                ImageLoader.getInstance().displayImage(bean.getData().getActivityInfo().getActivityInfoList().get(position % bean.getData().getActivityInfo().getActivityInfoList().size()).getActivityImg(), big_image);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        viewPager_home.setCurrentItem(bean.getData().getActivityInfo().getActivityInfoList().size() * 10000);


    }

    /**
     * 初始化viewPager轮播图
     */
    private void initRoolViewPager() {
        List<Bean1.DataBean.Ad1Bean> ad1 = bean.getData().getAd1();
        for (int i = 0; i < ad1.size(); i++) {
            imgUrlList.add(ad1.get(i).getImage());
        }
        initDots(ad1);


        myRoolViewPager.initData(imgUrlList, dotArray, dotList);
        myRoolViewPager.startViewPager();
        myRoolViewPager.setOnPageClickListener(new MyRoolViewPager.OnPageClickListener() {
            @Override
            public void setOnPage(int position) {
//                Intent intent=new Intent();
                Toast.makeText(getActivity(), "我要跳转到详情了", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 初始化小点
     *
     * @param ad1
     */
    private void initDots(List<Bean1.DataBean.Ad1Bean> ad1) {
        dotList.clear();
        ll_dots.removeAllViews();
        for (int i = 0; i < ad1.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            if (i == 0) {
                imageView.setImageResource(dotArray[0]);
            } else {
                imageView.setImageResource(dotArray[1]);
            }
            dotList.add(imageView);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(5, 0, 5, 12);
            ll_dots.addView(imageView, params);
        }
    }

    public void getLinLay(LinearLayout lin) {
        for (int i = 0; i < 6; i++) {
            View view = View.inflate(getActivity(), R.layout.week_hot_gv_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.weekhot_gv_item_img);
            TextView textView1 = (TextView) view.findViewById(R.id.weekhot_gv_item_tv);
            TextView textView2 = (TextView) view.findViewById(R.id.weekhot_gv_item_tv2);
            TextView textView3 = (TextView) view.findViewById(R.id.weekhot_gv_item_tv3);
            ImageLoader.getInstance().displayImage(bean.getData().getBestSellers().get(0).getGoodsList().get(i).getGoods_img(), imageView);
            if (bean.getData().getSubjects().get(0).getGoodsList().get(i).getGoods_name().length() > 10) {
                textView1.setText(bean.getData().getSubjects().get(0).getGoodsList().get(i).getGoods_name().substring(0, 10) + "...");
            } else {
                textView1.setText(bean.getData().getSubjects().get(0).getGoodsList().get(i).getGoods_name());
            }
            textView2.setText(bean.getData().getSubjects().get(0).getGoodsList().get(i).getMarket_price());
            textView3.setText(bean.getData().getSubjects().get(0).getGoodsList().get(i).getShop_price());
            LinearLayout.LayoutParams lay = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lay.setMargins(2, 0, 2, 0);
            lin.addView(view, lay);
        }

    }

    public void getLinLay4() {
        for (int i = 6; i < 12; i++) {
            View view = View.inflate(getActivity(), R.layout.week_hot_gv_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.weekhot_gv_item_img);
            TextView textView1 = (TextView) view.findViewById(R.id.weekhot_gv_item_tv);
            TextView textView2 = (TextView) view.findViewById(R.id.weekhot_gv_item_tv2);
            TextView textView3 = (TextView) view.findViewById(R.id.weekhot_gv_item_tv3);
            ImageLoader.getInstance().displayImage(bean.getData().getBestSellers().get(0).getGoodsList().get(i).getGoods_img(), imageView);
            if (bean.getData().getSubjects().get(0).getGoodsList().get(i).getGoods_name().length() > 10) {
                textView1.setText(bean.getData().getSubjects().get(0).getGoodsList().get(i).getGoods_name().substring(0, 10) + "...");
            } else {
                textView1.setText(bean.getData().getSubjects().get(0).getGoodsList().get(i).getGoods_name());
            }
            textView2.setText(bean.getData().getSubjects().get(0).getGoodsList().get(i).getMarket_price());
            textView3.setText(bean.getData().getSubjects().get(0).getGoodsList().get(i).getShop_price());
            LinearLayout.LayoutParams lay = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lay.setMargins(2, 0, 2, 0);
            linlay4.addView(view, lay);
        }

    }

    public void getLinLay5() {
        for (int i = 13; i > 7; i--) {
            View view = View.inflate(getActivity(), R.layout.week_hot_gv_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.weekhot_gv_item_img);
            TextView textView1 = (TextView) view.findViewById(R.id.weekhot_gv_item_tv);
            TextView textView2 = (TextView) view.findViewById(R.id.weekhot_gv_item_tv2);
            TextView textView3 = (TextView) view.findViewById(R.id.weekhot_gv_item_tv3);
            ImageLoader.getInstance().displayImage(bean.getData().getBestSellers().get(0).getGoodsList().get(i).getGoods_img(), imageView);
            if (bean.getData().getSubjects().get(0).getGoodsList().get(i).getGoods_name().length() > 10) {
                textView1.setText(bean.getData().getSubjects().get(0).getGoodsList().get(i).getGoods_name().substring(0, 10) + "...");
            } else {
                textView1.setText(bean.getData().getSubjects().get(0).getGoodsList().get(i).getGoods_name());
            }
            textView2.setText(bean.getData().getSubjects().get(0).getGoodsList().get(i).getMarket_price());
            textView3.setText(bean.getData().getSubjects().get(0).getGoodsList().get(i).getShop_price());
            LinearLayout.LayoutParams lay = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lay.setMargins(2, 0, 2, 0);
            linlay5.addView(view, lay);
        }

    }

    public void getLinLay2() {
        for (int i = 0; i < 6; i++) {
            View view = View.inflate(getActivity(), R.layout.week_hot_gv_item, null);
            ImageView imageView = (ImageView) view.findViewById(R.id.weekhot_gv_item_img);
            TextView textView1 = (TextView) view.findViewById(R.id.weekhot_gv_item_tv);
            TextView textView2 = (TextView) view.findViewById(R.id.weekhot_gv_item_tv2);
            TextView textView3 = (TextView) view.findViewById(R.id.weekhot_gv_item_tv3);
            ImageLoader.getInstance().displayImage(bean.getData().getSubjects().get(0).getGoodsList().get(i).getGoods_img(), imageView);
            if (bean.getData().getSubjects().get(0).getGoodsList().get(i).getGoods_name().length() > 10) {
                textView1.setText(bean.getData().getSubjects().get(0).getGoodsList().get(i).getGoods_name().substring(0, 10) + "...");
            } else {
                textView1.setText(bean.getData().getSubjects().get(0).getGoodsList().get(i).getGoods_name());
            }
            textView2.setText(bean.getData().getSubjects().get(0).getGoodsList().get(i).getMarket_price());
            textView3.setText(bean.getData().getSubjects().get(0).getGoodsList().get(i).getShop_price());
            LinearLayout.LayoutParams lay = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            lay.setMargins(2, 0, 2, 0);
            this.linlay2.addView(view, lay);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent in=new Intent(getActivity(),goods_Web.class);
        startActivity(in);
    }

    class MyHomeData extends BaseData {


        @Override
        public void setResultData(String data) {
            //先设置数据
            Home_Fragment.this.data = data;
            //data有可能为空
            //再展示
            Gson gson = new Gson();
            bean = gson.fromJson(data, Bean1.class);

            Home_Fragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_SUCCESS);
        }

        @Override
        protected void setResulttError(ShowingPage.StateType stateLoadError) {
            //失败
            Home_Fragment.this.showCurrentPage(ShowingPage.StateType.STATE_LOAD_ERROR);

        }
    }
}



