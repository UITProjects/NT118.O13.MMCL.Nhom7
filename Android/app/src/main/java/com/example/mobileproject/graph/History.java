package com.example.mobileproject.graph;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mobileproject.R;

public class History extends Fragment {

    View view;

    @Override
    public void onStart() {
        Graph.mode = 1;
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Graph.mode = 1;
        view =  inflater.inflate(R.layout.fragment_history, container, false);
        return view;
    }
}