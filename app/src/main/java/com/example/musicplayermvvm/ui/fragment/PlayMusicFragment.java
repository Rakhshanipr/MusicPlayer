package com.example.musicplayermvvm.ui.fragment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.musicplayermvvm.R;
import com.example.musicplayermvvm.databinding.FragmentPlayMusicBinding;
import com.example.musicplayermvvm.services.MusicService;

public class PlayMusicFragment extends Fragment {

    public static PlayMusicFragment newInstance() {
        PlayMusicFragment fragment = new PlayMusicFragment();
        return fragment;
    }

    FragmentPlayMusicBinding mPlayMusicBinding;
    MusicService mMusicService;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPlayMusicBinding= DataBindingUtil.inflate(inflater
                ,R.layout.fragment_play_music
                ,container,false);
        return mPlayMusicBinding.getRoot();
    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        Intent intent = MusicService.newIntent(getContext());
//
//        getContext().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
//    }
//
//    @Override
//    public void onStop() {
//        super.onStop();
//        getContext().unbindService(mConnection);
//    }

}