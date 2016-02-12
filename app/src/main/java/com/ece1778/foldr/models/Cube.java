package com.ece1778.foldr.models;

import com.ece1778.foldr.data.AminoAcidProperty;
import com.threed.jpct.Object3D;
import com.threed.jpct.SimpleVector;

/**
 * Created by Ding on 3/17/2015.
 */
public class Cube extends Object3D {
    private final AminoAcidProperty property;

    public Cube(Object3D mesh, AminoAcidProperty property){
        super(mesh, true);
        this.property = property;
    }

    public AminoAcidProperty getProperty(){
        return this.property;
    }

    public String getHash(){
        SimpleVector center = this.getTransformedCenter();
        return Math.round(center.x) + "|" + Math.round(center.y) + "|" + Math.round(center.z);
    }
}
