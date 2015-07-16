package com.jaigaur.demobrowser;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by Hitesh Verma on 6/16/2015.
 */
public class GLCubeEx extends Activity {

    GLSurfaceView ourSurface; //for graphical Purpose
    MediaPlayer MySong;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ourSurface = new GLSurfaceView(this); //setting up the surface view
        ourSurface.setRenderer(new GLCubeRendererEx()); //setting up the Renderer
        setContentView(ourSurface);
        starttheMainActivity();
        startTheToast();
    }


    private void startTheToast() {
        Toast toast = new Toast(GLCubeEx.this);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 0);


        LayoutInflater inflater = getLayoutInflater();
        View apperance = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.layout)); //1 )param is the layout file 2) is the view group
        toast.setView(apperance);
        toast.show();
    }


    private void starttheMainActivity() {
        MySong = MediaPlayer.create(GLCubeEx.this, R.drawable.woosh);
        //setting the preferences to turn off or turn on the welcome music by the prefrences
        SharedPreferences getPref = PreferenceManager.getDefaultSharedPreferences(getBaseContext()); //getting thee data from shared preference
        boolean music = getPref.getBoolean("checkbox", true);
        //first param is to check the key in the pref.xml
        //second param is to set the default value .. when it is not set by the user
        if (music == true) {
            MySong.start();
        }
        Log.d("HIT", "SPLASH CLASS");
        Thread hit = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent OpenMainActivity = new Intent(GLCubeEx.this,MainActivity.class);
                    startActivity(OpenMainActivity);
                }
            }

        };
        hit.start();


    }





    @Override
    protected void onPause() {
        super.onPause();
        ourSurface.onPause(); //pause our surfaceview

        MySong.release();
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ourSurface.onResume(); //resume our SurfaceView
    }
}
