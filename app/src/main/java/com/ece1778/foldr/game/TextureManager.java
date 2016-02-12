package com.ece1778.foldr.game;

import android.content.res.AssetManager;
import android.util.Log;

import com.ece1778.foldr.FoldrApplication;
import com.ece1778.foldr.models.Textures;
import com.threed.jpct.Texture;

import java.io.IOException;

/**
 * Created by Ding on 3/30/2015.
 */
public class TextureManager {
    private static TextureManager textureManager;
    private AssetManager assetManager;
    private TextureManager(){
        this.assetManager = FoldrApplication.getContext().getAssets();
        this.loadTextures();
    }

    public synchronized static TextureManager getInstance(){
        if(textureManager == null){
            textureManager = new TextureManager();
        }

        return textureManager;
    }

    private void loadTextures(){
        try {
            com.threed.jpct.TextureManager manager = com.threed.jpct.TextureManager.getInstance();
            Texture red = new Texture(this.assetManager.open("textures/red.jpg"));
            Texture yellow = new Texture(this.assetManager.open("textures/yellow.jpg"));
            Texture blue = new Texture(this.assetManager.open("textures/blue.jpg"));
            Texture green = new Texture(this.assetManager.open("textures/green.jpg"));
            Texture grey = new Texture(this.assetManager.open("textures/grey.jpg"));
            Texture background = new Texture(this.assetManager.open("textures/back.jpg"));

            manager.addTexture(Textures.Red, red);
            manager.addTexture(Textures.Yellow, yellow);
            manager.addTexture(Textures.Green, green);
            manager.addTexture(Textures.Grey, grey);
            manager.addTexture(Textures.Blue, blue);
            manager.addTexture(Textures.Background, background);

            manager.compress();
        }catch (IOException e){
            Log.e(TextureManager.class.getSimpleName(), e.getMessage());
        }
    }
}
