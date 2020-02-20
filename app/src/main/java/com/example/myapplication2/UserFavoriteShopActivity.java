package com.example.myapplication2;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

public class UserFavoriteShopActivity extends AppCompatActivity {

    //

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        setContentView(R.layout.activity_user_favorite_shop);

        FragmentManager fm = getSupportFragmentManager();

        fm.beginTransaction()
                .add(R.id.fragment_container,UserFavoriteShopFragment.newInstance())
                .commit();

    }
}
