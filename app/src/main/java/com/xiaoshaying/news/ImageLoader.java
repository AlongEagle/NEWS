package com.xiaoshaying.news;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016-01-22.
 */
public class ImageLoader  {

    private ImageView imageView;



    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            imageView.setImageBitmap((Bitmap) msg.obj);
        }
    };

    public void showImageByThread(final String imageUrl,ImageView imageView){
        this.imageView=imageView;

        new Thread(){
            @Override
            public void run() {
                super.run();
                Bitmap bitmap=getBitmapFromURL(imageUrl);

                Message message=Message.obtain();

                message.obj=bitmap;

                handler.sendMessage(message);

            }
        }.start();

    }

    public Bitmap getBitmapFromURL(String urlString){
        BufferedInputStream bis=null;
        Bitmap bitmap=null;

        try {
            URL url=new URL(urlString);

            HttpURLConnection connection= (HttpURLConnection) url.openConnection();

             bis=new BufferedInputStream(connection.getInputStream());

           bitmap=BitmapFactory.decodeStream(bis);




        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        }
        return bitmap;
    }
}
