package com.example.musicplayermvvm.ui.activity;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.media.session.MediaSessionCompat;
import android.view.Menu;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.musicplayermvvm.R;
import com.example.musicplayermvvm.data.model.Music;
import com.example.musicplayermvvm.databinding.ActivityMainBinding;
import com.example.musicplayermvvm.services.MusicService;
import com.example.musicplayermvvm.ui.fragment.MusicBarFragment;
import com.example.musicplayermvvm.utilities.CreateNotification;
import com.example.musicplayermvvm.utilities.GetMusicPicture;
import com.example.musicplayermvvm.utilities.NotificationReciver;
import com.example.musicplayermvvm.veiwmodel.InfoMusicViewModel;
import com.example.musicplayermvvm.veiwmodel.MainActivityViewModel;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends AppCompatActivity implements InfoMusicViewModel.ICallbackMusicInfo {

    //region defind static method and variable
    public static final int REQUEST_CODE_GET_PERMISSION = 1;
    public static final String ACTION_PREV = "ACTION_PREV";
    public static final String ACTION_NEXT = "ACTION_NEXT";
    public static final String ACTION_PLAY = "ACTION_PLAY";
    //endregion

    //region defind variable

    Music mMusic;

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
        if (MusicService.sMusicAvailible&&mMainBinding.containerNavigationMusicBar.getVisibility()==View.INVISIBLE){
            mMainBinding.containerNavigationMusicBar.setVisibility(View.VISIBLE);

            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container_music_bar);
            if (fragment == null) {

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_music_bar, MusicBarFragment.newInstance(MusicService.sMusic))
                        .commit();
            } else {
                ((MusicBarFragment) fragment).updateInfo(MusicService.sMusic);
            }
        }
    }

    @Override
    public void onMusicClick(Music music) {
        mMusic=music;
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
    public void onPanelClosed(int featureId, @NonNull Menu menu) {
        super.onPanelClosed(featureId, menu);
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        mNotificationManager.cancel(MusicService.ID_MUSIC_NOTIFICATION);
    }
}