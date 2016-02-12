package com.ece1778.foldr.control;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.ece1778.foldr.models.CameraSetting;
import com.threed.jpct.SimpleVector;

/**
 * Created by Ding on 3/29/2015.
 */
public class TouchCameraControl implements View.OnTouchListener, ICameraControl {

    private CameraSetting setting;

    public TouchCameraControl(){
        this.setting = new CameraSetting();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            this.setting.touchUseable = true;
            this.setting.xpos = event.getX();
            this.setting.ypos = event.getY();
        }

        if (event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
            this.setting.cameraZoom = true;
            this.setting.touchUseable = false;
            this.setting.xpos1 = event.getX(1);
            this.setting.ypos1 = event.getY(1);
            this.setting.zoomValueLast = zoomValue();
            this.setting.touchTurn = 0;
            this.setting.touchTurnUp = 0;
        }

        if (event.getActionMasked() == MotionEvent.ACTION_POINTER_UP) {
            this.setting.cameraZoom = false;
            this.setting.xpos1 = -1;
            this.setting.ypos1 = -1;
        }


        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float xd = event.getX() - this.setting.xpos;
            float yd = event.getY() - this.setting.ypos;
            this.setting.xpos = event.getX();
            this.setting.ypos = event.getY();

            if (this.setting.cameraZoom && !this.setting.touchUseable) {
                this.setting.xpos1 = event.getX(1);
                this.setting.ypos1 = event.getY(1);
                float currentZoomValue = zoomValue();
                float newValue = 1 + (this.setting.zoomValueLast - currentZoomValue) / 400f;
                this.setting.zoomValueLast = currentZoomValue;
                this.setting.zoomValue = newValue;
            } else if (!this.setting.cameraZoom && this.setting.touchUseable) {
                this.setting.touchTurn = xd / -520f;
                this.setting.touchTurnUp = yd / -520f;
            }
        }

        try {
            Thread.sleep(15);
        } catch (Exception e) {
            Log.e(TouchCameraControl.class.getSimpleName(),e.getMessage());
        }

        return true;
    }

    @Override
    public CameraSetting calculateCameraPosition(long ticks) {
        // up/down
        if (this.setting.touchTurnUp != 0) {
            this.setting.touchTurnUp *= ticks;
            if (this.setting.cameraHeight <= -0.1f && this.setting.cameraHeight >= -1f)
                this.setting.cameraHeight += this.setting.touchTurnUp;
            this.setting.turnPower = this.setting.touchTurnUp;
            this.setting.touchTurnUp = 0;
        } else {
            this.setting.turnPower *= 0.95f;
            this.setting.cameraHeight += this.setting.turnPower;
        }
        if (this.setting.cameraHeight > -0.1f)
            this.setting.cameraHeight = -0.1f;
        else if (this.setting.cameraHeight < -1f)
            this.setting.cameraHeight = -1f;

        // right/left
        if (this.setting.touchTurn != 0) {
            this.setting.rotatePower = this.setting.touchTurn * ticks;
            this.setting.touchTurn = 0;
        } else
            this.setting.rotatePower *= 0.95f;

        // zoom
        if (this.setting.cameraZoom)
            this.setting.zoomPower = this.setting.zoomValue;
        else
            this.setting.zoomPower = 1 + (this.setting.zoomPower - 1) * 0.9f;

        return this.setting;
    }

    private float zoomValue() {
        float distance = (float) Math.sqrt((this.setting.xpos - this.setting.xpos1) * (this.setting.xpos - this.setting.xpos1) + (this.setting.ypos - this.setting.ypos1) * (this.setting.ypos - this.setting.ypos1));
        return distance;
    }
}
