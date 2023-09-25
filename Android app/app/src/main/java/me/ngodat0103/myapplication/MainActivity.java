package me.ngodat0103.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Tcp_client_thread client_thread = new Tcp_client_thread("10.0.2.2",2509);
        client_thread.start();
    }
    public void onBtnClick(View view){
        EditText edtFirstName = findViewById(R.id.editFirstName);
        EditText editLastName = findViewById(R.id.editLastName);
        EditText editEmail = findViewById(R.id.editEmail);
        TextView viewFirstName = findViewById(R.id.texFirstName);
        TextView viewLastName = findViewById(R.id.textLastName);
        TextView viewEmail = findViewById(R.id.textEmail);
        viewFirstName.setText(edtFirstName.getText().toString());
        viewLastName.setText(editLastName.getText());
        viewEmail.setText(editEmail.getText());
    }
}