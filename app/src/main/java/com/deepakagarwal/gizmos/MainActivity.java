package com.deepakagarwal.gizmos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void playNewGame(View view){
        Intent intent = new Intent(this,AskNumberOfPlayers.class);
        startActivity(intent);
    }

    public void goToRulePage(View view){
        Intent intent = new Intent(this,RulesPage.class);
        startActivity(intent);
    }
}