package com.example.myapplication.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.myapplication.interfaces.GetData;
import com.example.myapplication.tools.HttpUrlUtils;
import com.example.myapplication.tools.JsonParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.Map;


/**
 * Created by YerningStars on 2015/12/20.
 */
public class LoadDataTask extends AsyncTask {

    private String url;
    private List<Map<String,Object>> list;
    private GetData getData;
    private Context context;

    public LoadDataTask(GetData getData,Context context){

        this.getData = getData;
        this.context = context;
    }
    @Override
    protected Object doInBackground(Object[] params) {
        url = (String) params[0];
        //Log.i("loadDataTask",url);
        try {

          // String json  =  HttpUrlUtils.loadData(url);
          // Log.i("loadDataTask", json);
            String json = new String(HttpUrlUtils.loadData(url));
           // Log.i("ttg",json);
           list = JsonParser.parserJson(json);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (o!=null) {
           // Log.i("loadDataTask", ((List<Map<String, Object>>) o).get(1).get("subject").toString());


           list = (List<Map<String, Object>>) o;

            // 将数据缓存在内存中,list以及里面的内容必须实现序列化,即实现Serilizable接口
            storeList(list);

            getData.getList(list);
        }
    }

    /**
     * 应用中数据是存在list中，不需要再存储，存在内存中的目的是为了下次打开应用时，不至于在加载数据时，出现空白
     * 将数据缓存在内存中,list以及里面的内容必须实现序列化,即实现Serilizable接口
     * @param list
     */
    private void storeList(List<Map<String, Object>> list){

       // Log.i("ttg",url.substring(url.indexOf("PageSize=")+9,url.indexOf("&pageIndex=")));

        String fileName = null;
        if(url.substring(url.lastIndexOf("&")).equals("&val=100511D3BE5301280E0992C73A9DEC41")){
            fileName = "list" +
                    url.substring(url.indexOf("column=")+7,url.indexOf("&PageSize="))+".dat";
        }else {
            fileName = "list.data";
        }

        File path = context.getFilesDir();

        File file = new File(path,fileName);

        //若文件存在，且大于5kb，就先删除原来的文件，再建一个，保存数据
        if (file.exists()&&file.length()>1024*5){
            //boolean k =
            context.deleteFile(fileName);
            //Log.i("ttg",""+k);
            file = new File(path,fileName);
            writeFile(file);
        }else {
            writeFile(file);
        }

    }

    /**
     * 向文件中写入数据
     * @param file
     */
    private void writeFile(File file){

        FileOutputStream out = null;
        ObjectOutputStream objectOut = null;

        try {
            out = new FileOutputStream(file,true);//是否需要追加
            objectOut = new ObjectOutputStream(out);
            objectOut.writeObject(list);
            objectOut.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (objectOut!=null){
                try {
                    objectOut.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
