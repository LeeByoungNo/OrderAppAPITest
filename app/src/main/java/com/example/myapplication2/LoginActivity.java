package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_form);
    }

    public void apiLogin(View view){

        Toast.makeText(LoginActivity.this,"Please Login!",Toast.LENGTH_SHORT).show();

        Log.d("LOGIN","Login Please!");

        // ID, Password 값 얻어 오기


    }
}
