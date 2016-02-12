package com.ece1778.foldr.engine;

import android.opengl.GLSurfaceView;

import com.threed.jpct.FrameBuffer;
import com.threed.jpct.RGBColor;
import com.threed.jpct.World;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Ding on 3/16/2015.
 */
public class FoldrRenderer implements GLSurfaceView.Renderer {

    private final Engine engine;
    private FrameBuffer buffer;
    private RGBColor backColor;

    public FoldrRenderer(Engine engine){
        this.backColor = new RGBColor(169,208,245);
        this.engine = engine;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if(this.buffer != null){
            this.buffer.dispose();
        }

        this.buffer = new FrameBuffer(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // re-calculate camera position
        this.engine.updateCameraPosition();

        // render scene to screen
        this.buffer.clear(this.backColor);
        World world = this.engine.getWorld();
        world.renderScene(this.buffer);
        world.draw(this.buffer);
        this.buffer.display();
    }
}
