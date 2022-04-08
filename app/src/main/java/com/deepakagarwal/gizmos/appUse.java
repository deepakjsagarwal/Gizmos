package com.deepakagarwal.gizmos;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.github.barteksc.pdfviewer.PDFView;

public class appUse extends AppCompatActivity {
    PDFView pdfRules;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_use);
        pdfRules = (PDFView)findViewById(R.id.pdfRules);
        pdfRules.fromAsset("appuse.pdf").load();
    }

    public void back(View v){
        finish();
    }

}