package com.example.musicplayermvvm.data.model;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class Music implements Serializable {

    private String mName;
    private String mArtist;
    private String mAlbum;
    private Long mDuration;
    private String mFilePath;
    public Music mPrev=null;
    public Music mNext=null;

    public Music() {
    }

    public static String convertMilliTotime(long milliseconds){

        String minutes
                = Long.toString(TimeUnit.MILLISECONDS.toMinutes(milliseconds));

        String seconds
                =Long.toString( (TimeUnit.MILLISECONDS.toSeconds(milliseconds)
                % 60));

        if (minutes.length()==1)
            minutes="0"+minutes;

        if (seconds.length()==1)
            seconds="0"+seconds;

        return minutes+":"+seconds;

    }

    public static int convertMilliToSecond(long milliseconds){
        return (int)TimeUnit.MILLISECONDS.toSeconds(milliseconds);
    }

    public static int convertSecondToMillis(int second){
        return second*1000;
    }

    public Music getPrev() {
        return mPrev;
    }

    public void setPrev(Music prev) {
        mPrev = prev;
    }

    public Music getNext() {
        return mNext;
    }

    public void setNext(Music next) {
        mNext = next;
    }

    public Music(String name, String artist, String album, Long duration, String filePath) {
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

    public Long getDuration() {
        return mDuration;
    }

    public String getFormatedTime(){
        return convertMilliTotime(getDuration());
    }

    public void setDuration(Long duration) {
        mDuration = duration;
    }

    public String getFilePath() {
        return mFilePath;
    }

    public void setFilePath(String filePath) {
        mFilePath = filePath;
    }
}


