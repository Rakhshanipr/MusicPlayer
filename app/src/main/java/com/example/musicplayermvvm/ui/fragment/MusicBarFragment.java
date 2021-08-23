package com.example.musicplayermvvm.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.musicplayermvvm.R;
import com.example.musicplayermvvm.data.model.Music;
import com.example.musicplayermvvm.databinding.FragmentMusicBarBinding;
import com.example.musicplayermvvm.services.MusicService;
import com.example.musicplayermvvm.veiwmodel.PlayMusicViewModel;

public class MusicBarFragment extends Fragment {

    public static final String ARGS_KEY_MUSIC = "com.example.musicplayermvvm.ui.fragment.MusicBarFragment";

    public static MusicBarFragment newInstance(Music music) {
        MusicBarFragment fragment = new MusicBarFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_KEY_MUSIC, music);
        fragment.setArguments(args);
        return fragment;
    }

    // region defind variable
    Handler mHandler = new Handler();

    Runnable updateCurrentMusic = new Runnable() {

        @Override
        public void run() {
            mBinding.textViewBarTime.setText(mMusicViewModel.getCurrentPosition());
            mBinding.seekBar.setProgress(
                    Music.convertMilliToSecond(mMusicViewModel.getCurrentMillis()), true);
            mHandler.postDelayed(this, 1000);
        }
    };

    FragmentMusicBarBinding mBinding;

    PlayMusicViewModel mMusicViewModel;

    Music mMusic;
    //endregion

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_music_bar, container, false);
        initial();
        mBinding.setMusicPlayViewModel(mMusicViewModel);

        setListners();

        mHandler.postDelayed(updateCurrentMusic, 1000);

        return mBinding.getRoot();
    }

    private void initial() {

        mMusicViewModel = new ViewModelProvider(requireActivity()).get(PlayMusicViewModel.class);
        mMusic = (Music) getArguments().getSerializable(ARGS_KEY_MUSIC);


        mMusicViewModel.setReactionMusicPlayer(new PlayMusicViewModel.IReactionMusicPlayer() {
            @Override
            public void playPauseClicked() {
                if (mMusicViewModel.isPlaying()) {
                    mBinding.imageButtonBarPlay.setImageDrawable(
                            AppCompatResources.getDrawable(getContext(), R.mipmap.pausemusic));
                } else {
                    mBinding.imageButtonBarPlay.setImageDrawable(
                            AppCompatResources.getDrawable(getContext(), R.drawable.play_music));
                }
            }

            @Override
            public void setInfo(Music music) {
                mBinding.textViewBarTitleName.setText(music.getName());
                mBinding.textViewBarTime.setText(music.getFormatedTime());
                mBinding.textViewBarArtist.setText(music.getArtist());
                mBinding.textViewBarTime.setText(mMusicViewModel.getCurrentPosition());
                if (mMusicViewModel.isPlaying()) {
                    mBinding.imageButtonBarPlay.setImageDrawable(
                            AppCompatResources.getDrawable(getContext(), R.mipmap.pausemusic));
                } else {
                    mBinding.imageButtonBarPlay.setImageDrawable(
                            AppCompatResources.getDrawable(getContext(), R.drawable.play_music));
                }
                mBinding.seekBar.setProgress(
                        Music.convertMilliToSecond(mMusicViewModel.getCurrentMillis()), true);

                //region setCover
                mMusic = music;
                MediaMetadataRetriever mMediaMetadataRetriever = new MediaMetadataRetriever();

                mMediaMetadataRetriever.setDataSource(music.getFilePath());

                final byte[] mPic = mMediaMetadataRetriever.getEmbeddedPicture();
                if (mPic != null) {
                    BitmapFactory.Options bitmapFactory = new BitmapFactory.Options();
                    bitmapFactory.outWidth = mBinding.imageView2.getWidth();
                    bitmapFactory.outHeight = mBinding.imageView2.getHeight();
                    Bitmap songImage = BitmapFactory.decodeByteArray(mPic, 0, mPic.length, bitmapFactory);
                    mBinding.imageView2.setImageBitmap(songImage);

                } else {
                    mBinding.imageView2.setImageDrawable(
                            getResources().getDrawable(R.drawable.ic_launcher_foreground));
                }

                //endregion

            }

            @Override
            public void randome(boolean isActive) {
                if (isActive) {
                    //Tobe change view of randome image button
                } else {

                }
            }

            @Override
            public void repeat(boolean isActive) {
                if (isActive) {
                    //Tobe change view of randome image button
                } else {

                }
            }

            @Override
            public void like(boolean isActive) {
            }

            @Override
            public void setSeekBar() {

            }

            @Override
            public void shareMusic(Intent intent) {

            }

        });

        try {
            mMusicViewModel.setMusic(mMusic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateInfo(Music music) {
        mMusic = music;
        try {
            mMusicViewModel.setMusic(music);
        } catch (Exception e) {
           Log.e("TAG_NOT",e.getMessage());
        }
    }

    private void setListners() {
        mBinding.textViewBarTitleName.setSelected(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        if (mBinding != null && MusicService.sMusic != null) {
            mBinding.textViewBarTitleName.setText(MusicService.sMusic.getName());
            mBinding.textViewBarTime.setText(MusicService.sMusic.getFormatedTime());
            mBinding.textViewBarArtist.setText(MusicService.sMusic.getArtist());

            //region setCover
            MediaMetadataRetriever mMediaMetadataRetriever = new MediaMetadataRetriever();

            mMediaMetadataRetriever.setDataSource(MusicService.sMusic.getFilePath());

            final byte[] mPic = mMediaMetadataRetriever.getEmbeddedPicture();
            if (mPic != null) {
                BitmapFactory.Options bitmapFactory = new BitmapFactory.Options();
                bitmapFactory.outWidth = mBinding.imageView2.getWidth();
                bitmapFactory.outHeight = mBinding.imageView2.getHeight();
                Bitmap songImage = BitmapFactory.decodeByteArray(mPic, 0, mPic.length, bitmapFactory);
                mBinding.imageView2.setImageBitmap(songImage);

            } else {
                mBinding.imageView2.setImageDrawable(
                        getResources().getDrawable(R.drawable.ic_launcher_foreground));
            }

            //endregion
        }
    }
}