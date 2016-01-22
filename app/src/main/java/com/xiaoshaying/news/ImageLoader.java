package com.xiaoshaying.news;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2016-01-22.
 */
public class ImageLoader {

    private String imageUrl;
    private ImageView imageView;

    private LruCache<String, Bitmap> mCache;

    public ImageLoader() {

        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 4;
        mCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };

    }

    public void addToLrc(String url, Bitmap bitmap) {
        if(getFromLrc(url)==null){
            mCache.put(url,bitmap);
        }
    }

    public Bitmap getFromLrc(String url) {


        return   mCache.get(url);
    }


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if (imageView.getTag().equals(imageUrl)) {

                imageView.setImageBitmap((Bitmap) msg.obj);
            }

        }
    };


    public void showImageByAsyncTask(String url, ImageView imageView) {

       Bitmap bitmap=getFromLrc(url);
        if(bitmap==null){

           new MyAsyncTask(imageView, url).execute(url);
       }else {
            imageView.setImageBitmap(bitmap);
        }

    }

    private class MyAsyncTask extends AsyncTask<String, Void, Bitmap> {

        private ImageView imageView;

        private String url;

        public MyAsyncTask(ImageView imageView, String url) {
            this.imageView = imageView;
            this.url = url;
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            Bitmap bitmap=getBitmapFromURL(params[0]);

            if(bitmap!=null){
                addToLrc(params[0],bitmap);
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if (imageView.getTag().equals(url)) {

                imageView.setImageBitmap(bitmap);
            }

        }
    }

    public void showImageByThread(final String imageUrl, ImageView imageView) {
        this.imageView = imageView;

        this.imageUrl = imageUrl;


        new Thread() {
            @Override
            public void run() {
                super.run();
                Bitmap bitmap = getBitmapFromURL(imageUrl);

                Message message = Message.obtain();

                message.obj = bitmap;

                handler.sendMessage(message);

            }
        }.start();

    }

    public Bitmap getBitmapFromURL(String urlString) {
        BufferedInputStream bis = null;
        Bitmap bitmap = null;

        try {
            URL url = new URL(urlString);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            bis = new BufferedInputStream(connection.getInputStream());

            bitmap = BitmapFactory.decodeStream(bis);


            connection.disconnect();
            return bitmap;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }

        return null;
    }
}
