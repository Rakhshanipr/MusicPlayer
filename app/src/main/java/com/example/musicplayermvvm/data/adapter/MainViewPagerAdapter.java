package com.example.musicplayermvvm.data.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.musicplayermvvm.data.model.Music;
import com.example.musicplayermvvm.ui.fragment.ListAlbumFragment;
import com.example.musicplayermvvm.ui.fragment.ListArtistFragment;
import com.example.musicplayermvvm.ui.fragment.ListMusicFragment;
import com.example.musicplayermvvm.ui.fragment.ListMusicLikeFragment;
import com.example.musicplayermvvm.utilities.QueryPreferences;

import java.util.List;

public class MainViewPagerAdapter extends FragmentStateAdapter {

    public static int sFragment_state=0;
    public static List<Music> sMusicListSelected =null;
    public static List<Music> sMusicListFull =null;

    Context mContext;
    public MainViewPagerAdapter(@NonNull FragmentActivity fragmentActivity,Context context) {
        super(fragmentActivity);
        mContext=context;
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
        } else if (position==3){
            return ListMusicLikeFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}