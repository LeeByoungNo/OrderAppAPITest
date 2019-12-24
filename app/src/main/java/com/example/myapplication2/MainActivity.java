package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;

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

    public void insertReview(View view){
        Intent intent = new Intent(this, InsertReviewActivity.class);

        startActivity(intent);
    }

    public void apiCallTest(View view){
        Log.d("API","API CALL TEST clicked");

        new Thread(){
            public void run(){
//        HttpURLConnection conn = new HttpURLConnection();
                String result = callPostAPI("http://192.168.0.3:8090/testApi");

                Log.d("API","RESULT: "+result);

                try{
                    final JSONObject jsonObject = new JSONObject(result);

                    Log.d("json","status : "+jsonObject.get("status")+", data : "+jsonObject.get("data"));

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

    public static String callPostAPI(String urlStr){

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
            wr.write("param1=testParam1");
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
