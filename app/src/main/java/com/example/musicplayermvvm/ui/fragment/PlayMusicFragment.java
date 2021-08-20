package com.example.musicplayermvvm.ui.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.musicplayermvvm.R;
import com.example.musicplayermvvm.data.model.Music;
import com.example.musicplayermvvm.databinding.FragmentPlayMusicBinding;
import com.example.musicplayermvvm.veiwmodel.PlayMusicViewModel;

public class PlayMusicFragment extends Fragment {

    //region defind static method and variable
    public static final String ARGS_KEY_MUSIC = "com.example.musicplayermvvm.ui.fragment.keyMusic";

    public static PlayMusicFragment newInstance(Music music) {
        PlayMusicFragment fragment = new PlayMusicFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARGS_KEY_MUSIC, music);
        fragment.setArguments(args);
        return fragment;
    }
    //endregion

    //region defind variable
    Handler mHandler = new Handler();
    FragmentPlayMusicBinding mPlayMusicBinding;
    Music mMusic;
    PlayMusicViewModel mMusicViewModel;
    Runnable updateCurrentMusic = new Runnable() {

        @Override
        public void run() {
            mPlayMusicBinding.textViewPlayedTime.setText(mMusicViewModel.getCurrentPosition());
            mPlayMusicBinding.seekBarDuration.setProgress(
                    Music.convertMilliToSecond(mMusicViewModel.getCurrentMillis()), true);
            mHandler.postDelayed(this, 1000);
        }
    };
    //endregion

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMusicViewModel = new ViewModelProvider(requireActivity()).get(PlayMusicViewModel.class);

        initial();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mPlayMusicBinding = DataBindingUtil.inflate(inflater
                , R.layout.fragment_play_music
                , container, false);

        mPlayMusicBinding.setMusicPlayViewModel(mMusicViewModel);

       setListners();

        mHandler.postDelayed(updateCurrentMusic, 1000);

        return mPlayMusicBinding.getRoot();
    }

    private void initial() {

        mMusic = (Music) getArguments().getSerializable(ARGS_KEY_MUSIC);


        mMusicViewModel.setReactionMusicPlayer(new PlayMusicViewModel.IReactionMusicPlayer() {
            @Override
            public void playPauseClicked() {
                if (mMusicViewModel.isPlaying()) {
                    mPlayMusicBinding.imageButtonPlayPuase.setImageDrawable(
                            AppCompatResources.getDrawable(getContext(), R.mipmap.pausemusic));
                } else {
                    mPlayMusicBinding.imageButtonPlayPuase.setImageDrawable(
                            AppCompatResources.getDrawable(getContext(), R.mipmap.playmusic));
                }
            }

            @Override
            public void setInfo(Music music) {
                mPlayMusicBinding.textViewTitleMusic.setText(music.getName());
                mPlayMusicBinding.textViewDuration.setText(music.getFormatedTime());
                mPlayMusicBinding.textViewPlayedTime.setText(mMusicViewModel.getCurrentPosition());
                mPlayMusicBinding.seekBarDuration.setProgress(
                        Music.convertMilliToSecond(mMusicViewModel.getCurrentMillis()), true);
                if (mMusicViewModel.isPlaying()) {
                    mPlayMusicBinding.imageButtonPlayPuase.setImageDrawable(
                            AppCompatResources.getDrawable(getContext(), R.mipmap.pausemusic));
                } else {
                    mPlayMusicBinding.imageButtonPlayPuase.setImageDrawable(
                            AppCompatResources.getDrawable(getContext(), R.mipmap.playmusic));
                }

                //region setCover
                mMusic=music;
                MediaMetadataRetriever mMediaMetadataRetriever = new MediaMetadataRetriever();

                mMediaMetadataRetriever.setDataSource(music.getFilePath());

                final byte[] mPic = mMediaMetadataRetriever.getEmbeddedPicture();
                if (mPic != null) {
                    BitmapFactory.Options bitmapFactory = new BitmapFactory.Options();
                    bitmapFactory.outWidth = 60;
                    bitmapFactory.outHeight = 60;
                    Bitmap songImage = BitmapFactory.decodeByteArray(mPic, 0, mPic.length, bitmapFactory);
                    mPlayMusicBinding.imageViewMainImage.setImageBitmap(songImage);

                }else{
                    mPlayMusicBinding.imageViewMainImage.setImageDrawable(
                            getResources().getDrawable(R.drawable.ic_launcher_foreground));
                }

                    //endregion
                like(mMusicViewModel.isLiked());
            }

            @Override
            public void randome(boolean isActive) {
                if (isActive){
                    //Tobe change view of randome image button
                }
                else{

                }
            }

            @Override
            public void repeat(boolean isActive){
                if (isActive){
                    //Tobe change view of randome image button
                }
                else{

                }
            }

            @Override
            public void like(boolean isActive) {
                if (isActive){
                    mPlayMusicBinding.imageButtonLike.setImageBitmap(
                            BitmapFactory.decodeResource(getResources(), R.mipmap.like));
                }
                else{
                    mPlayMusicBinding.imageButtonLike.setImageBitmap(
                            BitmapFactory.decodeResource(getResources(), R.mipmap.unlike));
                }
            }

            @Override
            public void setSeekBar() {
                mPlayMusicBinding.textViewPlayedTime.setText(mMusicViewModel.getCurrentPosition());
                mPlayMusicBinding.seekBarDuration.setProgress(
                        Music.convertMilliToSecond(mMusicViewModel.getCurrentMillis()), true);
            }

            @Override
            public void shareMusic(Intent intent) {
//                startActivity(intent);
            }

        });

        try {
            mMusicViewModel.setMusicPlayActivity(mMusic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setListners() {
        mPlayMusicBinding.textViewTitleMusic.setSelected(true);
        mPlayMusicBinding.seekBarDuration.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    seekBar.setProgress(progress);
                    mMusicViewModel.setCurrentPosition(Music.convertSecondToMillis(seekBar.getProgress()));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        if (mMusicViewModel.isLiked()){
            mPlayMusicBinding.imageButtonLike.setImageBitmap(
                    BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground));
        }
        else{
            mPlayMusicBinding.imageButtonLike.setImageBitmap(
                    BitmapFactory.decodeResource(getResources(), R.mipmap.unlike));
        }

        mPlayMusicBinding.imageButtonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

}