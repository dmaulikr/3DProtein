package com.ece1778.foldr.activities;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.ece1778.foldr.engine.Engine;
import com.ece1778.foldr.game.MediaManager;
import com.threed.jpct.util.AAConfigChooser;

/**
 * Created by Ding on 3/16/2015.
 */
public abstract class BaseOpenGLActivity extends Activity {
    protected MediaManager mediaManager;
    protected GLSurfaceView glSurfaceView;
    protected Engine engine;

    protected void initView(){
        this.glSurfaceView = new GLSurfaceView(this);
        this.glSurfaceView.setEGLContextClientVersion(2);
        this.glSurfaceView.setEGLConfigChooser(new AAConfigChooser(this.glSurfaceView));
        this.glSurfaceView.setRenderer(this.engine.getRenderer());
    }

    protected void initWindow(){
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        // init window
        this.initWindow();
        super.onCreate(savedInstanceState);
        this.mediaManager = MediaManager.getInstance();
        // init OpenGL view
        this.engine = new Engine();
        this.initView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.glSurfaceView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.glSurfaceView.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed(){
        // do nothing
    }
}
