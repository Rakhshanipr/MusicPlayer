package com.example.musicplayermvvm.veiwmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.musicplayermvvm.data.adapter.MainViewPagerAdapter;

public class MainActivityViewModel extends AndroidViewModel {

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
    }

    public static MainViewPagerAdapter CreateMainViewPager(Activity activity){
        return new MainViewPagerAdapter((FragmentActivity) activity);
    }

}
