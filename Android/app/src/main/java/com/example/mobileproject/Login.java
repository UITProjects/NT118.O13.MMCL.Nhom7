package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class Login extends AppCompatActivity {
    Spinner spinner;
    public static final String[] languages = {"Choose Language", "English", "Vietnamese"};
    EditText username_edt;
    EditText password_edt;
    Button login_btn,back_btn;
    WebView login_webview;
    TextView forgot_password_textview, signup_textview;
    Handler ui_handle = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username_edt = findViewById(R.id.edt_username);
        password_edt = findViewById(R.id.edt_password);
        back_btn = findViewById(R.id.btn_back);
        login_webview = findViewById(R.id.webview_login);

        forgot_password_textview = findViewById(R.id.txtview_forgot_password);
        signup_textview = findViewById(R.id.txtview_sign_up);

        forgot_password_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgot_password = new Intent(getApplicationContext(),ForgotPassword.class);
                startActivity(forgot_password);
            }
        });

        signup_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup = new Intent(getApplicationContext(), Register.class);
                startActivity(signup);
            }
        });






        login_webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                Log.d("webview","Should called");
                Log.d("webview",request.getUrl().toString());
                view.loadUrl(request.getUrl().toString());
                return false;

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("webview","onpage called");
                Log.d("webview",url);

                if(url.contains("auth?client_id")){
                    String auto_fill_placeholder = "%s.value=\"%s\";";
                    view.evaluateJavascript(
                            "let username = document.getElementById(\"username\"); "+
                            "let password = document.getElementById(\"password\"); "+
                            String.format(auto_fill_placeholder,"username",username_edt.getText().toString())+
                            String.format(auto_fill_placeholder,"password",password_edt.getText().toString())+
                            "let elements = document.getElementsByTagName(\"*\"); "+
                            "elements[31].click() ;"

                    ,null);
                }
                else if(url.contains("authenticate?session_code")){
                    Toast.makeText(getApplicationContext(), R.string.notice2,Toast.LENGTH_SHORT).show();
                    login_webview.getSettings().setJavaScriptEnabled(false);

                }else if(url.contains("authenticate?execution")){
                    Toast.makeText(getApplicationContext(), R.string.notice3,Toast.LENGTH_SHORT).show();
                    login_webview.getSettings().setJavaScriptEnabled(false);
                }

            }
        });


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        login_btn = findViewById(R.id.btn_login);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username_edt.getText().toString().isEmpty() || password_edt.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), R.string.notice1,Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    CookieManager cookieManager = CookieManager.getInstance();
                    cookieManager.removeAllCookies(null);


                    login_webview.clearCache(true);
                    login_webview.clearHistory();
                    login_webview.getSettings().setJavaScriptEnabled(true);
                    login_webview.loadUrl("https://uiot.ixxc.dev/manager/");
                }
            }
        });
        spinner = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languages);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedLang = parent.getItemAtPosition(position).toString();
                if(selectedLang.equals("English")){
                    setLocal(Login.this, "en");
                    finish();
                    startActivity(getIntent());
                }else if(selectedLang.equals("Vietnamese")){
                    setLocal(Login.this, "hi");
                    finish();
                    startActivity(getIntent());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void setLocal(Activity activity, String langCode){
        Locale locale = new Locale(langCode);
        locale.setDefault(locale);
        Resources resources = activity.getResources();
        Configuration config = resources.getConfiguration();
        config.setLocale(locale);
        resources.updateConfiguration(config,resources.getDisplayMetrics());
    }
}