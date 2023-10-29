package com.example.listview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.listview.normal_recycle.NormalRecycleView;

public class MainActivity extends AppCompatActivity {

    Button goto_btn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        goto_btn = findViewById(R.id.goto_button);
        goto_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recycle_Intent = new Intent("android.intent.action.MENU");
                startActivity(recycle_Intent);
            }
        });
    }
}