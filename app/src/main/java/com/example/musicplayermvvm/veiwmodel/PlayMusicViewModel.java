package com.example.musicplayermvvm.veiwmodel;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Telephony;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.musicplayermvvm.data.model.Music;
import com.example.musicplayermvvm.services.MusicService;

import java.io.IOException;

public class PlayMusicViewModel extends AndroidViewModel {

    Context mContext;
    Music mMusic;
    MusicService mMusicService;

    public PlayMusicViewModel(@NonNull Application application,Music music) {
        super(application);
        mContext=getApplication().getApplicationContext();
        mMusic=music;

        mContext.bindService(MusicService.newIntent(mContext),mConnection
                ,Context.BIND_AUTO_CREATE);
        try {

            MediaPlayer mediaPlayer=new MediaPlayer();
            mediaPlayer.setDataSource(mMusic.getFilePath());
            mMusicService.setMediaPlayer(mediaPlayer);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void PlayMuisc(){
        mMusicService.playPauseMusic();
    }

    public void PauseMuisc(){
        mMusicService.playPauseMusic();
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            if (!(service instanceof MusicService.MusicBinder))
                Log.e("service", "this service is wrong");


            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            mMusicService = binder.getMusicService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}