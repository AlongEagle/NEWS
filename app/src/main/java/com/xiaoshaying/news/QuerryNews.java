package com.xiaoshaying.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class QuerryNews extends AppCompatActivity {

private Button button;
    private EditText editText;

    String key="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_querry_news);

        button= (Button) findViewById(R.id.buttonPanel);
        editText= (EditText) findViewById(R.id.text);


    }


    public void qNew(View view){

        key=editText.getText().toString();

        Intent intent=new Intent(this,MainActivity.class);
        intent.putExtra("key",key);
        startActivity(intent);
    }
}
