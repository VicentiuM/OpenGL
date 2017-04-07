package com.example.vic.opengl;

import android.opengl.GLSurfaceView;
import android.opengl.GLU;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by vic on 2/20/2017.
 */

public class GLRendererEx implements GLSurfaceView.Renderer {

    private GLTriangleEx tri;

    //Constructor
    public GLRendererEx() {
        //am initializat triunghiul
        tri = new GLTriangleEx();
    }

    //Pentru a crea suprafata initiala
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        //scoatem ce nu avem nevoie pentru a creste performanta
        gl.glDisable(GL10.GL_DITHER);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

        gl.glClearColor(.4f, .2f, .6f, 1f);
        gl.glClearDepthf(1f);
    }

    //Ce se deseneaza la fiecare frame
    @Override
    public void onDrawFrame(GL10 gl) {
        gl.glDisable(GL10.GL_DITHER);
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        //la ce obiect ne uitam si unde e camera
        GLU.gluLookAt(gl, 0, 0, -5, 0, 0, 0, 0, 2, 0);

        tri.draw(gl);
    }

    //Cand se schimba de la portrait la landscape
    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        //Setam viewportul
        gl.glViewport(0, 0, width, height);
        //pentru cand schimbam din landscape to portrait
        float ratio = (float) width/height;
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        //pentru a scoate obiectele de prea departe sa nu mai fie randate
        gl.glFrustumf(-ratio, ratio, -1, .5f, 1, 25);
    }
}
