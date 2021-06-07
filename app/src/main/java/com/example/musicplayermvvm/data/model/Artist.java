package com.example.musicplayermvvm.data.model;

import java.util.List;

public class Artist {

    String mName;
    List<Music> mMusicList;

    public Artist(String name) {
        mName = name;
    }

    public Artist(String name, List<Music> musicList) {
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