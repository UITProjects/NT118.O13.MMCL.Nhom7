package me.ngodat0103.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class DashboardActivity extends AppCompatActivity {
ImageView image_profile_ImageView;
Handler ui_Handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        image_profile_ImageView = findViewById(R.id.image_profile_ImageView);
        Intent self_Intent = getIntent();
        String username_primary = self_Intent.getStringExtra("username_primary");
        Thread load_profile_image_Thread = new Thread(new Runnable() {
            byte[] image_profile_bytes;
            @Override
            public void run() {
                try {
                    image_profile_bytes =  handle_request_types_module.load_profile_image(username_primary);
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
                ui_Handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Bitmap image_Bitmap = BitmapFactory.decodeByteArray(image_profile_bytes,0,image_profile_bytes.length);
                        image_profile_ImageView.setImageBitmap(image_Bitmap);
                    }
                });
            }
        });
        load_profile_image_Thread.start();
    }

}