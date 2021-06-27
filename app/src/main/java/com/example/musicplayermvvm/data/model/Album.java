package com.example.musicplayermvvm.data.model;

import java.util.ArrayList;
import java.util.List;

public class Album {

    String mName;
    List<Music> mMusicList;

    public Album(String name) {
        mName = name;
        mMusicList=new ArrayList<>();
    }

    public Album(String name, List<Music> musicList) {
        mName = name;
        mMusicList = musicList;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public List<Music> getMusicList() {
        return mMusicList;
    }

    public void setMusicList(List<Music> musicList) {
        mMusicList = musicList;
    }

    public void addMusic(Music music){
        mMusicList.add(music);
    }



}
