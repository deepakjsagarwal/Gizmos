package com.deepakagarwal.gizmos;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class RulesPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_page);
    }

    public void gameRules(View view){
        Intent intent = new Intent(this,gameRules.class);
        startActivity(intent);
    }

    public void appUse(View view){
        Intent intent = new Intent(this,appUse.class);
        startActivity(intent);
    }
    public void backToGame(View view){
        finish();
    }
}