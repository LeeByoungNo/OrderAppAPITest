package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication2.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ShopReviewListActivity extends AppCompatActivity {

    private RecyclerView recyclerView ;

    private RecyclerView.LayoutManager layoutManager;

    private RecyclerView.Adapter mAdapter ;

    private List<JSONObject> reviewList = new ArrayList<JSONObject>() ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_review_list);


        Log.d("test","ShopReviewListActivity's onCreate...");

        recyclerView = findViewById(R.id.shop_review_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

//       final List<String> reviewList = new ArrayList<String>();

        /*final List<JSONObject> reviewList = new ArrayList<JSONObject>();

        new Thread(){
            public void run(){

                String spCode = "J00001002000003" ;

                try{

                    String result = HttpUtil.sendPostData("https://m.delivera.co.kr/api/shopReviewList.json","spCode="+spCode+"&pageSize=20&pageNo=1");

                    Log.d("API","RESULT: "+result);
                    final JSONObject jsonObject = new JSONObject(result);

                    //
                    if(jsonObject.get("status").equals("1")) {

                        JSONArray data = jsonObject.getJSONArray("data");
                        if(data != null) {
                            for(int j=0; j < data.length() ; j++) {
                                JSONObject reviewInfo = data.getJSONObject(j);

                                //
                                Log.d("리뷰 테스트: 내용 => ",j+":" +reviewInfo.getString("content"));

                                reviewList.add(reviewInfo);
                            }

                        }

                    }

                    *//*mAdapter = new MyAdapter(reviewList);
                    recyclerView.setAdapter(mAdapter);*//*

                    mAdapter.notifyDataSetChanged();

                }catch(Exception e){
                    Log.d("error",""+e);
                }



            }
        }.start();

        mAdapter = new MyAdapter(reviewList);
        recyclerView.setAdapter(mAdapter);*/

        setupAdapter();

        new FetchReviewListTask().execute("");

    }

    private void setupAdapter(){
        recyclerView.setAdapter(new MyAdapter(reviewList));
    }

    private class FetchReviewListTask extends AsyncTask<String,Void,Void>{
        @Override
        protected Void doInBackground(String... strings) {

            String spCode = "J00001002000003" ;

            try{

                String result = HttpUtil.sendPostData("https://m.delivera.co.kr/api/shopReviewList.json","spCode="+spCode+"&pageSize=20&pageNo=1");

                Log.d("API","RESULT: "+result);
                final JSONObject jsonObject = new JSONObject(result);

                //
                if(jsonObject.get("status").equals("1")) {

                    JSONArray data = jsonObject.getJSONArray("data");
                    if(data != null) {
                        for(int j=0; j < data.length() ; j++) {
                            JSONObject reviewInfo = data.getJSONObject(j);

                            Log.d("리뷰 테스트: 내용 => ",j+":" +reviewInfo.getString("content"));

                            reviewList.add(reviewInfo);
                        }

                    }

                }

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

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

        private List<JSONObject> mDataset;

        public MyAdapter(List<JSONObject> dataset) {
            mDataset = dataset;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.activity_review_item, parent, false);

            MyViewHolder myViewHolder = new MyViewHolder(v);

            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            JSONObject reviewInfo = mDataset.get(position) ;

            try {
                holder.mTitle.setText(reviewInfo.getString("userId"));
                holder.mContent.setText(reviewInfo.getString("content"));
                holder.mRegDate.setText(reviewInfo.getString("regDateFormatted"));

            }catch (Exception e){

            }
        }

        @Override
        public int getItemCount() {
            return mDataset.size() ;

        }



        public class MyViewHolder extends RecyclerView.ViewHolder{

            public TextView mTitle ;
            public TextView mContent ;
            public TextView mRegDate ;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                mTitle = itemView.findViewById(R.id.review_item_user_id);
                mContent = itemView.findViewById(R.id.review_item_content);
                mRegDate = itemView.findViewById(R.id.review_item_reg_date);

            }
        }
    }
}
