package com.example.meetingpoc.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetingpoc.Database.DBhelper;
import com.example.meetingpoc.Models.RecordingItem;
import com.example.meetingpoc.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FileViewerFragment extends Fragment {
   @BindView(R.id.recyclerview)
    RecyclerView recyclerView;
   DBhelper dBhelper;
   ArrayList<RecordingItem> arrayListaudio;
   private FileViewerAdapter fileViewerAdapter;







    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_file_viewer,container,false);
        ButterKnife.bind(this,view);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dBhelper=new DBhelper(getContext());
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager llm= new LinearLayoutManager(getContext());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        llm.setReverseLayout(true);
        llm.setStackFromEnd(true);
        recyclerView.setLayoutManager(llm);
        arrayListaudio= dBhelper.getAudios();
        if(arrayListaudio==null){
            Toast.makeText(getContext(), "No audio files", Toast.LENGTH_SHORT).show();


        }
        else{
            fileViewerAdapter=new FileViewerAdapter(getActivity(),arrayListaudio,llm );
            recyclerView.setAdapter(fileViewerAdapter);
        }


    }
}
