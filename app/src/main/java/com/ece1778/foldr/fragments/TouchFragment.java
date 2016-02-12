package com.ece1778.foldr.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ece1778.foldr.R;
import com.ece1778.foldr.control.Axis;
import com.ece1778.foldr.control.Direction;
import com.ece1778.foldr.control.IGameControl;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class TouchFragment extends Fragment {

    private IGameControl gameControl;

    @InjectView(R.id.up) ImageView upButton;
    @InjectView(R.id.down) ImageView downButton;
    @InjectView(R.id.left) ImageView leftButton;
    @InjectView(R.id.right) ImageView rightButton;
    @InjectView(R.id.in) ImageView inButton;
    @InjectView(R.id.out) ImageView outButton;
    @InjectView(R.id.x) ImageView xButton;
    @InjectView(R.id.y) ImageView yButton;
    @InjectView(R.id.z) ImageView zButton;
    @InjectView(R.id.done) ImageView doneButton;
    @InjectView(R.id.menu) ImageView menuButton;
    @InjectView(R.id.help) ImageView helpButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_touch, container, false);
        ButterKnife.inject(this, view);
        this.bindButtons();
        return view;
    }

    public void setGameContrl(IGameControl gameControl){
        this.gameControl = gameControl;
    }

    private void bindButtons(){
        this.upButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameControl != null) gameControl.moveBlock(Axis.Y, Direction.Plus);
            }
        });
        this.downButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameControl != null) gameControl.moveBlock(Axis.Y, Direction.Minus);
            }
        });
        this.leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameControl != null) gameControl.moveBlock(Axis.X, Direction.Plus);
            }
        });
        this.rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameControl != null) gameControl.moveBlock(Axis.X, Direction.Minus);
            }
        });
        this.inButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameControl != null) gameControl.moveBlock(Axis.Z, Direction.Plus);
            }
        });
        this.outButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameControl != null) gameControl.moveBlock(Axis.Z, Direction.Minus);
            }
        });
        this.xButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameControl != null) gameControl.rotateBlock(Axis.X);
            }
        });
        this.yButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameControl != null) gameControl.rotateBlock(Axis.Y);
            }
        });
        this.zButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameControl != null) gameControl.rotateBlock(Axis.Z);
            }
        });
        this.doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameControl != null) gameControl.nextBlock();
            }
        });

        this.menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameControl != null) gameControl.showMenu();
            }
        });

        this.helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameControl != null) gameControl.showHelp();
            }
        });
    }

}
