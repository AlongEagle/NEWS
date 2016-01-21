package com.xiaoshaying.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2016-01-22.
 */
public class NewsAdaptor extends BaseAdapter {
    private List<NewsBean> mList;
    private LayoutInflater mLayoutInflater;

    public NewsAdaptor(Context context,List<NewsBean> list){
        this.mList=list;
        mLayoutInflater=LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;

        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView=mLayoutInflater.inflate(R.layout.item,null);

            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.iv);
            viewHolder.textView1= (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.textView2= (TextView) convertView.findViewById(R.id.tv_content);

            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }


        if(mList.get(position).newIconUrl!=""){

            new ImageLoader().showImageByThread(mList.get(position).newIconUrl,viewHolder.imageView);
        }else {
            viewHolder.imageView.setImageResource(R.drawable.ic_launcher);

        }
        viewHolder.textView1.setText(mList.get(position).title);
        viewHolder.textView2.setText(mList.get(position).content);


        return convertView;
    }


    class ViewHolder {

        ImageView imageView;
        TextView textView1,textView2;

    }
}
