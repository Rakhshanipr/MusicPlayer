package com.example.musicplayermvvm.data.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayermvvm.R;
import com.example.musicplayermvvm.data.model.Music;
import com.example.musicplayermvvm.databinding.MusicInfoListBinding;
import com.example.musicplayermvvm.thread.SetMusicCover;
import com.example.musicplayermvvm.utilities.LRUCache;
import com.example.musicplayermvvm.veiwmodel.InfoMusicViewModel;

import java.util.ArrayList;
import java.util.List;

public class MusicAdapter
        extends RecyclerView.Adapter<MusicAdapter.MusicHolder> {

    Context mContext;

    List<Music> mMusicList;

    SetMusicCover mSetMusicCover;

    Fragment mFragment;

    public MusicAdapter(Context context, SetMusicCover musicCover
            , Fragment fragment) {
        mContext = context;
        mMusicList = new ArrayList<>();
        mSetMusicCover = musicCover;
        mFragment = fragment;
    }

    public void setMusicList(List<Music> musicList) {
        mMusicList = musicList;
    }

    @Override
    public MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        MusicInfoListBinding musicInfoListBinding = DataBindingUtil.inflate(
                inflater, R.layout.music_info_list, parent, false);
        musicInfoListBinding.textViewTitle.setSelected(true);

        return new MusicHolder(musicInfoListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicAdapter.MusicHolder holder, int position) {
        if (mMusicList.get(position) != null) {
            holder.bind(mMusicList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return mMusicList.size();
    }

    class MusicHolder extends RecyclerView.ViewHolder {

        MusicInfoListBinding mMusicInfoListBinding;
        Music mMusic;

        public MusicHolder(MusicInfoListBinding musicInfoListBinding) {
            super(musicInfoListBinding.getRoot());
            mMusicInfoListBinding = musicInfoListBinding;

            mMusicInfoListBinding.setInfoViewModel(new
                    InfoMusicViewModel((InfoMusicViewModel.ICallbackMusicInfo) mFragment.getActivity()));
        }

        void bind(Music music) {

            mMusic = music;

            mMusicInfoListBinding.getInfoViewModel().setMusic(mMusic);

                mSetMusicCover.queueImageCover(music.getFilePath()
                        , mMusicInfoListBinding.imageView);
        }
    }
}