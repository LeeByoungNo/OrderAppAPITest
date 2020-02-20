package com.example.myapplication2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


public class BannerImageShowFragment extends Fragment {

    private String TAG = "FRAGMENT Banner" ;

    private ImageView mImageView ;

    public static BannerImageShowFragment newInstance(){
        return new BannerImageShowFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_banner_show,container,false) ;

        Log.d(TAG,"onCreateView");

        mImageView = v.findViewById(R.id.fragment_banner_image);

        Glide.with(getActivity()).load("http://img.delivera.co.kr/0000/0000_banner_20200128175201377.jpg").into(mImageView);

        return v;
    }
}
