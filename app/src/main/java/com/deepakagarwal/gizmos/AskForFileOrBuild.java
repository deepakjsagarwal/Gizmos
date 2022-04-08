package com.deepakagarwal.gizmos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class AskForFileOrBuild extends AppCompatActivity {
    int a;
    String s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_for_file_or_build);
        Intent intent = getIntent();
        a = intent.getIntExtra("isItFiledCard",-1);
        if(a == 1){
            Button file = findViewById(R.id.file);
            file.setVisibility(View.INVISIBLE);
        }
        try{
            s = intent.getStringExtra("tag");
        }
        catch(Exception ignored){

        }
        //ImageView imageView = findViewById(R.id.imageCard);
    }

    public void what(View view){
        Intent resultIntent = new Intent();
        try{
            resultIntent.putExtra("tag",s);
        }
        catch(Exception ignored){

        }
        if(view.getId() == R.id.build){
            if(a==1)
                resultIntent.putExtra("value",2);
            else
                resultIntent.putExtra("value",0);
        }
        else if(view.getId() == R.id.file){
            resultIntent.putExtra("value",1);
        }
        setResult(RESULT_OK,resultIntent);
        finish();
    }

}