package com.ece1778.foldr.control;

/**
 * Created by Ding on 3/30/2015.
 */
public interface IGameControl {

    public void moveBlock(Axis axis, Direction direction);

    public void rotateBlock(Axis axis);

    public void nextBlock();

    public void showMenu();

    public void showHelp();
}
