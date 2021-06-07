package com.example.musicplayermvvm.veiwmodel;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.musicplayermvvm.data.model.Artist;
import com.example.musicplayermvvm.data.model.Music;

public class ArtistInfoViewModel extends BaseObservable {

    Artist mArtist;

    public ArtistInfoViewModel(Artist artist) {
        mArtist = artist;
        notifyPropertyChanged(3);
    }

    public Artist getArtist() {
        return mArtist;
    }

    public void setArtist(Artist artist) {
        mArtist = artist;
    }

    @Bindable
    public String getName(){
        return mArtist.getName();
    }

    @Bindable
    public String getCount(){
        return Integer.toString(mArtist.getMusicList().size());
    }

}
