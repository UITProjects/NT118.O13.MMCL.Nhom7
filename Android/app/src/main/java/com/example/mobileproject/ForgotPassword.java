package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;
import com.google.gson.Gson;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ForgotPassword extends AppCompatActivity {
    Button search_btn,verify_btn,change_password_btn;
    EditText otp_code_edt,new_password_edt,confirm_new_password_edt;
    Handler ui_handle = new Handler();
    CustomRequest admin_token_request;
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
                            admin_token_request = new CustomRequest(
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

                           for (UserData user: userData_)
                               if(user.getUserFullName().contains(username_edt.getText().toString())){
                                   CustomRequest get_infor_user = new CustomRequest(
                                           "https://uiot.ixxc.dev/api/master/user/master/".concat(user.getId().getUserId()),
                                           "GET",
                                           header,
                                           null
                                   );
                                   response = get_infor_user.sendRequest();
                                   String email = response.get("email");


                                   header = new HashMap<>();
                                   header.put("check-valid-otp","false");
                                   header.put("projects","mobile");

                                   parameters = new HashMap<>();
                                   parameters.put("email",email);


                                   CustomRequest send_email_request = new CustomRequest(
                                           "https://server.uitprojects.com/reset_password",
                                           "POST",
                                           header,
                                           parameters
                                   );
                                   response = send_email_request.sendRequest();
                                   if (Objects.equals(response.get("status"), "otp_sent"))
                                   {
                                       ui_handle.post(new Runnable() {
                                           @Override
                                           public void run() {
                                               setContentView(R.layout.otp_layout);
                                               Toast.makeText(getApplicationContext(),"Enter the OTP code sent to email", Toast.LENGTH_SHORT).show();
                                               otp_code_edt = findViewById(R.id.edt_otp_code);
                                               verify_btn = findViewById(R.id.btn_verify);

                                               verify_btn.setOnClickListener(new View.OnClickListener() {
                                                   @Override
                                                   public void onClick(View v) {
                                                       Thread check_otp_Thread = new Thread(new Runnable() {
                                                           @Override
                                                           public void run() {
                                                               Map<String,String> header = new HashMap<>();
                                                               Map<String,String> parameter = new HashMap<>();


                                                               header.put("check-valid-otp","true");
                                                               header.put("projects","mobile");

                                                               parameter.put("email",email);
                                                               parameter.put("otp_code",otp_code_edt.getText().toString());

                                                               try {
                                                                   CustomRequest check_valid_otp_request = new CustomRequest(
                                                                           "https://server.uitprojects.com/reset_password",
                                                                           "POST",
                                                                           header,
                                                                           parameter
                                                                   );
                                                                   Map<String,String> response = check_valid_otp_request.sendRequest();
                                                                   if (Objects.equals(response.get("status"), "valid")){
                                                                       ui_handle.post(new Runnable() {
                                                                           @Override
                                                                           public void run() {
                                                                               setContentView(R.layout.new_password_layout);
                                                                               Toast.makeText(getApplicationContext(),"Enter new password",Toast.LENGTH_SHORT).show();
                                                                               change_password_btn = findViewById(R.id.btn_change_password);
                                                                               new_password_edt = findViewById(R.id.edt_new_password);
                                                                               confirm_new_password_edt = findViewById(R.id.edt_confirm_new_password);
                                                                               change_password_btn.setOnClickListener(new View.OnClickListener() {
                                                                                   @Override
                                                                                   public void onClick(View v) {

                                                                                       if (!confirm_new_password_edt.getText().toString().equals(new_password_edt.getText().toString()))
                                                                                       {
                                                                                           Toast.makeText(getApplicationContext(),"Password didn't match",Toast.LENGTH_SHORT).show();
                                                                                           return;
                                                                                       }
                                                                                       Thread change_new_password_Thread = new Thread(new Runnable() {
                                                                                           @Override
                                                                                           public void run() {
                                                                                               Boolean status;
                                                                                               Map<String,String> response = null;
                                                                                               try {


                                                                                                   assert access_token != null;
                                                                                                   Map<String,String> parameters = new HashMap<>();
                                                                                                   parameters.put("username",AdminCredential.USERNAME);
                                                                                                   parameters.put("password",AdminCredential.PASSWORD);
                                                                                                   parameters.put("client_id","openremote");
                                                                                                   parameters.put("grant_type","password");
                                                                                                   admin_token_request = new CustomRequest(
                                                                                                           "https://uiot.ixxc.dev/auth/realms/master/protocol/openid-connect/token",
                                                                                                           "POST",
                                                                                                           null,
                                                                                                           parameters
                                                                                                   );
                                                                                                   Map<String,String> header = new HashMap<>();

                                                                                                   header.put("Content-Type","application/json");
                                                                                                   Map<String,String> json_data_Map = new HashMap<>();
                                                                                                   json_data_Map.put("type","string");
                                                                                                   json_data_Map.put("value",new_password_edt.getText().toString());
                                                                                                   json_data_Map.put("temporary","false");
                                                                                                   response = admin_token_request.sendRequest();
                                                                                                   String access_token = response.get("access_token");
                                                                                                   header.put("Authorization","Bearer ".concat(access_token));
                                                                                                   CustomRequest change_password_request = new CustomRequest(
                                                                                                     "https://uiot.ixxc.dev/api/master/user/master/reset-password/".concat(user.getId().getUserId()),
                                                                                                           "PUT",
                                                                                                           header,
                                                                                                           null
                                                                                                   );
                                                                                                   status =change_password_request.sendPutRequest(json_data_Map);
                                                                                                    if (status)
                                                                                                        ui_handle.post(new Runnable() {
                                                                                                            @Override
                                                                                                            public void run() {
                                                                                                                Toast.makeText(getApplicationContext(), "Change password successfully", Toast.LENGTH_SHORT).show();
                                                                                                                finish();
                                                                                                            }
                                                                                                        });
                                                                                                    else
                                                                                                        ui_handle.post(new Runnable() {
                                                                                                            @Override
                                                                                                            public void run() {
                                                                                                                Toast.makeText(getApplicationContext(), "Something is not right", Toast.LENGTH_SHORT).show();
                                                                                                                finish();

                                                                                                            }
                                                                                                        });



                                                                                               } catch (
                                                                                                       IOException e) {
                                                                                                   throw new RuntimeException(e);
                                                                                               }
                                                                                           }
                                                                                       });
                                                                                       change_new_password_Thread.start();

                                                                                   }
                                                                               });
                                                                           }
                                                                       });
                                                                   }
                                                                   else{
                                                                       ui_handle.post(new Runnable() {
                                                                           @Override
                                                                           public void run() {
                                                                               Toast.makeText(getApplicationContext(),"OTP invalid",Toast.LENGTH_SHORT).show();

                                                                           }
                                                                       });
                                                                   }
                                                               } catch (IOException e) {
                                                                   throw new RuntimeException(e);
                                                               }
                                                           }
                                                       });
                                                       check_otp_Thread.start();



                                                   }
                                               });
                                           }
                                       });
                                   }
                                    return;
                               }
                           ui_handle.post(new Runnable() {
                               @Override
                               public void run() {
                                   Toast.makeText(getApplicationContext(),"username didn't found",Toast.LENGTH_SHORT).show();
                               }
                           });
                           return;


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