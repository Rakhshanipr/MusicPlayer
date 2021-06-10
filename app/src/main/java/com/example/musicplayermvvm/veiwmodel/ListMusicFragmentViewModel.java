package com.example.musicplayermvvm.veiwmodel;

import android.content.Context;
import android.os.Handler;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.musicplayermvvm.data.adapter.ArtistAdapter;
import com.example.musicplayermvvm.data.adapter.MusicAdapter;
import com.example.musicplayermvvm.data.model.Artist;
import com.example.musicplayermvvm.data.model.Music;
import com.example.musicplayermvvm.data.repository.MusicRepository;
import com.example.musicplayermvvm.thread.SetMusicCover;

import java.util.List;

public class ListMusicFragmentViewModel extends ViewModel {

    MusicRepository mMusicRepository;

    SetMusicCover mSetMusicCover;

    MutableLiveData<List<Music>> mListMusicMutable;

    MutableLiveData<List<Artist>> mListArtistMutable;

    Context mContext;

    MusicAdapter mMusicAdapter;

    public ListMusicFragmentViewModel() {
    }


    public void setContext(Context context) {

        mContext = context;
        mMusicRepository = MusicRepository.getInstance(mContext);

        mSetMusicCover = new SetMusicCover();

        mListMusicMutable = mMusicRepository.getListMutableLiveDataMusic();

        mListArtistMutable = mMusicRepository.getListMutableLiveDataArtist();

    }

    public MusicAdapter createAdapterMusic(Handler handler) {

        mMusicAdapter = new MusicAdapter(mContext, getSetMusicCover(handler));

        mListMusicMutable.observe((LifecycleOwner) mContext
                , new Observer<List<Music>>() {

                    @Override
                    public void onChanged(List<Music> music) {
                        mMusicAdapter.setMusicList(music);
                        mMusicAdapter.notifyDataSetChanged();
                    }
                });

        mMusicRepository.setMusicList();

        return mMusicAdapter;
    }

    public ArtistAdapter createAdapterArtist(Handler handler,Context context) {

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

    public SetMusicCover getSetMusicCover(Handler handler) {

        mSetMusicCover.start();

        mSetMusicCover.setHandlerSetPhoto(handler);

        mSetMusicCover.getLooper();
        return mSetMusicCover;
    }

    public void fetchMusicList() {

    }
}