package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapplication2.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ShopListActivity extends AppCompatActivity {

    private RecyclerView recyclerView ;
    private RecyclerView.LayoutManager layoutManager;

//    private RecyclerView.Adapter mAdapter ;

    // Handler TEST ==================
    private Handler myHandler ;
    // Handler TEST ==================

    private List<JSONObject> shopList = new ArrayList<JSONObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_shop_list);

        recyclerView = findViewById(R.id.shop_list);

        Log.d("상점목록","recylerView find.."+recyclerView);

        Intent intent = getIntent();

        String shopCategoryCode = "0" ;

        if(intent != null &&  intent.getExtras() != null && intent.getExtras().getString("type") != null){
            shopCategoryCode = intent.getExtras().getString("type");
        }

        final String toSearchType = shopCategoryCode ;

        Log.d("DEBUG::","parameter's type : "+toSearchType);

        // Handler TEST ==================
        myHandler = new Handler();
        // Handler TEST ==================

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        setupAdapter();

        // TEST ===============================
        new FetchShopListTask().execute(toSearchType);
        // TEST ===============================

    }

    private void setupAdapter(){


        recyclerView.setAdapter(new ShopListAdapter(shopList));
    }

    private class FetchShopListTask extends AsyncTask<String,Void,Void>{


        @Override
        protected Void doInBackground(String... params) {

            // ================================= TEST ==========================
            String spCode = "J00001002000003" ;

            try{

                String result = HttpUtil.sendPostData("https://m.delivera.co.kr/api/storeListDeli.json","type="+(params[0].equals("0")?"":params[0])+"&x=37.4732219&y=126.8843009&pageSize=20&pageNo=1");

                Log.d("API","RESULT: "+result);
                final JSONObject jsonObject = new JSONObject(result);

                //
                if(jsonObject.get("status").equals("1")) {

                    JSONArray data = jsonObject.getJSONArray("data");
                    if(data != null) {
                        for(int j=0; j < data.length() ; j++) {
                            JSONObject shopInfo = data.getJSONObject(j);

                            Log.d("상점 테스트: 내용 => ",j+":" +shopInfo.getString("spCode"));
                            shopList.add(shopInfo);
                        }
                    }
                }

            }catch(Exception e){
                Log.d("error",""+e);
            }
            // ================================= TEST ==========================

            return null;
        }

        @Override
        protected void onPostExecute(Void o) {
            setupAdapter();
        }
    }

    private class ShopListAdapter extends RecyclerView.Adapter<ShopListAdapter.ShopItemHolder>{

        private List<JSONObject> mShopList;

        public ShopListAdapter(List<JSONObject> mShopList) {
            this.mShopList = mShopList;
        }

        @NonNull
        @Override
        public ShopItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.active_shop_item, parent, false);

            ShopItemHolder holder = new ShopItemHolder(v);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ShopItemHolder holder, int position) {

            JSONObject shopInfo = mShopList.get(position) ;

            holder.bindHolderShop(shopInfo);
        }

        @Override
        public int getItemCount() {
            return mShopList != null ? mShopList.size() : 0;
        }

        public class ShopItemHolder extends RecyclerView.ViewHolder{

            JSONObject shopInfo ;

            public TextView mShopName ;
            public ImageView mShopImage ;

            private Bitmap mShopBitmap ;

            public ShopItemHolder(@NonNull View itemView) {
                super(itemView);

                mShopName = itemView.findViewById(R.id.shop_name);
                mShopImage = itemView.findViewById(R.id.shop_image_path);


                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getApplication(), ShopInfoActivity.class);

                        try {
                            intent.putExtra("uid", shopInfo.getString("uid"));
                        }catch (Exception e){

                        }

                        startActivity(intent);

                        // Handler TEST ==================
                        /*myHandler.post(new Runnable() {
                            @OverrideadminPathImage
                            public void run() {
                                Intent intent = new Intent(getApplication(), ShopReviewListActivity.class);
                                startActivity(intent);
                            }
                        });*/
                        // Handler TEST ==================

                    }
                });

            }



            public void bindHolderShop(JSONObject shopInfo){
                this.shopInfo = shopInfo ;

                try{

                    // 상점명
                    mShopName.setText(shopInfo.getString("spName"));

                    // 이미지 없음 image ( default로 출력 )
                    String adminPathImage = "https://m.delivera.co.kr/img/store_noimg_color.gif" ;
                    String imgPath = shopInfo.getString("adminPathImage");

                    if(false == shopInfo.isNull("adminPathImage") && false == imgPath.trim().equals("")){
                        adminPathImage = imgPath ;
                    }

                    // 상점 image 표출
                    Glide.with(getApplicationContext()).load(adminPathImage).into(mShopImage);

                }catch (Exception e){

                }


            }
        }
    }

}
