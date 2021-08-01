package com.example.musicplayermvvm.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.musicplayermvvm.data.model.Music;

import java.net.ContentHandler;

public class QueryPreferences {

    public static String sSettingsPref="Settings";
    public static String sSettingsRepeatPref="repeat";
    public static String sSettingsRandomPref="random";
    public static String sLikesMusicPref="Like";
    public static String sFavoritesMusicPref="Favorite";

    public static void setRepeatPref(Context context, boolean isActive){
        SharedPreferences preferencee=context.getSharedPreferences(sSettingsPref,Context.MODE_PRIVATE);
        preferencee.edit().putBoolean(sSettingsRepeatPref,isActive).apply();
    }

    public static Boolean getRepeatPref(Context context){
        SharedPreferences preferencee=context.getSharedPreferences(sSettingsPref,Context.MODE_PRIVATE);
        return preferencee.getBoolean(sSettingsRepeatPref,false);
    }

    public static void setRandomPref(Context context, boolean isActive){
        SharedPreferences preferencee=context.getSharedPreferences(sSettingsPref,Context.MODE_PRIVATE);
        preferencee.edit().putBoolean(sSettingsRandomPref,isActive).apply();
    }

    public static boolean getRandomPref(Context context){
        SharedPreferences preferencee=context.getSharedPreferences(sSettingsPref,Context.MODE_PRIVATE);
        return preferencee.getBoolean(sSettingsRandomPref,false);
    }

    public static void setMusicLikePref(Context context, Music music,boolean isActive){
        if (isActive){
            SharedPreferences preferencee=context.getSharedPreferences(sLikesMusicPref,Context.MODE_PRIVATE);
            preferencee.edit().putString(music.getFilePath(),"true").apply();
        }else {
            SharedPreferences preferencee = context.getSharedPreferences(sLikesMusicPref, Context.MODE_PRIVATE);
            preferencee.edit().remove(music.getFilePath()).apply();
        }
    }

    public static void setMusicFavoritPref(Context context, Music music,boolean isActive){
        if (isActive){
            SharedPreferences preferencee=context.getSharedPreferences(sFavoritesMusicPref,Context.MODE_PRIVATE);
            preferencee.edit().putString(music.getFilePath(),"true").apply();
        }else {
            SharedPreferences preferencee = context.getSharedPreferences(sFavoritesMusicPref, Context.MODE_PRIVATE);
            preferencee.edit().remove(music.getFilePath()).apply();
        }
    }
}
