package com.example.musicplayermvvm.data.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayermvvm.R;
import com.example.musicplayermvvm.data.model.Music;
import com.example.musicplayermvvm.databinding.MusicInfoListBinding;
import com.example.musicplayermvvm.thread.SetMusicCover;
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
        mFragment=fragment;
    }

    public void setMusicList(List<Music> musicList) {
        mMusicList = musicList;
    }

    @Override
    public MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        MusicInfoListBinding musicInfoListBinding = DataBindingUtil.inflate(
                inflater, R.layout.music_info_list, parent, false);

        return new MusicHolder(musicInfoListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MusicAdapter.MusicHolder holder, int position) {
        holder.bind(mMusicList.get(position));
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
            mMusicInfoListBinding.setInfoViewModel(
                    null);
        }

        void bind(Music music) {

            mMusic = music;

            InfoMusicViewModel musicViewModel=new InfoMusicViewModel();
            musicViewModel.setMusic(mMusic);
            mMusicInfoListBinding.setContext(mContext);
            mMusicInfoListBinding.setInfoViewModel(musicViewModel);


            //TODO how not change this code observe for change data
//            mMusicInfoListBinding.getInfoViewModel().setMusic(music);

            mMusicInfoListBinding.imageView.setImageBitmap(null);
            mSetMusicCover.queueImageCover(music.getFilePath()
                 ,mMusicInfoListBinding.imageView);
        }
    }
}