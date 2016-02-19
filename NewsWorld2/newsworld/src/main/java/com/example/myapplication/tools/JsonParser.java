package com.example.myapplication.tools;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YerningStars on 2015/12/20.
 */
public class JsonParser {

    public static List<Map<String,Object>> parserJson(String json) throws Exception {
        List<Map<String,Object>> list = null;


        JSONObject jsonObject = null;
        JSONArray array = null;

        try {
            jsonObject = new JSONObject(json);
            if (jsonObject.get("status").toString().equals("ok")){
                jsonObject = jsonObject.getJSONObject("paramz");
                array = jsonObject.getJSONArray("feeds");

                list = new ArrayList<Map<String, Object>>();

                for (int i=0;i<array.length();i++){
                    jsonObject = array.getJSONObject(i);

                    Map<String,Object> map = new HashMap<String, Object>() ;

                    //解析正文地址
                    jsonObject.get("category");
                    map.put("oid", jsonObject.get("oid"));
                    map.put("category",jsonObject.get("category"));

                    //解析标题内容
                    jsonObject = jsonObject.getJSONObject("data");

                    map.put("subject",jsonObject.get("subject"));
                    map.put("summary",jsonObject.get("summary"));
                    map.put("cover",jsonObject.get("cover"));//解析图片地址
                    map.put("changed",jsonObject.get("changed"));

                    list.add(map);
                }
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception();
        }
        return  null;
    }


}
