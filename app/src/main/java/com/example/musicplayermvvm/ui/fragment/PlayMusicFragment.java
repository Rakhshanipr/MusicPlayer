package com.example.musicplayermvvm.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.musicplayermvvm.R;
import com.example.musicplayermvvm.data.model.Music;
import com.example.musicplayermvvm.databinding.FragmentPlayMusicBinding;
import com.example.musicplayermvvm.services.MusicService;
import com.example.musicplayermvvm.veiwmodel.PlayMusicViewModel;

import java.util.HashMap;
import java.util.Random;

public class PlayMusicFragment extends Fragment {

    //region defind static method and variable
    public static final String ARGS_KEY_MUSIC = "com.example.musicplayermvvm.ui.fragment.keyMusic";

    public static PlayMusicFragment newInstance(Music music) {
        PlayMusicFragment fragment = new PlayMusicFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_KEY_MUSIC, music);
        fragment.setArguments(args);
        return fragment;
    }
    //endregion

    Handler mHandler=new Handler();
    FragmentPlayMusicBinding mPlayMusicBinding;

    Music mMusic;
    PlayMusicViewModel mMusicViewModel;

    Runnable updateCurrentMusic = new Runnable() {

        @Override
        public void run() {
            mPlayMusicBinding.textViewPlayedTime.setText(mMusicViewModel.getCurrentPosition());
            mPlayMusicBinding.seekBarDuration.setProgress(
                    Music.convertMilliToSecond(mMusicViewModel.getCurrentMillis()),true);
            mHandler.postDelayed(this,1000);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initial();
    }

    private void initial() {

        mMusic = (Music) getArguments().getSerializable(ARGS_KEY_MUSIC);

        mMusicViewModel = new ViewModelProvider(requireActivity()).get(PlayMusicViewModel.class);

        mMusicViewModel.setReactionMusicPlayer(new PlayMusicViewModel.IReactionMusicPlayer() {
            @Override
            public void playPauseClicked() {
                if (mMusicViewModel.isPlaying()) {
                    mPlayMusicBinding.imageButtonPlayPuase.setImageDrawable(
                            AppCompatResources.getDrawable(getContext(), R.mipmap.pausemusic));
                } else {
                    mPlayMusicBinding.imageButtonPlayPuase.setImageDrawable(
                            AppCompatResources.getDrawable(getContext(), R.mipmap.playmusic));
                }
            }

            @Override
            public void setInfo(Music music) {
                mPlayMusicBinding.textViewTitleMusic.setText(music.getName());
                mPlayMusicBinding.textViewDuration.setText(music.getFormatedTime());
            }
        });

        try {
            mMusicViewModel.setMusic(mMusic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPlayMusicBinding = DataBindingUtil.inflate(inflater
                , R.layout.fragment_play_music
                , container, false);

        mPlayMusicBinding.setMusicPlayViewModel(mMusicViewModel);

        mHandler.postDelayed(updateCurrentMusic, 1000);

        return mPlayMusicBinding.getRoot();
    }
}