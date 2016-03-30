package com.marlin.tralp.Views;

import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.MenuItem;


import com.marlin.tralp.MainActivity;
import com.marlin.tralp.MainApplication;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.JavaCameraView;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvException;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

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
    private Mat matRGBA, matGray, matGrayT;
    private File folder;
    //private static int fileCounter, variance_counter;
    private List<Mat> frameBuffer;
    //private double [] variances;
    private long startTime;
    //int threadCounter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(); @TODO Display the view here and buttons
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        /* These deprecated libraries don't affect the project since we used them just to get hardware info*/
        final Camera cam = Camera.open();
        cam.setDisplayOrientation(90);
        final Camera.Parameters parameters = cam.getParameters();
        final List<Size> supportedVideoSizes = parameters.getSupportedVideoSizes();
        for (int i = 0; i< supportedVideoSizes.size(); i++)
            Log.d("Suported Video Sizes", supportedVideoSizes.get(i).toString());
        final Size size = supportedVideoSizes.get(0);
        opencvCameraView = new JavaCameraView(this,0);
        opencvCameraView.setMaxFrameSize(size.width, size.height);
        opencvCameraView.setCvCameraViewListener(this);
        opencvCameraView.enableFpsMeter();
        frameBuffer = new ArrayList<Mat>();
        //threadCounter = 0;
        //fileCounter = 0;
        //variance_counter = 0;
        //variances = new double[500];
        Log.d("[onCreate]", "Recording Class has been created");

        setContentView(opencvCameraView);


    }

    //@TODO NEED TO CREATE BUTTONS TO STOP THIS


    private BaseLoaderCallback openCVLoaderCallback = new BaseLoaderCallback(this){

        @Override
        public void onManagerConnected(int status){
            super.onManagerConnected(status);
            if(status == LoaderCallbackInterface.SUCCESS){
                Log.i("OpenCVLoader", "OpenCV Loaded Successfully");
                //Load OpenCV Resources now
//                opencvCameraView.setRotation(90);
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
        matGray = new Mat(width, height, CvType.CV_8UC1);
        matGrayT = new Mat(height, width, CvType.CV_8UC1);
        prepareFolder();
        startTime = (SystemClock.currentThreadTimeMillis())/1000;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(opencvCameraView != null){
            opencvCameraView.disableView();
        }
        Log.d("Exiting","trying to save");
        //@TODO Save and stash all frame files (Needed?)
        MainApplication mApp = (MainApplication)getApplicationContext();
        mApp.setFrameBuffer(frameBuffer);
        //@Answer not really possible, too slow to save
//        Mat outMat = new Mat();
//        MatOfDouble std = new MatOfDouble();
//        MatOfDouble mean = new MatOfDouble();
//        double var;
//        Iterator itr = frameBuffer.iterator();
//        while(itr.hasNext()) {
////                    while (threadCounter > 10) SystemClock.sleep(3);
//            Mat oneframe = (Mat)itr.next();
//            Imgproc.Laplacian(oneframe, outMat, CvType.CV_64F); //dst, out, CvType.CV_64F);
//            Core.meanStdDev(outMat, mean, std);
//            var = std.get(0,0)[0];
//            var = var * var;
//            //String _filename = "[var]:"+String.format("%.2f", var)+"test:" + fileCounter + ".png";
//            saveFrame(oneframe, _filename);
//            //fileCounter += 1 ;
//        }
//        frameBuffer.clear();
    }

    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        if(((SystemClock.currentThreadTimeMillis())/1000 - startTime) > 15) {
            Log.d("[time]", "should stop");
            Intent intent = new Intent(GravacaoVideo.this, CameraViewLayout.class);
            startActivity(intent);
        }
//        fileCounter++;
//        if((fileCounter%30) == 0 )
//            Log.d("[time]", "Time Elapsed:"+
//                    ((SystemClock.currentThreadTimeMillis())/1000 - startTime) );
        matGray = inputFrame.gray();
//        Core.transpose(matGray,matGrayT);
//        Core.flip(matGrayT, matGrayT, 1);
//        Mat mRgbaT = matGray.t();
//        Core.flip(matGray.t(), mRgbaT, 1);
//        Imgproc.resize(mRgbaT, mRgbaT, new org.opencv.core.Size(matGray.height(),matGray.width()));
//        return mRgbaT;
//        Mat here = new Mat();
//        rotate90(matGray, here);
        //matRGBA = inputFrame.rgba();
//        Mat out = new Mat();
//        MatOfDouble std = new MatOfDouble();
//        MatOfDouble mean = new MatOfDouble();
//        Imgproc.Laplacian(matGray,out, CvType.CV_64F); //dst, out, CvType.CV_64F);
//        Core.meanStdDev(out, mean, std);
//        double var = std.get(0,0)[0];
//        var = var * var;
//        variances[variance_counter] = var;
//        variance_counter++;
//        Imgproc.putText(matGray, "SomeText", new Point(50, 10), Core.FONT_HERSHEY_SIMPLEX, 10, new Scalar(255));
//        Imgproc.putText(matGray, ""+var, new Point(100, 10), Core.FONT_HERSHEY_SIMPLEX, 10, new Scalar(128));
//        Log.d("AboutSTD", std.toString());
//        Log.d("STD.Length", ""+std.get(0,0).length);
//        Log.d("Variance", ""+var);


        //saveFrame(matGray,"defaultName:" + fileCounter + ".png" );
        saveFrameToBuffer(matGray);
      return matGray;
    }
    private void rotate90(Mat source, Mat destination){
        destination.create(source.size(),source.type());
        Core.transpose(source, destination);
        Core.flip(destination, destination, 1);
    }
    @Override
    public void onCameraViewStopped(){
        Log.d("onCameraViewStopped", "Saindo");
        if(opencvCameraView != null){
            opencvCameraView.disableView();
        }
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
        final String _TAG = "[saveFrame]";
        final Mat frame = frameToSave;
        final String filename = nameToSave;

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                threadCounter++;
                Bitmap bmp = null;
                try {
                    bmp = Bitmap.createBitmap(frame.cols(), frame.rows(), Bitmap.Config.ARGB_8888);
                    Utils.matToBitmap(frame, bmp);
                } catch (CvException e) {
                    Log.d(_TAG, e.getMessage());
                }
                frame.release(); //?

                FileOutputStream out = null;
                File dest = new File(folder, filename);
                Log.d("FullPath", dest.toString());
                try{
                    out = new FileOutputStream(dest);
                    bmp.compress(Bitmap.CompressFormat.PNG,100,out);
                    out.close();
                    Log.d(_TAG, filename+" has been saved");

                }catch (Exception e){
                    e.printStackTrace();
                    Log.d(_TAG, e.getMessage());
                }
                //threadCounter--;
//            }
//        }).start();
    }

}
