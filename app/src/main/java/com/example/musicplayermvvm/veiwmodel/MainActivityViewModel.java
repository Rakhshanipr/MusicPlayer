package com.example.musicplayermvvm.veiwmodel;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;

import com.example.musicplayermvvm.data.adapter.MainViewPagerAdapter;

public class MainActivityViewModel extends ViewModel {

    public static MainViewPagerAdapter CreateMainViewPager(Activity activity){
        return new MainViewPagerAdapter((FragmentActivity) activity);
    }

}
