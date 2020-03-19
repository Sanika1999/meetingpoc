package com.example.meetingpoc.Fragments;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.meetingpoc.R;
import com.example.meetingpoc.Services.RecordingService;
import com.melnykov.fab.FloatingActionButton;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;


import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.os.Build.VERSION_CODES.M;

public class RecordFragment extends Fragment {
    @BindView(R.id.chronometer)
    Chronometer chronometer;
    @BindView(R.id.recordingstatustext)
    TextView recordingstatustext;
    @BindView(R.id.btnrecord)
    FloatingActionButton btnrecord;
    @BindView(R.id.btnpause)
    Button btnpause;
    private boolean mStartRecording=true;
    private boolean mPauseRecording=true;
    long timewhenpaused=0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View recordView=inflater.inflate(R.layout.fragment_record,container,false);
        ButterKnife.bind(this,recordView);
        return recordView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnpause.setVisibility(View.GONE);
        btnrecord.setColorPressed(getResources().getColor(R.color.colorPrimary));

    }

    @OnClick(R.id.btnrecord)
    public void recordaudio(){
        //onRecord(mStartRecording);
        checkPermission(mStartRecording);
        mStartRecording=!mStartRecording;

    }

    private void onRecord(boolean Start) {
        Intent intent=new Intent(getActivity(), RecordingService.class);
        if(Start){

            btnrecord.setImageResource(R.drawable.ic_mic_white);
            Toast.makeText(getContext(), "Recording Started", Toast.LENGTH_SHORT).show();
            File folder=new File(Environment.getExternalStorageDirectory()+"/mysoundrecordings");
            if(!folder.exists()){
                folder.mkdir();
            }
            chronometer.setBase(SystemClock.elapsedRealtime());
            chronometer.start();
            Objects.requireNonNull(getActivity()).startService(intent);
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            recordingstatustext.setText("recording");
        }
        else{
            btnrecord.setImageResource(R.drawable.stop);
            chronometer.stop();
            chronometer.setBase(SystemClock.elapsedRealtime());
            timewhenpaused=0;
            recordingstatustext.setText("recording stopped");
            Objects.requireNonNull(getActivity()).stopService(intent);


        }
    }

    private void checkPermission(final boolean isStart) {
        //Check permissions
        if (Build.VERSION.SDK_INT >= M) {

            final String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO};

            int permissionAudioCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.RECORD_AUDIO);
            int readStorageCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE);
            int writeStorageCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (permissionAudioCheck == PERMISSION_GRANTED &&
                    readStorageCheck == PERMISSION_GRANTED &&
                    writeStorageCheck == PERMISSION_GRANTED)
            {
                onRecord(isStart);
                return;
            }

            Permissions.check(getActivity(),
                    permissions,
                    null,
                    null,
                    new PermissionHandler() {
                        @Override
                        public void onGranted() {

                            onRecord(isStart);
                        }

                        @Override
                        public void onDenied(Context context, ArrayList<String> deniedPermissions) {
                            // permission denied, block the feature.
                            Toast.makeText(getActivity(), "Camera permission is required is use this feature", Toast.LENGTH_LONG).show();
                        }
                    });
            return;
        }
    }
}
