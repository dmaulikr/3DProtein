package com.ece1778.foldr.game;

import com.ece1778.foldr.data.AminoAcidProperty;
import com.ece1778.foldr.models.Block;
import com.ece1778.foldr.models.Cube;
import com.threed.jpct.Object3D;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Ding on 3/18/2015.
 */
public class ScoreCalculator {
    private static ScoreCalculator scoreCalculator;
    private int score;
    private int compatScore;
    private int basePoint = 100;

    private ScoreCalculator(){

    }

    public synchronized static ScoreCalculator getInstance(){
        if(scoreCalculator == null){
            scoreCalculator = new ScoreCalculator();
        }
        return scoreCalculator;
    }

    public void calculate(HashMap<String, Cube> collisionMap, Block currentBlock){
        List<Cube> cubes = currentBlock.getCubes();
        for(Cube cube : cubes){
            AminoAcidProperty property = cube.getProperty();
            if(property != AminoAcidProperty.None){
                List<String> hashes = this.getAdjacentHashes(cube);
                for(String hash : hashes){
                    if(collisionMap.containsKey(hash)){
                        Cube target = collisionMap.get(hash);

                        AminoAcidProperty targetProperty = target.getProperty();

                        if(targetProperty == AminoAcidProperty.Hydrophobic && property == AminoAcidProperty.Hydrophilic){
                            score -= basePoint;
                        }else if(targetProperty == AminoAcidProperty.Hydrophilic && property == AminoAcidProperty.Hydrophobic){
                            score -= basePoint;
                        }else if(targetProperty == AminoAcidProperty.Hydrophobic || property == AminoAcidProperty.Hydrophobic) {
                            score += basePoint;
                        }else if(targetProperty == AminoAcidProperty.Acidic && property == AminoAcidProperty.Acidic){
                            score -= basePoint;
                        }else if(targetProperty == AminoAcidProperty.Basic && property == AminoAcidProperty.Basic){
                            score -= basePoint;
                        }else if(targetProperty == AminoAcidProperty.Acidic && property == AminoAcidProperty.Basic){
                            score += basePoint;
                        }else if(targetProperty == AminoAcidProperty.Basic && property == AminoAcidProperty.Acidic){
                            score += basePoint;
                        }
                    }
                }
            }
        }
    }

    public void calculateCompatScore(HashMap<String,Cube> collisionMap){
        int total = 0;
        int covered = 0;

        Set<String> keySet = collisionMap.keySet();
        for(String key : keySet){
            Cube cube = collisionMap.get(key);
            List<String> hashes = this.getAdjacentHashes(cube);
            for(String hash : hashes){
                if(collisionMap.containsKey(hash)){
                    covered++;
                }
            }
            total += 6; // A cube has 6 faces
        }

        this.compatScore = basePoint * keySet.size() * covered / total;
    }

    private List<String> getAdjacentHashes(Cube cube){
        SimpleVector center = cube.getTransformedCenter();
        List<String> hashes = new ArrayList<>();
        int x = Math.round(center.x);
        int y = Math.round(center.y);
        int z = Math.round(center.z);
        hashes.add((x + 2) + "|" + y + "|" + z);
        hashes.add((x - 2) + "|" + y + "|" + z);
        hashes.add(x + "|" + (y + 2) + "|" + z);
        hashes.add(x + "|" + (y - 2) + "|" + z);
        hashes.add(x + "|" + y + "|" + (z + 2));
        hashes.add(x + "|" + y + "|" + (z - 2));

        return hashes;
    }

    public int getScore(){
        return this.score + compatScore;
    }
}
