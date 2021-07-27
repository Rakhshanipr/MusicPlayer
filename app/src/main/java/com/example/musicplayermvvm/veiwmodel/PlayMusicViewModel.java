package com.example.musicplayermvvm.veiwmodel;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.musicplayermvvm.data.model.Music;
import com.example.musicplayermvvm.services.MusicService;

import java.io.IOException;
import java.io.PushbackInputStream;

public class PlayMusicViewModel extends AndroidViewModel {

    //region defind variable
    Context mContext;
    Music mMusic;
    MusicService mMusicService;
    boolean mBound = false;
    IReactionMusicPlayer mReactionMusicPlayer;
    //endregion

    MutableLiveData<Music> mMusicLiveData=new MutableLiveData<>();

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
        if (mMusicService!=null)
        mReactionMusicPlayer.setInfo(mMusic);

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
        return mMusicService==null ? "In the name of God": mMusic.getName();
    }

    public String getFullTime(){
        return mMusicService==null ? "12:12":  Integer.toString(mMusicService.getDuration());
    }

    public int getCurrentMillis(){
        return mMusicService.getCurrentPosition();
    }


    public int getFullTimeSeconds(){
        return Music.convertMilliToSecond(mMusic.getDuration());
    }

    public String getCurrentPosition(){
        return mMusicService==null ? "00:00": Music.convertMilliTotime(mMusicService.getCurrentPosition());
    }

    public void setCurrentPosition(int position){
        if (mMusicService!=null)
        mMusicService.setCurrentPosition(position);
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
            mReactionMusicPlayer.setInfo(mMusic);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
        }
    };

    public interface IReactionMusicPlayer {
        void playPauseClicked();
        void setInfo(Music music);
    }
}