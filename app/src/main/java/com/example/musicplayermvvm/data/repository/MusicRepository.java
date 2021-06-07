package com.example.musicplayermvvm.data.repository;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import androidx.lifecycle.MutableLiveData;

import com.example.musicplayermvvm.data.model.Artist;
import com.example.musicplayermvvm.data.model.Music;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListResourceBundle;

public class MusicRepository {

    //region defind static method and variable

    private static MusicRepository sMusicRepository;

    public static MusicRepository getInstance(Context context){
        if (sMusicRepository==null)
            sMusicRepository=new MusicRepository(context);
        return sMusicRepository;
    }

    //endregion

    Context mContext;
    private List<Music> mMusicList;

    private MutableLiveData<List<Music>> mListMutableLiveData=new MutableLiveData<>();

    private MusicRepository(Context context){
        mContext=context;
        mMusicList =new ArrayList<>();
    }

    public MutableLiveData<List<Music>> getListMutableLiveData() {
        return mListMutableLiveData;
    }

    public void setMusicList(){

        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        String[] projection={
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.DATA

        };


        Cursor songsCursor=mContext.getApplicationContext().getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                null);

        songsCursor.moveToFirst();
        int h=songsCursor.getCount();
        while (!songsCursor.isAfterLast()){

            //region get music info from cursor
            String title=songsCursor.getString(songsCursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String artist=songsCursor.getString(songsCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            String album=songsCursor.getString(songsCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            String duration=songsCursor.getString(songsCursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            String data=songsCursor.getString(songsCursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            //endregion

            Music music=new Music(title,artist,album,duration,data);
            mMusicList.add(music);
            songsCursor.moveToNext();
        }
        mListMutableLiveData.setValue(mMusicList);
    }


    public List<Artist> getArtists(){

        if (mMusicList==null || mMusicList.size()==0){
            return null;
        }

        HashMap<String, Artist> hashMap=new HashMap<>();

        for (Music music:mMusicList) {

            if (hashMap.containsKey(music.getArtist())){
                hashMap.get(music.getArtist()).addMusic(music);

            }else{
                Artist artist=new Artist(music.getArtist());
                artist.addMusic(music);
                hashMap.put(music.getArtist(),artist);
            }

        }

        return getArtists();
    }
}