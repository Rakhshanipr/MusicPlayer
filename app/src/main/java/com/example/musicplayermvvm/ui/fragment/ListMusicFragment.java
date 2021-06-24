package com.example.musicplayermvvm.ui.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musicplayermvvm.R;
import com.example.musicplayermvvm.databinding.FragmentListMusicBinding;
import com.example.musicplayermvvm.veiwmodel.ListMusicFragmentViewModel;

public class ListMusicFragment extends Fragment {

    //region defind static method and variable

    public static final String ARGS_STATE = "com.example.musicplayermvvm.ui.fragment.state_fragment";

    //endregion

    ListMusicFragmentViewModel mMusicFragmentViewModel;

    FragmentListMusicBinding mListMusicBinding;

    public ListMusicFragment() {

    }

    public static ListMusicFragment newInstance() {
        ListMusicFragment fragment = new ListMusicFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMusicFragmentViewModel=new
                ViewModelProvider(requireActivity()).get(ListMusicFragmentViewModel.class);
        mMusicFragmentViewModel.setContext(getContext());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mListMusicBinding= DataBindingUtil.inflate(inflater
                ,R.layout.fragment_list_music,container,false);

        Initial();

        return mListMusicBinding.getRoot();
    }

    private void Initial() {

        mListMusicBinding.recyclerViewList.setLayoutManager(new
                LinearLayoutManager(getContext()));

        mListMusicBinding.recyclerViewList.setAdapter(
                mMusicFragmentViewModel.createAdapterMusic(new Handler()));
    }
}