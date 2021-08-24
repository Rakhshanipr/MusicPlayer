package com.example.musicplayermvvm.utilities;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.example.musicplayermvvm.services.MusicService;
import com.example.musicplayermvvm.ui.activity.MainActivity;


public class NotificationReciver extends BroadcastReceiver {


    MusicService mMusicService;
    String mAction;
    Context mContext;

    TelephonyManager telManager;
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        mContext = context.getApplicationContext();
        mContext.bindService(MusicService.newIntent(context), mConnection
                , Context.BIND_AUTO_CREATE);
        telManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
        telManager.listen(phoneListener, PhoneStateListener.LISTEN_CALL_STATE);

        mAction = intent.getAction();

    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            if (!(service instanceof MusicService.MusicBinder))
                Log.e("service", "this service is wrong");

            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            mMusicService = binder.getMusicService();


            if (mAction == null)
                return;

            switch (mAction) {
                case MainActivity.ACTION_PREV:
                    mMusicService.getCallBackMusicService().prev();
                    break;
                case MainActivity.ACTION_NEXT:
                    mMusicService.getCallBackMusicService().next();
                    break;
                case MainActivity.ACTION_PLAY:
                    mMusicService.getCallBackMusicService().playPause();
                    break;
            }

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mContext.unbindService(mConnection);
        }
    };

    private final PhoneStateListener phoneListener = new PhoneStateListener() {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            try {
                switch (state) {
                    case TelephonyManager.CALL_STATE_RINGING: {
                        mMusicService.getMediaPlayer().pause();
                        break;
                    }
                    case TelephonyManager.CALL_STATE_OFFHOOK: {
                        mMusicService.getMediaPlayer().pause();
                        break;
                    }
                    case TelephonyManager.CALL_STATE_IDLE: {
                        break;
                    }
                    default: { }
                }
            } catch (Exception ex) {

            }
        }
    };
}
