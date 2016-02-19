package com.example.myapplication.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.tasks.LoadImageTask;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YerningStars on 2015/12/20.
 */
public class MyAdapter  extends BaseAdapter{

    private Context context;
    private List<Map<String,Object>> list;
    private  String url;

    public MyAdapter(Context context,List<Map<String,Object>> list,String url){

        this.context = context;
        this.list = list;
        this.url = url;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder holder = null;

        if (convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.listview_items_layout,null);

            holder.imageView = (ImageView) convertView.findViewById(R.id.image);
            holder.textView = (TextView) convertView.findViewById(R.id.tv1);
            holder.textView2 = (TextView) convertView.findViewById(R.id.tv2);
            holder.textView3 = (TextView) convertView.findViewById(R.id.tv3);

            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Map<String,Object> map = (HashMap<String, Object>) getItem(position);

        String rootUrl = url.substring(0,url.lastIndexOf("/api/"));
        String imageUrl2 = map.get("cover").toString();
        String imageUrl = rootUrl + imageUrl2;

        //读取内存图片
        String fileName = imageUrl.substring(imageUrl.lastIndexOf("/")+1);
        File path = context.getFilesDir();
        File file = new File(path,fileName);

        if (file.exists()&&file.length()!=0){
            Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
            holder.imageView.setImageBitmap(bitmap);
        }
        else
            new LoadImageTask(holder.imageView,imageUrl,context).execute(imageUrl);

        holder.textView.setText(map.get("subject").toString());
        holder.textView2.setText(map.get("summary").toString());
        holder.textView3.setText(map.get("changed").toString());
        return convertView;
    }

    public class ViewHolder{
        public ImageView imageView;
        public TextView textView;
        public TextView textView2;
        public TextView textView3;
    }
}
