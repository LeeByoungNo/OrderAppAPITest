package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.active_webview_form);

        WebView  webView = findViewById(R.id.html_viwer);


        webView.loadUrl("http://img.delivera.co.kr/0000/0000_20200203152915233.html");
//        webView.loadUrl("http://img.delivera.co.kr/0000/0000_20200131131510496.html");

    }
}
