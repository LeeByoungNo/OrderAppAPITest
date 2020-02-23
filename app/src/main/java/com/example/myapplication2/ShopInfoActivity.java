package com.example.myapplication2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication2.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

public class ShopInfoActivity extends AppCompatActivity {

    private TextView shopInfoOpentime ;

    private JSONObject shopInfo ;
    private ImageView shopImage ;
    private TextView shopInfoaddress ;

    private String uid ;
    private String spCode ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_info);

        Intent intent = getIntent();

        uid = intent.getExtras().getString("uid");
        spCode = intent.getExtras().getString("spCode");

        shopInfoOpentime = findViewById(R.id.shop_info_open_time);
        shopImage = findViewById(R.id.shop_info_shop_image);
        shopInfoaddress = findViewById(R.id.shop_info_address);

       /* new Thread(){
            public void run(){
                try{

                    String result = HttpUtil.sendPostData("https://m.delivera.co.kr/api/storeDetail.json","uid="+(uid != null ? uid : "")+"&spCode="+(spCode!=null?spCode:""));
//                    String result = HttpUtil.sendPostData("http://dev.delivera.co.kr:8082/api/storeDetail.json","uid="+(uid != null ? uid : "")+"&spCode="+(spCode!=null?spCode:""));  // LOCAL


                    Log.d("API","RESULT: "+result);

                    JSONObject jsonObjTotalAdList =  new JSONObject(result);
                    if(jsonObjTotalAdList.get("status").equals("1")) {

                        JSONArray data = jsonObjTotalAdList.getJSONArray("data");

                        if(data != null) {

                            // 상점 정보 setting ===================
                            shopInfo = data.getJSONObject(0);
                            // 상점 정보 setting ===================

                            Log.d("DEBUG","adminPathImage:"+shopInfo.getString("adminPathImage"));
                            Log.d("DEBUG","spSimpleAddr:"+shopInfo.getString("spSimpleAddr"));
                        }
                    }
                }catch(Exception e){
                    Log.d("error",""+e);
                }
            }
        }.start();*/
        new FetchShopInfoTask().execute("");

    }

    private class FetchShopInfoTask extends AsyncTask<String,Void,Void> {

        @Override
        protected Void doInBackground(String... strings) {

            try{

                String result = HttpUtil.sendPostData("https://m.delivera.co.kr/api/storeDetail.json","uid="+(uid != null ? uid : "")+"&spCode="+(spCode!=null?spCode:""));

                Log.d("API","RESULT: "+result);

                JSONObject jsonObjTotalAdList =  new JSONObject(result);
                if(jsonObjTotalAdList.get("status").equals("1")) {

                    JSONArray data = jsonObjTotalAdList.getJSONArray("data");

                    if(data != null) {

                        // 상점 정보 setting ===================
                        shopInfo = data.getJSONObject(0);
                        // 상점 정보 setting ===================

                        Log.d("DEBUG","adminPathImage:"+shopInfo.getString("adminPathImage"));
                        Log.d("DEBUG","spSimpleAddr:"+shopInfo.getString("spSimpleAddr"));
                    }
                }
            }catch(Exception e){
                Log.d("error",""+e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            showInfo();
        }
    }

    private void showInfo(){
        try{
            shopInfoOpentime.setText(shopInfo.getString("openTime")+" ~ "+shopInfo.getString("closeTime"));
            //                            shopInfoaddress.setText(shopInfo.getString("spSimpleAddr"));
            shopInfoaddress.setText(shopInfo.getString("spSimpleAddr"));

            // 이미지 없음 image ( default로 출력 )
            String adminPathImage = "https://m.delivera.co.kr/img/store_noimg_color.gif" ;
            String imgPath = shopInfo.getString("adminPathImage");

            if(false == shopInfo.isNull("adminPathImage") && false == imgPath.trim().equals("")){
                adminPathImage = imgPath ;
            }

            Glide.with(getApplicationContext()).load(adminPathImage).into(shopImage);

        }catch (Exception e){

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        /*try{
            shopInfoOpentime.setText(shopInfo.getString("openTime")+" ~ "+shopInfo.getString("closeTime"));
            //                            shopInfoaddress.setText(shopInfo.getString("spSimpleAddr"));
            shopInfoaddress.setText(shopInfo.getString("spSimpleAddr"));

            // 이미지 없음 image ( default로 출력 )
            String adminPathImage = "https://m.delivera.co.kr/img/store_noimg_color.gif" ;
            String imgPath = shopInfo.getString("adminPathImage");

            if(false == shopInfo.isNull("adminPathImage") && false == imgPath.trim().equals("")){
                adminPathImage = imgPath ;
            }

            Glide.with(getApplicationContext()).load(adminPathImage).into(shopImage);

        }catch (Exception e){

        }*/
    }


}
