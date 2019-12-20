package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.os.Bundle;
import android.webkit.WebView;

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
}
