package com.example.myapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.myapplication.R;
import com.example.myapplication.activities.ContentActivity;
import com.example.myapplication.interfaces.GetData;
import com.example.myapplication.tasks.LoadDataTask;
import com.example.myapplication.tools.MyAdapter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * Created by YerningStars on 2015/12/20.
 */
public class ContentFragment extends android.support.v4.app.ListFragment implements GetData{

    public List<Map<String,Object>> list;

    private ListView listView;

    private MyAdapter adapter;

    private boolean loadType= false;//初次加载数据

    //为了实现分页查询，对地址进行拼接
    private int column;
    private int pageSize;
    private int pageIndex;
    private  String parentPath = "http://litchiapi.jstv.com";
    private String endPath = "&val=100511D3BE5301280E0992C73A9DEC41";
    private String midPath;

   // String url = "http://litchiapi.jstv.com/api/GetFeeds?column=0&PageSize=10&pageIndex=1&val=100511D3BE5301280E0992C73A9DEC41";
    private String url;

    private Button button;//在页面底部增加新的UI
    private Button button2;//在页面中间增加新的UI

    private File file;

    private int code;

    private String fileName;
    private boolean readFlag;//设置是否读取了文件的数据的标志

    /**
     * 加载Fragment的布局文件，无数据，内容为空
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_fragment_layout,container,false);

        return view;
    }

    /**
     * 启动异步任务加载数据
     * 设置滑动接听器，实现分页加载
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();

        choiceUrl();
/*
        initFile();//得到fileName

        if (file.exists()&&file.length()!=0){
            list = readList();//读取内存的数据
            if (list!=null){
                adapter = new MyAdapter(getActivity(), list, url);
                listView.setAdapter(adapter);
                readFlag = true;//读取了文件
            }
        }

         //打开应用时，获取当前网络的状态
        if (!TextUtils.isEmpty(url)){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    URL urlObject = null;
                    HttpURLConnection connection = null;
                    try {
                        urlObject = new URL(url);
                        connection = (HttpURLConnection) urlObject.openConnection();
                       // connection.setConnectTimeout(3000);
                        code = connection.getResponseCode();
                    } catch (Exception e) {
                        e.printStackTrace();
                        //noInternet = "请打开网络";
                    }finally {
                        connection.disconnect();
                    }
                }
            }).start();
        }*/

       new LoadDataTask(this, getContext()).execute(url);//加载数据

      /*  SystemClock.sleep(500);//等待网络请求的回复

       if (code==200){//网络正常，刷新
           getContext().deleteFile(fileName);//有网时，删除上次加载的数据，更新数据

            new LoadDataTask(this, getContext()).execute(url);//加载数据
        }
*/
        //设置滑动监听器
        setListener();

    }

    /**
     * 根据ViewPager穿过来的key值，选择不同的url,加载不同的内容
     */
    private void choiceUrl(){

        Bundle bundle = getArguments();
        int key = bundle.getInt("key");

        //第一次加载数据
        column = 0;
        pageSize = 10;
        pageIndex = 1;

        switch (key){

            case 0:
                midPath = "/api/GetFeeds?column="+ column + "&PageSize=" + pageSize + "&pageIndex=" + pageIndex;
                url = parentPath + midPath +endPath;
                break;
            case 1:
                column = 1;
                midPath = "/api/GetFeeds?column="+ column + "&PageSize=" + pageSize + "&pageIndex=" + pageIndex;
                url = parentPath + midPath +endPath;
                break;
            case 2:
                column = 3;
                midPath = "/api/GetFeeds?column="+ column + "&PageSize=" + pageSize + "&pageIndex=" + pageIndex;
                url = parentPath + midPath +endPath;
                break;
            case 3:
                column = 4;
                midPath = "/api/GetFeeds?column="+ column + "&PageSize=" + pageSize + "&pageIndex=" + pageIndex;
                url = parentPath + midPath +endPath;
                break;
            case 4:
                column = 5;
                midPath = "/api/GetFeeds?column="+ column + "&PageSize=" + pageSize + "&pageIndex=" + pageIndex;
                url = parentPath + midPath +endPath;
                break;
            case 5:
                column = 6;
                midPath = "/api/GetFeeds?column="+ column + "&PageSize=" + pageSize + "&pageIndex=" + pageIndex;
                url = parentPath + midPath +endPath;
                break;
            case 6:
                column = 7;
                midPath = "/api/GetFeeds?column="+ column + "&PageSize=" + pageSize + "&pageIndex=" + pageIndex;
                url = parentPath + midPath +endPath;
                break;
            case 7:
                column = 8;
                midPath = "/api/GetFeeds?column="+ column + "&PageSize=" + pageSize + "&pageIndex=" + pageIndex;
                url = parentPath + midPath +endPath;
                break;
            case 8:
                midPath = "/api/GetMixedFeeds?1=1&PageSize="+ pageSize + "&pageIndex=" + pageIndex;
                endPath = "&val=643EA9065D9714F5D71291A94D8D4403";
                url = parentPath + midPath +endPath;
                break;

        }
    }

    /**
     * 初始化控件
     */
    private void initView(){

        listView = getListView();//注意ListView要在这里获取，在Activity创建的时候
        //View view2 = getActivity().getLayoutInflater().inflate(R.layout.listview_items_layout,null);
        //怎样将view2在载中间界面

        //添加新的UI控件
        View view =  getActivity().getLayoutInflater().inflate(R.layout.button_layout,null);
        listView.addFooterView(view);

        button = (Button) view.findViewById(R.id.add_button);
    }

    /**
     * 设置滑动监听器
     */
    public void setListener(){
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {

            private boolean flag;//加载的数据是否显示完成

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                //已加载的数据已经显示完，用户滑动屏幕，并松开了手，需要加载后面的数据
                if(flag&&scrollState==SCROLL_STATE_IDLE){//分页加载数据，开始加载后面10条数据
                    pageIndex++;
                   String midPath = "/api/GetFeeds?column="+ column + "&PageSize=" + pageSize + "&pageIndex=" + pageIndex;
                    url = parentPath + midPath +endPath;

                    loadType = true;
                    button.setVisibility(Button.VISIBLE);
                    new LoadDataTask(ContentFragment.this,ContentFragment.this.getContext()).execute(url);
                    //接下来需要更改数据源和通知适配器，在getList()中完成
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem + visibleItemCount == totalItemCount)
                    flag = true;//已加载的数据已经显示完，需要加载后面的数据
                else
                    flag = false;//已加载的数据还没有显示完，不需要加载后面的数据
            }
        });

    }


    /**
     * 设置点击监听事件
     */
    public void setClickListener(){

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String,Object> map = list.get(position);
                //内容的Url
               String contentUrl = parentPath + "/wap/" +
                       map.get("category") + "/" + map.get("oid");

                //页面跳转
                Intent intent = new Intent(ContentFragment.this.getContext(), ContentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("key",contentUrl);
                intent.putExtra("bundle",bundle);
                startActivity(intent);

            }
        });
    }

    /**
     * 该Fragment回调的方法，从而将异步下载的数据传进来
     * 设置适配器，将数据加载到界面上
     * @param list
     */
   @Override
    public void getList(List<Map<String, Object>> list) {

       if (!loadType) {//第一次加载时调用

           this.list = list;//注意位置
           /* if(readFlag){//读取了文件
                adapter.notifyDataSetChanged();
            }
           else{//文件不存在
                adapter = new MyAdapter(getActivity(), list, url);
                listView.setAdapter(adapter);
            }*/


           adapter = new MyAdapter(getActivity(), list, url);
           listView.setAdapter(adapter);

       } else if (list!=null){//加载后面的数据

           this.list.addAll(list);//在原有的数据源中追加数据
           adapter.notifyDataSetChanged();
           //通知适配器
       }else {
           //两种情况，一种没数据了，第二种网络异常
           //显示网络异常，用一个悬浮窗体实现，该窗体在没有网络的时候或者再页面还没加载的
           // 时候显示“请稍等，正在为您拼命加载中...",10s后，
           // 若还不能加载数据，就显示“网络已断开连接，请检查您的网络...”，其他时候隐藏

       }

       setClickListener();

    }

    //初始化文件对象
    private void initFile(){

        File path = getContext().getFilesDir();

        fileName = null;
        if(url.substring(url.lastIndexOf("&")).equals("&val=100511D3BE5301280E0992C73A9DEC41")){
            fileName = "list" +
                    url.substring(url.indexOf("column=")+7,url.indexOf("&PageSize="))+".dat";
        }else {
            fileName = "list.data";
        }

        file = new File(path,fileName);
    }

    /**
     * 读取内存的数据
     *
     */
    private List<Map<String, Object>> readList(){

        List<Map<String, Object>> list = null;

            FileInputStream in = null;
            ObjectInputStream objectIn = null;

            try {
                in = new FileInputStream(file);
                objectIn = new ObjectInputStream(in);
                list = (List<Map<String, Object>>) objectIn.readObject();
                return list;
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(objectIn!=null){
                    try {
                        objectIn.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (in!=null){
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        return null;
    }

}
