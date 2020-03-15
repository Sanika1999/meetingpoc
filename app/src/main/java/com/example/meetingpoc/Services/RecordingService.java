package com.example.meetingpoc.Services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.meetingpoc.Database.DBhelper;
import com.example.meetingpoc.Models.RecordingItem;
import com.example.meetingpoc.R;
import com.melnykov.fab.FloatingActionButton;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;


public class RecordingService extends Service {

    MediaRecorder mediaRecorder= new MediaRecorder();
    long mStartingtimemillis = 0;
    long mElapsedmillis = 0;
    File file;
    String filename;
    DBhelper dBhelper;




    @Override
    public void onCreate() {
        super.onCreate();
        dBhelper = new DBhelper(getApplicationContext());



    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Startrecording();
        return START_STICKY;



    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void Startrecording() {
        MediaRecorder mediaRecorder= new MediaRecorder();
        Long tsmillis = System.currentTimeMillis() / 1000;
        String ts = tsmillis.toString();
        filename = "_audio" + ts;
        file = new File(Environment.getExternalStorageDirectory() + "/mysoundrecordings" + filename + ".mp3");

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setOutputFile(file.getAbsoluteFile());
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setAudioChannels(1);
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
            mStartingtimemillis = System.currentTimeMillis();

        } catch (IOException e) {
            e.printStackTrace();
        }






    }
   public void Stoprecording() {


        Log.i("stopping recording", "stop");
        mediaRecorder.stop();
        mElapsedmillis = (System.currentTimeMillis()) - mStartingtimemillis;
        mediaRecorder.reset();

        Toast.makeText(this, "recording saved " + file.getAbsolutePath(), Toast.LENGTH_SHORT).show();
        //add to db
        RecordingItem recordingItem = new RecordingItem(filename, file.getAbsolutePath(), mElapsedmillis, System.currentTimeMillis()
        );
        dBhelper.addRecording(recordingItem);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mediaRecorder!=null){
            Stoprecording();
        }
    }
}
