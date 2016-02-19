package com.example.myapplication.tools;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by YerningStars on 2015/12/20.
 */
public class HttpUrlUtils {

    public  static byte[] loadData(String url) throws Exception {

       // String json = null;
        URL urlObject = new URL(url);
        HttpURLConnection connection = null;
        BufferedInputStream in = null;
        try {
            connection = (HttpURLConnection) urlObject.openConnection();
            connection.setDoInput(true);
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);

            if (connection.getResponseCode()==200){
                in = new BufferedInputStream(connection.getInputStream());
               //Log.i("loadDataTask",new String(streamToByte(in)));
              //  json = new String(streamToByte(in));
                //return json;

                return  streamToByte(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }finally {
            if (in!=null){
                in.close();
            }
            if (connection!=null)
                connection.disconnect();
        }
        return  null;
    }

    public static byte[] streamToByte(InputStream in) throws Exception {
        byte[] b = new byte[1024];
        byte[] c = null;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        if (in!=null){
            int num = 0;
            try {
                while ((num=in.read(b))!=-1){
                    out.write(b,0,num);
                    out.flush();
                }
                c = out.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
                throw new Exception();
            }finally {
                if(out!=null)
                    out.close();
            }
          //  Log.i("loadDataTask",new String(out.toByteArray()));
            return c;
        }
        return  null;
    }

}
