package com.example.mobileproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mobileproject.graph.Graph;
import com.example.mobileproject.map.Map;

public class Dashboard extends AppCompatActivity {
    String username,password;
    Fragment map_fragment,graph_fragment;
    Button map_btn,graph_btn;
    void replaceFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout_main,fragment);
        transaction.commit();
    }

    void hideFragment(Fragment fragment){
        if(fragment.isHidden())
            return;
        FragmentManager manager  = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.hide(fragment);
        transaction.commit();

    }
    void showFragment(Fragment fragment){
        if(!fragment.isHidden())
            return;
        FragmentManager manager  = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.show(fragment);
        transaction.commit();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Intent current_intent = getIntent();
        map_btn = findViewById(R.id.btn_map);
        graph_btn = findViewById(R.id.btn_graph);

        map_fragment = new Map();
        graph_fragment = new Graph();
        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new Map());
            //    showFragment(map_fragment);
            }
        });

        graph_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new Graph());
            //    showFragment(graph_fragment);
            }
        });




    }
}