package com.ece1778.foldr.activities;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ece1778.foldr.R;
import com.ece1778.foldr.control.ICameraControl;
import com.ece1778.foldr.control.MyoControl;
import com.ece1778.foldr.control.TouchCameraControl;
import com.ece1778.foldr.data.Protein;
import com.ece1778.foldr.engine.GameCallback;
import com.ece1778.foldr.fragments.TouchFragment;
import com.ece1778.foldr.game.GameCode;
import com.ece1778.foldr.game.ProteinManager;
import com.thalmic.myo.scanner.ScanActivity;


import butterknife.ButterKnife;
import butterknife.InjectView;

public class GameActivity extends BaseOpenGLActivity implements GameCallback {

    @InjectView(R.id.glContainer)
    FrameLayout glContainer;
    @InjectView(R.id.ruleContainer)
    LinearLayout ruleContainer;
    @InjectView(R.id.rule)
    ImageView rule;
    @InjectView(R.id.score)
    TextView score;
    @InjectView(R.id.moves)
    TextView moves;

    private final ProteinManager proteinManager;
    private TouchCameraControl cameraControl;
    private MyoControl myoControl;

    public GameActivity(){
        this.proteinManager = ProteinManager.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_game);
        ButterKnife.inject(this);

        glContainer.addView(this.glSurfaceView);
        this.engine.setGameCallback(this);
        this.engine.getWorld().setAmbientLight(200,200,200);

        this.initControl();
       // this.initMyoControl();

        initGame();
    }

    private void initControl(){
        this.cameraControl = new TouchCameraControl();
        this.glSurfaceView.setOnTouchListener(this.cameraControl);
        this.engine.setCameraControl(this.cameraControl);

        TouchFragment touchFragment = new TouchFragment();
        touchFragment.setGameContrl(this.engine);

        FragmentManager fragmentManager = this.getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.controlContainer, touchFragment);
        transaction.commit();

        this.rule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ruleContainer.setVisibility(View.GONE);
            }
        });
    }

    private void initMyoControl(){
        this.myoControl = new MyoControl(this);
        Intent intent = new Intent(this, ScanActivity.class);
    //    this.startActivity(intent);
    }

    private void initGame(){
        Intent intent = this.getIntent();
        int index = intent.getExtras().getInt("ProteinId");
        Protein protein = this.proteinManager.getProteinByIndex(index);

        this.engine.startGame(protein);
    }

    @Override
    public void gameFinished(GameCode code, int points) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        if(code == GameCode.Pass) {
            builder.setTitle("Pass (^_^)").setMessage("You got " + points + " points!");
        }else{
            builder.setTitle("Failed T_T").setMessage("Better luck next time.");
        }
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.create().show();

        // play sound effect
        if(code == GameCode.Pass){
            mediaManager.playWinSound();
        }else{
            mediaManager.playLoseSound();
        }
    }

    @Override
    public void showBlockedToast(){
        Toast.makeText(this, "Invalid Positioning", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showMenu() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Quit?").setMessage("Do you want to quit this level?");
        builder.setPositiveButton("Quit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mediaManager.playLoseSound();
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void showHelp() {
        this.ruleContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume(){
        super.onResume();
        this.mediaManager.playBackgroundSound();
 //       this.myoControl.connect();
    }

    @Override
    public void onPause(){
        super.onPause();
        this.mediaManager.stopBackgroundSound();
    //    this.myoControl.disconnect();
    }

    @Override
    public void updateScoreAndMoves(int points, int moves){
        this.moves.setText(moves + " Block(s) Left");
        this.score.setText("Score: " + points);
    }
}
