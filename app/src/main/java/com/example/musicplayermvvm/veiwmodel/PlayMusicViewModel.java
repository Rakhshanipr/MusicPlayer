package com.example.musicplayermvvm.veiwmodel;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.musicplayermvvm.data.model.Music;
import com.example.musicplayermvvm.services.MusicService;

import java.io.IOException;

public class PlayMusicViewModel extends AndroidViewModel {

    Context mContext;
    Music mMusic;
    MusicService mMusicService;
    boolean mBound = false;
    IReactionMusicPlayer mReactionMusicPlayer;

    public PlayMusicViewModel(@NonNull Application application) {
        super(application);
        mContext = getApplication().getApplicationContext();

    }

    public Music getMusic() {
        return mMusic;
    }

    public boolean isPlaying(){
        return mMusicService.getMediaPlayer().isPlaying();
    }

    public void setServicesData() {
        if (mBound) {

            try {


                mMusicService.changeMediaPlayerData(getMusic().getFilePath());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void setMusic(Music music) throws Exception {
        if (mReactionMusicPlayer==null){
            throw new Exception("must be set Interface reaction");
        }
        mMusic = music;
        mContext.bindService(MusicService.newIntent(mContext), mConnection
                , Context.BIND_AUTO_CREATE);
    }

    public IReactionMusicPlayer getReactionMusicPlayer() {
        return mReactionMusicPlayer;
    }

    public void setReactionMusicPlayer(IReactionMusicPlayer reactionMusicPlayer) {
        mReactionMusicPlayer = reactionMusicPlayer;
    }

    public void PlayMuisc() {
        mMusicService.playPauseMusic();
        mReactionMusicPlayer.playPauseClicked();
    }

    public void nextMusic() {
        try {
            mMusicService.changeMediaPlayerData(getMusic().getNext().getFilePath());
            setMusic(getMusic().getNext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prevMusic(){
        try {
            mMusicService.changeMediaPlayerData(getMusic().getPrev().getFilePath());
            setMusic(getMusic().getPrev());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getTitle(){
        return mMusic.getName();
    }

    public String getFullTime(){
        return Integer.toString(mMusicService.getDuration());
    }

    public String getCurrentPosition(){
        return Integer.toString(mMusicService.getCurrentPosition());
    }

    public void PauseMuisc() {
        mMusicService.playPauseMusic();
        mReactionMusicPlayer.playPauseClicked();
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            if (!(service instanceof MusicService.MusicBinder))
                Log.e("service", "this service is wrong");


            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            mMusicService = binder.getMusicService();
            mBound = true;
            setServicesData();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    public interface IReactionMusicPlayer {
        void playPauseClicked();

    }
}