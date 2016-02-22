package com.marlin.tralp.Views;

import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.MenuItem;


import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.Utils;
import org.opencv.core.CvException;
import org.opencv.core.Mat;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by gabriel on 16-02-22.
 */
public class GravacaoVideo extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private CameraBridgeViewBase opencvCameraView;
    private Mat matRGBA, matGray;
    private File folder;
    private static int fileCounter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(); @TODO Display the view here and buttons
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        /* These deprecated libraries don't affect the project since we used them just to get hardware info*/
        final Camera cam = Camera.open();
        final Camera.Parameters parameters = cam.getParameters();
        final List<Size> supportedVideoSizes = parameters.getSupportedVideoSizes();
        final Size size = supportedVideoSizes.get(0);
        opencvCameraView = new JavaCameraView(this,0);
        opencvCameraView.setMaxFrameSize(size.width, size.height);
        opencvCameraView.setCvCameraViewListener(this);
        opencvCameraView.enableFpsMeter();

        fileCounter = 0;
        Log.d("[onCreate]", "Recording Class has been created");

        setContentView(opencvCameraView);


    }

    //@TODO NEED TO CREATE BUTTONS TO STOP THIS AND Time limit


    public void onCameraViewStarted(int width, int height){
        //@TODO setup file folder and filename counters
        prepareFolder();
    }

    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        matGray = inputFrame.gray();
        matRGBA = inputFrame.rgba();
        fileCounter += 1 ;
        saveFrame(matGray,"defaultName:" + fileCounter + ".png" );

        return matRGBA;
    }

    public void onCameraViewStopped(){
        //@TODO Save and stash all frame files
    }

    private void prepareFolder(){
        folder = new File(Environment.getExternalStorageDirectory()+"/frames");

        /* Checks if external storage is available for read and write */
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Log.d("[prepareFolder]", "Write Error!");
            return;
        }


        if(folder.exists()){
            folder.delete();
        }
        if(folder.mkdir())
            Log.d("[prepareFolder]", "Folder Created!");
        else
            Log.d("[prepareFolder]", "Write Error!");

    }

    private void saveFrame(Mat frameToSave, String nameToSave){
        final String TAG = "[saveFrame]";
        final Mat frame = frameToSave;
        final String filename = nameToSave;

        new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap bmp = null;
                try {
                    bmp = Bitmap.createBitmap(frame.cols(), frame.rows(), Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap(frame, bmp);
                } catch (CvException e) {
                    Log.d(TAG, e.getMessage());
                }
                frame.release(); //?

                FileOutputStream out = null;
                File dest = new File(folder, filename);
                try{
                    out = new FileOutputStream(dest);
                    bmp.compress(Bitmap.CompressFormat.PNG,100,out);
                    out.close();
                    Log.d(TAG, filename+" has been saved");

                }catch (Exception e){
                    e.printStackTrace();
                    Log.d(TAG, e.getMessage());
                }
            }
        }).start();
    }

}
