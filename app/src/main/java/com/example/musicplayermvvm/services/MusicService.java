package com.example.musicplayermvvm.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class MusicService extends Service {

    //region defind static method and variable
    public static Intent newIntent(Context context) {
        return new Intent(context, MusicService.class);
    }

    //endregion

    MediaPlayer mMediaPlayer;

    IBinder mBinder=new MusicBinder();

    public MusicService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }


    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        mMediaPlayer = mediaPlayer;
    }

    public MediaPlayer getMediaPlayer(){
        return mMediaPlayer;
    }

    public int getCurrentPosition(){
        return mMediaPlayer.getCurrentPosition();
    }

    public void playPauseMusic(){
        if (getMediaPlayer().isPlaying())
            getMediaPlayer().pause();
        else
            getMediaPlayer().start();
    }

    public class MusicBinder extends Binder{
        public MusicService getMusicService(){
            return MusicService.this;
        }
    }


}