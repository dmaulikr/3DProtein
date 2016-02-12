package com.ece1778.foldr.engine;

import android.opengl.GLSurfaceView;
import android.widget.Toast;

import com.ece1778.foldr.control.Axis;
import com.ece1778.foldr.control.Direction;
import com.ece1778.foldr.control.ICameraControl;

import com.ece1778.foldr.control.IGameControl;
import com.ece1778.foldr.data.FastaFragment;
import com.ece1778.foldr.data.FastaType;
import com.ece1778.foldr.data.Protein;
import com.ece1778.foldr.game.BlockFactory;
import com.ece1778.foldr.game.GameCode;
import com.ece1778.foldr.game.MediaManager;
import com.ece1778.foldr.game.ScoreCalculator;
import com.ece1778.foldr.models.Block;
import com.ece1778.foldr.models.CameraSetting;
import com.ece1778.foldr.models.Cube;
import com.ece1778.foldr.models.GameGrid;
import com.threed.jpct.Camera;
import com.threed.jpct.Light;
import com.threed.jpct.Object3D;
import com.threed.jpct.SimpleVector;
import com.threed.jpct.World;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


/**
 * Created by Ding on 3/16/2015.
 */
public class Engine implements IGameControl {

    // Components
    private final MediaManager mediaManager;
    private final BlockFactory blockFactory;
    private final Ticker ticker = new Ticker(15);
    private final ScoreCalculator calculator;
    private final FoldrRenderer renderer;
    private final World world;
    private final Camera camera;

    public final float moveStep = 2f;

    // Camera
    private ICameraControl cameraControl;
    private GameCallback gameCallback;

    // Protein
    private Protein protein;
    private Queue<FastaFragment> fragments;
    private Block currentBlock;
    private HashMap<String, Cube> collisionSet;

    public Engine(){
        // Components
        this.mediaManager = MediaManager.getInstance();
        this.blockFactory = BlockFactory.getInstance();
        this.calculator = ScoreCalculator.getInstance();
        this.world = new World();
        this.camera = this.world.getCamera();
        this.renderer = new FoldrRenderer(this);

        initLights();
    }

    public GLSurfaceView.Renderer getRenderer(){
        return this.renderer;
    }

    public World getWorld(){
        return this.world;
    }

    public void startGame(Protein protein){
        this.protein = protein;
        this.fragments = new LinkedList<>();
        this.fragments.addAll(this.protein.getFragments());
        this.collisionSet = new HashMap<>();
        this.nextBlock();
    }

    public void setGameCallback(GameCallback callback){
        this.gameCallback = callback;
    }

    private void initLights(){
        Light light = new Light(world);
        light.setPosition(new SimpleVector(0, -20, -20));
        light.setIntensity(230, 230, 230);
    }

    @Override
    public void moveBlock(Axis axis, Direction direction) {
        if(currentBlock != null){
            SimpleVector position = currentBlock.getCenter();
            switch(axis){
                case X:
                    position.x += direction == Direction.Plus ? moveStep : -moveStep;
                    break;
                case Y:
                    position.y += direction == Direction.Plus ? -moveStep : moveStep;
                    break;
                case Z:
                    position.z += direction == Direction.Plus ? -moveStep : moveStep;
                    break;
            }
            currentBlock.translate(position);
            this.fixBlockPosition(currentBlock);
        }
        this.mediaManager.playMoveBlockSound();
    }

    @Override
    public void rotateBlock(Axis axis) {
        if(currentBlock != null){
            switch(axis){
                case X:
                    currentBlock.rotateX((float)Math.PI/2);
                    break;
                case Y:
                    currentBlock.rotateY((float)Math.PI/2);
                    break;
                case Z:
                    currentBlock.rotateZ((float) Math.PI / 2);
                    break;
            }
            this.fixBlockPosition(currentBlock);
        }
        this.mediaManager.playMoveBlockSound();
    }
    @Override
    public void nextBlock(){
        // Check for collsion
        if(this.currentBlock != null){
            if(hasCollision(this.currentBlock)){
                this.mediaManager.playBlockedSound();
                if(this.gameCallback != null){
                    this.gameCallback.showBlockedToast();
                }
                return;
            }else{
                // Calculate Score
                this.calculator.calculate(this.collisionSet, this.currentBlock);
                // Add to collision map
                this.addToCollistionSet(this.currentBlock);
                // Calculate compactness score
                this.calculator.calculateCompatScore(this.collisionSet);
            }
        }

        if(this.gameCallback != null) this.gameCallback.updateScoreAndMoves(this.calculator.getScore(), this.fragments.size());

        FastaFragment fragment = this.fragments.poll();

        if(fragment == null && this.gameCallback != null){
            this.gameCallback.gameFinished(GameCode.Pass, this.calculator.getScore());
            return;
        }

        this.currentBlock = fragment.getType() == FastaType.AlphaHelix ?
                this.blockFactory.buildAlphaHelix(fragment.getSequence()) :
                this.blockFactory.buildBetaSheet(fragment.getSequence());

        this.putBlockToWorld(this.currentBlock);
        this.findValidSpace(this.currentBlock);

        this.mediaManager.playNextBlockSound();
    }

    @Override
    public void showMenu() {
        if(this.gameCallback != null) this.gameCallback.showMenu();
    }

    @Override
    public void showHelp() {
        if(this.gameCallback != null) gameCallback.showHelp();
    }

    public void updateCameraPosition(){
        long tick = this.ticker.getTicks();
        CameraSetting setting = this.cameraControl != null ?
                this.cameraControl.calculateCameraPosition(tick) : new CameraSetting();
        this.setCameraPosition(setting);
        this.camera.lookAt(setting.lookAt);
    }

    private void setCameraPosition(CameraSetting setting) {
        setting.cameraAngle += setting.rotatePower;
        float x = (float) Math.cos(setting.cameraAngle);
        float z = (float) Math.sin(setting.cameraAngle);
        SimpleVector camPosition = SimpleVector.create(x, setting.cameraHeight, z);
        float newDistance = setting.cameraDistance * setting.zoomPower;
        if (newDistance > 10 && newDistance < 60)
            setting.cameraDistance = newDistance;
        camPosition.scalarMul(setting.cameraDistance);
        this.camera.setPosition(camPosition);
    }

    private void putBlockToWorld(Block block){
        block.addToWorld(this.world);
    }

    public void setCameraControl(ICameraControl cameraControl){
        this.cameraControl = cameraControl;
    }

    private boolean hasCollision(Block block){
        List<Cube> cubes = block.getCubes();
        for(Cube cube: cubes){
            if(this.collisionSet.containsKey(cube.getHash())){
                return true;
            }
        }

        return false;
    }

    private void findValidSpace(Block block){
        fixBlockPosition(block);

        while(this.hasCollision(block)){
            block.translate(new SimpleVector(moveStep, -moveStep,-moveStep));
        }
    }

    private void fixBlockPosition(Block block){
        SimpleVector center = block.getCubes().get(0).getTransformedCenter();
        SimpleVector translation = new SimpleVector();
        if(Math.round(center.x) % 2 != 0) translation.x += 1;
        if(Math.round(center.y) % 2 != 0) translation.y -=1;
        if(Math.round(center.z) % 2 != 0) translation.z -=1;
        block.translate(translation);
    }

    private void addToCollistionSet(Block block){
        List<Cube> cubes = block.getCubes();
        for(Cube cube : cubes){
            String hash = cube.getHash();
            this.collisionSet.put(hash, cube);
        }
    }
}
