package com.example.mobileproject.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mobileproject.api.APIClient;
import com.example.mobileproject.api.APIInterface;
import com.example.mobileproject.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserSettingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserSettingFragment extends Fragment {
    View view;
    Button logout_btn;
    TextView name, lastname, firstname, username, email;
    ImageView img;
    APIInterface apiInterface;

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
        name = view.findViewById(R.id.textView3);
        lastname = view.findViewById(R.id.lastname);
        firstname = view.findViewById(R.id.firstname);
        username = view.findViewById(R.id.username);
        email = view.findViewById(R.id.email);
        img = view.findViewById(R.id.imageView1);
        apiInterface = APIClient.getClient().create(APIInterface.class);
        Call User = apiInterface.getUser();
        User.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.d("API Call", response.code() + "");
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("API Call", "Successful");
                    Log.d("API Call", response.toString());
                    Log.d("API Call", "response: " + new Gson().toJson(response.body()));
                    try {
                        JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                        String userValue = jsonObject.getString("username");
                        String firstnameValue = jsonObject.getString("firstName");
                        String lastnameValue = jsonObject.getString("lastName");
                        String emailValue = jsonObject.getString("email");
                        if(firstnameValue == null || lastnameValue == null || firstnameValue == null && lastnameValue == null){
                            name.setText(userValue);
                            username.setText(userValue);
                            firstname.setText(firstnameValue);
                            lastname.setText(lastnameValue);
                            email.setText(emailValue);
                        } else {
                            name.setText(lastnameValue + "!");
                            username.setText(userValue);
                            firstname.setText(firstnameValue);
                            lastname.setText(lastnameValue);
                            email.setText(emailValue);
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Log.d("API CALL", t.getMessage().toString());

                //t.printStackTrace();

            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimation();
            }
        });
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        logout_btn.setVisibility(View.GONE);
        return view;

    }
    private void startAnimation(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                img.animate().rotationBy(360).withEndAction(this).setDuration(1000)
                        .setInterpolator(new LinearInterpolator()).start();
            }
        };
    }
}