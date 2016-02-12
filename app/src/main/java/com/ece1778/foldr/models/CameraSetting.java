package com.ece1778.foldr.models;

import com.threed.jpct.SimpleVector;

/**
 * Created by Ding on 3/29/2015.
 */
public class CameraSetting {
    public float cameraDistance = 60;
    public float cameraAngle = 1.2f;
    public float cameraHeight = -0.5f;
    public float rotatePower;
    public float turnPower;
    public float zoomPower = 1;

    public boolean cameraZoom;
    public float zoomValueLast;
    public float zoomValue;
    public boolean touchUseable = true;

    public float xpos = -1;
    public float ypos = -1;
    public float xpos1 = -1;
    public float ypos1 = -1;

    public float touchTurn;
    public float touchTurnUp;

    public SimpleVector lookAt = new SimpleVector();
}
