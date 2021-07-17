package com.example.musicplayermvvm.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.musicplayermvvm.R;
import com.example.musicplayermvvm.data.model.Music;
import com.example.musicplayermvvm.databinding.ActivityPlayMusicBinding;
import com.example.musicplayermvvm.ui.fragment.PlayMusicFragment;
import com.example.musicplayermvvm.veiwmodel.PlayMusicViewModel;

public class PlayMusicActivity extends AppCompatActivity {


    public static final String EXTRA_MUSIC = "com.example.musicplayermvvm.ui.activity.musicPlay";

    public static Intent newIntent(Context context, Music music){
        Intent intent = new Intent(context , PlayMusicActivity.class);
        intent.putExtra(EXTRA_MUSIC,music);
        return intent;
    }

    ActivityPlayMusicBinding mActivityPlayMusicBinding;
    PlayMusicViewModel mPlayMusicViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityPlayMusicBinding = ActivityPlayMusicBinding.inflate(getLayoutInflater());

        Music music=(Music) (getIntent().getSerializableExtra(EXTRA_MUSIC));

        setContentView(mActivityPlayMusicBinding.getRoot());
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container_play_music);
        if (fragment == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_play_music, PlayMusicFragment.newInstance(music))
                    .commit();
        }else{

        }



    }


}