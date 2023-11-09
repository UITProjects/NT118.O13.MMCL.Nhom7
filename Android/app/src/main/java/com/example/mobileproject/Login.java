package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class Login extends AppCompatActivity {
    Spinner spinner;
    public static final String[] languages = {"Choose Language", "English", "Vietnamese"};
    EditText username_edt;
    EditText password_edt;
    Button login_btn,back_btn;
    Handler ui_handle = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username_edt = findViewById(R.id.edt_username);
        password_edt = findViewById(R.id.edt_password);
        back_btn = findViewById(R.id.btn_back);
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
                    Toast.makeText(getApplicationContext(),"Tài khoản hoặc mật khẩu không được để trống",Toast.LENGTH_SHORT).show();
                }
                else {
                    Thread login_request_Thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            final String HOST = "https://uiot.ixxc.dev/auth/realms/master/protocol/openid-connect/token";
                            Map<String,String> parameter = new HashMap<>();
                            parameter.put("username",username_edt.getText().toString());
                            parameter.put("password",password_edt.getText().toString());
                            parameter.put("client_id","openremote");
                            parameter.put("grant_type","password");

                            try {
                                CustomRequest login = new CustomRequest(HOST,"POST",null,parameter);
                                Map<String,String> response_body = login.sendRequest();
                                ui_handle.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (response_body.containsKey("error"))
                                            Toast.makeText(getApplicationContext(),response_body.get("error_description"),Toast.LENGTH_SHORT).show();
                                        else
                                            Toast.makeText(getApplicationContext(),"Login successful",Toast.LENGTH_SHORT).show();
                                    }
                                });

                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                        }

                    });
                    login_request_Thread.start();
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