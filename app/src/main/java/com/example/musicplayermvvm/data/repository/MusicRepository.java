package com.example.musicplayermvvm.data.repository;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import androidx.lifecycle.MutableLiveData;

import com.example.musicplayermvvm.data.model.Music;

import java.util.ArrayList;
import java.util.List;

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

}