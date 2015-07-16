package com.jaigaur.demobrowser;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.os.SystemClock;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Hitesh Verma on 6/16/2015.
 */
public class GLCubeRendererEx implements GLSurfaceView.Renderer {
    private GLCube cube ;
    public GLCubeRendererEx(){
        cube = new GLCube();

    }

    //when the Surface is Created this is called to set up the basic
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig eglconfig) {
        gl.glDisable(GL10.GL_DITHER); //speed up the processing
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT ,GL10.GL_FASTEST); //speed up the processing

        gl.glClearColor(.3f, 0f, 1f, 1f);  //RGB and Alhpa(Transparaency) Values (0f to 10f )
        //this tells the color to play with

        gl.glClearDepthf(1f);  //set the Depth
    }

    //when the canvas is changed (refresh over and over)
    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glDisable(GL10.GL_DITHER); //speed up the processing


//this gona store the color on the screen until the activity isclosed
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);  //handle our color and its depth
        //Drawing the Trianlge
        gl.glMatrixMode(GL10.GL_MODELVIEW);        //Tell our camera to look at a specific point
        gl.glLoadIdentity();
        GLU.gluLookAt(gl, 0, 0, -5, 0, 0, 0, 0, 2, 0); //cahange the view of the camera
        //1)it the view to be seen ,2-3-4) are the distance of the camera form the view


        //Roatating acc to time
        long time = SystemClock.uptimeMillis()%4000L;
        float angle = .090f*((int)time); //angle change acc to time

        gl.glRotatef(angle,1,1,2); //1)angle , 2)x-axis to affect(1) , no effect(0)
        //3)yaxis toaffect(1)or tonoeffect(0)....same
        cube.draw(gl);


    }


    //when we cahnge the Orientation of the screen it will gona change the view acc to it
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);  //whole activity Screen
        float ratio = (float) width/height; //good for diff Orientations
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 25);  //we will be able to see in this area only


    }
}
