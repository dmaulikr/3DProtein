package com.ece1778.foldr.fragments;


import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ece1778.foldr.R;
import com.ece1778.foldr.activities.HomeActivity;
import com.ece1778.foldr.game.MediaManager;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainMenuFragment extends Fragment  {

    @InjectView(R.id.start) ImageView start;
    @InjectView(R.id.tutorial) ImageView tutorial;

    private final MediaManager mediaManager;

    public MainMenuFragment(){
        this.mediaManager = MediaManager.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_menu, container, false);
        ButterKnife.inject(this, view);
        this.bindButtons();
        return view;
    }

    private void bindButtons(){
        this.start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainMenuFragment.this.mediaManager.playMenuClickSound();
                ((HomeActivity)getActivity()).showLevels();
            }
        });

        this.tutorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainMenuFragment.this.mediaManager.playMenuClickSound();
            }
        });
    }

}
