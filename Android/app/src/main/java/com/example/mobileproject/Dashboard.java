package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity {
    String username,password;
    WebView dashboard_webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Intent current_intent = getIntent();
        username = current_intent.getStringExtra("username");
        password = current_intent.getStringExtra("password");


        dashboard_webview = findViewById(R.id.webview_dashboard);
        dashboard_webview.getSettings().setJavaScriptEnabled(true);
        dashboard_webview.getSettings().setLoadsImagesAutomatically(true);
        dashboard_webview.getSettings().setAllowContentAccess(true);

        dashboard_webview.getSettings().setUseWideViewPort(true);
        dashboard_webview.getSettings().setLoadWithOverviewMode(true);
        dashboard_webview.getSettings().setDomStorageEnabled(true);
        dashboard_webview.setHorizontalScrollBarEnabled(false);
        dashboard_webview.getSettings().setDatabaseEnabled(true);
        dashboard_webview.setVerticalScrollBarEnabled(false);
        dashboard_webview.getSettings().setBuiltInZoomControls(true);
        dashboard_webview.getSettings().setDisplayZoomControls(false);
        dashboard_webview.getSettings().setAllowFileAccess(true);
        dashboard_webview.setScrollbarFadingEnabled(false);
        dashboard_webview.setWebViewClient(new WebViewClient());
        dashboard_webview.setInitialScale(1);
        dashboard_webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return false;

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("webview","onPageFinished called");
                Log.d("webview",url);

                if(url.contains("auth?client_id")){
                    String auto_fill_placeholder = "%s.value=\"%s\";";
                    view.evaluateJavascript(
                            "let username = document.getElementById(\"username\"); "+
                                    "let password = document.getElementById(\"password\"); "+
                                    String.format(auto_fill_placeholder,"username",username)+
                                    String.format(auto_fill_placeholder,"password",password)+
                                    "let elements = document.getElementsByTagName(\"*\"); "+
                                    "elements[31].click() ;"

                            ,null);
                }
            }

        });
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookies(null);
        dashboard_webview.clearCache(true);
        dashboard_webview.clearHistory();
        dashboard_webview.loadUrl("https://uiot.ixxc.dev/manager/");

    }
}