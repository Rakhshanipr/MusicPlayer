package com.example.musicplayermvvm.data.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.musicplayermvvm.data.model.Music;
import com.example.musicplayermvvm.ui.fragment.ListAlbumFragment;
import com.example.musicplayermvvm.ui.fragment.ListArtistFragment;
import com.example.musicplayermvvm.ui.fragment.ListMusicFragment;

import java.util.List;

public class MainViewPagerAdapter extends FragmentStateAdapter {

    public static int sFragment_state=0;
    public static List<Music> sMusicList=null;

    public MainViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @Override
    public Fragment createFragment(int position) {
        if(position==0){
            sFragment_state=0;
            return ListMusicFragment.newInstance();
        }else if (position==1){
            sFragment_state=1;
            return ListArtistFragment.newInstance();
        }else if (position==2){
            return ListAlbumFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}