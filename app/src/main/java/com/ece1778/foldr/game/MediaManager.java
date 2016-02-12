package com.ece1778.foldr.game;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.util.Log;

import com.ece1778.foldr.FoldrApplication;
import com.ece1778.foldr.R;

import java.io.IOException;

/**
 * Created by Ding on 3/29/2015.
 */
public class MediaManager {

    private static MediaManager manager;

    private final Context context;
    private final AssetManager assetManager;
    private final MediaPlayer backgroundPlayer = new MediaPlayer();
    private final MediaPlayer movePlayer = new MediaPlayer();
    private final MediaPlayer nextPlayer = new MediaPlayer();
    private final MediaPlayer winPlayer = new MediaPlayer();
    private final MediaPlayer menuPlayer = new MediaPlayer();
    private final MediaPlayer titlePlayer = new MediaPlayer();
    private final MediaPlayer losePlayer = new MediaPlayer();
    private final MediaPlayer blockPlayer = new MediaPlayer();
    private final String AssetPath = "file:///android_asset/";

    private MediaManager(){
        this.context = FoldrApplication.getContext();
        this.assetManager = this.context.getAssets();

        try {
            AssetFileDescriptor descriptor = null;

            // Background music
            descriptor = this.assetManager.openFd("audio/background.mp3");
            this.backgroundPlayer.setDataSource(descriptor.getFileDescriptor(),descriptor.getStartOffset(), descriptor.getLength());
            this.backgroundPlayer.prepareAsync();
            this.backgroundPlayer.setLooping(true);
            descriptor.close();

            // Move Sound
            descriptor = this.assetManager.openFd("audio/move.mp3");
            this.movePlayer.setDataSource(descriptor.getFileDescriptor(),descriptor.getStartOffset(), descriptor.getLength());
            this.movePlayer.prepareAsync();
            descriptor.close();

            // Next Sound
            descriptor = this.assetManager.openFd("audio/next.mp3");
            this.nextPlayer.setDataSource(descriptor.getFileDescriptor(),descriptor.getStartOffset(), descriptor.getLength());
            this.nextPlayer.prepareAsync();
            descriptor.close();

            // Win Sound
            descriptor = this.assetManager.openFd("audio/win.mp3");
            this.winPlayer.setDataSource(descriptor.getFileDescriptor(),descriptor.getStartOffset(), descriptor.getLength());
            this.winPlayer.prepareAsync();
            descriptor.close();

            // Menu Click Sound
            descriptor = this.assetManager.openFd("audio/success.mp3");
            this.menuPlayer.setDataSource(descriptor.getFileDescriptor(),descriptor.getStartOffset(), descriptor.getLength());
            this.menuPlayer.prepareAsync();
            descriptor.close();

            // Title Sound
            descriptor = this.assetManager.openFd("audio/title.mp3");
            this.titlePlayer.setDataSource(descriptor.getFileDescriptor(),descriptor.getStartOffset(), descriptor.getLength());
            this.titlePlayer.prepareAsync();
            this.titlePlayer.setLooping(true);
            descriptor.close();

            // Lose Sound
            descriptor = this.assetManager.openFd("audio/lose.mp3");
            this.losePlayer.setDataSource(descriptor.getFileDescriptor(),descriptor.getStartOffset(), descriptor.getLength());
            this.losePlayer.prepareAsync();
            descriptor.close();

            // Block Sound
            descriptor = this.assetManager.openFd("audio/blocked.mp3");
            this.blockPlayer.setDataSource(descriptor.getFileDescriptor(),descriptor.getStartOffset(), descriptor.getLength());
            this.blockPlayer.prepareAsync();
            descriptor.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public synchronized static MediaManager getInstance(){
        if(manager == null){
            manager = new MediaManager();
        }
        return manager;
    }

    public void playMenuClickSound(){
        if(menuPlayer.isPlaying()){
            menuPlayer.seekTo(0);
        }else {
            menuPlayer.start();
        }
    }

    public void playNextBlockSound(){
        if(nextPlayer.isPlaying()){
            nextPlayer.seekTo(0);
        }else {
            nextPlayer.start();
        }
    }

    public void playMoveBlockSound(){
        if(movePlayer.isPlaying()){
            movePlayer.seekTo(0);
        }else {
            movePlayer.start();
        }
    }

    public void playWinSound(){
        if(winPlayer.isPlaying()){
            winPlayer.seekTo(0);
        }else {
            winPlayer.start();
        }
    }

    public void playLoseSound(){
        if(losePlayer.isPlaying()){
            losePlayer.seekTo(0);
        }else{
            losePlayer.start();
        }
    }

    public void playTitleSound(){
        if(titlePlayer.isPlaying()){
            titlePlayer.seekTo(0);
        }else{
            titlePlayer.start();
        }
    }

    public void stopTitleSound(){
        if(titlePlayer.isPlaying()){
            titlePlayer.pause();
        }
        titlePlayer.seekTo(0);
    }

    public void playBlockedSound(){
        if(blockPlayer.isPlaying()){
            blockPlayer.seekTo(0);
        }else{
            blockPlayer.start();
        }
    }

    public void playBackgroundSound(){
        if(!backgroundPlayer.isPlaying()){
            backgroundPlayer.start();
        }
    }

    public void stopBackgroundSound(){
        if(backgroundPlayer.isPlaying()){
            backgroundPlayer.pause();
        }
    }

    public void dispose(){
        this.backgroundPlayer.stop();
        this.winPlayer.stop();
        this.menuPlayer.stop();
        this.nextPlayer.stop();
        this.movePlayer.stop();

        this.backgroundPlayer.release();
        this.winPlayer.release();
        this.menuPlayer.release();
        this.nextPlayer.release();
        this.movePlayer.release();
    }
}
