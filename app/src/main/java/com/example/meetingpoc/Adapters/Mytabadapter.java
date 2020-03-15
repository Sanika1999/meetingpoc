package com.example.meetingpoc.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.meetingpoc.Fragments.FileViewerFragment;
import com.example.meetingpoc.Fragments.RecordFragment;

public class Mytabadapter extends FragmentPagerAdapter {
    String[] titles={"Record", "Saved Recording"};

    public Mytabadapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new RecordFragment();

            case 1:
                return new FileViewerFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
