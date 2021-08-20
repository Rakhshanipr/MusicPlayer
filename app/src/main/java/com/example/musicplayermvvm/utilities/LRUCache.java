package com.example.musicplayermvvm.utilities;

import android.graphics.Bitmap;

import androidx.collection.LruCache;

public class LRUCache extends LruCache<String, Bitmap> {

    private LruCache<String, Bitmap> memoryCache;

    private static LRUCache sLruCache;

    public static LRUCache getInstance(){
        if (sLruCache==null)
            sLruCache=new LRUCache(3072);
        return sLruCache;
    }

    private LRUCache(int maxSize) {
        super(maxSize);
        memoryCache=new LruCache<String, Bitmap>(maxSize);
    }

    public Bitmap getbitmap(String path){
        return memoryCache.get(path);
    }

    public void putBitmap(String path,Bitmap bitmap){
        memoryCache.put(path,bitmap);
    }
}
