package com.example.vic.opengl;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by vic on 4/6/2017.
 */

public class CameraView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mHolder;
    private Camera mCamera;

    public CameraView(Context context) {
        super( context );
/*
        mCamera = camera;
        mCamera.setDisplayOrientation(90);

        //get the holder and set this class as the callback, so we can get camera data here
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);
*/
        getHolder().addCallback( this );
        getHolder().setType( SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS );
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mCamera = Camera.open();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // This method is called when the surface changes, e.g. when it's size is set.
        // We use the opportunity to initialize the camera preview display dimensions.
        Camera.Parameters p = mCamera.getParameters();
        p.setPreviewSize( width, height );
        mCamera.setParameters( p );

        // We also assign the preview display to this surface...
        try {
            mCamera.setPreviewDisplay( holder );
        } catch( IOException e ) {
            e.printStackTrace();
        }
        // ...and start previewing. From now on, the camera keeps pushing preview
        // images to the surface.
        mCamera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if (mCamera!=null) {
            mCamera.stopPreview();
            mCamera.setPreviewCallback(null);

            mCamera.release();
            mCamera = null;
        }
    }
}
