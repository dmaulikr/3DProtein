package com.ece1778.foldr.models;

/**
 * Created by Ding on 3/17/2015.
 */
public class GameGrid {
    private Cube[][][] grid;
    private int size;

    public GameGrid(int size){
        this.size = size;
        this.grid = new Cube[size * 2][size * 2][size * 2];
    }
}
