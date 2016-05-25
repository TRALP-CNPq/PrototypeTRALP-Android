package com.marlin.tralp.Views;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.MenuItem;
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
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
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

/**
 * Created by gabriel on 16-02-22.
 */
public class GravacaoVideo extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

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
        frameBuffer = new ArrayList<com.marlin.tralp.Model.Mat>();
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
    //        File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
    //        mCascadeFile = new File(cascadeDir, "palm.xml");
    //        mJavaDetector = new CascadeClassifier(mCascadeFile.getAbsolutePath());
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
        long tempTime = SystemClock.currentThreadTimeMillis();
        if((tempTime /1000) > 30) {
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
//        fileCounter++;
//        if((fileCounter%30) == 0 )
//            Log.d("[time]", "Time Elapsed:"+
//                    ((SystemClock.currentThreadTimeMillis())/1000 - startTime) );
        matGray = inputFrame.gray();
        com.marlin.tralp.Model.Mat mGray = new com.marlin.tralp.Model.Mat(matGray);
        mGray.second = (int) tempTime / 1000;  //(tempTime - startTime)/1000;

/// / novo codigo
        MatOfRect palms = new MatOfRect();

        if (mJavaDetector != null)
                mJavaDetector.detectMultiScale(mGray, palms, 1.1, 2, 0, // TODO: objdetect.CV_HAAR_SCALE_IMAGE
                        new org.opencv.core.Size(45,80), new org.opencv.core.Size(165,320));
/*        }
        else if (mDetectorType == NATIVE_DETECTOR) {
            if (mNativeDetector != null)
                mNativeDetector.detect(mGray, faces);
        }
        else {
            Log.e(TAG, "Detection method is not selected!");
        }
*/
        Rect[] palmsArray = palms.toArray();
        for (int i = 0; i < palmsArray.length; i++)
            Imgproc.rectangle(matGray, palmsArray[i].tl(), palmsArray[i].br(), FACE_RECT_COLOR, 3);


//        Core.transpose(matGray,matGrayT);
//        Core.flip(matGrayT, matGrayT, 1);
//        Mat mRgbaT = matGray.t();
//        Core.flip(matGray.t(), mRgbaT, 1);
//        Imgproc.resize(mRgbaT, mRgbaT, new org.opencv.core.Size(matGray.height(),matGray.width()));
//        return mRgbaT;
//        Mat here = new Mat();
//        rotate90(matGray, here);
//        matRGBA = inputFrame.rgba();


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




        saveFrameToBuffer(mGray);
        //saveFrame(matGray,"defaultName:" + fileCounter + ".png" );
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
//        Intent intent = new Intent(GravacaoVideo.this, ProcessView.class);//CameraViewLayout.class);// MainActivity.class
//        //startActivityFromFragment(new ProcessView(), intent, 0);
//        startActivity(intent);
        if(opencvCameraView != null){
            Log.d("onCameraViewStopped", "desabilitando");
            opencvCameraView.disableView();
        }
        Log.d("onCameraViewStopped", "indo para ProcessView");
        processImages(matGray);
//        Intent intent = new Intent(GravacaoVideo.this, ProcessView.class);//CameraViewLayout.class);// MainActivity.class
//        //startActivityFromFragment(new ProcessView(), intent, 0);
//        startActivity(intent);
    }

    private void processImages(final Mat tMatGray){
        Handler uiThreadHandler;
        Thread imageProcessControllerThread;

        uiThreadHandler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message inputMessage) {
                Log.d("msg", " " + inputMessage.what);
                //String receivedObject = (String) inputMessage.obj;
                //String someKey = inputMessage.getData().getString("thisKey");
//                TextView mTxtView = (TextView)findViewById(R.id.textView); // getView().
//                mTxtView.setText("Consegui!! "+inputMessage.what);
                //mTxtView.setText(receivedObject);
                //Log.d("msg", someKey);
                //filterThread.interrupt();
                //filterThread.interrupt();
            }
        };
        MainApplication.setFrameBuffer(frameBuffer);
        imageProcessControllerThread = new Thread(new Controller((MainApplication)this.getApplicationContext(), uiThreadHandler));
        imageProcessControllerThread.start();

//        Thread t = new Thread(new Runnable(){
//
//            @Override
//            public void run() {
//                try {
//                    final int imgsize = (int) (tMatGray.total()*tMatGray.channels());
//                    byte[] data = new byte[imgsize];
//                    tMatGray.get(0,0,data);
//                    final int col = tMatGray.cols();
//                    final int row = tMatGray.rows();
//
//                    mHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getBaseContext(), "size:"+imgsize+" row:"+row+" col:"+col, Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                    //--- SEND IMAGE TO SERVER ---//
//                    Socket s = new Socket ("192.168.1.25", 6000);
//
//                    DataOutputStream dout = new DataOutputStream(s.getOutputStream());
//                    dout.writeInt(col);
//                    dout.writeInt(row);
//                    dout.writeInt(imgsize);
//                    dout.write(data);
//
//                    mHandler.post(new Runnable() {
//                        @Override
//                        public void run() {
//                            Toast.makeText(getBaseContext(), "Dout written", Toast.LENGTH_SHORT).show();
//                        }
//                    });
//
//                    dout.flush();
//                    dout.close();
//                    s.close(); //close socket
//
//                } catch (UnknownHostException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        t.start();
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
    private void saveFrameToBuffer(com.marlin.tralp.Model.Mat frameToSave){
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

    public List<com.marlin.tralp.Model.Mat> getFrameBuffer(){
        return frameBuffer;
    }
}
