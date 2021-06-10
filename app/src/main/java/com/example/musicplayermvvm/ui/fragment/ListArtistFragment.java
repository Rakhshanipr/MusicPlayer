package com.example.musicplayermvvm.ui.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musicplayermvvm.R;
import com.example.musicplayermvvm.databinding.ArtistInfoListBinding;
import com.example.musicplayermvvm.databinding.FragmentListArtistBinding;
import com.example.musicplayermvvm.veiwmodel.ListMusicFragmentViewModel;

public class ListArtistFragment extends Fragment {

    //region defind static method and variable
    public static ListArtistFragment newInstance() {
        ListArtistFragment fragment = new ListArtistFragment();
        return fragment;
    }
    //endregion

    //region defind variable
    FragmentListArtistBinding mListBinding;

    ListMusicFragmentViewModel mMusicFragmentViewModel;
    //endregion

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMusicFragmentViewModel=new ListMusicFragmentViewModel();
        mMusicFragmentViewModel.setContext(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mListBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_list_artist
                ,container,false);

        initial();


        return mListBinding.getRoot();
    }

    private void initial() {


        mListBinding.recyclerViewListArtist.setLayoutManager(new GridLayoutManager(getContext(),2));

        mListBinding.recyclerViewListArtist.setAdapter(
                mMusicFragmentViewModel.createAdapterArtist(new Handler(),getContext()));

    }
}