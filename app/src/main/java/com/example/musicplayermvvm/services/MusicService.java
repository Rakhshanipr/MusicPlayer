package com.example.musicplayermvvm.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.core.app.NotificationCompat;

import com.example.musicplayermvvm.R;
import com.example.musicplayermvvm.data.model.Music;
import com.example.musicplayermvvm.data.repository.MusicRepository;
import com.example.musicplayermvvm.ui.activity.MainActivity;
import com.example.musicplayermvvm.utilities.CreateNotification;
import com.example.musicplayermvvm.utilities.GetMusicPicture;
import com.example.musicplayermvvm.utilities.NotificationReciver;

import java.io.IOException;
import java.io.Serializable;

public class MusicService extends Service {
    public static final String BUNDLE_CALL_BACK = "bundle_CallBack";
    public static final int ID_MUSIC_NOTIFICATION = 0;

    //region defind static method and variable

    public static Intent newIntent(Context context) {
        return new Intent(context, MusicService.class);
    }

    //endregion

    MediaSessionCompat mMediaSessionCompat;

    public static boolean sMusicAvailible = false;
    public static Music sMusic = null;

    MediaPlayer mMediaPlayer;
    String mPath = "";

    public ICallBackMusicService getCallBackMusicService() {
        return mCallBackMusicService;
    }

    public ICallBackMusicService mCallBackMusicService;

    IBinder mBinder = new MusicBinder();

    public MusicService() {

    }

    @Override
    public IBinder onBind(Intent intent) {
        mMediaSessionCompat = new MediaSessionCompat(this, "mediaSessionCompatNotification");
        return mBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    public void setCallBackMusicService(ICallBackMusicService callBackMusicService) {
        mCallBackMusicService = callBackMusicService;
    }

    public Music getMusic() {
        return sMusic;
    }

    public void showNotification(int iconPlayPause) {

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent =
                PendingIntent.getActivity(this, 0, intent, 0);

        Intent prevIntent = new Intent
                (this, NotificationReciver.class).setAction(MainActivity.ACTION_PREV);

        PendingIntent prevPendingIntent = PendingIntent.getBroadcast(
                this, 0, prevIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent playIntent = new Intent
                (this, NotificationReciver.class).setAction(MainActivity.ACTION_PLAY);
        PendingIntent playPendingIntent = PendingIntent.getBroadcast(
                this, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent nextIntent = new Intent
                (this, NotificationReciver.class).setAction(MainActivity.ACTION_NEXT);
        PendingIntent nextPendingIntent = PendingIntent.getBroadcast(
                this, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new NotificationCompat.Builder(this, CreateNotification.CHANELL)
                .setSmallIcon(R.mipmap.pausemusic)
                .setLargeIcon(GetMusicPicture.convertBitmap(sMusic.getFilePath()))
                .setContentTitle(sMusic.getName())
                .setContentText(sMusic.getArtist())
                .addAction(R.drawable.previous_music, "previuos", prevPendingIntent)
                .addAction(iconPlayPause, "play", playPendingIntent)
                .addAction(R.drawable.next_music, "next", nextPendingIntent)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView()
                        .setMediaSession(mMediaSessionCompat.getSessionToken()))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(contentIntent)
                .setOngoing(true)
                .setSilent(true)
                .setOnlyAlertOnce(true)
                .build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(ID_MUSIC_NOTIFICATION, notification);

    }

    public void setMusic(Music music) {
        sMusicAvailible = true;
        sMusic = music;

    }

    public void next10Second() {
        if (getMediaPlayer().getCurrentPosition() + 10000 <= getMediaPlayer().getDuration()) {
            getMediaPlayer().seekTo(getMediaPlayer().getCurrentPosition() + 10000);
        }
    }

    public void prev10Second() {
        if (getMediaPlayer().getCurrentPosition() - 10000 >= 0) {
            getMediaPlayer().seekTo(getMediaPlayer().getCurrentPosition() - 10000);
        }
    }

    public void setCurrentPosition(int position) {
        mMediaPlayer.seekTo(position);
    }

    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        mMediaPlayer = mediaPlayer;
    }

    public void changeMediaPlayerData(String path) throws IOException {
        if (getMediaPlayer() == null) {
            mMediaPlayer = new MediaPlayer();
        }

        sMusicAvailible = true;
        mPath = path;
        mMediaPlayer.reset();
        mMediaPlayer.setDataSource(path);
        mMediaPlayer.prepare();
        mMediaPlayer.start();

        setMusic(MusicRepository.convertDataSourceToMusic(path));
        showNotification(R.drawable.pause_music);
        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mCallBackMusicService.completeSong();
            }
        });
    }

    public MediaPlayer getMediaPlayer() {
        return mMediaPlayer;
    }

    public int getCurrentPosition() {
        return mMediaPlayer.getCurrentPosition();
    }

    public int getDuration() {
        return mMediaPlayer.getDuration();
    }

    public void playPauseMusic() {
        if (getMediaPlayer().isPlaying()) {
            getMediaPlayer().pause();
            showNotification(R.drawable.play_music);
        }
        else{
            getMediaPlayer().start();
            showNotification(R.drawable.pause_music);
        }
    }

    public class MusicBinder extends Binder {
        public MusicService getMusicService() {
            return MusicService.this;
        }

    }

    public interface ICallBackMusicService extends Serializable {
        void completeSong();
        void playPause();
        void next();
        void prev();
    }
}