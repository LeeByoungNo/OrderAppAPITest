package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication2.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_form);

        // SharedPreferences Write TEST
        /*SharedPreferences sharedPref = getSharedPreferences("loginKey", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("loginKey","test");
        editor.putString("refreshKey","REFRESH KEY");
        editor.commit();*/

    }

    public void apiLogin(View view){

        Toast.makeText(LoginActivity.this,"Please Login!",Toast.LENGTH_SHORT).show();

        Log.d("LOGIN","Login Please!");

        // ID, Password 값 얻어 오기
        // editTextID
        EditText editTextID = findViewById(R.id.editTextID) ;

        // editTextPassword
        EditText editTextPassword = findViewById(R.id.editTextPassword) ;

        final String id = editTextID.getText().toString() ;
        final String password = editTextPassword.getText().toString() ;

        Toast.makeText(LoginActivity.this,"ID:"+id+" , PWD:"+password,Toast.LENGTH_SHORT).show();

        new Thread(){
            public void run(){

                try{
                    String loginResult = HttpUtil.sendPostData("https://m.delivera.co.kr/api/login.json","userId="+id+"&userPwd="+password);

                    Log.d("LOGIN result",loginResult);

                    JSONObject result = new JSONObject(loginResult);

                    if(result.get("status").equals("1")){  // 로그인 성공
                        JSONArray data = result.getJSONArray("data");
                        if(data != null){
                            JSONObject loginInfo = data.getJSONObject(0);

                            String loginKey = (String)loginInfo.get("loginKey");
                            String refreshKey = (String)loginInfo.get("refreshKey");

                            Log.d("loginInfo","loginKey:"+loginKey+" , refreshKey:"+refreshKey);

                            /*Intent intent = new Intent(null, MainActivity.class);
                            startActivity(intent);*/



                        }
                    }else{  // 로그인 실패
                        Log.e("login fail","로그인 실패");
                    }

                }catch(Exception e){
                    Log.e("LOGIN result",e+"");
                }

            }
        }.start();

    }
}
