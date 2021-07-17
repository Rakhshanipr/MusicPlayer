package com.example.musicplayermvvm.veiwmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.musicplayermvvm.R;
import com.example.musicplayermvvm.data.model.Music;
import com.example.musicplayermvvm.ui.activity.PlayMusicActivity;

public class InfoMusicViewModel {

    Music mMusic;

    MutableLiveData<Music> mMusicLiveData=new MutableLiveData<>();

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

    public void onClickMusic(Context context) {

        context.startActivity(
                PlayMusicActivity.newIntent(context,getMusic()));

    }
}