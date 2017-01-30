package com.marlin.tralp.Views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;

//import android.hardware.Camera;


import android.hardware.Camera;

import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.marlin.tralp.MainActivity;
import com.marlin.tralp.MainApplication;
import com.marlin.tralp.Model.Pair;
import com.marlin.tralp.R;
import com.marlin.tralp.Transcriber.ImageProcess.Controller;

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
//import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfRect;
//import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import static com.marlin.tralp.Constantes.Constantes.getQualCamera;

/**
 * Created by gabriel on 16-02-22.
 */
public class GravacaoVideo extends AppCompatActivity implements View.OnTouchListener, CameraBridgeViewBase.CvCameraViewListener2 {

    private CameraBridgeViewBase opencvCameraView;
    private Mat matGray;
    private File folder;
    private List<com.marlin.tralp.Model.Mat> frameBuffer;
    private long counterTime = 0 ;
    private long startTime;
    private MainApplication mApplication;
    private String state;
    private final static int mCameraId = getQualCamera();   //Camera.CameraInfo.CAMERA_FACING_BACK;     //CAMERA_FACING_FRONT;
    private int counter = 0;


    public void GravacaoVideo() {
        Log.i("GravacaoVideo: ", "Instantiated new " + this.getClass());
        mApplication = (MainApplication)this.getApplicationContext();
    }

    private BaseLoaderCallback openCVLoaderCallback = new BaseLoaderCallback(this){

        @Override
        public void onManagerConnected(int status){
            super.onManagerConnected(status);
            if(status == LoaderCallbackInterface.SUCCESS){
                Log.d("OpenCVLoader", "OpenCV Loaded Successfully");
                opencvCameraView.enableView();
                opencvCameraView.setOnTouchListener(GravacaoVideo.this);
            }
        }
    };

    public void onTrimMemory(int level) {
        Log.d("[GravacaoVideo]", "Memory Danger: "+level);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        state="started";
        Log.d("GravacaoVideo OnCreate", "mCameraId: " + mCameraId);

        opencvCameraView = new PortraitCameraView(this, mCameraId);
        opencvCameraView.setMaxFrameSize(750, 750);
        opencvCameraView.setCvCameraViewListener(this);
        opencvCameraView.enableFpsMeter();

        frameBuffer = new ArrayList<com.marlin.tralp.Model.Mat>();
        Log.d("[onCreate]", "Recording Class has been created");
        setContentView(opencvCameraView);
    }

    public boolean isCameraUsebyApp() {
        Camera camera = null;
        try {
            camera = Camera.open();
        } catch (RuntimeException e) {
            Log.d("[isCameraUsebyApp]", e.getMessage());
            return true;
        } finally {
            if (camera != null) camera.release();
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (state != "stopped"){
            state = "stopped";
            Log.d("[onTouch]", "pare a captura");
            opencvCameraView.disableView();
        }
        return false;
    }


    @Override
    public void onResume(){
        super.onResume();
        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, openCVLoaderCallback);
    }

    @Override
    public void onCameraViewStarted(int width, int height){
        startTime = SystemClock.currentThreadTimeMillis();
        Log.d("onCameraViewStarted ", " startTime: " + (startTime / 1000));
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        if(opencvCameraView != null){
            opencvCameraView.disableView();
        }
        Log.d("Exiting","trying to save");
    }

    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
//        if((tempTime /1000) > 3 && state != "stopped") {
//            state = "stopped";
//            Log.d("[time]", "should stop, tempTime: " + (tempTime / 1000) + " startTime: " + (startTime / 1000));
//            opencvCameraView.disableView();
////            Intent intent = new Intent(GravacaoVideo.this, ProcessView.class);//MainActivity.class);// MainActivity.class    CameraViewLayout
////            //startActivityFromFragment(new ProcessView(), intent, 0);
////            startActivity(intent);
//        }
////        Log.d("[time]", " tempTime: " + (tempTime / 1000) + " startTime: " + (startTime / 1000) + " counterTime: " + counterTime);
//        if(counterTime == 0){
//            counterTime = tempTime;
//            counter = 0;
//        }
//        if((tempTime-counterTime) > 1000){
//            counterTime = tempTime;
//            counter++;
//        }
        long tempTime = SystemClock.currentThreadTimeMillis();
        matGray = inputFrame.gray();
        com.marlin.tralp.Model.Mat mGray = new com.marlin.tralp.Model.Mat(matGray);
        mGray.second = (int) (tempTime - startTime)/1000;

//        MatOfRect palms = new MatOfRect();
//
//        if (mJavaDetector != null)
//            mJavaDetector.detectMultiScale(mGray, palms, 1.1, 2, 0, // TODO: objdetect.CV_HAAR_SCALE_IMAGE
//                    new org.opencv.core.Size(40,40), new org.opencv.core.Size(300,300));
//        Rect[] palmsArray = palms.toArray();
//        for (int i = 0; i < palmsArray.length; i++)
//            Imgproc.rectangle(matGray, palmsArray[i].tl(), palmsArray[i].br(), FACE_RECT_COLOR, 3);

//        saveFrameToBuffer(mGray);
        MainApplication.addToFrameBuffer(mGray);
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
//        if(opencvCameraView != null){
//            Log.d("onCameraViewStopped", "desabilitando");
//            opencvCameraView.disableView();
//        }
        Log.d("onCameraViewStopped", "indo para ProcessView");
//        mApplication.setFrameBuffer(frameBuffer);
//        MainApplication.setFrameBuffer(frameBuffer);
//        processImages();
        Intent intent;
        intent = new Intent(this, ResultadoCaptura.class);
        intent.putExtra("frase", "ainda vou processar");
        startActivity(intent);
    }

    private void processImages(){
        Log.d("processImages", "Cheguei a setar o buffer");

        Log.d("processImages", "Voltei com a frase: " + "ainda vou processar");
        Intent intent;
        intent = new Intent(this, ResultadoCaptura.class);
        intent.putExtra("frase", "ainda vou processar");
        startActivity(intent);

    }

    private void saveFrameToBuffer(com.marlin.tralp.Model.Mat frameToSave){
        frameBuffer.add(frameToSave);
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
        }
        if(folder.mkdir())
            Log.d("[prepareFolder]", "Folder Created!");
        else {
            Log.d("[prepareFolder]", "Write Error!");
        }
    }

    void deleteRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);

        fileOrDirectory.delete();
    }

    private void saveFrame(Mat frameToSave, String nameToSave){
        final String _TAG = "[saveFrame]";
        final Mat frame = frameToSave;
        final String filename = nameToSave;

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
        } catch (Exception e){
            e.printStackTrace();
            Log.d(_TAG, e.getMessage());
        }

    }

    public List<com.marlin.tralp.Model.Mat> getFrameBuffer(){
        return frameBuffer;
    }

}
