package com.xiaoshaying.news;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mlistView;

    private String url="http://www.imooc.com/api/teacher?type=4&num=90";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent=getIntent();
//
//        url=url+intent.getStringExtra("key");

        mlistView= (ListView) findViewById(R.id.listview);

        new NewsAsyncTask().execute(url);


    }

    private List<NewsBean> getData(String url) {
        List<NewsBean> newsBeanList=new ArrayList<>();



        String jsonString;

        JSONObject jsonObject;
        try {
            jsonString = readStream(new URL(url).openStream());

            jsonObject=new JSONObject(jsonString);


            JSONArray jsonArray=jsonObject.getJSONArray("data");

            for(int i=0;i<jsonArray.length();i++){
                jsonObject=jsonArray.getJSONObject(i);


                NewsBean newsBean=new NewsBean();
                newsBean.content=jsonObject.getString("description");
                newsBean.title=jsonObject.getString("name");
                newsBean.newIconUrl=jsonObject.getString("picSmall");


                newsBeanList.add(newsBean);
            }


            Log.d("diy",jsonString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return newsBeanList;

    }

    private String readStream(InputStream is){
        String line="";
        String res="";
        try {
            InputStreamReader isr=new InputStreamReader(is,"utf-8");
            BufferedReader bufferReader=new BufferedReader(isr);


            while((line=bufferReader.readLine())!=null){

                res+=line;


            }

            isr.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return res;

    }

    class NewsAsyncTask extends AsyncTask<String,Void,List<NewsBean>>{
        @Override
        protected List<NewsBean> doInBackground(String... params) {
            return getData(params[0]);
        }


        @Override
        protected void onPostExecute(List<NewsBean> list) {
            super.onPostExecute(list);

           NewsAdaptor newsAdapter=new NewsAdaptor(MainActivity.this,list);

            mlistView.setAdapter(newsAdapter);
        }
    }
}
