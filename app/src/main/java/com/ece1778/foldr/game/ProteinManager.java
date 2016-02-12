package com.ece1778.foldr.game;

import android.content.res.AssetManager;

import com.ece1778.foldr.FoldrApplication;
import com.ece1778.foldr.data.Protein;
import com.ece1778.foldr.utils.ProteinReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Ding on 3/29/2015.
 */
public class ProteinManager {
    private static ProteinManager proteinManager;
    private final AssetManager assetManager;
    private final ArrayList<Protein> proteins;
    private final String levelFolder = "levels";
    private boolean loaded;

    private ProteinManager(){
        this.proteins = new ArrayList<>();
        this.assetManager = FoldrApplication.getContext().getAssets();
    }

    public synchronized static ProteinManager getInstance(){
        if(proteinManager == null){
            proteinManager = new ProteinManager();
        }

        return proteinManager;
    }

    public void loadProteins(){
        // Load only once
        if(this.loaded){
            return;
        }

        try {
            String[] levels = this.assetManager.list(levelFolder);
            for(String level : levels){
                if(level.endsWith(".lv")) {
                    InputStream stream = this.assetManager.open(levelFolder + "/" + level);
                    Protein protein = ProteinReader.readProteinFile(stream);
                    stream.close();
                    this.proteins.add(protein);
                }
            }
            this.loaded = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Collection<Protein> getProteins(){
        return this.proteins;
    }

    public Protein getProteinByIndex(int index){
        return this.proteins.get(index);
    }
}
