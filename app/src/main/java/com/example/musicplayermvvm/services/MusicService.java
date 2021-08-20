package com.example.musicplayermvvm.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.example.musicplayermvvm.data.model.Music;
import com.example.musicplayermvvm.data.repository.MusicRepository;

import java.io.IOException;

public class MusicService extends Service {

    //region defind static method and variable

    public static Intent newIntent(Context context) {
        return new Intent(context, MusicService.class);
    }

    //endregion

    public static boolean sMusicAvailible = false;
    public static Music sMusic=null;

    MediaPlayer mMediaPlayer;
    String mPath = "";
    ICallBackMusicService mCallBackMusicService;

    IBinder mBinder = new MusicBinder();

    Music mMusic;


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

    public void setCallBackMusicService(ICallBackMusicService callBackMusicService) {
        mCallBackMusicService = callBackMusicService;
    }

    public Music getMusic() {
        return mMusic;
    }

    public void setMusic(Music music) {
        mMusic = music;
        sMusicAvailible=true;
        sMusic=music;
    }

    public void next10Second() {
        if (getMediaPlayer().getCurrentPosition() + 10000 <= getMediaPlayer().getDuration()) {
            getMediaPlayer().seekTo(getMediaPlayer().getCurrentPosition() + 10000);
        }
    }

    public void prev10Second() {
        if (getMediaPlayer().getCurrentPosition() - 10000 >= 0) {
            getMediaPlayer().seekTo(getMediaPlayer().getCurrentPosition() - 10000);
        }
    }

    public void setCurrentPosition(int position) {
        mMediaPlayer.seekTo(position);
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        mMediaPlayer = mediaPlayer;
    }

    public void changeMediaPlayerData(String path) throws IOException {
        if (getMediaPlayer() == null) {
            mMediaPlayer = new MediaPlayer();
        }

        sMusicAvailible=true;
        mPath = path;
        mMediaPlayer.reset();
        mMediaPlayer.setDataSource(path);
        mMediaPlayer.prepare();
        mMediaPlayer.start();
        setMusic(MusicRepository.convertDataSourceToMusic(path));

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mCallBackMusicService.completeSong();
            }
        });
    }

    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    public int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    public int getDuration() {
        return mMediaPlayer.getDuration();
    }

    public void playNext() {

    }

    public void playPauseMusic() {
        if (getMediaPlayer().isPlaying())
            getMediaPlayer().pause();
        else
            getMediaPlayer().start();
    }

    public class MusicBinder extends Binder {
        public MusicService getMusicService() {
            return MusicService.this;
        }

        public void songCompleted() {

        }
    }

    public interface ICallBackMusicService {
        void completeSong();
    }
}