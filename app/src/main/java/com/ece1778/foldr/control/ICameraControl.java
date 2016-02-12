package com.ece1778.foldr.control;

import com.ece1778.foldr.models.CameraSetting;

/**
 * Created by Ding on 3/29/2015.
 */
public interface ICameraControl {
    public CameraSetting calculateCameraPosition(long ticks);
}
