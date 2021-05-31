package com.example.musicplayermvvm.data.model;

public class Music {
    private String mName;
    private String mArtist;
    private String mAlbum;
    private String mDuration;
    private String mFilePath;

    public Music() {
    }

    public Music(String name, String artist, String album, String duration, String filePath) {
        mName = name;
        mArtist = artist;
        mAlbum = album;
        mDuration = duration;
        mFilePath = filePath;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getArtist() {
        return mArtist;
    }

    public void setArtist(String artist) {
        mArtist = artist;
    }

    public String getAlbum() {
        return mAlbum;
    }

    public void setAlbum(String album) {
        mAlbum = album;
    }

    public String getDuration() {
        return mDuration;
    }

    public void setDuration(String duration) {
        mDuration = duration;
    }

    public String getFilePath() {
        return mFilePath;
    }

    public void setFilePath(String filePath) {
        mFilePath = filePath;
    }
}


