package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebviewRegister extends AppCompatActivity {
    WebView signup_Webview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_webview);
        signup_Webview = findViewById(R.id.Webview_signup);
        signup_Webview.getSettings().setJavaScriptEnabled(true);



        signup_Webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.d("webview",request.getUrl().toString());
                view.loadUrl(request.getUrl().toString());
                request.
                return super.shouldOverrideUrlLoading(view, request);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.i("webview", "Finished loading URL: " + url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                Log.e("webview", "Error: " + description);
            }
        });



        signup_Webview.loadUrl("https://uiot.ixxc.dev/auth/realms/master/login-actions/registration?client_id=openremote&tab_id=c5V2sz59rQc");
    }
}