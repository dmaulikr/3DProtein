package com.ece1778.foldr.game;

import com.ece1778.foldr.data.AminoAcidProperty;

import com.ece1778.foldr.data.FastaType;
import com.ece1778.foldr.models.Block;
import com.ece1778.foldr.models.Cube;
import com.ece1778.foldr.models.Textures;
import com.ece1778.foldr.utils.AminoAcidPropertyMapper;
import com.threed.jpct.Object3D;
import com.threed.jpct.Primitives;
import com.threed.jpct.util.ExtendedPrimitives;

/**
 * Created by Ding on 3/16/2015.
 */
public class BlockFactory {
    private final AminoAcidPropertyMapper aminoAcidPropertyMapper;
    private static BlockFactory blockFactory;

    private static final float CubeEdgeLength = 2.0f;
    private static final int Unit = 1;
    private static final int[] AlphaHelixPattern = new int[]{ 0,6,11,12,17,22,27,29,34};
    private static final int[] BetaSheetPattern = new int[]{1,2};
    private static final Object3D Mesh = ExtendedPrimitives.createCube(CubeEdgeLength);

    private BlockFactory(){
        this.aminoAcidPropertyMapper = new AminoAcidPropertyMapper();
    }

    public synchronized static BlockFactory getInstance(){
        if(blockFactory == null){
            blockFactory = new BlockFactory();
        }

        return blockFactory;
    }

    public Block buildAlphaHelix(CharSequence sequence){
        Block block = new Block(FastaType.AlphaHelix);
        int size = sequence.length() % 2 == 1 ? sequence.length() + 1 : sequence.length();

        Cube[] cubes  = new Cube[size * 4];

        for(int j = 0; j < sequence.length(); j++){
            AminoAcidProperty property = this.aminoAcidPropertyMapper.getProperty(sequence.charAt(j));
            Cube cube = new Cube(Mesh, property);

            // calculate index position
            int baseIndex =  (j / AlphaHelixPattern.length) * (4 * AlphaHelixPattern.length);
            int jumpIndex = AlphaHelixPattern[j % AlphaHelixPattern.length];
            cubes[baseIndex + jumpIndex] = cube;
        }

        // fill remaining cubes and build textures
        for(int i = 0; i < cubes.length; i ++){
            Cube cube = cubes[i];
            if(cube == null){
                cube = new Cube(Mesh, AminoAcidProperty.None);
                cubes[i] = cube;
            }
            this.mapTexture(cube);

            // set cube position
            float x = (i % 4 <= 1) ? Unit : -Unit;
            float y = i / 4 * CubeEdgeLength - size + 1;
            float z = i % 2 == 0 ? Unit : -Unit;
            cube.translate(x,y,z);

            cube.build();
        }

        block.addCubes(cubes);
        return block;
    }

    public Block buildBetaSheet(CharSequence sequence){
        Block block = new Block(FastaType.BetaSheet);
        int size = sequence.length() % 2 == 1 ? sequence.length() + 1 : sequence.length();
        Cube[] cubes = new Cube[size * 2];
        for(int j=0; j < sequence.length(); j++){
            AminoAcidProperty property = this.aminoAcidPropertyMapper.getProperty(sequence.charAt(j));
            Cube cube = new Cube(Mesh, property);
            // calculate index position
            int baseIndex =  (j / BetaSheetPattern.length) * (2 * BetaSheetPattern.length);
            int jumpIndex = BetaSheetPattern[j % BetaSheetPattern.length];
            cubes[baseIndex + jumpIndex] = cube;
        }

        // fill remaining cubes and build textures
        for(int i = 0; i < cubes.length; i ++){
            Cube cube = cubes[i];
            if(cube == null){
                cube = new Cube(Mesh, AminoAcidProperty.None);
                cubes[i] = cube;
            }
            this.mapTexture(cube);

            // set cube position
            float x = i / 2 * CubeEdgeLength - size;
            float y = 1;
            float z = i % 2 == 0 ? Unit : -Unit;
            cube.translate(x,y,z);

            cube.build();
        }
        block.translate(CubeEdgeLength/2,0,CubeEdgeLength/2);
        block.addCubes(cubes);
        return block;
    }

    public Object3D buildGround(int size){
        Object3D ground = Primitives.getPlane(size, size);
        ground.rotateX((float) Math.PI / 2);
        ground.setTexture(Textures.Background);
        ground.build();
        return ground;
    }

    private void mapTexture(Cube cube){
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
}
