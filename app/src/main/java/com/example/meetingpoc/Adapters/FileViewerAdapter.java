package com.example.meetingpoc.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meetingpoc.Models.RecordingItem;

import java.util.ArrayList;

public class FileViewerAdapter extends RecyclerView.Adapter<FileViewerAdapter.FileViewerViewHolder> {

    public FileViewerAdapter(Context context, ArrayList<RecordingItem> arrayList, LinearLayoutManager mm)
    {

    }
    @NonNull
    @Override
    public FileViewerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewerViewHolder holder, int position) {

    }


    @Override
    public int getItemCount() {
        return 0;
    }

    public class FileViewerViewHolder extends RecyclerView.ViewHolder{

        public FileViewerViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
