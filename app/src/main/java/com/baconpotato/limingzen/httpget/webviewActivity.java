package com.baconpotato.limingzen.httpget;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URI;

public class webviewActivity extends AppCompatActivity {
private String url="http://map.baidu.com";
    private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        //Uri uri= Uri.parse(url);
        //Intent intent =new Intent(Intent.ACTION_VIEW,uri);
        //startActivity(intent);
        init();
    }

    private void init() {
        webView=(WebView)findViewById(R.id.webview);
        webView.loadUrl("http://map.baidu.com");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        WebSettings settings = webView.getSettings();
                settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setDomStorageEnabled(true);//允许DCOM
    }


}
