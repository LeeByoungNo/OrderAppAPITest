package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.example.myapplication2.util.HttpUtil;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AppMainUiActivity extends AppCompatActivity {

    private RecyclerView recyclerView ;
    private RecyclerView.LayoutManager layoutManager ;

    private RecyclerView.Adapter mAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main_ui);

        recyclerView = findViewById(R.id.app_main_ui_list);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        layoutManager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(layoutManager);

        final List<JSONObject> categoryList = new ArrayList<JSONObject>();

        try{
            JSONObject allCategory = new JSONObject();
            allCategory.put("code","0");
            allCategory.put("codeName","전체");
            categoryList.add(allCategory);
        }catch (Exception e){
            Log.e("ERROR","error",e);
        }


        new Thread(){
            public void run(){

                try{
                    String result = HttpUtil.sendGetData("https://m.delivera.co.kr/api/categoryList.json","");

                    Log.d("API","RESULT: "+result);
                    final JSONObject jsonObject = new JSONObject(result);

                    //
                    if(jsonObject.get("status").equals("1")) {

                        JSONArray data = jsonObject.getJSONArray("data");
                        if(data != null) {
                            for(int j=0; j < data.length() ; j++) {
                                JSONObject categoryInfo = data.getJSONObject(j);
                                //
                                Log.d("상점 카테고리: 이름 => ",j+":" +categoryInfo.getString("codeName"));
                                categoryList.add(categoryInfo);
                            }
                        }
                    }
                    mAdapter.notifyDataSetChanged();

                }catch(Exception e){
                    Log.d("error",""+e);
                }
            }
        }.start();

        mAdapter = new AppMainUiActivity.ShopCategoryAdapter(categoryList);
        recyclerView.setAdapter(mAdapter);
    }


    private class ShopCategoryAdapter extends RecyclerView.Adapter<ShopCateogryHolder>{

        private List<JSONObject> mShopCategoryList;

        public ShopCategoryAdapter(List<JSONObject> mShopCategoryList) {
            this.mShopCategoryList = mShopCategoryList;
        }

        @NonNull
        @Override
        public ShopCateogryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.activity_app_main_element, parent, false);

            ShopCateogryHolder holder = new ShopCateogryHolder(v);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ShopCateogryHolder holder, int position) {
            holder.bindShopCategory(mShopCategoryList.get(position));
        }

        @Override
        public int getItemCount() {
            return mShopCategoryList.size();
        }


    }
    private class ShopCateogryHolder extends RecyclerView.ViewHolder{

        JSONObject categoryInfo ;

        public ImageButton mCategoryButton ;

        public ShopCateogryHolder(@NonNull View itemView) {
            super(itemView);

            mCategoryButton = itemView.findViewById(R.id.shop_category_element);



        }

        public void bindShopCategory(JSONObject categoryInfo){
            this.categoryInfo = categoryInfo ;

            try {

//                mCategoryButton.setText(categoryInfo.getString("codeName"));
//                mCategoryButton.setBackground(Drawable);

                String code = categoryInfo.getString("code");

                int codeInt = Integer.parseInt(code);

                Glide.with(getApplicationContext()).load("https://m.delivera.co.kr/img/bt0"+(codeInt+1)+".jpg").into(mCategoryButton);

                final String codeToSearch = code ;


                mCategoryButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

//                    Toast.makeText(AppMainUiActivity.this, "상점 목록으로 이동(CODE:"+codeToSearch+")", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplication(), ShopListActivity.class);

                        intent.putExtra("type",codeToSearch);
                        startActivity(intent);
                    }
                });

            }catch (Exception e){

            }

        }

    }
}

