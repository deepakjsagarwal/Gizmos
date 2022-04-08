package com.deepakagarwal.gizmos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class Research extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_research);

        Intent intent = getIntent();
    }

    public void level(View view){
        Intent resultIntent = new Intent();
        resultIntent.putExtra("level",Integer.parseInt(String.valueOf(view.getTag())));
        setResult(RESULT_OK,resultIntent);
        finish();
    }
}