package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.example.myapplication.fragments.ContentFragment;
import com.example.myapplication.fragments.TitleFragment;
import com.example.myapplication.tools.MyFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    private static ViewPager viewPager;
    private android.support.v4.app.FragmentManager fragmentManager;
    private List<Fragment> fragments;
    private TitleFragment titleFragment;

    private int k;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

        //初始化数据
        initView();
        initDate();

        MyFragmentAdapter adapter = new MyFragmentAdapter(fragmentManager,fragments);
        viewPager.setAdapter(adapter);

        //设置滑动监听器
        viewPager.setOnPageChangeListener(this);
    }

    private void initView(){
        fragmentManager = getSupportFragmentManager();
       android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();

        titleFragment = new TitleFragment();

        transaction.replace(R.id.linearLayout1,titleFragment,"title");
        transaction.commit();//加载标题

        viewPager = (ViewPager) findViewById(R.id.viewPager);

    }

    private void initDate(){
        fragments = new ArrayList<>();

        ContentFragment contentFragment = new ContentFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("key",0);
        contentFragment.setArguments(bundle);
        fragments.add(contentFragment);

        contentFragment = new ContentFragment();
        bundle = new Bundle();
        bundle.putInt("key",1);
        contentFragment.setArguments(bundle);
        fragments.add(contentFragment);

        contentFragment = new ContentFragment();
        bundle = new Bundle();
        bundle.putInt("key",2);
        contentFragment.setArguments(bundle);
        fragments.add(contentFragment);

        contentFragment = new ContentFragment();
        bundle = new Bundle();
        bundle.putInt("key",3);
        contentFragment.setArguments(bundle);
        fragments.add(contentFragment);

        contentFragment = new ContentFragment();
        bundle = new Bundle();
        bundle.putInt("key",4);
        contentFragment.setArguments(bundle);
        fragments.add(contentFragment);

        contentFragment = new ContentFragment();
        bundle = new Bundle();
        bundle.putInt("key",5);
        contentFragment.setArguments(bundle);
        fragments.add(contentFragment);

        contentFragment = new ContentFragment();
        bundle = new Bundle();
        bundle.putInt("key",6);
        contentFragment.setArguments(bundle);
        fragments.add(contentFragment);

        contentFragment = new ContentFragment();
        bundle = new Bundle();
        bundle.putInt("key",7);
        contentFragment.setArguments(bundle);
        fragments.add(contentFragment);

        contentFragment = new ContentFragment();
        bundle = new Bundle();
        bundle.putInt("key",8);
        contentFragment.setArguments(bundle);
        fragments.add(contentFragment);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        List<TextView> list = titleFragment.getViews();

        for (int i = 0; i < list.size(); i++) {
            list.get(i).setTextColor(Color.parseColor("#000000"));
        }

        TextView textView = list.get(position);
        textView.setTextColor(Color.WHITE);

        /**
         * 下面的代码为了实现ScrollView和ViewPager的同步移动
         *
         * 研究之后发现，若没有设定多分辨率支持的话，
         *Android系统会将240x320的低密度（120）尺寸转换为中等密度（160）对应的尺寸，
         *这样的话就大大影响了程序的编码。所以，需要在工程的AndroidManifest.xml文件中，
         *加入supports-screens节点
         */
        //初始化ScrollView
        HorizontalScrollView scrollView = (HorizontalScrollView) findViewById(R.id.scrollView);
        DisplayMetrics dm = new DisplayMetrics();
        //获取窗口属性
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        //窗口的宽度
        int screenWidth = dm.widthPixels;
        //视图的宽和高
        int x = textView.getWidth();
        int y = textView.getHeight();
        //int k = 0;应将k值设为成员变量。由k值所在的条件可知，设置它只能匹配某一固定的屏幕，去掉它
        //也可以，只是点击标题的最后一页的最左边的条目，不能弹出隐藏的条目，问题不大

        if(textView.getId() == R.id.textView5&&scrollView.getWidth()- k < screenWidth){
            scrollView.smoothScrollTo(textView.getLeft()-x,y);
        }else if(textView.getRight()+x>screenWidth) {
            if (textView.getId() == R.id.textView5){
                k = scrollView.getScrollX();
            }
            scrollView.smoothScrollTo(textView.getLeft()+x,y);
        }

        else if(textView.getLeft()+x<screenWidth){

            scrollView.smoothScrollTo(textView.getLeft()-x,y);
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public static ViewPager getViewPager(){
        return viewPager;
    }
}
