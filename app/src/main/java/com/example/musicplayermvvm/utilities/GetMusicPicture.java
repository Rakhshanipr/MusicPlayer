package com.example.musicplayermvvm.utilities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;

public class GetMusicPicture {

    public static Bitmap convertBitmap(String path) {

        MediaMetadataRetriever mMediaMetadataRetriever = new MediaMetadataRetriever();

        mMediaMetadataRetriever.setDataSource(path);

        final byte[] mPic = mMediaMetadataRetriever.getEmbeddedPicture();
        if (mPic != null) {
            BitmapFactory.Options bitmapFactory = new BitmapFactory.Options();
            bitmapFactory.outWidth = 60;
            bitmapFactory.outHeight = 60;
            Bitmap songImage = BitmapFactory.decodeByteArray(mPic, 0, mPic.length, bitmapFactory);

            return songImage;
        }
        return null;
    }
}
