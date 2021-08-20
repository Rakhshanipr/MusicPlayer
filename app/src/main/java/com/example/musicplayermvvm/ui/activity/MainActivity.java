package com.example.musicplayermvvm.ui.activity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.musicplayermvvm.R;
import com.example.musicplayermvvm.data.model.Music;
import com.example.musicplayermvvm.databinding.ActivityMainBinding;
import com.example.musicplayermvvm.services.MusicService;
import com.example.musicplayermvvm.ui.fragment.MusicBarFragment;
import com.example.musicplayermvvm.veiwmodel.InfoMusicViewModel;
import com.example.musicplayermvvm.veiwmodel.MainActivityViewModel;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity implements InfoMusicViewModel.ICallbackMusicInfo {

    //region defind static method and variable
    public static final int REQUEST_CODE_GET_PERMISSION = 1;
    //endregion

    //region defind variable
    ActivityMainBinding mMainBinding;
    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMainBinding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(mMainBinding.getRoot());
        mMainBinding.containerNavigationMusicBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(PlayMusicActivity.newIntent(getApplicationContext(), MusicService.sMusic));
            }
        });
        getPermission();
        Initial();
    }

    private void Initial() {
        mMainBinding.viewPager2Main.setAdapter(
                MainActivityViewModel.CreateMainViewPager(this)
        );

        mMainBinding.containerNavigationMusicBar.setVisibility(View.INVISIBLE);

        String[] titles={"Tracks","Artist","Album","Favorit"};
        // attaching tab mediator
        new TabLayoutMediator(mMainBinding.tabLayout
                , mMainBinding.viewPager2Main,
                (tab, position) -> tab.setText(titles[position])).attach();
    }

    private void getPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext()
                , Manifest.permission.READ_EXTERNAL_STORAGE) == -1) {
            this.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
                    , REQUEST_CODE_GET_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions
            , @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_GET_PERMISSION
                && grantResults[0] == 0) {
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onMusicClick(Music music) {
        mMainBinding.containerNavigationMusicBar.setVisibility(View.VISIBLE);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container_music_bar);
        if (fragment == null) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_music_bar, MusicBarFragment.newInstance(music))
                    .commit();
        } else {
            ((MusicBarFragment) fragment).updateInfo(music);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container_music_bar);
//        if (fragment != null) {
//            getSupportFragmentManager().beginTransaction()
//                    .remove(fragment)
//                    .commit();
//            mMainBinding.containerNavigationMusicBar.setVisibility(View.INVISIBLE);
//        }
    }
}