package com.example.musicplayermvvm.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayermvvm.R;
import com.example.musicplayermvvm.data.model.Artist;
import com.example.musicplayermvvm.databinding.ArtistInfoListBinding;
import com.example.musicplayermvvm.thread.SetMusicCover;
import com.example.musicplayermvvm.veiwmodel.ArtistInfoViewModel;

import java.util.ArrayList;
import java.util.List;

public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ArtistHolder> {

    SetMusicCover mSetMusicCover;

    Context mContext;

    List<Artist> mArtists;


    public ArtistAdapter(SetMusicCover setMusicCover, Context context) {
        mSetMusicCover = setMusicCover;
        mContext = context;
        mArtists = new ArrayList<>();

    }

    public void setArtists(List<Artist> artists) {
        mArtists = artists;
    }

    @Override
    public ArtistHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(mContext);

        ArtistInfoListBinding artistInfoListBinding= DataBindingUtil.inflate(inflater
                ,R.layout.artist_info_list,parent,false);

        return new ArtistHolder(artistInfoListBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtistAdapter.ArtistHolder holder, int position) {
        holder.bind(mArtists.get(position));
    }

    @Override
    public int getItemCount() {
        return mArtists.size();
    }

    class ArtistHolder extends RecyclerView.ViewHolder{

        ArtistInfoListBinding mListBinding;

        public ArtistHolder(ArtistInfoListBinding artistInfoListBinding) {
            super(artistInfoListBinding.getRoot());
            mListBinding=artistInfoListBinding;
            artistInfoListBinding.setInfoViewModel(new
                    ArtistInfoViewModel(null,mContext));
        }

        public void bind(Artist artist){

            mListBinding.setInfoViewModel(new ArtistInfoViewModel(artist,mContext));

            //TODO how not change this code observe for change data
//            mListBinding.getInfoViewModel().setArtist(artist);

            mListBinding.imageViewArtist.setImageBitmap(null);

            mSetMusicCover.queueImageCover(artist.getMusicList().get(0).getFilePath()
                    ,mListBinding.imageViewArtist);


        }
    }
}