package com.example.musicplayermvvm.veiwmodel;

import android.content.Context;
import android.content.Intent;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.fragment.app.FragmentManager;

import com.example.musicplayermvvm.data.adapter.MainViewPagerAdapter;
import com.example.musicplayermvvm.data.model.Artist;
import com.example.musicplayermvvm.ui.activity.SelectedMusicActivity;

public class InfoArtistViewModel extends BaseObservable {

    Artist mArtist;
    Context mContext;

    public InfoArtistViewModel(Artist artist, Context context) {
        mArtist = artist;
        mContext=context;
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

    public void onClickLayout(){

        MainViewPagerAdapter.sFragment_state=1;
        MainViewPagerAdapter.sMusicListSelected =mArtist.getMusicList();
        Intent intent = new Intent(mContext, SelectedMusicActivity.class);
        mContext.startActivity(intent);

    }
}