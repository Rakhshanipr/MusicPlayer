package com.example.musicplayermvvm.thread;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import java.util.concurrent.ConcurrentHashMap;

public class SetMusicCover extends HandlerThread {


    public static final String TAG_MUSIC_COVER = "SetMusicCover";
    public static final int WHAT = 0;
    Handler mRequstHandler;

    Handler mHandlerSetPhoto;


    public void setHandlerSetPhoto(Handler handlerSetPhoto) {
        mHandlerSetPhoto = handlerSetPhoto;
    }

    ConcurrentHashMap<String,ImageView> mHashMap=new ConcurrentHashMap<>();

    public SetMusicCover() {
        super(TAG_MUSIC_COVER);
    }

    @Override
    protected void onLooperPrepared() {
        super.onLooperPrepared();
        mRequstHandler=new Handler(){

            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

                if (msg.what==WHAT){

                    String path=(String)msg.obj;

                    MediaMetadataRetriever mMediaMetadataRetriever = new MediaMetadataRetriever();

                    mMediaMetadataRetriever.setDataSource(path);

                    final byte[] mPic = mMediaMetadataRetriever.getEmbeddedPicture();
                    if (mPic != null) {
                        BitmapFactory.Options bitmapFactory = new BitmapFactory.Options();
                        bitmapFactory.outWidth = 60;
                        bitmapFactory.outHeight = 60;
                        Bitmap songImage = BitmapFactory.decodeByteArray(mPic, 0, mPic.length, bitmapFactory);

                        mHandlerSetPhoto.post(new Runnable() {
                            @Override
                            public void run() {
                                mHashMap.get(path).setImageBitmap(songImage);
                            }
                        });
                    }
                    else{
                        mHandlerSetPhoto.post(new Runnable() {
                            @Override
                            public void run() {
                                mHashMap.get(path).setImageBitmap(null);
                            }
                        });

                    }

                }
            }
        };
    }

    public void queueImageCover(String filePath, ImageView imageView){
        mHashMap.put(filePath,imageView);

        mRequstHandler.obtainMessage(WHAT,filePath).sendToTarget();

    }

}
