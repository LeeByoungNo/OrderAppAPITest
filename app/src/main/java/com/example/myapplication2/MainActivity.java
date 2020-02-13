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

//        Toast.makeText(MainActivity.this,"SharedPreferences's loginKey :"+loginKey+" , refreshKey : "+refreshKey,Toast.LENGTH_LONG);

        // WebView remote debug 설정
//        getApplicationInfo();
//        ApplicationInfo.FLAG_DEBUGGABLE ;
//        WebView.setWebContentsDebuggingEnabled(true);

//        Build.VERSION_CODES.LOLLIPOP_MR1 ;
//        Build.VERSION.SDK_INT ;
        // WebView remote debug 설정

        /*
        *  var post_data = "uid="+req.body.uid ;
           var post_options = setPostOption("/api/storeDetail.json",post_data,req.body.loginKey);
           *
           var post_data = "type="+req.body.type+"&dong="+req.body.dong+"&x="+req.body.x+"&y="+req.body.y+"&spName="+req.body.spName
                 +"&pageNo="+req.body.pageNo+"&pageSize="+req.body.pageSize ;

           var post_options = setPostOption("/api/storeListDeli.json",post_data,req.body.loginKey);
           *
           var post_data = "&x="+req.body.x+"&y="+req.body.y+"&pageSize="+req.body.pageSize ;
           var post_options = setPostOption("/api/storeListDeliAll.json",post_data,"");
           *
           * lkj1212@daum.net / rkddb1212
           *  x : 37.4732219 , y : 126.8843009
           * https://m.delivera.co.kr/api/ + API 명
           *
           * login.json : userId, userPwd
           *
           * reviewInsert.json : loginKey, userId,content,spCode,grade,file
           * noticeList.json : loginKey
           * noticeDetail.json : loginKey , nid
           * eventList.json : loginKey , spCode
           * categoryList.json : GET
           * storeDetail.json : uid (상점ID)
           * showReviewList.json : spCode, pageSize, pageNo
        * */

        /*
        public static void main(String[] args) {

            // 로그인
            String userId = "lkj1212@daum.net" ;
            String userPassword = "rkddb1212" ;

            // 리뷰작성 (reviewInsert.json)
            String loginKey = "de70dd03-fb91-4d52-9f08-94079bf77fb4" ;
            String content = "contet 테스트3" ;
            String spCode = "J00001002000003" ;
            String grade = "3" ;
            String file = "" ;


            // 리뷰조회  (shopReviewList.json)
            String pageSize = "10" ;
            String pageNo = "1" ;

            try {

                // 1. login
    //			String result = sendPostData("http://localhost:8080/api/login.json","userId="+userId+"&userPwd="+userPassword);

                // 2. 리뷰조회
    //			String result = sendPostData("http://localhost:8080/api/shopReviewList.json","spCode="+spCode+"&pageSize="+pageSize+"&pageNo="+pageNo);

                Map<String,String> data = new HashMap<String,String>();
                data.put("spCode", spCode);
                data.put("content", content);
                data.put("grade", grade);
                data.put("userId", userId);
    //			String result = sendPostDataWithLoginKey("http://localhost:8080/api/reviewInsert.json","spCode="+spCode+"&content="+content+"&grade="+grade+"&userId="+userId,loginKey);

                String filePath = "C:\\Users\\leebn\\Pictures\\img_158639_1.jpg" ;

                String result = sendPostMultiFormData("http://localhost:8080/api/reviewInsert.json",data,loginKey,filePath);
                JSONObject jsonObj =  new JSONObject(result);

                System.out.println("result = "+result);

                System.out.println("status = "+jsonObj.get("status"));

                if(jsonObj.get("status").equals("1")) {
                    System.out.println("저장 성공");
                }else {
                    System.out.println("저장 실패");
                }

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
	    }

	    // 배너 테스트 URL
	    http://img.delivera.co.kr/0000/0000_banner_20200116155000148.jpg
	    // editor HTML 테스트 URL
	    http://img.delivera.co.kr/0000/0000_20200116155122624.html

        * */
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

    public void webViewTest(View view){
        Intent intent = new Intent(this, WebViewActivity.class);

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
        /*FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>(){
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
        });*/
    }

    public void apiCallReviewList(View view){

        /*new Thread(){
            public void run(){

                String spCode = "J00001002000003" ;

                String result = callPostAPI("https://m.delivera.co.kr/api/shopReviewList.json","spCode="+spCode+"&pageSize=5&pageNo=1");

                Log.d("API","RESULT: "+result);

                try{
                    final JSONObject jsonObject = new JSONObject(result);

                    Log.d("json","status : "+jsonObject.get("status")+", data : "+jsonObject.get("data"));

                }catch(Exception e){
                    Log.d("error",""+e);
                }



            }
        }.start();*/

        //
        Intent intent = new Intent(this, ShopReviewListActivity.class);

        startActivity(intent);

    }

    public void callShopList(View view){

        Intent intent = new Intent(this, ShopListActivity.class);

        startActivity(intent);
    }

    public void apiCallTest(View view){
        Log.d("API","API CALL TEST clicked");

        new Thread(){
            public void run(){


                String result = callPostAPI("http://192.168.0.3:8090/testApi","");

                Log.d("API","RESULT: "+result);

                try{
                    final JSONObject jsonObject = new JSONObject(result);

                    //Log.d("json","status : "+jsonObject.get("status")+", data : "+jsonObject.get("data"));

                    setStatus((String) jsonObject.get("status"));
//                    String status = (String)jsonObject.get("status");

//                    Handler handler = new Handler(){
//                        public void handleMessage(Message msg){
//                            EditText editText = (EditText) findViewById(R.id.editText2);
//                            editText.setText("status: "+status);
//                        }
//                    };

                }catch(Exception e){
                    Log.d("error",""+e);
                }



            }
        }.start();



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
