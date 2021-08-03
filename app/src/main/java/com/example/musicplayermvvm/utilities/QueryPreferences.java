package com.example.musicplayermvvm.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.musicplayermvvm.data.model.Music;
import com.example.musicplayermvvm.data.repository.MusicRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class QueryPreferences {

    public static String sSettingsPref = "Settings";
    public static String sSettingsRepeatPref = "repeat";
    public static String sSettingsRandomPref = "random";
    public static String sLikesMusicPref = "Like";

    //region Settings
    public static void setRepeatPref(Context context, boolean isActive) {
        SharedPreferences preferencee = context.getSharedPreferences(sSettingsPref, Context.MODE_PRIVATE);
        preferencee.edit().putBoolean(sSettingsRepeatPref, isActive).apply();
    }

    public static Boolean getRepeatPref(Context context) {
        SharedPreferences preferencee = context.getSharedPreferences(sSettingsPref, Context.MODE_PRIVATE);
        return preferencee.getBoolean(sSettingsRepeatPref, false);
    }

    public static void setRandomPref(Context context, boolean isActive) {
        SharedPreferences preferencee = context.getSharedPreferences(sSettingsPref, Context.MODE_PRIVATE);
        preferencee.edit().putBoolean(sSettingsRandomPref, isActive).apply();
    }

    public static boolean getRandomPref(Context context) {
        SharedPreferences preferencee = context.getSharedPreferences(sSettingsPref, Context.MODE_PRIVATE);
        return preferencee.getBoolean(sSettingsRandomPref, false);
    }

    //endregion

    public static void setMusicLikePref(Context context, Music music, boolean isActive) {
        if (!isActive) {
            SharedPreferences preferencee = context.getSharedPreferences(sLikesMusicPref, Context.MODE_PRIVATE);
            preferencee.edit().putString(music.getFilePath(), "true").apply();
        } else {
            SharedPreferences preferencee = context.getSharedPreferences(sLikesMusicPref, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor=preferencee.edit();
                    editor.remove(music.getFilePath());
                    editor.commit();
        }
    }

    public static List<Music> getMusicsLikePref(Context context) {

        SharedPreferences preferencee = context.getSharedPreferences(sLikesMusicPref, Context.MODE_PRIVATE);
        Map<String, String> map = (Map<String, String>) preferencee.getAll();

        Set<String> stringSet = map.keySet();

        List<Music> musics = new ArrayList<>();
        for (String path : stringSet) {
            musics.add(MusicRepository.convertDataSourceToMusic(path));
        }
        return musics;
    }


    public static boolean getMusicLikePref(Context context,String musicPath) {

        SharedPreferences preferencee = context.getSharedPreferences(sLikesMusicPref, Context.MODE_PRIVATE);
        Map<String, String> map = (Map<String, String>) preferencee.getAll();

        Set<String> stringSet = map.keySet();

        boolean exsits=stringSet.contains(musicPath);

        return exsits;
    }
}