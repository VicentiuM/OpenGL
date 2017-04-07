package com.example.vic.opengl;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import static com.example.vic.opengl.Triangle.triangleCoords;

/**
 * Created by vic on 2/21/2017.
 */

public class Cube {
    private final FloatBuffer vertexBuffer;
    private final ShortBuffer drawListBuffer;
    private final int shaderProgram;
    private int mPositionHandle;
    private int mColorHandle;
    private int mMVPMatrixHandle;

    // number of coordinates per vertex in this array
    static final int COORDS_PER_VERTEX = 3;

    static float cubeCoords[];
    private final short drawOrder[]; // order to draw vertices

    private final int vertexStride = COORDS_PER_VERTEX * 4; // 4 bytes per vertex

    // Set color with red, green, blue and alpha (opacity) values
    float color[];



    private final String vertexShaderCode =
            // This matrix member variable provides a hook to manipulate
            // the coordinates of the objects that use this vertex shader
            "uniform mat4 uMVPMatrix;" +
                    "attribute vec4 vPosition;" +
                    "void main() {" +
                    // the matrix must be included as a modifier of gl_Position
                    // Note that the uMVPMatrix factor *must be first* in order
                    // for the matrix multiplication product to be correct.
                    "  gl_Position = uMVPMatrix * vPosition;" +
                    "}";

    private final String fragmentShaderCode =
            "precision mediump float;" +
                    "uniform vec4 vColor;" +
                    "void main() {" +
                    "  gl_FragColor = vColor;" +
                    "}";

    // Use to access and set the view transformation
    private int myMVPMatrixHandle;



    /*
    public static int loadShader(int type, String shaderCode){
        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
    */

    //The constructor of the square class
    public Cube(float cbcoords[], short drorder[], float clr[]) {

        cubeCoords = cbcoords;
        drawOrder = drorder;
        color = clr;

        // initialize vertex byte buffer for shape coordinates
        ByteBuffer bb = ByteBuffer.allocateDirect(
                // (number of coordinate values * 4 bytes per float)
                cubeCoords.length * 4);
        // use the device hardware's native byte order
        bb.order(ByteOrder.nativeOrder());

        // create a floating point buffer from the ByteBuffer
        vertexBuffer = bb.asFloatBuffer();
        // add the coordinates to the FloatBuffer
        vertexBuffer.put(cubeCoords);
        // set the buffer to read the first coordinate
        vertexBuffer.position(0);

        // initialize byte buffer for the draw list
        ByteBuffer dlb = ByteBuffer.allocateDirect(
                // (# of coordinate values * 2 bytes per short)
                drawOrder.length * 2);
        dlb.order(ByteOrder.nativeOrder());
        drawListBuffer = dlb.asShortBuffer();
        drawListBuffer.put(drawOrder);
        drawListBuffer.position(0);

        int vertexShader = GLES20Renderer.loadShader(
                GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = GLES20Renderer.loadShader(
                GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        // create empty OpenGL ES Program
        shaderProgram = GLES20.glCreateProgram();

        // add the vertex shader to program
        GLES20.glAttachShader(shaderProgram, vertexShader);

        // add the fragment shader to program
        GLES20.glAttachShader(shaderProgram, fragmentShader);

        // creates OpenGL ES program executables
        GLES20.glLinkProgram(shaderProgram);

    }

    public void draw(float[] myVPMatrix) { // pass in the calculated transformation matrix

        // Add program to OpenGL ES environment
        GLES20.glUseProgram(shaderProgram);

        // get handle to vertex shader's vPosition member
        mPositionHandle = GLES20.glGetAttribLocation(shaderProgram, "vPosition");

        // Enable a handle to the square vertices
        GLES20.glEnableVertexAttribArray(mPositionHandle);

        // Prepare the square coordinate data
        GLES20.glVertexAttribPointer(mPositionHandle, COORDS_PER_VERTEX,
                GLES20.GL_FLOAT, false,
                vertexStride, vertexBuffer);

        // get handle to fragment shader's vColor member
        mColorHandle = GLES20.glGetUniformLocation(shaderProgram, "vColor");

        // Set color for drawing the square
        GLES20.glUniform4fv(mColorHandle, 1, color, 0);

        // get handle to shape's transformation matrix
        myMVPMatrixHandle = GLES20.glGetUniformLocation(shaderProgram, "uMVPMatrix");
        GLES20Renderer.checkGlError("glGetUniformLocation");

        // Pass the projection and view transformation to the shader
        GLES20.glUniformMatrix4fv(myMVPMatrixHandle, 1, false, myVPMatrix, 0);
        GLES20Renderer.checkGlError("glUniformMatrix4fv");

        // Draw the cube
        GLES20.glDrawElements(
                GLES20.GL_TRIANGLES, drawOrder.length,
                GLES20.GL_UNSIGNED_SHORT, drawListBuffer);

        // Disable vertex array
        GLES20.glDisableVertexAttribArray(mPositionHandle);

    }
}
