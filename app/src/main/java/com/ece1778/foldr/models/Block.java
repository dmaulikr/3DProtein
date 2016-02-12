package com.ece1778.foldr.models;

import com.ece1778.foldr.data.FastaType;
import com.threed.jpct.Object3D;
import com.threed.jpct.World;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Ding on 3/17/2015.
 */
public class Block extends Object3D {
    private final ArrayList<Cube> cubes;
    private final FastaType type;

    public Block(FastaType type){
        super(Object3D.createDummyObj());
        this.cubes = new ArrayList<>();
        this.type = type;
    }

    public void addCube(int index, Cube cube){
        this.cubes.add(index, cube);
        cube.addParent(this);
    }

    public void addCube(Cube cube){
        this.cubes.add(cube);
        cube.addParent(this);
    }

    public void addCubes(Cube[] cubes){
        for(Cube cube : cubes){
            this.addCube(cube);
        }
    }

    public List<Cube> getCubes(){
        return this.cubes;
    }

    public boolean needPositionFix(){
        return (type == FastaType.AlphaHelix && (this.cubes.size() / 4) % 2 == 1) ||
               (type == FastaType.BetaSheet && (this.cubes.size()/2) % 2 == 1);
    }

    public void addToWorld(World world){
        world.addObject(this);
        for(Object3D cube : cubes){
            if(cube != null){
                if(cube instanceof Block){
                    ((Block)cube).addToWorld(world);
                }else {
                    world.addObject(cube);
                }
            }
        }
    }

    public FastaType getType(){
        return this.type;
    }
}
