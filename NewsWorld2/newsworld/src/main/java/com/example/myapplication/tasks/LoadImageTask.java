package com.example.myapplication.tasks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.tools.HttpUrlUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by YerningStars on 2015/12/20.
 */
public class LoadImageTask extends AsyncTask{

    private String url;
    private ImageView imageView;
    private Context context;

    public LoadImageTask(ImageView imageView,String url,Context context){
        this.imageView = imageView;
        this.url = url;
        this.context = context;

        //这两部很重要喔
        imageView.setImageResource(R.mipmap.ic_launcher);
        imageView.setTag(url);
    }

    @Override
    protected Object doInBackground(Object[] params) {
        url = (String) params[0];
        byte[] b = null;
        try {
            //Log.i("loadDataTask",HttpUrlUtils.loadData(url));
            b = HttpUrlUtils.loadData(url);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (o!=null){
            byte[] b = (byte[]) o;

            //图片缓存到内存中
            storeImage(b);

            Bitmap bitmap = BitmapFactory.decodeByteArray(b,0,b.length);
            if(url.equals(imageView.getTag()))
                imageView.setImageBitmap(bitmap);
        }
    }

    /**
     *  //图片缓存到内存中
     * @param b
     */
    private void storeImage(byte[] b){
        String fileName = url.substring(url.lastIndexOf("/")+1);
        File path = context.getFilesDir();
        File file = new File(path,fileName);
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(b);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
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
