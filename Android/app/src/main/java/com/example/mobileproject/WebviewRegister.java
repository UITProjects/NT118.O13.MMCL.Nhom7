package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import java.text.Format;

import javax.xml.transform.Result;

public class WebviewRegister extends AppCompatActivity {
    WebView signup_Webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_webview);
        signup_Webview = findViewById(R.id.Webview_signup);
        signup_Webview.getSettings().setJavaScriptEnabled(true);

        Intent current_intent = getIntent();
        String username = current_intent.getStringExtra("username");
        String password = current_intent.getStringExtra("password");
        String confirm_password =  current_intent.getStringExtra("confirm_password");
        String email = current_intent.getStringExtra("email");
        signup_Webview.clearCache(true);
        signup_Webview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.d("webview",request.getUrl().toString());
                view.loadUrl(request.getUrl().toString());
                return super.shouldOverrideUrlLoading(view, request);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                if (url.contains("auth?")) {
                    Log.i("webview", "Finished loading URL: " + url);
                    view.evaluateJavascript("" +
                                    "let elements = document.getElementsByTagName(\"*\");\n" +
                                    "elements[33].click()",

                            new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String value) {
                                    Log.d("js return", value);
                                }
                            });
                }
                else if(url.contains("registration")){


                    String string_placeholder = "let %s =document.getElementById(\"%s\");";
                    String auto_fill_placeholder = "%s.value=\"%s\";";


                    Log.d("registration","email: " +email);
                    view.evaluateJavascript(


                            String.format(string_placeholder,"username_input","username")+
                            String.format(string_placeholder,"password_input","password")+
                            String.format(string_placeholder,"email_input","email")+
                            String.format(string_placeholder,"confirm_password_input","password-confirm")+


                            String.format(auto_fill_placeholder,"email_input",email)+
                            String.format(auto_fill_placeholder,"username_input",username)+
                            String.format(auto_fill_placeholder,"password_input",password)+
                            String.format(auto_fill_placeholder,"confirm_password_input",confirm_password)+
                            "let elements = document.getElementsByTagName(\"*\");"+
                            "elements[39].click();"



                            ,null
                    );

                }else if(url.contains("registration?session_code")){
                    signup_Webview.clearHistory();
                    getApplicationContext().deleteDatabase("webview.db");
                    getApplicationContext().deleteDatabase("webviewCache.db");
                    finish();
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, String failingUrl) {
                Log.e("webview", "Error: " + description);
            }
        });



        signup_Webview.loadUrl("https://uiot.ixxc.dev/manager/");
    }
}