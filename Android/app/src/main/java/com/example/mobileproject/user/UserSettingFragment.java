package com.example.mobileproject.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.mobileproject.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserSettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserSettingFragment extends Fragment {
    View view;
    Button logout_btn;

    @Override
    public void onResume() {
        logout_btn.setVisibility(View.VISIBLE);
        super.onResume();
    }

    @Override
    public void onPause() {
        logout_btn.setVisibility(View.GONE);
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_usersetting, container, false);
        logout_btn = view.findViewById(R.id.btn_logout);
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        logout_btn.setVisibility(View.GONE);
        return view;



    }
}