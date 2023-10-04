package me.ngodat0103.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor share_PreFerences_Editor;
    Handler ui_Handle = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("token", Context.MODE_PRIVATE);
        share_PreFerences_Editor = sharedPreferences.edit();
        setContentView(R.layout.activity_main);
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    client_connection_module.client_connection_module_init("10.0.2.2",2509);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

    }
    public void login_Button(View view) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, IOException, InvalidKeyException {
        EditText username_EditText = findViewById(R.id.username);
        EditText password_EditText = findViewById(R.id.password);
        Thread authentication_thread = new Thread(new Runnable() {
            Map response_from_server_Map;
            public void run() {
                try {
                    response_from_server_Map = handle_request_types_module.authentication(username_EditText.getText().toString(),password_EditText.getText().toString());
                } catch (NoSuchPaddingException e) {
                    throw new RuntimeException(e);
                } catch (IllegalBlockSizeException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                } catch (BadPaddingException e) {
                    throw new RuntimeException(e);
                } catch (InvalidKeyException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InvalidAlgorithmParameterException e) {
                    throw new RuntimeException(e);
                }
                ui_Handle.post(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("authentication",response_from_server_Map.toString());
                        share_PreFerences_Editor.putString("refresh_token",response_from_server_Map.get("refresh_token").toString());
                        share_PreFerences_Editor.apply();
                       String refresh_token= sharedPreferences.getString("refresh_token","NULL");
                        Log.d("authentication","token: "+refresh_token);
                        Intent dashboard_Intent = new Intent(MainActivity.this,DashboardActivity.class);
                        dashboard_Intent.putExtra("username_primary",username_EditText.getText().toString());
                        startActivity(dashboard_Intent);

                    }
                });
            }
        });
    authentication_thread.start();

    }
}