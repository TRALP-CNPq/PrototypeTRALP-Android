package com.marlin.tralp;

import android.app.Application;
import com.marlin.tralp.Model.Mat;
import com.marlin.tralp.Model.Pair;
import com.marlin.tralp.Transcriber.Models.FeatureStructure;
import com.marlin.tralp.Transcriber.Models.FrameQueue;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabriel on 16-03-15.
 */
public class MainApplication extends Application {
    static private List<Mat> frameBuffer;

    static public FrameQueue frameQueue;

    static public ArrayList<FeatureStructure> annotation;

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
}

