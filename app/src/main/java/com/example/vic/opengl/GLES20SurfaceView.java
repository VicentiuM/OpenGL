package com.example.vic.opengl;

import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.opengl.GLSurfaceView;
import android.util.Log;

/**
 * Created by vic on 2/28/2017.
 */

public class GLES20SurfaceView extends GLSurfaceView implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private Sensor mAccSensor;
    private Sensor mMagSensor;

    private final GLES20Renderer myRenderer;

    private float[] mAccData = new float[3];
    private float[] mMagData = new float[3];
    private float[] mR = new float[16];
    private float[] mI = new float[16];
    private float[] mOrientation = new float[3];
    private int mCount = 0;

    public GLES20SurfaceView(Context context) {
        super(context);

        // Create an OpenGL ES 2.0 context
        setEGLContextClientVersion(2);

        //myRenderer = new GLES20Renderer(mCamera, mHolder);
        myRenderer = new GLES20Renderer();

        //Set background image
        setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        getHolder().setFormat(PixelFormat.TRANSLUCENT);

        setZOrderOnTop(true);

        // Set the Renderer for drawing on the GLSurfaceView
        setRenderer(myRenderer);

        mSensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
        //mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        mAccSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        mMagSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mSensorManager.registerListener(this, mAccSensor, SensorManager.SENSOR_DELAY_GAME);
        mSensorManager.registerListener(this, mMagSensor, SensorManager.SENSOR_DELAY_GAME);

        //mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);



    }

    private final float TOUCH_SCALE_FACTOR = 180.0f / 320;
    private float mPreviousX;
    private float mPreviousY;

    private float sensorX;
    private float sensorY;
    private float sensorZ;


/*
    public void onSensorChanged(SensorEvent event) {
        sensorX = event.values[0];
        sensorY = event.values[1];
        sensorZ = event.values[2];

        float dx = sensorX * 100 - mPreviousX;
        float dy = sensorY * 100 - mPreviousY;

        myRenderer.setXAngle(sensorX);
        myRenderer.setYAngle(sensorY);
        myRenderer.setZAngle(sensorZ);

        myRenderer.setAngle(
                myRenderer.getAngle() +
                        ((dx + dy)));
        requestRender();

        mPreviousX = sensorX;
        mPreviousY = sensorY;
    }
*/

    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();
        float[] data;
        if (type == Sensor.TYPE_GYROSCOPE) {
            data = mAccData;
            //mMagData = myRenderer.getMagData();
            //myRenderer.setAccData(data);
        } else if (type == Sensor.TYPE_MAGNETIC_FIELD) {
            data = mMagData;
            //mAccData = myRenderer.getAccData();
            //myRenderer.setMagData(data);
        } else {
            Log.d("SurfaceView", "Nu am putut accessa acceloremetrul si magnetic fieldul");
            return;
        }

        for (int i=0 ; i<3 ; i++)
            data[i] = event.values[i];


        myRenderer.setXAngle(data[0]);
        myRenderer.setYAngle(data[1]);
        myRenderer.setZAngle(data[2]);

        float v[] = {myRenderer.centerX - myRenderer.eyeX,myRenderer.centerY - myRenderer.eyeY, myRenderer.centerY - myRenderer.eyeZ};

        //myRenderer.eyeX += v[0] * data[0];
        //myRenderer.eyeZ += v[2] * data[2];

        //myRenderer.centerX +=  v[0] * data[0];
        //myRenderer.centerZ +=  v[2] * data[2];


        SensorManager.getRotationMatrix(mR, mI, mAccData, mMagData);

        SensorManager.getOrientation(mR, mOrientation);
        float incl = SensorManager.getInclination(mI);

        myRenderer.setMatrixRotation(mR);

        if (mCount++ > 50) {
            final float rad2deg = (float)(180.0f/Math.PI);
            mCount = 0;
            Log.d("Sensor", "xA = " + mAccData[0] + " yA = " + mAccData[1] + " zA = " + mAccData[2]);
            Log.d("Sensor", "xM = " + mMagData[0] + " yM = " + mMagData[1] + " zM = " + mMagData[2]);
            Log.d("Compass", "yaw: " + (int)(mOrientation[0]*rad2deg) +
                    "  pitch: " + (int)(mOrientation[1]*rad2deg) +
                    "  roll: " + (int)(mOrientation[2]*rad2deg) +
                    "  incl: " + (int)(incl*rad2deg)
            );
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    public void addCube() {
        System.out.println("====ADAUG CUBUL====\n");
        myRenderer.addCube();

    }

    public void moveXLeft() { myRenderer.moveXLeft(); }
    public void moveXRight() { myRenderer.moveXRight (); }
    public void moveYLeft() { myRenderer.moveYLeft(); }
    public void moveYRight() { myRenderer.moveYRight (); }
    public void moveZLeft() { myRenderer.moveZLeft(); }
    public void moveZRight() { myRenderer.moveZRight (); }

    public void confirmObject() {
        myRenderer.confirmObject();
    }
}