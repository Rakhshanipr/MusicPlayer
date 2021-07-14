package com.example.musicplayermvvm.veiwmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.musicplayermvvm.data.model.Music;

public class InfoMusicViewModel extends AndroidViewModel {

    Music mMusic;

    MutableLiveData<Music> mMusicLiveData=new MutableLiveData<>();

    public InfoMusicViewModel(@NonNull Application application) {
        super(application);
    }


    public Music getMusic() {
        return mMusic;
    }

    public void setMusic(Music music) {
        mMusic = music;
        mMusicLiveData.setValue(music);
    }

    public MutableLiveData<Music> getMusicLiveData() {
        return mMusicLiveData;
    }

    public void setMusicLiveData(MutableLiveData<Music> musicLiveData) {
        mMusicLiveData = musicLiveData;
    }

    public String getTitle() {
        return mMusic.getName();
    }

    public String getTime() {
        return mMusic.getDuration();
    }

    public String getSigner() {
        return mMusic.getArtist();
    }

    public void onClickMusic(){
        Context context;
    }

}