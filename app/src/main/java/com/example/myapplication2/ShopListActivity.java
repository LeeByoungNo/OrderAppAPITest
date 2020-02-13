package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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

    private RecyclerView.Adapter mAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.active_shop_list);

        recyclerView = findViewById(R.id.shop_list);

        Log.d("상점목록","recylerView find.."+recyclerView);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ShopListAdapter(null);
        recyclerView.setAdapter(mAdapter);

        final List<JSONObject> shopList = new ArrayList<JSONObject>();

        new Thread(){
            public void run(){

                String spCode = "J00001002000003" ;

                try{

                    String result = HttpUtil.sendPostData("https://m.delivera.co.kr/api/storeListDeli.json","type=1&x=37.4732219&y=126.8843009&pageSize=20&pageNo=1");

                    Log.d("API","RESULT: "+result);
                    final JSONObject jsonObject = new JSONObject(result);

                    //
                    if(jsonObject.get("status").equals("1")) {

                        JSONArray data = jsonObject.getJSONArray("data");
                        if(data != null) {
                            for(int j=0; j < data.length() ; j++) {
                                JSONObject shopInfo = data.getJSONObject(j);
                                //
                                Log.d("상점 테스트: 내용 => ",j+":" +shopInfo.getString("spCode"));
                                shopList.add(shopInfo);
                            }

                        }

                    }

                    mAdapter.notifyDataSetChanged();

                }catch(Exception e){
                    Log.d("error",""+e);
                }



            }
        }.start();

        mAdapter = new ShopListAdapter(shopList);
        recyclerView.setAdapter(mAdapter);

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
            }

            public void bindHolderShop(JSONObject shopInfo){
                this.shopInfo = shopInfo ;

                try{

                    mShopName.setText(shopInfo.getString("spName"));

                    /*Uri uriShopImage = new Uri("");

                    mShopImage.setImageURI("https://m.delivera.co.kr/img/store_noimg_color.gif");*/
                    final String adminPathImage = "https://m.delivera.co.kr/img/store_noimg_color.gif" ;

                    Thread mThread = new Thread(){
                        @Override
                        public void run() {

                            try{
                                //
                                URL url = new URL(adminPathImage);

                                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                                conn.connect();

                                InputStream is = conn.getInputStream();
                                mShopBitmap = BitmapFactory.decodeStream(is);

                            }catch (Exception e){

                            }

                        }
                    };

                    mThread.run();

                    mThread.join();
                    mShopImage.setImageBitmap(mShopBitmap);


                }catch (Exception e){

                }


            }
        }
    }

}
