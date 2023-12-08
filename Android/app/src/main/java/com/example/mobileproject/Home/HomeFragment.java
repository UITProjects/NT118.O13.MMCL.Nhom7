package com.example.mobileproject.home;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.mobileproject.Dashboard;
import com.example.mobileproject.R;
import com.example.mobileproject.api.AssetApi;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class HomeFragment extends Fragment {

    View view;
    Thread background_Thread;
    TextView temp,hum,win_dir,win_speed,rainfall;
    Handler ui_handler = new Handler();

    @Override
    public void onStart() {
        background_Thread.start();
        super.onStart();
    }

    @Override
    public void onDestroyView() {
        background_Thread.interrupt();
        super.onDestroyView();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_home, container, false);


        temp = view.findViewById(R.id.temp_value);
        hum = view.findViewById(R.id.humidity_value);
        win_dir = view.findViewById(R.id.wind_direction_value);
        win_speed = view.findViewById(R.id.win_speed_value);
        rainfall =view.findViewById(R.id.rainfall_value);


        background_Thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){


                    try {
                        AssetApi request = new AssetApi(
                                String.format("https://uiot.ixxc.dev/api/master/asset/%s","5zI6XqkQVSfdgOrZ1MyWEf"),
                                "GET",
                                Dashboard.token
                        );
                        Map<String,String> response = request.GeData();
                        ui_handler.post(new Runnable() {
                            @Override
                            public void run() {
                                temp.setText(Objects.requireNonNull(response.get("temperature")).concat(" °C"));
                                hum.setText(Objects.requireNonNull(response.get("humidity")).concat(" %"));
                                win_dir.setText(response.get("windDirection"));
                                win_speed.setText(Objects.requireNonNull(response.get("windSpeed")).concat(" km/s"));
                                rainfall.setText(Objects.requireNonNull(response.get("rainfall")).concat(" mm"));
                                Log.d("interrupt","false");


                            }
                        });


                        Thread.sleep(1800000);
                    } catch (IOException | InterruptedException e) {
                        ui_handler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.d("interrupt","true");
                            }
                        });
                        return;
                    }
                }
            }
        });


        return view;
    }
}