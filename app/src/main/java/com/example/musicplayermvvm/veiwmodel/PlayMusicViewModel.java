package com.example.musicplayermvvm.veiwmodel;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.musicplayermvvm.data.adapter.MainViewPagerAdapter;
import com.example.musicplayermvvm.data.model.Music;
import com.example.musicplayermvvm.data.repository.MusicRepository;
import com.example.musicplayermvvm.services.MusicService;
import com.example.musicplayermvvm.utilities.QueryPreferences;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayMusicViewModel extends AndroidViewModel {

    //region defind variable

    Context mContext;
    Music mMusic;
    MusicService mMusicService;
    boolean mBound = false;
    IReactionMusicPlayer mReactionMusicPlayer;

    List<Music> mMusicListPrev;

    MusicRepository mMusicRepository;

    //endregion

    public PlayMusicViewModel(@NonNull Application application) {
        super(application);
        mContext = getApplication().getApplicationContext();
        mContext.bindService(MusicService.newIntent(mContext), mConnection
                , Context.BIND_AUTO_CREATE);
        mMusicListPrev = new ArrayList<>();
        mMusicRepository=MusicRepository.getInstance(mContext);
    }

    public Music getMusic() {
        return mMusic;
    }

    public boolean isPlaying() {
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
        if (mReactionMusicPlayer == null) {
            throw new Exception("must be set Interface reaction");
        }
        mMusic = music;
        if (mMusicListPrev.size() <= 1) {
            mMusicListPrev.add(music);
        } else {
            if (!music.getFilePath().equals(mMusicListPrev
                    .get(mMusicListPrev.size() - 2).getFilePath())) {
                mMusicListPrev.add(music);
            }
        }

        if (mMusicService != null) {
            mMusicService.changeMediaPlayerData(music.getFilePath());
            mReactionMusicPlayer.setInfo(mMusic);
        }


    }

    public IReactionMusicPlayer getReactionMusicPlayer() {
        return mReactionMusicPlayer;
    }

    public void setReactionMusicPlayer(IReactionMusicPlayer reactionMusicPlayer) {
        mReactionMusicPlayer = reactionMusicPlayer;

    }

    public void playMuisc() {
        mMusicService.playPauseMusic();
        mReactionMusicPlayer.playPauseClicked();
    }

    public void nextMusic() {
        try {
            if (QueryPreferences.getRandomPref(mContext)) {
                if (MainViewPagerAdapter.sFragment_state == 0) {
                    int size = MainViewPagerAdapter.sMusicListFull.size();
                    int randome = new Random().nextInt(size);
                    Music music = MainViewPagerAdapter.sMusicListFull.get(randome);

                    setMusic(music);
                } else {
                    int size = MainViewPagerAdapter.sMusicListSelected.size();
                    int randome = new Random().nextInt(size);
                    Music music = MainViewPagerAdapter.sMusicListSelected.get(randome);
                    setMusic(music);
                }
            } else {
                setMusic(getMusic().getNext());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void prevMusic() {
        try {
            if (mMusicListPrev.size() != 0) {
                mMusicService.changeMediaPlayerData(mMusicListPrev
                        .get(mMusicListPrev.size() - 2).getFilePath());

                setMusic(mMusicListPrev
                        .get(mMusicListPrev.size() - 2));
                mMusicListPrev.remove(mMusicListPrev.size() - 1);
            } else {
                mMusicService.changeMediaPlayerData(getMusic().getPrev().getFilePath());

                setMusic(getMusic().getPrev());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void randome() {
        boolean isActive = QueryPreferences.getRandomPref(mContext);
        if (isActive) {
            QueryPreferences.setRandomPref(mContext, false);
        } else
            QueryPreferences.setRandomPref(mContext, true);
        mReactionMusicPlayer.randome(isActive);
    }

    public void repeat() {
        boolean isActive = QueryPreferences.getRepeatPref(mContext);
        if (isActive) {
            QueryPreferences.setRepeatPref(mContext, false);
        } else
            QueryPreferences.setRepeatPref(mContext, true);
        mReactionMusicPlayer.repeat(isActive);
    }

    public void onLike() {
        if (QueryPreferences.getMusicLikePref(mContext,getMusic().getFilePath())){
            mReactionMusicPlayer.like(false);
            QueryPreferences.setMusicLikePref(mContext,getMusic(),true);
        }else {
            mReactionMusicPlayer.like(true);
            QueryPreferences.setMusicLikePref(mContext, getMusic(), false);
        }

        mMusicRepository.setLikeMusic(mContext);
    }

    public String getTitle() {
        return mMusicService == null ? "In the name of God" : mMusic.getName();
    }

    public String getFullTime() {
        return mMusicService == null ? "12:12" : Integer.toString(mMusicService.getDuration());
    }

    public int getCurrentMillis() {
        return mMusicService==null? 0: mMusicService.getCurrentPosition();
    }

    public int getFullTimeSeconds() {
        return Music.convertMilliToSecond(mMusic.getDuration());
    }

    public String getCurrentPosition() {
        return mMusicService == null ? "00:00" : Music.convertMilliTotime(mMusicService.getCurrentPosition());
    }

    public void setCurrentPosition(int position) {
        if (mMusicService != null)
            mMusicService.setCurrentPosition(position);
    }

    public void PauseMuisc() {
        mMusicService.playPauseMusic();
        mReactionMusicPlayer.playPauseClicked();
    }

    public boolean isLiked(){
        return QueryPreferences.getMusicLikePref(mContext,getMusic().getFilePath());
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
            mMusicService.setCallBackMusicService(new MusicService.ICallBackMusicService() {
                @Override
                public void completeSong() {
                    if (QueryPreferences.getRepeatPref(mContext)) {
                        try {
                            mMusicService.changeMediaPlayerData(getMusic().getFilePath());
                            setMusic(getMusic());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        nextMusic();
                    }
                }
            });
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

        void randome(boolean isActive);

        void repeat(boolean isActive);

        void like(boolean isActive);
    }

}