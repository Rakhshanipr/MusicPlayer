package com.example.musicplayermvvm.veiwmodel;

import android.content.Context;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.musicplayermvvm.data.model.Music;

public class InfoMusicViewModel extends BaseObservable {

    Music mMusic;

    ICallbackMusicInfo mCallbackMusicInfo;

    public Music getMusic() {
        return mMusic;
    }

    public InfoMusicViewModel(ICallbackMusicInfo callbackMusicInfo) {
        mCallbackMusicInfo = callbackMusicInfo;
    }

    public void setMusic(Music music) {
        mMusic = music;
        notifyChange();
    }

    public ICallbackMusicInfo getCallbackMusicInfo() {
        return mCallbackMusicInfo;
    }

    public void setCallbackMusicInfo(ICallbackMusicInfo callbackMusicInfo) {
        mCallbackMusicInfo = callbackMusicInfo;
    }

    @Bindable
    public String getTitle() {
        return mMusic == null ? " " : mMusic.getName();
    }

    @Bindable
    public String getTime() {
        return mMusic == null ? " " : mMusic.getFormatedTime();
    }

    @Bindable
    public String getSigner() {
        return mMusic == null ? " " : mMusic.getArtist();
    }

    public void onClickMusic(Context context) {

        getCallbackMusicInfo().onMusicClick(mMusic);
//        context.startActivity(
//                PlayMusicActivity.newIntent(context,getMusic()));

    }

    public interface ICallbackMusicInfo {
        void onMusicClick(Music music);
    }
}