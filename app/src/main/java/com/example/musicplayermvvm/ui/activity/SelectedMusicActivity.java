package com.example.musicplayermvvm.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.musicplayermvvm.R;
import com.example.musicplayermvvm.data.adapter.MainViewPagerAdapter;
import com.example.musicplayermvvm.databinding.ActivitySelectedMusicBinding;
import com.example.musicplayermvvm.ui.fragment.ListMusicFragment;
import com.example.musicplayermvvm.services.MusicService;

public class SelectedMusicActivity extends AppCompatActivity {

    //region defind static method and variable
    public static final String TAG_SELCTED_MUSIC_FRAGMENT = "com.example.musicplayermvvm.ui.activity.TAG_SELCTED_MUSIC_FRAGMENT";
    //endregion

    //region defind variable

    ActivitySelectedMusicBinding mBinding;

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivitySelectedMusicBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_selected_music, ListMusicFragment.newInstance(), TAG_SELCTED_MUSIC_FRAGMENT)
                .commit();
        Activity activity=this;
        AppCompatActivity appCompatActivity=(AppCompatActivity)activity;
        FragmentManager a=appCompatActivity.getSupportFragmentManager();
        int af=1;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainViewPagerAdapter.sFragment_state = 0;
    }
}