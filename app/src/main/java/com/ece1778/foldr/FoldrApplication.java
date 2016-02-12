package com.ece1778.foldr;

import android.app.Application;
import android.content.Context;

import com.ece1778.foldr.game.TextureManager;

/**
 * Created by Ding on 3/29/2015.
 */
public class FoldrApplication extends Application {
    private static Context applicationContext;

    @Override
    public void onCreate()
    {
        applicationContext = this.getApplicationContext();
        TextureManager.getInstance();
    }

    public static Context getContext(){
        return applicationContext;
    }
}
