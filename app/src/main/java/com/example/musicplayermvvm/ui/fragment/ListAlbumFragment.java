package com.example.musicplayermvvm.ui.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musicplayermvvm.R;
import com.example.musicplayermvvm.databinding.FragmentListAlbumBinding;
import com.example.musicplayermvvm.veiwmodel.ListMusicFragmentViewModel;

public class ListAlbumFragment extends Fragment {


    FragmentListAlbumBinding mListBinding;

    ListMusicFragmentViewModel mMusicFragmentViewModel;

    public static ListAlbumFragment newInstance() {
        ListAlbumFragment fragment = new ListAlbumFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMusicFragmentViewModel=new ListMusicFragmentViewModel();
        mMusicFragmentViewModel.setContext_MusicCover(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mListBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_list_album
                ,container,false);

        initial();



        return mListBinding.getRoot();
    }


    private void initial() {

        mListBinding.recyclerViewListAlbum.setLayoutManager(new GridLayoutManager(getContext(),1));

        mListBinding.recyclerViewListAlbum.setAdapter(
                mMusicFragmentViewModel.createAdapterAlbum(new Handler(),
                        getContext()));
    }
}