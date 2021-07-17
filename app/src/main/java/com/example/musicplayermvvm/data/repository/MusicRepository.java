package com.example.musicplayermvvm.data.repository;

import android.content.Context;
import android.database.Cursor;
import android.provider.MediaStore;

import androidx.lifecycle.MutableLiveData;

import com.example.musicplayermvvm.data.adapter.MainViewPagerAdapter;
import com.example.musicplayermvvm.data.model.Album;
import com.example.musicplayermvvm.data.model.Artist;
import com.example.musicplayermvvm.data.model.Music;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MusicRepository {

    //region defind static method and variable

    private static MusicRepository sMusicRepository;

    public static MusicRepository getInstance(Context context) {
        if (sMusicRepository == null)
            sMusicRepository = new MusicRepository(context);
        return sMusicRepository;
    }

    //endregion

    //region defind variable
    Context mContext;
    private List<Music> mMusicList;

    private MutableLiveData<List<Music>> mListMutableLiveDataMusic = new MutableLiveData<>();
    private MutableLiveData<List<Artist>> mListMutableLiveDataArtist = new MutableLiveData<>();
    private MutableLiveData<List<Album>> mListMutableLiveDataAlbum = new MutableLiveData<>();
    //endregion

    private MusicRepository(Context context) {
        mContext = context;
        mMusicList = new ArrayList<>();
    }

    public MutableLiveData<List<Music>> getListMutableLiveDataMusic() {
        return mListMutableLiveDataMusic;
    }

    public MutableLiveData<List<Artist>> getListMutableLiveDataArtist() {
        return mListMutableLiveDataArtist;
    }

    public MutableLiveData<List<Album>> getListMutableLiveDataAlbum() {
        return mListMutableLiveDataAlbum;
    }

    public void setMusicList() {

        if (MainViewPagerAdapter.sFragment_state == 0) {
            String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

            String[] projection = {
                    MediaStore.Audio.Media.TITLE,
                    MediaStore.Audio.Media.ARTIST,
                    MediaStore.Audio.Media.ALBUM,
                    MediaStore.Audio.Media.DURATION,
                    MediaStore.Audio.Media.DATA

            };

            Cursor songsCursor = mContext.getApplicationContext().getContentResolver().query(
                    MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                    projection,
                    selection,
                    null,
                    null);

            mMusicList=new ArrayList<>();
            songsCursor.moveToFirst();
            int h = songsCursor.getCount();

            Music music1=new Music();

            while (!songsCursor.isAfterLast()) {

                //region get music info from cursor
                String title = songsCursor.getString(songsCursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                String artist = songsCursor.getString(songsCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                String album = songsCursor.getString(songsCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                String duration = songsCursor.getString(songsCursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                String data = songsCursor.getString(songsCursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                //endregion

                music1 = new Music(title, artist, album, duration, data);

                mMusicList.add(music1);
                songsCursor.moveToNext();
            }

            for (int i=1;i<mMusicList.size()-1;i++){
                mMusicList.get(i).setPrev(mMusicList.get(i-1));
                mMusicList.get(i).setNext(mMusicList.get(i+1));
            }

            mMusicList.get(0).setPrev(mMusicList.get(mMusicList.size()-1));

            mMusicList.get(mMusicList.size()-1).setNext(mMusicList.get(0));

            if (mMusicList.size()==2){
                mMusicList.get(0).setNext(mMusicList.get(mMusicList.size()-1));

                mMusicList.get(mMusicList.size()-1).setPrev(mMusicList.get(0));
            }
            mListMutableLiveDataMusic.setValue(mMusicList);
        } else {
            mListMutableLiveDataMusic.setValue(MainViewPagerAdapter.sMusicList);
        }
    }

    public List<Music> getMusicList() {
        return mMusicList;
    }

    public void setArtistList() {

        if (mMusicList == null || mMusicList.size() == 0) {
            return;
        }

        List<Artist> artistList = new ArrayList<>();
        HashMap<String, Artist> hashMap = new HashMap<>();

        for (Music music : mMusicList) {

            if (hashMap.containsKey(music.getArtist())) {
                hashMap.get(music.getArtist()).addMusic(music);

            } else {
                Artist artist = new Artist(music.getArtist());
                artist.addMusic(music);
                artistList.add(artist);
                hashMap.put(music.getArtist(), artist);
            }
            for (Artist artist:artistList) {
                for (int i=1;i<artist.getMusicList().size()-1;i++){
                    artist.getMusicList().get(i).setPrev( artist.getMusicList().get(i-1));
                    artist.getMusicList().get(i).setNext( artist.getMusicList().get(i+1));
                }

                if (artist.getMusicList().size()==2){
                    artist.getMusicList().get(0).setNext(artist.getMusicList().get(artist.getMusicList().size()-1));

                    artist.getMusicList().get(artist.getMusicList().size()-1).setPrev(artist.getMusicList().get(0));
                }
                artist.getMusicList().get(0).setPrev(artist.getMusicList().get(artist.getMusicList().size()-1));

                artist.getMusicList().get(artist.getMusicList().size()-1).setNext(artist.getMusicList().get(0));
            }
        }
        mListMutableLiveDataArtist.setValue(artistList);
    }

    public void setAlbumList() {

        if (mMusicList == null || mMusicList.size() == 0) {
            return;
        }
        List<Album> albumList = new ArrayList<>();
        HashMap<String, Album> hashMap = new HashMap<>();

        for (Music music : mMusicList) {

            if (hashMap.containsKey(music.getAlbum())) {
                hashMap.get(music.getAlbum()).addMusic(music);

            } else {
                Album album = new Album(music.getArtist());
                album.addMusic(music);
                albumList.add(album);
                hashMap.put(music.getArtist(), album);
            }
        }
        mListMutableLiveDataAlbum.setValue(albumList);
    }
}