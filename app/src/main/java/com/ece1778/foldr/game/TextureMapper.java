package com.ece1778.foldr.game;

import com.ece1778.foldr.models.Cube;
import com.ece1778.foldr.models.Textures;
import com.threed.jpct.Object3D;

/**
 * Created by Ding on 3/18/2015.
 */
public class TextureMapper {
    public void mapTexture(Cube cube){
        switch(cube.getProperty()){
            case Hydrophilic:
                cube.setTexture(Textures.Blue);
                break;
            case Hydrophobic:
                cube.setTexture(Textures.Red);
                break;
            case Acidic:
                cube.setTexture(Textures.Yellow);
                break;
            case Basic:
                cube.setTexture(Textures.Green);
                break;
            default:
                cube.setTexture(Textures.Grey);
                break;
        }
    }

    public void setGroundTexture(Object3D ground){
        ground.setTexture(Textures.Background);
    }
}
