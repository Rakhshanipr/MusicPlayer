package com.example.musicplayermvvm.ui.fragment;

import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.musicplayermvvm.R;
import com.example.musicplayermvvm.databinding.FragmentListMusicBinding;

public class ListMusicFragment extends Fragment {

    //region defind static method and variable
    public static final String ARGS_STATE = "com.example.musicplayermvvm.ui.fragment.state_fragment";

    //endregion

    FragmentListMusicBinding mListMusicBinding;

    public ListMusicFragment() {

    }

    public static ListMusicFragment newInstance(int state) {
        ListMusicFragment fragment = new ListMusicFragment();
        Bundle args = new Bundle();
        args.putInt(ARGS_STATE,state);
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
        mListMusicBinding= DataBindingUtil.inflate(inflater
                ,R.layout.fragment_list_music,container,false);

        Initial();

        return mListMusicBinding.getRoot();
    }

    private void Initial() {

        mListMusicBinding.recyclerViewList.setLayoutManager(new LinearLayoutManager(getContext()));
//        mListMusicBinding.recyclerViewList.setAdapter();
    }
}