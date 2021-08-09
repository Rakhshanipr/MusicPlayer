package com.example.musicplayermvvm.ui.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musicplayermvvm.R;
import com.example.musicplayermvvm.data.adapter.MusicAdapter;
import com.example.musicplayermvvm.data.model.Music;
import com.example.musicplayermvvm.data.repository.MusicRepository;
import com.example.musicplayermvvm.databinding.FragmentListMusicLikeBinding;
import com.example.musicplayermvvm.veiwmodel.ListMusicFragmentViewModel;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListMusicLikeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListMusicLikeFragment extends Fragment {

    FragmentListMusicLikeBinding mBinding;

    ListMusicFragmentViewModel mMusicFragmentViewModel;

    public static ListMusicLikeFragment newInstance() {
        ListMusicLikeFragment fragment = new ListMusicLikeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding= DataBindingUtil.inflate(inflater
                ,R.layout.fragment_list_music_like,container,false);

        mMusicFragmentViewModel = new
                ViewModelProvider(requireActivity()).get(ListMusicFragmentViewModel.class);
        mMusicFragmentViewModel.setContext_MusicCover(getContext());

        initial();


        return mBinding.getRoot();
    }

    private void initial() {

        mBinding.recyclerViewListMusicLike.setLayoutManager(new
                LinearLayoutManager(getContext()));

        mBinding.recyclerViewListMusicLike.setAdapter(
                mMusicFragmentViewModel.createLikeMusiceAdapter(new Handler(), this));

    }

}