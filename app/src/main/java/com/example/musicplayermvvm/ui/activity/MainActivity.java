package com.example.musicplayermvvm.ui.activity;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.CompositePageTransformer;

import android.Manifest;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;

import com.example.musicplayermvvm.R;
import com.example.musicplayermvvm.data.repository.MusicRepository;
import com.example.musicplayermvvm.databinding.ActivityMainBinding;
import com.example.musicplayermvvm.veiwmodel.MainActivityViewModel;

import java.io.IOException;
import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    //region defind static method and variable
    public static final int REQUEST_CODE_GET_PERMISSION = 1;
    //endregion

    ActivityMainBinding mMainBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMainBinding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mMainBinding.getRoot());
        getPermission();
        Initial();
    }

    private void Initial() {
        mMainBinding.viewPager2Main.setAdapter(
                MainActivityViewModel.CreateMainViewPager(this)
        );
    }

    private void getPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext()
                , Manifest.permission.READ_EXTERNAL_STORAGE)==-1){
            this.requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}
            , REQUEST_CODE_GET_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,@NonNull String[] permissions
            , @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==REQUEST_CODE_GET_PERMISSION
                && grantResults[0]==0){
        }
    }

}