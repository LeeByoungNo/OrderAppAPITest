package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // SharedPreferences GET TEST
        SharedPreferences sharedPref = getSharedPreferences("loginKey", Context.MODE_PRIVATE);
        String loginKey = sharedPref.getString("loginKey","값 없음");
        String refreshKey = sharedPref.getString("refreshKey","값 없음");

        Log.d("SharedPreferences","SharedPreferences's loginKey :"+loginKey+" , refreshKey : "+refreshKey);
        // SharedPreferences GET TEST

        // WebView remote debug 설정
//        getApplicationInfo();
//        ApplicationInfo.FLAG_DEBUGGABLE ;
//        WebView.setWebContentsDebuggingEnabled(true);

//        Build.VERSION_CODES.LOLLIPOP_MR1 ;
//        Build.VERSION.SDK_INT ;
        // WebView remote debug 설정


    }


    public void setStatus(final String status){


        /*Handler handler = new Handler(){
            public void handleMessage(Message msg){
                EditText editText = (EditText) findViewById(R.id.editText2);
                editText.setText("status: "+status);
            }
        };
        handler.sendMessage(null);*/

//        EditText editText = (EditText) findViewById(R.id.editText2);
//        editText.setText("status: "+status);
    }

    public void uiTestView(View view){
        Intent intent = new Intent(this, UiTestActivity.class);

        startActivity(intent);
    }

    public void adListTest(View view){
        Intent intent = new Intent(this, AdListActivity.class);

        startActivity(intent);
    }

    public void insertReview(View view){
        Intent intent = new Intent(this, InsertReviewActivity.class);

        startActivity(intent);
    }

    public void loginFormGo(View view){

        Intent intent = new Intent(this, LoginActivity.class);

        startActivity(intent);
    }

    public void showToken(View view){

        Log.d("fcm","token 값:");

        // CURRENT FCM Token 값 구하기
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>(){
            @Override
            public void onComplete(@NonNull Task<InstanceIdResult> task) {

                if (!task.isSuccessful()) {
                    Log.w("", "getInstanceId failed", task.getException());
                    return;
                }

                // Get new Instance ID token
                String token = task.getResult().getToken();

                // Log and toast
//                String msg = getString(R.string.msg_token_fmt, token);
                Log.d("", token);
                Toast.makeText(MainActivity.this, "token: "+token, Toast.LENGTH_SHORT).show();


            }
        });
    }

    public void apiCallReviewList(View view){

        //
        Intent intent = new Intent(this, ShopReviewListActivity.class);

        startActivity(intent);

    }

    public void callShopList(View view){

        Intent intent = new Intent(this, ShopListActivity.class);

        startActivity(intent);
    }

    public static String callPostAPI(String urlStr,String param){

        StringBuffer sb = new StringBuffer();

        try{

            Log.d("URL",urlStr);
            URL url = new URL(urlStr);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();

            con.setRequestMethod("POST");

//		con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            con.setDoInput(true);
            con.setDoOutput(true); //POST 데이터를 OutputStream으로 넘겨 주겠다는 설정

            OutputStreamWriter wr = new OutputStreamWriter(con.getOutputStream());
            wr.write(param);
            wr.flush();


            if(con.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

                String line ;
                while((line = br.readLine()) != null) {
                    sb.append(line).append('\n');

                }
                br.close();

                System.out.println("result: "+sb.toString());

            }else {
                System.out.println(con.getResponseMessage());
            }
        }catch (Exception e){
            Log.d("Util","API send error :"+e);
        }


        return sb.toString() ;
    }
}
