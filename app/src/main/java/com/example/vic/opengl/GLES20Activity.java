package com.example.vic.opengl;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by vic on 2/20/2017.
 */

public class GLES20Activity extends Activity{

    //Suprafata OpenGL
    GLSurfaceView myGLView;

    //Suprafata pentru Camera
    CameraView cameraView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Stergem bara de sus
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        //Pentru a face fullscreen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (hasGLES20()) {
            System.out.println("E COMPATIBIL");
            //Cream SurfaceView-ul pentru OpenGl
            myGLView = new GLES20SurfaceView(this);
            //Il adaugam sa afiseze continutul
            this.setContentView(myGLView);
            //Cream CameraView-ul pentru a se ocupa de afisarea Camerei
            cameraView = new CameraView(this);
            //Adaugam sa afiseze continutul in spatele celui pentru OpenGl
            this.addContentView(cameraView, new WindowManager.LayoutParams( WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT ) );

        } else {
            // Telefonul nu e compatibil cu opengl 2.0
            System.out.println("NU E COMPATIBIL");
        }
    }

    //Verificam daca dispozitivul suporta opengl 2.0
    private boolean hasGLES20() {
        ActivityManager am = (ActivityManager)
                getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo info = am.getDeviceConfigurationInfo();
        return info.reqGlEsVersion >= 0x20000;
    }


    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("========RESUME=========");
        myGLView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("========PAUSE=========");
        myGLView.onPause();
    }


}
