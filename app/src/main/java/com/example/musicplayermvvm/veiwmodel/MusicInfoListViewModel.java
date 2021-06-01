package com.example.musicplayermvvm.veiwmodel;

import androidx.lifecycle.ViewModel;

import com.example.musicplayermvvm.data.model.Music;

public class MusicInfoListViewModel extends ViewModel {

    Music mMusic;

    public MusicInfoListViewModel(Music music){
        mMusic=music;
    }

    public String getTitle(){
        return mMusic.getName();
    }

    public String getTime(){
        return mMusic.getDuration();
    }

    public String getSigner(){
        return mMusic.getArtist();
    }
}
