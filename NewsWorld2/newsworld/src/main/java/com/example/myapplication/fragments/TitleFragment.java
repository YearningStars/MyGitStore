package com.example.myapplication.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by YerningStars on 2015/12/20.
 */
public class TitleFragment extends android.support.v4.app.Fragment implements View.OnClickListener{

    private View view;

    private TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9;
    private List<TextView> views;

    private int flag;//当某一个标题被点击时，传给加载内容的Fragment的标志，给据标志选择不同的内容进行加载
    //private int flag2;//同样的内容不同时，传的标志不同，被选中的标题也不一样

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.title_fragment_layout,container,false);

        initView();
        return view;
    }

    //初始化控件
    private void initView(){

        tv1 = (TextView) view.findViewById(R.id.textView1);
        tv2 = (TextView) view.findViewById(R.id.textView2);
        tv3 = (TextView) view.findViewById(R.id.textView3);
        tv4 = (TextView) view.findViewById(R.id.textView4);
        tv5 = (TextView) view.findViewById(R.id.textView5);
        tv6 = (TextView) view.findViewById(R.id.textView6);
        tv7 = (TextView) view.findViewById(R.id.textView7);
        tv8 = (TextView) view.findViewById(R.id.textView8);
        tv9 = (TextView) view.findViewById(R.id.textView9);


        //设置监听器
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
        tv4.setOnClickListener(this);
        tv5.setOnClickListener(this);
        tv6.setOnClickListener(this);
        tv7.setOnClickListener(this);
        tv8.setOnClickListener(this);
        tv9.setOnClickListener(this);

        //进入界面时默认第一条标题被选中、
        tv1.setTextColor(Color.WHITE);

        //传到主线程中与ViewPager同步
        views = new ArrayList<>();
        views.add(tv1);
        views.add(tv2);
        views.add(tv3);
        views.add(tv4);
        views.add(tv5);
        views.add(tv6);
        views.add(tv7);
        views.add(tv8);
        views.add(tv9);

    }

    @Override
    public void onClick(View v) {

        tv1.setTextColor(Color.parseColor("#000000"));
        tv2.setTextColor(Color.parseColor("#000000"));
        tv3.setTextColor(Color.parseColor("#000000"));
        tv4.setTextColor(Color.parseColor("#000000"));
        tv5.setTextColor(Color.parseColor("#000000"));
        tv6.setTextColor(Color.parseColor("#000000"));
        tv7.setTextColor(Color.parseColor("#000000"));
        tv8.setTextColor(Color.parseColor("#000000"));
        tv9.setTextColor(Color.parseColor("#000000"));

        ViewPager viewPager = MainActivity.getViewPager();

        switch (v.getId()){
            case R.id.textView1:
                tv1.setTextColor(Color.WHITE);
                viewPager.setCurrentItem(0);
                break;
            case R.id.textView2:
                tv2.setTextColor(Color.WHITE);
                viewPager.setCurrentItem(1);
                break;
            case R.id.textView3:
                tv3.setTextColor(Color.WHITE);
                viewPager.setCurrentItem(2);
                break;
            case R.id.textView4:
                tv4.setTextColor(Color.WHITE);
                viewPager.setCurrentItem(3);
                break;
            case R.id.textView5:
                tv5.setTextColor(Color.WHITE);
                viewPager.setCurrentItem(4);
                break;
            case R.id.textView6:
                tv6.setTextColor(Color.WHITE);
                viewPager.setCurrentItem(5);
                break;
            case R.id.textView7:
                tv7.setTextColor(Color.WHITE);
                viewPager.setCurrentItem(6);
                break;
            case R.id.textView8:
                tv8.setTextColor(Color.WHITE);
                viewPager.setCurrentItem(7);
                break;
            case R.id.textView9:
                tv9.setTextColor(Color.WHITE);
                viewPager.setCurrentItem(8);
                break;
        }
    }

   public List<TextView> getViews(){
       return views;
   }

}
