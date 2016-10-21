package com.marlin.tralp.Views;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;

//import android.hardware.Camera;


import android.hardware.Camera;
//import android.hardware.camera2.CameraAccessException;
//import android.hardware.camera2.CameraCaptureSession;
//import android.hardware.camera2.CameraCharacteristics;
//import android.hardware.camera2.CameraDevice;
//import android.hardware.camera2.CameraManager;
//import android.hardware.camera2.CameraMetadata;
//import android.hardware.camera2.CaptureRequest;
//import android.hardware.camera2.CaptureResult;
//import android.hardware.camera2.TotalCaptureResult;
//import android.hardware.camera2.params.StreamConfigurationMap;

import android.os.Bundle;
import android.os.Environment;
//import android.os.Handler;
//import android.os.Looper;
//import android.os.Message;
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
    private Mat matRGBA, matGray, matGrayT;
    private File folder;
    //private static int fileCounter, variance_counter;
    private List<com.marlin.tralp.Model.Mat> frameBuffer;
    private long counterTime = 0 ;
    private int counter = 0;
    //private double [] variances;
    private long startTime;
    //int threadCounter;
    private MainApplication mApplication;
    private File mCascadeFile;
    private CascadeClassifier mJavaDetector;
    //    private DetectionBasedTracker  mNativeDetector;
    private static final Scalar    FACE_RECT_COLOR     = new Scalar(0, 255, 0, 255);
    private Button btnParar;
    //   private CameraBridgeViewBase mOpenCvCameraView;
    private String state;
    private final static int mCameraId = getQualCamera();   //Camera.CameraInfo.CAMERA_FACING_BACK;     //CAMERA_FACING_FRONT;

    public void GravacaoVideo() {
        Log.i("GravacaoVideo: ", "Instantiated new " + this.getClass());
    }

    private BaseLoaderCallback openCVLoaderCallback = new BaseLoaderCallback(this){

        @Override
        public void onManagerConnected(int status){
            super.onManagerConnected(status);
            if(status == LoaderCallbackInterface.SUCCESS){
                Log.i("OpenCVLoader", "OpenCV Loaded Successfully");
                opencvCameraView.enableView();
                opencvCameraView.setOnTouchListener(GravacaoVideo.this);
            }
            int rawResource = R.raw.palm;
            MainApplication mApp = (MainApplication)getApplicationContext();
            try {
                InputStream is = mApp.getResources().openRawResource(rawResource);
                File cascadeDir = mApp.getDir("cascade", Context.MODE_PRIVATE);
                File mCascadeFile = new File(cascadeDir, "cascade["+rawResource+"].xml");
                FileOutputStream os = null;
                os = new FileOutputStream(mCascadeFile);
                byte[] buffer = new byte[4096];
                int bytesRead;
                while((bytesRead = is.read(buffer)) != -1)
                {
                    os.write(buffer, 0, bytesRead);
                }
                is.close();
                os.close();
                mJavaDetector = new CascadeClassifier(mCascadeFile.getAbsolutePath());
            } catch (java.io.IOException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        state="started";
        Log.d("GravacaoVideo OnCreate", "mCameraId: " + mCameraId);

        opencvCameraView = new JavaCameraView(this,mCameraId);

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
        state="stopped";
        Log.d("[onTouch]", "pare a captura");
        onCameraViewStopped();
        return false;
    }
    //@TODO NEED TO CREATE BUTTONS TO STOP THIS

    private void CarregarListeners() {
        btnParar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    btnParar.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.presence_video_busy));
                    //ChamarEventoConfirma();

                } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btnParar.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.presence_video_online));
                }
                return true;
            }
        });
    }



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
        //@TODO Save and stash all frame files (Needed?)
        MainApplication mApp = (MainApplication)getApplicationContext();
        mApp.setFrameBuffer(frameBuffer);
    }

    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        long tempTime = SystemClock.currentThreadTimeMillis();
        if((tempTime /1000) > 8 || state == "stopped") {
            Log.d("[time]", "should stop, tempTime: " + (tempTime / 1000) + " startTime: " + (startTime / 1000));
            Intent intent = new Intent(GravacaoVideo.this, ProcessView.class);//MainActivity.class);// MainActivity.class    CameraViewLayout
            //startActivityFromFragment(new ProcessView(), intent, 0);
            startActivity(intent);
        }
        Log.d("[time]", " tempTime: " + (tempTime / 1000) + " startTime: " + (startTime / 1000) + " counterTime: " + counterTime);
        if(counterTime == 0){
            counterTime = tempTime;
            counter = 0;
        }
        if((tempTime-counterTime) > 1000){
            counterTime = tempTime;
            counter++;
        }
        matGray = inputFrame.gray();
        com.marlin.tralp.Model.Mat mGray = new com.marlin.tralp.Model.Mat(matGray);
        mGray.second = (int) tempTime / 1000;  //(tempTime - startTime)/1000;
        
/// / novo codigo
        MatOfRect palms = new MatOfRect();

        if (mJavaDetector != null)
            mJavaDetector.detectMultiScale(mGray, palms, 1.1, 2, 0, // TODO: objdetect.CV_HAAR_SCALE_IMAGE
                    new org.opencv.core.Size(45,80), new org.opencv.core.Size(165,320));
        Rect[] palmsArray = palms.toArray();
        for (int i = 0; i < palmsArray.length; i++)
            Imgproc.rectangle(matGray, palmsArray[i].tl(), palmsArray[i].br(), FACE_RECT_COLOR, 3);

        saveFrameToBuffer(mGray);
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
            Log.d("onCameraViewStopped", "desabilitando");
            opencvCameraView.disableView();
        }
        Log.d("onCameraViewStopped", "indo para ProcessView");
        processImages(matGray);
    }

    private void processImages(final Mat tMatGray){
        String frase;
        MainApplication.setFrameBuffer(frameBuffer);
        frase = new Controller((MainApplication)this.getApplicationContext()).process();
        Log.d("processImages", "Voltei com a frase: " + frase);
        Intent intent;//CameraViewLayout.class);// MainActivity.class    ProcessView
        intent = new Intent(this, ResultadoCaptura.class);
        intent.putExtra("frase", frase);
        startActivity(intent);

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
    private void saveFrameToBuffer(com.marlin.tralp.Model.Mat frameToSave){
        frameBuffer.add(frameToSave);
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
