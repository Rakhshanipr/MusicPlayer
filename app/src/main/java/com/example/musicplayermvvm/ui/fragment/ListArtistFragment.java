package com.example.musicplayermvvm.ui.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musicplayermvvm.R;
import com.example.musicplayermvvm.databinding.ArtistInfoListBinding;

public class ListArtistFragment extends Fragment {

    //region defind static method and variable
    public static ListArtistFragment newInstance() {
        ListArtistFragment fragment = new ListArtistFragment();
        return fragment;
    }
    //endregion

    //region defind variable
    ArtistInfoListBinding mListBinding;

    //endregion

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mListBinding= DataBindingUtil.inflate(inflater,R.layout.fragment_list_artist
                ,container,false);

        return mListBinding.getRoot();
    }
}