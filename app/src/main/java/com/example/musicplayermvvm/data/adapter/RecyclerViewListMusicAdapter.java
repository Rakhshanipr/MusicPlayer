package com.example.musicplayermvvm.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayermvvm.R;
import com.example.musicplayermvvm.data.model.Music;
import com.example.musicplayermvvm.databinding.MusicInfoListBinding;
import com.example.musicplayermvvm.veiwmodel.MusicInfoListViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class RecyclerViewListMusicAdapter
        extends RecyclerView.Adapter<RecyclerViewListMusicAdapter.MusicHolder> {

    Context mContext;
    List<Music> mMusicList;

    public RecyclerViewListMusicAdapter(Context context) {
        mContext=context;
        mMusicList=new ArrayList<>();
    }

    public void setMusicList(List<Music> musicList) {
        mMusicList = musicList;
    }

    @Override
    public MusicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater= LayoutInflater.from(mContext);

        MusicInfoListBinding musicInfoListBinding= DataBindingUtil.inflate(
                inflater, R.layout.music_info_list,parent,false
        );

        return new MusicHolder(musicInfoListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewListMusicAdapter.MusicHolder holder, int position) {
        holder.bind(mMusicList.get(position));
    }

    @Override
    public int getItemCount() {
        return mMusicList.size();
    }

    class MusicHolder extends RecyclerView.ViewHolder{

        MusicInfoListBinding mMusicInfoListBinding;
        Music mMusic;

        public MusicHolder(MusicInfoListBinding musicInfoListBinding) {
            super(musicInfoListBinding.getRoot());
            mMusicInfoListBinding=musicInfoListBinding;
            musicInfoListBinding.setInfoViewModel(new MusicInfoListViewModel(mMusic));
        }

        void bind(Music music){
            mMusic=music;
            mMusicInfoListBinding.getInfoViewModel().setMusic(music);
        }
    }

}
