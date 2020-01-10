package com.example.myapplication2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class InsertReviewActivity extends AppCompatActivity {

    private static final int READ_REQUEST_CODE = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_review_form);
    }


    public void onClickReviewInsert(View view){
        Log.d("insert","등록 테스트");

        //
//        Context context = getApplicationContext();

        EditText userIdEdit = findViewById(R.id.userId);
        EditText editTextValidatePoints = findViewById(R.id.editTextValidatePoints);
        EditText editTextContent  = findViewById(R.id.editTextContent);

        String userId = userIdEdit.getText().toString() ;
        String validatePoints = editTextValidatePoints.getText().toString();
        String content = editTextContent.getText().toString();

        // validation CHECK
        if(userId == null || userId.trim().length() == 0){

        }
        if(validatePoints == null || validatePoints.trim().length() == 0){

        }
        if(content == null || content.trim().length() == 0){

        }
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
            Uri uri = null;
            if (data != null) {
                uri = data.getData();

                ImageView img =  findViewById(R.id.imageView2);

                img.setImageURI(uri);
            }
        }
    }
}
