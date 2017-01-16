package com.marlin.tralp;

import android.app.Application;
import android.content.Context;
import android.graphics.YuvImage;

import com.marlin.tralp.Model.Mat;
import com.marlin.tralp.Model.Pair;
import com.marlin.tralp.Transcriber.Models.FeatureStructure;
import com.marlin.tralp.Transcriber.Models.FrameQueue;
import com.marlin.tralp.Transcriber.tr_Models.tr_FeatureStructure;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabriel on 16-03-15.
 */
public class MainApplication extends Application {
    private static Context mContext;
    private static MainApplication instance;

    static private List<Mat> frameBuffer;

    static public FrameQueue frameQueue;

    static public ArrayList<tr_FeatureStructure> annotation;

    static public void setFrameBuffer(List<Mat> received){
        frameBuffer = received;
    }

    static public List<Mat> getFrameBuffer(){
        return frameBuffer;
    }
    static public void frameBufferUnSet(){
        frameBuffer.clear(); // unset the list reference to its elements; clear from other classes if
                            //any object only have reference to the list
        frameBuffer = null;  //unsets this reference to the list
    }

    public static Context getContext() {
        return mContext;
    }

    public static MainApplication getInstance(){
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = getApplicationContext();
    }
}

