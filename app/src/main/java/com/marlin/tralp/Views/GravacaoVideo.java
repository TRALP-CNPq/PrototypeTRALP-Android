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


import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.CvException;
import org.opencv.core.Mat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by gabriel on 16-02-22.
 */
public class GravacaoVideo extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    private CameraBridgeViewBase opencvCameraView;
    private Mat matRGBA, matGray;
    private File folder;
    private static int fileCounter;
    private List<Mat> frameBuffer;


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
        frameBuffer = new ArrayList<Mat>();

        fileCounter = 0;
        Log.d("[onCreate]", "Recording Class has been created");

        setContentView(opencvCameraView);


    }

    //@TODO NEED TO CREATE BUTTONS TO STOP THIS AND Time limit


    private BaseLoaderCallback openCVLoaderCallback = new BaseLoaderCallback(this){

        @Override
        public void onManagerConnected(int status){
            super.onManagerConnected(status);
            if(status == LoaderCallbackInterface.SUCCESS){
                Log.i("OpenCVLoader", "OpenCV Loaded Successfully");
                //Load OpenCV Resources now
                opencvCameraView.enableView();
            }
        }
    };

    @Override
    public void onResume(){
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, openCVLoaderCallback);
    }

    @Override
    public void onCameraViewStarted(int width, int height){
        //@TODO setup file folder and filename counters
        prepareFolder();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(opencvCameraView != null){
            opencvCameraView.disableView();
        }
        Log.d("Exiting","Trying to save");
        //@TODO Save and stash all frame files (Needed?)
        Iterator itr = frameBuffer.iterator();
        while(itr.hasNext()) {
            Mat oneframe = (Mat)itr.next();
            fileCounter += 1 ;
            saveFrame(oneframe,"defaultName:" + fileCounter + ".png" );
        }
        frameBuffer.clear();
    }

    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        matGray = inputFrame.gray();
        matRGBA = inputFrame.rgba();
        //fileCounter += 1 ;
        //saveFrame(matGray,"defaultName:" + fileCounter + ".png" );
        saveFrameToBuffer(matGray);
        return matRGBA;
    }
    @Override
    public void onCameraViewStopped(){
            Log.d("onCameraViewStopped", "Saindo");
    }

    private void prepareFolder(){
        folder = new File(Environment.getExternalStorageDirectory()+"/frames");

        /* Checks if external storage is available for read and write */
        String state = Environment.getExternalStorageState();
        if (!Environment.MEDIA_MOUNTED.equals(state)) {
            Log.d("[prepareFolder]", "Write Error!" + state.toString());
            return;
        }


        if(folder.exists()){
            deleteRecursive(folder);
            //folder = new File(Environment.getExternalStorageDirectory()+"/frames");
        }
        if(folder.mkdir())
            Log.d("[prepareFolder]", "Folder Created!");
        else {
            Log.d("[prepareFolder]", "Write Error!");
            //throw new ExceptionInInitializerError("Folder not Created");
        }
    }
    void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);

        fileOrDirectory.delete();
    }
    private void saveFrameToBuffer(Mat frameToSave){
        frameBuffer.add(frameToSave);
    }
    private void saveFrame(Mat frameToSave, String nameToSave){
        final String TAG = "[saveFrame]";
        final Mat frame = frameToSave;
        final String filename = nameToSave;

        //new Thread(new Runnable() {
           // @Override
           // public void run() {
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
                Log.d("FullPath", dest.toString());
                try{
                    out = new FileOutputStream(dest);
                    bmp.compress(Bitmap.CompressFormat.PNG,100,out);
                    out.close();
                    Log.d(TAG, filename+" has been saved");

                }catch (Exception e){
                    e.printStackTrace();
                    Log.d(TAG, e.getMessage());
                }
          //  }
      //  }).start();
    }

}
