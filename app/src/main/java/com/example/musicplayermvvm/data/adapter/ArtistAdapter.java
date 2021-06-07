package com.example.musicplayermvvm.data.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicplayermvvm.data.model.Artist;
import com.example.musicplayermvvm.databinding.ArtistInfoListBinding;
import com.example.musicplayermvvm.veiwmodel.ArtistInfoViewModel;

public class ArtistAdapter {



    class ArtistHolder extends RecyclerView.ViewHolder{

        ArtistInfoListBinding mListBinding;

        public ArtistHolder(ArtistInfoListBinding artistInfoListBinding) {
            super(artistInfoListBinding.getRoot());
            mListBinding=artistInfoListBinding;
            artistInfoListBinding.setInfoViewModel(new ArtistInfoViewModel(null));
        }

        public void bind(Artist artist){

            mListBinding.getInfoViewModel().setArtist(artist);

            mListBinding.imageViewArtist.setImageBitmap(null);



        }

    }
}
