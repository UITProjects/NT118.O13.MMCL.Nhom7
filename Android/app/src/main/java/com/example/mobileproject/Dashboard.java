package com.example.mobileproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.mobileproject.graph.GraphFragment;
import com.example.mobileproject.map.MapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Dashboard extends AppCompatActivity {


    BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Intent current_intent = getIntent();

        ViewPager2 viewPager = findViewById(R.id.viewPager);
        mBottomNavigationView = findViewById(R.id.bottom_navigation);

        viewPager.setAdapter(new CustomFragmentAdapter(this));
        viewPager.setPageTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                final float MIN_SCALE = 0.75f;
                int pageWidth = page.getWidth();
                if (position < -1) {
                    page.setAlpha(0f);
                } else if (position <= 0) {
                    page.setAlpha(1f);
                    page.setTranslationX(0f);
                    page.setScaleX(1f);
                    page.setScaleY(1f);
                } else if (position <= 1) {
                    page.setAlpha(1 - position);
                    page.setTranslationX(pageWidth * -position);
                    float scaleFactor = MIN_SCALE
                            + (1 - MIN_SCALE) * (1 - Math.abs(position));
                    page.setScaleX(scaleFactor);
                    page.setScaleY(scaleFactor);
                } else {
                    page.setAlpha(0f);
                }
            }
        });

        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        mBottomNavigationView.getMenu().findItem(R.id.menu_home).setChecked(true);
                        break;
                    case 1:
                        mBottomNavigationView.getMenu().findItem(R.id.menu_graph).setChecked(true);
                        break;
                    case 2:
                        mBottomNavigationView.getMenu().findItem(R.id.menu_map).setChecked(true);
                        break;
                    case 3:
                        mBottomNavigationView.getMenu().findItem(R.id.menu_user).setChecked(true);
                        break;
                }
            }
        });



    }
}