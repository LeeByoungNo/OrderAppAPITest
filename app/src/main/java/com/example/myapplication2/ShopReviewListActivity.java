package com.example.myapplication2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ShopReviewListActivity extends AppCompatActivity {

    private RecyclerView recyclerView ;

    private RecyclerView.LayoutManager layoutManager;

    private RecyclerView.Adapter mAdapter ;

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


        String[] mDataset = new String[]{"A","B","C"};

        mAdapter = new MyAdapter(mDataset);

    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

        private String[] mDataset;

        public MyAdapter(String[] dataset) {
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
            holder.mTitle.setText(mDataset[position]);
        }

        @Override
        public int getItemCount() {
            return mDataset.length ;

        }



        public class MyViewHolder extends RecyclerView.ViewHolder{

            public TextView mTitle ;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                mTitle = itemView.findViewById(R.id.list_item_review);

            }
        }
    }
}
