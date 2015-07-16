package com.jaigaur.demobrowser;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Hitesh Verma on 6/16/2015.
 */
public class GLCube {
    private float vertices[] =
            {
                    1, 1, -1, //The point 0 (topfrontRight)
                    1, -1, -1, //the point1   (bottomFrontRight)
                    -1, -1, -1, //the point 2 (bottomFrontLeft)
                    -1, 1, -1,// the point  3 (FronttopLeft)
                    1, 1, 1, //The point 0 (topBacktRight)
                    1, -1, 1, //the point1   (bottombackRight)
                    -1, -1, 1, //the point 2 (bottomBackLeft)
                    -1, 1, 1,// the point  3 (FrontBackLeft)
//float is4 byte
            };

    private float rgbaVals[] =
            {1, 1, 1, .5f, // RGBA value of first vertices
                    1f, 0, 1f, 1f, //for second
                    1f, 1f, 1f, 1f, //for third
                    1f, 1f, 1f,
                    1f, 1f, 1f,
                    1f, 1f, 1f,
                    1f, 1f, 1f,
                    1f, 1f, 1f,

            };

    private FloatBuffer vertBuff, colorBuff;

    private short[] pIndex = { //check video 180
            3, 4, 0, 0, 4, 1, 3, 0, 1,  // first piece of triangle
            3, 7, 4, 7, 6, 4, 7, 3, 6,
            3, 1, 2, 1, 6, 2, 6, 3, 2,
            1, 4, 5, 5, 6, 1, 6, 5, 4}; //short is 2 byte


    private ShortBuffer pBuff;

    public GLCube() {
        // we gona create a vertex buffer and byte buffer(it tells us the memory (bytes) we are using)
        ByteBuffer bBuff = ByteBuffer.allocateDirect(vertices.length * 4);  //get the total no of byte used which is 24 in this case
        bBuff.order(ByteOrder.nativeOrder());  //setting up the order
        vertBuff = bBuff.asFloatBuffer();
        vertBuff.put(vertices);
        vertBuff.position(0);   ///start from which position

        ByteBuffer pbBuff = ByteBuffer.allocateDirect(pIndex.length * 2);
        pbBuff.order(ByteOrder.nativeOrder());
        pBuff = pbBuff.asShortBuffer();
        pBuff.put(pIndex);
        pBuff.position(0);

        //for color
        ByteBuffer cBuff = ByteBuffer.allocateDirect(rgbaVals.length * 4);
        cBuff.order(ByteOrder.nativeOrder());
        colorBuff = cBuff.asFloatBuffer();
        colorBuff.put(rgbaVals);
        colorBuff.position(0);
    }

    public void draw(GL10 gl) {
        //define how to draw a The Triangle
        gl.glFrontFace(GL10.GL_CW);  //Clockwise(GL_CW)
        gl.glEnable(GL10.GL_CULL_FACE); //remove the back face
        gl.glCullFace(GL10.GL_BACK);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);  //allow us to use a vertex array

        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);  //for color


        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertBuff);  //param 1) Size(3= 3D) , 2)type(float) , 3)stride (0) ,4)pointer

        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuff); //1)size dimenesions(4 = RGBA) ,2)type(FLOAT) ,3)stride , 4)pointer

        gl.glDrawElements(GL10.GL_TRIANGLES, pIndex.length, GL10.GL_UNSIGNED_SHORT, pBuff); //param 1)mode (going to connect our Index ,2)cout (tells how many Index we have
        //3)type (short) , 4)indices


        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY); // we need also to disable it if we have enabled it
        gl.glDisable(GL10.GL_CULL_FACE);
    }

}
