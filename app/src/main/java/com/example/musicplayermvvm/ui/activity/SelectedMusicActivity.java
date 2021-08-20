package com.example.musicplayermvvm.ui.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.musicplayermvvm.R;
import com.example.musicplayermvvm.data.adapter.MainViewPagerAdapter;
import com.example.musicplayermvvm.data.model.Music;
import com.example.musicplayermvvm.databinding.ActivitySelectedMusicBinding;
import com.example.musicplayermvvm.services.MusicService;
import com.example.musicplayermvvm.ui.fragment.ListMusicFragment;
import com.example.musicplayermvvm.ui.fragment.MusicBarFragment;
import com.example.musicplayermvvm.veiwmodel.InfoMusicViewModel;
import com.example.musicplayermvvm.veiwmodel.PlayMusicViewModel;

public class SelectedMusicActivity extends AppCompatActivity
        implements InfoMusicViewModel.ICallbackMusicInfo {

    //region defind static method and variable
    public static final String TAG_SELCTED_MUSIC_FRAGMENT = "com.example.musicplayermvvm.ui.activity.TAG_SELCTED_MUSIC_FRAGMENT";
    //endregion

    //region defind variable

    ActivitySelectedMusicBinding mBinding;

    PlayMusicViewModel mMusicViewModel;

    //endregion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = ActivitySelectedMusicBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());

        Fragment fragment1 = getSupportFragmentManager().findFragmentById(R.id.container_selected_music);
        if (fragment1 == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_selected_music, ListMusicFragment.newInstance(), TAG_SELCTED_MUSIC_FRAGMENT)
                    .commit();
        }


        if (MusicService.sMusicAvailible) {
            Fragment fragment2 = getSupportFragmentManager().findFragmentById(R.id.container_selected_music);
            if (fragment2 == null) {
                mBinding.containerNavigationMusicBar.setVisibility(View.VISIBLE);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_music_bar,
                                MusicBarFragment.newInstance(MusicService.sMusic))
                        .commit();
            }
        } else {
            mBinding.containerNavigationMusicBar.setVisibility(View.INVISIBLE);
        }

        mBinding.containerNavigationMusicBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(PlayMusicActivity.newIntent(getApplicationContext(), MusicService.sMusic));
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainViewPagerAdapter.sFragment_state = 0;
    }

    @Override
    public void onMusicClick(Music music) {
        mBinding.containerNavigationMusicBar.setVisibility(View.VISIBLE);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container_music_bar);
        if (fragment == null) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_music_bar, MusicBarFragment.newInstance(music))
                    .commit();
        } else {
            ((MusicBarFragment) fragment).updateInfo(music);
        }
    }
}