package me.ngodat0103.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
    Handler handler = new Handler();
    private ImageView image_profile_ImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        image_profile_ImageView = findViewById(R.id.imageView);
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
                byte[] image_profile_bytes;
                try {
                  image_profile_bytes =  handle_request_types_module.load_profile_image("test_account3");
                } catch (NoSuchPaddingException e) {
                    throw new RuntimeException(e);
                } catch (IllegalBlockSizeException e) {
                    throw new RuntimeException(e);
                } catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                } catch (BadPaddingException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InvalidKeyException e) {
                    throw new RuntimeException(e);
                } catch (InvalidAlgorithmParameterException e) {
                    throw new RuntimeException(e);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap image_Bitmap = BitmapFactory.decodeByteArray(image_profile_bytes,0,image_profile_bytes.length);
                        image_profile_ImageView.setImageBitmap(image_Bitmap);
                    }
                });
            }
        });

        thread.start();


    }
}