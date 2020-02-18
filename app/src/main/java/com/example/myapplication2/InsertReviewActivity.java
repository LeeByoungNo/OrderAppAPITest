package com.example.myapplication2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication2.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class InsertReviewActivity extends AppCompatActivity {

    private static final int READ_REQUEST_CODE = 42;

    private EditText userIdEdit ;
    private EditText editTextValidatePoints ;
    private EditText editTextContent ;
    private ImageView replyImageView ;
    private Uri uri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_review_form);

        userIdEdit = findViewById(R.id.userId);
        editTextValidatePoints = findViewById(R.id.editTextValidatePoints);
        editTextContent  = findViewById(R.id.editTextContent);
        replyImageView =  findViewById(R.id.imageView2);
    }


    public void onClickReviewInsert(View view){
        Log.d("insert","등록 테스트");

        //
//        Context context = getApplicationContext();



        String userId = userIdEdit.getText().toString() ;
        final String validatePoints = editTextValidatePoints.getText().toString();
        final String content = editTextContent.getText().toString();

        // validation CHECK
        if(userId == null || userId.trim().length() == 0){

        }
        if(validatePoints == null || validatePoints.trim().length() == 0){

        }
        if(content == null || content.trim().length() == 0){

        }

//        replyImageView.get
        // validation CHECK

        // IMAGE SELECT check

        // IMAGE SELECT check

        Toast.makeText(getApplicationContext(),"uerId : "+userId+" , validatePoints:"+
                                    validatePoints+" , content : "+content,Toast.LENGTH_LONG).show();

        new AlertDialog.Builder(this)
                .setTitle("리뷰 등록")
                .setMessage("등록 하시겠습니까?")
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //
                    new Thread(){
                        public void run(){

                            SharedPreferences sharedPref = getSharedPreferences("loginKey", Context.MODE_PRIVATE);
                            String userId = sharedPref.getString("userId","");
                            String loginKey = sharedPref.getString("loginKey","");
                            String refreshKey = sharedPref.getString("refreshKey","");

                            Map<String,String> params = new HashMap<String,String>();
                            params.put("userId",userId);
                            params.put("content",content);
                            params.put("spCode","J00001002000003");
                            params.put("grade",validatePoints);

                            try{
                                String result = HttpUtil.sendPostMultiFormData("https://m.delivera.co.kr/api/reviewInsert.json", params, loginKey, uri,getBaseContext());  // File 대신 Uri로 변경 필요

                                JSONObject jsonObj =  new JSONObject(result);

                                Log.d("DEBUG::","status : "+jsonObj.get("status"));

                            }catch(Exception e){
                                Log.e("리뷰작성",""+e);
                            }

                        }
                    }.start();
                    }
                })
                .show();
    }
    public void onClickImgSelect(View view){

        Intent intentOpenDocu = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intentOpenDocu.addCategory(Intent.CATEGORY_OPENABLE);
        intentOpenDocu.setType("image/*");

        startActivityForResult(intentOpenDocu, READ_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if ((requestCode == READ_REQUEST_CODE) && (resultCode == Activity.RESULT_OK)) {
            //

            if (data != null) {
                uri = data.getData();

                replyImageView.setImageURI(uri);
            }
        }
    }
}
