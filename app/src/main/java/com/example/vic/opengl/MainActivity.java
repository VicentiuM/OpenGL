package com.example.vic.opengl;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    //public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        startActivity(intent);

    }

    /** Called when the user clicks the OpenGl 1.0 button */
    public void opengl(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, GLExample.class);
        startActivity(intent);

    }

    /** Called when the user clicks the OpenGl 2.0 button */
    public void opengl2(View view) {
        // Do something in response to button
        Intent intent = new Intent(this, GLES20Activity.class);
        startActivity(intent);

    }
}
