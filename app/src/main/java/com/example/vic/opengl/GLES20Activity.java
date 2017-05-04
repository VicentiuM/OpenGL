package com.example.vic.opengl;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by vic on 2/20/2017.
 */

public class GLES20Activity extends Activity{

    //Suprafata OpenGL
    GLES20SurfaceView myGLView;

    //Suprafata pentru Camera
    CameraView cameraView;


    Button bCreate, bCube, bSquare, bxl, bxr, byl, byr, bzl, bzr, bxc, byc, bzc, bAccept;




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


            FrameLayout ui = new FrameLayout(this);

            //Creez un buton ce va adauga obiecte(in acest moment cuburi si dreptunghiuri)
            LinearLayout ll = new LinearLayout(this);
            bCreate = new Button(this);
            bCreate.setText("Add Object");
            bCreate.setOnClickListener(buttonListener);

            bCube = new Button(this);
            bCube.setText("Cube");
            bCube.setVisibility(View.INVISIBLE);
            bCube.setOnClickListener(cubeListener);

            bSquare = new Button(this);
            bSquare.setText("Square");
            bSquare.setVisibility(View.INVISIBLE);

            ll.addView(bCreate);
            ll.addView(bCube);
            ll.addView(bSquare);
            ll.setGravity(Gravity.TOP |  Gravity.RIGHT);
            ll.setOrientation(LinearLayout.VERTICAL);
            ui.addView(ll);


            LinearLayout llx = new LinearLayout(this);
            bxl = new Button(this);
            bxl.setText("<");
            bxl.setOnClickListener(xlListener);
            bxr = new Button(this);
            bxr.setText(">");
            bxr.setOnClickListener(xrListener);
            bxc = new Button(this);
            bxc.setText("X");
            bxc.setClickable(false);
            llx.addView(bxl);
            llx.addView(bxc);
            llx.addView(bxr);
            llx.setGravity(Gravity.TOP |  Gravity.LEFT);
            ui.addView(llx);

            LinearLayout lly = new LinearLayout(this);
            byl = new Button(this);
            byl.setText("<");
            byl.setOnClickListener(ylListener);
            byr = new Button(this);
            byr.setText(">");
            byr.setOnClickListener(yrListener);
            byc = new Button(this);
            byc.setText("Y");
            byc.setClickable(false);
            lly.addView(byl);
            lly.addView(byc);
            lly.addView(byr);
            lly.setGravity(Gravity.CENTER |  Gravity.LEFT);
            ui.addView(lly);

            LinearLayout llz = new LinearLayout(this);
            bzl = new Button(this);
            bzl.setText("<");
            bzl.setOnClickListener(zlListener);
            bzr = new Button(this);
            bzr.setText(">");
            bzr.setOnClickListener(zrListener);
            bzc = new Button(this);
            bzc.setText("Z");
            bzc.setClickable(false);
            llz.addView(bzl);
            llz.addView(bzc);
            llz.addView(bzr);
            llz.setGravity(Gravity.BOTTOM |  Gravity.LEFT);
            ui.addView(llz);

            LinearLayout lla = new LinearLayout(this);
            bAccept = new Button(this);
            bAccept.setText("Accept");
            bAccept.setOnClickListener(accListener);
            lla.addView(bAccept);
            lla.setGravity(Gravity.TOP | Gravity.CENTER);
            ui.addView(lla);

            this.addContentView(ui,
                    new ActionBar.LayoutParams(ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.FILL_PARENT));


            bxl.setVisibility(View.INVISIBLE);
            bxc.setVisibility(View.INVISIBLE);
            bxr.setVisibility(View.INVISIBLE);

            byl.setVisibility(View.INVISIBLE);
            byc.setVisibility(View.INVISIBLE);
            byr.setVisibility(View.INVISIBLE);

            bzl.setVisibility(View.INVISIBLE);
            bzc.setVisibility(View.INVISIBLE);
            bzr.setVisibility(View.INVISIBLE);

            bAccept.setVisibility(View.INVISIBLE);


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

    //Listenerurile pentru butoane
    View.OnClickListener buttonListener = new View.OnClickListener() {
        boolean clicked = false;

        @Override
        public void onClick(View v) {
            if (!clicked) {
                clicked = true;
                System.out.println("AI APASAT BUTONUL");

                bCube.setVisibility(View.VISIBLE);
                bSquare.setVisibility(View.VISIBLE);
            }
            else {
                clicked = false;
                bCube.setVisibility(View.INVISIBLE);
                bSquare.setVisibility(View.INVISIBLE);
            }
        }
    };

    View.OnClickListener cubeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            myGLView.addCube();

            bxl.setVisibility(View.VISIBLE);
            bxc.setVisibility(View.VISIBLE);
            bxr.setVisibility(View.VISIBLE);

            byl.setVisibility(View.VISIBLE);
            byc.setVisibility(View.VISIBLE);
            byr.setVisibility(View.VISIBLE);

            bzl.setVisibility(View.VISIBLE);
            bzc.setVisibility(View.VISIBLE);
            bzr.setVisibility(View.VISIBLE);

            bAccept.setVisibility(View.VISIBLE);
        }
    };

    View.OnClickListener xlListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            myGLView.moveXLeft();
        }
    };

    View.OnClickListener xrListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            myGLView.moveXRight();
        }
    };

    View.OnClickListener ylListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            myGLView.moveYLeft();
        }
    };

    View.OnClickListener yrListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            myGLView.moveYRight();
        }
    };

    View.OnClickListener zlListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            myGLView.moveZLeft();
        }
    };

    View.OnClickListener zrListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            myGLView.moveZRight();
        }
    };

    View.OnClickListener accListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            bxl.setVisibility(View.INVISIBLE);
            bxc.setVisibility(View.INVISIBLE);
            bxr.setVisibility(View.INVISIBLE);

            byl.setVisibility(View.INVISIBLE);
            byc.setVisibility(View.INVISIBLE);
            byr.setVisibility(View.INVISIBLE);

            bzl.setVisibility(View.INVISIBLE);
            bzc.setVisibility(View.INVISIBLE);
            bzr.setVisibility(View.INVISIBLE);

            bAccept.setVisibility(View.INVISIBLE);

            myGLView.confirmObject();
        }
    };

}
