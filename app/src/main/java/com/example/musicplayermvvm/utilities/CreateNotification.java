package com.example.musicplayermvvm.utilities;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.musicplayermvvm.R;
import com.example.musicplayermvvm.data.model.Music;

public class CreateNotification extends Application {

    public static final String TAG = "tag";
    public static final String CHANELL = "CHANNEL_1";
    public static android.app.Notification sNotification;

    public static void createNotification(Context context, Music music){

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(context);
        MediaSessionCompat mediaSessionCompat=new MediaSessionCompat(context, TAG);

        sNotification=new NotificationCompat.Builder(context, CHANELL)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(music.getName())
                .setContentText(music.getArtist())
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.mipmap.like))
                .setOnlyAlertOnce(true)
                .setShowWhen(false)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationManagerCompat.notify(1,sNotification);

    }

    @Override
    public void onCreate() {
        super.onCreate();

        NotificationChannel notificationChannel=new NotificationChannel(
                CHANELL,"channel(1)", NotificationManager.IMPORTANCE_HIGH);

        notificationChannel.setDescription("in the name of God");

        NotificationManager notificationManager=getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(notificationChannel);


//
//
//        mNotificationManager=getSystemService(NotificationManager.class);
//        if (mNotificationManager!=null){
//            mNotificationManager.createNotificationChannel(notificationChannel);
//        }
    }
}
