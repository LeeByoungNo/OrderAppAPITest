package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myapplication2.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdListActivity extends AppCompatActivity {

    private RecyclerView recyclerView ;
    private RecyclerView.LayoutManager layoutManager;

    private RecyclerView.Adapter adListAdapter ;

    private List<JSONObject> advertiseList = new ArrayList<JSONObject>();

    @Override
    protected void onResume() {
        super.onResume();

        //adapter.addAll(list);

//        adapter.notifyDataSetChanged();
    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);

        //adapter.addAll(list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.advertise_list);
        recyclerView = findViewById(R.id.advertise_list);

        //데이터를 저장하게 되는 리스트

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

//        final List<JSONObject> advertiseList = new ArrayList<JSONObject>();

        // data GET =================================================================
       /* new Thread(){
            public void run(){*//*

                try{

                    String result = HttpUtil.sendPostData("https://m.delivera.co.kr/api/totalAdList.json","");

                    Log.d("API","RESULT: "+result);


                    JSONObject jsonObjTotalAdList =  new JSONObject(result);
                    if(jsonObjTotalAdList.get("status").equals("1")) {

                        JSONArray data = jsonObjTotalAdList.getJSONArray("data");

                        if(data != null) {
                            for(int j=0; j < data.length() ; j++) {

                                JSONObject adInfo = data.getJSONObject(j);
                                Log.d("debug",adInfo.get("subject")+""+adInfo.get("regDate"));

                                advertiseList.add(adInfo);
                            }
                        }
                    }

                    // 일반광고
                    result = HttpUtil.sendPostData("https://m.delivera.co.kr/api/publicAdList.json", "mode=A");

                    JSONObject jsonObj =  new JSONObject(result);
                    if(jsonObj.get("status").equals("1")) {

                        JSONArray data = jsonObj.getJSONArray("data");

                        if(data != null) {
                            for(int j=0; j < data.length() ; j++) {
                                JSONObject adInfo = data.getJSONObject(j);
                                // null check
                                *//**//*if(false == adInfo.isNull("htmlUrl")) {
                                    dataInfo.put("html_url", (String)adInfo.get("htmlUrl"));
                                }*//**//*
                                advertiseList.add(adInfo);
                            }
                        }
                    }
                    // 일반광고

                    adListAdapter.notifyDataSetChanged();

                }catch(Exception e){
                    Log.d("error",""+e);
                }
            }
        }.start();*/
        // data GET =================================================================

//        Log.d("DEBUG","onCreate 's "+advertiseList.size());

/*        adListAdapter = new AdListAdapter(advertiseList);
        recyclerView.setAdapter(adListAdapter);*/

        setupAdapter();

        new FetchAdListTask().execute("");

    }

    private void setupAdapter(){
        recyclerView.setAdapter(new AdListAdapter(advertiseList));
    }
    private class FetchAdListTask extends AsyncTask<String,Void,Void>{

        @Override
        protected Void doInBackground(String... strings) {

            try{

                String result = HttpUtil.sendPostData("https://m.delivera.co.kr/api/totalAdList.json","");

                Log.d("API","RESULT: "+result);


                JSONObject jsonObjTotalAdList =  new JSONObject(result);
                if(jsonObjTotalAdList.get("status").equals("1")) {

                    JSONArray data = jsonObjTotalAdList.getJSONArray("data");

                    if(data != null) {
                        for(int j=0; j < data.length() ; j++) {

                            JSONObject adInfo = data.getJSONObject(j);
                            Log.d("debug",adInfo.get("subject")+""+adInfo.get("regDate"));

                            advertiseList.add(adInfo);
                        }
                    }
                }

                // 일반광고
                result = HttpUtil.sendPostData("https://m.delivera.co.kr/api/publicAdList.json", "mode=A");

                JSONObject jsonObj =  new JSONObject(result);
                if(jsonObj.get("status").equals("1")) {

                    JSONArray data = jsonObj.getJSONArray("data");

                    if(data != null) {
                        for(int j=0; j < data.length() ; j++) {
                            JSONObject adInfo = data.getJSONObject(j);
                            // null check
                                /*if(false == adInfo.isNull("htmlUrl")) {
                                    dataInfo.put("html_url", (String)adInfo.get("htmlUrl"));
                                }*/
                            advertiseList.add(adInfo);
                        }
                    }
                }
                // 일반광고

                adListAdapter.notifyDataSetChanged();

            }catch(Exception e){
                Log.d("error",""+e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            setupAdapter();
        }
    }

    private class AdListAdapter extends RecyclerView.Adapter<AdListAdapter.AdInfoHolder>{

        private List<JSONObject> mAdvList;

        public AdListAdapter(List<JSONObject> mAdvList) {

            Log.d("DEBUG::::","AdListAdapter's constructor..."+mAdvList.size());

            this.mAdvList = mAdvList;
        }

        @NonNull
        @Override
        public AdInfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_advertise_item,parent,false);

            AdInfoHolder holder = new AdInfoHolder(v);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull AdInfoHolder holder, int position) {
            JSONObject advertiseInfo = mAdvList.get(position);

            Log.d("DEBUG::::","AdListAdapter's onBindViewHolder...");

            holder.bindHolderAdvertisement(advertiseInfo);
        }

        @Override
        public int getItemCount() {
            return (mAdvList != null ? mAdvList.size() : 0) ;
        }

        public class AdInfoHolder extends RecyclerView.ViewHolder{

            private JSONObject advInfo ;

            private TextView subject ;
            private ImageView banner ;

            public AdInfoHolder(@NonNull View itemView) {
                super(itemView);

                subject = itemView.findViewById(R.id.advertise_subject);
                banner = itemView.findViewById(R.id.advertise_banner_image);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            String linkType =  advInfo.getString("linkType");
                            String htmlUrl = advInfo.getString("htmlUrl");


                            Log.d("DBUG","linkType : "+linkType);

                            if(linkType.equals("I")){  // 내부 URL
                                //
                                Intent intent = new Intent(getApplication(), WebViewActivity.class);

                                intent.putExtra("htmlUrl",htmlUrl);
                                startActivity(intent);

                            }else if(linkType.equals("O")){  // 외부 URL
                                Toast.makeText(AdListActivity.this, "외부 URL입니다.", Toast.LENGTH_SHORT).show();

                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(htmlUrl));
                                startActivity(browserIntent);

                            }else if(linkType.equals("A")){  // 주문앱의 상점Activity
                                Toast.makeText(AdListActivity.this, "주문앱 상점 으로 이동", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplication(), ShopInfoActivity.class);

                                String spCode = advInfo.getString("spCode");

                                intent.putExtra("spCode",spCode);
                                startActivity(intent);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            public void bindHolderAdvertisement(JSONObject advInfo){

                this.advInfo = advInfo ;

                Log.d("debug","bindHolderAdvertisement");

                try {
                    subject.setText(advInfo.getString("subject"));

                    Glide.with(getApplicationContext()).load(advInfo.getString("bannerUrl")).into(banner);

                }catch (Exception e){

                }
            }
        }
    }
}
