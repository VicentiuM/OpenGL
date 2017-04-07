package com.example.vic.opengl;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;


public class GLExample extends Activity {

    GLSurfaceView ourSurface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Punem contextul clasei in interior
        ourSurface = new GLSurfaceView(this);
        ourSurface.setRenderer(new GLRendererEx());
        setContentView(ourSurface);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ourSurface.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ourSurface.onResume();
    }
}
