package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ForgotPassword extends AppCompatActivity {
    Button search_btn;
    Handler ui_handle = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        search_btn = findViewById(R.id.btn_search);
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Thread forgot_password_thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EditText username_edt = findViewById(R.id.edt_username);
                        Map<String,String> parameters = new HashMap<>();
                        parameters.put("username",AdminCredential.USERNAME);
                        parameters.put("password",AdminCredential.PASSWORD);
                        parameters.put("client_id","openremote");
                        parameters.put("grant_type","password");
                        try {
                            CustomRequest admin_token_request = new CustomRequest(
                                    "https://uiot.ixxc.dev/auth/realms/master/protocol/openid-connect/token",
                                    "POST",
                                    null,
                                    parameters
                            );
                            Map<String,String> response = admin_token_request.sendRequest();
                            String access_token = response.get("access_token");
                            Map<String,String> header = new HashMap<>();
                            assert access_token != null;
                            header.put("Authorization","Bearer ".concat(access_token));
                            CustomRequest get_all_user_request = new CustomRequest(
                                    "https://uiot.ixxc.dev/api/master/asset/user/link",
                                    "GET",
                                    header,
                                    null
                            );
                            String data_json = get_all_user_request.sendRequest(true);
                            UserData[] userData_ = new Gson().fromJson(data_json,UserData[].class) ;
                            Log.d("forgot_password","debug");

                            for(UserData user: userData_){
                                if (user.getUserFullName().contains(username_edt.getText()))
                                {
                                    Log.d("forgot_password", user.getId().getUserId());
                                    ui_handle.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ForgotPassword.this, "Account found", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }


                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                forgot_password_thread.start();

            }
        });
    }
}