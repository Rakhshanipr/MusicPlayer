package com.example.musicplayermvvm.veiwmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.musicplayermvvm.data.model.Music;

public class MusicInfoListViewModel extends BaseObservable {

    Music mMusic;

    public MusicInfoListViewModel(Music music) {
        mMusic = music;
    }

    public Music getMusic() {
        return mMusic;
    }

    public void setMusic(Music music) {
        mMusic = music;
        notifyPropertyChanged(7);
    }

    @Bindable
    public String getTitle() {
        return mMusic.getName();
    }

    @Bindable
    public String getTime() {
        return mMusic.getDuration();
    }

    @Bindable
    public String getSigner() {
        return mMusic.getArtist();
    }
}
