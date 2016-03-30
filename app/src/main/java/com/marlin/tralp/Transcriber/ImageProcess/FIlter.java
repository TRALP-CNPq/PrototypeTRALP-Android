package com.marlin.tralp.Transcriber.ImageProcess;

import com.marlin.tralp.MainApplication;

import org.opencv.core.Mat;

import java.util.List;

/**
 * Created by gabriel on 16-02-16.
 */
public class Filter {
    private List<Mat> frameBuffer;
    private MainApplication mApp;
    public class Classification{
        String [] best;
        String [] worst;
    }
    public Classification[] Filter(String frames_type_loaded, MainApplication app){
        Classification [] mock = new Classification[2];
        // @TODO Make classification based on Laplacian to classify per second
        MainApplication mApp = app;
        frameBuffer = mApp.getFrameBuffer();
        return mock;
    }
    public void process(){
        //@TODO do main classification process
    }
    public Mat getNext(){
        //@Todo return the next Mat to be used throughtout the ImageProcess pipeline

        return new Mat();
    }
    public Mat getNextOnFail(){
        //@TODO same as above, but keeping close to the latest

        //@TODO should return NULL on emptiness of frame neighborhood
        return new Mat();
    }
}
