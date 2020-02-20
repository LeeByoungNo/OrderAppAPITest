package com.example.myapplication2;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.webkit.WebView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class UserFavoriteShopActivity extends AppCompatActivity {

    //
    private String TAG = "단골매장";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG,"테스트 테스트");

        setContentView(R.layout.activity_user_favorite_shop);

        FragmentManager fm = getSupportFragmentManager();

        fm.beginTransaction()
                .add(R.id.fragment_container,BannerImageShowFragment.newInstance())
                .add(R.id.fragment_container,UserFavoriteShopFragment.newInstance())
                .commit();

    }

}
