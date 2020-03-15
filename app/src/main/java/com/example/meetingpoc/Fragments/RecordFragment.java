package com.example.meetingpoc.Fragments;

import android.content.Intent;
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
import androidx.fragment.app.Fragment;

import com.example.meetingpoc.R;
import com.example.meetingpoc.Services.RecordingService;
import com.melnykov.fab.FloatingActionButton;


import java.io.File;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
        onRecord(mStartRecording);
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
}
