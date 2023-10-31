package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    EditText username_edt;
    EditText password_edt;
    Button login_btn;
    Handler ui_handle = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username_edt = findViewById(R.id.edt_username);
        password_edt = findViewById(R.id.edt_password);
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
                            URL login_url;
                            HttpURLConnection con;
                            Map<String, String> parameters = new HashMap<>();
                            DataOutputStream out;

                            try {
                                login_url = new URL("https://uiot.ixxc.dev/auth/realms/master/protocol/openid-connect/token");
                                parameters.put("client_id","openremote");
                                parameters.put("username",username_edt.getText().toString());
                                parameters.put("password",password_edt.getText().toString());
                                parameters.put("grant_type","password");
                                con = (HttpURLConnection) login_url.openConnection();
                                con.setRequestMethod("POST");
                                con.setRequestProperty("Content-Type","application/x-www-form-urlencoded");


                                con.setDoOutput(true);
                                StringBuilder result = new StringBuilder();
                                out = new DataOutputStream(con.getOutputStream());

                                for(Map.Entry<String,String> entry: parameters.entrySet()) {
                                    result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                                    result.append("=");
                                    result.append(URLEncoder.encode(entry.getValue(),"UTF-8"));
                                    result.append("&");
                                }
                                String resultString = result.toString();
                                resultString = resultString.substring(0,resultString.length()-1);
                                out.writeBytes(resultString);
                                out.flush();
                                out.close();
                                int status = con.getResponseCode();
                                ui_handle.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.d("login","status code: "+ String.valueOf(status));

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
    }
}