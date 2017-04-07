package com.example.vic.opengl;

/**
 * Created by vic on 2/20/2017.
 */

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;


import java.io.IOException;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

class GLES20Renderer implements GLSurfaceView.Renderer {

    //private Triangle tri;
    private Cube cube1;
    private Cube cube2;
    private Square sqr;

    private float[] mAccData = new float[3];
    private float[] mMagData = new float[3];

    // mMVPMatrix is an abbreviation for "Model View Projection Matrix"
    private final float[] mMVPMatrix = new float[16];
    private final float[] mProjectionMatrix = new float[16];
    private final float[] mViewMatrix = new float[16];
    private float[] mRotationMatrix = new float[16];

    public float eyeX = 0, eyeY = 0, eyeZ = -5;
    public float centerX = 0, centerY = 0, centerZ = 0;


    public volatile float angle;
/*
    public GLES20Renderer(Camera camera, SurfaceHolder holder) {
        mCamera = camera;
        mHolder = holder;

    }
*/
    @Override
    public void onDrawFrame(GL10 gl) {
        float[] scratch = new float[16];

        //GLES20.glClearColor(.7f, .3f, .1f, .0f);
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Set the camera position (View matrix)
        Matrix.setLookAtM(mViewMatrix, 0, eyeX, eyeY, eyeZ, centerX, centerY, centerZ, 0, 1, 0);
        //Matrix.setLookAtM(mViewMatrix, 0, xangle + 20, yangle + 6, -5, 0, 0, 0, 0, 1, 0);


        // Calculate the projection and view transformation
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectionMatrix, 0, mViewMatrix, 0);
        //System.out.println(mViewMatrix);

        //Matrix.setRotateM(mRotationMatrix, 0, angle, xangle, yangle, zangle -1.0f);
        //mRotationMatrix = mMVPMatrix;


        //Ca sa nu mai fie rotatie
        //Matrix.multiplyMM(scratch, 0, mMVPMatrix, 0, mRotationMatrix, 0);
        scratch = mMVPMatrix;

/*
        System.out.println("===================");
        System.out.println("mViewMatrix");
        for(int i=0;i < 16;i++)
            System.out.print(mViewMatrix[i]+" ");
        System.out.println("");
        System.out.println("===================");
*/
        // Draw shape
        //tri.draw(scratch);
        cube1.draw(scratch);
        cube2.draw(scratch);
        sqr.draw(scratch);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

        GLES20.glViewport(0, 0, width, height);

        float ratio = (float) width / height;

        // this projection matrix is applied to object coordinates
        // in the onDrawFrame() method
        // it is used to determine what objects to be rendered(if they are too far)
        Matrix.frustumM(mProjectionMatrix, 0, -ratio, ratio, -1, 1, 3, 7);

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //tri = new Triangle();
        float sqrcoords1[] = {
                0.5f, 1.5f, 1.5f,   // top left front
                0.5f, 0.5f, 1.5f,   // bottom left front
                1.5f, 0.5f, 1.5f,   // bottom right front
                1.5f, 1.5f, 1.5f,  // top right front

                0.5f, 1.5f, 0.5f,   // top left back
                0.5f, 0.5f, 0.5f,   // bottom left back
                1.5f, 0.5f, 0.5f,   // bottom right back
                1.5f, 1.5f, 0.5f  // top right back
        };
        short drorder[] = { 0, 1, 2, 0, 2, 3, 4, 0, 3, 4, 3, 7, 4, 5, 6, 4, 6, 7, 5, 1, 2, 5, 2, 6, 0, 1, 5, 0, 5, 4, 3, 2, 6, 3, 6, 7 };
        float color1[] = { 0.3f, 0.5f, 0.2f, 1.0f };
        cube1 = new Cube(sqrcoords1, drorder, color1);

        float sqrcoords2[] = {
                -1.5f, -0.5f, -0.5f,   // top left front
                -1.5f, -1.5f, -0.5f,   // bottom left front
                -0.5f, -1.5f, -0.5f,   // bottom right front
                -0.5f, -0.5f, -0.5f,  // top right front

                -1.5f, -0.5f, -1.5f,   // top left back
                -1.5f, -1.5f, -1.5f,   // bottom left back
                -0.5f, -1.5f, -1.5f,   // bottom right back
                -0.5f, -0.5f, -1.5f  // top right back
        };
        float color2[] = { 0.6f, 0.2f, 0.897f, 1.0f };
        cube2 = new Cube(sqrcoords2, drorder, color2);
        sqr = new Square();
    }

    //Method for compiling an OpenGL shader
    public static int loadShader(int type, String shaderCode){

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    //For debuggin purposes
    public static void checkGlError(String glOperation) {
        int error;
        while ((error = GLES20.glGetError()) != GLES20.GL_NO_ERROR) {
            Log.e("GLES20Renderer", glOperation + ": glError " + error);
            throw new RuntimeException(glOperation + ": glError " + error);
        }
    }

    float xangle, yangle, zangle;
    public float getAngle() {
        return angle;
    }


    public void setAngle(float a) {
        angle = a;
    }
    public void setXAngle(float x) {
        xangle = x;
    }
    public void setYAngle(float y) {
        yangle = y;
    }
    public void setZAngle(float a) {
        zangle = a;
    }

    public void setMatrixRotation(float[] mR) {
        mRotationMatrix = mR;
    }

    public float[] getAccData() {
        return mAccData;
    }

    public float[] getMagData() {
        return mMagData;
    }

    public void setAccData(float[] acc) {
        mAccData = acc;
    }

    public void setMagData(float[] mag) {
        mMagData = mag;
    }

}