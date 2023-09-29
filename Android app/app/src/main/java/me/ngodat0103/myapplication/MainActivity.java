package me.ngodat0103.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public void onBtnClick(View view) throws InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, IOException, InvalidKeyException {
        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                Map<String, String> status = null;
                try {
                    status = handle_request_types_module.authentication(username.getText().toString(), password.getText().toString());
                } catch (Exception e) {
                    Log.d("authentication", String.valueOf(e.getCause()));
                    Log.d("authentication", String.valueOf(e.getMessage()));
                    Log.d("authentication",e.toString());
                }
            }
        });

        thread.start();


    }
}