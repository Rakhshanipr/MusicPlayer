package com.example.musicplayermvvm.ui.activity;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.os.Bundle;

import com.example.musicplayermvvm.R;
import com.example.musicplayermvvm.data.repository.MusicRepository;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_GET_PERMISSION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermission();
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