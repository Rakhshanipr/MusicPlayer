package com.example.musicplayermvvm.data.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.musicplayermvvm.ui.fragment.ListMusicFragment;

public class MainViewPagerAdapter extends FragmentStateAdapter {


    public MainViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @Override
    public Fragment createFragment(int position) {
        if(position==0){
            return ListMusicFragment.newInstance();
        }else if (position==1){

        }else if (position==2){

        }
        return null;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}