package com.example.musicplayermvvm.veiwmodel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.musicplayermvvm.data.adapter.AlbumAdapter;
import com.example.musicplayermvvm.data.adapter.ArtistAdapter;
import com.example.musicplayermvvm.data.adapter.MainViewPagerAdapter;
import com.example.musicplayermvvm.data.adapter.MusicAdapter;
import com.example.musicplayermvvm.data.model.Album;
import com.example.musicplayermvvm.data.model.Artist;
import com.example.musicplayermvvm.data.model.Music;
import com.example.musicplayermvvm.data.repository.MusicRepository;
import com.example.musicplayermvvm.thread.SetMusicCover;

import java.util.List;

public class ListMusicFragmentViewModel extends AndroidViewModel {

    //region defind variable
    MusicRepository mMusicRepository;

    SetMusicCover mSetMusicCover;

    MutableLiveData<List<Music>> mListMusicMutable;

    MutableLiveData<List<Music>> mListMusicLikeMutable;

    MutableLiveData<List<Artist>> mListArtistMutable;

    MutableLiveData<List<Album>> mListAlbumtMutable;

    Context mContext;

    MusicAdapter mMusicAdapter;

    //endregion
    public ListMusicFragmentViewModel(@NonNull Application application) {
        super(application);
    }

    public void setContext_MusicCover(Context context) {

        mContext = context;
        mMusicRepository = MusicRepository.getInstance(mContext);

        mSetMusicCover = new SetMusicCover();

        mListMusicMutable = mMusicRepository.getListMutableLiveDataMusic();

        mListArtistMutable = mMusicRepository.getListMutableLiveDataArtist();

        mListAlbumtMutable = mMusicRepository.getListMutableLiveDataAlbum();

        mListMusicLikeMutable=mMusicRepository.getListMutableLiveDataLikeMusic();
    }

    public MusicAdapter createAdapterMusic(Handler handler, Fragment fragment) {

        mMusicAdapter = new MusicAdapter(mContext, getSetMusicCover(handler),fragment);

            mListMusicMutable.observe((LifecycleOwner) mContext
                    , new Observer<List<Music>>() {
                        @Override
                        public void onChanged(List<Music> music) {
                            mMusicAdapter.setMusicList(music);
                            MainViewPagerAdapter.sMusicListFull=music;
                            mMusicAdapter.notifyDataSetChanged();
                        }
                    });

            mMusicRepository.setMusicList();

        return mMusicAdapter;
    }

    public MusicAdapter createLikeMusiceAdapter(Handler handler, Fragment fragment){
        MusicAdapter musicAdapter=new
                MusicAdapter(mContext,getSetMusicCover(handler),fragment);

        mListMusicLikeMutable.observe((LifecycleOwner) mContext
                , new Observer<List<Music>>() {
                    @Override
                    public void onChanged(List<Music> music) {
                        musicAdapter.setMusicList(music);
                        musicAdapter.notifyDataSetChanged();
                    }
                });

        mMusicRepository.setLikeMusic(mContext);

        return musicAdapter;
    }

    public ArtistAdapter createAdapterArtist(Handler handler, Context context) {

        ArtistAdapter mArtistAdapter = new ArtistAdapter(getSetMusicCover(handler)
                , context);

        mListArtistMutable.observe((LifecycleOwner) mContext
                , new Observer<List<Artist>>() {

                    @Override
                    public void onChanged(List<Artist> artists) {
                        mArtistAdapter.setArtists(artists);
                        mArtistAdapter.notifyDataSetChanged();
                    }
                });

        mMusicRepository.setArtistList();
        return mArtistAdapter;
    }

    public void setMusicLikeRepository(){
        mMusicRepository.setLikeMusic(mContext);
    }

    public AlbumAdapter createAdapterAlbum(Handler handler, Context context) {

        AlbumAdapter mArtistAdapter = new AlbumAdapter(getSetMusicCover(handler)
                , context);

        mListAlbumtMutable.observe((LifecycleOwner) mContext
                , new Observer<List<Album>>() {

                    @Override
                    public void onChanged(List<Album> albums) {
                        mArtistAdapter.setAlbums(albums);
                        mArtistAdapter.notifyDataSetChanged();
                    }
                });

        mMusicRepository.setAlbumList();
        return mArtistAdapter;
    }

    public SetMusicCover getSetMusicCover(Handler handler) {

        mSetMusicCover.start();

        mSetMusicCover.setHandlerSetPhoto(handler);

        mSetMusicCover.getLooper();
        return mSetMusicCover;
    }
}