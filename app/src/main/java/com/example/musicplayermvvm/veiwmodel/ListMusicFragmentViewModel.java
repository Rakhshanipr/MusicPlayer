package com.example.musicplayermvvm.veiwmodel;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.example.musicplayermvvm.data.adapter.RecyclerViewListMusicAdapter;
import com.example.musicplayermvvm.data.model.Music;
import com.example.musicplayermvvm.data.repository.MusicRepository;
import com.example.musicplayermvvm.ui.fragment.ListMusicFragment;

import java.util.List;

public class ListMusicFragmentViewModel extends ViewModel {

    MusicRepository mMusicRepository;

    MutableLiveData<List<Music>> mListMutableLiveData;

    Context mContext;

    RecyclerViewListMusicAdapter mMusicAdapter;

    public ListMusicFragmentViewModel() {
    }

    public void setContext(Context context) {
        mContext = context;
        mMusicRepository=MusicRepository.getInstance(mContext);

        mListMutableLiveData=mMusicRepository.getListMutableLiveData();
    }

    public RecyclerViewListMusicAdapter createAdapterRecyclerView(){

        mMusicAdapter=new RecyclerViewListMusicAdapter(mContext);

        mListMutableLiveData.observe((LifecycleOwner) mContext
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

    public void fetchMusicList(){

    }
}