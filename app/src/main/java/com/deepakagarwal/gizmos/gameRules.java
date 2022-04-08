package com.deepakagarwal.gizmos;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

public class gameRules extends AppCompatActivity {
    PDFView pdfRules;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_rules);
        pdfRules = (PDFView)findViewById(R.id.pdfRules);
        pdfRules.fromAsset("rules.pdf").load();
    }

    public void back(View v){
        finish();
    }
}