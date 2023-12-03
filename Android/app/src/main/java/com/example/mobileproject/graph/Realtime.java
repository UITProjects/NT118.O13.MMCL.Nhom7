package com.example.mobileproject.graph;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mobileproject.R;

import java.util.ArrayList;


public class Realtime extends Fragment {
    View view;
    Spinner timeframe_spinner;

    @Override
    public void onStart() {
        Graph.mode = 0;
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view =  inflater.inflate(R.layout.fragment_realtime, container, false);
        timeframe_spinner = view.findViewById(R.id.spinner_timeframe);

        timeframe_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                if (item.contains("day")) {
                    Graph.last_time = Long.parseLong(item.split(" ")[0]) * 86400 * 1000;
                    Graph.axis_x_format = 0 ;
                }
                else if (item.contains("week")) {
                    Graph.last_time = Long.parseLong(item.split(" ")[0])*86400*7*1000;
                    Graph.axis_x_format = 1;
                }
                else if(item.contains("month")){
                    Graph.last_time = Long.parseLong(item.split(" ")[0])*86400*30*1000;
                    Graph.axis_x_format = 1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


        ArrayList<String> timeframe = new ArrayList<>();
        timeframe.add("1 day");
        timeframe.add("1 week");
        timeframe.add("1 month");

        ArrayAdapter<String> adapter = new ArrayAdapter(
                view.getContext(),
                android.R.layout.simple_spinner_item,
                timeframe
                );
        adapter.setDropDownViewResource(android.R.layout.select_dialog_singlechoice);
        timeframe_spinner.setAdapter(adapter);
        timeframe_spinner.setSelection(0);



        return view;
    }
}