package com.example.myapplication2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication2.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class UserFavoriteShopFragment extends Fragment {

    private RecyclerView mRecyclerView ;

    private RecyclerView.LayoutManager layoutManager;

    private RecyclerView.Adapter mAdapter ;

    public static UserFavoriteShopFragment newInstance(){
        return new UserFavoriteShopFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_user_favorite_shop,container,false) ;

        mRecyclerView = v.findViewById(R.id.user_favorite_recyclerview);

        mRecyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);



        final List<JSONObject> shopList = new ArrayList<JSONObject>();

        new Thread(){
            public void run(){

                try{

                    SharedPreferences sharedPref = getActivity().getSharedPreferences("loginKey", Context.MODE_PRIVATE);
                    String userId = sharedPref.getString("userId","");
                    String loginKey = sharedPref.getString("loginKey","");

                    String result = HttpUtil.sendPostData("https://m.delivera.co.kr/api/userFavoriteShop.json","userId="+userId,loginKey);

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

        mAdapter = new UserFavoriteShopAdapter(shopList);
        mRecyclerView.setAdapter(mAdapter);

        return v;

    }

    private class UserFavoriteShopAdapter extends RecyclerView.Adapter<UserFavoriteShopHolder>{

        List<JSONObject> favoriteShopList ;

        public UserFavoriteShopAdapter(List<JSONObject> favoriteShopList) {
            this.favoriteShopList = favoriteShopList;
        }

        @NonNull
        @Override
        public UserFavoriteShopHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_user_favorite_shop,parent,false);

            UserFavoriteShopHolder holder = new UserFavoriteShopHolder(v);

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull UserFavoriteShopHolder holder, int position) {

            JSONObject favoriteShop = favoriteShopList.get(position);

            holder.bindHolder(favoriteShop);
        }

        @Override
        public int getItemCount() {
            return favoriteShopList.size();
        }
    }

    private class UserFavoriteShopHolder extends RecyclerView.ViewHolder{

        JSONObject shopInfo ;

        public TextView mShopName ;

        public UserFavoriteShopHolder(@NonNull View itemView) {
            super(itemView);

            mShopName = itemView.findViewById(R.id.user_favorite_shop_name);
        }

        public void bindHolder(JSONObject shopInfo){
            this.shopInfo = shopInfo ;

            try{
                mShopName.setText(shopInfo.getString("spName"));
            }catch (Exception e){

            }

        }
    }
}
