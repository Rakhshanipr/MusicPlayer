package com.example.musicplayermvvm.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.musicplayermvvm.R;
import com.example.musicplayermvvm.data.adapter.MainViewPagerAdapter;
import com.example.musicplayermvvm.databinding.ActivityMainBinding;
import com.example.musicplayermvvm.databinding.ActivitySelectedMusicBinding;
import com.example.musicplayermvvm.ui.fragment.ListMusicFragment;

public class SelectedMusicActivity extends AppCompatActivity {


    public static final String TAG_SELCTED_MUSIC_FRAGMENT = "com.example.musicplayermvvm.ui.activity.TAG_SELCTED_MUSIC_FRAGMENT";
    ActivitySelectedMusicBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding= ActivitySelectedMusicBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container_selected_music, ListMusicFragment.newInstance(), TAG_SELCTED_MUSIC_FRAGMENT)
                .commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainViewPagerAdapter.sFragment_state=0;
    }
}