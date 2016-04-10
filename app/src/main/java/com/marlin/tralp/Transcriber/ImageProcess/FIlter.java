package com.marlin.tralp.Transcriber.ImageProcess;

import android.util.Log;

import com.marlin.tralp.MainApplication;
import com.marlin.tralp.Model.Pair;
import com.marlin.tralp.Transcriber.Models.FrameQueue;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gabriel on 16-02-16.
 */
public class Filter {
    private List<com.marlin.tralp.Model.Mat> frameBuffer; // List of <PhotoFrame, itsSecond>
    private FrameQueue frameQueue;
    private MainApplication mApp;
    private int seconds;

    public Filter(MainApplication app){
        // @TODO Make classification based on Laplacian to classify per second
        MainApplication mApp = app;
        frameBuffer = mApp.getFrameBuffer();
        seconds = frameBuffer.get(frameBuffer.size()-1).second;
        frameQueue = new FrameQueue();
        frameQueue.setHowManySeconds(seconds);
    }
    public void process(){
        //@TODO Finish main classification process
        //@TODO Cut the right portion of the image for Laplacian blur classification

        Mat out = new Mat();
        MatOfDouble std = new MatOfDouble();
        MatOfDouble mean = new MatOfDouble();
        //double dStd ;// = std.get(0,0)[0];
        double calulatedLaplacian = 0;

        //Process per each second
        int lastFrameBufferIndex = 0;
        for (int second = 0; second < seconds ; second++) {

            //Find how many frames are inside current second
            int thisSecondFrameAmount = 0;
            for(int i = lastFrameBufferIndex; i < frameBuffer.size(); i++)
                if (frameBuffer.get(i).second > second) {
                    thisSecondFrameAmount = i;
                    break;
                }

            Log.d("Filter_FPS", "Second: "+second+" qtt: " + thisSecondFrameAmount);

            //Calculate how many frames (setOfFrames) we have of each of the **positions**
            int setOfFramesAmount = thisSecondFrameAmount/frameQueue.resolutionFramesAmount;

            //For each position of setOfFrames over frameQueue
            for (int position = 0; position < frameQueue.resolutionFramesAmount; position++) {

                //For each frame in setOfFrames classify
                //and insert over List<Pair<Integer frameBufferIndex, Double quality>>classificationList

                List<Pair<Integer, Double>> classificationList = new ArrayList<Pair<Integer, Double>>();
                int ceilIndex = lastFrameBufferIndex+setOfFramesAmount;

                for (; lastFrameBufferIndex < ceilIndex; lastFrameBufferIndex++) {

                    /* CORE Blur Calculation*/
                    com.marlin.tralp.Model.Mat tempMat = frameBuffer.get(lastFrameBufferIndex);
                    Imgproc.Laplacian(tempMat, out, CvType.CV_64F); //dst, out, CvType.CV_64F);
                    Core.meanStdDev(out, mean, std);
                    calulatedLaplacian = std.get(0,0)[0];
                    calulatedLaplacian = calulatedLaplacian * calulatedLaplacian;
                    /* CORE Blur Calculation*/

                    tempMat.quality = calulatedLaplacian;
                    classificationList = insertOrderingAscending(classificationList,
                            new Pair<Integer, Double>(lastFrameBufferIndex, calulatedLaplacian));
                }

                //insert on frameQueue at the right *second* and *position*
                while(!classificationList.isEmpty()){
                    // Is inserting from lowest quality to the Highest
                    Pair<Integer, Double> temp = classificationList.remove(0);
                    com.marlin.tralp.Model.Mat tempMat = frameBuffer.get(temp.x);
                    frameQueue.setMatFrame(second,position,0,tempMat);
                }
            }
        }
        mApp.frameBufferUnSet(); //Free Memory
        mApp.frameQueue = frameQueue;
    }
    private List<Pair<Integer, Double>> insertOrderingAscending(List<Pair<Integer, Double>> defaultList,Pair<Integer, Double> pair ){
        Pair<Integer, Double> temp;
        for (int i = 0; i < defaultList.size() ; i++) {
            if(defaultList.get(i).y > pair.y){
                temp = defaultList.get(i);
                defaultList.set(i, pair);
                pair = temp;
            }
        }
        defaultList.add(pair);
        return defaultList;
    }
}
