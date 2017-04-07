package com.example.vic.opengl;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by vic on 2/20/2017.
 */

public class GLTriangleEx {

    private float vertices[] = {
        0f, 1f,     //p0
        1f, -1f,    //p1
        -1f, -1f    //p2
    };

    private FloatBuffer vertBuff;

    private short[] pIndex = { 0, 1, 2};

    private ShortBuffer pBuff;

    public GLTriangleEx() {
        //fiecare vertice are nevoie de 4 bytes
        ByteBuffer bBuff = ByteBuffer.allocateDirect(vertices.length * 4);
        bBuff.order(ByteOrder.nativeOrder());

        vertBuff = bBuff.asFloatBuffer();
        vertBuff.put(vertices);
        vertBuff.position(0);

        //pentru pointeri fiind short are nevoie de 2 bytes
        ByteBuffer pbBuff = ByteBuffer.allocateDirect(pIndex.length * 2);
        pbBuff.order(ByteOrder.nativeOrder());
        pBuff = pbBuff.asShortBuffer();
        pBuff.put(pIndex);
        pBuff.position(0);
    }

    /*
              0


        2            1
     */

    public void draw(GL10 gl) {
        //In sens ceasornic ca incepe de la 0 si se duce la 1 apoi la 2
        gl.glFrontFace(GL10.GL_CW);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        //2 ca e 2D
        //float ca e vertexul de tip float
        //stride e 0 ca nu mai adaugam informatii in plus la obiect
        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertBuff);
        //pentru indecsi
        gl.glDrawElements(GL10.GL_TRIANGLES, pIndex.length, GL10.GL_UNSIGNED_SHORT, pBuff);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}
