package com.marlin.tralp.Transcriber.ImageProcess;

import android.graphics.Bitmap;
import android.util.Log;

import com.marlin.tralp.MainApplication;
import com.marlin.tralp.Model.Pair;
import com.marlin.tralp.Transcriber.Models.FrameQueue;
import com.marlin.tralp.Views.GravacaoVideo;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by gabriel on 16-02-16.
 */
public class Filter {
    private List<com.marlin.tralp.Model.Mat> frameBuffer; // List of <PhotoFrame, itsSecond>
    private FrameQueue frameQueue;
    private int seconds;

    public Filter(){
        frameBuffer = MainApplication.getFrameBuffer();
        Log.d("Filter", "amount of frames: " + frameBuffer.size());
        seconds = frameBuffer.get(frameBuffer.size()-1).second;
        frameQueue = new FrameQueue();
        frameQueue.setHowManySeconds(seconds);

    }
    public void process(){

        Mat out = new Mat();
        MatOfDouble std = new MatOfDouble();
        MatOfDouble mean = new MatOfDouble();

        double calulatedLaplacian = 0;
        int frameBufferSize = frameBuffer.size();

        //Process per each second
        int lastFrameBufferIndex = 0;
        for (int second = 0; second < seconds ; second++) {

            //Find how many frames are inside current second
            int thisSecondFrameAmount = 0;
            Log.d("Filter_FPS", "Second: "+second+" lastFrameBufferIndex: " + lastFrameBufferIndex + "  frameBuffer.second: " + frameBuffer.get(lastFrameBufferIndex).second);
            for(int i = lastFrameBufferIndex; i < frameBuffer.size(); i++)
                if (frameBuffer.get(i).second > second) {
                    thisSecondFrameAmount = i-lastFrameBufferIndex;
                    break;
                }
                else {
                    if (frameBuffer.get(i).second < second)
                        lastFrameBufferIndex++;
                }

            Log.d("Filter_FPS", "Second: "+second+" qtt: " + thisSecondFrameAmount + "  frameBuffer.size: " + frameBuffer.size()+" lastFrameBufferIndex: " + lastFrameBufferIndex);

            //Calculate how many frames (setOfFrames) we have of each of the **positions**
            int setOfFramesAmount = thisSecondFrameAmount/frameQueue.resolutionFramesAmount;
            Log.d("Filter_FPS - ", " setOfFramesAmount: " + setOfFramesAmount + "  frameQueue.resolutionFramesAmount: " + frameQueue.resolutionFramesAmount );
            //For each position of setOfFrames over frameQueue
            for (int position = 0; position < frameQueue.resolutionFramesAmount; position++) {

                //For each frame in setOfFrames classify
                //and insert over List<Pair<Integer frameBufferIndex, Double quality>>classificationList

                List<Pair<Integer, Double>> classificationList = new ArrayList<Pair<Integer, Double>>();
                int ceilIndex = lastFrameBufferIndex+setOfFramesAmount;
                Log.d("Filter_FPS - ", " frameBufferSize: " + frameBufferSize );
                if (ceilIndex > frameBufferSize) {
                    Log.d("Filter_FPS - ", " frameBufferSize: " + frameBufferSize );
                    ceilIndex = frameBuffer.size();
                }
                Log.d("Filter_FPS inicio for ", "ceilIndex: "+ceilIndex+" lastFrameBufferIndex: " + lastFrameBufferIndex+" setOfFramesAmount:"+setOfFramesAmount);
                for (; lastFrameBufferIndex < ceilIndex ; lastFrameBufferIndex++) {

                    if (ceilIndex > frameBuffer.size()) {
                        break;
                    }
                    /* CORE Blur Calculation*/
                    com.marlin.tralp.Model.Mat temp  = frameBuffer.get(lastFrameBufferIndex);
                    org.opencv.core.Mat tempMat = bitmapToMat(temp);
                    if(tempMat.size().area() == 0)
                        Log.d("Laplacian", "Area zero for " + second + " second and " + position + "group");
//                    Imgproc.Laplacian(new Mat(tempMat.nativeObj), out, CvType.CV_64F); //dst, out, CvType.CV_64F);
//                    Core.meanStdDev(out, mean, std);
//                    calulatedLaplacian = std.get(0,0)[0];
//                    calulatedLaplacian = calulatedLaplacian * calulatedLaplacian;
                    /* CORE Blur Calculation*/

                    temp.quality = 0; //calulatedLaplacian;
                    classificationList = insertOrderingAscending(classificationList,
                            new Pair<Integer, Double>(lastFrameBufferIndex, calulatedLaplacian));
                    Log.d("Filter_FPS", "calulatedLaplacian: "+calulatedLaplacian+" lastFrameBufferIndex: " + lastFrameBufferIndex);
                }
                Log.d("Filter_FPS final for ", "ceilIndex: "+ceilIndex+" lastFrameBufferIndex: " + lastFrameBufferIndex+" setOfFramesAmount:"+setOfFramesAmount);

                //insert on frameQueue at the right *second* and *position*
                while(!classificationList.isEmpty()){
                    // Is inserting from lowest quality to the Highest
                    Pair<Integer, Double> temp = classificationList.remove(0);
                    com.marlin.tralp.Model.Mat tempMat = frameBuffer.get(temp.x);
                    frameQueue.setMatFrame(second,position,0,tempMat);
                }
            }
        }
        Log.d("FinishedFilter", "frameQueue: " + frameQueue.toString());
        MainApplication.frameBufferUnSet(); //Free Memory
        MainApplication.frameQueue = frameQueue;
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
    private org.opencv.core.Mat bitmapToMat(com.marlin.tralp.Model.Mat img){
        Bitmap bmp = img.data;
        org.opencv.core.Mat tmp = new org.opencv.core.Mat(bmp.getHeight(), bmp.getWidth(), CvType.CV_8UC1);
        Utils.bitmapToMat(bmp, tmp);
        org.opencv.core.Mat t2 = new org.opencv.core.Mat();
        Imgproc.cvtColor(tmp, t2, Imgproc.COLOR_RGB2GRAY);
        tmp.release();
        return t2;
    }

}
