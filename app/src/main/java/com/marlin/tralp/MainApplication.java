package com.marlin.tralp;

import android.app.Application;

import org.opencv.core.Mat;

import java.util.List;

/**
 * Created by gabriel on 16-03-15.
 */
public class MainApplication extends Application {
    private List<Mat> frameBuffer;

    public void setFrameBuffer(List<Mat> received){
        frameBuffer = received;
    }

    public List<Mat> getFrameBuffer(){
        return frameBuffer;
    }
}
